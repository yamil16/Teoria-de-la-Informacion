package TP1_TeoriaInformacion;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;

public class Decodificador {

	private int ancho,alto;	
	private String codificacionBit;
	private Muestreo_Computacional huffman;
	private String DestinorutaImagen;
	private String OrigenArchivoComprimido;	
	private Vector<Nodo> nodos;
	private Nodo raiz;
	
	public Decodificador(String DestinorutaImagen, String OrigenArchivoComprimido) {
		super();
		this.ancho=0;
		this.alto = 0;		
		this.codificacionBit="";
		this.huffman=new Muestreo_Computacional(""); //no usaria DestinorutaImagen
		this.nodos=new Vector<Nodo>();
		this.raiz=null;
		this.OrigenArchivoComprimido = OrigenArchivoComprimido;
		this.DestinorutaImagen = DestinorutaImagen;		
	}
	

public void setDestinorutaImagen(String destinorutaImagen) {
		DestinorutaImagen = destinorutaImagen;
	}


	public void setOrigenArchivoComprimido(String origenArchivoComprimido) {
		OrigenArchivoComprimido = origenArchivoComprimido;
	}


	//Huffman------------------------------------------------------------------------------------------------------------------
	public void leerArchivoComprimidoHuffman() {
		DataInputStream dis;
		try {
			 dis=new DataInputStream(new FileInputStream(OrigenArchivoComprimido));		
			codificacionBit="";
			alto=dis.readInt();
			ancho=dis.readInt();
			int cantTonos=dis.readInt();
			for (int i = 0; i < cantTonos; i++) {
				int tono=dis.readInt();
				double probabilidad=dis.readDouble();
				Nodo nodo=new Nodo(probabilidad,Integer.toString(tono));
				nodos.addElement(nodo);
			}
			char c='0';
			while(c!=':'){
				c=dis.readChar();
				String aux=Integer.toBinaryString(c);
				int diferencia= 16-aux.length();
				for (int j = 0; j < diferencia; j++) {
					aux="0"+aux;
				}
				codificacionBit=codificacionBit+aux;
			}
		} catch(IOException e){};
	}

	
	public Vector<Integer> decodificacionBits(){		
		Vector<Integer> resultado=new Vector<Integer>();
		Nodo nodo=raiz;
		for (int i = 0; i < codificacionBit.length(); i++) {
			if(codificacionBit.charAt(i) == '1') 			//rama derecha
				nodo=nodo.getDer();
			else											//rama izquierda
				nodo=nodo.getIzq();
			if(nodo.getDer()==null && nodo.getIzq()==null){ //es hoja				
				resultado.add(Integer.parseInt((String)nodo.getSimbolo()));
			nodo=raiz;
			}
		}
		return resultado;
	}
	
	
	public void DecodificadorHuffman(){			
		leerArchivoComprimidoHuffman(); //obtuvimos los nodos		
		huffman.setNodos(nodos); //ahora obtengo los nodos que hacia antes armarvectornodos
		raiz=huffman.ArmarArbol(); //obtenemos la raiz que se va utilizar para poder decodificar los bits		
		BufferedImage img = new BufferedImage(ancho,alto,BufferedImage.TYPE_BYTE_GRAY);		
		Vector<Integer> resultado;
		resultado=decodificacionBits();
		int cont=0;
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
				if(cont<resultado.size()){
					Color c=new Color(resultado.get(cont),resultado.get(cont),resultado.get(cont));
					img.setRGB(j, i, c.getRGB());
					cont++;
				}
			}
		}		
		try {
            ImageIO.write(img, "bmp", new File(this.DestinorutaImagen));            
        } catch (IOException ex) {
            System.out.println("Error!!! No se pudo guardar la imagen en el destino: "+this.DestinorutaImagen);
            System.exit(0);
        }			
	}	
	
//Run lenght------------------------------------------------------------------------------------------------------------------	
	
	public Vector<Integer> generarVectorTonos(DataInputStream dis){
		Vector<Integer> retorno=new Vector<Integer>();
		int tono=0,cont=1;
		while(tono != -1){
			try {
				tono=dis.read();
				cont=dis.read();				
				for (int i = 1; i <= cont; i++) {
					retorno.addElement(tono);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retorno;
	}
	
	public void DecodificacionRunLength(){			
		DataInputStream dis;
		try {
			dis=new DataInputStream(new FileInputStream(this.OrigenArchivoComprimido)); 
			alto=dis.readInt();
			ancho=dis.readInt();
			BufferedImage img = new BufferedImage(ancho,alto,BufferedImage.TYPE_BYTE_GRAY);			
			Vector<Integer> resultado=generarVectorTonos(dis);			
			int cont=0;
			for (int i = 0; i < alto; i++) {
				for (int j = 0; j < ancho; j++) {
					if(cont<resultado.size()){
						Color c=new Color(resultado.get(cont),resultado.get(cont),resultado.get(cont));
						img.setRGB(j, i, c.getRGB());
						cont++;
					}
				}
			}			
            ImageIO.write(img, "bmp", new File(this.DestinorutaImagen));
        } catch (IOException ex) {
        	 System.out.println("Error!!! No se pudo guardar la imagen en el destino: "+this.DestinorutaImagen);
            System.exit(0);
        }
	}
}
