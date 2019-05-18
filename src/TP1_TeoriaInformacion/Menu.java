package TP1_TeoriaInformacion;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Menu {
	
	static Leer_Imagen leer;
	static Double LongMediaA;
	static Double LongMediaB;
	static Double EntropiaA;
	static Double EntropiaB;
	static Double EntropiaC;
	
	Vector<Nodo> res;
	 static Muestreo_Computacional muestreo;
	 @SuppressWarnings("deprecation")
	public static void interfaz() {
		 JFrame panel = new JFrame ("Teoría de la Información");
         panel.setSize(350, 260);
         panel.setLocationRelativeTo(null);          
         JTextArea areaTexto = new JTextArea();
         areaTexto.setForeground(Color.darkGray);
         areaTexto.setFont(new Font("TimesRoman",Font.BOLD,18));
         areaTexto.setLineWrap(true);
         areaTexto.setWrapStyleWord(true);
         areaTexto.setEditable(false);
         JScrollPane scroll = new JScrollPane(areaTexto);
         scroll.setBounds(10,50,400,300);         
       /*  areaTexto.append("Primer Entrega - Codificación de la fuente" + "\n\n" );  
         areaTexto.append("Longitud Media de Fuente sin Memoria por simbolo: "+ LongMediaA +"\n\n" );
         areaTexto.append("Longitud Media de Fuente con Memoria Orden 2 por simbolo: "+ LongMediaB/2 +"\n\n" );
         areaTexto.append("Entropia de la Fuente sin Memoria: "+ EntropiaA +" bits"+"\n\n" );
         areaTexto.append("Entropia de la Fuente con Memoria con respecto a 2 simbolos: "+ EntropiaC +" bits"+"\n\n" );
         areaTexto.append("Entropia de la Fuente con Memoria con respecto a 1 simbolo: "+ EntropiaB*(-1) +" bits" +"\n\n" );
         */
         areaTexto.append("Segunda Entrega - Compresion de Datos" + "\n\n" );  
         areaTexto.append("\nAlumnos:" + "\n" + "Luti, Alberto. " + "\n" + "Lacoste, Yamil.");
         
          panel.getContentPane().add(scroll, BorderLayout.CENTER); 
        panel.show();
	}
	

	public static void main(String[] args) {
		//TP1
/*		
		muestreo = new Muestreo_Computacional("/resources/stars_8.bmp");
		muestreo.ArmarVectorNodos();
		Nodo raiz1=muestreo.ArmarArbol();
		Vector<Nodo> res1= muestreo.ObtenerHojas(raiz1);
		LongMediaA=muestreo.LongitudMedia(res1);
		EntropiaA=muestreo.Entropia();
		muestreo.GuardarEnArchivo(res1,LongMediaA,EntropiaA, "Teoria Resultados a)");
		//fin paso uno
		muestreo.armarVectorNodosOrdenDos();
		Nodo raiz2=muestreo.ArmarArbol();
		Vector<Nodo> res2= muestreo.ObtenerHojas(raiz2);
		LongMediaB=muestreo.LongitudMedia(res2);
		EntropiaB=muestreo.EntropiaCondicional();
		EntropiaC=muestreo.EntropiaOrdenDos();
		muestreo.GuardarEnArchivo(res2,LongMediaB,EntropiaB*-1, "Teoria Resultados b)");
		 interfaz();
*/		
		
		//TP2
		
		//compresion por Huffman
		/*
		Codificador codificador=new Codificador("resources/stars_8.bmp", "ArchivoComprimidoHufmman");
		codificador.codificadorHufman(); //genera el archivo comprimido segun la ruta de la imagen leida		
		Decodificador decodificador=new Decodificador("resources/starsDescomprimidoHuffman_8.bmp", "ArchivoComprimidoHufmman");
		decodificador.DecodificadorHuffman(); //genera la imagen con los datos del archivo comprimido	
		
		//compresion por Run lenght
		codificador.setDestinoArchivo( "ArchivoComprimidoRunLenght");
		codificador.codificadorRunLength();
		decodificador.setOrigenArchivoComprimido("ArchivoComprimidoRunLenght");
		decodificador.setDestinorutaImagen("resources/starsDescomprimidoRunLenght_8.bmp");
		decodificador.DecodificacionRunLength();
		 interfaz();
	*/
		//TP3
		
	/*	CanalSinMatrizTransicion canalSinMatriz = new CanalSinMatrizTransicion("resources/stars_8.bmp","resources/stars_8_noisy.bmp");
		canalSinMatriz.realizarCalculos("1");
		canalSinMatriz.VaciarArchivo("Resultados Tp3 ejercicio 1");
		canalSinMatriz.GuardarEnArchivo("Resultados Tp3 ejercicio 1", "1");
		
		
		CanalConMatrizTransicion canalConMatriz=new CanalConMatrizTransicion("resources/nebula.bmp", canalSinMatriz.getMatrizTransicion(),canalSinMatriz.getHashOrigen(),canalSinMatriz.getPosSimboloMatrizDestino());
		canalConMatriz.realizarCalculos("2");
		canalConMatriz.VaciarArchivo("Resultados Tp3 ejercicio 2");
		canalConMatriz.GuardarEnArchivo("Resultados Tp3 ejercicio 2", "2");
		
		Leer_Imagen imagen=new Leer_Imagen("resources/stars_8.bmp");
		imagen.leerImagen();
		//el valor 50 pasado por parametro es el minimo de iteraciones de la simulacion
		//inciso 3 a)
		CanalSimulacion canal=new CanalSimulacion(canalSinMatriz.getMatrizTransicion(), 0.000001, 10, imagen.getDistribucionProbabilidad(),10);
		int aux=1;
		canal.VaciarArchivo("Resultados Tp3 ejercicio 3a");
		for (int i = 0; i < 5; i++) {
			aux=aux*10;
			canal.setMaximo(aux);
			canal.realizarSimulacion();
			canal.realizarCalculos("3-Maximo(+"+aux+")");
			canal.GuardarEnArchivo("Resultados Tp3 ejercicio 3a", "3-Maximo(+"+aux+")");
		}
		//inciso 3 b)
		canal.setMaximo(1000000);
		canal.realizarSimulacion();
		canal.realizarCalculos("3b");
		canal.VaciarArchivo("Resultados Tp3 ejercicio 3b");
		canal.GuardarEnArchivo("Resultados Tp3 ejercicio 3b", "3b");		

	*/
		//Proyecto final
		
		//inciso 1
		//huffman para nebula, fuente sin memoria
		muestreo = new Muestreo_Computacional("resources/nebula.bmp");
		muestreo.ArmarVectorNodos();
		Nodo arbolNebula=muestreo.ArmarArbol();
		Vector<Nodo> simbolosNebula= muestreo.ObtenerHojas(arbolNebula);
		LongMediaA=muestreo.LongitudMedia(simbolosNebula);
		EntropiaA=muestreo.Entropia();
		muestreo.GuardarEnArchivo(simbolosNebula,LongMediaA,EntropiaA, "Nebula Huffman sin memoria");

		
		//huffman para nebula, fuente markoviana
		muestreo.armarVectorNodosOrdenDos();
		Nodo arbolNebulaMarkoviano=muestreo.ArmarArbol();
		Vector<Nodo> simbolosNebulaMarkoviano= muestreo.ObtenerHojas(arbolNebulaMarkoviano);
		LongMediaB=muestreo.LongitudMedia(simbolosNebulaMarkoviano);
		EntropiaB=muestreo.EntropiaCondicional();
		EntropiaC=muestreo.EntropiaOrdenDos();
		muestreo.GuardarEnArchivo(simbolosNebulaMarkoviano,LongMediaB,EntropiaC, "Nebula Huffman con memoria");
		
		//huffman para stars_8, fuente sin memoria
		muestreo = new Muestreo_Computacional("resources/stars_8.bmp");
		muestreo.ArmarVectorNodos();
		Nodo arbolStars=muestreo.ArmarArbol();
		Vector<Nodo> simbolosStars= muestreo.ObtenerHojas(arbolStars);
		LongMediaA=muestreo.LongitudMedia(simbolosStars);
		EntropiaA=muestreo.Entropia();
		muestreo.GuardarEnArchivo(simbolosStars,LongMediaA,EntropiaA, "Stars Huffman sin memoria");

		//huffman para stars_8, fuente markoviana
		muestreo.armarVectorNodosOrdenDos();
		Nodo arbolStarsMarkoviano=muestreo.ArmarArbol();
		Vector<Nodo> simbolosStarsMarkoviano= muestreo.ObtenerHojas(arbolStarsMarkoviano);
		LongMediaB=muestreo.LongitudMedia(simbolosStarsMarkoviano);
		EntropiaB=muestreo.EntropiaCondicional();
		EntropiaC=muestreo.EntropiaOrdenDos();
		muestreo.GuardarEnArchivo(simbolosNebulaMarkoviano,LongMediaB,EntropiaC, "Stars Huffman con memoria");

		System.out.println("termino inciso 1");
		
		//inciso 2
		//compresion por Huffman, para stars_8
		Codificador codificador=new Codificador("resources/stars_8.bmp", "ArchivoComprimidoHuffmanStars");
		codificador.codificadorHufman(); //genera el archivo comprimido segun la ruta de la imagen leida		
		Decodificador decodificador=new Decodificador("resources/starsDescomprimidoHuffmanStars.bmp", "ArchivoComprimidoHuffmanStars");
		decodificador.DecodificadorHuffman(); //genera la imagen con los datos del archivo comprimido	
		
		//compresion por Huffman, para nebula
		codificador.setOrigenArchivo("resources/nebula.bmp");
		codificador.setDestinoArchivo( "ArchivoComprimidoHuffmanNebula");
		codificador.codificadorHufman(); //genera el archivo comprimido segun la ruta de la imagen leida		
		decodificador.setOrigenArchivoComprimido("ArchivoComprimidoHuffmanNebula");
		decodificador.setDestinorutaImagen("resources/starsDescomprimidoHuffmanNebula.bmp");
		decodificador.DecodificadorHuffman(); //genera la imagen con los datos del archivo comprimido	
		
		System.out.println("termino inciso 2");
		
	//inciso 4
		CanalSinMatrizTransicion canalSinMatriz = new CanalSinMatrizTransicion("resources/stars_8.bmp","resources/stars_8_noisy.bmp");
		Leer_Imagen imagen=new Leer_Imagen("resources/stars_8.bmp");
		imagen.leerImagen();
		CanalSimulacion canal=new CanalSimulacion(canalSinMatriz.getMatrizTransicion(), 0.000001, 100, imagen.getDistribucionProbabilidad(),10);
		canal.setMaximo(-1);//esto sirve para que la simulacion no este condicionada por un maximo, mirar metodo converge
		canal.VaciarArchivo("datos para grafico");
		canal.realizarSimulacion();
		canal.realizarCalculos("3b");
		
		System.out.println("termino inciso 4");

	}
	
}


