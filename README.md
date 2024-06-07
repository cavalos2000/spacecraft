# spacecraft application


## build the image

To build the application please execute the following commanf line :
docker build -t spacecraft-app .

## run spacecraft application

default user and pass defined in Dockerfile are user/pass

To run the application please execute the following line :

docker run -e -p 8080:8080 spacecraft-app

or if you want your own user and password you could user arguments

docker run -e SERVICE_SECURITY_USER_USERNAME=user -e SERVICE_SECURITY_USER_PASSWORD=pass -e SERVICE_SECURITY_USER_ROLE=USER -p 8080:8080 spacecraft-app

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

## Name

Spacecraft app

## Description

challenge for w2m, with this application it is possible to create, update, delete, get by id and get paged list of spacecraft

Api endpoints are available on

http://localhost:8080/swagger-ui/index.html#/

the first step is authenticate by using /login endpoint, using the given user/password above, this endpoint returns a token, 
you should copy and paste into authorize section , and then you are able to use the logic endpoints called:

# Login

curl -X 'POST' \
'http://localhost:8080/login?username=user&password=pass' \
-H 'accept: */*' \
-d ''


# get a spacecraft by id :

curl -X 'GET' \
'http://localhost:8080/api/spacecrafts/1' \
-H 'accept: */*' \
-H 'Authorization: Bearer [your token]'

# update a spacecraft :

curl -X 'PUT' \
'http://localhost:8080/api/spacecrafts/1' \
-H 'accept: */*' \
-H 'Authorization: Bearer [ your token] \
-H 'Content-Type: application/json' \
-d '{
"name": "string",
"model": "string",
"manufactureDate": "2024-06-07",
"weight": 1
}'

# delete a spacecraft
curl -X 'DELETE' \
'http://localhost:8080/api/spacecrafts/1' \
-H 'accept: */*' \
-H 'Authorization: Bearer [your token]'

# get all spacecrafts

curl -X 'GET' \
'http://localhost:8080/api/spacecrafts?page=0&size=10&sort=name%2Casc' \
-H 'accept: */*' \
-H 'Authorization: Bearer [toker]'

# create spacecrafts
curl -X 'POST' \
'http://localhost:8080/api/spacecrafts' \
-H 'accept: */*' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzE3Nzg1OTY1LCJleHAiOjE3MTc3ODk1NjV9.Mg97wL0GdJDHxaz6L0ZNAxVDV-DJPHYmMmreN0K4l7s' \
-H 'Content-Type: application/json' \
-d '{
"name": "string2",
"model": "string",
"manufactureDate": "2024-06-07",
"weight": 1
}'

# get spacecrafts that contains a given name
curl -X 'GET' \
'http://localhost:8080/api/spacecrafts/byName?name=name' \
-H 'accept: */*' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzE3Nzg1OTY1LCJleHAiOjE3MTc3ODk1NjV9.Mg97wL0GdJDHxaz6L0ZNAxVDV-DJPHYmMmreN0K4l7s'

## Authors and acknowledgment
Christian Avalos
