# Quiz API
Simple API written in Java using Spring Boot and PostgreSQL.

### API Endpoints

1) The Category API
    - POST /api/v1/categories Add a new category.
    - GET /api/v1/categories Retrieve all categories.
    - GET /api/v1/categories/{id} Get a category by its id.
    - PUT /api/v1/categories/{id} Update a category by its id.
    - DELETE /api/v1/categories/{id} Delete a category by its id

1) The Quiz API
    - POST /api/v1/quizzes Add a new Quiz.
    - GET /api/v1/quizzes Retrieve all Quizzes.
    - GET /api/v1/quizzes/{id} Get a Quiz by its id.
    - PUT /api/v1/quizzes/{id} Update a Quiz by its id.
    - DELETE /api/v1/quizzes/{id} Delete a Quiz by its id.

1) The Question API
    - POST /api/v1/questions Add a new Question.
    - GET /api/v1/questions Retrieve all Questions.
    - GET /api/v1/questions/{id} Get a Question by its id.
    - PUT /api/v1/questions/{id} Update a Question by its id.
    - DELETE /api/v1/questions/{id} Delete a Question by its id.

1) The Answer API
    - POST /api/v1/answers Add a new Answer.
    - GET /api/v1/answers Retrieve all Answers.
    - GET /api/v1/answers/{id} Get an Answer by its id.
    - PUT /api/v1/answers/{id} Update an Answer by its id.
    - DELETE /api/v1/answers/{id} Delete an Answer by its id.

### Documentation
- /swagger-ui
- /api-docs

### Development

##### Prerequisites
Download and Install the [OpenJDK](https://openjdk.java.net/install/).  
Download and Install the [PostgreSQL](https://www.postgresql.org/https://www.postgresql.org/), an open source relational database.  
Install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git), the free and open source distributed version control system.
Install [Docker](https://docs.docker.com/get-docker/), an open source containerization platform.

##### Clone the project
Run `git@github.com:orbanszlrd/quiz-api.git` to clone the repository from [GitHub](https://github.com/orbanszlrd/quiz-api).

##### Create a Database
After installing PostgreSQL, create a database for your project.

##### Create the necessary Environment Variables
```
SPRING_DATASOURCE_URL=jdbc:postgresql://<hostname>:5432/<dbname>
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>
```