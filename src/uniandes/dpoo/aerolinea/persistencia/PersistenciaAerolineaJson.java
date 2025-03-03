package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea {

	@Override
	public void cargarAerolinea(String archivo, Aerolinea aerolinea) throws java.io.IOException, InformacionInconsistenteException {
		// TODO Auto-generated method stub
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );
		
        cargarAviones( aerolinea, raiz.getJSONArray( "aviones" ) );
        cargarRutas( aerolinea, raiz.getJSONArray( "rutas" ) );
        cargarVuelos( aerolinea, raiz.getJSONArray( "vuelos" ) );
	}

	@Override
	public void salvarAerolinea(String archivo, Aerolinea aerolinea) throws IOException {
		// TODO Auto-generated method stub
		JSONObject jobject = new JSONObject( );
		
		salvarAviones(aerolinea,jobject);
		salvarRutas(aerolinea,jobject);
		salvarVuelos(aerolinea,jobject);
		
		PrintWriter pw = new PrintWriter( archivo );
        jobject.write( pw, 2, 0 );
        pw.close( );
		
	}
	
	private void cargarAviones(Aerolinea aerolinea, JSONArray jAviones) {
		int numAviones = jAviones.length( );
		Avion avion = null;
        for( int i = 0; i < numAviones; i++ )
        {
        	JSONObject jAvion = jAviones.getJSONObject( i );
            String nombre = jAvion.getString("nombre");
            int capacidad = Integer.parseInt(jAvion.getString("capacidad"));
            avion = new Avion(capacidad,nombre);
            
            aerolinea.agregarAvion(avion);
        }
	}
	
	private void salvarAviones(Aerolinea aerolinea, JSONObject jobject) {
		
		ArrayList<Avion> aviones = (ArrayList<Avion>)aerolinea.getAviones();
		JSONArray jAviones = new JSONArray( );
		for(Avion avion: aviones) {
			JSONObject jAvion = new JSONObject( );
			jAvion.put( "nombre", avion.getNombre() );
			jAvion.put( "capacidad", avion.getCapacidad() );
			
			jAviones.put(jAvion);
		}
		jobject.put( "aviones", jAviones );
	}
	
	private void cargarRutas(Aerolinea aerolinea, JSONArray jRutas) throws JSONException, AeropuertoDuplicadoException {
		int numRutas = jRutas.length( );
		
		
        for( int i = 0; i < numRutas; i++ )
        {	
        	JSONObject jRuta = jRutas.getJSONObject( i );
        	
        	Ruta ruta = null;
        	Aeropuerto aDestino = null;
    		Aeropuerto aOrigen = null;
            String codigoRuta = jRuta.getString("codigoRuta");
            
            String destino = jRuta.getString("destinoCodigo");
            String origen = jRuta.getString("origenCodigo");
            if (origen == destino) {
            	throw new AeropuertoDuplicadoException(origen);
            }
            aDestino = new Aeropuerto(jRuta.getString("destinoNombre"),destino,jRuta.getString("destinoNombreCiudad"),jRuta.getDouble("destinoLatud"),jRuta.getDouble("destinoLongitud"));
            aOrigen= new Aeropuerto(jRuta.getString("origenNombre"),origen,jRuta.getString("origenNombreCiudad"),jRuta.getDouble("origenLatud"),jRuta.getDouble("origenLongitud"));

            String llegada = jRuta.getString("horaLlegada");
            String salida = jRuta.getString("horaSalida");
            
            ruta = new Ruta(salida, aDestino, aOrigen, llegada, codigoRuta);
            aerolinea.agregarRuta(ruta);
        }
	}
        
        
	private void salvarRutas(Aerolinea aerolinea, JSONObject jobject) {
		
		Collection<Ruta> rutas = aerolinea.getRutas();
		
		JSONArray jRutas= new JSONArray( );
		for(Ruta ruta: rutas) {
			JSONObject jRuta = new JSONObject( );
			jRuta.put( "codigoRuta", ruta.getCodigoRuta());

			jRuta.put( "destinoCodigo", ruta.getDestino().getCodigo());
			jRuta.put( "destinoLatitud", ruta.getDestino().getLatitud());
			jRuta.put( "destinoLongitud", ruta.getDestino().getLongitud());
			jRuta.put( "destinoNombre", ruta.getDestino().getNombre());
			jRuta.put( "destinoNombreCiudad", ruta.getDestino().getNombreCiudad());
			
			jRuta.put( "horaLlegada", ruta.getHoraLlegada() );
			jRuta.put( "horaSalida", ruta.getHoraSalida() );
			
			jRuta.put( "origenCodigo", ruta.getOrigen().getCodigo());
			jRuta.put( "origenLatitud", ruta.getOrigen().getLatitud());
			jRuta.put( "origenLongitud", ruta.getOrigen().getLongitud());
			jRuta.put( "origenNombre", ruta.getOrigen().getNombre());
			jRuta.put( "origenNombreCiudad", ruta.getOrigen().getNombreCiudad());
			jRutas.put(jRuta);
		}
		jobject.put( "rutas", jRutas);
	}
	
	private void cargarVuelos(Aerolinea aerolinea, JSONArray jVuelos) throws Exception {
		int numVuelos= jVuelos.length( );
        for( int i = 0; i < numVuelos; i++ )
        {
        	JSONObject jVuelo = jVuelos.getJSONObject( i );
        	String ruta = jVuelo.getJSONObject("ruta").getString("codigoRuta");
            String fecha = jVuelo.getString("fecha");
            String avionNombre = jVuelo.getString("avionNombre");
            aerolinea.programarVuelo(ruta, fecha, avionNombre);
        }
	}
	
	private void salvarVuelos(Aerolinea aerolinea, JSONObject jobject) {
		JSONArray jVuelos = new JSONArray( );
		Collection<Vuelo> vuelos = aerolinea.getVuelos();
		
		for(Vuelo vuelo: vuelos) {
			JSONObject jVuelo = new JSONObject( );
			Ruta ruta = vuelo.getRuta();
			JSONObject jRuta = new JSONObject( );
			jRuta.put( "codigoRuta", ruta.getCodigoRuta());

			jRuta.put( "destinoCodigo", ruta.getDestino().getCodigo());
			jRuta.put( "destinoLatitud", ruta.getDestino().getLatitud());
			jRuta.put( "destinoLongitud", ruta.getDestino().getLongitud());
			jRuta.put( "destinoNombre", ruta.getDestino().getNombre());
			jRuta.put( "destinoNombreCiudad", ruta.getDestino().getNombreCiudad());
			
			jRuta.put( "horaLlegada", ruta.getHoraLlegada() );
			jRuta.put( "horaSalida", ruta.getHoraSalida() );
			
			jRuta.put( "origenCodigo", ruta.getOrigen().getCodigo());
			jRuta.put( "origenLatitud", ruta.getOrigen().getLatitud());
			jRuta.put( "origenLongitud", ruta.getOrigen().getLongitud());
			jRuta.put( "origenNombre", ruta.getOrigen().getNombre());
			jRuta.put( "origenNombreCiudad", ruta.getOrigen().getNombreCiudad());
			
			
			jVuelo.put( "ruta", jRuta);
			jVuelo.put( "fecha", vuelo.getFecha());
			jVuelo.put( "avionNombre", vuelo.getAvion().getNombre());
			jVuelo.put( "avionCapacidad", vuelo.getAvion().getCapacidad());
			jVuelos.put(jVuelo);
		}
		jobject.put( "vuelos", jVuelos);
		
	}
	

}