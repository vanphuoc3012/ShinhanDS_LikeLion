# Practice Project 3: Spring Boot Test, Actuator, DevTools

# I - Spring Boot Test

1. Task 1: Create a three layers CRUD app

    ![img_1.png](crud-app.png)
2. Task 2: Write test case for each layer:

   1. Repository Layer:
      (using H2 database with data from data.sql)
        ![img.png](test-repository.png)
   2. Service Layer:
     - Setup:
       ![img.png](service-setup.png)
     - Test findAll():
        ![img.png](service-findAll.png)
     - Test deleteById()
        ![img.png](service-deleteById.png)
     - Check other test case in TutorialServiceTest
    
   3. Controller Layer:
    - Setup:
        ![img.png](controller-setup.png)
    - Test case: POST valid and invalid entity:
        ![img.png](controller-postTest.png)
    - Test case: GET find exist and not exist entity:
        ![img.png](controller-getByIdTest.png)
    - Test case: DELETE delete tutorial by id and delete all tutorial
        ![img.png](controller-deleteTest.png)
    - Check other test case in TutorialControllerTest

# II - Actuator and Devtool
1. Task 1: Actuator
    - Set actuator properties:
        ![img.png](actuator-properties.png)
    
     
