package TP1_TeoriaInformacion;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class CanalConMatrizTransicion extends Canal{

	private Double[][] matrizTransicionAcumulada,matrizTransicion; 
	
	public CanalConMatrizTransicion(String ruta,Double[][] matrizTrans,Hashtable<Integer,Integer> hashOrigen, Hashtable<Integer,Integer> hashDestino) {
		this.matrizTransicion=matrizTrans;
		inicializarEstructuras(matrizTrans.length);
		generarAcumuladaTrans();
		
		Leer_Imagen imagenOrigen= new Leer_Imagen(ruta);
		imagenOrigen.leerImagen();	
		int altoOrigen=imagenOrigen.getAlto_imagen();
		int anchoOrigen=imagenOrigen.getAncho_imagen();
		total=altoOrigen * anchoOrigen;
		PosSimboloMatrizOrigen=hashOrigen;
		PosSimboloMatrizDestino=hashDestino;
		
		escribirImagen(imagenOrigen, anchoOrigen, altoOrigen);
	}
	
	public void generarAcumuladaTrans(){
		double suma=0;
		for(int i=0; i< matrizTransicion.length;i++){  
			for (int j = 0; j < matrizTransicion[i].length; j++) {
				suma+=matrizTransicion[i][j];
				matrizTransicionAcumulada[i][j]=suma;
			}
		suma=0;
		}		
	}
	
	private void escribirImagen(Leer_Imagen ImagenOrigen,int anchoOrigen,int altoOrigen){
		BufferedImage img = new BufferedImage(anchoOrigen,altoOrigen,BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i <altoOrigen; i++) {
			for (int j = 0; j < anchoOrigen; j++) {			
				int columna=PosSimboloMatrizOrigen.get(ImagenOrigen.getSimbolo(j, i));
				int fila=getSalidaCanal(columna);
				Color c;
				int simboloSalida=getSimboloHash(fila);
				c=new Color(simboloSalida,simboloSalida,simboloSalida);
				img.setRGB(j, i, c.getRGB());
				matrizConjunta[columna][fila]++;
				marginalX[columna]++;
				marginalY[fila]++;
			}
		}
		obtenerCondicionales();
		try {
	        ImageIO.write(img, "bmp", new File("resources/canales1salida.bmp"));            
	    } catch (IOException ex) {
	        System.out.println("Error!!! No se pudo guardar la imagen en el destino: "+"resources/canales1salida");
	        System.exit(0);
	    }			
	}
	
	private int getSimboloHash(int posicion){
		int simboloSalida=0;
		for (int key : PosSimboloMatrizDestino.keySet()) {
			if(posicion==PosSimboloMatrizDestino.get(key))
				simboloSalida=key;
		}
		return simboloSalida;
	}
	
	private int getSalidaCanal(int posicionEntrada){
		double nroRandom=Math.random();
		for (int posicionSalida = 0; posicionSalida < matrizTransicionAcumulada.length; posicionSalida++) {
			if(nroRandom<matrizTransicionAcumulada[posicionEntrada][posicionSalida])
					return posicionSalida;
		}		
		return -1;
	}

	protected void inicializarEstructuras(int n) {
		matrizConjunta=new Double[n][n];
		matrizTransicionAcumulada=new Double[n][n];
		matrizCondicionalX=new Double[n][n];
		matrizCondicionalY=new Double[n][n];
		marginalX=new Double[n];
		marginalY=new Double[n];
		for(int i=0;i<n;i++){
			for (int j = 0; j < n; j++) {
				matrizConjunta[i][j]=(double)0;
				matrizTransicionAcumulada[i][j]=(double) 0; //Para el 2
			}
			marginalX[i]=(double) 0;
			marginalY[i]=(double) 0;
		}
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
