
package trabajo;

import java.util.Scanner;
import java.util.Objects;

public class Partida {
    
    private int identificador;
    private boolean regalo_de_palabra;
    private boolean primeraLetra;
    private int numPalabras;
    private Jugador j1;
    private Jugador j2;
    private Palabra[] palabra;
    private Marcador marcador;
    
    public Partida(int id, boolean rdp,boolean pl, int np,Jugador ju1,Jugador ju2,Palabra[] pal,Marcador mar){
        identificador=id;
        regalo_de_palabra=rdp;
        primeraLetra=pl;
        numPalabras=np;
        j1=ju1;
        j2=ju2;
        palabra=pal;
        marcador=mar;
        j1.setPartida(this);
        j2.setPartida(this);
    }
    
    public Partida(int id,Jugador a, Jugador b){
        identificador=id;
        regalo_de_palabra=true;
        primeraLetra=Configuracion.GetPrimeraLetra();
        numPalabras=Configuracion.GetNumPalabras();
        j1=a;
        j2=b;
        palabra=new Palabra[10];
        marcador= new Marcador();
        j1.setPartida(this);
        j2.setPartida(this);
    }
    
    public Partida(){
        primeraLetra=Configuracion.GetPrimeraLetra();
    }
    
    public int getNumPal(){
        return numPalabras;
    }
    
    public void setNumPal(int np){
        numPalabras=np;
    }
    
    public boolean getRDP(){
        return regalo_de_palabra;
    }
    
    public void setRDP(boolean rdp){
        regalo_de_palabra=rdp;
    }
    
    public boolean getPL(){
        return primeraLetra;
    }
    
    public void setPL(boolean pl){
        primeraLetra=pl;
    }
    
    public Jugador getJ1(){
        return  j1;
    }
    
    public void setJ1(Jugador a){
        j1=a;
    }
    
    public Jugador getJ2(){
        return  j2;
    }
    
    public void setJ2(Jugador a){
        j2=a;
    }
    
    public int getId(){
        return identificador;
    }
    
    public void setId(int id){
        identificador=id;
    }
    
    public Palabra[] getPalabras(){
        return palabra;
    }
    
    public void setPalabras(Palabra[] pal){
        palabra=pal;
    }
    
    public Marcador getMarcador(){
        return marcador;
    }
    
    public void setMarcador(Marcador mar){
        marcador=mar;
    }
    
    public void cambiarTurno(){
        int cont=0;
        boolean rdp1=true;
        boolean rdp2=true;
        while(cont<numPalabras){
            System.out.print("Turno de ");
            if(cont%2==0){
                System.out.println(j1.GetNombre());
                regalo_de_palabra=rdp1;
            }
            else {
                System.out.println(j2.GetNombre());
                regalo_de_palabra=rdp2;
            }
            palabra[cont]=new Palabra();
            if(!usar_pista_de_letra())palabra[cont].SetRDL(false);
            if(regalo_de_palabra && usar_pista_de_palabra()){
                System.out.println("¿Usar regalo de Palabra(-3 putnos)? 0(no) o 1(si)");
                Scanner scan =new Scanner(System.in);
                int siOno=scan.nextInt();
                if(siOno==1){
                    regalo_de_palabra=false;
                    System.out.println(palabra[cont].getPalabra());
                    if(cont%2==0){
                        marcador.setPuntos_J1(marcador.getPuntos_J1()-3);
                        rdp1=false;
                    }
                    else {
                        marcador.setPuntos_J2(marcador.getPuntos_J2()-3);
                        rdp2=false;
                    }
                }
            }
            if(primeraLetra)System.out.println("La primera letra es: "+palabra[cont].getPalabra()[0]);
            palabra[cont].jugar();
            if(usar_pista_de_letra() && !palabra[cont].GetRDL()){
                System.out.println("-1 punto(regalo de letra)");
                if(cont%2==0)marcador.setPuntos_J1(marcador.getPuntos_J1()-1);
                else marcador.setPuntos_J2(marcador.getPuntos_J2()-1);
            }
            actualizarMarcador();
            cont++;
            System.out.println();
        }
        if(!rdp1 || !rdp2)regalo_de_palabra=false;//registrar si cualquier jugador ha usado pista de palabra
        j1.GetEstadisticas().SetPuntos(j1.GetEstadisticas().GetPuntos()+marcador.getPuntos_J1());
        j2.GetEstadisticas().SetPuntos(j2.GetEstadisticas().GetPuntos()+marcador.getPuntos_J2());
        if(marcador.getPuntos_J1()>marcador.getPuntos_J2()){
            j1.GetEstadisticas().SetGanadas(j1.GetEstadisticas().GetGanadas()+1);
            j2.GetEstadisticas().SetPerdidas(j2.GetEstadisticas().GetPerdidas()+1);
        }
        else if(marcador.getPuntos_J1()<marcador.getPuntos_J2()){
            j2.GetEstadisticas().SetGanadas(j2.GetEstadisticas().GetGanadas()+1);
            j1.GetEstadisticas().SetPerdidas(j1.GetEstadisticas().GetPerdidas()+1);
        }
        else{
            j2.GetEstadisticas().SetEmpatadas(j2.GetEstadisticas().GetEmpatadas()+1);
            j1.GetEstadisticas().SetEmpatadas(j1.GetEstadisticas().GetEmpatadas()+1);
        }
    }
    
    public void actualizarMarcador(){
        int cont=numPalabras-1;
        while(palabra[cont]==null){
            cont--;
        }
        int puntos=palabra[cont].puntos_obtenidos();
        if(cont%2==0)marcador.setPuntos_J1(marcador.getPuntos_J1()+puntos);
        else marcador.setPuntos_J2(marcador.getPuntos_J2()+puntos);
    }
    
    public boolean usar_pista_de_letra(){
        int cont=numPalabras-1;
        while(palabra[cont]==null){
            cont--;
        }
        if(cont%2==0){
            if(marcador.getPuntos_J1()>=1)return true;
            return false;
        }
        if(marcador.getPuntos_J2()>=1)return true;
        return false;
    }
    
     public boolean usar_pista_de_palabra(){
         int cont=numPalabras-1;
        while(palabra[cont]==null){
            cont--;
        }
        if(cont%2==0){
            if(marcador.getPuntos_J1()>=3)return true;
            return false;
        }
        if(marcador.getPuntos_J2()>=3)return true;
        return false;
    }
     
     public void info_partida(){
         Scanner scan= new Scanner(System.in);
         int siOno;
         System.out.println();
         System.out.println("Jugador 1: "+j1.GetNombre());
         System.out.println("Jugador 2: "+j2.GetNombre());
         System.out.println();
         System.out.println("¿Ver regalo de palabra? 0(no) o 1(si)");
         siOno=scan.nextInt();
         if(siOno==1){
            System.out.println();
            System.out.print("Regalo de palabra: ");
            if(regalo_de_palabra)System.out.println("true");
            else System.out.println("false");
         }
         System.out.println();
         System.out.println("¿Ver palabras? 0(no) o 1(si)");
         siOno=scan.nextInt();
         if(siOno==1){
             int numP,cont;
             boolean salir;
             System.out.println();
             for(int i=0;i<numPalabras;i++){
                 numP=i+1;
                 System.out.println("Palabra "+(numP)+": "+String.valueOf(palabra[i].getPalabra()));
                 if(i%2==0)System.out.println("\tJugado por "+j1.GetNombre()+".");
                 else System.out.println("\tJugado por "+j2.GetNombre()+".");
                 System.out.println("Pista de letra: "+palabra[i].GetRDL());
                 System.out.println();
                 System.out.println("¿Ver intentos de la palabra? 0(no) o 1(si)");
                 siOno=scan.nextInt();
                 if(siOno==1)palabra[i].secuencia_resultados();
                 else System.out.println();
             }
         }
         System.out.println("¿Ver puntuacion? 0(no) o 1(si)");
         siOno=scan.nextInt();
         if(siOno==1){
             System.out.println();
             System.out.println("Puntos "+j1.GetNombre()+": "+marcador.getPuntos_J1());
             System.out.println("Puntos "+j2.GetNombre()+": "+marcador.getPuntos_J2());
         }
     }
     
    @Override
    public boolean equals(Object o){
        
        if(this==o)return true;
        if(o==null)return false;
        if(getClass()==o.getClass())return false;
        
        Partida partida= (Partida) o;
        return this.identificador==partida.getId();
    }
}
