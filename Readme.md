run the following commands: 
    
    docker build -t charging-station .
    
    docker run -d -p 8080:8080 --env-file .env  charging-station

check http://localhost:8080/swagger-ui.html