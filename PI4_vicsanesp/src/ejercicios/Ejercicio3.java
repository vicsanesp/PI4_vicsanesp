package ejercicios;

import java.util.function.Function;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.tour.HeldKarpTSP;

import tipos.Calle;
import tipos.Interseccion;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Ejercicio3 {

	public static void main(String[] args) {
		
		Graph<Interseccion, Calle>g3Dur = GraphsReader.newGraph("ficheros/PI4E3_DatosEntrada.txt",
				Interseccion::ofFormat,
				Calle::ofFormat,
				Graphs2::simpleWeightedGraph,
				a -> Double.valueOf(a.getDuracion()));

		Graph<Interseccion, Calle>g3Esf = GraphsReader.newGraph("ficheros/PI4E3_DatosEntrada.txt",
				Interseccion::ofFormat,
				Calle::ofFormat,
				Graphs2::simpleWeightedGraph,
				a -> Double.valueOf(a.getEsfuerzo()));
		
		Function<Calle, String> calleDuracion = a -> a.getDuracion().toString();
		Function<Calle, String> calleEsfuerzo = a -> a.getEsfuerzo().toString();
		
		//Apartado A1
		Interseccion i1 = null;
		Interseccion i2 = null;
		for(Interseccion i: g3Dur.vertexSet()) {
			if(i.getNombreMonumento().equals("m7"))
				i1=i;
			else if(i.getNombreMonumento().equals("m2"))
				i2=i;
			else if(i1!=null && i2!=null)
				break;
		}
		var alg = new FloydWarshallShortestPaths<>(g3Dur);
		GraphPath<Interseccion, Calle> gp = alg.getPath(i1, i2);
		GraphColors.toDot(g3Dur, "grafos/grafo3A1.dot", 
				Interseccion::toString,
				calleDuracion,
				v -> GraphColors.colorIf(Color.blue, Color.black, gp.getVertexList().contains(v)),
				e -> GraphColors.colorIf(Color.blue, Color.black, gp.getEdgeList().contains(e)));
	
		//Apartado A2
		i1 = null;
		i2 = null;
		for(Interseccion i: g3Dur.vertexSet()) {
			if(i.getNombreMonumento().equals("m4"))
				i1=i;
			else if(i.getNombreMonumento().equals("m9"))
				i2=i;
			else if(i1!=null && i2!=null)
				break;
		}
		alg = new FloydWarshallShortestPaths<>(g3Dur);
		GraphPath<Interseccion, Calle> gp2 = alg.getPath(i1, i2);
		GraphColors.toDot(g3Dur, "grafos/grafo3A2.dot", 
				Interseccion::toString,
				calleDuracion,
				v -> GraphColors.colorIf(Color.blue, Color.black, gp2.getVertexList().contains(v)),
				e -> GraphColors.colorIf(Color.blue, Color.black, gp2.getEdgeList().contains(e)));
		
		//Apartado B
		var alg2 = new HeldKarpTSP<Interseccion, Calle>();
		GraphPath<Interseccion, Calle> gpb = alg2.getTour(g3Esf);
		GraphColors.toDot(g3Esf, "grafos/grafo3B.dot", Interseccion::toString, calleEsfuerzo,
				v -> GraphColors.colorIf(Color.blue, Color.black, gpb.getVertexList().contains(v)),
				e -> GraphColors.colorIf(Color.blue, Color.black, gpb.getEdgeList().contains(e)));
		
	}
	
}
