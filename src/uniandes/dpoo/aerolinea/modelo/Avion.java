package uniandes.dpoo.aerolinea.modelo;

public class Avion
{
	private int capacidad;
	private String nombre;
	
	public Avion(int capacidad, String nombre)
	{
		this.capacidad = capacidad;
		this.nombre = nombre;
	}
	
	public int getCapacidad() {
		return this.capacidad;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}