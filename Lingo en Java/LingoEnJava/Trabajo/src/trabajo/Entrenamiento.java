
package trabajo;


public class Entrenamiento {
    
    private int puntos;
    private boolean regalo_de_palabra;
    
    public Entrenamiento(){
        puntos=0;
        regalo_de_palabra=true;
    }
    public int mostrar_puntos(){
        return puntos;
    }
    
    public boolean usar_Pista_de_Palabra(){
        if(puntos>=3)return true;
        return false;
    }
    
    public boolean getRDP(){
        return regalo_de_palabra;
    }
    
    public void setRDP(boolean rdp){
        regalo_de_palabra=rdp;
    }
    
    public void setPuntos(int pt){
        puntos=pt;
    }
    
}
