package tipos;

import java.util.Objects;

public class Interseccion {

	private Integer id;
	private boolean esRelevante;
	private String nombreMonumento;
	private Integer relevancia;
	
	public Integer getId() {
		return id;
	}
	public boolean isEsRelevante() {
		return esRelevante;
	}
	public Integer getRelevancia() {
		return relevancia;
	}
	public String getNombreMonumento() {
		return nombreMonumento;
	}
	public static Interseccion ofFormat(String[] formato) {
		return new Interseccion(formato);
	}
	public static Interseccion of() {
		return new Interseccion("");
	}
	public static Interseccion of(Integer id) {
		return new Interseccion(id);
	}
	public static Interseccion ofMonumento(String monumento) {
		return new Interseccion(monumento);
	}
	
	private Interseccion(String[] formato) {
		
		this.id = Integer.valueOf(formato[0]);
		this.esRelevante = Boolean.valueOf(formato[1]);
		this.nombreMonumento = formato[2];
		String relevancia = formato[3].replace("int", "");
		this.relevancia = Integer.valueOf(relevancia);
		
	}
	private Interseccion(Integer id) {
		this.id = id;
		this.esRelevante = false;
		this.nombreMonumento = null;
		this.relevancia = 0;
	}
	private Interseccion(String monumento) {
		this.id = null;
		this.esRelevante = false;
		this.nombreMonumento = monumento;
		this.relevancia = null;
	}
	@Override
	public int hashCode() {
		return Objects.hash(esRelevante, id, nombreMonumento, relevancia);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interseccion other = (Interseccion) obj;
		return esRelevante == other.esRelevante && Objects.equals(id, other.id)
				&& Objects.equals(nombreMonumento, other.nombreMonumento)
				&& Objects.equals(relevancia, other.relevancia);
	}
	public String toString() {
		
		String s = "";
		if (this.nombreMonumento.isEmpty())
			s = "INT-" + this.getId();
		else
			s = "INT-" + this.getId() + ", " + this.getNombreMonumento();
		
		return s;
		
	}
	
}
