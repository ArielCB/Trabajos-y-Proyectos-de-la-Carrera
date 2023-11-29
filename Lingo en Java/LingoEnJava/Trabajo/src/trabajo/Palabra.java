
package trabajo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Random;
import java.util.Scanner;


public class Palabra {
    
    
    private NumLetras numLetras;
    private char[] palabra;
    private boolean regalo_de_letra;
    private Intento[] mis_intentos;
    private Pista_de_letra pista;
    
    public Palabra(){
        numLetras=Configuracion.GetNumLetras();
        sacar_palabra_aleatoria();
        regalo_de_letra=true;
        mis_intentos= new Intento[5];
    }
    
    public Palabra(char[] pal){
        palabra=pal;
        if(palabra.length==5)numLetras=NumLetras.CINCO;
        else numLetras=NumLetras.SEIS;
        mis_intentos=new Intento[5];
    }
    
    public Palabra(char[] pal,boolean rdl,Intento[] ints){
        palabra=pal;
        if(palabra.length==5)numLetras=NumLetras.CINCO;
        else numLetras=NumLetras.SEIS;
        regalo_de_letra=rdl;
        mis_intentos=new Intento[5];
        for(int i=0;i<5;i++){
            mis_intentos[i]=ints[i];
        }
    }
    
    public NumLetras getNL(){
        return numLetras;
    }
    
    public void setNL(NumLetras nl){
        numLetras=nl;
    }
    
    public Intento[] getIntentos(){
        return mis_intentos;
    }
    
    public void setIntentos(Intento[] ints){
        for(int i=0;i<5;i++){
            mis_intentos[i]=ints[i];
        }
    }
    
    public char[] getPalabra(){
        return palabra;
    }
    
    public void setPalabra(char[] pal){
        palabra=pal;
    }
    
    public boolean GetRDL(){
    return regalo_de_letra;
    }
    
    public void SetRDL(boolean reg){
    regalo_de_letra=reg;
    }
    
    public void jugar(){
        System.out.println("La palabra a adivinar tiene "+numLetras.cifra()+" letras.");
        
        int cont=0;
        boolean acierto=false;
        boolean rdl=true;
    
        while(cont<5 && !acierto){
            if(regalo_de_letra && rdl){
                System.out.println("¿Usar la pista de letra(-1 punto)? 0(no) o 1(si)");
                Scanner scan = new Scanner(System.in);
                int siOno = scan.nextInt();
                if(siOno==1){
                    pista= new Pista_de_letra(this);
                    if(pista.regalar_letra()){
                        regalo_de_letra=false;
                        pista.mostrar_palabra_actualizada();
                    }
                    else {
                        System.out.println("No se puede usar con una letra restante;");
                        rdl=false;
                    }
                }
            }
            System.out.println("Escriba su intento: ");
            mis_intentos[cont]= new Intento();
            char[]intento=mis_intentos[cont].recoger_intento();
            if(String.valueOf(palabra).equals(String.valueOf(intento))) {
                acierto=true;
                mostrar_intento_resuelto();
                System.out.println("¡¡Correcto!!");
                System.out.println();
            }
            else if(cont<4){
                mostrar_intento_resuelto();
                System.out.println();
            }
            else{
                mostrar_intento_resuelto();
                System.out.println();
                System.out.println("Fallaste, la palabra era: "+String.valueOf(palabra));
                System.out.println();
            }
            cont++;
        }
        System.out.println("Has obtenido " + puntos_obtenidos() + " puntos");
        //System.out.println();
        //secuencia_resultados();
    }
    
    
    private void comprobar_colocadas(){
        
        int cont=4;
        
        while(cont>=0 && mis_intentos[cont]==null){
            cont--;
        }
        
        char[]intento=mis_intentos[cont].getIntento();
        
        for(int i=0;i<numLetras.cifra();i++){
            if(intento[i]==palabra[i]){
                mis_intentos[cont].getVerificacion()[i]='v';
            }
        }
    }
    
 
    private void comprobar_distinta_posicion(){
     
        int cont=4;
        
        while(cont>=0 && mis_intentos[cont]==null){
            cont--;
        }
        
        char[]intento=mis_intentos[cont].getIntento();
        
        for(int i=0;i<numLetras.cifra();i++){
            for(int j=0; j<numLetras.cifra();j++){
                if(intento[i]==palabra[j]&& intento[i]!=palabra[i]){//comprueba que la letra esté en otras posiciones y que no esté en el sitio correcto
                    mis_intentos[cont].getVerificacion()[i]='a';
                }
            }
        }
    }
    
    public void mostrar_intento_resuelto(){
        String amarillo="\033[33m";
        String verde="\033[32m";
        String negro="\033[30m";
        comprobar_colocadas();
        comprobar_distinta_posicion();
        
        int cont=4;
        
        while(mis_intentos[cont]==null){
            cont--;
        }
        char[] intento=mis_intentos[cont].getIntento();
        
        for(int i=0;i<intento.length;i++){
            switch(mis_intentos[cont].getVerificacion()[i]){
                case 'v':
                    System.out.print(verde+intento[i]);
                    break;
                case 'a':
                    System.out.print(amarillo+intento[i]);
                    break;
                default:
                    System.out.print(negro+intento[i]);
            }
        }
        System.out.println(negro);
    }
    
    
    public int puntos_obtenidos(){
        int puntos=0;
        if(mis_intentos[4]!=null && !String.valueOf(mis_intentos[4].getIntento()).equals(String.valueOf(palabra))){
            return (puntos);
        }else{
            puntos++;
            int cont=4;
            while(mis_intentos[cont]==null){
                cont--;
                puntos++;
            }
            return puntos;
        }
    }
    
    public void sacar_palabra_aleatoria(){
        String[] palabras=new String[1000];
        int cont=0;
        try{
            FileReader f = new FileReader("FicherosLingo\\Palabras.txt");
            BufferedReader buffer = new BufferedReader(f);
            
            String linea;
            if(numLetras==NumLetras.CINCO){
                boolean palabras6=false;
                while((linea=buffer.readLine())!=null){
                    if(linea.equals("6")){
                        palabras6=true;
                    }
                    else if(!palabras6){
                        palabras[cont]=linea;
                        cont++;
                    }
                }
                int idRandom=(int)(Math.random()*(cont-1));
                palabra=palabras[idRandom].toCharArray();
            }
            else{
                boolean palabras6=false;
                while((linea=buffer.readLine())!=null){
                    if(palabras6){
                        palabras[cont]=linea;
                        cont++;
                    }
                    else if(linea.equals("6"))palabras6=true;
                }
                int idRandom=(int)(Math.random()*(cont-1));
                palabra=palabras[idRandom].toCharArray();
            }
        }catch(IOException e){
            System.out.println("Excepcion de E/S:"+e);
        }
    }
    
    //A que se refiere
    public void secuencia_resultados(){
        boolean salir=true;
        Intento[] aux= new Intento[5];
        for(int i=0;i<5 && salir;i++){
            if(mis_intentos[i]==null)salir=false;
            else{
                for(int j=0;j<5;j++){
                    aux[j]=mis_intentos[j];
                }
                for(int j=0;j<5;j++){
                    if(i!=j)mis_intentos[j]=null;
                }
                int numInt=i+1;
                System.out.println("Intento "+numInt+":");
                mostrar_intento_resuelto();
                System.out.println();
                for(int j=0;j<5;j++){
                    mis_intentos[j]=aux[j];
                }
            }
        }
    }
   
}
