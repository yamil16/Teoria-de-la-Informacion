package TP1_TeoriaInformacion;

import java.io.PrintWriter;
import java.util.Vector;

public class CanalSimulacion extends Canal{

	private Double[][] matrizTransicion,matrizTransicionAcumulada;
	private Double[] probabilidadesOcurrenciaAcumulada;
	private double cota;
	private int minimoSimulaciones,maximoSimulaciones,contador;
	
	public int getContador() {
		return contador;
	}

	public CanalSimulacion(Double[][] matrizTrans,double cota,int minimoSimulaciones,Vector<Double>probabilidadesOcurrencia,int maximoSimulaciones) {
		matrizTransicion=matrizTrans;
		this.cota=cota;
		this.minimoSimulaciones=minimoSimulaciones;
		if(maximoSimulaciones==-1)
			this.maximoSimulaciones=maximoSimulaciones;
		else
			if(maximoSimulaciones<minimoSimulaciones)
				this.maximoSimulaciones=minimoSimulaciones;
			else
				this.maximoSimulaciones=maximoSimulaciones;
		inicializarEstructuras(probabilidadesOcurrencia.size());
		generarProbabilidadOcurrenciaAcumulada(probabilidadesOcurrencia);
		generarAcumuladaTrans();
	}

	private void generarProbabilidadOcurrenciaAcumulada(Vector<Double> probOcurrencia){
		double acumulada=0.0;
		for (int i = 0; i < probOcurrencia.size(); i++) {
			acumulada=acumulada+probOcurrencia.elementAt(i);
			probabilidadesOcurrenciaAcumulada[i]=acumulada;
		}
	}
	
	private void generarAcumuladaTrans(){
		double suma=0;
		for(int i=0; i< matrizTransicion.length;i++){  
			for (int j = 0; j < matrizTransicion[i].length; j++) {
				suma+=matrizTransicion[i][j];
				matrizTransicionAcumulada[i][j]=suma;
			}
		suma=0;
		}
	}

	private int getSimboloEntrada(){
		double nroRamdom=Math.random();
		for (int i = 0; i < probabilidadesOcurrenciaAcumulada.length; i++) {
			if(nroRamdom<probabilidadesOcurrenciaAcumulada[i])
				return i;
		}
		return probabilidadesOcurrenciaAcumulada.length-1;
	}

	private int getSalidaCanal(int posicionEntrada){
		double nroRandom=Math.random();
		for (int posicionSalida = 0; posicionSalida < matrizTransicionAcumulada.length; posicionSalida++) {
			if(nroRandom<matrizTransicionAcumulada[posicionEntrada][posicionSalida])
				if(matrizTransicion[posicionEntrada][posicionSalida]!=0){
					return posicionSalida;
				}
		}
		//si retorna -1 entonces hay problemas con transicion(todas son cero) o transicion acumulada(se calculo mal la acumulada)
		return -1;
	}

	private boolean converge(double[] estadoActual,double[] estadoAnterior,int contador){
		if(maximoSimulaciones!=-1 && contador==maximoSimulaciones){
			/*si maximo es -1 nunca converge por llegar a un nro maximo de iteraciones(caso 3b) si tiene 
			 * otro valor entonces si puede llegar a "converger" por dicha via (caso 3a)
			 * */
			return true;
		}
		else
			if(contador>minimoSimulaciones){
				for (int i = 0; i < estadoAnterior.length; i++) {
					if(Math.abs(estadoActual[i]-estadoAnterior[i])>cota){
						return false;
					}
				}
				return true;
			}
			else
				return false;
	}
	
	public void imprimirArreglo(Double[] array){
		String aImprimir="";
		double suma=0.0;
		for (int i = 0; i < array.length; i++) {
			aImprimir=aImprimir+array[i]+" ";
			suma=suma+array[i];
		}
		System.out.println(aImprimir);
		System.out.println(suma);
	}
	
	public void imprimirMatriz(Double[][] matriz){
		for (int i = 0; i < matriz.length; i++) {
			String aImprimir="";
			for (int j = 0; j < matriz.length; j++) {
				aImprimir=aImprimir+matriz[i][j]+" ";
			}
			System.out.println(aImprimir);
		}
	}

	protected void inicializarEstructuras(int n){
		matrizConjunta=new Double[n][n];
		marginalX=new Double[n];
		marginalY=new Double[n];
		matrizCondicionalX=new Double[n][n];
		matrizCondicionalY=new Double[n][n];
		matrizTransicionAcumulada=new Double[n][n];
		probabilidadesOcurrenciaAcumulada=new Double[n];
		total=0;
		for (int i=0; i<n; i++) {
			marginalX[i]=0.0;
			marginalY[i]=0.0;
			for (int j=0; j<matrizConjunta.length; j++) {
				matrizCondicionalX[i][j]=0.0;
				matrizCondicionalY[i][j]=0.0;
				matrizConjunta[i][j] = 0.0;
			}
		}
	}

	protected void setearEstructurasSimulacion(){
		total=0;
		for (int i=0; i<marginalX.length; i++) {
			marginalX[i]=0.0;
			marginalY[i]=0.0;
			for (int j=0; j<matrizConjunta.length; j++)
				matrizConjunta[i][j] = 0.0;
		}
	}
	
	public void realizarSimulacion() {
		setearEstructurasSimulacion();
		double[] estadoActual=new double[3];
		double[] estadoAnterior=new double[3];
		for (int i = 0; i < estadoActual.length; i++) {
			estadoActual[i]=0.0;
			estadoAnterior[i]=-1.0;
		}
		contador = 0;
		while (!converge(estadoAnterior, estadoActual, contador)) {
			contador++;
			int simboloEmitido = getSimboloEntrada();
			int salida = getSalidaCanal(simboloEmitido);

			/* Incrementamos en 1 las ocurrencias */
			marginalX[simboloEmitido]++;
			marginalY[salida]++;
			matrizConjunta[simboloEmitido][salida]++;
			total++;

			for (int i = 0; i < estadoAnterior.length; i++) {
				estadoAnterior[i]=estadoActual[i];
			}
			//obtener condicionales
			obtenerCondicionales();
			for (int i = 0; i < matrizCondicionalX.length; i++) {
				if(marginalX[i]!=0)
					matrizCondicionalX[i][salida]=matrizConjunta[i][salida]/marginalX[i];
				if(marginalY[i]!=0)
					matrizCondicionalY[simboloEmitido][i]=matrizConjunta[simboloEmitido][i]/marginalY[i];
			}
			estadoActual[0]=obtenerRuido();
			estadoActual[1]=obtenerPerdida();
			estadoActual[2]=obtenerInformacionMutua();
			GuardarEnArchivo2("datos para grafico", Integer.toString(total),estadoActual[0],estadoActual[1],estadoActual[2]);
		}		
	}
	
	public void setMaximo(int maximo){
		maximoSimulaciones=maximo;
	}

	public void imprimirCondicional(PrintWriter wr) {
		wr.println("");
		for(int i=0;i<matrizCondicionalX.length;i++){
			for (int j = 0; j < matrizCondicionalX.length; j++) {			
				wr.append(matrizCondicionalX[i][j]+" ");					
			}
			wr.println("");			
		}
		
	}

}
