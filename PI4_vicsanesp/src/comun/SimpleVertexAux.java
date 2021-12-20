package comun;

public class SimpleVertexAux implements Comparable<SimpleVertexAux> {
	
	public static SimpleVertexAux of() {
		return new SimpleVertexAux("");
	}

	public static SimpleVertexAux ofFormat(String[] formato) {
		return new SimpleVertexAux(formato);
	}

	public static SimpleVertexAux ofName(String nombre) {
		return new SimpleVertexAux (nombre);
	}
	
	private final String nombre;
	

	private SimpleVertexAux(String nombre) {
		super();
		this.nombre = nombre;
	}

	private SimpleVertexAux(String[] formato){
		super();
		this.nombre = formato[0];
	}
	
	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		SimpleVertexAux other = (SimpleVertexAux) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

	@Override
	public int compareTo(SimpleVertexAux o) {
		return getNombre().compareTo(o.getNombre());
	}
}
