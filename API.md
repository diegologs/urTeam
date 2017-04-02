# urTeam - DOCUMENTACIÓN API

## Eventos

* Público

Método | URL | Descripcion | Respuesta | Error
--- | --- | --- | --- | --- |
GET | /api/events/ | Devuelve los eventos. | `200 OK` |
GET | /api/events/{id} | Devuelve el evento específico. |  `200 OK` | `404 NOT FOUND`
GET | /api/events/{id}/members | Devuelve los miembros de ese evento. | `200 OK`| `404 NOT FOUND`

* Privado (El usuario debe estar logueado para poder realizar el método)

Método | URL | Descripcion | Respuesta | Error
--- | --- | --- | --- | --- |
POST | /api/events/ | Crea el evento con los datos del body. | `200 OK`| `400 BAD REQUEST`
PUT | /api/events/{id} | Modifica el evento específico. | `200 OK` | `404 NOT FOUND 401 UNAUTHORIZED`
PUT | /api/events/{id}/members | El usuario logueado sigue o deja de seguir al evento específico | `200 OK` | `404 NOT FOUND`
DELETE | /api/events/{id} | El evento específico es borrado | `204 NO CONTENT` | `401 UNAUTHORIZED`

Nota: El método DELETE es de uso exclusivo para el role admin.

#### Ejemplos de peticiones

**POST /api/events/**

Body:
```json
        {
          "name": "Evento",
          "sport": "Mountain Bike",
          "price": 1,
          "place": "Universidad Rey Juan Carlos I",
          "start_date": "2017-04-30",
          "end_date": "2017-11-02"
        }
```
**PUT /api/events/{id}**

Body:
```json
        {
          "name": "Nuevo_Evento",
          "sport": "Mountain Bike",
          "price": 2,
          "place": "Universidad Rey Juan Carlos I",
          "start_date": "2017-04-31",
          "end_date": "2017-11-03"
        }
```

## Comunidades

* Privado (El usuario debe estar logueado para poder realizar el método)

Método | URL | Descripcion | Respuesta | Error
--- | --- | --- | --- | --- | 
GET | /api/groups/ | Devuelve los grupos. | `200 OK` |
GET | /api/groups/{id} | Devuelve el grupo específico. |  `200 OK` | `404 NOT FOUND`
POST | /api/groups/ | Crea el grupo con los datos del body. | `200 OK`| `400 BAD REQUEST`
PUT | /api/groups/{id} | Modifica el evento específico. | `200 OK` | `404 NOT FOUND 401 UNAUTHORIZED`
POST | /api/groups/{id}/news | Añade una noticia a un grupo  | `200 OK` | `404 NOT FOUND 401 UNAUTHORIZED`
GET | /api/groups/{id}/news | Devuelve las noticias de un grupo. |  `200 OK` | `404 NOT FOUND`
PUT | /api/groups/{id}/avatar | Modifica el avatar de un grupo | `200 OK` | `404 NOT FOUND 401 UNAUTHORIZED`
PUT | /api/groups/{id}/followers | El usuario logueado sigue o deja de seguir a la comunidad específica | `200 OK` | `404 NOT FOUND`
DELETE | /api/groups/{id} | El grupo específico es borrado | `204 NO CONTENT` | `401 UNAUTHORIZED`

Nota: El método DELETE es de uso exclusivo para el role admin.

#### Ejemplos de peticiones
    
**POST /api/groups/**

Body:
```json
        {
              "name": "ExampleName",
              "city": "Madrid",
              "sport": "Mountain Bike",
              "info": "Info about community."   
        }
  ```

**PUT /api/groups/{id}**

Body:
```json
        {
              "name": "ExampleName",
              "city": "Madrid",
              "sport": "Mountain Bike",
              "info": "Info about community."   
        }
```
        
**POST /api/groups/news**

Body:
```json
        {
              "title": "Example news title",
              "text": "Info about news."   
        }
 ```
 
 ## Usuario
 
 * Privado (El usuario debe estar logueado para poder realizar el método)
 
 Método | URL | Descripcion | Respuesta | Error
--- | --- | --- | --- | --- |
GET | /api/users/ | Devuelve los usuarios. | `200 OK` | `404 NOT FOUND`
POST | /api/users/ | Crea un usuario nuevo. | `200 OK` | `400 BAD REQUEST`
GET | /api/users/{nickname} | Devuelve un usuario específico. | `200 OK` | `404 NOT FOUND`
PUT | /api/users/{nickname} | Modifica un usuario específico. | `200 OK` | `404 NOT FOUND`
GET | /api/users/{nickname}/friends | Devuelve los amigos de un usuario. | `200 OK` | `404 NOT FOUND`
GET | /api/users/{nickname}/followers | Devuelve los seguidores de un usuario. | `200 OK` | `404 NOT FOUND`

#### Ejemplos de peticiones
    
**POST /api/users/**

Body:
```json
        {
           "nickname": "ExampleName",
           "password": "ExamplePassword",  
        }
  ```
 **POST /api/users/{nickname}**

Body:
```json
        {
            "id": 2,
            "username": "Example",
            "surname": "Example",
            "nickname": "user1",
            "city": "city1",
            "score": "0",  
        }
  ```
  
## Estadísticas

 * Privado (El usuario debe estar logueado para poder realizar el método)
 
 Método | URL | Descripcion | Respuesta | Error
--- | --- | --- | --- | --- |
GET | /api/stats/{nickname} | Devuelve las estadsticas de un usuario. | `200 OK` | `404 NOT FOUND`
POST | /api/stats/{nickname}/{sportName} | Añade una nueva estadstica a un usuario. | `200 OK` | `404 NOT FOUND`

#### Ejemplos de peticiones
    
**POST /api/stats/{nickname}/{sportName}**

Body:
```json
    {
        "totalSesionTime": 4,
        "date": "2017-04-12"
    }
  ```

## Deportes

 * Privado (El usuario debe estar logueado para poder realizar el método)
 
 Método | URL | Descripcion | Respuesta | Error
--- | --- | --- | --- | --- |
GET | /api/sports/ | Devuelve los deportes. | `200 OK` | `404 NOT FOUND`
POST | /api/sports/ | Añade un deporte nuevo. | `200 OK` | `404 NOT FOUND`
GET | /api/sports/{sportName} | Devuelve los datos de un deporte. | `200 OK` | `404 NOT FOUND`
PUT | /api/sports/{sportName} | Modifica un deporte. | `200 OK` | `404 NOT FOUND`
DELETE | /api/sports/{sportName} | Elimina un deporte. | `200 OK` | `404 NOT FOUND`

#### Ejemplos de peticiones
    
**POST /api/sports/**

Body:
```json
    {
       "name": "ExampleName",
       "multiplicator": "ExamplePassword",  
    }
  ```
**PUT /api/sports/{sportName}**

Body:
```json
     {
       "id": 1,
       "name": "ExampleName",
       "multiplicator": 0.75
    }
  ```
