package uniandes.dpoo.aerolinea.persistencia;

import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import java.io.IOException;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;

public interface IPersistenciaAerolinea{
	
	void cargarAerolinea​(String archivo, Aerolinea aerolinea) throws IOException, InformacionInconsistenteException;
	
	void salvarAerolinea​(String archivo, Aerolinea aerolinea) throws IOException;

	void cargarAerolinea(String archivo, Aerolinea aerolinea) throws IOException, InformacionInconsistenteException;

	
}