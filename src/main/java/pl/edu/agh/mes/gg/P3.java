package pl.edu.agh.mes.gg;


public class P3 extends Production {
	public P3(Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	public Vertex apply(Vertex T) {
	  T.set_label("int");
	  return T;
	}
}