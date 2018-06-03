Feel free to complete this test task, override or change everything

For start app: 
1. Upadte tmdb.apikey in src\main\resources\application.properties
2. Run mvn clean install
3. Run mvn spring-boot:run

### Register user
curl -X POST --data "email=test@test2.com&password=test" http://localhost:8080/user/register/ -v

### View popular movies
curl -X POST --data "email=test@test2.com&password=test" http://localhost:8080/movie/popular/ -v