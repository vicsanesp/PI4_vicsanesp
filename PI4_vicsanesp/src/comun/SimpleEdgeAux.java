package comun;

public class SimpleEdgeAux<V>  {

	private static int num = 0;
	
	private Double weight;
	private final int id;
	//FIXME: Cambia respecto curso 2020-2021
	//private final V source;
	//private final V target;
		
	public static <V> SimpleEdgeAux<V> of(V v1, V v2) {
		return new SimpleEdgeAux<>(v1,v2);
	}
	
	public static <V> SimpleEdgeAux<V> of() {
		return new SimpleEdgeAux<>();
	}
	
	public static <V> SimpleEdgeAux<V> of(V v1, V v2, double weight) {
		return new SimpleEdgeAux<V>(v1, v2, weight);
	}
	
	public static <V> SimpleEdgeAux<V> ofFormat(String[] formato) {
		return new SimpleEdgeAux<V>(null, null, formato);
	}
	
	//FIXME: Cambia respecto curso 2020-2021
	//FIXME: No tiene utilidad
	public static <V> SimpleEdgeAux<V> ofFormat(V v1, V v2, String[] formato) {
		return new SimpleEdgeAux<V>(v1, v2, formato);
	}	

	public SimpleEdgeAux() {
		super();
		//FIXME: Cambia respecto curso 2020-2021
		//this.source = null;
		//this.target = null;
		this.weight = 1.;
		id = num++;
	}

	public SimpleEdgeAux(V c1, V c2) {
		super();
		//FIXME: Cambia respecto curso 2020-2021
		//this.source = c1;
		//this.target = c2;
		this.weight = 1.;
		id = num++;
	}

	public SimpleEdgeAux(V c1, V c2, double weight) {
		super();
		//FIXME: Cambia respecto curso 2020-2021
		//this.source = c1;
		//this.target = c2;
		this.weight = weight;
		id = num++;
	}
	
	private SimpleEdgeAux(V c1, V c2, String[] formato) {
		//FIXME: Cambia respecto curso 2020-2021
		//this.source = c1;
		//this.target = c2;
		this.weight = formato.length==3? Double.parseDouble(formato[2]):1.;
		this.id = num++;		
	}	
	
	//FIXME: Cambia respecto curso 2020-2021
	//public V getSource(){
	//	return this.source;
	//}

	//FIXME: Cambia respecto curso 2020-2021
	//public V getTarget(){
	//	return this.target;
	//}

	public Double getEdgeWeight(){
		return this.weight;
	}		

	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @param v Un vértice de la arista
	 * @return El otro vértice
	 */

	//FIXME: Cambia respecto curso 2020-2021
    //FIXME: No habria forma de hacerlo
	//public  V otherVertex(V v){
	//	V r = null;
	//	if(v.equals(this.source)){
	//		r = this.target;
	//	} else if(v.equals(this.target)){
	//		r = this.source;
	//	}
	//	return r;
	//}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleEdgeAux other = (SimpleEdgeAux) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		//FIXME: Cambia respecto curso 2020-2021
	    //FIXME: No habria forma de hacerlo		
		//return String.format("(%s,%s)=%.2f",this.source,this.target,this.weight);
		return String.format("(%s)=%.2f","E_"+num,this.weight);
	}

	
}