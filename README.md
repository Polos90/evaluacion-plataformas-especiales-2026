# Evaluación Plataformas Especiales 2026

Implementación de la evaluación con:

- Java 21
- Spring Boot
- API #1
- API #2
- OpenFeign
- Spring Data JPA / JpaRepository
- H2 in-memory
- Bean Validation
- RestControllerAdvice
- AES-256-GCM
- BCrypt
- PATCH
- Pagination + sorting
- Vue 3 + Vite

## Arquitectura

Vue 3
  |
  | POST /api/operaciones
  v
API #1 :8081
  |
  | OpenFeign
  v
API #2 :8082
  |
  v
H2 + JPA

## 1. Ejecutar API #2

cd api-2
mvn spring-boot:run

Puerto: 8082

H2:
http://localhost:8082/h2-console

JDBC URL:
jdbc:h2:mem:evaluaciondb

Usuario:
sa

Password:
(vacío)

## 2. Ejecutar API #1

cd api-1
mvn spring-boot:run

Puerto: 8081

## 3. Ejecutar frontend

cd frontend
npm install
npm run dev

Abrir:
http://localhost:5173

Usuario de prueba:
admin

Password:
Admin123

## 4. Flujo de operación

El frontend recibe el secreto en texto.

1. Frontend cifra secreto con AES-256-GCM.
2. Frontend manda el secreto cifrado a API #1.
3. API #1 valida operación, importe y cliente.
4. API #1 descifra secreto.
5. API #1 manda los datos a API #2 mediante OpenFeign.
6. API #2 genera referencia numérica de 6 dígitos.
7. API #2 guarda estatus Aprobada.
8. API #2 devuelve id, estatus, referencia y operación.
9. API #1 devuelve la respuesta al frontend.

## 5. PATCH

Ejemplo:

PATCH http://localhost:8082/api/transacciones/1

Body:

{
  "estatus": "cancelar"
}

La respuesta queda con:

"estatus": "Cancelada"

## 6. Paginación

Ejemplo:

GET http://localhost:8082/api/transacciones?page=0&size=10&sortBy=id&direction=asc

Otros campos:

sortBy=importe
sortBy=cliente
sortBy=estatus
sortBy=operacion

## Nota de seguridad

Para la evaluación se deja la clave AES en .env y application.properties para facilitar la ejecución.
