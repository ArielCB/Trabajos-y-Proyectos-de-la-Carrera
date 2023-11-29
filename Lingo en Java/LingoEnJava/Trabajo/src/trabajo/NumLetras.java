/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajo;

/**
 *
 * @author a.carnes.2021
 */
public enum NumLetras{
    CINCO(5),
    SEIS(6);
    
    private final int nl;
    
    NumLetras(int numl){
        nl=numl;
    }
    
    public int cifra(){return nl;}
}
