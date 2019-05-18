package TP1_TeoriaInformacion;


//Abro imagen, obtengo la lista de tonos y su cantidad. Seguidamente, armo el vector de probabilidad
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.awt.Color;
import javax.imageio.ImageIO;

public class Leer_Imagen {
	private int ancho_imagen;
	private int alto_imagen;
	private BufferedImage img = null;
	private String rutaImagen;
	private Vector<Integer> simbolos;
	
 	public Leer_Imagen(String rutaImagen) {
		img = new BufferedImage(553,395,BufferedImage.TYPE_INT_RGB);
		this.ancho_imagen=0;
		this.alto_imagen=0;
		this.rutaImagen=rutaImagen;
		this.simbolos=new Vector<Integer>();
	}
	
	public void leerImagen(){		
        try {
            img = ImageIO.read(new File(rutaImagen));
            this.setAlto_imagen(img.getHeight());
            this.setAncho_imagen(img.getWidth());
        } catch (IOException ex) {
            System.out.println("No se pudo leer la imagen en la direccion: "+rutaImagen);
            System.exit(0);
        }
    }
	
	public void inicializarEstructuras(Vector<Double> conjunta,Double[][] matrizTransicion, int n){
		for (int i = 0; i < n; i++) {
			conjunta.add((Double)0.0);
			for (int j = 0; j < n; j++) {
				matrizTransicion[i][j]=0.0;
			}
		}
	}

	public void ActualizarMatrizTransicionProb(int val1,int val2,Vector<Double> conjunta,Double[][] matrizTransicion,  Vector<Integer> tonos){
		for(int i=0;i<tonos.size();i++){	  
			if(tonos.get(i)==val2)
      			for(int j=0;j<tonos.size();j++){
      				if(tonos.get(j)==val1){
      					conjunta.set(j,conjunta.get(j)+1);					
      					matrizTransicion[i][j]=matrizTransicion[i][j]+1;
      				}
      			}
		}
	}

	public void getProbabilidadCondicional(Vector<Double> conjunta,Double[][] matrizTransicion, int n){
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <n; j++) {
				matrizTransicion[i][j]=matrizTransicion[i][j]/conjunta.get(j);
			}
		}
	}
	
	public Double[][] getMatrizTransicionProbabilidades(int n){
		Vector<Double> conjunta=new Vector<Double>();
		Vector<Integer> tonos=new Vector<Integer>();
		Double[][] matrizTransicion=new Double[n][n];
		int i=0;
		int j=0;
		inicializarEstructuras(conjunta,matrizTransicion,n);
		boolean ultimaColumna=false;
			while ( i < img.getHeight()){//height=alto
				while (j < img.getWidth()){//widght=ancho
	            	 Color c1= new Color(img.getRGB(j, i));
	            	  int escala1=c1.getRed();
	            	  if(!tonos.contains(escala1))
	            		  tonos.add(escala1);
	            	  int escala2;
	            	  if(j==img.getWidth()-1){//celda(in,j) ultima columna
	            		  ultimaColumna=true;
	            		  if(i==img.getHeight()-1){//ultima celda de la matrizTransicion
	            			  escala2=escala1;
	            		  }
	            		  else{
	            			  Color c2=new Color(img.getRGB(0, i+1));
	            			  escala2=c2.getRed();
	            			  if(!tonos.contains(escala2))
	    	            		  tonos.add(escala2);
	            		  }
	            	  }
	            	  else{//caso comun celda(i,j)
	            		  Color c2=new Color(img.getRGB(j+1, i));
	            		  escala2=c2.getRed();
	            		  if(!tonos.contains(escala2))
    	            		  tonos.add(escala2);
	            	  }
	            	  ActualizarMatrizTransicionProb(escala1,escala2,conjunta,matrizTransicion, tonos);
	            	  j=j+2;	  
	            }
				i=i+1;
				if(ultimaColumna){
					ultimaColumna=false;
					j=1;
				}
				else
					j=0;
			}
			getProbabilidadCondicional(conjunta,matrizTransicion,n);
			return matrizTransicion;
	}
	
	public  Vector<Double> getDistribucionProbabilidad() {
		Vector<Integer> Canttonos= new Vector<Integer>();
		
		   for (int i=0; i < img.getWidth(); i++){
	            for (int j=0; j < img.getHeight(); j++){
	            	 Color c= new Color(img.getRGB(i, j));
	            	  int escala=(c.getRed()+c.getGreen()+c.getBlue())/3;
	            	  if(!simbolos.contains(escala)){
	            		  simbolos.add(escala);
	            		  Canttonos.add(1);
	            	  }
	            	  else{
	            		   for(int t=0;t<Canttonos.size();t++){	  	            			 
	  	            		if  (simbolos.get(t)==escala){     
	  	            			Canttonos.set(t, Canttonos.get(t)+1);
	  	            		}	
	            		  } 
	            	  }
	             }
		   }
			Vector<Double> DistProb= new Vector<Double>();
			for(int t=0; t<simbolos.size();t++){		
				double valor=(double)Canttonos.get(t)/(img.getWidth()*img.getHeight());
				DistProb.add(t, valor);
			}
		   return DistProb;	}
	
	public Vector<Integer> getSimbolos(){
		return simbolos;
	}
	
	//Para el TP2
	
 	public Vector<Integer> getPixel(){
		Vector<Integer> pixeles= new Vector<Integer>();
		for (int i=0; i < img.getHeight(); i++){
            for (int j=0; j < img.getWidth(); j++){
            	 Color c= new Color(img.getRGB(j, i));
            	  int escala=(c.getRed()+c.getGreen()+c.getBlue())/3;
            	  pixeles.add(escala);
            }
		}
		return pixeles;
	}

	public int getAncho_imagen() {
		return ancho_imagen;
	}

	public void setAncho_imagen(int ancho_imagen) {
		this.ancho_imagen = ancho_imagen;
	}

	public int getAlto_imagen() {
		return alto_imagen;
	}

	public void setAlto_imagen(int alto_imagen) {
		this.alto_imagen = alto_imagen;
	}

	public int getSimbolo(int x,int y){
		return new Color(img.getRGB(x, y)).getBlue();
	}
}


