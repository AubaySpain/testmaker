package com.github.jorge2m.testmaker.utils.filter.resources;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Test
@SuppressWarnings({"unused"})
public class ClassWithTCasesStub {
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
            
    @BeforeMethod(groups={"Micuenta", "Canal:all_App:all"})
    @Parameters({"brwsr-path","urlBase", "AppEcom", "Channel", "xmlPaises"})
    public void login(String bpath, String urlBase, String appEcom, String channel, String xmlPaises, ITestContext context, Method method) throws Exception {
        //
    }

    @AfterMethod (groups={"Micuenta", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        //
    }       

    @Test (
    	testName="MIC001",
        groups={"Micuenta", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
        description="Verificar opciones de 'mi cuenta'")
    @Parameters({"userConDevolucionPeroSoloEnPRO", "passwordUserConDevolucion"})
    public void Opciones_Mi_Cuenta(String userConDevolucionPeroNoEnPRO, String passwordUserConDevolucion, ITestContext context, Method method) throws Exception {
        //
    }
    
    @Test (
    	testName="MIC002",
        groups={"Micuenta", "Canal:mobile_App:shop"}, alwaysRun=true, 
        description="Consulta de mis compras con un usuario con datos a nivel de Tienda y Online")
    @Parameters({"userConComprasPeroSoloOnlineEnPRO", "passwordUserConCompras"})
    public void CheckConsultaMisCompras(String userConCompras, String passwordUserConCompras, ITestContext context, Method method) throws Exception {
        //
    }    
    
    @Test (
    	testName="GPO001",
        groups={"GaleriaProducto", "Canal:all_App:all"}, alwaysRun=true, 
        description="[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color")    
    public void Galeria_Camisas(ITestContext context, Method method) throws Exception {
        //
    }
    
    @Test (
    	testName="BOR001",    		
        groups={"Bolsa", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Añadir artículo a la bolsa")
    public void AddBolsaFromGaleria_NoReg(ITestContext context, Method method) throws Exception {
        //
    }
    
}
