/**
 * @(#)Production.java
 *
 *
 * @author
 * @version 1.00 2011/6/13
 */

package pl.edu.agh.mes.gg;

abstract class Production extends Thread {

	Production(Vertex Vert,Counter Count){
		m_vertex = Vert;
		m_counter = Count;
		m_drawer = new GraphDrawer();
	}

	//returns first vertex from the left
	abstract Vertex apply(Vertex v);

	//run the thread
	public void run() {
		//m_counter.inc();
		//apply the production
		m_vertex = apply(m_vertex);
		//plot the graph
		//m_drawer.draw(m_vertex);
		//m_counter.dec();
	}

	//vertex where the production will be applied
	Vertex m_vertex;
	//graph drawer
	GraphDrawer m_drawer;
	//productions counter
	Counter m_counter;
}

class P1 extends Production {
	P1(Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	Vertex apply(Vertex S) {
	  System.out.println("p1");
	  Vertex T1 = new Vertex(null,null,S,"T");
	  Vertex T2 = new Vertex(null,null,S,"T");
	  S.set_left(T1);
	  S.set_right(T2);
	  S.set_label("root");
	  return S;
	}
}

class P2 extends Production {
	P2(Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	Vertex apply(Vertex T) {

	  Vertex T1 = new Vertex(null,null,T,"T");
	  Vertex T2 = new Vertex(null,null,T,"T");

	  T.set_left(T1);

	  T.set_right(T2);

	  T.set_label("int");
	  return T;
	}
}

class P3 extends Production {
	P3(Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	Vertex apply(Vertex T) {
	  System.out.println("p3");
	  Vertex T1 = new Vertex(null,null,T,"node");
	  Vertex T2 = new Vertex(null,null,T,"node");
	  T.set_left(T1);
	  T.set_right(T2);
	  T.set_label("int");
	  return T;
	}
}

class A1 extends Production {
	A1(Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	Vertex apply(Vertex T) {
	  System.out.println("A1");
	  T.m_a[1][1]=1.0;
	  T.m_a[2][1]=1.0;
	  T.m_a[1][2]=0.0;
	  T.m_a[2][2]=-1.0;
	  T.m_b[1]=0.0;
	  T.m_b[2]=0.0;

	  return T;
	}
}

class AN extends Production{

	AN(Vertex vertex, Counter counter){
		super(vertex, counter);
	}
	
	@Override
	Vertex apply(Vertex T) {
		System.out.println("AN");
		T.m_a[1][1]=-1.0;
		T.m_a[2][1]=0.0;
		T.m_a[1][2]=1.0;
		T.m_a[2][2]=1.0;
		T.m_b[1]=0.0;
		T.m_b[2]=21.0;
		
		
		return T; 
	}
	
}

class A extends Production{
	A(Vertex vertex, Counter counter){
		super(vertex, counter);
	}

	@Override
	Vertex apply(Vertex T) {
		System.out.println("A");
		T.m_a[1][1]=-1.0;
		T.m_a[2][1]=1.0;
		T.m_a[1][2]=1.0;
		T.m_a[2][2]=-1.0;
		T.m_b[1]=0.0;
		T.m_b[2]=0.0;
		
		return T; 
	}
}

class A2 extends Production {
	A2(Vertex Vert,Counter Count){
		super(Vert,Count);
	}
	Vertex apply(Vertex T) {
	  System.out.println("A2");
	  T.m_a[0][0]=T.m_left.m_a[2][2]+T.m_right.m_a[1][1];
	  T.m_a[1][0]=T.m_left.m_a[1][2];
	  T.m_a[2][0]=T.m_right.m_a[2][1];
	  T.m_a[0][1]=T.m_left.m_a[2][1];
	  T.m_a[1][1]=T.m_left.m_a[1][1];
	  T.m_a[2][1]=0.0;
	  T.m_a[0][2]=T.m_right.m_a[1][2];
	  T.m_a[1][2]=0.0;
	  T.m_a[2][2]=T.m_right.m_a[2][2];
	  T.m_b[0]=T.m_left.m_b[1] + T.m_right.m_b[1];
	  T.m_b[1]=T.m_left.m_b[0] + T.m_right.m_b[0];
	  T.m_b[2]=T.m_left.m_b[2] + T.m_right.m_b[2];

	  return T;
	}
}
	
class E2 extends Production{
	E2(Vertex vertex, Counter counter){
		super(vertex,counter);
	}

	@Override
	Vertex apply(Vertex T) {
		System.out.println("E2");
		double x = T.m_a[0][0];
		T.m_a[0][0] /= x; 
		T.m_a[0][1] /= x;
		T.m_a[0][2] /= x;
		T.m_b[0] /= x;
		
		x = T.m_a[1][0];
		T.m_a[1][0] = 0; 
		T.m_a[1][1] -= x*T.m_a[0][1];
		T.m_a[1][2] -= x*T.m_a[0][2];
		T.m_b[1] -= x*T.m_b[0];
		
		x = T.m_a[2][0];
		T.m_a[2][0] = 0; 
		T.m_a[2][1] -= x*T.m_a[0][1];
		T.m_a[2][2] -= x*T.m_a[0][2];
		T.m_b[2] -= x*T.m_b[0];
		
		return T; 
	}
}

class ERoot extends Production{

	ERoot(Vertex vertex, Counter counter){
		super(vertex,counter);
	}
	
	@Override
	Vertex apply(Vertex T) {
		System.out.println("ERoot");
		double x = T.m_a[0][0];
		T.m_a[0][0] /= x; 
		T.m_a[0][1] /= x;
		T.m_a[0][2] /= x;
		T.m_b[0] /= x;
		
		x = T.m_a[1][0];
		T.m_a[1][0] = 0; 
		T.m_a[1][1] -= x*T.m_a[0][1];
		T.m_a[1][2] -= x*T.m_a[0][2];
		T.m_b[1] -= x*T.m_b[0];
		
		x = T.m_a[2][0];
		T.m_a[2][0] = 0; 
		T.m_a[2][1] -= x*T.m_a[0][1];
		T.m_a[2][2] -= x*T.m_a[0][2];
		T.m_b[2] -= x*T.m_b[0];
		
		x = T.m_a[1][1];
		T.m_a[1][1] /=x;
		T.m_a[1][2] /=x;
		T.m_b[1] /=x; 
		
		x = T.m_a[2][1];
		T.m_a[2][1] = 0; 
		T.m_a[2][2] -= x*T.m_a[1][2]; 
		T.m_b[2] -=x*T.m_b[1];
		
		x = T.m_a[2][2];
		T.m_a[2][2] /= x;
		T.m_b[2] /= x; 
		
		return T;
	}
	
}

class BS extends Production{

	BS(Vertex vertex, Counter counter){
		super(vertex, counter);
	}
	
	@Override
	Vertex apply(Vertex T) {
		System.out.println("BS");
		if(T.m_parent == null){
			T.m_b[1] -= T.m_a[1][2]*T.m_b[2];
			T.m_b[0] -= T.m_a[0][2]*T.m_b[2] + T.m_a[0][1]*T.m_b[1];
		}
		else{
			Vertex parent = T.m_parent;
			if(T.m_parent.m_left == T){
				T.m_b[2] = parent.m_b[0];
				T.m_b[1] = parent.m_b[1];
				T.m_b[0] -= T.m_a[0][1]*T.m_b[1] + T.m_a[0][2]*T.m_b[2]; 
			}
			else{
				T.m_b[2] = parent.m_b[2];
				T.m_b[1] = parent.m_b[0];
				T.m_b[0] -= T.m_a[0][1]*T.m_b[1] + T.m_a[0][2]*T.m_b[2];
			}
			
		}
		
		return T; 
	}
	
}


