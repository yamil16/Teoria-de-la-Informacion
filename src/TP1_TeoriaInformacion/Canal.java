package TP1_TeoriaInformacion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;
import java.util.Vector;

public abstract class Canal {

	protected Hashtable<Integer,Integer> PosSimboloMatrizOrigen;
	protected Double[][] matrizConjunta,matrizCondicionalX,matrizCondicionalY;
	protected Double[] marginalX,marginalY;
	private String Calculos;
	protected int total;
	
	/*guarda la posicion de un simbolo de destino en las estructuras*/
	protected Hashtable<Integer,Integer> PosSimboloMatrizDestino;

	public Hashtable<Integer, Integer> getPosSimboloMatrizDestino() {
		return PosSimboloMatrizDestino;
	}

	protected abstract void inicializarEstructuras(int n);
	
	protected Hashtable<Integer,Integer> getSimbolosPosicionMatriz(Vector<Integer> simbolos){		
		
		Hashtable<Integer,Integer>  resSimbolo= new Hashtable<Integer,Integer>();
		for (int i = 0; i < simbolos.size(); i++) {
			resSimbolo.put(simbolos.elementAt(i), i);
		}		
		return resSimbolo;
	}
	
	protected void obtenerCondicionales(){
		/*cada simbolo origen*/
		for(int i=0; i< matrizConjunta.length;i++){  
			/*cada simbolo destino*/
			for (int j = 0; j < matrizConjunta[i].length; j++) {
				/*por columnas da 1*/
				if(marginalX[i]!=0)
					matrizCondicionalX[i][j]=matrizConjunta[i][j]/marginalX[i];
				/*por filas da 1*/
				if(marginalY[j]!=0){
					matrizCondicionalY[i][j]=matrizConjunta[i][j]/marginalY[j];
				}
			}
		}
	}

	public Double obtenerRuido(){
		Double resultado=0.0;
		for(int i=0; i<matrizCondicionalX.length;i++){
			Double suma=0.0;
			Double prob=marginalX[i]/total;
			for (int j = 0; j < matrizCondicionalX[i].length; j++) {
				if (matrizCondicionalX[i][j]!=(double)0)
					suma+=-matrizCondicionalX[i][j]*(Math.log10(matrizCondicionalX[i][j])/Math.log10(2));				
			}
			resultado+=suma*prob;
		}
		return resultado;
	}
	
	public Double obtenerPerdida(){
		Double resultado=0.0;
		for(int i=0; i<matrizCondicionalY.length;i++){
			Double suma=0.0;
			Double prob=marginalY[i]/total;			
			for (int j = 0; j < matrizCondicionalY[i].length; j++) {
				if (matrizCondicionalY[j][i]!=(double)0){
					suma+=-matrizCondicionalY[j][i]*(Math.log10(matrizCondicionalY[j][i])/Math.log10(2));				
				}
			}
			resultado+=suma*prob;
		}
		return resultado;
	}
	
	protected Double EntropiaY(){
		Double resultado=0.0;
		for(int i=0;i<marginalY.length;i++){
			if(marginalY[i]!=0)
				resultado+=-marginalY[i]/total*(Math.log10(marginalY[i]/total)/Math.log10(2));
		}
		return resultado;		
	}
	
	public Double obtenerInformacionMutua(){
		return this.EntropiaY()-this.obtenerRuido();
	}
	
	public void realizarCalculos(String i){
		double ruido=obtenerRuido();
		double perdida=obtenerPerdida();
		double infoMutua=obtenerInformacionMutua();
		Calculos="Ruido "+ruido+", Perdida "+ perdida+ ", Informacion mutua "+infoMutua;		
	}

	public abstract void imprimirCondicional(PrintWriter wr);
	
	public void GuardarEnArchivoExistente(String nombre){
		try {
			@SuppressWarnings({ "resource", "unused" })
			FileWriter fichero = new FileWriter(nombre,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void GuardarEnArchivo2(String nombre,String i,Double ruido,Double perdida,Double info){		
		File f;
		f=new File(nombre);		
		try{
			FileWriter w = new FileWriter(f,true);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);
			double ruido2=new BigDecimal(ruido.toString()).setScale(2,RoundingMode.HALF_UP).doubleValue();
			double perdida2=new BigDecimal(perdida.toString()).setScale(2,RoundingMode.HALF_UP).doubleValue();
			double info2=new BigDecimal(info.toString()).setScale(2,RoundingMode.HALF_UP).doubleValue();
			wr.write(","+ruido2+","+perdida2+","+info2);
			wr.println(" ");
			wr.close();
			bw.close();
		}catch(IOException e2){};	
		
	}
	
	public void GuardarEnArchivo(String nombre,String i){		
		File f;
		f=new File(nombre);		
		try{
			FileWriter w = new FileWriter(f,true);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);		
			wr.write("Resultados del tp3, parte "+i+" ");
			wr.println("");
			wr.append(Calculos);
			wr.println(" ");
			wr.append("Impresion de la Matriz del canal: ");
			wr.println("");			
			imprimirCondicional(wr);
			wr.println("");
			wr.close();
			bw.close();
		}catch(IOException e2){};	
		
}
	
	public void VaciarArchivo(String nombre){
		File f;
		f=new File(nombre);
		try{
			FileWriter w = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);		
			wr.write("");
			wr.close();
			bw.close();
		}catch(IOException e2){};	
		
	}

}
