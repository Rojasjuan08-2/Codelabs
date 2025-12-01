## Attribute-Driven Design (ADD) y Clean Architecture

### 1. ¿Qué es Attribute-Driven Design (ADD) y cuál es su propósito en el diseño de software?

ADD es una metodología sistemática de diseño arquitectónico que prioriza los atributos de calidad (como rendimiento, seguridad, modificabilidad) sobre la funcionalidad pura para derivar la estructura del sistema. Su propósito es reducir los riesgos del proyecto diseñando una arquitectura que satisfaga demostrablemente los requisitos no funcionales críticos antes de comenzar la construcción detallada.

### 2. ¿Cómo se relaciona ADD con Clean Architecture en el proceso de diseño de sistemas?

La relación es de proceso frente a estructura (Macro vs. Micro). ADD es el **proceso** de toma de decisiones que identifica qué atributos son prioritarios. Si ADD determina que la "mantenibilidad" y la "testabilidad" son críticas, **Clean Architecture** es el **patrón estructural** (o táctica) que se selecciona para satisfacer esas necesidades, organizando los componentes internos para cumplir con dichos objetivos.

### 3. ¿Cuáles son los pasos principales del método ADD para definir una arquitectura de software?

1.  **Confirmar requisitos:** Verificar que existen metas de negocio y requerimientos funcionales claros.
2.  **Seleccionar un elemento del sistema:** Decidir qué parte de la arquitectura se diseñará en esta iteración.
3.  **Identificar los drivers arquitectónicos:** Seleccionar los atributos de calidad más críticos para ese elemento.
4.  **Elegir conceptos de diseño:** Seleccionar patrones (como Clean Architecture) y tácticas para satisfacer los drivers.
5.  **Instanciar elementos y definir interfaces:** Asignar responsabilidades y definir cómo se comunican los componentes.
6.  **Dibujar vistas y verificar:** Documentar la estructura y verificar si cumple los requisitos.
7.  **Repetir:** Iterar el proceso para el siguiente nivel de granularidad.

### 4. ¿Cómo se identifican los atributos de calidad en ADD y por qué son importantes?

Se identifican formalizando las necesidades vagas mediante **Escenarios de Calidad** (compuestos por fuente, estímulo, artefacto, entorno, respuesta y medida). Son importantes porque actúan como los "drivers" principales; sin ellos, la arquitectura se basaría en suposiciones, lo que a menudo resulta en sistemas funcionalmente correctos pero inoperables bajo condiciones reales de carga o cambio.

### 5. ¿Por qué Clean Architecture complementa ADD en la implementación de una solución?

Clean Architecture actúa como una táctica de implementación robusta. Cuando el proceso ADD identifica riesgos relacionados con el acoplamiento tecnológico o la dificultad de pruebas, Clean Architecture provee la solución prescriptiva mediante la separación de preocupaciones y la inversión de dependencias, garantizando que la lógica de negocio permanezca protegida y el sistema cumpla con la calidad esperada.

### 6. ¿Qué criterios se deben considerar al definir las capas en Clean Architecture dentro de un proceso ADD?

-   **La Regla de Dependencia:** El código fuente solo puede apuntar hacia adentro; las capas internas no conocen nada de las externas.
-   **Nivel de Abstracción:** Las capas internas (Entidades, Casos de Uso) deben contener normas de negocio abstractas, mientras que las externas (UI, BD, Web) contienen detalles de implementación concretos.
-   **Adaptadores de Interfaz:** Definir mecanismos claros (como DTOs y Mappers) para cruzar los límites de las capas sin propagar dependencias.

### 7. ¿Cómo ADD ayuda a tomar decisiones arquitectónicas basadas en necesidades del negocio?

ADD obliga a traducir las **Metas de Negocio** (ej. "Time-to-market rápido") en **Atributos de Calidad** (ej. "Modificabilidad") y estos a su vez en **Tácticas Arquitectónicas** (ej. "Arquitectura de Plugins" o "Capas"). Esto asegura una trazabilidad directa donde cada decisión técnica (como usar Clean Architecture) tiene una justificación económica y estratégica clara.

### 8. ¿Cuáles son los beneficios de combinar ADD con Clean Architecture en un sistema basado en microservicios?

-   **Definición de Límites (ADD):** ADD ayuda a determinar la granularidad y fronteras de los servicios basándose en requisitos de rendimiento y disponibilidad.
-   **Robustez Interna (Clean Architecture):** Asegura que cada microservicio sea independiente tecnológicamente, fácil de probar y refactorizar internamente sin afectar a la red global.
-   **Cognitive Load:** Estandariza la estructura interna de los servicios, facilitando que los desarrolladores cambien entre equipos o servicios con menor curva de aprendizaje.

### 9. ¿Cómo se asegura que la arquitectura resultante cumpla con los atributos de calidad definidos en ADD?

Se asegura mediante validación proactiva y reactiva:
-   **Análisis de Trade-offs:** Evaluar conscientemente qué calidades se sacrifican para obtener otras.
-   **Evaluación ATAM:** Realizar sesiones de revisión donde se camina por la arquitectura con los escenarios de calidad para detectar riesgos.
-   **Fitness Functions:** Implementar pruebas automatizadas de arquitectura que fallen si se violan reglas estructurales críticas (ej. tiempo de respuesta o dirección de dependencias).

### 10. ¿Qué herramientas o metodologías pueden ayudar a validar una arquitectura diseñada con ADD y Clean Architecture?

-   **ArchUnit / NetArchTest:** Herramientas de "Unit Testing para Arquitectura" que validan en código que se respete la Regla de Dependencia de Clean Architecture.
-   **ATAM (Architecture Tradeoff Analysis Method):** Metodología formal de evaluación cualitativa.
-   **Modelo C4:** Para documentar y visualizar los diferentes niveles de abstracción y asegurar que el diseño se comunique correctamente.
-   **Pruebas de Carga (JMeter/K6):** Para validar cuantitativamente los atributos de rendimiento definidos en la fase ADD.