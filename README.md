* Run app
`mvn clean spring-boot:run`

* Go to
http://localhost:8080/reports/status_check?fileFormat=pdf&client_message=Hello
http://localhost:8080/reports/pokemon?fileFormat=pdf

# Deploy
* Package in a single jar file
`mvn clean package`

TO DO:
- Update jasperreport to 7.0.1
- Load .jrxml on the fly after packing without restarting the application


