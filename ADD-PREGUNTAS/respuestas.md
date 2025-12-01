## Metodología Attribute-Driven Design (ADD): Principios y Práctica

### 1. ¿Cuál es el propósito principal de la metodología ADD en el diseño de arquitecturas de software?

El propósito principal de ADD es diseñar una arquitectura de software de manera sistemática y predecible, basándose primordialmente en los **requisitos de atributos de calidad** (como rendimiento, seguridad y modificabilidad) en lugar de solo en los requisitos funcionales. Busca reducir el riesgo del proyecto asegurando que las decisiones arquitectónicas tempranas soporten las necesidades críticas del negocio y las restricciones técnicas antes de comenzar la implementación detallada.

### 2. ¿Qué atributos de calidad se consideran en ADD y por qué son importantes en el proceso de diseño?

ADD considera cualquier atributo de calidad relevante para el sistema, comúnmente categorizados en estándares como ISO/IEC 25010. Los más frecuentes incluyen:
* **Rendimiento:** Tiempos de respuesta y throughput.
* **Disponibilidad:** Uptime y recuperación ante fallos.
* **Seguridad:** Confidencialidad e integridad de datos.
* **Modificabilidad:** Facilidad de cambio y mantenimiento.
* **Usabilidad:** Facilidad de uso para el usuario final.

Son importantes porque actúan como los **"Drivers Arquitectónicos"**. Mientras que la funcionalidad dicta *qué* hace el sistema, los atributos de calidad dictan *cómo* debe estar estructurado para hacerlo bien. Una arquitectura incorrecta puede cumplir la función, pero fallar estrepitosamente bajo carga o ser imposible de mantener.

### 3. Explica la importancia de la selección del módulo a diseñar en ADD. ¿Cuáles son los criterios principales para elegir un módulo?

ADD es un proceso recursivo e iterativo; no se diseña todo el sistema de una sola vez. La selección del módulo define el alcance de cada iteración (descomposición).

**Criterios principales para la elección:**
* **Nivel de abstracción:** Generalmente se comienza por el sistema completo y luego se baja a subsistemas.
* **Riesgo y dificultad:** Se priorizan los componentes más difíciles o riesgosos para mitigar problemas temprano.
* **Dependencias de personal:** Se pueden elegir módulos para desbloquear el trabajo de equipos específicos.

### 4. ¿Cómo influyen las tácticas arquitectónicas en la toma de decisiones dentro de ADD? Menciona dos ejemplos de tácticas y su aplicación.

Las tácticas arquitectónicas son las herramientas fundamentales o "primitivas" de diseño que un arquitecto selecciona para satisfacer un atributo de calidad específico. En ADD, una vez identificado un driver (ej. alta disponibilidad), se elige una táctica probada para resolverlo.

**Ejemplos:**
1.  **Táctica de Ping/Echo (para Disponibilidad):** Un componente envía una señal periódica a otro para confirmar que está vivo. *Aplicación:* Implementar health-checks en microservicios para que el orquestador reinicie instancias caídas.
2.  **Táctica de Introducir un Intermediario (para Modificabilidad):** Romper la dependencia directa entre dos componentes. *Aplicación:* Usar un patrón Repository para desacoplar la lógica de negocio de la base de datos específica.

### 5. ¿Cuál es la relación entre los escenarios de calidad y la evaluación de la arquitectura en ADD?

La relación es de **Criterio vs. Validación**. Los escenarios de calidad (descripciones concretas de cómo debe comportarse el sistema ante un estímulo) definen la meta. La evaluación consiste en verificar si las tácticas y estructuras elegidas permiten satisfacer esos escenarios. Sin escenarios concretos, la evaluación sería subjetiva; con ellos, se puede determinar objetivamente si el diseño es apto (ej. "¿Nuestra táctica de caché permite responder en <200ms como pide el escenario?").

### 6. Describe los principales pasos del proceso ADD y cómo se interrelacionan.

El flujo es cíclico:
1.  **Revisar Inputs:** Confirmar requisitos y restricciones.
2.  **Elegir Elemento:** Seleccionar qué parte del sistema diseñar.
3.  **Identificar Drivers:** Determinar qué atributos de calidad son críticos para *ese* elemento.
4.  **Elegir Conceptos de Diseño:** Seleccionar patrones y tácticas arquitectónicas para satisfacer los drivers.
5.  **Instanciar y Definir Interfaces:** Asignar responsabilidades a los componentes resultantes y definir sus APIs.
6.  **Dibujar y Documentar:** Crear vistas arquitectónicas.
7.  **Analizar:** Verificar si lo diseñado cumple con los drivers. Si quedan elementos por descomponer, se repite el ciclo (Paso 2).

### 7. ¿Por qué es crucial documentar las decisiones arquitectónicas en ADD? Menciona al menos tres elementos que deben incluirse en la documentación.

La documentación es la memoria del proyecto; evita que el equipo olvide el "por qué" de las decisiones, previniendo cambios futuros que violen la integridad arquitectónica.

**Elementos esenciales:**
1.  **La Decisión:** Qué tecnología, patrón o estructura se eligió.
2.  **El Racional (Rationale):** La justificación de por qué se eligió eso sobre otras opciones (basado en los atributos de calidad).
3.  **Los Trade-offs:** Qué se sacrificó al tomar esa decisión (ej. "Ganamos rendimiento, pero aumentamos la complejidad de despliegue").

### 8. En un proyecto real, ¿cómo se puede evaluar si una arquitectura diseñada con ADD cumple con los atributos de calidad esperados?

La evaluación se realiza en dos fases:
* **Fase de Diseño (Temprana):** Mediante métodos como **ATAM (Architecture Tradeoff Analysis Method)**, donde stakeholders y arquitectos revisan mentalmente o en pizarra si el diseño soporta los escenarios.
* **Fase de Implementación (Continua):**
    * **Pruebas de Carga/Estrés:** (JMeter, K6) para validar rendimiento.
    * **Fitness Functions:** Tests automatizados (ArchUnit) que fallan si se violan reglas de dependencia.
    * **Chaos Engineering:** Provocar fallos reales para validar la disponibilidad y recuperación.

### 9. ¿Cuáles son los beneficios de utilizar ADD en comparación con otros enfoques de diseño arquitectónico?

* **Trazabilidad:** Existe una línea clara entre las metas de negocio y las decisiones técnicas.
* **Reducción de Riesgos:** Ataca los problemas más difíciles (calidad) al principio, no al final.
* **Estandarización:** Provee un método repetible, evitando que la arquitectura dependa únicamente de la "intuición" o experiencia de un solo individuo.
* **Eficiencia:** Evita el sobrediseño (Gold-plating) al enfocarse solo en los atributos que el negocio realmente prioriza.

### 10. ¿Qué desafíos pueden surgir al aplicar ADD en un equipo de desarrollo y cómo podrían abordarse?

**Desafíos:**
* **Parálisis por Análisis:** Querer definir todo antes de codificar.
* **Curva de Aprendizaje:** Requiere que el equipo entienda conceptos de atributos de calidad y tácticas.
* **Falta de Requisitos Claros:** Stakeholders que no saben definir escenarios de calidad (solo dicen "que sea rápido").

**Cómo abordarlos:**
* **Time-boxing:** Limitar el tiempo de diseño para forzar decisiones y avanzar a prototipos.
* **Talleres de Atributos de Calidad:** El arquitecto debe facilitar sesiones para ayudar al negocio a concretar sus deseos en escenarios medibles.
* **Diseño Evolutivo:** Aceptar que ADD se puede aplicar iterativamente a medida que el sistema crece, no solo al inicio.