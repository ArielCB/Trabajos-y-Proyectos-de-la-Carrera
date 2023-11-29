
package trabajo;
import java.util.Comparator;

public class ComparaVictorias implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
    Jugador J1=(Jugador) o1;
    Jugador J2=(Jugador) o2;
    
    if(J1.GetEstadisticas().GetGanadas()>J2.GetEstadisticas().GetGanadas()) return -1;
    if(J1.GetEstadisticas().GetGanadas()==J2.GetEstadisticas().GetGanadas()) return -1;
    return 1;  
    }
    
}
