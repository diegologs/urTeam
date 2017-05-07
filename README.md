# urTeam

* urTeam es una nueva aplicación web para organizar tus encuentros deportivos, estará orientada sobre todo para deportes en ruta de exterior (ciclismo, running, patines...).  
* La parte pública de la web constará de los eventos, los grupos y de la página personal de cada usuario.  
* La parte privada se compone del panel de control de cada usuario donde podrá modificar su perfil, añadir eventos y administrar sus grupos.

[![Video Demostracion](https://i.imgur.com/MlwydWz.jpg)](https://youtu.be/h2w2BToKEFI "urTeam : Video Demostracion")

__Entidades:__
* ___Usuarios:___ Personas que se registran en la aplicacion con el animo de asistir y crear eventos de su deporte favorito.
* ___Grupos:___ Los usuarios podran crear grupos o asociaciones las cuales creen e incentiven la participacion en eventos, estas podran ser privadas o publicadas.
* ___Deportes:___ Lista de deportes practicados por los usuarios de la aplicacion.
* ___Eventos:___ Los eventos los podran crear tanto los usuarios a nivel particular asi como los _admin_ de los grupos. Estos eventos podran ser privados o publicos y constaran de un titulo una descripcion asi como del horario en el que se realizaran.
* ___Estadisticas:___ Las estadisticas forman parte del usuario, y estan compuestas por la informacion de las sesiones de cada deporte del usuario.
* ___Noticias:___ Las noticias pueden ser creadas en las comunidades.

__Equipo de desarrollo:__
* Jose Miguel García Benayas
  * _email:_ jm.garciaben@alumnos.urjc.es
  * _GitHub:_ [JoseMiguel92](https://github.com/JoseMiguel92)
* Pablo García Benayas
  * _email:_ p.garciaben@alumnos.urjc.es
  * _GitHub:_ [pablogarciabenayas](https://github.com/pablogarciabenayas)
* Diego Lopez García
  * _email:_ d.lopezgarc@alumnos.urjc.es
  * _GitHub:_ [Frostqui](https://github.com/Frostqui)
* Daniel Oliver Gallego
  * _email:_ d.oliver@alumnos.urjc.es
  * _GitHub:_ [oly13](https://github.com/oly13)
  
__Fase 2:__  
 
_index.html_ : Muestra una información rápida de los eventos públicos disponibles ordenados por deporte.  

![index.html Photo](http://i.imgur.com/7vBDoQn.jpg)

_events.html_ : Contiene un listado de todos los eventos disponibles y públicos.  

![events.html Photo](http://i.imgur.com/QPsizLF.jpg)

_event.html_ : Muestra información detallada del evento.   

![event.html Photo](http://i.imgur.com/PcWoEmz.jpg)

_groups.html_ : Contiene un listado de todos los grupos disponibles y públicos.  

![groups.html Photo](http://i.imgur.com/XxINME2.jpg)  

_group.html_ : Muestra información detallada del grupo.   

![group.html Photo](http://i.imgur.com/cnedO5A.jpg)

_login.html_ : Permite el inicio de sesión y registro de usuarios.  

![login.html Photo](http://i.imgur.com/u9BQITg.jpg)
![login.html Photo](http://i.imgur.com/iBR1i12.jpg)

_user.html_ : Muestra la información del usuario.  

![user.html Photo](http://i.imgur.com/MX5rfb7.jpg)

_settings.html_ : Permite modificar tu perfil.  

![settings.html Photo](http://i.imgur.com/oA7sAX8.jpg)

_addGroup.html_ : Permite añadir un grupo nuevo a la página.  

![addGroup.html Photo](http://imgur.com/OggX35J.jpg)

_addEvent.html_ : Permite añadir un evento nuevo a la página.  

![addEvent.html Photo](http://i.imgur.com/caaP6rp.png)

_controlPanel.html_ : Permite modificar los datos de la web con permisos de administración.  

![controlPanel.html Photo](http://i.imgur.com/RsyGHxX.png)  

_Diagrama de navegación:_  

![Diagrama de navegación](http://i.imgur.com/rC46Qbn.png)  

__Fase 3:__ 

_Diagrama de clases y relaciones:_  

![Diagrama de clases y relaciones](https://github.com/Frostqui/urTeam/blob/master/web/screenshots/DAW.png)  

_Diagrama de la base de datos:_  

![Diagrama de la base de datos](https://github.com/Frostqui/urTeam/blob/master/web/screenshots/BBDD.jpg) 

__Fase 4:__ 

_Diagrama de clases y relaciones de Spring:_
![Diagrama de clases y relaciones](https://github.com/Frostqui/urTeam/blob/master/web/screenshots/fase4-diagramadeclases-servicioscontrollerrestcontroller-daw.png)

Este diagrama es complementario a los anteriores, añadiéndose las clases servicios, y restcontroller necesarios para la APIRest.

_Documentación de la API:_
[DOCUMENTACION API - API.md](https://github.com/Frostqui/urTeam/blob/master/API.md)

__Fase 5:__ 

_Diagrama de clases y relaciones de Angular:_
[Diagrama Angular](https://frostqui.github.io/urTeam/)

_Video demostracin de uso:_
[![Video Demostracion](https://i.imgur.com/MlwydWz.jpg)](https://youtu.be/h2w2BToKEFI "urTeam : Video Demostracion")

 __Plantillas__ 
 * Control Panel Template [control Panel template](https://www.creative-tim.com/product/light-bootstrap-dashboard)
 * Login Template [login template](http://bootsnipp.com/snippets/featured/login-and-register-tabbed-form)
 * Footer Template [footer template](http://bootsnipp.com/snippets/33WGq)
