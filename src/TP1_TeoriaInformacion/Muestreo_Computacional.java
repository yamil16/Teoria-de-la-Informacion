package TP1_TeoriaInformacion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

public class Muestreo_Computacional  {	
	private Vector<Double> probOriginales;
	private Vector<Nodo> nodos;
	private Vector<Nodo> hojas; 
	private Double[][] matrizTransicion;
	private Vector<Double> probOrdenDos;
	private Vector<Integer> simbolos;
	private String rutaImagen;
	
	public Muestreo_Computacional(String rutaImagen) {
		super();
		this.probOriginales = new Vector<Double>();
		this.nodos = new Vector<Nodo>();
		this.hojas = new Vector<Nodo>();
		this.matrizTransicion = null;
		this.probOrdenDos = new Vector<Double>();
		this.simbolos = new Vector<Integer>();
		this.rutaImagen=rutaImagen;
	}

	public Vector<Nodo> getNodos() {
		return nodos;
	}

	public void setNodos(Vector<Nodo> nodos) {
		this.nodos = nodos;
	}


	public Double Entropia(){
	Double resultado=0.0;
	for(int i=0;i<probOriginales.size();i++){
		if (probOriginales.get(i)!=0)
			resultado+=probOriginales.get(i)*(Math.log10(probOriginales.get(i))/Math.log10(2)*-1);
	}
	return resultado;		
	}
	

	public Double EntropiaCondicional(){
		Double resultado=0.0;
		for (int j = 0; j < matrizTransicion.length; j++) {
			Double suma=0.0;
			Double prob=-probOriginales.get(j);
			for (int i = 0; i < matrizTransicion.length; i++) {
			if (matrizTransicion[i][j]!=0)
				suma+=matrizTransicion[i][j]*(Math.log10(matrizTransicion[i][j])/Math.log10(2));
			}
			if (prob!=0)
				resultado+=suma*prob;
		}
		return resultado;
	}

	public Double EntropiaOrdenDos(){
		return  Entropia()-EntropiaCondicional();
	}
	
	public Double LongitudMedia(Vector<Nodo> res){
		Double resultado=0.0;
		for(int i=0;i<res.size();i++){
			resultado+=(res.get(i).getValor()*res.get(i).getValHuf().length());
		}
		return resultado;		
	}
	
	public void ArmarVectorNodos(){
		Leer_Imagen leer= new Leer_Imagen(rutaImagen);
		leer.leerImagen();
		if(nodos.size()>0){
			System.out.println("Estamos borrando vector nodos");
			hojas.removeAllElements();
			nodos.removeAllElements();
		}
		probOriginales=leer.getDistribucionProbabilidad();
		simbolos=leer.getSimbolos();
		for(int i=0;i<probOriginales.size();i++){
			//String simbolo="("+Integer.toString(simbolos.get(i))+")";  me daba error los parentesis en TP2  
			String simbolo=Integer.toString(simbolos.get(i));
			Nodo nuevo= new Nodo(probOriginales.get(i),simbolo);
			nodos.addElement(nuevo);				
		}			
	}
	
	public void imprimirVector(Vector<Double> vec){
		System.out.println("Imprimiendo vector");
		String impresion="";
		for (int i = 0; i < vec.size(); i++) {
			impresion=impresion+vec.get(i)+" ";
		}
		System.out.println(impresion);
	}
	
	public void getProbConjunta(){
		for (int j = 0; j < matrizTransicion.length; j++) {
			for (int i = 0; i < probOriginales.size(); i++) {
				probOrdenDos.addElement(matrizTransicion[i][j]*probOriginales.elementAt(j));
			}
		}
	}	
	public void armarVectorNodosOrdenDos(){
		Leer_Imagen leer= new Leer_Imagen(rutaImagen);
		leer.leerImagen();
		simbolos.removeAllElements();
		probOriginales=leer.getDistribucionProbabilidad();
		simbolos=leer.getSimbolos();
		matrizTransicion=leer.getMatrizTransicionProbabilidades(probOriginales.size());
		getProbConjunta();
		hojas.removeAllElements();
		nodos.removeAllElements();
		for(int i=0;i<probOrdenDos.size();i++){
			String simbolo = "("+Integer.toString(simbolos.get(i/probOriginales.size()))+","+ Integer.toString(simbolos.get(i%probOriginales.size()))+")";	//ver		
			Nodo nuevo= new Nodo(probOrdenDos.get(i),simbolo);
			nodos.addElement(nuevo);
		}		
	}
	
	public Nodo ArmarArbol(){
		Nodo raiz;
		while (nodos.size()>1){		
			Collections.sort(nodos);
			double valor1=nodos.get(0).getValor();
			double valor2=nodos.get(1).getValor();
			double suma=valor1+valor2;
			Nodo nuevo= new Nodo(suma,"");		
			if(valor1>valor2){
				nuevo.setDer(nodos.get(0));
				nuevo.setIzq(nodos.get(1));
				InsertarValorHufman(1,nodos.get(0));
				InsertarValorHufman(0,nodos.get(1));
			}
			else
			{
				nuevo.setDer(nodos.get(1));
				nuevo.setIzq(nodos.get(0));
				InsertarValorHufman(1,nodos.get(1));
				InsertarValorHufman(0,nodos.get(0));
			}
			nodos.remove(1);
			nodos.remove(0);
			nodos.addElement(nuevo);
			}
		raiz=nodos.get(0);
		return raiz;
	}	
	
	public void InsertarValorHufman(int val, Nodo n){
		if(n!=null){
			n.setValHuf(val+n.getValHuf());
			InsertarValorHufman(val,n.getIzq());
			InsertarValorHufman(val,n.getDer());
		}		
	}
	
	public Vector<Nodo> ObtenerHojas(Nodo n){
		if ((n.getDer()==null) &&(n.getIzq()==null))
			hojas.add(n);
		else{
			ObtenerHojas(n.getIzq());
			ObtenerHojas(n.getDer());
		}	
		return hojas;
	}
	public void GuardarEnArchivo(Vector<Nodo> res,Double longitud,Double entropia,String nombre){
			File f;
			f=new File(nombre);
			try{
				FileWriter w = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(w);
				PrintWriter wr = new PrintWriter(bw);
				for(int i=0;i<res.size();i++){
					wr.write("Simbolo "+res.get(i).getSimbolo()+" ");
					wr.append("Probabilidad Ocurrencia "+res.get(i).getValor()+" "); 
					wr.append("Codigo Asociado "+res.get(i).getValHuf()+" " );
					wr.append("Longitud "+res.get(i).getValHuf().length()+ "\n\n");					
				}
				wr.append("Longitud  Media de la fuente  "+longitud+ "\n\n");
				wr.append("Entropia  de la fuente  "+entropia+" bits."+"\n\n");
				wr.close();
				bw.close();
			}catch(IOException e){};		
	}
	
	
	//Para el tp 2
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public Hashtable<Integer,String> getSimbolosHufman( Nodo raiz){		
		Vector<Nodo> resultadoHojas= ObtenerHojas(raiz);
		Hashtable<Integer,String>  resTonoSimbolo= new Hashtable<Integer,String>();
		for (Nodo nodo : resultadoHojas) {
			int pos=0;
			pos=Integer.parseInt((String)nodo.getSimbolo()); //convierto el String a int
			String valorHuffman=nodo.getValHuf();
			resTonoSimbolo.put(pos, valorHuffman);
		}
		return resTonoSimbolo;
	}	
}
