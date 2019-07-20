# MyMediaLib

Stack:
 - Java 12
 - Spring 5
 - Angular 5
 - Docker
 
Docker images: https://cloud.docker.com/u/valychbreak/repository/docker/valychbreak/mymedialib

## Build

 - Install JDK 12
 - Install maven 3.\*.\*
 - Copy ```backend/src/main/resources/application.yml.template``` to ```backend/src/main/resources/application.yml```, change db properties if needed
 - Execute: ```mvn clean install -DskipTests```
 - Copy ```frontend/src/main/frontend/dist``` to ```backend/src/main/resources/static```
 - Execute from ```backend/```: ```mvn clean install -DskipTests``` 
 - (Optional) Run: ```docker build -t valychbreak/mymedialib ./backend```
 
## Run

### Without docker
 - Install PostgresSQL locally or use remote DB (modify application.yml in backend project according to your db)
 - Setup new database according to application.yml in backend project
 - From ```backend/```, run: ```mvn flyway:migrate -Dflyway.url=<db_url> -Dflyway.user=<db_user> -Dflyway.password=<user_password>```
 - Run: ```mvn spring-boot:run```
 
### With docker
 - Run: ```docker stack deploy -c backend/stack.yml mymedialib```
 - Goto http://localhost:3333/. Login with pgadmin4@pgadmin.org and admin
 - Create user "dbuser" with password "dbtest"
 - Create database "mymedialib"
 - From .../backend project, run: ```mvn flyway:migrate -Dflyway.url=<db_url> -Dflyway.user=<db_user> -Dflyway.password=<user_password>```
 - Restart mymedialib service

