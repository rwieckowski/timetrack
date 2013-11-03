timetrack
=========

Timetrack application

Domain rules and validation
---------------------------
**DomainException** is a subclass of **Exception**, it requires to be handled on the presentation layer.

1. Null checking - when instantiating an domain object, throws **NullPointerException** through guava
   **Preconditions** class.

2. Validation - when instantiating an domain object, checking if the request to the application is correct, general
   mechanism to handle all kinds of validations, requires only data from single, throws **InvalidEntityException**
   a subclass of **DomainException**.

   *TO-DO*: How to validate composite objects?

3. Single application rule - when executing business logic, assumes that request is correct, requires getting data from
   the repository to check, throws subclass of **DomainException**.

4. Multiple application rule - when executing business logic, assumes that request is correct, handled problematically
   by creating collections of value object, throws subclass of **DomainException**.

Checking cost grows with every level. First rule is the cheapest. Last one is most expensive.