
package trabajo;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Configuracion {
    
    
    private static NumLetras numLetras;
    private static int numPalabras;
    private static boolean primeraLetra;
    
    public Configuracion(){
        try{
            FileReader archivo=new FileReader("FicherosLingo\\Configuracion.txt");
            BufferedReader buffer= new BufferedReader(archivo);
            
            String linea1=buffer.readLine();
            int nl=linea1.charAt(0);
            int np=linea1.charAt(2);
            String pl=linea1.substring(4,6);
            if(pl.equals("si"))primeraLetra=true;
            else primeraLetra=false;
            if(nl==5)numLetras=NumLetras.CINCO;
            else numLetras=NumLetras.SEIS;
            numPalabras=np;
        }catch(IOException e){
            System.out.println("Excepcion de E/S:"+e);
        }
    }
    
    public static NumLetras GetNumLetras() { 
        return numLetras; 
    }
    
    public static void SetNumLetras(NumLetras nl) { 
        numLetras=nl; 
    }
 
    
    public static int GetNumPalabras(){
    return numPalabras;
    }
    
    public static boolean GetPrimeraLetra(){
    return primeraLetra;
    }
    
    public static void SetNumPalabras(int np){
    numPalabras=np;
    }
    
    public static void SetPrimeraLetra(boolean pl){
    primeraLetra=pl;
    }
    
}
