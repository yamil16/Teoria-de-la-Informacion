package TP1_TeoriaInformacion;





import java.util.Comparator;

public class Criterio implements Comparator<Nodo>{

	
	public Criterio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int compare(Nodo a1, Nodo a2) {
		 if (a1.getValor() > a2.getValor())
			return 1;
		else
		if (a1.getValor() < a2.getValor())
			return -1;			
		else
			return 0; 
	}

	
	
}
