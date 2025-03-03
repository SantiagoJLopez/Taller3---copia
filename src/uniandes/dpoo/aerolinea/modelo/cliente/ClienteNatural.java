package uniandes.dpoo.aerolinea.modelo.cliente;

public class ClienteNatural extends Cliente
{
	private static final String NATURAL = "Natural";
	private String nombre;
	
	public ClienteNatural(String nom)
	{
		this.nombre = nom;
	}
	
	@Override
    public String getTipoCliente()
    {
    	return NATURAL;
    }
    
    public String getIdentificador()
    {
    	return nombre;
    }
}