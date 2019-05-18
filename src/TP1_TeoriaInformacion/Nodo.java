package TP1_TeoriaInformacion;





public class Nodo implements Comparable<Nodo>{
	private String simbolo;
	private double valor;
	private String valHuf;
	private Nodo izq;
	private Nodo der;
	public Nodo(double valor,String simbolo) {
		super();
		this.simbolo=simbolo;
		this.setValor(valor);
		//this.setValHuf(new Vector<Integer>());
		this.setIzq(null);
		this.setDer(null);
		this.valHuf="";
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getValHuf() {
		return valHuf;
	}
	public void setValHuf(String valHuf) {
		this.valHuf = valHuf;
	}
	public Nodo getIzq() {
		return izq;
	}
	public void setIzq(Nodo izq) {
		this.izq = izq;
	}
	public Nodo getDer() {
		return der;
	}
	public void setDer(Nodo der) {
		this.der = der;
	}
	@Override
	public int compareTo(Nodo a2) {
		 if (this.getValor() > a2.getValor())
				return 1;
			else
			if (this.getValor() < a2.getValor())
				return -1;			
			else
				return 0; 
	}
	public String getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	

}
