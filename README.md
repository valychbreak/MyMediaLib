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
 - Execute: ```mvn clean install -DskipTests```
 - Copy ```.../frontend/src/main/frontend/dist``` to ```.../backend/src/main/resources/static```
 - (Optional) Run: ```docker build -t valychbreak/mymedialib ./backend```
 
## Run

### Without docker
 - Install PostgresSQL locally or use remote DB (modify application.yml in backend project according to your db)
 - Setup new database according to application.yml in backend project
 - Run schema.sql and data.sql in backend project against created DB
 - Run: ```mvn spring-boot:run```
 
### With docker
 - Run: ```docker stack deploy -c backend/stack.yml mymedialib```
 - Goto http://localhost:3333/. Login with pgadmin4@pgadmin.org and admin
 - Create user "dbuser" with password "dbtest"
 - Create database "mymedialib"
 - Run schema.sql and data.sql in backend project against created DB
 - Restart mymedialib service

