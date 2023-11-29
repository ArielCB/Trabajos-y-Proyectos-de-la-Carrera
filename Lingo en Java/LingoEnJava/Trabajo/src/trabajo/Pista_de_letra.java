/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajo;

/**
 *
 * @author margo
 */
public class Pista_de_letra {
    
    private Palabra solucion;
    
    public Pista_de_letra(Palabra sol){
        solucion = sol;
    }
    
    public boolean regalar_letra(){
        int num_espacios = solucion.getPalabra().length;
        int cont=0;
        boolean salir=true;
        boolean[] correctas={false,false,false,false,false,false};
        if(Configuracion.GetPrimeraLetra()){
            correctas[0]=true;
            num_espacios--;
        }
        while(cont<5 && salir){
            if(solucion.getIntentos()[cont]==null)salir=false;
            else{
                for(int i=0;i<solucion.getPalabra().length;i++){
                    if(solucion.getIntentos()[cont].getVerificacion()[i]=='v'){
                        if(!correctas[i]){
                            num_espacios--;
                            correctas[i]=true;
                        }
                    }
                }
            }
            cont++;
        }
        if(num_espacios > 1){ 
            return true;
        }
        return false;
    }
   
   public void mostrar_palabra_actualizada(){
        int cont=0;
        boolean salir=true;
        char[] correctas= new char[solucion.getPalabra().length];
        while(cont<5 && salir){
            if(solucion.getIntentos()[cont]==null)salir=false;
            else{
                for(int i=0;i<solucion.getPalabra().length;i++){
                    if(solucion.getIntentos()[cont].getVerificacion()[i]=='v'){
                        correctas[i]=solucion.getPalabra()[i];
                    }
                }
            }
            cont++;
        }
        cont=solucion.getPalabra().length-1;
        salir=true;
        while(salir){
            if(correctas[cont]=='\0'){
                correctas[cont]=solucion.getPalabra()[cont];
                salir=false;
            }
            cont--;
        }
        if(Configuracion.GetPrimeraLetra())correctas[0]=solucion.getPalabra()[0];
        System.out.println(correctas);
    }
}
