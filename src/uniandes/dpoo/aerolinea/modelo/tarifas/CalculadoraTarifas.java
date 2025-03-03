package uniandes.dpoo.aerolinea.modelo.tarifas;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;

public abstract class CalculadoraTarifas{

	public static final double IMPUESTO = 0.28;
	
	public CalculadoraTarifas() {}
	
	public int calcularTarifa​(Vuelo vuelo, Cliente cliente) {
		int costoBase = calcularCostoBase​(vuelo, cliente);
		int descuento = (int)(costoBase * calcularPorcentajeDescuento​(cliente));
		int impuesto = calcularValorImpuestos​(costoBase);
		return costoBase + impuesto - descuento;
	}
	
	protected abstract int calcularCostoBase​(Vuelo vuelo, Cliente cliente);
	
	protected abstract double calcularPorcentajeDescuento​(Cliente cliente);
	
	protected int calcularDistanciaVuelo​(Ruta ruta) {
		Aeropuerto aeropuerto1 = ruta.getOrigen();
		Aeropuerto aeropuerto2 = ruta.getDestino();
		int distancia = Aeropuerto.calcularDistancia(aeropuerto1, aeropuerto2);
		return distancia;
	}
	
	protected int calcularValorImpuestos​(int costoBase) {
		return (int)(costoBase * IMPUESTO);
	}
}