package ejemplos;

import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import comun.Graphs3;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.TriFunction;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Ejemplo2Elena {

	/* A partir de un grafo no dirigido y ponderado, se pide:
	 * 		a. Obtener un nuevo grafo que contenga los mismos vértices y sea completo. Las nuevas aristas
	 * 		   tendrán un peso muy grande. Muestre el grafo resultante configurando su apariencia de forma 
	 * 		   que se resalten las nuevas aristas y los vértices de dichas aristas.
	 * 		b. A partir del grafo original, dados dos vértices v1 y v2 de dicho grafo obtener el camino 
	 * 		   mínimo para ir de v1 a v2. Muestre el grafo original configurando su apariencia de forma que 
	 * 		   se resalte el camino mínimo para ir de v1 a v2.
	 * 		c. Obtener un nuevo grafo dirigido con los mismos vértices y que por cada arista original tenga 
	 * 		   dos dirigidas y de sentido opuesto con los mismos pesos.
	 * 		d. Calcule las componentes conexas del grafo original. Muestre el grafo original configurando su 
	 * 		   apariencia de forma que se coloree cada componente conexa de un color diferente.*/

		// APARTADO A
		// Construcción
		public static <V,E> Graph<V, E> apartadoA1 (Graph<V, E> grafo, 
				TriFunction<V, V, Double, E> fact_peso, Function<E, Double> func_peso) {
			// apartado A)
			return Graphs2.explicitCompleteGraph(
					grafo, 
					1000., 
					Graphs2::simpleWeightedGraph, 
					fact_peso, 
					func_peso);
		}
		
		// pinta
		public static <V, E> void apartadoA2(Graph<V,E> grafo, String fichero) {
			Predicate<V> p_v = v -> grafo.edgesOf(v).stream().anyMatch(e -> ((Carretera)e).getKm()==1000.);
			Predicate<E> p_a = e -> grafo.getEdgeWeight(e) == 1000.;
			
			GraphColors.toDot(grafo, "ficheros_ejemplos/Andalucia_2A.gv", 
					v -> ((Ciudad)v).getNombre(),
					a -> ((Carretera)a).getNombre() + "--" + ((Carretera)a).getKm(),
					v -> GraphColors.colorIf(Color.blue, Color.orange, p_v.test(v)),
					e -> GraphColors.colorIf(Color.blue, Color.orange, p_a.test(e))
			);
		}
	  //--------------------------------------------------------------------------------------------------
		// APARTADO B
		public static <V,E> GraphPath<V, E> apartadoB1 (Graph<V,E> grafo, V origen, V destino) {
			var alg = new DijkstraShortestPath<>(grafo);
			return alg.getPath(origen, destino);
		}
		
		public static <V,E> void apartadoB2 (GraphPath<V, E> camino_minimo, Graph<V,E> grafo, String fichero) {
			Predicate<V> p_v = v -> camino_minimo.getVertexList().contains(v);
			Predicate<E> p_a = e -> camino_minimo.getEdgeList().contains(e);
			
			// el grafo se pinta con ...
			GraphColors.toDot(grafo, "ficheros_ejemplos/Andalucia_2B.gv", 
					v -> ((Ciudad)v).getNombre(),
					a -> ((Carretera)a).getNombre() + "--" + ((Carretera)a).getKm(),
					v -> GraphColors.colorIf(Color.blue, Color.orange, p_v.test(v)),
					e -> GraphColors.colorIf(Color.blue, Color.orange, p_a.test(e))
			);
		}
	  //--------------------------------------------------------------------------------------------------
		//APARTADO C)
		public static <V,E> Graph<V,E> apartadoC (SimpleWeightedGraph<V, E> grafo, Function<E, E> f_a) {
			return Graphs2.toDirectedWeightedGraph(grafo, f_a);
		}

	//--------------------------------------------------------------------------------------------------	
		// TEST
		private static SimpleWeightedGraph<Ciudad, Carretera> importar_grafp (String fichero) {
			return GraphsReader.newGraph("ficheros_ejemplos/" + fichero + ".txt",
					Ciudad::ofFormat,
					Carretera::ofFormat,
					Graphs2::simpleWeightedGraph,
					Carretera::getKm);
		}
		
		public static void main (String[] arg) {
			SimpleWeightedGraph<Ciudad, Carretera> andalucia = importar_grafp("Andalucia");
			
			Graph<Ciudad, Carretera> g_v = apartadoA1(andalucia, Carretera::ofWeight, Carretera::getKm);
			apartadoA2(g_v, "Andalucia 2-A");
		//--------------------------------------------------------------------------------------------------
			// APARATADO b
			String origen = "Sevilla";
			String destino = "Almeria";
			// combertimos los STRING 'origen' y 'destino' a VERTICES
			Ciudad desde = Graphs3.findVertex(andalucia, Ciudad.ofName(origen));
			Ciudad hasta = Graphs3.findVertex(andalucia, Ciudad.ofName(destino));
			
			GraphPath<Ciudad, Carretera> gp_b = apartadoB1(andalucia, desde, hasta);
			apartadoB2(gp_b, andalucia, "Andalucia");
		//--------------------------------------------------------------------------------------------------
			// APARTADO c
			Graph<Ciudad, Carretera> g_c = apartadoC(andalucia, Carretera::reverse);
			GraphColors.toDot(g_c, "ficheros_ejemplos/Andalucia_2C.gv", 
					v -> v.getNombre(),
					a -> a.getNombre() + "--" + a.getKm()
			);

		}
	
}
