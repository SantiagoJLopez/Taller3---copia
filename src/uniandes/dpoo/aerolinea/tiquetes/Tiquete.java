package uniandes.dpoo.aerolinea.tiquetes;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class Tiquete
{
	private Cliente cliente;
	private String codigo;
	private int tarifa;
	private boolean usado;
	private Vuelo vuelo;
	
	public Tiquete(String codigo, Vuelo vuelo, Cliente clienteComprador, int tarifa) {
		this.cliente = clienteComprador;
		this.codigo = codigo;
		this.tarifa = tarifa;
		this.vuelo = vuelo;
	}
	
	public boolean esUsado() {
		return this.usado;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public Vuelo getVuelo() {
		return this.vuelo;
	}
	
	public int getTarifa() {
		return this.tarifa;
	}
	
	public void marcarComoUsado() {
		this.usado = true;
	}
	
}