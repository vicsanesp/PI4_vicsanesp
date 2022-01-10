package tipos;


public class Trabajos {
	
	public static Trabajos of() {
		return new Trabajos();
	}

	public static Trabajos ofVertex(Investigador c1, Investigador c2) {
		return new Trabajos(c1,c2);
	}

	public static Trabajos ofFormat(String[] formato) {
		return new Trabajos(formato);
	}

	public static Trabajos ofWeight(Investigador c1, Investigador c2, Double Cantidad) {
		return new Trabajos(c1, c2, Cantidad);
	}
	
	public static Trabajos reverse(Trabajos c) {
		return new Trabajos(c.target, c.source, c.Cantidad);
	}

	private static int num =0;
	private Investigador source;
	private Investigador target;
	private Double Cantidad;
	private int id;

	private Trabajos(Investigador c1, Investigador c2) {
		this.source = c1;
		this.target = c2;
		this.Cantidad = 0.;
		this.id = num;
		num++;
	}
	
	private Trabajos() {
		this.source = null;
		this.target = null;
		this.Cantidad = 0.;
		this.id = num;
		num++;
	} 
	
	private Trabajos(Investigador source, Investigador target, Double Cantidad, String nombre) {
		super();
		this.source = source;
		this.target = target;
		this.Cantidad = Cantidad;
		this.id = num;
		num++;
	}

	private Trabajos(String[] nombre) {
		this.source = Investigador.ofID(Integer.parseInt(nombre[0]));
		this.target = Investigador.ofID(Integer.parseInt(nombre[1]));
		this.Cantidad = Double.parseDouble(nombre[2]);		
		this.id = num;
		num++;
	}
	
	private Trabajos(Investigador c1, Investigador c2, Double Cantidad) {
		this.source = c1;
		this.target = c2;
		this.Cantidad = Cantidad;
		this.id = num;
		num++;
	}



	public Double getCantidad() {
		return this.Cantidad;
	}

	
	public Investigador getSource(){
		return source;
	}
	
	public Investigador getTarget(){
		return target;
	}
	
	public Investigador getTarget2(Investigador i) {
		
		Investigador res = null;
		if (this.getSource().getID()==i.getID()) res = this.getTarget();
		else res = this.getSource();
		
		return res;
		
	}


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
		if (!(obj instanceof Trabajos))
			return false;
		Trabajos other = (Trabajos) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "El investigador: " + source.getID() + ", trabaja con el: " + target.getID() + ", en : " + Cantidad + " ocasiones";
	}

	
}
