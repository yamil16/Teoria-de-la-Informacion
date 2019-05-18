package TP1_TeoriaInformacion;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class Codificador {
	private Muestreo_Computacional huffman;
	private String rutaImagen;
	private String destinoArchivo;
	private Nodo raiz;
	public Codificador(String rutaImagen, String destinoArchivo) {
		super();
		huffman= new Muestreo_Computacional(rutaImagen);
		this.rutaImagen=rutaImagen;
		this.destinoArchivo=destinoArchivo;
		huffman.ArmarVectorNodos();
		raiz=huffman.ArmarArbol();
				
	}
	
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public void setDestinoArchivo(String destinoArchivo) {
		this.destinoArchivo = destinoArchivo;
	}
	
	public void setOrigenArchivo(String origenArchivo) {
		this.rutaImagen = origenArchivo;
	}
//Huffman------------------------------------------------------------------------------------------------------------------
	public void generarCodificacion(DataOutputStream dos){			
		Vector<Integer>pixeles; //es un vector con cada tono		
		Leer_Imagen leer= new Leer_Imagen(rutaImagen);
		leer.leerImagen();
		pixeles= leer.getPixel(); 
		Hashtable<Integer,String> simboloTono; // obtengo para cada tono el simbolo correspondiente del huffman
		simboloTono=huffman.getSimbolosHufman(raiz); 
		char temp=0; //variable auxiliar donde se va complentando los char
		int cantDigitosChar=0; //contar la cantidad de digitos del char
		for (int i = 0; i < pixeles.size(); i++) {
			int tono=pixeles.get(i);
			String codSimbolo=simboloTono.get(tono); //tengo la codificacion de hufman para ese tono
			for (int j = 0; j < codSimbolo.length(); j++) {			
				temp=(char) (temp << 1);
				if( codSimbolo.charAt(j)=='1'){				
					temp=(char) (temp|1);					
				}					
				cantDigitosChar++;				
				if (cantDigitosChar==16){							
					try {
						dos.writeChar(temp);
					} catch (IOException e) {						
						e.printStackTrace();
					}
					temp=0;
					cantDigitosChar=0;					
				}				
			}			
		}
		if((cantDigitosChar<16) && (cantDigitosChar!=0)){
			temp=(char) (temp<<(16-cantDigitosChar));
			try {
				dos.writeChar(temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			dos.writeChar(':');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void codificadorHufman(){		
		Vector<Nodo> hojas=huffman.ObtenerHojas(raiz);
		Leer_Imagen leer= new Leer_Imagen(rutaImagen);
		leer.leerImagen();
		try{
			DataOutputStream dos=new DataOutputStream(new FileOutputStream(this.destinoArchivo));
			dos.writeInt(leer.getAlto_imagen());
			dos.writeInt(leer.getAncho_imagen());
			dos.writeInt(hojas.size());
			for (Nodo nodo : hojas) {
				dos.writeInt(Integer.parseInt(nodo.getSimbolo()));
				dos.writeDouble((nodo.getValor()));//le pido el tono de gris y su probabilidad de ocurrencia				
			}			
			generarCodificacion(dos);						
		}catch(IOException e){};
	}
//Run lenght------------------------------------------------------------------------------------------------------------------		
	public void codificadorRunLength(){
		Vector<Integer>pixeles; //es un vector con cada tono		
		Leer_Imagen leer= new Leer_Imagen(rutaImagen);
		leer.leerImagen();		
		pixeles= leer.getPixel(); //obtener vector de tonos leyendo la imagen, es decir la matriz pasada a vector con su tono correspondiente
		DataOutputStream dos;
		try{
			dos=new DataOutputStream(new FileOutputStream(this.destinoArchivo));
			dos.writeInt(leer.getAlto_imagen());
			dos.writeInt(leer.getAncho_imagen());
			int cont=1,i=1;		
			if(pixeles.size()>=2){
				while(i<pixeles.size()){
					if(pixeles.elementAt(i)==pixeles.elementAt(i-1)){
						cont++;
						i++;
					}
					else{
						dos.write(pixeles.elementAt(i-1));
						dos.write(cont);
						i++;
						cont=1;
					}
				}
				dos.write(pixeles.elementAt(i-1));
				dos.write(cont);
				dos.writeInt(-1);
				dos.writeInt(-1);
			}
		}
		catch(IOException e){};
	}
}