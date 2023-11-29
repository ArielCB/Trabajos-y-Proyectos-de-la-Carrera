package trabajo;

public class Estadisticas {
    private int ganadas;
    private int empatadas;
    private int perdidas;
    private int puntos;
    
    public Estadisticas(){
    
    ganadas=0;
    empatadas=0;
    perdidas=0;
    puntos=0;  
    }
    
    public Estadisticas(int gan,int emp, int per,int pt){
        ganadas=gan;
        empatadas=emp;
        perdidas=per;
        puntos=pt;
    }
    
    public int GetGanadas(){
    
    return ganadas;
    }
    
    public void SetGanadas(int gan){
    
    ganadas=gan;
    }
    
    public int GetEmpatadas(){
    
    return empatadas;
    }
    
    public void SetEmpatadas(int emp){
    
    empatadas=emp;
    }
    
    public int GetPerdidas(){
    
    return perdidas;
    }
    
    public void SetPerdidas(int per){
    
    perdidas=per;
    }
    
    public int GetPuntos(){
    
    return puntos;
    }
    
    public void SetPuntos(int pun){
    
    puntos=pun;
    }
    
    @Override
    public String toString(){
    
    return " Victorias:"+ GetGanadas()+ " Perdidadas:"+ GetPerdidas()+ " Empatadas:"+ GetEmpatadas()+ " Puntos:"+ GetPuntos();
  

    }
}
