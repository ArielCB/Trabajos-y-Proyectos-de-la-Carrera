
package trabajo;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Trabajo {
    public static void main(String[] args){
        
        try{
            File f= new File("FicherosLingo\\Configuracion.txt");
            Scanner scane= new Scanner(f);
            
            
            int nl=scane.nextInt();
            if(nl==5)Configuracion.SetNumLetras(NumLetras.CINCO);
            else Configuracion.SetNumLetras(NumLetras.SEIS);
            Configuracion.SetNumPalabras(scane.nextInt());
            String pl=scane.next();
            if(pl.equals("si"))Configuracion.SetPrimeraLetra(true);
            else Configuracion.SetPrimeraLetra(false);
            scane.close();
        }catch(IOException e){
            System.out.println("Excepciion de E/S:"+e);
        }catch(NoSuchElementException e){
            System.out.println("Runtime Exception:"+e);
        }
        Almacen_de_palabras almacen_palabras= new Almacen_de_palabras();
        Almacen_de_jugadores almacen_jugadores= new Almacen_de_jugadores();
        Almacen_de_partidas almacen_partidas= new Almacen_de_partidas(almacen_jugadores,almacen_palabras);
        
        //almacen_palabras.cargarFichero();
        boolean salir=true;
        boolean registrado=false;
        boolean admin=false;
        int respuesta;
        Scanner scan= new Scanner(System.in);
        Jugador J1= new Jugador();
        while(salir){
            if(!registrado){
                System.out.println("¿Qué deseas hacer? 0(Entrenamiento) 1(Iniciar sesion) 2(Iniciar como Admin) 3(salir)");
                respuesta=scan.nextInt();
                switch(respuesta){
                    case 0:
                        Entrenamiento entreno = new Entrenamiento();
                        boolean exit=true;
                        while(exit){
                            Palabra palabra= new Palabra();
                            if(entreno.getRDP() && entreno.usar_Pista_de_Palabra()){
                                System.out.println("¿Usar regalo de Palabra(-3 putnos)? 0(no) o 1(si)");
                                respuesta=scan.nextInt();
                                if(respuesta==1){
                                    entreno.setRDP(false);
                                    entreno.setPuntos(entreno.mostrar_puntos()-3);
                                    System.out.println("La palabra es: "+String.valueOf(palabra.getPalabra()));
                                }
                            }
                            if(entreno.mostrar_puntos()<1)palabra.SetRDL(false);
                            if(Configuracion.GetPrimeraLetra())System.out.println("La primera letra es: "+palabra.getPalabra()[0]);
                            palabra.jugar();
                            if(entreno.mostrar_puntos()>=1 && !palabra.GetRDL()){
                                System.out.println("-1 punto(regalo de letra)");
                                entreno.setPuntos(entreno.mostrar_puntos()-1);
                            }
                            entreno.setPuntos(entreno.mostrar_puntos()+palabra.puntos_obtenidos());
                            System.out.println("Puntos totales: "+entreno.mostrar_puntos());
                            System.out.println();
                            System.out.println("¿Seguir entrenamiento? 0(no) o 1(si)");
                            respuesta=scan.nextInt();
                            if(respuesta==0)exit=false;
                        }
                        break;
                    case 1:
                        boolean salida=true;
                        while(!registrado && salida){
                            System.out.println("Introducir nombre de usuario y contraseña");
                            String nombre=scan.next();
                            String contrasena=scan.next();
                            Jugador J=new Jugador(nombre,contrasena);
                            if(almacen_jugadores.autenticar(J)){
                                System.out.println("Sesión iniciada");
                                J1=almacen_jugadores.getJugador(nombre);
                                registrado=true;
                            }
                            else{
                                System.out.println("Usuario o contraseña incorrectos");
                                System.out.println("Salir(0) o Volver a intentar(1)");
                                respuesta=scan.nextInt();
                                if(respuesta==0)salida=false;
                            }
                        }
                        break;
                    case 2:
                        boolean sal=true;
                        try{
                            File f= new File("FicherosLingo\\FichJugadores.txt");
                            Scanner escaner=new Scanner(f);
                            escaner.next();
                            String nomAdmin=escaner.next();
                            String contraAdmin=escaner.next();
                            escaner.close();
                            while(!registrado && sal){
                                System.out.println("Introducir nombre de usuario y contraseña");
                                String nombre=scan.next();
                                String contrasena=scan.next();
                                if(nombre.equals(nomAdmin)&& contrasena.equals(contraAdmin)){
                                    System.out.println("Sesión iniciada");
                                    J1=almacen_jugadores.getJugador(nombre);
                                    registrado=true;
                                    admin=true;
                                }
                                else{
                                    System.out.println("Usuario o contraseña incorrectos");
                                    System.out.println("Salir(0) o Volver a intentar(1)");
                                    respuesta=scan.nextInt();
                                    if(respuesta==0)sal=false;
                                }
                            }
                        }catch(IOException e){
                            System.out.println("Excepcion de E/S:"+e);
                        }catch(NoSuchElementException e){
                            System.out.println("Runtime Exception:"+e);
                        }
                        break;
                    case 3:
                        salir=false;
                        break;
                    default:
                        System.out.println("Respuesta no válida");
                }
            }
            else if(admin){
                System.out.println("0(Dar de alta a jugador) 1(Dar de baja) 2(configuracion) 3(cargar fichero)");
                System.out.println("4(informacion de las partidas) 5(continuar como jugador) 6(cerrar sesion)");
                respuesta=scan.nextInt();
                switch(respuesta){
                    case 0:
                        System.out.println("Introduce nombre y contraseña del nuevo jugador");
                        String nombre=scan.next();
                        String contrasena=scan.next();
                        Jugador J =new Jugador(nombre,contrasena);
                        boolean existe=false;
                        for(Jugador aux:almacen_jugadores.ranking_ordenado_por_nombre()){
                            if(nombre.equals(aux.GetNombre()))existe=true;
                        }
                        if(existe)System.out.println("Ese jugador ya existe");
                        else almacen_jugadores.alta(J);
                        break;
                    case 1:
                        System.out.println("Introduce el nombre del jugador a eliminar");
                        String nomb=scan.next();
                        boolean exist=false;
                        for(Jugador aux:almacen_jugadores.ranking_ordenado_por_nombre()){
                            if(aux.GetNombre().equals(nomb))exist=true;
                        }
                        if(exist)almacen_jugadores.baja(almacen_jugadores.getJugador(nomb));
                        else System.out.println("El jugador no existe");
                        break;
                    case 2:
                        boolean exit=true;
                        while(exit){
                            System.out.println("0(Numero de letras) 1(Numero de palabras) 2(Primera letra) 3(Salir de configuracion)");
                            respuesta=scan.nextInt();
                            switch(respuesta){
                                case 0:
                                    System.out.println("Introduce nuevo numero de letras(5 o 6)");
                                    respuesta=scan.nextInt();
                                    if(respuesta==5)Configuracion.SetNumLetras(NumLetras.CINCO);
                                    else if(respuesta==6)Configuracion.SetNumLetras(NumLetras.SEIS);
                                    break;
                                case 1:
                                    System.out.println("Introduce nuevo numero de palabras(1-10)");
                                    respuesta=scan.nextInt();
                                    if(respuesta>0 && respuesta<11)Configuracion.SetNumPalabras(respuesta);
                                    break;
                                case 2:
                                    System.out.println("¿Quieres que se vea la primera letra? 0(no) o 1 (si)");
                                    respuesta=scan.nextInt();
                                    if(respuesta==1)Configuracion.SetPrimeraLetra(true);
                                    else if(respuesta==0)Configuracion.SetPrimeraLetra(false);
                                    break;
                                case 3:
                                    exit=false;
                                    break;
                                default:
                                    System.out.println("Respuesta no válida");
                            }
                        }
                        break;
                    case 3:
                        almacen_palabras.cargarFichero();
                        break;
                    case 4:
                        almacen_partidas.info_partidas();
                        break;
                    case 5:
                        admin=false;
                        break;
                    case 6:
                        registrado=false;
                        admin=false;
                        break;
                    default:
                        System.out.println("Respuesta no válida");
                }
            }
            else{
                System.out.println("0(Jugar partida) 1(Ver perfil) 2(ver ranking) 3(Cerrar sesion)");
                respuesta=scan.nextInt();
                switch(respuesta){
                    case 0:
                        System.out.println("Jugador 2 introduce nombre y contraseña");
                        String nombre=scan.next();
                        String contraseña=scan.next();
                        Jugador J2= new Jugador(nombre,contraseña);
                        if(almacen_jugadores.autenticar(J2)){
                            J2=almacen_jugadores.getJugador(nombre);
                            int cont=0;
                            while(almacen_partidas.getPartidas()[cont]!=null){
                                cont++;
                            }
                            Partida partida= new Partida(cont,J1,J2);
                            partida.cambiarTurno();
                            almacen_partidas.setPartida(partida, almacen_palabras);
                            almacen_jugadores.setJugadores();
                        }
                        else System.out.println("Nombre o contraseña incorrectos");
                        break;
                    case 1:
                        boolean exit=true;
                        while(exit){
                            System.out.println();
                            System.out.println("Tus estadisticas:");
                            System.out.println(J1.GetEstadisticas());
                            System.out.println("0(Partidas contra otro jugador) 1(Salir de Perfil)");
                            respuesta=scan.nextInt();
                            if(respuesta==0){
                                System.out.println("Introduce el nombre del jugador");
                                String nom=scan.next();
                                boolean existe=false;
                                for(Jugador aux:almacen_jugadores.ranking_ordenado_por_nombre()){
                                    if(nom.equals(aux.GetNombre()))existe=true;
                                }
                                if(existe){
                                    J1.partidas_contra_otro(almacen_jugadores.getJugador(nom));
                                }
                                else{
                                    System.out.println("Ese jugador no existe");
                                }
                            }
                            else if(respuesta==1){
                                exit=false;
                            }
                            else{
                                System.out.println("Respuesta no válida");
                            }
                        }
                        break;
                    case 2:
                        System.out.println("0(En orden alfabetico) 1(En orden de victorias)");
                        respuesta=scan.nextInt();
                        if(respuesta==0){
                            System.out.println(almacen_jugadores.ranking_ordenado_por_nombre());
                        }
                        else if(respuesta==1){
                            System.out.println(almacen_jugadores.ranking_ordenado_por_victorias());
                        }
                        else{
                            System.out.println("Respuesta no válida");
                        }
                        break;
                    case 3:
                        registrado=false;
                        break;
                    default:
                        System.out.println("Respuesta no válida");
                }
            }
        }
    }
}
