package uniandes.dpoo.aerolinea.modelo.cliente;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import java.util.List;
import java.util.ArrayList;

public abstract class Cliente
{
	private List<Tiquete> tiquetesSinUsar;
	private List<Tiquete> tiquetesUsados;
	
	
	public Cliente()
	{
		tiquetesSinUsar = new ArrayList<Tiquete>();
		tiquetesUsados = new ArrayList<Tiquete>();
	}
	
	public void agregarTiquete(Tiquete tiquete) {
		tiquetesSinUsar.addLast(tiquete);
	}
	
	public int calcularValorTotalTiquetes() {
		int valorTotal = 0;
		for (Tiquete tiquete : tiquetesSinUsar) {
			valorTotal += tiquete.getTarifa();
		}
		return valorTotal;
	}
	
	public void usarTiquetes​(Vuelo vuelo) {
		for(Tiquete tiquete: tiquetesSinUsar) {
			if (tiquete.getVuelo() == vuelo) {
				tiquetesSinUsar.remove(tiquete);
				tiquetesUsados.add(tiquete);
			}
		}
	}
	
	public abstract String getTipoCliente();
	
	public abstract String getIdentificador();
}

