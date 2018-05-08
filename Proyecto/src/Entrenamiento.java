import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;

public class Entrenamiento 
{
	/**
	 * Variables Globales
	 */
 
	 ArrayList<String> list = new ArrayList<String>(); // Listado de las lineas del archivo de texto (palabras + etiqueta)
	 ArrayList<String> palabras = new ArrayList<String>(); // Se guardan palabras de cada oracion (No estan separadas)
	 ArrayList<String> etiquetas = new ArrayList<String>(); // Se guardan etiquetas de cada oracion.
	 ArrayList<String> listadoPalabras = new ArrayList<String>(); // Aqui se guardan las palabras individualmente
	 ArrayList<String> listadoEtiquetas = new ArrayList<String>(); //Aquí se guardan las etiquetas sin repetir
	 ArrayList<Integer> listadoFrecEtiquetas = new ArrayList<Integer>(); //Frecuencia de cada etiqueta
	 ArrayList<String> probabilidadesEtiquetas = new ArrayList<String>(); //Probabilidades individuales de las etiquetas
	 ArrayList<Integer> frecuenciaFraseEtiqueta = new ArrayList<Integer>(); //Lista para guardar las frecuencias de cada etiqueda en las frases
	 ArrayList<TablaFrecuencias> tabla = new ArrayList<TablaFrecuencias>();
	 ArrayList<BagOfWords> bagofwords = new ArrayList<BagOfWords>(); 
	 
	
	/**
	 * Constructor de la Clase
	 * @param NombreArchivo
	 */
	public Entrenamiento(String NombreArchivo)
	{
		LeerTexto(NombreArchivo);
		DividirPalabras(list);
		SepararPalabras(palabras);
		ListaEtiquetas(etiquetas);
		CrearTablaFrecuencias();
		FrecuenciaEtiqueta(tabla,listadoEtiquetas);
		CrearBagOfWords(tabla);
		ObtenerProbabilidadesEtiquetas();
		int k = 0;
		k  = 1;
	}
	
	/**
	 * Metodo que lee el archivo de entrenamiento
	 */
	
	public void LeerTexto(String NombreArchivo) // Mï¿½todo para leer el archivo de entrenamiento.
	{
		//String texto = "";
		try
		{
			//BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(NombreArchivo), "ISO-8859-1"));
                        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(NombreArchivo), "UTF-8"));

			String temp = "";
			String bfRead;
			
			while((bfRead = bf.readLine()) != null)
			{
				list.add(bfRead); // Almacena en la lista todas las lineas que va leyendo.
			}
			
			bf.close(); // Cierra el Archivo, esto siempre se debe de hacer al finalizar de leerlo. 
			
			
		}
		catch(Exception e)
		{
			System.out.println("No se encontro el archivo");
		}
		
	}
	
	/**
	 * Metodo que divide palabras y etiqueta. 
	 */
	
	private void DividirPalabras(ArrayList<String> listadoLineas)
	{
		String linea;
		
		String [] palabraEtiqueta;
		
		for(int i = 0; i < listadoLineas.size(); i++)
		{
			linea = listadoLineas.get(i);
			palabraEtiqueta = linea.split("\\|");
			
			palabras.add(StringUtils.stripAccents(palabraEtiqueta[0]).trim()); // Se almacena la linea de palabras de la linea completa.
			etiquetas.add(palabraEtiqueta[1].trim()); //Se almacena la etiqueta de la linea completa.
		}
	}
	
	
	/**
	 *  Separar las palabras individualmente
	 * @param lineaDePalabras
	 */
	
	private void SepararPalabras(ArrayList<String> lineaDePalabras)
	{
            
		String [] lineaSeparar;
		for(int i = 0; i < lineaDePalabras.size(); i++)
		{
			lineaSeparar = lineaDePalabras.get(i).split("\\s");
			
			for(int j = 0; j < lineaSeparar.length; j++)
			{
				if(lineaSeparar[j].length() != 0 && !lineaSeparar[j].matches("\\W"))
				{
                                    if(lineaSeparar[j].substring(lineaSeparar[j].length()-1).matches("\\W"))
                                    {
                                      lineaSeparar[j] = lineaSeparar[j].substring(0, lineaSeparar[j].length()-1);
                                    }
                                    if(!IgnoreCase(listadoPalabras,lineaSeparar[j])) // Se evalua si la palabra se encuentra o no en la lista.
                                    {
                                        String prueba = lineaSeparar[j].trim();
                                        
                                        if(listadoPalabras.contains(prueba))
                                        {
                                        }
                                        else
                                        {
                                           listadoPalabras.add(prueba);
                                        }
                               	    }
				}
			}
		}
		
	}
	
	private boolean IgnoreCase(ArrayList<String> lineaDePalabras, String palabra)
	{
		for(int i = 0; i<lineaDePalabras.size(); i++)
		{
			if(lineaDePalabras.get(i).equalsIgnoreCase(palabra))
				return true;
		}
		return false;
	}
	
	private void CrearTablaFrecuencias()
	{
		String etiqueta;
		String oracion;
		String [] fraseCompleta;
		String [] separarOracion;
		int frecuenciaPalabra = 0;
		ArrayList<String> palabrasRevisadas = new ArrayList<String>();
		
		for(int i = 0; i < list.size(); i++)
		{
			fraseCompleta = list.get(i).split("\\|");
		
			oracion = StringUtils.stripAccents(fraseCompleta[0]);
			etiqueta = StringUtils.stripAccents(fraseCompleta[1]);
			
			separarOracion = oracion.split("\\s"); // Separa por palabras la oracion
			
                        //---------------
                        
                        
                        //-----------
                        
			for(int j = 0; j < separarOracion.length; j++) // Se recorre el arreglo de las palabras de la frase que se esta revisando
			{
                            //-------------------------
                            if(separarOracion[j].length() != 0 && !separarOracion[j].matches("\\W"))
				{
                                    if(separarOracion[j].substring(separarOracion[j].length()-1).matches("\\W"))
                                    {
                                      separarOracion[j] = separarOracion[j].substring(0, separarOracion[j].length()-1);
                                    }
                                    
                                   
                                        
                                        if(!palabrasRevisadas.contains(separarOracion[j]))
                                        {
                                            frecuenciaPalabra = DeterminarFrecuencia(separarOracion[j], separarOracion);
                                            palabrasRevisadas.add(separarOracion[j]);
                                            tabla.add(new TablaFrecuencias(separarOracion[j].trim(), etiqueta.trim(), i + 1, frecuenciaPalabra));
                                            frecuenciaPalabra = 0;
                                        }
                               	    
                                }
                            
                            

                            
			}
			
                         palabrasRevisadas.clear();

		}
	}
	
	
	private int DeterminarFrecuencia(String palabraRevisada,String [] oracionSeparada)
	{
		int repetido = 0;
		
		
		for(int i = 0; i < oracionSeparada.length; i++)
		{
			if(palabraRevisada.equalsIgnoreCase(oracionSeparada[i]))
			{
				repetido++;
			}
		}
		
		return repetido;
	}
	
	private void ListaEtiquetas(ArrayList<String> LEtiquetas)
	{
		for(int i = 0; i<LEtiquetas.size(); i++)
		{
			if(!listadoEtiquetas.contains((LEtiquetas.get(i))))
			{
				listadoEtiquetas.add(LEtiquetas.get(i));
			}
		}
	}
	
	private void FrecuenciaEtiqueta(ArrayList<TablaFrecuencias> TablaFrecuencia, ArrayList<String> LEtiquetas)
	{
		ArrayList<Integer> NoFrases = new ArrayList<Integer>();
		int contadorPalabra = 0;
		int NoFrasesEtiqueta = 0;
		for(int i = 0; i<LEtiquetas.size(); i++)
		{
			String etiqueta = LEtiquetas.get(i);
			for(int j = 0; j<TablaFrecuencia.size(); j++)
			{
				if(etiqueta.equalsIgnoreCase(TablaFrecuencia.get(j).etiqueta()))
				{
					contadorPalabra += TablaFrecuencia.get(j).frecuencia();
				}
				if(!NoFrases.contains(TablaFrecuencia.get(j).nofrase()) && etiqueta.equalsIgnoreCase(TablaFrecuencia.get(j).etiqueta()))
				{
					NoFrasesEtiqueta++;
					NoFrases.add(TablaFrecuencia.get(j).nofrase());
				}
			}
			listadoFrecEtiquetas.add(contadorPalabra);
			contadorPalabra = 0;
			frecuenciaFraseEtiqueta.add(NoFrasesEtiqueta);
			NoFrasesEtiqueta = 0;
		}
	}
	
	private void CrearBagOfWords(ArrayList<TablaFrecuencias> TablaFrecuencia)
	{
		int frecuenciaEtiqueta = 0;
		ArrayList<NodoBOW> listaEtiquetasxPalabra = null;
		
		for(int i = 0; i<listadoPalabras.size();i++)
		{
			String palabra = listadoPalabras.get(i);
			listaEtiquetasxPalabra = new ArrayList<NodoBOW>();
			for(int j = 0; j<listadoEtiquetas.size(); j++)
			{
				String etiqueta = listadoEtiquetas.get(j);
				for(int k = 0; k<TablaFrecuencia.size(); k++)
				{
					if(palabra.equalsIgnoreCase(TablaFrecuencia.get(k).palabra()) && etiqueta.equalsIgnoreCase(TablaFrecuencia.get(k).etiqueta()))
					{
						frecuenciaEtiqueta += TablaFrecuencia.get(k).frecuencia();
					}
				}
				
				BigDecimal num = new BigDecimal(frecuenciaEtiqueta + 1);
				BigDecimal den = new BigDecimal(listadoFrecEtiquetas.get(j) + listadoPalabras.size());
				BigDecimal prob = new BigDecimal("0.0");
				prob = num.divide(den,MathContext.DECIMAL128);

				NodoBOW nodo = new NodoBOW(etiqueta,prob.toString());
				
				listaEtiquetasxPalabra.add(nodo);
				frecuenciaEtiqueta = 0;
			}
			BagOfWords bow = new BagOfWords(palabra,listaEtiquetasxPalabra);
			bagofwords.add(bow);
		}
		
		
	}
	
	private void ObtenerProbabilidadesEtiquetas()
	{
		int NoFrases = etiquetas.size();
		for(int i = 0; i<frecuenciaFraseEtiqueta.size(); i++)
		{
			BigDecimal num = new BigDecimal(frecuenciaFraseEtiqueta.get(i));
			BigDecimal den = new BigDecimal(NoFrases);
			BigDecimal prob = new BigDecimal(0.0);
			prob = num.divide(den, MathContext.DECIMAL128);
			probabilidadesEtiquetas.add(prob.toString());
		}
		
	}

	
	//----------------------------------------SEGUNDA FASE---------------------------------
	
	public void Validar(String frase)
	{
		SepararFrase(frase);
	}
	
	private void SepararFrase(String frase)
	{
		ArrayList<String> nuevaPalabra = new ArrayList<String>();
		boolean palabraNueva = false;
		frase = StringUtils.stripAccents(frase);
		String[] palabras = frase.split("\\s");
		for(int j = 0; j < palabras.length; j++) // Se recorre el arreglo de las palabras de la frase
		{
			if(palabras[j].length() != 0 && !palabras[j].matches("\\W"))
			{
				if(palabras[j].substring(palabras[j].length()-1).matches("\\W"))
				{
					palabras[j] = palabras[j].substring(0, palabras[j].length()-1);
				}
				if(!IgnoreCase(listadoPalabras,palabras[j]))
				{
					palabraNueva = true;
					nuevaPalabra.add(palabras[j]);
				}       	    
			}         
		}
		int k = 0;
		k = 1;
	}
	
	private void CalcularEtiqueta(boolean palabraNueva, ArrayList<String> nuevaPalabra, String[] palabras)
	{
		
	}
