package ejercicios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.Files2;
import us.lsi.common.List2;

public class Ejercicio2 {

	public static void main(String[] args) {

		System.out.println("=============================Ejercicio 2=================================");
		// Grafo del ejercicio 2
		SimpleDirectedGraph<String, DefaultEdge> g2 = new SimpleDirectedGraph<>(String::new, DefaultEdge::new, false);
		List<String> lineas = Files2.linesFromFile("ficheros/PI4E2_DatosEntrada1.txt");
		List<String> aristas = lineas.subList(lineas.indexOf("#EDGE#") + 1, lineas.size());
		for (String linea : aristas) {
			String[] v = linea.split(",");
			g2.addVertex(v[0]);
			g2.addVertex(v[1]);
			g2.addEdge(v[0], v[1]);
		}
		// Apartado A
		System.out.println("a)Los libros más necesarios son :" + apartadoA(g2));
		GraphColors.toDot(g2, "grafos/grafo2A.dot", v -> v, e -> "",
				v -> GraphColors.colorIf(Color.blue, Color.black, apartadoA(g2).contains(v)),
				e -> GraphColors.style(Style.solid));
		
		// Apartado B
		List<List<String>> ej2b = lectorApartadoB("ficheros/PI4E2_DatosEntrada2.txt");
		System.out.println("\nb)");
		for (int i = 0; i < ej2b.size(); i++) {
			Boolean b = Ejercicio2.apartadoB(g2, ej2b.get(i));
			if (b)
				System.out.println("Para el " + ej2b.get(i) + " hay solución");
			else
				System.out.println("Para el " + ej2b.get(i) + " no hay solución");
		}
		//Apartado C
		List<String> libros = List2.empty();
		libros.add("L3");
		libros.add("L9");
		libros.add("L7");
		System.out.println("\nc)");
		for (String libro: libros) {
			System.out.println("Para " + libro + " previamente hay que leer " + Ejercicio2.apartadoC(g2, libro));
		}
	}

	// Metodos

	// Apartado A
	public static Set<String> apartadoA(Graph<String, DefaultEdge> grafaso) {
		List<String> vertices = grafaso.vertexSet().stream().toList();
		Set<String> res = new HashSet<>();
		for (int i = 0; i < vertices.size(); i++) {
			if (grafaso.inDegreeOf(vertices.get(i)) == 0)
				res.add(vertices.get(i));
		}
		return res;
	}

	// Apartado B
	public static Boolean apartadoB(Graph<String, DefaultEdge> gf, List<String> ls) {
		ShortestPathAlgorithm<String, DefaultEdge> alg = new DijkstraShortestPath<String, DefaultEdge>(gf);
		Boolean b = null;
		for (int i = 0; i < ls.size() - 1; i++) {
			GraphPath<String, DefaultEdge> gp = alg.getPath(ls.get(i), ls.get(i + 1));
			if (gp != null)
				b = true;
			else
				b = false;
		}
		return b;
	}

	// lector Apartado B
	public static List<List<String>> lectorApartadoB(String ruta) {
		List<List<String>> res = List2.empty();
		List<String> fichero = Files2.linesFromFile(ruta);
		for (String linea : fichero) {
			List<String> aux1 = List2.empty();
			String[] aux2 = linea.split(" ");
			String aux3 = aux2[1].replace("[", "").replace("]", "");
			for (String s : aux3.split(",")) {
				aux1.add(s);
			}
			res.add(aux1);
		}
		return res;
	}

	// Apartado C
	public static List<String> apartadoC(Graph<String, DefaultEdge> gf, String libro) {
		ShortestPathAlgorithm<String, DefaultEdge> alg = new DijkstraShortestPath<String, DefaultEdge>(gf);
		List<String> ls = gf.vertexSet().stream().filter(x -> !x.equals(libro) && alg.getPath(x, libro) != null)
				.toList();
		List<String> res = new ArrayList<>();
		var alg2 = new TopologicalOrderIterator<String, DefaultEdge>(gf);
		alg2.forEachRemaining(v -> res.add(v));
		gf.vertexSet().stream().filter(x -> !ls.contains(x)).forEach(x -> res.remove(x));
		return res;
	}
}
