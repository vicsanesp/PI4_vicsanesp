package ejercicios;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;

import tipos.Trabajos;
import tipos.Investigador;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Map2;
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
		System.out.println("b) Los 5 investigadores que tienen un mayor "
				+ "número de investigadores colaboradores son: "
				+ syso);
		GraphColors.toDot(g1,
				"grafos/grafo1B.dot",
				v->"Investigador " + String.valueOf(v.getID()),
				a->String.valueOf(a.getCantidad()),
				v->GraphColors.colorIf(Color.blue, Color.green, ej1B.contains(v)),
				a->GraphColors.color(Color.green)
				);
		
		//Apartado D
		Par ej1d = Ejercicio1.apartadoD(g1);
		ShortestPathAlgorithm<Investigador,Trabajos> cam = new DijkstraShortestPath<Investigador,Trabajos>(g1);
		GraphPath<Investigador,Trabajos> gp =  cam.getPath(ej1d.v1(),ej1d.v2());
		System.out.println("d) El par de investigadores más lejanos es: " + "(inv-" + Ejercicio1.apartadoD(g1).v1().getID() + ", inv-" + Ejercicio1.apartadoD(g1).v2().getID() + ")");
		GraphColors.toDot(g1,
				"grafos/grafo1D.dot",
				v->"Investigador " + String.valueOf(v.getID()),
				a->String.valueOf(a.getCantidad()),
				v->GraphColors.colorIf(Color.blue, Color.black, gp.getVertexList().contains(v)),
				a->GraphColors.colorIf(Color.blue, gp.getEdgeList().contains(a))
				);
		
	}
	
	
	//Metodos
	
	//Par para el apartado D
	public record Par(Investigador v1, Investigador v2) {
		public static Par of(Investigador v1, Investigador v2) {
			return new Par(v1, v2);
		}
	}

	//Apartado B
	public static Set<Investigador> apartadoB(Graph<Investigador, Trabajos> grafaso){
		Set<Investigador> res = Set2.empty();
		Map<Investigador, Integer> dic = Map2.empty();
		for(Investigador a:grafaso.vertexSet()) {
			dic.put(a, grafaso.outDegreeOf(a));
		}
		dic = sortByValue(dic);
		List<Investigador> aux = dic.keySet().stream().toList();
		for (int i = 0; i < 5; i++) {
			res.add(aux.get(i));
		}
		return res;
	}
	
	//Apartado C
	public static Map<Investigador, List<Investigador>> apartadoC(Graph<Investigador, Trabajos> grafaso){
		Map<Investigador, List<Investigador>> res = Map2.empty();
//		for(Investigador inv:grafaso.vertexSet()) {
//			
//		}
		return res;
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
	
	public static Map<Investigador, Integer> sortByValue(Map<Investigador, Integer> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}
	
	public static Integer apartadoE(Graph<Investigador, Trabajos> grafaso) {
		return 0;
	}
	
}
