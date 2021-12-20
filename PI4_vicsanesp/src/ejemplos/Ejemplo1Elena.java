package ejemplos;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;

public class Ejemplo1Elena {

	/* A partir de un grafo no dirigido y ponderado, se pide:
	 * 		a. Obtener un subgrafo con los vértices que cumplen una propiedad y la aristas que cumplen
	 * 		   otra propiedad dada. NO debe crear un grafo nuevo, sino obtener una vista del grafo 
	 * 		   original. Muestre el subgrafo resultante configurando su apariencia de forma que se 
	 * 		   muestren los vértices en los que inciden más de 2 aristas de un color diferente al 
	 * 		   resto de vértices.
	 * 		b. Realice pruebas con los siguientes predicados usando los grafos de Andalucía y Castilla 
	 * 		   La Mancha:
	 * 			i. Ciudades cuyo nombre contiene la letra “e”, y carreteras con menos de 100 km de distancia.
	 * 			ii. Ciudades que poseen menos de 500.000 habitantes, y carreteras cuya ciudad origen o 
	 * 			    destino tiene un nombre de más de 8 caracteres y poseen más de 150 km de distancia.*/
		
		public static <V, E> Graph<V,E> apartadoA(Graph<V,E> grafo, Predicate<V> p_v, Predicate<E> p_a) {
			return SubGraphView.of(grafo, p_v, p_a);
		}
		
		// De esta linea en adelante lo que hacemos es permitir que el grafo se pinte
		// TEST
		public static void main(String[] arg) {
		// Leer grafo
			Graph<Ciudad, Carretera> andalucia = GraphsReader.newGraph("ficheros_ejemplos/Andalucia.txt",
					Ciudad::ofFormat,
					Carretera::ofFormat,
					Graphs2::simpleWeightedGraph,
					Carretera::getKm);
		// Pintar
			GraphColors.toDot(andalucia, "ficheros_ejemplos/Andalucia.gv",
					v -> v.getNombre(),
					a -> a.getNombre() + "--" + a.getKm());
		
		// APLICAMOS LOS PREDICADOS --> es el apartado b.i)
			Predicate<Ciudad> p_v1 = c -> c.getNombre().contains("e");
			Predicate<Carretera> p_a1 = ca -> ca.getKm() < 100;
			Graph<Ciudad, Carretera> vista = apartadoA(andalucia, p_v1, p_a1);
			
			Predicate<Ciudad> p_vista = v -> vista.degreeOf(v) > 2;
			
			GraphColors.toDot(andalucia, "ficheros_ejemplos/Andalucia_A1.gv", 
					v -> v.getNombre(),
					a -> a.getNombre() + "--" + a.getKm(),
					v -> GraphColors.colorIf(Color.blue, Color.orange, p_vista.test(v)),
					e -> GraphColors.style(Style.solid));
	//------------------------------------------------------------------------------------------------------
		// apartado b.ii)
			Predicate<Ciudad> p_v2 = c -> c.getHabitantes() < 500000;
			Predicate<Carretera> p_a21 = ca -> andalucia.getEdgeSource(ca).getNombre().length() > 8 
					|| andalucia.getEdgeTarget(ca).getNombre().length() > 8;
			Predicate<Carretera> p_a22 = ca -> ca.getKm() > 150;
			
			Predicate<Carretera> p_a2 = p_a21.and(p_a22);
			
			Graph<Ciudad, Carretera> vista2 = apartadoA(andalucia, p_v2, p_a2);
			
			Predicate<Ciudad> p_vista_2 = v -> vista2.degreeOf(v) > 2;
			
			GraphColors.toDot(andalucia, "ficheros_ejemplos/Andalucia_A2.gv", 
					v -> v.getNombre(),
					a -> a.getNombre() + "--" + a.getKm(),
					v -> GraphColors.colorIf(Color.blue, Color.orange, p_vista_2.test(v)),
					e -> GraphColors.style(Style.solid));
		}
	
}
