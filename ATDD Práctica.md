
**INDICE**    

1. [Práctica ATDD](#epatdd)
1. [Ejemplo Test 1](#t1)
1. [Ejemplo Test 2](#t2)
1. [Ejemplo Test 3](#t3)
1. [Ejemplo Test 4](#t4)
1. [Ejemplo Test 5](#t5)
1. [Ejemplo Test 6,7,8,9](#t6)

**ANEXOS**
1. [Test Drive Bug Fixing](#tdbf)
1. [Test Drive Bug Fixing II](#tdbf2)
1. [Errores Comunes](#errores)
1. [Antipatrones](#anti)
1. [Pro y Contra](#procon)
1. [Niveles profesionales TDD](#pro)

 
## Práctica ATDD<a name="epatdd"></a>
Supongamos que un cliente nos contrata con este enunciado:
> "Quiero lanzar una aplicación monedero para el pago entre amigos. Cada usuario tendrá una cuenta con saldo. La idea es que se puedan hacer transferencias a tus amigos directamente desde la app. La aplicación permitirá al usuario ingresar dinero o retirarlo cuando quiera."

Aunque parece bastante claro, hay muchos detalles no especificados. No se puede aplicar TDD si no tenemos **ejemplos concretos y certeros**. Imaginémonos que tenemos la oportunidad de entrevistarnos con el cliente para detallar mejor la aplicación. Nuestra tarea es obtener un listado de **especificaciones ATDD**. Es decir, un listado de **ejemplos concretos y certeros, checkeable**, y que cubra todas las posibilidades para que no haya lugar a malinterpretaciones de ningún tipo sobre las funcionalidades.  
Evidentemente, los detalles los conoce el cliente, y no nosotros, así que para este ejercicio, nosotros mismos haremos también de cliente, dialogando con nosotros mismos hasta tener la lista de especificaciones ATDD completa.  

```diff
+ PRÁCTICA:
+ Dedicadle un buen rato a completar la lista antes de continuar.
! Aquí entra todo lo que hemos aprendido sobre cómo definir Clases en Java, métodos y propiedades.
```

### SOLUCIÓN
Esta lista, evidentemente no coincidirá con la vuestra porque el cliente no es el mismo que el vuestro, así que los requisitos de la aplicación del cliente no coinciden con los de la aplicación de vuestro cliente.
Pero el objetivo realmente es ver si somos capaces de llegar a elaborar un lista de ejemplos válidos para utilizar la técnica TDD.

#### The List I

_Una aplicación monedero para el pago entre amigos. Cada usuario tendrá una cuenta con saldo._
_La idea es que se puedan hacer transferencias a tus amigos directamente desde la app._
_La aplicación permitirá al usuario ingresar dinero o retirarlo cuando quiera._
Tras profundizar en cada funcionalidad con el cliente, resulta que tengo las siguientes especificaciones, mucho más detalladas:

**Creación de cuentas.**
- Las cuentas siempre se crean con saldo 0. Hay que hacer algún ingreso después si se quiere tener saldo

**Ingresos.**
- Suman la cantidad ingresada al saldo.
- No hay comisiones ni nada por el estilo.
- No se pueden hacer ingresos negativos
- Los ingresos admiten un máximo de 2 decimales de precisión
- La cantidad máxima que se puede ingresar es de 6000

**Retiradas.**
- Restan la cantidad ingresada al saldo.
- No se puede retirar una cantidad mayor a la del saldo disponible
- No hay comisiones ni nada por el estilo.
- No se pueden retirar cantidades negativas
- Las cantidades admiten un máximo de 2 decimales de precisión
- La cantidad máxima que se puede retirar es de 6000

**Transferencias**
- No se pueden transferir cantidades negativas
- El límite cantidad transferida es de 3000

Este es más o menos el nivel de detalle requerido en la metodología tradicional. Con esto ya se puede realizar un análisis y un diseño completos de la aplicación. Sin embargo el objetivo de ATDD es conseguir ejemplos concretos, para transformarlos posteriormente en tests concretos cuando apliquemos TDD.
Así que le damos todavía una vuelta más de tuerca para definir los ejemplos con los que validaremos las funcionalidades: (en negrita añado los ejemplos).
Antes es conveniente que se hayan ordenado por funcionalidad, agrupando todos los ejemplos de la misma funcionalidad. En nuestro caso, fuimos diligentes y organizados y los tenemos agrupados por funcionalidades.

#### The List II
**Creación de cuentas.**
- Las cuentas siempre se crean con saldo 0. Hay que hacer algún ingreso después si se quiere tener saldo:
  1. Al crear cuenta el saldo es cero
  
**Ingresos.**
- Suman la cantidad ingresada al saldo.
- No hay comisiones ni nada por el estilo.   
  2. Al ingresar 100 en cuenta vacía el saldo es 100  
  3. Al ingresar 3000 en cuenta vacía el saldo es 3000  
  4. Al ingresar 3000 en cuenta con 100 el saldo es 3100  
- No se pueden hacer ingresos negativos  
  5.  Al ingresar -100 en cuenta vacía, el saldo sigue siendo 0  
- Los ingresos admiten un máximo de 2 decimales de precisión  
  6. Si ingreso 100.45 en una cuenta vacía, el saldo es de 100.45  
  7. Si ingreso 100.457 en una cuenta vacía, el saldo es de 0  
- La cantidad máxima que se puede ingresar es de 6000  
  8. Si ingreso 6000.00 en una cuenta vacía, el saldo es de 6000.00  
  9. Si ingreso 6000.01 en una cuenta vacía, el saldo es de 0  
  
**Retiradas.** (no sigo numerando...)  
- Restan la cantidad ingresada al saldo.
- No hay comisiones ni nada por el estilo.
  - Al retirar 100 en cuenta con 500 el saldo es 400
- No se puede retirar una cantidad mayor a la del saldo disponible
  - Si retiro 500 en cuenta con 200 no ocurre nada y el saldo sigue siendo 200
- No se pueden retirar cantidades negativas
  - Si retiro -100 en cuenta con 500 no ocurre nada y el saldo sigue siendo 500
- Las cantidades admiten un máximo de 2 decimales de precisión
  - Al retirar 100.45 en cuenta con 500 el saldo es 399.55
  - Al retirar 100.457 en cuenta con 500 con 500 no ocurre nada y el saldo sigue siendo 500
- La cantidad máxima que se puede retirar es de 6000
  - Si retiro 6000.00 en una cuenta con 7000, el saldo es de 1000
  - Si retiro 6000.01 en una cuenta con 7000, no ocurre nada y el saldo sigue siendo 7000
  
**Transferencias**
- Al hacer una transferencia de 100 desde una cuenta con 500 a una con 50, en la primera cuenta el saldo se quedará en 400 y en la segunda se quedará en 150.
- No se pueden transferir cantidades negativas
  - Al hacer una transferencia de -100 desde una cuenta con 500 a una con 50, los saldos se quedan en 500 y 50 respectivamente
- El límite de transferencias en un mismo día desde una misma cuenta es de 3000:
  - Al hacer una transferencia de 3000 desde una cuenta con 3500 a una con 50, en la primera cuenta el saldo se quedará en 500 y en la segunda se quedará en 3050.
  - Al hacer una transferencia de 3000.01 desde una cuenta con 3500 a una con 50, en la primera cuenta el saldo se quedará en 3500 y en la segunda se quedará en 50.
  - Al hacer una transferencia de 2000 desde una cuenta con 3500 a una con 50, y justo después otra de 1200, en la primera cuenta el saldo se quedará en 1500 y en la segunda se quedará en 2050.

Y ahora **sí**, hemos acabado de capturar **las especificaciones**. Con esto ya podemos empezar a escribir tests y a programar siguiendo el **algoritmo TDD** visto con anterioridad. Cada ejemplo marcado en negrita, se convertirá en un test que habrá que pasar posteriormente.

**Recapitulamos**

Vamos a partir de una parte de las especificaciones obtenidas mediante ATDD en la nueva list II  
Recordemos que estos ejemplos, 20 en total más o menos, se habían sacado a partir de apenas 1 párrafo de las especificaciones inicial

>"Quiero lanzar una aplicación monedero para el pago entre amigos. Cada usuario tendrá una cuenta con saldo. La idea es que se puedan hacer transferencias a tus amigos directamente desde la app. La aplicación permitirá al usuario ingresar dinero o retirarlo cuando quiera."

Profundizando en la entrevista con el cliente, obtuvimos unas especificaciones basadas en **ejemplos concisos y concretos** que:
- Evitarán que tengamos que tomar ninguna decisión sobre el modelo de negocio.
- Nos darán confianza plena para tomar todas las decisiones de diseño necesarias durante la elaboración de los tests.
- Además, permitirán también tanto al cliente como a nosotros mismos tener el mismo Listado de tests de aceptación. 

Si nuestra aplicación pasa esos 20 chequeos, tanto nosotros como nuestro cliente **daremos por buena la implementación** de las 4 funcionalidades: _Creación de cuentas, ingresos, retiradas y transferencias_.

### Pongámonos ya manos al teclado

Hacemos el Setup del Proyecto VSCODE con Java y añadiremos Pruebas unitarias lo que nos instalará el Framework JUnit. Lo personalizamos como hemos visto en clase.

#### Primer test #1<a name="t1"></a>
Empezaremos con la primera funcionalidad, con el primer test de la misma: 
- Al crear cuenta el saldo es cero

**Toca el paso 1. El test que falla**

Hay que testear que al crear una cuenta, el saldo es 0.  
Escribe un test que cree una cuenta y haga un `assert` para comprobar que el saldo es 0.
En JUnit5 Utilizaremos la anotaciones de `@Test` y `@DisplayName`

Decisiones: _package name_ para los test y el _core_ , _nombres para las clases_, etc. _nombre del test_, _nombre de la clase que alberga el test_. etc...

```java
package app.test;
 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
 
class CuentaTest {
   @Test  
   @DisplayName("Al crear una Cuenta el Saldo debe ser Cero")
   void alCrearCuentaElSaldoEsCero() {
		fail("Not yet implemented");
	}
}
```

Tras crear esa clase como **plantilla**, solamente hemos aplicado TDD en _el nombre del test_, que debe ser muy descriptivo, aunque ahora podemos utilizar el `@DisplayName`, todo lo demás forma parte de la elaboración de tests con JUnit.  
Escribamos ahora el test. Según la especificación, el test debe:
- Crear una cuenta
- Comprobar que el saldo es 0  

Si nos fijamos el test va a hacer lo mismo que hará el cliente para validar la especificación.  
Bien, crear una cuenta. ¿Cómo se crea una cuenta en nuestra aplicación? En un desarrollo tradicional, tendrías (con suerte) un documento de diseño que te diría cómo se crea una cuenta. Pero con TDD no hay documento de diseño, las decisiones de diseño se toman **_al vuelo_**, mientras escribimos los tests.  
Así que, como no hay nada diseñado, tienes total libertad para hacer un diseño a tu gusto.   

#1 ¿Cómo te gustaría que fuera el código para crear una cuenta?  
```java
	void alCrearCuentaElSaldoEsCero() {
		Cuenta c = new Cuenta();
	}
```
Acabo de tomar varias decisiones de diseño: 
- Que existe una clase llamada Cuenta y que tiene un constructor que no recibe parámetros. Quizás otro desarrollador hubiera escrito new Account() y otro o new Cuenta(0). Cada uno según su estilo. 
- Optamos en el espíritu de TDD, en escribir lo mínimo necesario.  

**Toca el paso segundo:** comprobar que el saldo de la cuenta es 0. Voy a necesitar algo que todavía no existe. Un mecanismo para conocer el saldo de la cuenta. Voy a optar por un getter típico.
```java
    public void AlCrearCuentaElSaldoEsCero()
    {
        Cuenta c = new Cuenta();
        assertEquals(0, c.getSaldo());
    }
```
Así pues, otra decisión: que la clase tenga un método `getSaldo()`

```diff
+ Escribiendo primero los tests, mi código está orientado a ser fácilmente utilizable. Como debe ser fácilmente utilizado por los tests (no me voy a complicar la vida escribiendo test) acabará siendo también fácil de utilizar por otros componentes de la aplicación.
```

El test ya está hecho, ya lo podemos probar. Evidentemente, no debemos esperar que suceda algo mágico. El test fallará estrepitosamente. Pero ese es precisamente el **paso 1 del algoritmo:  Escribir un test que falle**.   
Ya podemos pasar al **segundo paso del algoritmo: Escribir el código para que el test pase**.
 
Concretamente, **el mínimo código necesario para que el test no falle**.
EL primer impulso es empezar a programar según tenemos en nuestra cabeza (gracias a nuestra experiencia) cómo tiene que ser la clase `Cuenta`, con sus _getters_ y sus _setters_, _propiedades_, _constructor_, _métodos_... y seguro que al terminar, el test pasa. 

Pero no es eso lo que dice TDD. **El código debe estar guiado por el test**. Por ahora el test ni arranca.  
El test necesita que exista una clase Cuenta, así que vamos a crearla. Ahora toca pensar dónde crear esa clase. Por claridad la voy a ubicar en otro package:
```diff
!Ojo! No indico todo el código. Sólo lo que he cambiado.
```
```java
package app.core;
 
public class Cuenta {
 
}
```

Y la importo en mi test:
```java
import app.core.Cuenta;
 
class CuentaTest {
	@Test
	@DisplayName("Al crear una Cuenta el Saldo debe ser Cero")
	void AlCrearCuentaElSaldoEsCero() {
		Cuenta c = new Cuenta();
		assertEquals(0, c.getSaldo());
	}
}
```

Volvemos a ejecutar el test:
```diff
- java.lang.Error: Unresolved compilation problem: 
-	The method getSaldo() is undefined for the type Cuenta
```
Ahora el test nos dice que falta un método `getSaldo()` en la clase `Cuenta`. Manos a la obra.  
```java
public class Cuenta {
 
	public int getSaldo() {
		// TODO Auto-generated method stub
		return null;
	}
}
```

Ejecutemos ahora el test:
```diff 
- Failed.
```

Arreglemoslo, no con un código super completo, y super funcional, sino, recordemos, con el mínimo código necesario para que el test pase.
```java
	public int getSaldo() {
		return 0;
	}
```

Volvemos a ejecutar el test…  
Y pasa. **Hemos acabado el segundo paso del algoritmo**.

```diff
Este es otro de los _chips_, que tenemos que cambiar para aplicar TDD. No pensar en soluciones fantásticas super funcionales... Nos limitamos a hacer lo que hay que hacer, y nada más, aunque parezca sin sentido y erróneo.  
Si el código final debe ser otro, vendrán más tests que nos harán, poco a poco llegar a esa solución final y con sentido. Y si no vienen tests que nos hagan cambiar este código y nos sigue pareciendo que no vale, hay dos posibilidades:
O bien no hemos definido suficientes tests para cubrir la especificación completamente y sin ambigüedades.
O bien todavía no hemos cambiado el chip y no somos capaces de entregar un código que haga únicamente lo que tiene que hacer y no más.  
```

**Toca el paso 3. Refactorización.**
 
En este paso a veces no hay nada que hacer, a veces sí. Depende del estado del código y también de la experiencia de los programadores para detectar necesidades de refactorización.  
En la clase Cuenta no hay nada que refactorizar. Son las 3 líneas que el test necesita: _el nombre de la clase, el nombre del método y que devuelva 0_. Ya nos encontraremos situaciones en las que haya que refactorizar el código.
```java
public class Cuenta {
	public int getSaldo() {
		return 0;
	}
}
``` 

Y el test (no olvidemos que los test también se deben refactorizar) tampoco parece que se preste.
```java
class CuentaTest {
	@Test
	@DisplayName("Al crear una Cuenta el Saldo debe ser Cero")
	void AlCrearCuentaElSaldoEsCero() {
		Cuenta c = new Cuenta();
		assertEquals(0, c.getSaldo());
	}
}
```

Podríamos **REFACTORIZAR** los nombres de los archivos, y consecuentemente las clases si no hemos acertado con ello. A la primera los nombres suelen ser horribles.
Si has refactorizado. Tengo que asegurarme de que no he roto nada. Vuelvo a ejecutar los tests.   

Vemos que no quedan más tests de la **primera funcionalidad**: creación de una cuenta, 
Perfecto. 
Así que pasamos a la segunda (Funcionalidad de ingreso)
Hemos acabado con el primer test. Pasemos al segundo.
 
#### Segundo Test<a name="t2"></a>

Vayamos con el segundo test. Revisa el listado de ejemplos que tenemos que convertir en tests.   
 
**Ingresos**.
2. Al ingresar 100 en cuenta vacía el saldo es 100

**Escribiendo el test**

Tomamos el primer ejemplo: 
- Al ingresar 100 en cuenta vacía el saldo es 100.

Convertimos el ejemplo en un test:
```java
	@Test
	@DisplayName("Al ingresar 100 en Cuenta nueva el Saldo es 100")
	void alIngresar100EnCuentaNuevaElSaldoEs100(){
        Cuenta c = new Cuenta();
        c.ingreso(100);
        assertEquals(100, c.getSaldo());
    }
```

He tenido que tomar otra decisión de diseño. Los ingresos se harán mediante un método `ingreso()` de la clase `Cuenta`, pasando la cantidad como parámetro.  

¡¡¡¡Ejecutamos los tests, **TODOS**!!!!.  

El primero pasa, lógicamente, pero el segundo falla:
A veces pasa que escribimos un test nuevo, que debe fallar y no falla. Esto tiene alguna de estas tres posibles causas:  
 - O bien **nos hemos equivocado** escribiendo el test. Pensamos que está testeando una cosa pero está testeando otra cosa totalmente diferente (cosas del copy-paste, o se nos ha olvidado el `assert`, o un bug en el test, que le puede pasar a cualquiera).
- O bien el nuevo test **no aporta ninguna funcionalidad nueva**, con lo que es un test innecesario.
- O bien, al programar el código que debía pasar los test anteriores, **hemos programado más código y más funcionalidades** de las necesarias.

Sea como sea, el fallo es nuestro y debemos identificarlo para proseguir correctamente:

**Reescribiendo el test.**  
Descartando el test actual y pasando al siguiente.  
Dejando el test como está, el código como está y pasando al siguiente test.  
Como en nuestro caso el test falla, pasamos al segundo paso del algoritmo. Escribir el código para que el test pase.  
```diff
+ Escribamos el mínimo código necesario para que el test pase, sin romper los anteriores, claro.  
```
Creo un método ingreso con un parámetro, porque me lo pide el test. Y siendo muy descuidado y muy torpe, hago que `getSaldo()` devuelva 100 para que mi test pase.
```java
public class Cuenta {
	public int getSaldo() {
		return 100;
	}
	public void ingreso(int cantidad){
	}
}
```

Ejecuto, y me encuentro con la sorpresa de que al programar mi funcionalidad **he roto otras**. No es realmente una pérdida de tiempo por torpeza, o por desconocimiento (es posible que yo acabe de retomar un trabajo que dejó otro programador). Sea como sea, todos los test que acaban de fallar revelan las dependencias o relaciones de mi ejemplo con otros ejemplos.  
La funcionalidad Al ingresar 100 en cuenta vacía el saldo es 100 está ligada con _'Al crear cuenta el saldo es cero'_ de forma que ahora tengo en la cabeza más pistas para escribir un código que pase todos los tests.  
Según los tests el método `getSaldo()` si se llama después de llamar a `ingreso()`, devuelve una cosa, pero si se llama a `getSaldo()` sin haber llamado previamente a `ingreso()` devuelve otra. Así que necesito algún tipo de **"memoria/indicador"** en la clase, como un atributo.
```java
public class Cuenta {
 
	private int saldo;
	
	public Cuenta() { this.saldo=0; }
	
	public int getSaldo() {
		return this.saldo;
	}
	
	public void ingreso(int cantidad){
        this.saldo = 100;
	}
}
```

El método `getSaldo()` ya es exactamente como hubiéramos programado desde el principio, pero ese `this.saldo = 100;` , es horroroso, está mal. Pues no, está perfecto. Ejecuto los tests y pasan ambos.  

```diff
- Ya vendrán otros tests que lo arreglaran, y si no, deberíamos alertar a quien elaboró los ejemplos, para que revise si faltan (le podemos proponer ejemplos sabemos que fallarían), y si no, si somos tan inexpertos que se nos pasa esto, ya vendrá el cliente y dirá: _La aplicación falla: ingreses lo que ingreses, el saldo se queda siempre en 100_ y haremos un ejemplo de que si ingresas 300 el saldo debe ser 300 y su test correspondiente y su código correspondiente.
```

Pero con estos dos tests, el código tal cual está, está perfecto.
En este caso no vemos nada que refactorizar ni en el código ni en los tests.
Los tests:

```java
class CuentaTest {
 
	@Test
	@DisplayName("Al crear una Cuenta el Saldo debe ser Cero")
	void AlCrearCuentaElSaldoEsCero() {
		Cuenta c = new Cuenta();
		assertEquals(0, c.getSaldo());
	}
 
	@Test
	@DisplayName("Al ingresar 100 en Cuenta nueva el Saldo es 100")
	void AlIngresar100EnCuentaNuevaElSaldoEs100() {
        Cuenta c = new Cuenta();
        c.ingreso(100);
        assertEquals(100, c.getSaldo());
    }
 
}
``` 
 
El código: **El visto arriba**
 
#### Test #3: Al ingresar 3000 en cuenta vacía el saldo es 3000 <a name="t3"></a>

Sigamos con el siguiente test.
El siguiente ejemplo de la lista ATDD es: _Al ingresar 3000 en cuenta vacía el saldo es 3000_
Convertimos el ejemplo en un test:
 
```java 
    @Test
	@DisplayName("Al ingresar 3000 en Cuenta nueva el Saldo es 3000")
	void AlIngresar3000EnCuentaNuevaElSaldoEs3000()  {
        Cuenta c = new Cuenta();
        c.ingreso(3000);
        assertEquals(3000, c.getSaldo());
    }
```
 
A primera vista, es lo mismo que el test anterior, pero con otra cantidad, parece que no aporta funcionalidad. Pero si ejecutamos el test, **falla**, así que sí, algo de funcionalidad nueva debe aportar.  

Mirando este test y el anterior vemos que el método ingreso NO devuelve siempre lo mismo. Con este ejemplo se ha eliminado ambigüedad al método ingreso (aunque todavía no se ha eliminado el 100% de ambigüedad, como veremos).  
Volviendo al tema, tenemos un método `getSaldo()` que devuelve 100 cuando al método `ingreso()` le pasamos 100 como parámetro y devuelve 3000 cuando al método ingreso le pasamos 3000 como parámetro. Y devuelve 0 si NO se llama al método `ingreso()`. Pues está clarísimo.

```java
    public void ingreso(cantidad){
        this.saldo = cantidad;
    }
```
 
Ejecutamos los tests, y pasan.  
Somos conscientes de que está mucho mejor que hace un test, pero que todavía no es la solución final, que ingreso tiene que sumar cantidad a lo que hubiera, pero hasta ahora no nos hemos encontrado ningún tests que nos lo exija.  
Si hay más de una solución funcionalmente distinta que hace pasar los tests, es síntoma de que necesitamos más tests. Y los hay, por supuesto, así que lo dejamos así, y pasamos a la fase 3 del algoritmo: **refactorizar**.  
No observamos nada que refactorizar, pasamos al siguiente test.

#### Test #4: Al ingresar 3000 en cuenta con 100 el saldo es 3100<a name="t4"></a>

El siguiente ejemplo de la lista ATDD es:
- Al ingresar 3000 en cuenta con 100 el saldo es 3100  

Esto resuelve la ambigüedad que nos quedaba: las cantidades que se ingresan, se van acumulando.
Convertimos el ejemplo en un test.

Hasta ahora, necesitábamos una cuenta vacía, virgen, y nos bastaba con `new Cuenta()`.  
Ahora necesitamos una cuenta con saldo 100. Tenemos que pensar cómo hacer que una cuenta tenga saldo 100. Volvemos a tener que tomar una decisión de diseño.  

Varias opciones:
- Un nuevo método: `setSaldo()`.
- Utilizar el método ingreso ya existente.
- Usar un parámetro en el constructor para indicar el saldo inicial.

No me quiero complicar si no hay tests que me lo exijan. Así que me quedo con el método `ingreso()`.

```java
    @Test
	@DisplayName("Al ingresar 3000 en Cuenta con 100 el Saldo es 3100")
	 void AlIngresar3000EnCuentaCon100ElSaldoEs3100(){
        Cuenta c = new Cuenta();
        c.ingreso(100);
        c.ingreso(3000);
        assertEquals(3100, c.getSaldo());
	}
```

Ejecutamos los test. 
Falla este último, perfecto.   
Paso 1: Terminado.  
Paso 2: Escribimos código para que no falle el test.  

```java
public class Cuenta {
 
	private int saldo;
	
	public Cuenta() { this.saldo=0;}
	
	public int getSaldo() {
		return this.saldo;
	}
	
	public void ingreso(int cantidad){
        this.saldo += cantidad;
	}
}
```


Ejecutamos los tests, y pasan.  
Además ya estamos tranquilos porque el código ahora es como nuestro sentido común decía que tenía que ser.  
A lo mejor hubiéramos preferido la solución con `setSaldo()` o la del parámetro en el constructor. No pasa nada, Sería un código un poquito más grande, pero igualmente robusto.  
Hemos acabado con los tests de la especificaciones principal de la funcionalidad de ingreso.  
Seguimos con el resto de especificaciones de dicha funcionalidad.

#### Test #5: Al ingresar -100 en cuenta vacía, el saldo sigue siendo 0 <a name="t5"></a>

**Ingresos.**  
...
- No se pueden hacer ingresos negativos
  - Al ingresar -100 en cuenta vacía, el saldo sigue siendo 0  

...   

Convertimos el ejemplo en un test:   
```java
    @Test
	@DisplayName("No se puede ingresar Cantidad Negativa")
	 void NoSePuedeIngresarCantidadNegativa() {
        Cuenta c = new Cuenta();
        c.ingreso(-100);
        assertEquals(3100, c.getSaldo());
    }
```

Los nombres de los tests, mejor la especificación que el ejemplo en sí.
El test falla.   
Paso 2: escribir el código:

```java 
    public void ingreso(float cantidad){
 	if(cantidad < 0){
            this.saldo = 0;
        } else {
            this.saldo += cantidad;
        }
	}
```

Pasa los tests. 

Ese `this.saldo = 0;` chirría, debería venir algún test para quitar esa ambigüedad, pero si echamos un vistazo, no lo hay. Deberíamos entonces avisar de que faltan ejemplos en las especificaciones para que sea del todo robusto.   
¿Por qué no lo programamos bien directamente? (al fin y al cabo sabemos cómo debe ser) y nos olvidamos.   

La respuesta es que si no tenemos una batería de test completa, en el futuro alguien puede hacer cambios en el código, refactorizaciones, etc, y podría volver a introducir el`this.saldo = 0;`, ejecutar los tests, ver que pasan todos, y dar por bueno ese código. Si aplicamos TDD hay que conseguir una batería de tests que no dejan cabida a la ambigüedad. **Esto se consigue con experiencia, y sobretodo, sin programar más funcionalidad de la que pida cada test**.

Voy a dejar este fallo como si no nos hubiéramos dado cuenta. En el futuro, en producción, el cliente lo detectará y tendremos un bug que corregir. Y lo corregiremos siguiendo la técnica TDD.   
No observamos nada que refactorizar, seguimos con el resto de casos


#### Test #6, #7, #8, #9 - Categoría DECIMALES <a name="t6"></a>

***Ingresos***
- Los ingresos admiten un máximo de 2 decimales de precisión  
  6. Si ingreso 100.45 en una cuenta vacía, el saldo es de 100.45  
  7. Si ingreso 100.457 en una cuenta vacía, el saldo es de 0  
- La cantidad máxima que se puede ingresar es de 6000  
  8. Si ingreso 6000.00 en una cuenta vacía, el saldo es de 6000.00  
  9. Si ingreso 6000.01 en una cuenta vacía, el saldo es de 0     

Tests:
```java
    @Test
	@DisplayName("Ingreso Cantidad con 2 Decimales")
	 void IngresoCantidad2Decimales() {
        Cuenta c = new Cuenta();
        c.ingreso(100.45);
        assertEquals(100.45, c.getSaldo());
    }
```

Ejecuto el test y... falla. El paso 1 de TDD dice que tengo que escribir un test que falle.   
Ojo! Con un Lenguaje de *Tipado dinámico* (PHP, Python, JS ) puede no fallar, ya que no haría distinción entre enteros y decimales. Java y la mayoría de lenguajes de *tipado estático* obliga con este test a ir al código y cambiar el tipo de dato de `int` a `float`.   
Lo corregimos, pasamos el test y refactorizamos (nada en este caso) y continuamos con el siguiente test

Seguimos.   
Haremos los 3 tests que quedan. No aportan nada didáctico nuevo.
- Si ingreso 100.457 en una cuenta vacía, el saldo es de 0
- Si ingreso 6000.00 en una cuenta vacía, el saldo es de 6000.00
- Si ingreso 6000.01 en una cuenta vacía, el saldo es de 0  
Tests:
```java
   @Test
   @DisplayName("Ingreso de Cantidad de más de 2 Decimales No EsValido")
   void IngresoCantidadMasDe2DecimalesNoEsValido(){
        Cuenta c = new Cuenta();
        c.ingreso(100.457);
        assertEquals(0, c.getSaldo());
    }
 
    @Test
    @DisplayName("Ingreso Máximo de 6000")
    void IngresoMaximoEsDe6000(){
        Cuenta c = new Cuenta();
        c.ingreso(6000);
        assertEquals(6000, c.getSaldo());
    }
 
    @Test
    @DisplayName("Ingreso de más de 6000 No Es Valido")
    void IngresoMasDe6000NoEsValido(){
        Cuenta c = new Cuenta();
        c.ingreso(6000.01);
        assertEquals(0, c.getSaldo());
    }
 
```    
Código final del `core`:
```java
public class Cuenta {
 
	private float saldo;
	
	public Cuenta() { this.saldo=0;}
	
	public int getSaldo() {
		return this.saldo;
	}
	
	public void ingreso(float cantidad){
        if(round(cantidad, 2)!=cantidad)
           { this.saldo = 0; }
        elseif(cantidad < 0)
           { this.saldo = 0; }
        elseif(cantidad > 6000.00)
           { this.saldo = 0; } 
        else 
           { this.saldo += cantidad;  }
	}
}
```

Paso 2: los tests pasan.  
Paso 3: Refactorizar. La función `ingreso()` empieza a tener más líneas de control que de lo que realmente hace. E paso me pide refactorizar. Voy a crear un método privado de `validarCantidadIngresada`

```diff
- Esta función no está en el codigo del repositorio.
```

```java    
    public void ingreso(float cantidad){
        bool esValida = this.validarCantidadIngresada(cantidad);
        if(esValida){ 
            this.saldo += cantidad;
        } else {
            this.saldo = 0;
        } 
    }
    
    private void validarCantidadIngresada(float cantidad){
        if(round(cantidad, 2)!=cantidad) {
            return false;
        }
        
        if(cantidad < 0) {
            return false;
        }
        
        if(cantidad > 6000.00){
            return false;
        } 
        
        return true;
    }
```

Vuelvo a ejecutar los tests. ¡¡¡Y pasan!!! Mi código hace exactamente lo mismo que antes. TDD da una seguridad muy grande a la hora de afrontar cambios o refactorizaciones.  
 
**Los tests como parte de la documentación**
- Importancia de los nombres de los tests
- Importancia de que cada tests se dedique a una única especificación
 
Ya hemos acabado con la funcionalidad de **ingreso**.
Ahora tocarían la de **retirada** y la de **transferencia**.

```diff
- Turno de la familia 1daw3. Os toca hacer los ejemplos que faltan.
! - Hacer los metodos necesarios, para las funcionalidades de retirar, y transferencia
+ PARA ESTAS NAVIDADES!!!

- Para seguir el texto que continua debes tener los metodos de "transferencia" finalizados. y "validarCantidadTransferencia".
```

## Test Driven Bug Fixing<a name="tdbf"></a>

Hace tiempo que acabamos el desarrollo de nuestra aplicación y está funcionando en producción sin problemas.   
Pero un día llega nuestro cliente super preocupado. La aplicación no va. Dice que las transferencias no funcionan bien.  
Como bien sabemos por experiencia, **para poder corregir un bug, necesitamos reproducirlo**. Así, que le pedimos al cliente que nos diga cómo ha sido alguno de los casos en los que ha ocurrido el fallo.  

>Un usuario ha intentado transferir 2500 teniendo solamente 2350 de saldo. Al emisor no se le ha quitado el dinero (bien) pero el receptor ha recibido dinero (mal).

Nuestro primer impulso es ir al código, a la función transferencia, y mirarla para ver si descubrimos el fallo. **¡¡¡Error!!!** eso no es TDD.  

Para corregir el bug siguiendo TDD hay que seguir la técnica denominada **Test Driven Bug Fixing** (Corrección de Bugs Guíado por Tests). Esto no es más que hacer lo que hemos estado haciendo durante todo el proyecto:  
- Escribir un test que falle
- Escribir el código que haga pasar el test
- Refactorizar.

Lo primero es verificar con el cliente qué debe hacer exactamente la aplicación en ese caso que no está funcionando.
>Si un usuario con 2350 transfiere 2500 a otro usuario con saldo 50 ¿cuál debería ser el resultado? Como no se puede hacer esa transferencia porque el usuario emisor no tiene suficiente saldo, los saldos deberían quedarse como están.

O. Entonces, si un usuario con 2350€ transfiere 2500€ a otro usuario con saldo 50€, el saldo del emisor debería seguir siendo 2350€ y el del receptor debería seguir siendo 50€ ¿correcto?

> "Correcto, así debe ser"

Hemos vuelto a hacer ATDD y ya tenemos un ejemplo concreto para convertirlo en un test.  
**TDD - Paso 1. Escribimos el test.**

```java
  	@Test
	@DisplayName("")
	void NoSePuedeTransferirMasSaldoDelDisponible() {
		Cuenta cuenta1 = new Cuenta();
		cuenta1.ingreso(2350);

		Cuenta cuenta2 = new Cuenta();
		cuenta2.ingreso(300);

		cuenta1.transferencia(cuenta2, 2500);

		assertEquals(2350, cuenta1.getSaldo());
		assertEquals(300, cuenta2.getSaldo());
	}
```

Ejecutamos. El test falla. Precisamente ocurre lo que el usuario ha reportado que ocurre: al emisor no se le descuenta ninguna cantidad, pero al receptor sí que se le ingresa la cantidad.   
**"Test Driven Bug Fixing"** nos da la seguridad de estar reproduciendo exactamente el fallo que hay que corregir, y al ejecutar el test, ver que realmente falla. ¡No tenemos ni que utilizar la aplicación para comprobarlo!

**TDD - Paso 2. Hacer que el test Pase.**

```java    
    void validarCantidadTransferencia(cantida){        
        if(cantidad < 0) {
            return false;
        }
        
        if(cantidad > this.saldo) {
            return false;
        }
        
        if(cantidad > 3000){
            return false;
        } 
        
        return true;
    }
}
```

Ejecutamos. ¡¡¡Pasa todos los tests!!! 

**TDD - Paso 3: Refactorizamos** (si es necesario) y volvemos a comprobar que pasan todos los tests
Todo correcto. Ya podemos subir el código a producción sin miedo. ;)

 
#### Test Driven Bug Fixing II<a name="tdbf2"></a>

Hace tiempo que acabamos el desarrollo de nuestra aplicación y está funcionando en producción sin problemas.   
Pero un día llega nuestro cliente super preocupado.   La aplicación no va. Dice que 
> a  algunos usuarios les deja sin saldo.   

Como bien sabemos por experiencia, para poder corregir un **bug**, necesitamos reproducirlo. Así, que le pedimos al cliente que nos diga cómo ha sido alguno de los casos en los que ha ocurrido el fallo.    
Nos cuenta que: 
> un usuario se ha quejado de que tenía 2350€ en su cuenta, ha ido a ingresar 7000€ y se ha quedado sin saldo.

Nuestro primer impulso es ir al código, a la función ingreso, y mirarla para ver si descubrimos el fallo. ¡¡¡Error!!!* eso no es TDD. Para corregir el bug siguiendo TDD hay que seguir la técnica denominada **Test Driven Bug Fixing** (Corrección de Bugs Guíado por Tests). Esto no es más que hacer lo que hemos estado haciendo durante todo el proyecto:  
- Escribir un test que falle
- Escribir el código que haga pasar el test
- Refactorizar.  

Lo primero es verificar con el cliente qué debe hacer exactamente la aplicación en ese caso que no está funcionando.

"Si un usuario con 2350€ ingresa 7000, el saldo debería ser 9350 ¿verdad?

> "No, no. Debería seguir siendo 2350. Hay un límite de ingreso de 6000."

"De acuerdo".

Hemos vuelto a hacer ATDD y ya tenemos un ejemplo concreto para convertirlo en un test:

**TDD - Paso 1. Escribimos el test.**

```java
    @Test
	@DisplayName("")
	public void ingresoMasDe6000NoEsValidoAlIngresar7000EnCuentaCon2350ElSaldoSeQuedaEn2350(){
        //Arrange
        Cuenta c = new Cuenta();
        c.ingreso(2350);
        
        //Act
        c.ingreso(7000);

        //Asert
        assertEquals(2350, c.getSaldo());
    }

```

Ejecutamos. El test falla. Precisamente ocurre lo que el usuario ha reportado que ocurre: **su saldo se queda a 0.**   
**"Test Driven Bug Fixing"** nos da la seguridad de estar reproduciendo exactamente el fallo que hay que corregir, y al ejecutar el test, ver que realmente falla. ¡No tenemos ni que utilizar la aplicación para comprobarlo!   

**TDD - Paso 2. Hacer que el test Pase.**
```java
    public void ingreso(cantidad){
        esValida = this.validarCantidadIngresada(cantidad);
        if(esValida){ 
            this.saldo += cantidad;
        }
    }
```

Ejecutamos. ¡¡¡Pasa todos los tests!!!   

**TDD - Paso 3: Refactorizamos (si es necesario)** y volvemos a comprobar que pasan todos los tests
Todo correcto. Ya podemos subir el código a producción sin miedo.

## Errores comunes en TDD<a name="errores"></a>
 
Empezar con TDD no es coser y cantar. A lo largo del proceso surgen muchas dudas y se cometen muchos errores.   
A continuación enumero una pequeña lista de errores comunes al empezar con TDD.  
- El nombre del test no es suficientemente descriptivo
Recordemos que el nombre de un método y de sus parámetros son su mejor documentación. En el caso de un test, su nombre debe expresar con total claridad la intención del mismo.
- Los nombres de los tests se deben parecer a esto:
  - IngresoCantidadMasDe2DecimalesNoEsValido
  - AlRetirar100EnCuentaCon500ElSaldoEs400
  - AlRetirar200EnCuentaCon1200ElSaldoEs1000YAlRetirarOtros150ElSaldoEs850   
y no a esto:  
  - testRetirada1
  - testRetirada2
  - testRetirada3
  - testForBUG128

aunque no debemos ser estrictos con JUnit5 ya que podemos utilizar el `@DisplayNmae`.

- _Escribir demasiados tests de una vez_  
La técnica establece que se debe escribir un test, y luego el código para hacerlo pasar. Luego otro test, y luego el código para hacerlo pasar... Siempre de uno en uno. De esta forma, al programar el código que debe pasar el test, nos aseguramos que cada decisión de diseño (de clases, métodos, relaciones entre clases, etc,) tomada en los tests es acertada, viable, válida...  
Al coger práctica y experiencia con TDD, es bastante habitual escribir varios tests seguidos y luego el código que los hace pasar. Pero si escribimos decenas de tests seguidos, corremos el riesgo de acarrear malas decisiones de diseño que no hemos contrastado al escribir el código que los hace pasar.
- _Adopción parcial de TDD_    
A veces sucede, por diversos motivos, que no todos los desarrolladores del equipo usan TDD.   
El proyecto fracasará con toda seguridad a menos que todo el equipo al completo esté aplicando TDD.
- _No sabemos qué es lo que queremos que haga el SUT_ (Subjet Under Test)  
Nos hemos lanzado a escribir un test pero no sabemos en realidad qué es lo que el código bajo prueba tiene que hacer.  
En algunas ocasiones, lo resolvemos hablando con el dueño del producto y, en otras, hablando con otros desarrolladores. Hay que tener en cuenta que se están tomando decisiones de diseño al escribir los tests por lo que las especificaciones tienen que estar muy claras antes de empezar para no acabar diseñando un software que no cumple las especificaciones.  
- _No sabemos quién es el SUT y quién es el colaborador_  
Es muy común que cuando necesitamos un colaborador, aquel que representamos mediante un doble (un mock, un stub...), para testear una funcionalidad del SUT, acabamos testeando al colaborador en lugar de al SUT.  MOCK y STUB están fuera de esta introducción.
TAL VEZ para la siguiente evaluación.   
- _Un mismo método de test está haciendo múltiples afirmaciones._    
Cuando practicamos TDD correctamente, apenas tenemos que usar el depurador. Cuando un test falla, lo encontramos directamente y lo corregimos en dos minutos. Para que esto sea así, cada método debe probar una única funcionalidad del SUT. A veces utilizamos varias afirmaciones (`asserts`) en el mismo test, pero sólo si giran en torno a la misma funcionalidad. Un método de test raramente excede las 10 líneas de código.  
- _Se nos olvida refactorizar._  
No sólo por tener una gran batería de tests, el código ya es más fácil de mantener. Si el código no está limpio, será muy costoso modificarlo, y también sus tests. No hay que olvidar buscar y corregir código duplicado después de hacer pasar cada test. El código de los tests debe estar tan limpio como el código de producción.  
- _No eliminamos código muerto._    
A veces, tras cambios en las especificaciones, queda código en desuso. Puede ser código de producción o pueden ser tests. Puesto que normalmente disponemos de un sistema de control de versiones que nos permite volver atrás si alguna vez volviese a hacer falta el código, debemos eliminar todo código que creamos en desuso. El código muerto induce a errores antes o después. Se suele menospreciar cuando se trata de tests pero, como hemos hecho notar antes, el código de los tests es tan importante como el código que testean.

## ANTIPATRONES<a name="anti"></a>

James Carr (https://blog.james-carr.org/) recopiló una lista de antipatrones ayudado por la comunidad TDD.   
Ese listado ya no está disponible en su blog, pero la comunidad de TDD mantiene un catálogo de antipatrones en stackoverflow:   
Los nombres que les pusieron tienen un carácter cómico y no son en absoluto oficiales pero su contenido dice mucho. Algunos de ellos ya están recogidos en los errores comentados en el trabajo anterior.   
Como en tantas otras áreas, las reglas tienen sus excepciones. El objetivo es tenerlos en mente para identificar posibles malas prácticas al aplicar TDD.


Carlos Herrera traducido algunos de ellos y enumerado a continuación:
- _El Mentiroso_ 
Un test completo que cumple todas sus afirmaciones (asserts) y parece ser válido pero que cuando se inspecciona más de cerca, muestra que realmente no está probando su cometido en absoluto.  
- _Setup Excesivo_   
Es un test que requiere un montón de trabajo para ser configurado. A veces se usan varios cientos de líneas de código para configurar el entorno de dicho test, con varios objetos involucrados, lo cual nos impide saber qué es lo que se está probando debido a tanto "ruido".   
- _El Gigante_  
Aunque prueba correctamente el objeto en cuestión, puede contener miles de líneas y probar muchísimos casos de uso. Esto puede ser un indicador de que el sistema que estamos probando es un Objeto Todopoderoso.  
- _El Imitador_  
A veces, usar mocks puede estar bien y ser práctico pero otras, el desarrollador se puede perder imitando los objetos colaboradores. En este caso un test contiene tantos mocks, stubs y/o falsificaciones, que el SUT ni siquiera se está probando. En su lugar estamos probando lo que los mocks están devolviendo.   
- _Sobras Abundantes_  
Es el caso en que un test crea datos que se guardan en algún lugar y otro test los reutiliza para sus propios fines. Si el generador de los datos se ejecuta después, o no se llega a ejecutar, el test que usa esos datos falla por completo.  
- _El Héroe Local_  
Depende del entorno de desarrollo específico en que fue escrito para poder ejecutarse. El resultado es que el test pasa en dicho entorno pero falla en cualquier otro sitio. Un ejemplo típico es poner rutas que son específicas de una persona, como una referencia a un fichero en su escritorio.  
- _El Cotilla Quisquilloso_  
Compara la salida completa de la función que se prueba, cuando en realidad sólo está interesado en pequeñas partes de ella. Esto se traduce en que el test tiene que ser continuamente mantenido a pesar de que los cambios sean insignificantes. Este es endémico de los tests de aplicaciones web. Ejemplo, comparar todo un HTML de salida cuando solo se necesita saber si el title es correcto.   
- _El Cazador Secreto_  
A primera vista parece no estar haciendo ninguna prueba por falta de afirmaciones (`asserts`). El test está en verdad confiando en que se lanzará una excepción en caso de que ocurra algún accidente desafortunado y que el framework de tests la capturará reportando el fracaso.   
Ejemplo: Test de conexión a base de datos. Se confía en que si no se establece conexión, el propio sistema lanzará una excepción provocando que falle el test.  
- _El Escaqueado_  
Un test que hace muchas pruebas sobre efectos colaterales (presumiblemente fáciles de hacer) pero que nunca prueba el auténtico comportamiento deseado.  
- _El Bocazas_  
Un test o batería de tests que llenan la consola con mensajes de diagnóstico, de log, de depuración, y demás forraje, incluso cuando los tests pasan. A veces, durante la creación de un test, es necesario mostrar salida por pantalla, y lo que ocurre en este caso es que, cuando se termina, se deja ahí aunque ya no haga falta, en lugar de limpiarlo.  
- _El Cazador Hambriento_  
Captura excepciones y no tiene en cuenta sus trazas, a veces reemplazandolas con un mensaje menos informativo, pero otras incluso registrando el suceso en un log y dejando el test pasar.  
- _El Secuenciador_   
Un test unitario que depende de que aparezcan, en el mismo orden, elementos de una lista sin ordenar.   
- _Dependencia Oculta_   
Un primo hermano del Héroe Local, un test que requiere que existan ciertos datos en alguna parte antes de correr. Si los datos no se rellenaron, el test falla sin dejar apenas explicación, forzando al desarrollador a indagar por acres de código para encontrar qué datos se suponía que debía haber.   
- _El Enumerador_  
Una batería de tests donde cada test es simplemente un nombre seguido de un número, ej, test1, test2, test3. Esto supone que la misión del test no queda clara y la única forma de averiguarlo es leer todo el test y rezar para que el código sea claro.   
- _El Extraño_   
Un test que ni siquiera pertenece a la clase de la cual es parte. Está en realidad probando otro objeto (X), muy probablemente usado por el que se está probando en la clase actual (objeto Y), pero saltándose la interacción que hay entre ambos, donde el objeto X debía funcionar en base a la salida de Y, y no directamente. También conocido como _La Distancia Relativa_.  
- _El Evangelista de los Sistemas Operativos  
Confía en que un sistema operativo específico se está usando para ejecutarse. Un buen ejemplo sería un test que usa la secuencia de nueva línea de Windows en la afirmación (assert), rompiéndose cuando corre bajo Linux.  
- El que _Siempre Funciona_  
Se escribió para pasar en lugar de para fallar primero. Como desafortunado efecto colateral, sucede que el test siempre funciona, aunque debiese fallar.  
- _El Libre Albedrío_
En lugar de escribir un nuevo test para probar una nueva funcionalidad, se añade una nueva afirmación (assert) dentro de un test existente.  
- _El Único_    
Una combinación de varios antipatrones, particularmente El Libre Albedrío y El Gigante. Es un sólo test unitario que contiene el conjunto entero de pruebas de toda la funcionalidad que tiene un objeto. Una indicación común de eso es que el test tiene el mismo nombre que su clase y contiene múltiples líneas de setup y afirmaciones.  
- _El Macho Chillón_  
Debido a recursos compartidos puede ver los datos resultantes de otro test y puede hacerlo fallar incluso aunque el sistema a prueba sea perfectamente válido. Esto se ha visto comúnmente, donde el uso de variables de clase estáticas, usadas para guardar colecciones, no se limpiaban adecuadamente después de la ejecución, a menudo repercutiendo de manera inesperada en otros tests. También conocido como _El huésped no invitado_.  
- _El Excavador Lento_  
Un test que se ejecuta de una forma increíblemente lenta. Cuando los desarrolladores lo lanzan, les da tiempo a ir al servicio,tomar café, o peor, dejarlo corriendo y marcharse a casa al terminar el día.  
- _Ciudadanos de segunda clase_  
El código de los tests no se refactoriza tan cuidadosamente como el código de producción, acabando con un montón de código duplicado, y haciendo que los tests sean difícil de mantener.  
- _El Inspector_  
Viola la encapsulación en un intento de conseguir el 100 % de cobertura de código y por ello sabe tanto del objeto a prueba que, cualquier intento de refactorizarlo, rompe el test.

### Ventajas y desventajas del TDD<a name="procon"></a>

#### Ventajas o beneficios
- Cuando se utiliza TDD en un proyecto virgen, en raras ocasiones se tiene la necesidad de utilizar el depurador o debugger.  
- A pesar de los elevados requisitos iniciales de aplicar esta metodología, el desarrollo guiado por pruebas (TDD) puede proporcionar un gran valor añadido en la creación de software, produciendo aplicaciones de más calidad y en menos tiempo.  
- Proporciona al programador una gran confianza en el código desarrollado.  
- Minimiza el número de bugs en producción.
- El equipo de desarrollo se focaliza en lo que el cliente quiere (todavía mucho más si se aplica ATDD).

#### Desventajas
Cuesta muchísimo trabajo encontrar en la literatura y en la bibliografía las desventajas del uso de TDD. Aquí pongo un listado de las que he encontrado:
- Aprender bien la técnica es todo un reto.
- Muchos tests pueden ser difíciles de escribir, sobretodo los tests no unitarios.
- Casi imposible de aplicar a código "legacy".
- TDD incrementa enormemente la confianza, pero puede crear una falsa sensación de seguridad.
- Al igual que introducimos bugs al programar el código, es posible introducir bugs al programar los tests.
- Si el programador malinterpreta la especificación entonces se acabará creando un test que llevará a un código que pasa el test pero que está mal.
- Los tests automatizados no son sustitutos de testeadores reales. Siempre es necesaria la mezcla de ambos.
- Dado que los programadores que escriben el código, testean el código, se pierden la buena práctica de que el testeador sea distinto del programador.
 


#### Los Niveles `profesionales` en TDD<a name="pro"></a>
o  ¿Puedo aplicar TDD con un equipo que no sea experto?


**Principiante** 
- Capaz de escribir un test unitario previo al código correspondiente.
- Capaz de escribir código suficiente para hacer pasar un test que previamente falla.

**Intermedio** 
- Practica "test driven bug fixing": Cuando se encuentra un bug, escribe un test que reproduce el defecto antes de corregirlo.
- Capaz de descomponer una funcionalidad de un programa en una secuencia de varios test unitarios.  
- Conoce y puede nombrar diferentes tácticas para guiar en la escritura de tests a sus compañeros (por ejemplo: "para testear un algoritmo recursivo, primero escribe un test para el caso correspondiente al fin de la recursión")   
- Capaz de detectar elementos reutilizables en los tests  
- Capaz de proporcionar herramientas de prueba específicas para cada situación  

**Experto**
- Capaz de establecer un "roadmap" de tests planificados para funcionalidades macroscópicas (y revisarlo si es necesario)  
- Capaz de aplicar TDD con diferentes paradigmas de diseño: orientación a objetos, funcional, dirigido por eventos...  
- Capaz de aplicar TDD con diferentes dominios técnicos: interfaz de usuario, acceso persistente a base de datos...



VSCODE:  
The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
