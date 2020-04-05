package com.github.jorge2m.testmaker.testreports.stepstore;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;


public class GestorDatosHarJSON {

	private JSONParser parser = new JSONParser();
	private JSONObject fileHAR = null;   

	public GestorDatosHarJSON(String fileHAR) throws Exception {
		parseHAR(fileHAR);
	}
	
	public GestorDatosHarJSON(StepTM step) throws Exception {
		String ficheroHAR = StepEvidence.har.getPathFile(step);
		parseHAR(ficheroHAR);
	}
	
	private void parseHAR(String fileHARToParse) throws Exception {
		try (FileReader fileReaderHAR = new FileReader(fileHARToParse)) { 
			this.fileHAR = (JSONObject)this.parser.parse(fileReaderHAR);
		}
	}
	
    /**
     * Obtiene la lista de entradas filtradas según el valor del atributo URL del request
     */
    @SuppressWarnings("unchecked")
	public JSONArray getListEntriesFilterURL(String urlFilter1, String urlFilter2) throws Exception {
        JSONArray entriesFilteredToReturn = new JSONArray();
	    
        //Obtenemos todas las entradas del log
        JSONObject logJSON = (JSONObject)this.fileHAR.get("log");
        JSONArray entriesJSON = (JSONArray)logJSON.get("entries");
	    
        //Filtramos todas las entradas con un request correspondiente a la url de filtro
        Iterator<?> iteratorEntries = entriesJSON.iterator();
        while (iteratorEntries.hasNext()) {
            JSONObject entrieJSON = (JSONObject)iteratorEntries.next();
            JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
            if (requestJSON.get("url")!=null) {
                if ("".compareTo(urlFilter1)==0 || requestJSON.get("url").toString().indexOf(urlFilter1)>0) {
                    if ("".compareTo(urlFilter2)==0 || ("".compareTo(urlFilter2)!=0 && requestJSON.get("url").toString().indexOf(urlFilter2)>0)) {
                        entriesFilteredToReturn.add(entrieJSON);
                    }
                }
            }
        }
	    
        return entriesFilteredToReturn;
    }
	
    /**
     * Obtiene la lista de peticiones con un response de un determinado mimeType
     */
    @SuppressWarnings("unchecked")
	public JSONArray getListEntriesOfMimeType(String mimeType) throws Exception {
        JSONArray entriesOfMimeType = new JSONArray();
	    
        //Obtenemos todas las entradas del log
        JSONObject logJSON = (JSONObject)this.fileHAR.get("log");
        JSONArray entriesJSON = (JSONArray)logJSON.get("entries");
	    
        //Filtramos todas las entradas con el type indicado
        Iterator<?> iteratorEntries = entriesJSON.iterator();
        while (iteratorEntries.hasNext()) {
            JSONObject entrieJSON = (JSONObject)iteratorEntries.next();
            JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
            JSONObject contentJSON = (JSONObject)responseJSON.get("content");
            if (contentJSON.get("mimeType")!=null && contentJSON.get("mimeType").toString().contains(mimeType)) {
                entriesOfMimeType.add(entrieJSON);
            }
        }
            
        return entriesOfMimeType;
    }
	
    public JSONObject getParameterFromRequestQuery(JSONObject requestJSON, String paramName) {
        JSONObject paramRetJSON = null;
        JSONArray queryJSON = (JSONArray)requestJSON.get("queryString");
        Iterator<?> itParams = queryJSON.iterator();
        while (itParams.hasNext()) {
            JSONObject paramJSON = (JSONObject)itParams.next();
            if (((String)paramJSON.get("name")).compareTo(paramName)==0) {
                paramRetJSON = paramJSON;
                break;
            }
        }
	    
        return paramRetJSON; 
	    
    }
	
    public JSONObject getParameterFromRequestCabe(JSONObject requestJSON, String paramName) {
        JSONObject paramRetJSON = null;
	    
        //Obtenemos las cabeceras del request
        JSONArray headersReq = ((JSONArray)((JSONObject)requestJSON.get("request")).get("headers"));
        boolean refererEncontrado = false;
        for (int i=0; (i<headersReq.size() && !refererEncontrado); i++) {
            JSONObject cabeceraJSON = (JSONObject)headersReq.get(i);
            if (((String)cabeceraJSON.get("name")).compareTo(paramName)==0) {
                paramRetJSON = cabeceraJSON;
                refererEncontrado = true;
            }
        }
	    
        return paramRetJSON;
    }
}
