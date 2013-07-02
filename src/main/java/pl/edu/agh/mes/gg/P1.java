package pl.edu.agh.mes.gg;


public class P1 extends Production {
	public P1(Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	public Vertex apply(Vertex S) {
	  System.out.println("p1");
	  Vertex T1 = new Vertex(null,null,S,"T");
	  Vertex T2 = new Vertex(null,null,S,"T");
	  S.set_left(T1);
	  S.set_right(T2);
	  S.set_label("root");
	  return S;
	}
}
