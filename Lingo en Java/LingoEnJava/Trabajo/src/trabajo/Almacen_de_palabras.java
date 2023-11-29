
package trabajo;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Almacen_de_palabras {
    
    private ArrayList <Palabra> palabras_de_cinco;
    private ArrayList <Palabra> palabras_de_seis;
  
 
    
    public Almacen_de_palabras(){
        palabras_de_cinco=new ArrayList();
        palabras_de_seis=new ArrayList();
    
        try{
            FileReader f = new FileReader("FicherosLingo\\PalabrasJugadas5.txt");
            BufferedReader buffer = new BufferedReader(f);
            
            String linea;
            while((linea=buffer.readLine())!=null){
                char[] palabra=linea.substring(0,5).toCharArray();
                String rdl=linea.substring(6,10);
                boolean RDL;
                if(rdl.equals("true"))RDL=true;
                else RDL=false;
                String intento;
                Intento[] ints=new Intento[5];
                int cont=0;
                while(linea.length()>(11+(cont*6))){
                    ints[cont]= new Intento(linea.substring(11+(cont*6),16+(cont*6)).toCharArray());
                    cont++;
                }
                palabras_de_cinco.add(new Palabra(palabra,RDL,ints));
            }
            buffer.close();
            f = new FileReader("FicherosLingo\\PalabrasJugadas6.txt");
            buffer = new BufferedReader(f);
            
            
            while((linea=buffer.readLine())!=null){
                char[] palabra=linea.substring(0,6).toCharArray();
                String rdl=linea.substring(7,11);
                boolean RDL;
                if(rdl.equals("true"))RDL=true;
                else RDL=false;
                String intento;
                Intento[] ints=new Intento[5];
                int cont=0;
                while(linea.length()>(12+cont*7)){
                    ints[cont]= new Intento(linea.substring(12+cont*7,18+cont*7).toCharArray());
                    cont++;
                }
                palabras_de_seis.add(new Palabra(palabra,RDL,ints));
            }
            buffer.close();
        }catch(IOException e){
            System.out.println("Excepcion de E/S:"+e);
        }
    }
    
    public void setPalabra(Palabra pal){
        if(pal.getNL()==NumLetras.CINCO){
            palabras_de_cinco.add(pal);
            try{
                FileWriter archivo=new FileWriter("FicherosLingo\\PalabrasJugadas5.txt",true);
                BufferedWriter buffer=new BufferedWriter(archivo);
                PrintWriter writer= new PrintWriter(buffer);
                
                writer.print(String.valueOf(pal.getPalabra())+" ");
                if(pal.GetRDL())writer.print("true ");
                else writer.print("fals ");
                boolean salir=true;
                int cont=0;
                while(salir && cont<5){
                    if(pal.getIntentos()[cont]==null)salir=false;
                    else{
                        writer.print(String.valueOf(pal.getIntentos()[cont].getIntento())+" ");
                    }
                    cont++;
                }
                writer.println();
                writer.close();
            }catch(IOException e){
                System.out.print("Excepcion de E/S:"+e);
            }
        }
        else{
            palabras_de_seis.add(pal);
            try{
                FileWriter archivo=new FileWriter("FicherosLingo\\PalabrasJugadas6.txt",true);
                BufferedWriter buffer=new BufferedWriter(archivo);
                PrintWriter writer= new PrintWriter(buffer);
                
                writer.print(String.valueOf(pal.getPalabra())+" ");
                if(pal.GetRDL())writer.print("1 ");
                else writer.print("0 ");
                boolean salir=true;
                int cont=0;
                while(salir && cont<5){
                    if(pal.getIntentos()[cont]==null)salir=false;
                    else{
                        writer.print(String.valueOf(pal.getIntentos()[cont].getIntento())+" ");
                    }
                    cont++;
                }
                writer.println();
                writer.close();
            }catch(IOException e){
                System.out.print("Excepcion de E/S:"+e);
            }
        }
    }
    
    public Palabra getPalabra(String palabra, int id){
        if(palabra.length()==5){
            for(Palabra aux: palabras_de_cinco){
                if(palabra.equals(String.valueOf(aux.getPalabra()))&& id==palabras_de_cinco.indexOf(aux))return aux;
            }
        }
        else{
            for(Palabra aux: palabras_de_seis){
                if(palabra.equals(String.valueOf(aux.getPalabra()))&& id==palabras_de_cinco.indexOf(aux))return aux;
            }
        }
        return new Palabra(palabra.toCharArray());
    }
    
    public ArrayList GetCinco(){
     return palabras_de_cinco;
   }
    
    public void SetCinco(ArrayList pal5){
     palabras_de_cinco=pal5;
   }
    
    public ArrayList GetSeis(){
     return palabras_de_seis;
   }
    
    public void SetSeis(ArrayList pal6){
     palabras_de_seis=pal6;
   }
    
    
    public void cargarFichero(){
    
        try{
            FileReader mi_archivo = new FileReader("FicherosLingo\\Configuracion.txt");
            BufferedReader mi_buffer=new BufferedReader(mi_archivo);
            
            File fi=new File("FicherosLingo\\Configuracion.txt");
            Scanner scan=new Scanner(fi);
            int num_letras1=scan.nextInt();
            int num_palabras=scan.nextInt();
            String siOno=mi_buffer.readLine().substring(4,6);
            boolean PL;
            if(siOno.equals("si"))PL=true;
            else PL=false;
            scan.close();
            
            if(num_letras1==5)Configuracion.SetNumLetras(NumLetras.CINCO);
            else Configuracion.SetNumLetras(NumLetras.SEIS);
            Configuracion.SetNumPalabras(num_palabras);
            Configuracion.SetPrimeraLetra(PL);
            
            FileWriter f=new FileWriter("FicherosLingo\\Palabras.txt");
            BufferedWriter buffer=new BufferedWriter(f);
            PrintWriter writer=new PrintWriter(buffer);
            
            writer.flush();
            writer.close();
            
            f=new FileWriter("FicherosLingo\\Palabras.txt",true);
            buffer = new BufferedWriter(f);
            writer= new PrintWriter(buffer);
            String linea;
            while((linea=mi_buffer.readLine())!=null){
                writer.println(linea);
            }
            mi_buffer.close();
            writer.close();
        }catch(IOException e){
            System.out.println("Excepción de E/S: " + e);
        }catch(NoSuchElementException e){
            System.out.println("Runtime Exception:"+e);
        }
    }  
}
