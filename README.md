# covid-tracker

Pre-requisites:
- java 1.8
- mvn 3.x
- docker
- docker-compose

Deploy:
- cd xebia-assignment 
- mvn clean package -DskipTests=true
- cd scripts
- run ./deploy.sh

ToDo:
- Test cases needs to be implemented
- Need to move to CI/CD tools like CircleCI, Azure pipelines for deployment.