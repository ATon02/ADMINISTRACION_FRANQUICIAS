This projects was developed with spring-webflux, using persistence in MySql and languaje of programing Java 17.
Steps to run from the code editor
    1. Create schema "management" in MySQL, in te host "localhost:3306"


Also, docker was used to perform containerized deployment in a local environment.
Steps for deploy with Docker:
    1. Excute:  mvn clean install -DskipTests     //Generate jar
    2. Execute: docker-compose build    //Build image
    3. Execute: docker-compose up db    //Run container db MySQL
    4. Execute:  docker-compose up java_app    //Run container java project

Note: The machine on which it is deployed must be running docker 


MODEL FRANCHISE
    {
        "name":"Franchise name"   //String - There should be not more franchises with this name.
    }

MODEL BRANCH
    {
        "name":"Branch name"   //String
        "franchiseId": 1    //Long - There must be a franchise with this id
    }

MODEL PRODUCT
    {
        "name":"Branch name"    //String
        "stock":1   //Long
        "branchId": 1    //Long - There must be a branch with this id
    }