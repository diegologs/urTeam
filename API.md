# urTeam - DOCUMENTACIÓN API

## Eventos

* Público

Método | URL | Descripcion | Respuesta | Error
--- | --- | --- | --- | --- |
GET | /api/events/ | Devuelve los eventos. | `200 OK` |
GET | /api/events/{id} | Devuelve el evento específico. |  `200 OK` | `404 NOT FOUND`
GET | /api/events/{id}/members | Devuelve los miembros de ese evento. | `200 OK`| `404 NOT FOUND`

* Privado (El usuario debe estar logueado para poder realizar el método)

Método | URL | Body | Descripcion | Respuesta | Error
--- | --- | --- | --- | --- | --- |
POST | /api/events/ | {"name": "eventName","price": "3"...} | Crea el evento con los datos del body. | `200 OK`| `400 BAD REQUEST`
PUT | /api/events/{id} | {"name": "eventName","price": "3"...} | Modifica el evento específico. | `200 OK` | `404 NOT FOUND 401 UNAUTHORIZED`
PUT | /api/events/{id}/members |  | El usuario logueado sigue o deja de seguir al evento específico | `200 OK` | `404 NOT FOUND`
DELETE | /api/events/{id} |  | El evento específico es borrado | `204 NO CONTENT` | `401 UNAUTHORIZED`

Nota: El método DELETE es de uso exclusivo para el role admin.

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
    
**POST /api/groups**
Body:
```json
        {
              "name": "ExampleName",
              "city": "Madrid",
              "sport": "Mountain Bike",
              "info": "Info about community."   
        }
  ```

**PUT /api/groups/2**
Body:
```json
        {
              "name": "ExampleName",
              "city": "Madrid",
              "sport": "Mountain Bike",
              "info": "Info about community."   
        }
```
        
**POST /api/groups/2**
Body:
```json
        {
              "title": "Example news title",
              "text": "Info about news."   
        }
 ```