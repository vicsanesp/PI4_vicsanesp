package ejemplos;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;

public class Ejemplo1 {

	private static final Predicate<Ciudad> PV1 = c -> c.getNombre().contains("e");
	private static final Predicate<Carretera> PA1 = ca -> ca.getKm() < 100;
	private static final Predicate<Ciudad> PV2 = c -> c.getHabitantes() < 500000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test("Andalucia");
		test("Castilla La Mancha");
		
	}
	
	private static void test(String file) {
		final Graph<Ciudad, Carretera> g = import_sg(file);
		final Predicate<Carretera> PA2 = ca -> ca.getKm() > 150 && (g.getEdgeSource(ca).getNombre().length()>8 || g.getEdgeTarget(ca).getNombre().length()>8);
		export_gf(SubGraphView.of(g,  PV1, PA1), file+" A");
		export_gf(SubGraphView.of(g,  PV1, PA2), file+" B");
	}
	
	private static Graph<Ciudad, Carretera> import_sg(String fichero){
		return GraphsReader.newGraph("ficheros_ejemplos/" + fichero + ".txt", Ciudad::ofFormat, Carretera::ofFormat, Graphs2::simpleGraph);
	}
	
	public static void export_gf(Graph<Ciudad, Carretera> g, String file) {
		String out = "ficheros_ejemplos/ejem1" + file + ".gv";
	}

}
