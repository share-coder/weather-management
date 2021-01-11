Project location:
=============================
Local: D:\Users\Ayush_A\repo\weather-management
Github: https://github.com/share-coder/weather-management.git

 
 Deploying as docker container
 ================================
 
 1) Create image <br/>
 1.1) Clone weather-management from GitHub location (https://github.com/share-coder/weather-management.git) into your machine <br/>
 <br/>
 1.2) Build docker image <br/>
 docker build -t weather:weather-v1 .
 <br/>
 <br/>
 Note: Make sure you have Dockerfile into your parent location of project.
 2) Check image with tag weather-v1 <br/>
 docker images
 
 3) Run weather-management image <br/>
 docker run -p 8080:8080 -d --name weather weather:weather-v1
 
    here -p is for port redirect <br/>
    here -d is for port detached ( to run container in background) <br/>
    here --name is for name container <br/>
 
 4) Check container status <br/>
 docker ps
 
 5) If container is running successfully then test using curl command. Refer Testing section.<br/>

 
 Testing:
 =============================
  curl -H 'content-type: application/json' 'http://localhost:8080/v1/weathers/forecast?city=Kanpur&forecastDays=3' -s



Design Diagram
====
src/design
