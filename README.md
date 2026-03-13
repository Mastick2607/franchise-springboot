# Sistema de Gestión de Franquicias (API Reactiva) en Spring Boot


##  Descripción

Este proyecto es una solución robusta y escalable para la gestión de franquicias, sucursales y productos. Ha sido diseñado siguiendo estándares de industria, utilizando programación reactiva, estructura basada en Clean Architecture y contenedores para garantizar un alto rendimiento y portabilidad.

## Stack Tecnológico

- Java 17 con Spring Boot 4.0.3.

- Spring WebFlux: Implementación de programación reactiva no bloqueante.

- MongoDB: Persistencia NoSQL optimizada para flujos de datos reactivos.

- Docker & Docker Compose: Orquestación de infraestructura local.

- Lombok: Para un código más limpio y mantenible.


## Para ejecutar este proyecto, solo necesitas tener instalado:

- Docker Desktop (incluye Docker Compose).

- Un cliente para probar APIs (como Postman o Insomnia).

*Nota: No es necesario instalar Java, Maven o MongoDB localmente; Docker se encarga de todo el entorno.*

##  Otros Requisitos Previos:

1. Clonar el repositorio

```bash
  git clone https://github.com/Mastick2607/franchise-springboot.git
  
```

2. Despliegue con Docker, desde la terminal en la raíz del proyecto clonado, utiliza los siguientes comandos:

- 2.1 Construir y levantar el entorno completo:


```bash
 docker-compose up -d --build
```

- 2.1 Estos comandos son mas segundarios, los puedes utilizar después de probar todas las peticiones en Postman :
```bash
docker-compose stop
```
```bash
docker-compose start
```

⚠️ *Nota de "Calentamiento" del Sistema
*Es importante notar que, al usar docker-compose up o start, el contenedor de la base de datos y la aplicación Java inician simultáneamente. Aunque Docker reporte que el contenedor está "arriba", la Máquina Virtual de Java (JVM) y el contexto de Spring Boot tardan unos segundos adicionales en completar la conexión con MongoDB y habilitar el puerto 8080.*

*Si recibes un error de "socket hang up" o "connection refused" en tu primera petición, no te preocupes; el sistema se está estabilizando. Por favor, espera entre 10 y 15 segundos antes de reintentar. Esto garantiza que todos los servicios reactivos estén totalmente operativos.*

## Se cumplió con todos los criterios de aceptación y se implementaron los puntos plus:

Se han abordado todos los requerimientos obligatorios y los siguientes puntos extra:

- Docker: Aplicación completamente empaquetada con construcción multi-etapa.

- Programación Reactiva: Uso de Mono y Flux para un manejo de hilos eficiente.

- Persistencia Cloud-Ready: Configurada mediante volúmenes de Docker, lista para conectar a proveedores como MongoDB Atlas.

- Endpoints de Actualización: Implementación de cambios de nombre para Franquicias, Sucursales y Productos.

- Arquitectura Limpia: Separación de responsabilidades y uso de DTOs.

## Guía de Pruebas (Endpoints & JSON)

La API estará disponible en: http://localhost:8080



#  Endpoints

## Crear Franquicia:

*POST*  http://localhost:8080/api/franchises

```bash
{
    "name": "Franquicia de Café Quindío"
}

```

## Crear Sucursal:

*POST*  http://localhost:8080/api/franchises/{ID_DE_FRANQUICIA}/branches

```bash
{
    "name": "Sucursal Armenia Centro"
}

```

## Crear Producto:

*POST*  http://localhost:8080/api/franchises/{franchiseId}/branches/{branchId}/products

```bash
{
    "name": "Café Excelso 500g",
    "stock": 50
}

```

## Eliminar Producto:

*DELETE*  http://localhost:8080/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}

## Actualizar Stock del Producto:

*PATH*  http://localhost:8080/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock

```bash
{
    "stock": 150
}

```


## Ver el stock mas alto de un Producto por cada sucursal de una Franquicia especifica:

*GET*  http://localhost:8080/api/franchises/{franchiseId}/top-products



*Para que se vea la logica de negocio de esta petición debes agregar mas sucursales y en cada sucursal mas de 2  productos, luego
este seria el resultado (los id de cada sucursal y productos lo mas seguro es que varien
pero el resultado es el mismo):*

```bash
[
    {
        "branchName": "Sucursal Armenia Centro",
        "topProduct": {
            "id": "c548abff-4a35-4078-a4d8-5194d92f7653",
            "name": "Café Excelso 500g",
            "stock": 150
        }
    },
    {
        "branchName": "Sucursal Armenia Norte",
        "topProduct": {
            "id": "35c9fbd0-7271-4e01-bf18-adf8207476e9",
            "name": "Café Tradicional 500g",
            "stock": 120
        }
    },
    {
        "branchName": "Sucursal Armenia occidente",
        "topProduct": {
            "id": "828f6960-e6fd-4c74-a417-913b61e6628b",
            "name": "Café Excelso 1kg",
            "stock": 210
        }
    },
    {
        "branchName": "Sucursal Armenia sur",
        "topProduct": {
            "id": "2ef5a351-5c3c-41f7-a9a0-c75e2e687b3a",
            "name": "Termo Acero Inox",
            "stock": 85
        }
    }
] 

```
Procede a agregar las sucursales y los productos en cada una:
- primero crea mas sucursales:

*POST*  http://localhost:8080/api/franchises/{franchiseId}/branches

```bash
{
  "name": "Sucursal Armenia Norte"
}

```
luego otra vez pero con este json:

```bash
{
  "name": "Sucursal Armenia occidente"
}

```
luego otra vez pero con este json:

```bash
{
  "name": "Sucursal Armenia sur"
}

```
Después agrega 2 productos a cada sucursal:

*POST*  http://localhost:8080/api/franchises/{franchiseId}/branches/{branchId}/products


- Este es de la Sucursal Armenia Centro
```bash
{ "name": "Café plata 400g", "stock": 10 }

```
luego otra vez pero con este json:
```bash
{ "name": "Café gold 400g", "stock": 50 }

```

- Este es de la Sucursal Armenia Norte
```bash
{ "name": "Café Premium 250g", "stock": 50 }

```
luego otra vez pero con este json:
```bash
{ "name": "Café Tradicional 500g", "stock": 120 } //este el ganador de esta sucursal

```


- Este es de la Sucursal Armenia Sur
```bash
{ "name": "Termo Acero Inox", "stock": 85 } // este el ganador de esta sucursal

```
luego otra vez pero con este json:
```bash
{ "name": "Prensa Francesa", "stock": 40 }

```


- Este es de la Sucursal Armenia Occidente
```bash
{
  "name": "Café Excelso 1kg",
  "stock": 210
}  // este el ganador de esta sucursal

```
luego otra vez pero con este json:
```bash
{
  "name": "Kit de Barista",
  "stock": 45
}


```

## Actualizar nombre de franquicia:

*PATH*  http://localhost:8080/api/franchises/{id}/name


- Este es de la Sucursal Armenia Centro
```bash
{
    "name": "Franquicia Café Quindío - Global"
}

```

## Actualizar nombre de Sucursal:

*PATH*  http://localhost:8080/api/franchises/{franchiseId}/branches/{branchId}/name

- Este es de la Sucursal Armenia Centro
```bash
{
    "name": "Sucursal Armenia Norte - Portal del Quindío"
}

```

## Actualizar nombre de Producto:

*PATH*  http://localhost:8080/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name

- Este es de la Sucursal Armenia Centro
```bash
{
    "name": "Café Excelso - Edición Especial Exportación"
}

```

# Ver todas las franquicias disponibles con sus sucursales y productos anidados

*GET* http://localhost:8080/api/franchises


