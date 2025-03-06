# Advanced Programming 2024/2025 - Assignments
> By Ida Made Revindra Dikta Mahendra - 2306275954

## Table of Contents
- [Module 1: Coding Standards](#module-1-coding-standards)
- [Module 2: CI/CD & DevOps](#module-2-cicd--devops)
- [Module 3: OO Principles & Software Maintainability](#module-3-oo-principles--software-maintainability)
- [Module 4: Refactoring and TDD](#module-4-refactoring-and-tdd)

# Module 1: Coding Standards

## Reflection 1

### Principles Applied

#### 1. Meaningful Names
I used meaningful names for variables and functions to make the code more readable and understandable. For example:
- 'editProductPost' is the name of the function that handles the POST request to edit a product.
- 'deleteProduct' is the name of the function that handles the GET request to delete a product.

#### 2. Function
I made sure that each function does only one thing and does it well. The functions has no side effects and are easy to understand.

### Areas for Improvements

#### 1. Error Handling
I would like to improve the error handling in the application. Currently, the application does not handle errors gracefully and does not provide meaningful error messages to the user.

#### 2. Comments
I would like to add more comments to the code to explain the purpose of each function and how it works.

#### 3. Authentication and Authorization
In the time being, the application does not have any authentication or authorization mechanism. I would like to add authentication and authorization to the application to make it more secure.

#### 4. Input Data Validation
I would like to add input validation to the application to prevent users from entering invalid inputs that could cause the application to crash or behave unexpectedly.

# Module 2: CI/CD & DevOps

# Reflection

## Code Quality Issues Fixed

1. Remove Unused Imports

    Some of the imports in the code were not used. I removed these imports to improve the code quality.
    
    For example:
    ```java
    import id.ac.ui.cs.advprog.eshop.service.ProductService;
    import org.springframework.boot.test.mock.mockito.MockBean;
    ```
   
2. Group Dependencies by Their Destination
    
    I grouped the dependencies in the `build.gradle.kts` file by their destination to make it easier to read and understand.
    
    For example:
    ```java
    dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    compileOnly("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    }
    ```
3. Avoid Field Dependency Injection
    
    I replaced field dependency injection with constructor dependency injection to improve the code quality.
    
    For example:
    ```java
    @Autowired
    private ProductService productService;
    ```
    to
    ```java
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    ```
   
4. Explain Empty Methods
    
    I added comments to explain why some methods are empty to improve the code quality.
    
    For example:
    ```java
    ...
    @Test
    void contextLoads() {
        // Does nothing for the time being
        // Can be utilized to ensure  context loads if needed in future developments.
    }
    ...
    ```
5. Remove the declaration of thrown exception 'java.lang.Exception'
    
    I removed the declaration of thrown exception 'java.lang.Exception' from the method signature to improve the code quality.
    
    For example:
    ```java
    @Test
    void createAndFindProduct(ChromeDriver driver) throws Exception {
    ```
    to
    ```java
    @Test
    void createAndFindProduct(ChromeDriver driver) {
    ```
6. Make Sure Every Test Method has Assertions
    
    I added assertions to the test methods that did not have any assertions to improve the code quality.
    
    For example:
    ```java
    @Test
    void mainMethodStartsApplication() {
        EshopApplication.main(new String[]{});
    }
    }
    ```
    to
    ```java
    @Test
    void mainMethodStartsApplication() {
        EshopApplication.main(new String[]{});
        assertTrue(true);
    }
    ```
   
## CI/CD Implementation
The current implementation partially meets the definition of Continuous Integration (CI) and Continuous Deployment (CD).

### Continuous Integration (CI)
- Continuous Integration is achieved by automatically running tests on every push and pull request.
- The CI workflow includes steps to check out the repository, set up the Java toolchain, and run unit tests, which helps in identifying issues early in the development process.

### Continuous Deployment (CD)
- Continuous Deployment is not fully implemented in the current workflow.
- The deployment service for this project (Koyeb) already provides a feature to deploy the application automatically when there's a push to the main branch.

# Module 3: OO Principles & Software Maintainability

## Reflection

### SOLID Principles Applied

1. Single Responsibility Principle (SRP)
   
   Each controller class has a single responsibility. For example, the CarController class is responsible for handling HTTP requests related to cars, and the ProductController class handles requests related to products.

2. Open/Closed Principle (OCP)

   The controllers are open for extension but closed for modification. For example, you can extend the ProductController to create a CarController without modifying the existing ProductController code.

3. Liskov Substitution Principle (LSP)

   The CarController can be used in place of the ProductController without affecting the correctness of the program. This is achieved by extending the ProductController and maintaining the expected behavior.

4. Interface Segregation Principle (ISP)

   The services used by the controllers are defined by specific interfaces (CarService, ProductService), ensuring that the controllers only depend on the methods they actually use.

5. Dependency Inversion Principle (DIP)

   The controllers depend on abstractions (interfaces) rather than concrete implementations. For example, the CarController depends on the CarService interface, and the ProductController depends on the ProductService interface.

### Advantages of applying SOLID principles to your project with examples.

1. **Single Responsibility Principle (SRP)**  
   - Advantage: Each class has a single responsibility, making the code easier to understand and maintain.
   - Example: The CarController class handles only car-related HTTP requests, while the ProductController handles product-related requests.


2. **Open/Closed Principle (OCP)**
   - Advantage: Classes are open for extension but closed for modification, allowing new functionality to be added without changing existing code.
   - Example: You can extend the ProductController to create a CarController without modifying the ProductController code.


3. **Liskov Substitution Principle (LSP)**
   - Advantage: Subtypes can replace their base types without affecting the correctness of the program, ensuring that derived classes extend base classes without changing their behavior.
   - Example: The CarController can be used in place of the ProductController because it extends the ProductController and maintains the expected behavior.
   

4. **Interface Segregation Principle (ISP)**
   - Advantage: Clients are not forced to depend on interfaces they do not use, leading to more modular and decoupled code.
   - Example: The CarService and ProductService interfaces ensure that controllers only depend on the methods they actually use.
   

5. **Dependency Inversion Principle (DIP)**
   - Advantage: High-level modules do not depend on low-level modules; both depend on abstractions, making the code more flexible and easier to test.
   - Example: The CarController depends on the CarService interface, and the ProductController depends on the ProductService interface, rather than concrete implementations.

### Disadvantages of not applying SOLID principles to your project with examples.

1. **Single Responsibility Principle (SRP)** 
   - Disadvantage: Classes with multiple responsibilities become harder to understand, maintain, and test.
   - Example: If CarController also handled user authentication, any change in authentication logic would require changes in CarController, increasing the risk of introducing bugs.
   

2. **Open/Closed Principle (OCP)**
   - Disadvantage: Modifying existing classes to add new functionality can introduce bugs and make the codebase less stable.
   - Example: If you need to add a new type of product and modify ProductController directly, you risk breaking existing functionality.
   

3. **Liskov Substitution Principle (LSP)**  
   - Disadvantage: Subtypes that do not adhere to the behavior of their base types can cause unexpected behavior and bugs.
   - Example: If CarController does not correctly implement methods from ProductController, it could lead to runtime errors when used in place of ProductController.


4. **Interface Segregation Principle (ISP)**
   - Disadvantage: Clients are forced to depend on interfaces they do not use, leading to bloated and less maintainable code.
   - Example: If CarService had methods unrelated to car operations, CarController would have unnecessary dependencies, making it harder to understand and maintain.


5. **Dependency Inversion Principle (DIP)**
   - Disadvantage: High-level modules depend on low-level modules, making the code less flexible and harder to test.
   - Example: If CarController directly depended on a concrete implementation of CarRepository instead of an interface, it would be difficult to replace CarRepository with a different implementation for testing or future changes.

# Module 4: Refactoring and TDD

## Reflection

### Reflection on TDD Flow

#### 1. Correctness
   - **Evaluation**: The tests ensure that the code behaves as expected and meets the requirements. Writing tests first helps in defining the expected behavior and catching bugs early.
   - **Improvement**: Increase test coverage to ensure all edge cases and critical paths are tested.

#### 2. Maintainability
   - **Evaluation**: Refactoring the code after making the tests pass improves its structure and maintainability. Clear and descriptive test names and comments also contribute to maintainability.
   - **Improvement**: Continue to refactor code and improve test readability to make the codebase easier to maintain.

#### 3. Productive Workflow
   - **Evaluation**: The TDD flow provides a productive workflow by ensuring that tests are written before production code, leading to fewer bugs and more maintainable code.
   - **Improvement**: Optimize the TDD flow by writing smaller, more focused tests and refactoring in smaller increments to maintain a smooth workflow.

### Reflection on F.I.R.S.T Principles Applied

#### 1. Fast  
   - **Evaluation**: The tests are relatively fast as they are unit tests and do not depend on external systems.
   - **Improvement**: Mockito setup and teardown can be optimized to make the tests even faster.

#### 2. Independent  
   - **Evaluation**: The tests are independent as they use mocks and do not rely on each other.
   - **Improvement**: Continue to use mocking frameworks to isolate tests and ensure they do not depend on the state of other tests.

#### 3. Repeatable  
   - **Evaluation**: The tests are repeatable and produce the same results every time they are run.
   - **Improvement**: Ensure that future tests do not depend on external factors like network or database state.

#### 4.Self-validating  
   - **Evaluation**: The tests are self-validating as they use assertions to check the expected outcomes.
   - **Improvement**: Ensure that all tests have clear and meaningful assertions to validate the results.

#### 5. Timely  
   - **Evaluation**: The tests were written in a timely manner, before the production code.
   - **Improvement**: Maintain the practice of writing tests before production code to ensure timely feedback.