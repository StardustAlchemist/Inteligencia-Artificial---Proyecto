import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

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
	 ArrayList<String> listadoEtiquetas = new ArrayList<String>(); //AquÃ­ se guardan las etiquetas sin repetir
	 ArrayList<Integer> listadoFrecEtiquetas = new ArrayList<Integer>(); //Frecuencia de cada etiqueta
	 ArrayList<BigDecimal> probabilidadesEtiquetas = new ArrayList<BigDecimal>(); //Probabilidades individuales de las etiquetas
	 ArrayList<Integer> frecuenciaFraseEtiqueta = new ArrayList<Integer>(); //Lista para guardar las frecuencias de cada etiqueda en las frases
	 ArrayList<int[]> frecEtiquetas = new ArrayList<int[]>();
	 ArrayList<String[]> bagofwords = new ArrayList<String[]>();
	 ArrayList<TablaFrecuencias> tabla = new ArrayList<TablaFrecuencias>();
	 //ArrayList<BagOfWords> bagofwords = new ArrayList<BagOfWords>(); 

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
		
	}
	
	public void recalcular(String frase, String etiqueta) {
		list.add(frase+" | "+etiqueta);
		palabras.clear();
		etiquetas.clear();
		listadoPalabras.clear();
		listadoEtiquetas.clear();
		listadoFrecEtiquetas.clear();
		probabilidadesEtiquetas.clear();
		frecuenciaFraseEtiqueta.clear();
		frecEtiquetas.clear();
		tabla.clear();
		bagofwords.clear();
		
		DividirPalabras(list);
		SepararPalabras(palabras);
		ListaEtiquetas(etiquetas);
		CrearTablaFrecuencias();
		FrecuenciaEtiqueta(tabla,listadoEtiquetas);
		CrearBagOfWords(tabla);
		ObtenerProbabilidadesEtiquetas();
	}
	
	/**
	 * Metodo que lee el archivo de entrenamiento
	 */
	
	public void LeerTexto(String NombreArchivo) // MÃ¯Â¿Â½todo para leer el archivo de entrenamiento.
	{
		//String texto = "";
		try
		{
			
			//BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(NombreArchivo), "ISO-8859-1"));
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(NombreArchivo), "UTF-8"));

			String bfRead;
			while((bfRead = bf.readLine()) != null)
			{
				
				if(!bfRead.isEmpty() && bfRead.contains("|")) {
					list.add(bfRead); // Almacena en la lista todas las lineas que va leyendo.
				}
			}
			bf.close(); // Cierra el Archivo, esto siempre se debe de hacer al finalizar de leerlo. 
			
			//quitar los caracteres al inicio
			char caracteres[] = list.get(0).toCharArray();
			for(int i = 0; i<caracteres.length; i++){
				String temp = ""+caracteres[i];
				if(temp.matches("\\w")) {
					break;
				}else {
					caracteres[i]=' ';
				}
			}
			list.set(0,new String(caracteres));
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
			
			palabras.add(StringUtils.lowerCase(StringUtils.stripAccents(palabraEtiqueta[0]).trim())); // Se almacena la linea de palabras de la linea completa.
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
					if(lineaSeparar[j].matches("\\W*\\w+\\W*"))
					{
						lineaSeparar[j] = lineaSeparar[j].replaceAll("\\W", " ").trim();
					}
                    /*if(lineaSeparar[j].substring(lineaSeparar[j].length()-1).matches("\\W"))
                    {
                      lineaSeparar[j] = lineaSeparar[j].substring(0, lineaSeparar[j].length()-1);
                    }*/
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
	            	if(separarOracion[j].matches("\\W*\\w+\\W*"))
					{
						separarOracion[j] = separarOracion[j].replaceAll("\\W", " ").trim();
					}
                    
                    if(!IgnoreCase(palabrasRevisadas,separarOracion[j]))
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
		//ArrayList<String> palabra = new ArrayList<String>();
		String palabra = "";
		int[] frecEtiqueta;
		
		int contadorPalabra = 0;
		int NoFrasesEtiqueta = 0;
		for(int i = 0; i<LEtiquetas.size(); i++)
		{
			frecEtiqueta = new int[listadoPalabras.size()];
			String etiqueta = LEtiquetas.get(i);
			
			for(int j = 0; j<TablaFrecuencia.size(); j++)
			{
				if(etiqueta.equalsIgnoreCase(TablaFrecuencia.get(j).etiqueta()))
				{
					contadorPalabra += TablaFrecuencia.get(j).frecuencia();
					palabra = TablaFrecuencia.get(j).palabra();
					int index = listadoPalabras.indexOf(palabra.toLowerCase());
					frecEtiqueta[index] += TablaFrecuencia.get(j).frecuencia();
				}
				if(!NoFrases.contains(TablaFrecuencia.get(j).nofrase()) && etiqueta.equalsIgnoreCase(TablaFrecuencia.get(j).etiqueta()))
				{
					NoFrasesEtiqueta++;
					NoFrases.add(TablaFrecuencia.get(j).nofrase());
				}
			}
			frecEtiquetas.add(frecEtiqueta);
			
			listadoFrecEtiquetas.add(contadorPalabra);
			contadorPalabra = 0;
			frecuenciaFraseEtiqueta.add(NoFrasesEtiqueta);
			NoFrasesEtiqueta = 0;
		}
	}
	
	private void CrearBagOfWords(ArrayList<TablaFrecuencias> TablaFrecuencia)
	{
		String[] probabilidades;
		for(int i = 0; i<frecEtiquetas.size(); i++)
		{
			probabilidades = new String[listadoPalabras.size()];
			for(int j = 0; j<frecEtiquetas.get(i).length; j++)
			{
				BigDecimal num = new BigDecimal(frecEtiquetas.get(i)[j] + 1);
				BigDecimal den = new BigDecimal(listadoFrecEtiquetas.get(i) + listadoPalabras.size());
				BigDecimal prob = new BigDecimal("0.0");
				prob = num.divide(den,MathContext.DECIMAL128);
				probabilidades[j] = prob.toString();
			}
			bagofwords.add(probabilidades);
		}
		/*int frecuenciaEtiqueta = 0;
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
		}*/
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
			probabilidadesEtiquetas.add(prob);
		}
		
	}

	
	//----------------------------------------SEGUNDA FASE---------------------------------
	
	public String Validar(String frase)
	{
		return SepararFrase(frase);
	}
	
	private String SepararFrase(String frase)
	{
		ArrayList<String> nuevaPalabra = new ArrayList<String>();
		boolean palabraNueva = false;
		frase = StringUtils.stripAccents(frase);
		String[] palabras = frase.split("\\s");
		for(int j = 0; j < palabras.length; j++) // Se recorre el arreglo de las palabras de la frase
		{
			if(palabras[j].length() != 0 && !palabras[j].matches("\\W"))
			{
				if(palabras[j].matches("\\W*\\w+\\W*"))
				{
					palabras[j] = palabras[j].replaceAll("\\W", " ").trim();
				}
				if(!IgnoreCase(listadoPalabras,palabras[j]))
				{
					palabraNueva = true;
					nuevaPalabra.add(palabras[j]);
				}
			}         
		}
		return CalcularEtiqueta(palabraNueva, nuevaPalabra, palabras);
	}
	
	private String CalcularEtiqueta(boolean palabraNueva, ArrayList<String> nuevaPalabra, String[] palabras)
	{
		boolean vinoPalabraVieja = false;
		ArrayList<BigDecimal> resultados = new ArrayList<BigDecimal>();
		ArrayList<Integer> posicionesProb = new ArrayList<Integer>();
		BigDecimal resultado;
		BigDecimal valor;
		BigDecimal probabilidad;
		
		
		for(int i = 0; i<palabras.length; i++)
		{
			if(listadoPalabras.contains(palabras[i]))
			{
				vinoPalabraVieja = true;
			}
		}
		
		if(vinoPalabraVieja)
		{
			for(int i = 0; i<palabras.length; i++)
			{
				if(listadoPalabras.indexOf(palabras[i].toLowerCase()) != -1)
				{
					posicionesProb.add(listadoPalabras.indexOf(palabras[i].toLowerCase()));
				}
			}
			
			for(int i = 0; i<bagofwords.size(); i++)
			{
				resultado = new BigDecimal(1);
				probabilidad = new BigDecimal(probabilidadesEtiquetas.get(i).toString());
				for(int j = 0; j<posicionesProb.size(); j++)
				{
					valor = new BigDecimal(bagofwords.get(i)[posicionesProb.get(j)]);
					resultado = resultado.multiply(valor);
				}
				resultado = resultado.multiply(probabilidad);
				resultados.add(resultado);
			}

			int index = resultados.indexOf(Collections.max(resultados));
			String etiquetaGanadora = listadoEtiquetas.get(index);
			JOptionPane.showMessageDialog(null, etiquetaGanadora);
			return etiquetaGanadora;
			
				/*for(int i = 0; i<palabras.length; i++)
				{
					palabra = palabras[i];
					//probabilidadesEtiqueta = new ArrayList<String>();
					
					for(int j = 0; j<bagofwords.size(); j++)
					{
						
						//etiqueta = listadoEtiquetas.get(j);
						if(palabra.equalsIgnoreCase(bagofwords.get(j).Palabra()))
						{
							probabilidadesEtiqueta = new ArrayList<String>();
							for(int k = 0; k<bagofwords.get(j).listaEtiquetas().size(); k++)
							{
								BigDecimal prob = new BigDecimal(bagofwords.get(j).listaEtiquetas().get(k).Probabilidad());
								probabilidadesEtiqueta.add(prob.toString());
							}
							
							listaEtiquetasProb.add(probabilidadesEtiqueta);

						}
						
						
					}

				}
				for(int j = 0; j<listadoEtiquetas.size(); j++)
				{
					PE = new ArrayList<String>();
					for(int i = 0; i<listaEtiquetasProb.size(); i++)
					  {
						  PE.add(listaEtiquetasProb.get(i).get(j));
					  }
					LEP.add(PE);
				}
				
				for(int i = 0; i<LEP.size(); i++)
				{
					resultado = new BigDecimal(1);
					probabilidad = new BigDecimal(probabilidadesEtiquetas.get(i).toString());
					for(int j = 0; j<LEP.get(i).size(); j++)
					{
						valor = new BigDecimal(LEP.get(i).get(j));
						resultado = resultado.multiply(valor);
					}
					resultado = resultado.multiply(probabilidad);
					resultados.add(resultado);
				}
				
			
				
				int index = resultados.indexOf(Collections.max(resultados));
				String etiquetaGanadora = listadoEtiquetas.get(index);
				JOptionPane.showMessageDialog(null, etiquetaGanadora);
				return etiquetaGanadora;*/
		}
		else
		{
			ArrayList<BigDecimal> probabilidades = new ArrayList<BigDecimal>();
			BigDecimal prob;
			for(int i = 0; i<probabilidadesEtiquetas.size(); i++)
			{
				prob = new BigDecimal(probabilidadesEtiquetas.get(i).toString());
				probabilidades.add(prob);
			}
			int index = probabilidadesEtiquetas.indexOf(Collections.max(probabilidades));
			String etiquetaGanadora = listadoEtiquetas.get(index);
			JOptionPane.showMessageDialog(null, etiquetaGanadora);
			return etiquetaGanadora;
		}
		
	}
	
}
