package ejemplos;

import java.util.function.Function;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.CompleteGraphView;

public class Ejemplo2 {

	private static SimpleWeightedGraph<Ciudad, Carretera> gf;
	private static Function<Ciudad, String> ETQ_V = Ciudad::getNombre;
	private static final Function<Carretera, String> ETQ_A = a->a.getKm() + " kms";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test("Andalucia", "Sevilla", "Almeria");
		test("Castilla La Mancha", "Albacete", "Toledo");
	}
	
	private static void test(String file, String origen, String destino) {
		gf = import_wg(file);
		apartadoA(file + " A");
//		apartadoB(file + " B", origen, destino);
//		apartadoC(file + " C");
//		apartadoD(file + " D");
		
	}
	
	private static SimpleWeightedGraph<Ciudad, Carretera> import_wg(String file){
		return GraphsReader.newGraph("ficheros_ejemplo/" + file + ".txt", Ciudad::ofFormat, Carretera::ofFormat, Graphs2::simpleWeightedGraph, Carretera::getKm);
	}
	
	private static void apartadoA(String file) {
		Graph<Ciudad, Carretera> g = CompleteGraphView.of(gf, Carretera::ofWeight, 1000., Carretera::getSource, Carretera::getTarget);
	}

}
