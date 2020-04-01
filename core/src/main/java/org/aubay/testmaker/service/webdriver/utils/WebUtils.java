package org.aubay.testmaker.service.webdriver.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.net.ssl.*;
import org.testng.*;
import org.aubay.testmaker.conf.Log4jConfig;
import org.aubay.testmaker.service.TestMaker;
import org.aubay.testmaker.testreports.html.ResultadoErrores;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.Logs;

public class WebUtils {

    public static void acceptAllCertificates () {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                @Override
                public void checkClientTrusted (java.security.cert.X509Certificate[] certs, String authType) {
                    //
		}
                
                @Override
                public void checkServerTrusted (java.security.cert.X509Certificate[] certs, String authType) {
                    //
                }
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } 
        catch (Exception e) {
            //
        }
    }

    // Just add these two functions in your program 
    public static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
 
        @SuppressWarnings("unused")
        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }
 
        @SuppressWarnings("unused")
        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }
 
        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) 
        throws java.security.cert.CertificateException {
            return;
        }
 
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) 
        throws java.security.cert.CertificateException {
            return;
        }
    }

 
    @SuppressWarnings("unused")
    private static void trustAllHttpsCertificates() throws Exception {
        //  Create a trust manager that does not validate certificate chains:
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(
        sc.getSocketFactory());
    }

    /**
     * Retorna el log de errores de WebDriver entre los que se encuentran los JavaScript
     * El nivel de errores de cada uno de los tipos se define en la creación del WebDriver
     * Detecta el caso en que no se había superado el máximo de errores en todos los errores detectados -> No mostraremos warning
     * Nota: No funciona con GeckoDriver porque no están implementados los servicios al no formar parte del protocolo W3C https://github.com/w3c/webdriver/issues/406
     */
    public static ResultadoErrores getLogErrors(Level levelFrom, WebDriver webdriver, int maxErrors) {
        ResultadoErrores resultado = new ResultadoErrores();
        resultado.setResultado(ResultadoErrores.Resultado.OK);
        ITestContext ctx = TestMaker.getTestCase().getTestRunContext();
        ArrayList<String> listaLogError = new ArrayList<>();
        List<Boolean> supMaximosList = new ArrayList<>();
        try {
            Logs logs = webdriver.manage().logs();
            Iterator<String> itTiposLog = logs.getAvailableLogTypes().iterator();
            while (itTiposLog.hasNext()) {
                String tipoLog = itTiposLog.next();
                LogEntries logEntries = logs.get(tipoLog);
                int j = 0;
                for (LogEntry logEntry : logEntries) {
                    if (logEntry.getLevel().intValue() >= levelFrom.intValue()) {
                        resultado.setResultado(ResultadoErrores.Resultado.ERRORES);
                        String descError = "<br><b>Error Log!</b> Tipo: " + tipoLog.toUpperCase() + " | Level: " + logEntry.getLevel() + " | Descripción: " + logEntry.getMessage();
                        listaLogError.add("<br>" + descError);
            
                        int numErrors = 0;
                        if (ctx.getAttribute(descError) != null) {
                            numErrors = ((Integer)ctx.getAttribute(descError)).intValue();
                        }
            
                        // Comprobamos si ya se había superado el máximo de errores
                        if (j == 0) {
                            Boolean supMaximo = Boolean.valueOf(numErrors >= maxErrors);
                            supMaximosList.add(supMaximo);
                        }
            
                        numErrors += 1;
                        ctx.setAttribute(descError, Integer.valueOf(numErrors));
                        j += 1;
                    }
                }
            }
        }
        catch (Exception e) {
            //Parece que en el caso de Safari se produce una excepción al cargar algunos logs como el de 'performance'
        	Log4jConfig.pLogger.info("Problem to load the WebDriver error Log", e);
        }

        // Retornamos la lista de errores y el estado
        resultado.setListaLogError(listaLogError);
        if (resultado.getResultado() == ResultadoErrores.Resultado.ERRORES && supMaximosList.indexOf(Boolean.valueOf(false)) < 0) {
            resultado.setResultado(ResultadoErrores.Resultado.MAX_ERRORES);
        }
        return resultado;
    }
}