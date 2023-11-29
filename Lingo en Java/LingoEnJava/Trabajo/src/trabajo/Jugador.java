
package trabajo;

import java.util.Objects;

public class Jugador implements Comparable{
    
    private String nombre;
    private String contrasena;
    Estadisticas mis_estadisticas;
    Partida[] partidas_jugadas;
    
    public Jugador(){
        
    }
    
    public Jugador(String nom){
        nombre=nom;
        partidas_jugadas=new Partida[100];
    }
    
    public Jugador(String nom, String con){
        nombre=nom;
        contrasena=con;
        mis_estadisticas=new Estadisticas();
        partidas_jugadas=new Partida[100];
    }
    
    public Jugador(String nom, String con, int g, int e, int p, int pt){
        nombre=nom;
        contrasena=con;
        mis_estadisticas= new Estadisticas(g,e,p,pt);
        partidas_jugadas= new Partida[100];
    }
    
    public Partida[] getPartidas(){
        return partidas_jugadas;
    }
    
    public void setPartida(Partida partida){
        int cont=0;
        boolean salir=true;
        while(salir){
            if(partidas_jugadas[cont]==null){
                partidas_jugadas[cont]=partida;
                salir=false;
            }
            else if(partidas_jugadas[cont].equals(partida)){
                salir=false;
            }
            cont++;
        }
    }
    
    public Estadisticas GetEstadisticas(){
    
        return mis_estadisticas;
    }
    
    public void SetEstadisticas(Estadisticas est){
    
        mis_estadisticas=est;
    }
    
    public String GetNombre(){
    
        return nombre;
    }
    
    public void SetNombre(String nom){
    
        nombre=nom;
    }
    
    public String GetContrasena(){
    
        return contrasena;
    }
    
    public void SetContrasena(String con){
    
        contrasena=con;
    }
    
    public void partidas_contra_otro(Jugador J){
        int cont=0;
        boolean salir=true;
        while(salir){
            if(partidas_jugadas[cont]==null)salir=false;
            else{
                if(partidas_jugadas[cont].getJ1().equals(J) || partidas_jugadas[cont].getJ2().equals(J)){
                    System.out.println("Indice de partida: "+partidas_jugadas[cont].getId());
                    partidas_jugadas[cont].info_partida();
                }
            }
            cont++;
        }
    }
    
    @Override
    public int compareTo (Object O) {
        Jugador J1 = (Jugador) O;
        return nombre.compareTo (J1.nombre);
    }
    
    @Override
    public boolean equals(Object O){
        
        if(this==O)return true;
        if(O==null)return false;
        if(getClass()==O.getClass())return false;
    
        Jugador J1 = (Jugador) O;
        return this.nombre.equals(J1.nombre);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
    
    @Override
    public String toString(){
        return "Nombre: "+ GetNombre()+ mis_estadisticas.toString()+"\n";
    }
}
