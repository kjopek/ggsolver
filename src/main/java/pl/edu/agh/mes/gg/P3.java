package pl.edu.agh.mes.gg;


public class P3 extends Production {
	public P3(Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	public Vertex apply(Vertex T) {
	  System.out.println("p3");
	  Vertex T1 = new Vertex(null,null,T,"node");
	  Vertex T2 = new Vertex(null,null,T,"node");
	  T.set_left(T1);
	  T.set_right(T2);
	  T.set_label("int");
	  return T;
	}
}