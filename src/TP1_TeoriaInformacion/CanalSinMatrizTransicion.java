
package TP1_TeoriaInformacion;

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

public class CanalSinMatrizTransicion extends Canal{
	

	
	//PARTE 2
	/*en cada celda [origen][destino] guarda la suma de [origen][0 hasta destino], varia entre 0 y 1*/

	public CanalSinMatrizTransicion(String rutaImagenOriginal,String rutaImagenDestino) {

		/*leo la imagen original*/
		Vector<Integer> simbolosImagenOriginal = new Vector<Integer>();
		Leer_Imagen imagenOriginal= new Leer_Imagen(rutaImagenOriginal);
		imagenOriginal.leerImagen();
		/*obtengo los simbolos que aparecen en la imagen original*/
		imagenOriginal.getDistribucionProbabilidad();
		simbolosImagenOriginal=imagenOriginal.getSimbolos();
		/*genero un hash con el simbolo-posicion para las estructuras*/
		PosSimboloMatrizOrigen=this.getSimbolosPosicionMatriz(simbolosImagenOriginal);
		
		/*repito lo anterior pero ahora para la imagen destino*/
		Vector<Integer> simbolosImagenDestino= new Vector<Integer>();
		Leer_Imagen imagenDestino= new Leer_Imagen(rutaImagenDestino);
		imagenDestino.leerImagen();		
		imagenDestino.getDistribucionProbabilidad();	
		simbolosImagenDestino=imagenDestino.getSimbolos();
		PosSimboloMatrizDestino=this.getSimbolosPosicionMatriz(simbolosImagenDestino);
		
		/*inicializo las estructuras*/
		inicializarEstructuras(simbolosImagenDestino.size());
	
		MatrizResultante(imagenOriginal,imagenDestino);
		obtenerCondicionales();
	}
//--------------------------------------------------LLENADO DE ESTRUCTURAS	
	
	protected void inicializarEstructuras(int n){
		matrizConjunta=new Double[n][n];
		matrizCondicionalX=new Double[n][n];
		matrizCondicionalY=new Double[n][n];
		marginalX=new Double[n];
		marginalY=new Double[n];
		total=0;
		for(int i=0;i<n;i++){
			for (int j = 0; j < n; j++) {
				matrizConjunta[i][j]=(double)0;
			}
			marginalX[i]=(double) 0;
			marginalY[i]=(double) 0;
		}
	}
	
	public  void MatrizResultante(Leer_Imagen imagenOrigen,Leer_Imagen imagenDestino){		
		/*cada ves que aparece un origen-destino, increimento su contador en las estructuras*/
		for (int i = 0; i < imagenOrigen.getAlto_imagen(); i++) {
			for (int j = 0; j < imagenOrigen.getAncho_imagen(); j++) {
				int posicionSimboloOrigen=PosSimboloMatrizOrigen.get(imagenOrigen.getSimbolo(j, i));
				int posicionSimboloDestino=PosSimboloMatrizDestino.get(imagenDestino.getSimbolo(j, i));
				matrizConjunta[posicionSimboloOrigen][posicionSimboloDestino]=matrizConjunta[posicionSimboloOrigen][posicionSimboloDestino]+1;
				
				marginalX[posicionSimboloOrigen]=marginalX[posicionSimboloOrigen]+1;
				marginalY[posicionSimboloDestino]=marginalY[posicionSimboloDestino]+1;
			}
		}
		total=imagenOrigen.getAlto_imagen() * imagenOrigen.getAncho_imagen();
	}
	
	public Double[][] getMatrizTransicion(){
		return matrizCondicionalX;
	}
	
	public Hashtable<Integer,Integer> getHashOrigen(){
		return PosSimboloMatrizOrigen;
	}

	
	public void imprimirCondicional(PrintWriter wr) {
		wr.println("");
		for(int i=0;i<matrizConjunta.length;i++){
			for (int j = 0; j < matrizConjunta.length; j++) {			
				wr.append(matrizConjunta[i][j]+" ");					
			}
			wr.println("");			
		}
				
		
	}
}
