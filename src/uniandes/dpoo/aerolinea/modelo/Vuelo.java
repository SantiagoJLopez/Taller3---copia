package uniandes.dpoo.aerolinea.modelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;

import java.util.HashMap;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class Vuelo
{
	private Avion avion;
	private String fecha;
	private Ruta ruta;
	private HashMap<String, Tiquete> tiquetes;
	
	public Vuelo(Avion avion, String fecha, Ruta ruta) {
		this.avion = avion;
		this.fecha = fecha;
		this.ruta = ruta;
		this.tiquetes = new HashMap<String, Tiquete>();
	}

	public Avion getAvion() {
		return avion;
	}

	public String getFecha() {
		return fecha;
	}

	public Ruta getRuta() {
		return ruta;
	}
	
	public HashMap<String, Tiquete> getTiquetes(){
		return tiquetes;
	}

	public int venderTiquetes​(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException {
		if (cantidad > avion.getCapacidad() || cantidad > (avion.getCapacidad() - tiquetes.size()) ) {
			throw new VueloSobrevendidoException(this);
		}
		else {
			for (int i=0; i<cantidad; i++) {
				int tarifa = calculadora.calcularTarifa​(this, cliente);
				Tiquete nuevoTiquete = GeneradorTiquetes.generarTiquete(this, cliente, tarifa);
				GeneradorTiquetes.registrarTiquete(nuevoTiquete);
				tiquetes.put(nuevoTiquete.getCodigo(), nuevoTiquete);
				cliente.agregarTiquete(nuevoTiquete);
			}
			return tiquetes.size();
		}
	}
	
}