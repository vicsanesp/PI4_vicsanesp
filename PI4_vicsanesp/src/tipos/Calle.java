package tipos;

import java.util.Objects;

public class Calle {

	private Integer id;
	private Interseccion int1;
	private Interseccion int2;
	private Integer duracion;
	private Integer esfuerzo;
	private static int num=0; 
	
	public Integer getId() {
		return id;
	}
	public Interseccion getInt1() {
		return int1;
	}
	public Interseccion getInt2() {
		return int2;
	}
	public Integer getDuracion() {
		return duracion;
	}
	public Integer getEsfuerzo() {
		return esfuerzo;
	}
	public static int getNum() {
		return num;
	}
	public static void setNum(Integer n) {
		num = n;
	}
	
	public static Calle ofFormat(String[] formato) {
		return new Calle(formato);
	}
	public static Calle ofVertex(Interseccion i1, Interseccion i2) {
		return new Calle(i1, i2);
	}
	public static Calle of(String s) {
		return new Calle(s);
	}
	
	private Calle(String[] s) {
		this.int1 = Interseccion.of(Integer.valueOf(s[0]));
		this.int2 = Interseccion.of(Integer.valueOf(s[1]));
		this.duracion = Integer.valueOf(s[2].replace("min", ""));
		this.esfuerzo = Integer.valueOf(s[3].replace("esf", ""));
		this.id = num;
		num++;
	}
	private Calle(Interseccion i1, Interseccion i2) {
		this.int1 = i1;
		this.int2 = i2;
		this.esfuerzo = null;
		this.duracion = null;
		this.id = num;
		num++;
	}
	private Calle(String s) {
		this.id = Integer.valueOf(s.replace("c", ""));
		this.int1 = null;
		this.int2 = null;
		this.duracion = null;
		this.esfuerzo = null;
	}
	@Override
	public int hashCode() {
		return Objects.hash(duracion, esfuerzo, id, int1, int2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Calle other = (Calle) obj;
		return Objects.equals(duracion, other.duracion) && Objects.equals(esfuerzo, other.esfuerzo)
				&& Objects.equals(id, other.id) && Objects.equals(int1, other.int1) && Objects.equals(int2, other.int2);
	}
	public String toString() {
		return "c" + this.getId().toString();
	}
	
	
}
