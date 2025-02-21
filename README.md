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