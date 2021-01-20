package app.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import app.core.Cuenta;

class CuentaTest {

	@Test
	@DisplayName("Al crear una Cuenta el Saldo debe ser Cero")
	void alCrearCuentaElSaldoEsCero() {
		Cuenta c = new Cuenta();
		assertEquals(0, c.getSaldo());
	}

	@Test
	@DisplayName("Al ingresar 100 en Cuenta nueva el Saldo es 100")
	void alIngresar100EnCuentaNuevaElSaldoEs100() {
		Cuenta c = new Cuenta();
		c.ingreso(100);
		assertEquals(100, c.getSaldo());
	}

	@Test
	@DisplayName("Al ingresar 3000 en Cuenta nueva el Saldo es 3000")
	void alIngresar3000EnCuentaNuevaElSaldoEs3000() {
		Cuenta c = new Cuenta();
		c.ingreso(3000);
		assertEquals(3000, c.getSaldo());
	}

	@Test
	@DisplayName("Al ingresar 3000 en Cuenta con 100 el Saldo es 3100")
	void alIngresar3000EnCuentaCon100ElSaldoEs3100() {
		Cuenta c = new Cuenta();
		c.ingreso(100);
		c.ingreso(3000);
		assertEquals(3100, c.getSaldo());
	}
	@test
	@displayName("Al ingresar -100 en ctavacia el saldo es 0")
	void test5(){
		cuenta c=new cuenta();
		c.ingreso(-100);
		assertEquals(0, c.getSaldo());
	}
	@test
	@displayName("Al ingresar 100,45 en ctanueva saldo es 100.45")
	void test6(){
		cuenta c=new cuenta();
		c.ingreso(100.45);
		assertEquals(100.45, c.getSaldo());
	}
	@test
	@displayName("Al ingresar 100,457 en cta vacia saldo es 0")
	void test7(){
		cuenta c=new cuenta();
		c.ingreso(100.457);
		assertEquals(0, c.getSaldo());
	}
	@test
	@displayName("Al ingresar 6000,00 en cta vacia saldo es 6000")
	void test8(){
		cuenta c=new cuenta();
		c.ingreso(6000);
		assertEquals(6000, c.getSaldo());
	}
	@test
	@displayName("Al ingresar 6000,01 en cta vacia saldo es 0")
	void test9(){
		cuenta c=new cuenta();
		c.ingreso(6000,01);
		assertEquals(0, c.getSaldo());
	}

}
