
package trabajo;


public class Marcador {
   private int puntos_j1;
   private int puntos_j2;
    
    public Marcador(){
        puntos_j1=0;
        puntos_j2=0;    
    }
    
    public int getPuntos_J1(){
        return puntos_j1;
    }
    
    public void setPuntos_J1(int puntos){
        puntos_j1=puntos;
    }
    
    public int getPuntos_J2(){
        return puntos_j2;
    }
    
    public void setPuntos_J2(int puntos){
        puntos_j2=puntos;
    }
    
}
