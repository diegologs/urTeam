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
