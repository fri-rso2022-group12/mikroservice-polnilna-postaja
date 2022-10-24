run the following commands: 
    
    docker build -t charging-station .
    
    docker run -d -p 8080:8080 charging-station

check http://localhost:8080/swagger-ui.html