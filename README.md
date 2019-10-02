# lm_challenge

- He resuelto la prueba con un servicio en Spring Boot, Java 8, base de datos embebida H2 y Maven.

- He integrado Travis CI para la integración continua, la cual ejecuta los test cada vez que se mergea en git.

- La aplicación cuenta con las capas habituales en un proyecto (controller, service y repository) estando estas lo más 
desacopladas posibles. Este acoplamiento se reduce a la comunicación a través de una interfaz. Cada capa dispone de sus 
propios objetos de datos (DTOs) los cuales son transformados entre capas haciendo uso de la libreria MapStruct. 

- He seguido ATDD para el desarrollo, creando primeramente los test de aceptación que se indicaban en el enunciado como 
input y posteriomente llevando a cabo el desarrollo para cumplir ese test. Una vez que el test se ejecutaba con éxito 
procedia a refactorizar tanto el código como el test.

- He creado una serie de test de integración (los cuales prueban la integración de todas las capas de la aplicación) y
test unitarios para asegurar el funcionamiento de la aplicación. 



