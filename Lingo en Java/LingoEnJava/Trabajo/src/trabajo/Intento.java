
package trabajo;

import java.util.Scanner;

public class Intento {
    
    private char[] intento;
    private char[] verificacion;
    
    public Intento(){
        verificacion= new char[5];
    }
    
    public Intento(char[] inten){
        intento=inten;
        verificacion=new char[5];
    }

    public char[] recoger_intento(){
        Scanner scan = new Scanner(System.in);
        intento= scan.next().toCharArray();
        verificacion = new char[intento.length];
        return intento;
    }
    
    public char[] getIntento(){
        return intento;
    }
    
    public void setIntento(char[] inten){
        intento=inten;
    }
    
    public char[] getVerificacion(){
        return verificacion;
    }
    
    public void setVerificacion(char[] ver){
        verificacion=ver;
    }
}
