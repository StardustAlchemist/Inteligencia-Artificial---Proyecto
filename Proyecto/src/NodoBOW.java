

public class NodoBOW {

	String etiqueta;
	String probabilidad;
	
	public NodoBOW(String eti, String prob)
	{
		etiqueta = eti;
		probabilidad = prob;
	}
	
	public void ActualizarProbabilidad(String prob)
	{
		probabilidad = prob;
	}
	
	public String Etiqueta()
	{
		return etiqueta;
	}
	
	public String Probabilidad()
	{
		return probabilidad;
	}
	
}
