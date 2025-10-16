# Autor: Adrian Hernández
#### https://www.linkedin.com/in/adri%C3%A1n-hern%C3%A1ndez-791747205/
##### ------------------------------------------------------------------------------------------------------
# Prueba técnica de automatización de API REST:
##### ------------------------------------------------------------------------------------------------------

#### URL BASE a consumir: https://api-sandbox.co.uat.wompi.dev/v1
#### Funciones automatizadas: GET, POST
#### Patron de diseño: Screenplay
#### Framework: gherkin, cucumber, Junit, Serenity-Rest, API-REST
#### Framework adicionales: cucumber for java
#### Lenguaje programacion: Java
#### version gradle: gradle-8.4
#### OPEN_JDK Version 17.0.9
#### ID: Intellij IDEA community Edition 2025.1
##### ------------------------------------------------------------------------------------------------------

### Todos los casos pruebas cumplen con cada criterio solicitado
### Todas las clases estan con auto-ident lines y reformat code
##### ------------------------------------------------------------------------------------------------------
# PASOS PARA LA EJECUCIÓN

## Opcion 1
### PASO A PASO PARA LA EJECUCIÓN POR CONSOLA

### Comando para limpiar el proyecto y luego ejecuta TODOS los escenarios uno por uno
#### ./gradlew clean test

### Comando para ejecutar por feature
#### ./gradlew test --tests "co.com.screenplay.project.runners.NequiTransaccionesRunner"
#### ./gradlew test --tests "co.com.screenplay.project.runners.TarjetaTransaccionesRunner"
#### ./gradlew test --tests "co.com.screenplay.project.runners" --- TODO LOS FEATURES

### Comando para generar reporte despues que haya terminado un test (Por consola arrojara la ruta .html )
#### ./gradlew reports

### Comando para limpiar la carpeta Target (Reportes)
#### ./gradlew clearReports