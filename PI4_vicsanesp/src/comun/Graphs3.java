package comun;

import org.jgrapht.Graph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Graphs3 {
	public static <V, E> V findVertex(Graph<V, E> g, V v) {
		return g.vertexSet().stream().filter(w -> w.equals(v)).findFirst().orElse(null);
	}
	
	public static Graph<SimpleVertexAux,SimpleEdgeAux<SimpleVertexAux>> importar_grafo(String fichero) {
		return GraphsReader.newGraph("ficheros/" + fichero + ".txt", 
				SimpleVertexAux::ofFormat, 
				SimpleEdgeAux::ofFormat,
				Graphs2::simpleWeightedGraph, 
				SimpleEdgeAux::getEdgeWeight);
	}		
	
}


