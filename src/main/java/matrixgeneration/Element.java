package matrixgeneration;


public class Element {
	
	private Position position;
	private int topLeftVertexNr; 
	private int topRightVertexNr; 
	private int botLeftVertexNr; 
	private int botRightVertexNr;
	
	private boolean firstTier; 
	
	private double[] botLeftCoord; 
	private double size; 
	
	
	private DoubleArgFunction topLeftFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			
			switch(position){
			case BOT_LEFT:
				//if(!firstTier)
					//return (1-(x-botLeftCoord[0])/size)*(y-botLeftCoord[1])/size/2.0;
				return (1-(x-botLeftCoord[0])/size)*(y-botLeftCoord[1])/size;
			case TOP_LEFT:
				return (1-(x-botLeftCoord[0])/size)*(y-botLeftCoord[1])/size;
			case TOP_RIGHT:
				//if(!firstTier)
					//return (1-(x-botLeftCoord[0])/size)*(y-botLeftCoord[1])/size/2.0;
				return (1-(x-botLeftCoord[0])/size)*(y-botLeftCoord[1])/size;
			case BOT_RIGHT:
				return (1-(x-botLeftCoord[0])/size)*(y-botLeftCoord[1])/size;
			default:
				throw new RuntimeException();
			}
		}
		
	};
	
	private DoubleArgFunction topRightFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			
			switch(position){
			case BOT_LEFT:
				return (x-botLeftCoord[0])/size*(y-botLeftCoord[1])/size;
			case TOP_LEFT:
				//if(!firstTier)
					//return (x-botLeftCoord[0])/size*(y-botLeftCoord[1])/size/2.0;
				return (x-botLeftCoord[0])/size*(y-botLeftCoord[1])/size;
			case TOP_RIGHT:
				return (x-botLeftCoord[0])/size*(y-botLeftCoord[1])/size;
			case BOT_RIGHT:
				return (x-botLeftCoord[0])/size*(y-botLeftCoord[1])/size;
			default:
				throw new RuntimeException();
			}
			
			
		}
		
	};
	
	private DoubleArgFunction botLeftFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			
			switch(position){
			case BOT_LEFT:
				return (1-(x-botLeftCoord[0])/size)*(1-(y-botLeftCoord[1])/size);
			case TOP_LEFT:
				//if(!firstTier)
					//return (1-(x-botLeftCoord[0])/size)*(1-(y-botLeftCoord[1])/size)/2.0;
				return (1-(x-botLeftCoord[0])/size)*(1-(y-botLeftCoord[1])/size);
			case TOP_RIGHT:
				return (1-(x-botLeftCoord[0])/size)*(1-(y-botLeftCoord[1])/size);
			case BOT_RIGHT:
				return (1-(x-botLeftCoord[0])/size)*(1-(y-botLeftCoord[1])/size);
			default:
				throw new RuntimeException();
			}
			
		}
		
	};
	
	private DoubleArgFunction botRightFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			
			return (x-botLeftCoord[0])/size*(1-(y-botLeftCoord[1])/size);
		}
		
	};
	
	public Element(double[] botLeftCoord, double size, Position position){
		this.botLeftCoord = botLeftCoord; 
		this.size = size; 
		this.position = position; 
		this.firstTier = false; 
		this.botLeftVertexNr = -1; 
		this.topLeftVertexNr = -1; 
		this.topRightVertexNr = -1; 
		this.botRightVertexNr = -1; 
	}
	
	
	public Element[] createAnotherTier(int nr){
		
		double[] botElementBotLeftCoord = new double[2];
		double[] rightElementBotLeftCoord = new double[2];
		
		botElementBotLeftCoord[0] = botLeftCoord[0];
		botElementBotLeftCoord[1] = botLeftCoord[1] - size;
		
		rightElementBotLeftCoord[0] = botLeftCoord[0] + size; 
		rightElementBotLeftCoord[1] = botLeftCoord[1]; 
		
		Element rightElement = new Element(rightElementBotLeftCoord, size, Position.TOP_RIGHT);
		Element botElement = new Element(botElementBotLeftCoord, size, Position.BOT_LEFT);
		
		this.topLeftVertexNr = nr + 1; 
		this.botLeftVertexNr = nr; 
		this.topRightVertexNr = nr + 2; 
		this.botRightVertexNr = nr + 4; 
		
		botElement.setBotLeftVertexNr(nr);
		botElement.setTopLeftVertexNr(nr + 1);
		botElement.setBotRightVertexNr(nr + 3);
		botElement.setTopRightVertexNr(nr + 4);
		
		
		rightElement.setTopLeftVertexNr(nr + 1);
		rightElement.setTopRightVertexNr(nr + 2);
		rightElement.setBotLeftVertexNr(nr + 4);
		rightElement.setBotRightVertexNr(nr + 5);
		
		Element[] elements = new Element[2];
		elements[0] = rightElement; 
		elements[1] = botElement;
		return elements;
		
	}
	
	public Element[] createFirstTier(int nr){
		
		double[] botElementBotLeftCoord = new double[2];
		double[] rightElementBotLeftCoord = new double[2];
		
		botElementBotLeftCoord[0] = botLeftCoord[0];
		botElementBotLeftCoord[1] = botLeftCoord[1] - size;
		
		rightElementBotLeftCoord[0] = botLeftCoord[0] + size; 
		rightElementBotLeftCoord[1] = botLeftCoord[1]; 
		
		Element rightElement = new Element(rightElementBotLeftCoord, size, Position.TOP_RIGHT);
		Element botElement = new Element(botElementBotLeftCoord, size, Position.BOT_LEFT);
		
		this.firstTier = true; 
		rightElement.firstTier = true; 
		botElement.firstTier = true; 
		
		this.topLeftVertexNr = nr + 2; 
		this.botLeftVertexNr = nr + 1; 
		this.topRightVertexNr = nr + 3; 
		this.botRightVertexNr = nr + 6; 
		
		botElement.setBotLeftVertexNr(nr);
		botElement.setTopLeftVertexNr(nr + 1);
		botElement.setBotRightVertexNr(nr + 5);
		botElement.setTopRightVertexNr(nr + 6);
		
		
		rightElement.setTopLeftVertexNr(nr + 3);
		rightElement.setTopRightVertexNr(nr + 4);
		rightElement.setBotLeftVertexNr(nr + 6);
		rightElement.setBotRightVertexNr(nr + 7);
		
		Element[] elements = new Element[2];
		elements[0] = rightElement; 
		elements[1] = botElement;
		return elements;
		
	}
	
	public Element[] createLastTier(int nr){
		
		this.topLeftVertexNr = nr + 1; 
		this.botLeftVertexNr = nr; 
		this.topRightVertexNr = nr + 2; 
		this.botRightVertexNr = nr + 3;
		
		this.botLeftCoord[1] -= size; 
		this.size*=2; 
		this.position = Position.BOT_RIGHT;
		return new Element[0];
		
		
	}
	
	private void comp(int indx1, int indx2, DoubleArgFunction f1, DoubleArgFunction f2,double[][] matrix){
		
		DoubleArgFunctionProduct product = new DoubleArgFunctionProduct();
		product.setFunctions(f1,f2);
		matrix[indx1][indx2] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);
	}
	
	public void fillMatrix(double[][] matrix){
		
		comp(botLeftVertexNr, botLeftVertexNr, botLeftFunction, botLeftFunction, matrix);
		comp(botLeftVertexNr, botRightVertexNr, botLeftFunction, botRightFunction, matrix);
		comp(botLeftVertexNr, topRightVertexNr, botLeftFunction, topRightFunction, matrix);
		comp(botLeftVertexNr, topLeftVertexNr, botLeftFunction, topLeftFunction, matrix);
		
		comp(topLeftVertexNr, botLeftVertexNr, topLeftFunction, botLeftFunction, matrix);
		comp(topLeftVertexNr, botRightVertexNr, topLeftFunction, botRightFunction, matrix);
		comp(topLeftVertexNr, topRightVertexNr, topLeftFunction, topRightFunction, matrix);
		comp(topLeftVertexNr, topLeftVertexNr, topLeftFunction, topLeftFunction, matrix);
		
		comp(topRightVertexNr, botLeftVertexNr, topRightFunction, botLeftFunction, matrix);
		comp(topRightVertexNr, botRightVertexNr, topRightFunction, botRightFunction, matrix);
		comp(topRightVertexNr, topRightVertexNr, topRightFunction, topRightFunction, matrix);
		comp(topRightVertexNr, topLeftVertexNr, topRightFunction, topLeftFunction, matrix);
		
		comp(botRightVertexNr, botLeftVertexNr, botRightFunction, botLeftFunction, matrix);
		comp(botRightVertexNr, botRightVertexNr, botRightFunction, botRightFunction, matrix);
		comp(botRightVertexNr, topRightVertexNr, botRightFunction, topRightFunction, matrix);
		comp(botRightVertexNr, topLeftVertexNr, botRightFunction, topLeftFunction, matrix);
	}
	
	public void fillTierMatrix(double[][] matrix, double[] rhs, DoubleArgFunction f, int startNrAdj){
		
		comp(botLeftVertexNr - startNrAdj, botLeftVertexNr - startNrAdj, botLeftFunction, botLeftFunction, matrix);
		comp(botLeftVertexNr - startNrAdj, botRightVertexNr - startNrAdj, botLeftFunction, botRightFunction, matrix);
		comp(botLeftVertexNr - startNrAdj, topRightVertexNr - startNrAdj, botLeftFunction, topRightFunction, matrix);
		comp(botLeftVertexNr - startNrAdj, topLeftVertexNr - startNrAdj, botLeftFunction, topLeftFunction, matrix);
		
		comp(topLeftVertexNr - startNrAdj, botLeftVertexNr - startNrAdj, topLeftFunction, botLeftFunction, matrix);
		comp(topLeftVertexNr - startNrAdj, botRightVertexNr - startNrAdj, topLeftFunction, botRightFunction, matrix);
		comp(topLeftVertexNr - startNrAdj, topRightVertexNr - startNrAdj, topLeftFunction, topRightFunction, matrix);
		comp(topLeftVertexNr - startNrAdj, topLeftVertexNr - startNrAdj, topLeftFunction, topLeftFunction, matrix);
		
		comp(topRightVertexNr - startNrAdj, botLeftVertexNr - startNrAdj, topRightFunction, botLeftFunction, matrix);
		comp(topRightVertexNr - startNrAdj, botRightVertexNr - startNrAdj, topRightFunction, botRightFunction, matrix);
		comp(topRightVertexNr - startNrAdj, topRightVertexNr - startNrAdj, topRightFunction, topRightFunction, matrix);
		comp(topRightVertexNr - startNrAdj, topLeftVertexNr - startNrAdj, topRightFunction, topLeftFunction, matrix);
		
		comp(botRightVertexNr - startNrAdj, botLeftVertexNr - startNrAdj, botRightFunction, botLeftFunction, matrix);
		comp(botRightVertexNr - startNrAdj, botRightVertexNr - startNrAdj, botRightFunction, botRightFunction, matrix);
		comp(botRightVertexNr - startNrAdj, topRightVertexNr - startNrAdj, botRightFunction, topRightFunction, matrix);
		comp(botRightVertexNr - startNrAdj, topLeftVertexNr - startNrAdj, botRightFunction, topLeftFunction, matrix);
		
	
		DoubleArgFunctionProduct product = new DoubleArgFunctionProduct();
		
		product.setFunctions(botLeftFunction, f);
		rhs[botLeftVertexNr - startNrAdj] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);
		
		product.setFunctions(botRightFunction, f);
		rhs[botRightVertexNr  - startNrAdj] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);
		
		
		product.setFunctions(topLeftFunction, f);
		rhs[topLeftVertexNr - startNrAdj] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);
		
		
		product.setFunctions(topRightFunction, f);
		rhs[topRightVertexNr - startNrAdj] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);
	}
	
	public void fillRhs(double[] rhs, DoubleArgFunction f){
		
		DoubleArgFunctionProduct product = new DoubleArgFunctionProduct();
		
		product.setFunctions(botLeftFunction, f);
		rhs[botLeftVertexNr] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);
		
		product.setFunctions(botRightFunction, f);
		rhs[botRightVertexNr] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);
		
		
		product.setFunctions(topLeftFunction, f);
		rhs[topLeftVertexNr] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);
		
		
		product.setFunctions(topRightFunction, f);
		rhs[topRightVertexNr] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);

		
	}
	
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public int getTopLeftVertexNr() {
		return topLeftVertexNr;
	}
	public void setTopLeftVertexNr(int topLeftVertexNr) {
		this.topLeftVertexNr = topLeftVertexNr;
	}
	public int getTopRightVertexNr() {
		return topRightVertexNr;
	}
	public void setTopRightVertexNr(int topRightVertexNr) {
		this.topRightVertexNr = topRightVertexNr;
	}
	public int getBotLeftVertexNr() {
		return botLeftVertexNr;
	}
	public void setBotLeftVertexNr(int botLeftVertexNr) {
		this.botLeftVertexNr = botLeftVertexNr;
	}
	public int getBotRightVertexNr() {
		return botRightVertexNr;
	}
	public void setBotRightVertexNr(int botRightVertexNr) {
		this.botRightVertexNr = botRightVertexNr;
	} 
	public double[] getBotLeftCoord() {
		return botLeftCoord;
	}
	public double getSize() {
		return size;
	}
	 
	
}
