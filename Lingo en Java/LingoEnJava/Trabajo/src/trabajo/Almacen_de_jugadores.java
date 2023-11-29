
package trabajo;

import java.util.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class Almacen_de_jugadores {
    
    private TreeSet <Jugador> mis_jugadores;
   
            
    public Almacen_de_jugadores(){
        mis_jugadores= new TreeSet();
        try{
            
            File f=new File("FicherosLingo\\FichJugadores.txt");
            Scanner scan= new Scanner(f);
            
            scan.next();
            while(scan.hasNext()){
                String nombre=scan.next();
                String contrasena=scan.next();
                int ganadas=scan.nextInt();
                int empatadas=scan.nextInt();
                int perdidas=scan.nextInt();
                int puntos=scan.nextInt();
                Jugador j= new Jugador(nombre,contrasena,ganadas,empatadas,perdidas,puntos);
                mis_jugadores.add(j);
            }
            scan.close();
        }catch(IOException e){
            System.out.println("Excepcion de E/S:"+e);
        }catch(NoSuchElementException e){
            System.out.println("Runtime exception:"+e);
        }
    }
    
    public Jugador getJugador(String nombre){
        for(Jugador aux: mis_jugadores){
            if(nombre.equals(aux.GetNombre()))return aux;
        }
        return new Jugador(nombre);
    }
    
    public void setJugadores(){
        try{
            FileWriter f= new FileWriter("FicherosLingo\\FichJugadores.txt");
            BufferedWriter buffer= new BufferedWriter(f);
            PrintWriter writer=new PrintWriter(buffer);
            
            writer.flush();
            writer.close();
            
            f= new FileWriter("FicherosLingo\\FichJugadores.txt",true);
            buffer= new BufferedWriter (f);
            writer= new PrintWriter(buffer);
            writer.print("Administrador: ");
            Jugador admin =getJugador("Ariel");
            Estadisticas stats=admin.GetEstadisticas();
            writer.println(admin.GetNombre()+" "+admin.GetContrasena()+" "+stats.GetGanadas()+" "+stats.GetEmpatadas()+" "+stats.GetPerdidas()+" "+stats.GetPuntos());
            for(Jugador aux:mis_jugadores){
                if(!aux.equals(admin)){
                    stats= aux.GetEstadisticas();
                writer.println(aux.GetNombre()+" "+aux.GetContrasena()+" "+stats.GetGanadas()+" "+stats.GetEmpatadas()+" "+stats.GetPerdidas()+" "+stats.GetPuntos());
                }
            }
            writer.close();
        }catch(IOException e){
            System.out.println("Excepcion de E/S:"+e);
        }
    }
    
    public boolean autenticar(Jugador J){
    
        boolean autenticado=false;
        for (Jugador aux : mis_jugadores) {
            if(J.GetNombre().equals(aux.GetNombre()) && J.GetContrasena().equals(aux.GetContrasena())) 
                autenticado=true;
        }
        return autenticado;
    }
    
    public TreeSet <Jugador> ranking_ordenado_por_victorias(){
    
        TreeSet <Jugador> ordena=new TreeSet(new ComparaVictorias());
    
        for(Jugador J: mis_jugadores){
            ordena.add(J);
        }
        return ordena;
    }
   
    
    public TreeSet <Jugador> ranking_ordenado_por_nombre(){
        return mis_jugadores;  
    }
    
    public void alta(Jugador J){
        mis_jugadores.add(J);
        try{
            FileWriter f= new FileWriter("FicherosLingo\\FichJugadores.txt", true);
            BufferedWriter buffer= new BufferedWriter(f);
            PrintWriter writer=new PrintWriter(buffer);
            
            Estadisticas stats= J.GetEstadisticas();
            writer.println(J.GetNombre()+" "+J.GetContrasena()+" "+stats.GetGanadas()+" "+stats.GetEmpatadas()+" "+stats.GetPerdidas()+" "+stats.GetPuntos());
            writer.close();
        }catch(IOException e){
            System.out.println("Excepcion de E/S:"+e);
        }
    }
    
    public void baja(Jugador J){
        mis_jugadores.remove(J);
        try{
            FileWriter f= new FileWriter("FicherosLingo\\FichJugadores.txt");
            BufferedWriter buffer= new BufferedWriter(f);
            PrintWriter writer=new PrintWriter(buffer);
            
            writer.flush();
            writer.close();
            
            f= new FileWriter("FicherosLingo\\FichJugadores.txt",true);
            buffer= new BufferedWriter (f);
            writer= new PrintWriter(buffer);
            writer.print("Administrador: ");
            Jugador admin =getJugador("Ariel");
            Estadisticas stats=admin.GetEstadisticas();
            writer.println(admin.GetNombre()+" "+admin.GetContrasena()+" "+stats.GetGanadas()+" "+stats.GetEmpatadas()+" "+stats.GetPerdidas()+" "+stats.GetPuntos());
            for(Jugador aux:mis_jugadores){
                if(!aux.equals(admin)){
                    stats= aux.GetEstadisticas();
                writer.println(aux.GetNombre()+" "+aux.GetContrasena()+" "+stats.GetGanadas()+" "+stats.GetEmpatadas()+" "+stats.GetPerdidas()+" "+stats.GetPuntos());
                }
            }
            writer.close();
        }catch(IOException e){
            System.out.println("Excepcion de E/S:"+e);
        }
    }
    
}
