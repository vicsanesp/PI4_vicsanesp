package tipos;

import java.util.Objects;

public class Investigador {

	public static Investigador of() {
		return new Investigador();
	}

	public static Investigador ofFormat(String[] formato) {
		return new Investigador(formato);
	}

	public static Investigador ofName(String universidad) {
		return new Investigador(universidad);
	}
	
	public static Investigador ofID(Integer id) {
		return new Investigador(id);
	}
	
	private String universidad;
	private Integer anyo;
	private Integer id;
	
	private Investigador(String universidad) {
		super();
		this.universidad = universidad;
		this.anyo = null;
		this.id = null;
	}
	
	private Investigador() {
		super();
		this.universidad = "";
		this.anyo = null;
		this.id = null;
	}

	private Investigador(Integer id) {
		super();
		this.id = id;
	}
	
	private Investigador(String[] formato){
		super();
		this.id = Integer.parseInt(formato[0]);
		this.universidad = formato[2];
		this.anyo = Integer.parseInt(formato[1]);
	}
	
	public Integer getID() {
		return id;
	}
	public Integer getanyo() {
		return anyo;
	}
	public String getuniversidad() {
		return universidad;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anyo, id, universidad);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Investigador other = (Investigador) obj;
		return Objects.equals(anyo, other.anyo) && Objects.equals(id, other.id)
				&& Objects.equals(universidad, other.universidad);
	}

	@Override
//	public String toString() {
//		return "Investigador de la universidad: " + this.universidad + ", del anyo: " + this.anyo + ", con la id: " + id;
//	}
	public String toString() {
		return "Investigador con la id: " + id;
	}

	
	
	
	
}
