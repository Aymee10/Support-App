# Support API 🚀

API para la gestión de solicitudes de soporte técnico. Permite a los usuarios crear, editar, eliminar y consultar solicitudes, asociadas a temas específicos.

 ## Tecnologías Utilizadas

Lenguaje: Java 21

Framework: Spring Boot 3.5.5

Base de datos: H2 (para desarrollo), MySQL (para producción)

ORM: Spring Data JPA

Documentación API: Springdoc OpenAPI (Swagger UI)

Validación: Jakarta Validation

Manejo de dependencias: Maven


## Diagrama de clases
<img width="3840" height="1786" alt="class diagram support api" src="https://github.com/user-attachments/assets/db58a220-c6eb-4367-925c-b9c399b62a81" />


## Diagrama Entidad-relacion
<img width="3289" height="3840" alt="diagrama entidad relacion support api" src="https://github.com/user-attachments/assets/980f6170-7478-4ca3-8bf7-caf087d91eaf" />

## Configuración y Ejecución
Clona el repositorio:
git clone https://github.com/Aymee10/Support-App.git

Compila y ejecuta la aplicación:
mvn clean install
java -jar target/support-api-0.0.1-SNAPSHOT.jar

## Documentación de la API (Swagger UI)
http://localhost:8081/swagger-ui.html

## Colección de Postman
Puedes descargar la colección de Postman para probar todos los endpoints aquí:
[Descargar Colección de Postman](./docs/postman/Support API.postman_collection.json)

