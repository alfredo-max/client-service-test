# Client REST Service

Servicio REST para consultar información básica de clientes según tipo y número de documento.

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior
- Spring Boot 3.5.4

## Estructura del Proyecto

El proyecto sigue una estructura estándar de Spring Boot con Maven:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── test/
│   │           └── client/
│   │               └── service/
│   │                   ├── controller/  # Controladores REST
│   │                   ├── exception/   # Excepciones personalizadas
│   │                   ├── model/       # Modelos de datos
│   │                   ├── service/     # Servicios de negocio
│   │                   └── Application.java  # Clase principal
│   └── resources/
│       └── application.properties  # Configuración de la aplicación
└── test/
    └── java/
        └── com/
            └── test/
                └── client/
                    └── service/
                        ├── controller/  # Pruebas de controladores
                        └── service/     # Pruebas de servicios
```

## Compilación

Para compilar el proyecto y generar el archivo JAR:

```bash
mvn clean package
```

El archivo JAR se generará en el directorio `target/`.

## Ejecución

Para ejecutar la aplicación:

```bash
java -jar target/client.service-0.0.1-SNAPSHOT.jar
```

La aplicación se iniciará en el puerto 8090.

## Uso de la API

### Consultar información de un cliente

**Endpoint**: GET /api/clients/info

**Parámetros de consulta**:
- `documentType`: Tipo de documento (obligatorio). Valores válidos: C (cédula) o P (pasaporte)
- `documentNumber`: Número de documento (obligatorio)

**Ejemplo de solicitud**:
```
GET http://localhost:8090/api/clients/info?documentType=C&documentNumber=23445322
```

**Respuesta exitosa** (Código 200):
```json
{
  "firstName": "Juan",
  "secondName": "Carlos",
  "firstLastName": "Rodríguez",
  "secondLastName": "Gómez",
  "phoneNumber": "3102224455",
  "address": "Calle 123 # 45-67",
  "city": "Bogotá"
}
```

## Códigos de estado HTTP

- `200 OK`: La solicitud se procesó correctamente
- `400 Bad Request`: Parámetros inválidos o faltantes
- `404 Not Found`: Cliente no encontrado
- `500 Internal Server Error`: Error interno del servidor

## Notas

- Según los requerimientos, solo existe información para el cliente con cédula número 23445322
- Los demás datos de clientes son simulados
