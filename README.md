# spacecraft application


## build the image

To build the application please execute the following commanf line :
docker build -t spacecraft-app .

## run spacecraft application

to run the application please execute the following line :

docker run -p 8080:8080 spacecraft-app

## tech stack

JAVA 21
Springboot 3.3.0
Maven 3.9.4
H2
Flyway 10.0.0
Jacoco 0.8.21
guava (Cache) 31.1-jre 

## Jacoco reports

execute :
mvn jacoco:report
reports are available on :
target/site/jacoco/index.html

## Json examples to request

# to create a spacecraft :

curl -X POST http://localhost:8080/api/spacecrafts \
-u user:pass \
-H "Content-Type: application/json" \
-d '{
"name": "nameExample",
"model": "model1",
"manufactureDate": "2023-06-05",
"weight": 100
}'

# to get a spacecraft by id :

curl -X GET http://localhost:8080/api/spacecrafts/1 \
-u user:pass

# to get a paginated spacecraft list and/or filtering name% :

curl -X GET "http://localhost:8080/api/spacecrafts?page=0&size=10&name=na" \
-u user:pass

# update

curl -X PUT http://localhost:8080/api/spacecrafts/1 \
-u user:pass \
-H "Content-Type: application/json" \
-d '{
"name": "nameUpdated",
"model": "model",
"manufactureDate": "2023-06-05",
"weight": 100
}'

# delete 

curl -X DELETE http://localhost:8080/api/spacecrafts/1 \
-u user:pass

## Name
Spacecraft app

## Description
challenge for w2m


## Authors and acknowledgment
Christian Avalos
