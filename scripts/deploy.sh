cd ..
mvn clean package -DskipTests=true
docker build -t eswaraiahji/covid-tracker:latest .
docker push eswaraiahji/covid-tracker:latest

docker-compose -f docker-compose.yml up