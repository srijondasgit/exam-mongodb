# exam-mongodb

to start mongo docker only - 
docker run -d -p 27017:27017 --name mymongodb mongo:latest

to start springboot and link to mongo - 
docker run -d --name springboot-mongodb -p 8080:8080 --link=mymongodb springboot-mongodb:latest

to start both springboot and mongodb, in directory with file docker-compose.yml -
docker-compose up

Build job - 
http://138.68.37.125:8080/