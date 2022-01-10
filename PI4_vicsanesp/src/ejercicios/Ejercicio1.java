package ejercicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import tipos.Investigador;
import tipos.Trabajos;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.Set2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Ejercicio1 {

	public static void main(String[] args) {
		System.out.println("=============================Ejercicio 1=================================");
		//Grafo del ejercicio 1
		SimpleGraph<Investigador, Trabajos> g1 = GraphsReader.newGraph(
				"ficheros/PI4E1_DatosEntrada.txt",
				Investigador::ofFormat,
				Trabajos::ofFormat,
				Graphs2::simpleWeightedGraph,
				Trabajos::getCantidad
				);
		SimpleGraph<Investigador, Trabajos> g1D = GraphsReader.newGraph(
				"ficheros/PI4E1_DatosEntrada.txt",
				Investigador::ofFormat,
				Trabajos::ofFormat,
				Graphs2::simpleWeightedGraph,
				null
				);
		
		//Apartado A	
		Predicate<Investigador> pV1 = v->v.getanyo()<1982 || g1.edgesOf(v).stream().anyMatch(a->a.getCantidad()>=5);
		GraphColors.toDot(g1,
				"grafos/grafo1A.dot",
				v->"Investigador " + String.valueOf(v.getID() + " Año " + String.valueOf(v.getanyo())),
				a->String.valueOf(a.getCantidad()),
				v->GraphColors.colorIf(Color.blue, pV1.test((Investigador) v)),
				a->GraphColors.colorIf(Color.blue, a.getCantidad()>=5)
				);
		
		//Apartado B
		Set<Investigador> ej1B = Ejercicio1.apartadoB(g1);
		String syso = "[";
		for(Investigador inv: ej1B) {
			syso += "inv-" + String.valueOf(inv.getID()) + ", ";
		}
		syso = syso.substring(0, syso.length()-2);
		syso += "]";
		System.out.println("b) Los 5 Investigador que tienen un mayor "
				+ "número de Investigador colaboradores son: "
				+ syso);
		GraphColors.toDot(g1,
				"grafos/grafo1B.dot",
				v->"Investigador " + String.valueOf(v.getID()),
				a->String.valueOf(a.getCantidad()),
				v->GraphColors.colorIf(Color.blue, Color.green, ej1B.contains(v)),
				a->GraphColors.color(Color.green)
				);
		//Apartado C
		System.out.println("c)");
		Map<Investigador, List<Investigador>> dic1C = apartadoC(g1);
		for(Investigador inv: dic1C.keySet()) {
			System.out.println("inv-" + inv.getID() + "->" + dic1C.get(inv));
		}
		GraphColors.toDot(g1, "grafos/grafo1C.dot", 
				v->"Investigador " + String.valueOf(v.getID()), 
				a->String.valueOf(a.getCantidad()),
				v -> GraphColors.color(Color.black),
				e -> GraphColors.colorIf(Color.blue, Color.black, listaMasTrabajos(dic1C, g1).contains(e)));
		
		//Apartado D
		Par ej1d = Ejercicio1.apartadoD(g1D);
		ShortestPathAlgorithm<Investigador,Trabajos> cam = new DijkstraShortestPath<Investigador,Trabajos>(g1D);
		GraphPath<Investigador,Trabajos> gp =  cam.getPath(ej1d.v1(),ej1d.v2());
		System.out.println("d) El par de Investigador más lejanos es: " + "(inv-" + Ejercicio1.apartadoD(g1D).v1().getID() + ", inv-" + Ejercicio1.apartadoD(g1D).v2().getID() + ")");
		GraphColors.toDot(g1D,
				"grafos/grafo1D.dot",
				v->"Investigador " + String.valueOf(v.getID()),
				a->"1",
				v->GraphColors.colorIf(Color.blue, Color.black, gp.getVertexList().contains(v)),
				a->GraphColors.colorIf(Color.blue, gp.getEdgeList().contains(a))
				);
		
		//Apartado E
		var alg1 = new GreedyColoring<>(g1);
		var coloring = alg1.getColoring();
		Map<Investigador, Integer> map = coloring.getColors();
		
		GraphColors.toDot(g1, "grafos/grafo1E.dot", 
				v->"Investigador " + String.valueOf(v.getID()) + " de " + v.getuniversidad(), 
				a->String.valueOf(a.getCantidad()),
				v -> GraphColors.color(map.get(v)), 
				a -> GraphColors.style(Style.solid));
		
		System.out.println("e) Las reuniones serían:");
		for (Set<Investigador> cjto: apartadoE(g1)) {
			System.out.println(cjto);	
		}
	}
	
	
	//Metodos
	
	//Par para el apartado D
	public record Par(Investigador v1, Investigador v2) {
		public static Par of(Investigador v1, Investigador v2) {
			return new Par(v1, v2);
		}
	}

	//Apartado B
	public static Set<Investigador> apartadoB(Graph<Investigador, Trabajos> grafo) {
		Map<Investigador, Integer> map = new HashMap<Investigador, Integer>();
		for(Investigador inv : grafo.vertexSet()) {
			map.put(inv, grafo.edgesOf(inv).size());
		}
		List<Entry<Investigador, Integer>> res = new ArrayList<>(map.entrySet());
		res.sort(Entry.comparingByValue(Comparator.reverseOrder()));
		Set<Investigador> conj = Set2.empty();
		for (int i = 0; i < 5; i++) {
			conj.add(res.get(i).getKey());
		}
		return conj;
	}
	
	//Apartado C
	private static List<Investigador> listaInvestigadores(Investigador i, Graph<Investigador, Trabajos> grafaso) {
		return grafaso.edgesOf(i).stream()
				.sorted(Comparator.comparing(Trabajos::getCantidad).reversed())
				.map(a -> a.getTarget2(i))
				.collect(Collectors.toList());	
	}
	
	private static List<Trabajos> listaMasTrabajos(Map<Investigador, List<Investigador>> mp, Graph<Investigador, Trabajos> grafaso){
		
		List<Trabajos> res = new ArrayList<>();
		for(Entry<Investigador, List<Investigador>> investigador:mp.entrySet()) {
			
			Investigador i = investigador.getValue().get(0);
			Investigador trueI = grafaso.vertexSet().stream().filter(v -> v.getID().equals(i.getID())).findFirst().get();
			res.add(grafaso.getEdge(trueI, investigador.getKey()));
			
		}
		return res;
	}
	
	public static Map<Investigador, List<Investigador>> apartadoC(Graph<Investigador, Trabajos> grafaso){
		Map<Investigador, List<Investigador>> mp = new HashMap<>();
		Set<Investigador> cjtoInv = grafaso.vertexSet();
		for (Investigador i: cjtoInv) {
			Investigador key = i;
			if (!mp.containsKey(key))
				mp.put(key, listaInvestigadores(i, grafaso));
		}
		return mp;
	}
	
	//Apartado D
	public static Par apartadoD(Graph<Investigador, Trabajos> grafaso) {
		int camino = 0;
		Par res = Par.of(null, null);
		ShortestPathAlgorithm<Investigador,Trabajos> a = new DijkstraShortestPath<Investigador,Trabajos>(grafaso);
		for(Investigador from:grafaso.vertexSet()) {
			for(Investigador to:grafaso.vertexSet()) {
				if(!from.equals(to)) {
					GraphPath<Investigador,Trabajos> gp =  a.getPath(from,to);
					if(gp.getLength()>camino) {
						res = Par.of(from, to);
						camino = gp.getLength();
					}
				}
			}
		}
		return res;
	}
	
	//Apartado E
	public static List<Set<Investigador>> apartadoE(Graph<Investigador, Trabajos> grafaso) {
		Graph<Investigador, Trabajos> copia = new SimpleWeightedGraph<Investigador, Trabajos>(null, null);
		Graphs.addGraph(copia, grafaso);
		for (Investigador i1: copia.vertexSet()) {
			for (Investigador i2: copia.vertexSet()) {
				if (!i1.equals(i2) && copia.getEdge(i1, i2) == null && i1.getuniversidad().equals(i2.getuniversidad())) {
					Trabajos art = Trabajos.ofVertex(i2, i1);
					copia.addEdge(i1, i2, art);
				}
			}
		}
		var alg = new GreedyColoring<>(copia);
		var coloring = alg.getColoring();
		return coloring.getColorClasses().stream().filter(x -> x.size()>1).toList();
		
	}
	
	
}
