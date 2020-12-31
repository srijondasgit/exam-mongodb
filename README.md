# exam-mongodb

to start mongo docker only - 
docker run -d -p 27017:27017 --name mymongodb mongo:latest

to start springboot and link to mongo - 
docker run --name springboot-mongodb -p 8080:8080 --link=mymongodb springboot-mongodb:latest

