
public class TablaFrecuencias 
{
	String Palabra;
	String Etiqueta;
	int NoFrase;
	int Frecuencia;
	
	public TablaFrecuencias(String palabra, String etiqueta, int noFrase, int frecuencia)
	{
		Palabra = palabra;
		Etiqueta = etiqueta;
		NoFrase = noFrase;
		Frecuencia = frecuencia;
	}
	
	public String palabra()
	{
		return Palabra;
	}
	
	public String etiqueta()
	{
		return Etiqueta;
	}
	
	public int nofrase()
	{
		return NoFrase;
	}
	
	public int frecuencia()
	{
		return Frecuencia;
	}
}
