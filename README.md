Feel free to complete this test task, override or change everything

For start app: 
1. Upadte tmdb.apikey in src\main\resources\application.properties
2. Run mvn clean install
3. Run mvn spring-boot:run

### Register user
curl -X POST --data "email=test@test2.com&password=test" http://localhost:8080/user/register/ -v

### View popular movies
curl -X GET --data "email=test@test2.com&password=test" http://localhost:8080/movie/popular/ -v

### Add new favorite actor
### favoritActorId - code of actor in tmdb service
curl -X POST --data "email=test@test2.com&password=test&favoritActorId=287" http://localhost:8080/actor/addfavorit/ -v

### Remove relation between user and actor
### favoritActorId - code of an actor in tmdb service
curl -X POST --data "email=test@test2.com&password=test&favoritActorId=287" http://localhost:8080/actor/removefavorit/ -v

### Mark movie as watched for specific user
### movieId - code of a movie in tmdb service
curl -X POST --data "email=test@test2.com&password=test&movieId=4476" http://localhost:8080/movie/makrWatched/ -v

### Add movie to database by code
### movieId - code of a movie in tmdb service
curl -X POST --data "email=test@test2.com&password=test&movieId=63" http://localhost:8080/movie/add/ -v

### Search unviewe movies by user favorite actors and specific month and year 
curl -X GET --data "email=test@test2.com&password=test&month=12&year=1995" http://localhost:8080/movie/searchUnviewed/ -v