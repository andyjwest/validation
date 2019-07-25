This is a highly customizable validation library.

#The Validator Class
The Validator class hold the core logic of the library. Three method exist for your convince. 

##validate(Supplier<ValidationError>... validators)
Provide an array of validators and this method will throw a ValidationException containing a list of all the Validation errors.

##runIfValid(Supplier<T> functionToCallIfValidationPasses, Supplier<ValidationError>... validators)
Like the validate method, only the first parameter is the function that you would like to be executed if there are not validation errors.

A note: the suppliers that are passed into either method can be wrapped in a lambda function, allowing your validators the option to pass any arguments they might need. Example:

```java
public class PersonService {

    //Handle DI however you see fit...
    public PersonService(Validator validator, PersonDao personDao){
        this.validator = validator;
        this.personDao = personDao;
    }
    
    private Validator validator;
    private PersonDao personDao;
    

    public Person createPerson(Person person) throws ValidationException {
        validator.runIfValid(
                () -> personDao.createPerson(person), //Run this if it is valid
                //All the validators
                () -> PersonValidator.firstNameRequired(person.getFirstName()), //Wrapping the function 
                () -> PersonValidator.booleanIssue("")
                );

        return personDao.createPerson(person);
    }
    
    public Person updatePerson(Person person) throws ValidationException{
        validator.validate(
                () -> PersonValidator.firstNameRequired(person.getFirstName()),
                PersonValidator::someMethodThatTakesNoParams,
                () -> PersonValidator.lastNameRequired(person.getLastName())
        );
        return personDao.updatePerson(person);
    }
}
```
