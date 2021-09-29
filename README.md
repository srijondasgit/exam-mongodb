# exam-mongodb
# application to conduct exams
to start mongo docker only - 
docker run -d -p 27017:27017 --name mymongodb mongo:latest

to start springboot and link to mongo - 
docker run -d --name springboot-mongodb -p 8080:8080 --link=mymongodb srijondas/springboot-mongodb:latest

to start both springboot and mongodb, in directory with file docker-compose.yml -
docker-compose up

Build job - 
http://138.68.37.125:8080/

Docker image - 
https://hub.docker.com/r/srijondas/springboot-mongodb

If Mongodb container is already running and application is updated - 
docker run -d --network=<Mongodb containerid> --name production-exam-gitanjali1  -p 8080:8080 --link=<Mongodb Container id> <Docker image for production>
