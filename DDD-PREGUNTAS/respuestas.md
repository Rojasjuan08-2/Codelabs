## Domain-Driven Design (DDD): Conceptos y Aplicación Estratégica

### 1. ¿Qué es Domain-Driven Design (DDD) y cuál es su objetivo principal?

DDD es un enfoque de desarrollo de software que centra el diseño en el núcleo del dominio del negocio (la lógica y las reglas fundamentales) en lugar de en la tecnología o la infraestructura.
**Objetivo principal:** Afrontar la complejidad del software conectando la implementación técnica directamente con un modelo evolutivo de la realidad del negocio. Busca que el código sea un reflejo fiel del problema que resuelve, facilitando la colaboración entre expertos del dominio y desarrolladores.

### 2. ¿Cuál es la diferencia entre una Entidad y un Objeto de Valor en DDD?

La diferencia radica en la **Identidad** vs. el **Valor**:
* **Entidad (Entity):** Se define por una identidad única que perdura en el tiempo, independientemente de que sus atributos cambien. *Ejemplo: Un "Cliente" (su nombre puede cambiar, pero su ID lo mantiene como el mismo cliente).*
* **Objeto de Valor (Value Object):** Se define por sus atributos, no tiene identidad propia y es inmutable. Si cambias un atributo, creas un nuevo objeto. *Ejemplo: Una "Dirección" o un "Monto de Dinero". Si dos billetes tienen el mismo valor, son intercambiables.*

### 3. ¿Qué es un Bounded Context y por qué es importante en DDD?

Un **Bounded Context** (Contexto Delimitado) es una frontera semántica dentro de la cual un modelo de dominio específico tiene un significado claro y unívoco.
**Importancia:** En grandes sistemas, términos como "Producto" significan cosas diferentes para el equipo de "Ventas" (precio, marketing) y para el de "Inventario" (peso, ubicación). El Bounded Context protege al modelo de la ambigüedad, permitiendo que diferentes equipos desarrollen modelos especializados sin corromperse mutuamente. Es la base para dividir monolitos en microservicios.

### 4. ¿Cuál es el propósito del Lenguaje Ubicuo (Ubiquitous Language) en DDD?

Es un lenguaje común y riguroso compartido por desarrolladores y expertos del dominio.
**Propósito:** Eliminar la fricción de traducción. Si el experto de negocio habla de "Póliza Vencida", el código debe tener una clase `Poliza` con un método `verificarVencimiento()`, no una tabla `config_rules` con `status_id=5`. Esto asegura que el software haga exactamente lo que el negocio necesita y que las discusiones sean fluidas.

### 5. ¿Qué es un Agregado (Aggregate) y cómo garantiza la consistencia en el dominio?

Un **Agregado** es un clúster de objetos de dominio (Entidades y Value Objects) que se tratan como una unidad única para cambios de datos. Posee una **Raíz de Agregado** (Aggregate Root), que es la única entidad accesible desde el exterior.
**Garantía de consistencia:** El Agregado impone invariantes (reglas de negocio) dentro de sus límites transaccionales. Nadie puede modificar un objeto interno directamente; deben pasar por la Raíz, la cual valida la regla antes de aceptar el cambio.

### 6. ¿Cómo se relacionan los Repositorios en DDD con la infraestructura de persistencia?

En DDD, un **Repositorio** es una abstracción que emula una colección de objetos en memoria.
**Relación:** El Repositorio se define como una **Interfaz** en la capa de Dominio (pura, sin dependencias técnicas), pero su **Implementación** reside en la capa de Infraestructura. Esto permite desacoplar la lógica de negocio de la base de datos (SQL, NoSQL), cumpliendo con el principio de Inversión de Dependencias. El dominio "pide" guardar una entidad, y la infraestructura decide "cómo" (ORM, archivo, API).

### 7. ¿Qué son los Eventos de Dominio y cómo mejoran la comunicación entre Bounded Contexts?

Un **Evento de Dominio** es un registro de que algo significativo sucedió en el negocio (verbo en pasado, ej: `PedidoConfirmado`).
**Mejora de comunicación:** Permiten un acoplamiento débil. Un Contexto A publica un evento y un Contexto B lo escucha y reacciona, sin que A sepa que B existe. Esto es vital en arquitecturas distribuidas para mantener la consistencia eventual sin llamadas directas bloqueantes.

### 8. ¿Cómo se diferencian los Servicios de Aplicación y los Servicios de Dominio en DDD?

* **Servicio de Dominio:** Contiene lógica de negocio pura que no pertenece naturalmente a una Entidad o Value Object (ej. un cálculo complejo que involucra varias entidades). "El QUÉ del negocio".
* **Servicio de Aplicación:** Es un orquestador. No tiene reglas de negocio. Se encarga de recibir peticiones externas, gestionar transacciones, seguridad, cargar entidades desde repositorios, invocar al dominio y guardar cambios. "El CÓMO del flujo".

### 9. ¿Cómo DDD facilita el diseño de software en sistemas complejos y escalables?

DDD divide la complejidad en dos frentes:
1.  **Diseño Estratégico (Bounded Contexts):** Divide el problema grande en subdominios manejables, lo que facilita la escalabilidad organizacional (varios equipos trabajando en paralelo) y técnica (microservicios independientes).
2.  **Diseño Táctico (Patrones):** Provee herramientas (Agregados, VO, Entidades) para crear modelos ricos y robustos que soportan cambios frecuentes sin romperse, facilitando la mantenibilidad a largo plazo.

### 10. ¿Cómo se puede combinar DDD con Clean Architecture en una aplicación de microservicios?

Se combinan mapeando los conceptos de DDD en las capas concéntricas de Clean Architecture:
* **Enterprise Business Rules (Centro):** Aquí viven las Entidades, Value Objects, Agregados y Eventos de Dominio.
* **Application Business Rules (Capa siguiente):** Aquí residen los Servicios de Aplicación y los Manejadores de Comandos (Handlers).
* **Interface Adapters:** Aquí están las implementaciones de los Repositorios y Controladores.
  Esta combinación asegura que el microservicio tenga un núcleo de negocio protegido (DDD) y una estructura técnica desacoplada y testable (Clean Architecture).