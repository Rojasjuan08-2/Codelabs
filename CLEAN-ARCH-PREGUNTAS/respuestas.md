## Clean Architecture: Principios, Estructura e Implementación

### 1. ¿Qué problema busca resolver Clean Architecture en el desarrollo de software?

Clean Architecture busca resolver el problema del **acoplamiento fuerte** y la "deuda técnica" generada por mezclar la lógica de negocio con detalles de implementación (UI, Frameworks, Bases de Datos). Su objetivo es crear sistemas que sean:
* **Independientes de Frameworks:** El framework es una herramienta, no tu arquitectura.
* **Testables:** La lógica de negocio se puede probar sin UI, BD o servidor web.
* **Independientes de la UI y BD:** Puedes cambiar de Oracle a Mongo, o de Web a Consola, sin tocar las reglas de negocio.

### 2. ¿Cuáles son las principales capas de Clean Architecture y qué responsabilidad tiene cada una?

Se estructura en círculos concéntricos (de adentro hacia afuera):
1.  **Entidades (Enterprise Business Rules):** Objetos de negocio centrales con las reglas más generales y de alto nivel.
2.  **Casos de Uso (Application Business Rules):** Orquestadores que contienen la lógica específica de la aplicación. Dirigen el flujo de datos hacia y desde las entidades.
3.  **Adaptadores de Interfaz (Interface Adapters):** Convierten datos del formato conveniente para los casos de uso y entidades, al formato conveniente para la base de datos o la web (Controladores, Presenters, Gateways).
4.  **Frameworks y Drivers (Infrastructure):** La capa más externa. Aquí vive todo el "detalle": la BD, el Framework Web, dispositivos externos, UI.

### 3. ¿Qué relación tiene Clean Architecture con el principio de Inversión de Dependencias (DIP) en SOLID?

Es una relación fundamental. Clean Architecture es la **aplicación arquitectónica del DIP**.
La "Regla de Dependencia" establece que las dependencias de código fuente solo pueden apuntar hacia adentro. Sin embargo, el flujo de control a menudo necesita ir hacia afuera (ej. un Caso de Uso necesita guardar en la BD). Para resolver esto, se usa DIP: el Caso de Uso define una interfaz (puerto) y la capa de infraestructura implementa esa interfaz (adaptador). Así, el código fuente sigue apuntando hacia adentro (hacia la interfaz), aunque la ejecución vaya hacia afuera.

### 4. ¿Por qué es importante desacoplar la lógica de negocio de la infraestructura en una aplicación?

Porque la **tasa de cambio** es diferente. La infraestructura (frameworks, librerías, APIs externas) cambia y evoluciona rápidamente o se vuelve obsoleta. Las reglas de negocio (cómo calcular un impuesto, cómo aprobar un préstamo) son más estables.
Si están acopladas, un cambio trivial en la librería de base de datos podría romper la lógica de cálculo de impuestos. El desacoplamiento protege al activo más valioso (el negocio) de la volatilidad tecnológica.

### 5. ¿Cómo Clean Architecture facilita la escalabilidad y mantenibilidad de un sistema?

* **Mantenibilidad:** Al tener responsabilidades segregadas, el desarrollador sabe exactamente dónde buscar un error. Si el cálculo está mal, es en la Entidad; si el flujo está mal, es en el Caso de Uso; si la respuesta HTTP está mal, es en el Controlador.
* **Escalabilidad:** Al estar modularizado correctamente, es más fácil identificar qué casos de uso tienen mayor carga y, eventualmente, extraerlos a microservicios separados sin tener que desenredar "código espagueti".

### 6. ¿Qué diferencia hay entre la capa de dominio y la capa de aplicación en Clean Architecture?

* **Capa de Dominio (Entidades):** Contiene la lógica que es verdadera para el negocio independientemente de la aplicación informática. Si no hubiera software, estas reglas seguirían existiendo en papel. (Ej: "Un empleado no puede tener sueldo negativo").
* **Capa de Aplicación (Casos de Uso):** Contiene la lógica de *automatización*. Define qué hace el usuario con el sistema. (Ej: "Obtener empleado, validar sueldo, guardar en repositorio y enviar email de notificación").

### 7. ¿Por qué los controladores (controllers) y la base de datos deben estar en la capa de infraestructura?

Porque son **mecanismos de entrada/salida (I/O)**, no reglas de negocio.
* Un **Controlador** solo recibe una petición HTTP, la deserializa y llama al Caso de Uso. Si mañana cambias de REST a gRPC, el negocio no cambia, solo el controlador.
* La **Base de Datos** es un detalle de almacenamiento. El negocio necesita "guardar datos", no le importa si es en un archivo .txt o en PostgreSQL. Por eso residen en la periferia.

### 8. ¿Qué ventajas tiene usar una interfaz en la capa de dominio para definir repositorios en lugar de usar directamente JpaRepository?

Si usas `JpaRepository` directamente en tu dominio, estás importando una dependencia de `Spring Data` (un framework) en tu núcleo.
**Ventaja de la interfaz propia:**
Defines `UserRepository` (interfaz pura) en el Dominio. Luego, en Infraestructura, implementas esa interfaz usando `JpaRepository`. Esto mantiene tu dominio **agnóstico** a la persistencia. Si mañana quieres cambiar Hibernate por JDBC puro, solo cambias la implementación en la capa externa sin tocar ni una línea del dominio.

### 9. ¿Cómo interactúan los casos de uso (UseCases) con las entidades de dominio en Clean Architecture?

El Caso de Uso actúa como un **director de orquesta**:
1.  Recibe un input (DTO).
2.  Recupera las Entidades necesarias a través de un Repositorio (interfaz).
3.  Invoca métodos de negocio dentro de esas Entidades (donde ocurre la "magia" de las reglas).
4.  Persiste el nuevo estado de las Entidades usando el Repositorio.
5.  Devuelve un output al presentador/controlador.
    El Caso de Uso no *contiene* la lógica de la entidad, solo la invoca.

### 10. ¿Cómo se puede aplicar Clean Architecture en una aplicación de microservicios con Spring Boot?

Se aplica mediante una estricta **organización de paquetes o módulos (Maven/Gradle)**:
* **Module `:domain`:** POJOs puros, interfaces de repositorios. Sin dependencias de Spring.
* **Module `:application`:** Servicios/Casos de uso. Depende solo de `:domain`.
* **Module `:infrastructure`:** Controladores REST, Implementación de Repositorios (Spring Data JPA), Configuración de Beans. Depende de `:application` y `:domain`.
  Spring Boot (la clase `Application`) reside en Infraestructura y "pega" todo mediante Inyección de Dependencias, inyectando las implementaciones de infraestructura en las interfaces de dominio/aplicación.