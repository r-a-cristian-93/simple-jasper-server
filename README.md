* Run app
`mvn clean spring-boot:run`

* Go to
https://localhost:8080/item-report/pdf
https://localhost:8080/item-report/xml

# Deploy
* Package in a single jar file
`mvn clean package`

TO DO:
- Update jasperreport to 7.0.1
- Load .jrxml on the fly after packing without restarting the application


