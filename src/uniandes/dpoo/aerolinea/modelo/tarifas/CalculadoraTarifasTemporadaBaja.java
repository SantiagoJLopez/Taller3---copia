package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.Ruta;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas{
	
	protected final int COSTO_POR_KM_NATURAL = 600;
	protected final int COSTO_POR_KM_CORPORATIVO = 900;
	protected final double DESCUENTO_PEQ = 0.02;
	protected final double DESCUENTO_MEDIANAS = 0.1;
	protected final double DESCUENTO_GRANDES = 0.2;
	
	public CalculadoraTarifasTemporadaBaja() {
		super();
	}
	
	@Override
	public int calcularCostoBase​(Vuelo vuelo, Cliente cliente) {
		Ruta ruta = vuelo.getRuta();
		int distancia = calcularDistanciaVuelo​(ruta);
		int costo = 0;
		if (cliente.getTipoCliente() == "Corporativo") {
			costo = COSTO_POR_KM_CORPORATIVO;
		}
		else if (cliente.getTipoCliente() == "Natural") {
			costo = COSTO_POR_KM_NATURAL;
		}
		return costo * distancia;
	}

	public double calcularPorcentajeDescuento​(Cliente cliente) {
		double descuento = 0.0;
		if (cliente.getTipoCliente() == "Corporativo") {
			ClienteCorporativo clienteC = (ClienteCorporativo) cliente;
			if(clienteC.getTamanoEmpresa() == 1) {
				descuento = DESCUENTO_PEQ;
			}
			else if(clienteC.getTamanoEmpresa() == 2) {
				descuento = DESCUENTO_MEDIANAS;
			}
			else if(clienteC.getTamanoEmpresa() == 3) {
				descuento = DESCUENTO_GRANDES;
			}
		}
		return descuento;
		
	}
}