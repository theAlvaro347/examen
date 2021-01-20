package app.core;

public class Cuenta {

	private double saldo;
	
	public Cuenta() { this.saldo=0;}
	
	public double getSaldo() {
		return this.saldo;
	}
	
	public void ingreso(double cantidad){
		int cint =(int) cantidad*100;
		String sint = String.format("%2f",cantidad);
		if(cint != cantidad)return;
		if(cantidad<0)return;
        this.saldo += cantidad;
	}

}
