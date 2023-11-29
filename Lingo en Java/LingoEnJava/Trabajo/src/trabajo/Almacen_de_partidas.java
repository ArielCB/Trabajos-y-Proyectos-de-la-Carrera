/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajo;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.util.NoSuchElementException;

import java.util.Scanner;
/**
 *
 * @author margo
 */
public class Almacen_de_partidas {
    
    private Partida[] partidas;
    
    public Almacen_de_partidas(Almacen_de_jugadores almacenj,Almacen_de_palabras almacenp){
        partidas= new Partida[500];
        try{
            File f=new File("FicherosLingo\\FichPartidas.txt");
            Scanner scan= new Scanner(f);
            
            while(scan.hasNextInt()){
                int indice = scan.nextInt();
                String regalo_de_palabra=scan.next();
                boolean rdp;
                if(regalo_de_palabra.equals("true"))rdp=true;
                else rdp=false;
                String primeraLetra=scan.next();
                boolean PL;
                if(primeraLetra.equals("true"))PL=true;
                else PL=false;
                int numPalabras=scan.nextInt();
                String nombre1=scan.next();
                String nombre2=scan.next();
                Jugador J1=almacenj.getJugador(nombre1);
                Jugador J2=almacenj.getJugador(nombre2);
                Palabra[] palabras=new Palabra[numPalabras];
                for (int i=0;i<numPalabras;i++){
                    palabras[i]=almacenp.getPalabra(scan.next(),scan.nextInt());
                }
                Marcador marcador= new Marcador();
                marcador.setPuntos_J1(scan.nextInt());
                marcador.setPuntos_J2(scan.nextInt());
                partidas[indice]= new Partida(indice,rdp,PL,numPalabras,J1,J2,palabras,marcador);
            }
            scan.close();
        }catch(IOException e){
            System.out.println("Excepcion de E/S: "+e);
        }catch(NoSuchElementException e){
            System.out.println("Runtime exception: "+e);
        }
    }
    
    public Partida[] getPartidas(){
        return partidas;
    }
    
    public void setPartida(Partida par,Almacen_de_palabras almacenp){
        int id=par.getId();
        partidas[id]=par;
        try{
            FileWriter archivo=new FileWriter("FicherosLingo\\FichPartidas.txt",true);
            BufferedWriter buffer=new BufferedWriter(archivo);
            PrintWriter writer= new PrintWriter(buffer);
            
            writer.println(par.getId() +" "+par.getRDP()+" "+par.getPL()+" "+par.getNumPal());
            
            writer.println(par.getJ1().GetNombre()+" "+par.getJ2().GetNombre());
            for(int i=0;i<par.getNumPal();i++){
                almacenp.setPalabra(par.getPalabras()[i]);
                writer.print(String.valueOf(par.getPalabras()[i].getPalabra())+" ");
                if(par.getPalabras()[i].getPalabra().length==5)writer.print((almacenp.GetCinco().size()-1)+" ");
                else writer.print((almacenp.GetSeis().size()-1)+" ");
            }
            writer.println();
            writer.println(par.getMarcador().getPuntos_J1()+" "+par.getMarcador().getPuntos_J2());
            writer.println();
            writer.close();
            
        }catch(IOException e){
            System.out.print("Excepcion de E/S:"+e);
        }
    }
    
    public void info_partidas(){
        Scanner scan = new Scanner(System.in);
        int cont=0;
        while(partidas[cont]!=null){
            cont++;
        }
        int indice=cont-1;
        boolean salir=true;
        if(indice==-1){
            salir=false;
            System.out.println("No hay partidas");
        }
        while(salir){
            System.out.println("Introduce el identificador de la partida. 0-"+indice+"  o -1(todas)");
            indice=scan.nextInt();
            if(indice==-1){
                salir=false;
                for(int i=0;i<cont;i++){
                    System.out.println("Partida "+partidas[i].getId());
                    partidas[i].info_partida();
                }
            }
            else{
                partidas[indice].info_partida();
                System.out.println();
                System.out.println("¿Acceder a otra partida? 0(no) o 1(si)");
                indice=scan.nextInt();
                if(indice!=1) salir=false;
            }
        }
    }
}
