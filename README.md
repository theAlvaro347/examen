# Introducción a Test/TDD/ATDD y Junit5

Ideas de:
- http://www.chuidiang.org/java/herramientas/test-automaticos/tdd-test-driven-development.php
- https://phauer.com/2019/modern-best-practices-testing-java/
- https://devs4j.com/2018/04/23/pruebas-unitarias-parte-2-junit-y-mockito-primeros-pasos/
- https://github.com/carherco/curso-unit-testing-phpunit (Carlos Herrera)<- Principal guía


**INDICE**    

1. [Pruebas Unitarias/Test](#test)
1. [TDD](#tdd)
1. [ATDD](#atdd)


## Pruebas Unitarias (Unit Tests) <a name="test"></a>
Fuente: [Wikipedia](https://es.wikipedia.org/wiki/Prueba_unitaria)  
En programación, una prueba unitaria es una *forma de comprobar el correcto funcionamiento de una unidad de código*. Por ejemplo en diseño estructurado o en diseño funcional una *función* o un *procedimiento*, en diseño orientado a objetos una *clase* y sus *método*. 

Objetivo del Test:
- Asegurar que cada unidad funcione correctamente y eficientemente por separado.(Caja negra)
- Verificar que el código hace lo que tiene que hacer (Caja blanca).
- Verificación semántica de nombres, 
- Los nombres y tipos de los parámetros, el tipo de lo que se devuelve
- Y si el estado inicial es válido, entonces el estado final es válido también.

La idea es escribir casos de prueba para cada función no trivial o método en el módulo, de forma que cada caso sea independiente del resto. Luego, con las Pruebas de Integración, se podrá asegurar el correcto funcionamiento del sistema o subsistema en cuestión.

### Frameworks de testeo <a name="fw"></a>
Permiten programar tests de manera muy sencilla.  
Cada lenguaje de programación tiene uno o más frameworks profesionales de testeo.  
Algunos de los más conocidos son: NUnit, xUnit, **JUnit**, PhpUnit, etc... 

Como aserciones podemos utilizar varios tipos en JUnit5, ej:
- `assertEquals(3000, c.getSaldo())`;
- `assertEquals(4, 4, "Ahora el mensaje opcional de la aserción es el último parámetro.")`;
- `assertTrue`, `assertAll`, etc... 

pero con `assertEquals` podemos avanzar esta introducción.


## TDD  (Test Driven Development)<a name="tdd"></a>
Es una práctica de ingeniería de software que involucra otras dos prácticas: 
- Escribir las pruebas primero (Test First Development) y 
- Refactorización (Refactoring). 
Fuente: [Wikipedia](https://es.wikipedia.org/wiki/Desarrollo_guiado_por_pruebas)

### Objetivos del TDD 
- Minimizar el número de errores/bugs. 
- Implementar las funcionalidades justas que el cliente necesita y no más. 
- Producir software modular, altamente reutilizable y preparado para el cambio. 

### Flujo de TDD
- Requisitos -> Pruebas -> Programación vs Requisitos->Analisis->Desarrollo->Pruebas g->Pruebas b

### Algoritmo TDD 
- Escribir un test que falle. 
- Escribir el mínimo código necesario para pasar los tests. 
- Refactorizar 
Este algoritmo también se conoce como algoritmo Red-Green-Refact

### Patron  **AAA** de toda Prueba 
- **Arrange** (set up). Preparación de los elementos sobre los que vamos a realizar el test
- **Act**. En esta parte se ejecuta la acción (o acciones) que se desea(n) poner a prueba
- **Assert**. En esta parte, se realiza la comprobación (o comprobaciones) pertinentes para verificar que la parte Act funciona como debe.

Lo puedes ver tambien como **GWT**, [Given, When, Then](https://phauer.com/2019/modern-best-practices-testing-java/), en el que una prueba debe contener tres bloques que estén separados por una línea vacía. Cada bloque de código debe ser lo más corto posible. Use subeventos para acortar estos bloques.

- **Given**(Input): La preparación de la prueba como la creación de datos o la configuración de las burlas
- **When**(Acción): Llama al método o acción que te gusta probar
- **Then** (Salida): Ejecutar aseveraciones para verificar la salida o el comportamiento correcto de la acción.

### Ejemplos
[Ejemplos TDD](https://github.com/junit-team/junit5-samples/blob/r5.6.1/junit5-jupiter-starter-maven/src/test/java/com/example/project/CalculatorTests.java) 


## ATDD  (Acceptance Test Driven Development) como Punto de partida para TDD<a name="atdd"></a>
- Son listados de ejemplos ejecutables 
- Escritos por los dueños del producto( ;)SCRUM) (o al menos validados por ellos) 
- Se centran en el qué y no en el cómo 
- Ejemplos concretos y certeros 
- Permiten comprobar muy rápido si el programa está cumpliendo los objetivos o no.

## Ejemplo ATDD<a name="eatdd"></a>
Enunciado:
> (...) Antes de proceder al pago, al cliente le saldrá un resumen detallado de los productos que tiene en la cesta,en los que se deberá aplicar el IVA (21%) o el RE (5.2%) al total, en caso de que el cliente requiera sus facturas con IVA o con RE.

Propuesta de especificación para "_mostrar los impuestos en el resumen de la factura_".   
[Especificación](https://es.wikipedia.org/wiki/Especificaci%C3%B3n_de_requisitos_de_software). 
¡¡¡Muy importante entender esto.!!!

Propuesta del programador:
Si un cliente con _IVA_ y con _RE_ elige un producto de 100€, entonces en el detalle de la factura se le mostrará:  
▸ Precio antes de impuestos: 100€  
▸ IVA: 21€  
▸ RE: 6.292€  
▸ Total: 127.29€ 

**Rectificación del cliente** (_en cursiva_) 
Si un cliente con IVA y con RE elige un producto de 100€, en el detalle de la factura se le mostrará:  
▸ _Base Imponible_: 100.00€  
▸ _I.V.A. (21%)_: 21.00€  
▸ _R.E. (5.2%)_: _5.20€_  
▸ Total: _126.20€_ 

**RESUMEN de Propuestas y Rectificaiones**  
**Programador (a la izda) y cliente (a la dcha).**

Cliente con IVA y con RE: | Cliente con IVA y con RE:
--|--
▸ Base Imponible: 100.00€ | ▸ Base Imponible: 100.00€ 
▸ I.V.A. (21%): 21.00€ | ▸ I.V.A. (21%): 21.00€ 
▸ R.E. (5.2%): 5.20€ | ▸ R.E. (5.2%): 5.20€ 
▸ Total: 126.20€ | ▸ Total: 126.20€ 


Cliente con IVA y sin RE: | Cliente con IVA y sin RE:
-- | --
▸ Base Imponible: 100.00€  | ▸ Base Imponible: 100.00€ 
▸ I.V.A. (21%): 21.00€  | ▸ I.V.A. (21%): 21.00€ 
▸ R.E. (0%): 0.00€  |   ▸ Total: 121.00€
▸ Total: 121.00€ | 


Cliente sin IVA y con RE:  | Cliente sin IVA y con RE: 
-- | --
▸ Base Imponible: 100.00€  | ▸ Base Imponible: 100.00€ 
▸ R.E. (5.2%): 5.20€  | ▸ I.V.A (0%): 0.00€ 
▸ Total: 105.20€ | ▸ R.E. (5.2%): 5.20€ 
▸    | ▸ Total: 105.20€


Cliente sin IVA y sin RE: | Cliente sin IVA y sin RE: 
-- | --
▸ Base Imponible: 100.00€  | ▸ Base Imponible: 100.00€ 
▸ Total: 100.00€  | ▸ I.V.A (0%): 0.00€ 
▸   | ▸ Total: 100.00€

**VB (Visto Bueno Cliente)**

Genial. Ya tenemos 4 tests de aceptación para la funcionalidad de "mostrar los impuestos en el resumen de la factura". 
Otras consideraciones:  
Otra ventaja de dirigir el desarrollo por los ejemplos, es que vamos a poder comprobar muy rápido si el programa está cumpliendo los objetivos o no. Conocemos en qué punto estamos y cómo vamos progresando. El Dueño de Producto puede revisar los tests de aceptación y ver cuántos se están cumpliendo, así que nuestro trabajo gana una confianza tremenda.

