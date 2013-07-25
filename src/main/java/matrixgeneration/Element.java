package matrixgeneration;

import java.util.Map;
import java.util.Random;


public class Element {
	
	private Position position;
	private int topLeftVertexNr; 
	private int topRightVertexNr; 
	private int botLeftVertexNr; 
	private int botRightVertexNr;
	private int leftEdgeNr; 
	private int topEdgeNr; 
	private int rightEdgeNr; 
	private int botEdgeNr; 
	private int interiorNr; 
	private Map<Integer, Double> nodeNrCoefficientMap;
	
	private boolean firstTier; 
	
	private double[] botLeftCoord; 
	private double size; 

	private double getChi1(double var, int varNr){
		return (var - botLeftCoord[varNr])/size;
	}
	
	private double getChi2(double var, int varNr){
		return 1 - getChi1(var, varNr);
	}
	
	private double getChi3(double var, int varNr){
		return getChi1(var, varNr)*getChi2(var, varNr);
	}
	
	private DoubleArgFunction topLeftFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			double value = getChi2(x, 0)*getChi1(y, 1);
			if(firstTier)
				return value; 
			switch(position){
			case BOT_LEFT: 
				return value/2.0;
			case TOP_LEFT:
				return value + getChi2(x, 0)*getChi2(y, 1)/2.0 + getChi1(x, 0)*getChi1(y, 1)/2.0;
			case TOP_RIGHT:
				return value/2.0;
			case BOT_RIGHT:
				return value; 
			default:
				throw new RuntimeException();
			}
		}
	};
	
	private DoubleArgFunction topRightFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){

			double value =  getChi1(x, 0)*getChi1(y, 1);
			if(firstTier)
				return value;
			switch(position){
			case BOT_LEFT:
				return value;
			case TOP_LEFT:
				return value/2.0;
			case TOP_RIGHT:
				return value + getChi2(x, 0)*getChi1(y, 1)/2.0;
			case BOT_RIGHT:
				return value; 
			default:
				throw new RuntimeException();
			}
		}
		
	};
	
	private DoubleArgFunction botLeftFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			
			double value = getChi2(x, 0)*getChi2(y, 1);
			if(firstTier)
				return value; 
			switch(position){
			case BOT_LEFT:
				return value + getChi2(x, 0)*getChi1(y, 1)/2.0;
			case TOP_LEFT:
				return value / 2.0;
			case TOP_RIGHT:
				return value;
			case BOT_RIGHT:
				return value;
			default:
				throw new RuntimeException();
			}
		}
		
	};
	
	private DoubleArgFunction botRightFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			
			return getChi1(x, 0)*getChi2(y, 1);
		}
		
	};
	
	private DoubleArgFunction leftFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			double value = getChi2(x, 0)*getChi3(y, 1);
			
			switch(position){
			case BOT_LEFT:
				if(firstTier)
					return value; 
				return (value + getChi2(x, 0)*getChi1(y, 1))*0.25;
			case TOP_LEFT:
				if(firstTier)
					return value; 
				return (value + getChi2(x, 0)*getChi2(y, 1))*0.25;
			case TOP_RIGHT:
				return value; 
			case BOT_RIGHT:
				return value; 
			default:
				throw new RuntimeException();
			}
			 
		}
		
	};
	
	private DoubleArgFunction topFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			double value = getChi3(x, 0)*getChi1(y, 1);
			 
			switch(position){
			case BOT_LEFT:
				return value;
			case TOP_LEFT:
				if(firstTier)
					return value; 
				return (value + getChi1(x, 0)*getChi1(y, 1))*0.25;
			case TOP_RIGHT:
				if(firstTier)
					return value; 
				return (value + getChi2(x, 0)*getChi1(y, 1))*0.25;
			case BOT_RIGHT:
				return value; 
			default:
				throw new RuntimeException(); 
			}
			
		}
	};
	
	private DoubleArgFunction rightFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			return getChi1(x, 0)*getChi3(y, 1);
		}
	};
	
	private DoubleArgFunction botFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			return getChi3(x, 0)*getChi2(y, 1);
		}
	};
	
	private DoubleArgFunction interiorFunction = new DoubleArgFunction(){
		public double computeValue(double x, double y){
			return getChi3(x, 0)*getChi3(y, 1);
		}
	};
	
	private DoubleArgFunction[] shapeFunctions = new DoubleArgFunction[]{
			botLeftFunction, leftFunction, topLeftFunction, topFunction, topRightFunction, botFunction, interiorFunction, rightFunction, botRightFunction	
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
		this.leftEdgeNr = -1; 
		this.topEdgeNr = -1; 
		this.rightEdgeNr = -1; 
		this.botEdgeNr = -1; 
		this.interiorNr = -1; 
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
		
		this.topLeftVertexNr = nr + 2; 
		this.botLeftVertexNr = nr; 
		this.topRightVertexNr = nr + 4; 
		this.botRightVertexNr = nr + 14;
		
		this.leftEdgeNr = nr + 1; 
		this.topEdgeNr = nr + 3; 
		this.rightEdgeNr = nr + 9; 
		this.botEdgeNr = nr + 7;
		
		this.interiorNr = nr + 8; 
		
		botElement.setBotLeftVertexNr(nr);
		botElement.setTopLeftVertexNr(nr + 2);
		botElement.setBotRightVertexNr(nr + 12);
		botElement.setTopRightVertexNr(nr + 14);
		
		botElement.setLeftEdgeNr(nr + 1);
		botElement.setTopEdgeNr(nr + 7);
		botElement.setRightEdgeNr(nr + 13);
		botElement.setBotEdgeNr(nr + 5);
		
		botElement.setInteriorNr(nr + 6);
		
		
		rightElement.setTopLeftVertexNr(nr + 2);
		rightElement.setTopRightVertexNr(nr + 4);
		rightElement.setBotLeftVertexNr(nr + 14);
		rightElement.setBotRightVertexNr(nr + 16);
		
		rightElement.setLeftEdgeNr(nr + 9);
		rightElement.setTopEdgeNr(nr + 3);
		rightElement.setRightEdgeNr(nr + 11);
		rightElement.setBotEdgeNr(nr + 15);
		
		rightElement.setInteriorNr(nr + 10);
		
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
		
		this.topLeftVertexNr = nr + 4; 
		this.botLeftVertexNr = nr + 2; 
		this.topRightVertexNr = nr + 6; 
		this.botRightVertexNr = nr + 18; 
		
		this.leftEdgeNr = nr + 3; 
		this.topEdgeNr = nr + 5; 
		this.rightEdgeNr = nr + 13; 
		this.botEdgeNr = nr + 11;
		
		this.interiorNr = nr + 12; 
		
		botElement.setBotLeftVertexNr(nr);
		botElement.setTopLeftVertexNr(nr + 2);
		botElement.setBotRightVertexNr(nr + 16);
		botElement.setTopRightVertexNr(nr + 18);
		
		botElement.setLeftEdgeNr(nr + 1);
		botElement.setTopEdgeNr(nr + 11);
		botElement.setRightEdgeNr(nr + 17);
		botElement.setBotEdgeNr(nr + 9);
		
		botElement.setInteriorNr(nr + 10);
		
		rightElement.setTopLeftVertexNr(nr + 6);
		rightElement.setTopRightVertexNr(nr + 8);
		rightElement.setBotLeftVertexNr(nr + 18);
		rightElement.setBotRightVertexNr(nr + 20);
		
		rightElement.setLeftEdgeNr(nr + 13);
		rightElement.setTopEdgeNr(nr + 7);
		rightElement.setRightEdgeNr(nr + 15);
		rightElement.setBotEdgeNr(nr + 19);
		
		rightElement.setInteriorNr(nr + 14);
		
		Element[] elements = new Element[2];
		elements[0] = rightElement; 
		elements[1] = botElement;
		return elements;
		
	}
	
	public Element[] createLastTier(int nr){
		
		this.topLeftVertexNr = nr + 2; 
		this.botLeftVertexNr = nr; 
		this.topRightVertexNr = nr + 4; 
		this.botRightVertexNr = nr + 8;
		
		this.leftEdgeNr = nr + 1; 
		this.topEdgeNr = nr + 3; 
		this.rightEdgeNr = nr + 7; 
		this.botEdgeNr = nr + 5; 
		
		this.interiorNr = nr + 6; 
		

		return new Element[0];
		
		
	}
	
	private void comp(int indx1, int indx2, DoubleArgFunction f1, DoubleArgFunction f2,double[][] matrix){
		
		DoubleArgFunctionProduct product = new DoubleArgFunctionProduct();
		product.setFunctions(f1,f2);
		matrix[indx1][indx2] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
				botLeftCoord[1], botLeftCoord[1] + size, product);
	}
	
	public void fillMatrix(double[][] matrix, int startNrAdj){
		
		int[] functionNumbers = new int[] {
			botLeftVertexNr, leftEdgeNr, topLeftVertexNr, topEdgeNr, topRightVertexNr, botEdgeNr, interiorNr, rightEdgeNr, botRightVertexNr
		};
		
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				comp(functionNumbers[i] - startNrAdj, functionNumbers[j] - startNrAdj, shapeFunctions[i], shapeFunctions[j], matrix);
			}
		}
		
		
	}
	
	public void fillMatrix(double[][] matrix){
		fillMatrix(matrix, 0);
	}
	
	public void fillTierMatrix(double[][] matrix, double[] rhs, DoubleArgFunction f, int startNrAdj){
		fillMatrix(matrix, startNrAdj);
		fillRhs(rhs, f, startNrAdj);
		
	}
	
	public void fillRhs(double[] rhs, DoubleArgFunction f, int startNrAdj){

		int[] functionNumbers = new int[] {
			botLeftVertexNr, leftEdgeNr, topLeftVertexNr, topEdgeNr, topRightVertexNr, botEdgeNr, interiorNr, rightEdgeNr, botRightVertexNr
		};
		for(int i = 0; i<9; i++){
			DoubleArgFunctionProduct product = new DoubleArgFunctionProduct();
			product.setFunctions(shapeFunctions[i], f);
			
			rhs[functionNumbers[i] - startNrAdj] += GaussianQuadrature.definiteDoubleIntegral(botLeftCoord[0], botLeftCoord[0] + size,
					botLeftCoord[1], botLeftCoord[1] + size, product);
		}		
	}
	
	public void fillRhs(double[] rhs, DoubleArgFunction f){
		fillRhs(rhs, f, 0);
	}
	
	public void setCoefficients(Map<Integer, Double> nodeNrCoefficientMap){
		this.nodeNrCoefficientMap = nodeNrCoefficientMap;
	}
	
	public void checkInterpolationCorectness(DoubleArgFunction f){
		Random random = new Random();
		
		double x = this.botLeftCoord[0];
		double y = this.botLeftCoord[1]; 
		
		for(int i = 0; i<5; i++){
			double randomXWithinElement = x + random.nextDouble()*size; 
			double randomYWithinElement = y + random.nextDouble()*size; 
			
			int[] functionNumbers = new int[]{
				botLeftVertexNr, leftEdgeNr, topLeftVertexNr, topEdgeNr, topRightVertexNr, botEdgeNr, interiorNr, rightEdgeNr, botRightVertexNr	
			};
			double result = 0;
		
			for(int j = 0; j<9; j++){
				result += shapeFunctions[j].computeValue(randomXWithinElement, randomYWithinElement)
					*this.nodeNrCoefficientMap.get(functionNumbers[j]);
				
			}
			
			if(! (Math.abs((result - f.computeValue(randomXWithinElement, randomYWithinElement))) < 0.001)){
				System.out.println("x");
				throw new RuntimeException("Wrong for shape function space input function solution! " + (result - f.computeValue(randomXWithinElement, randomYWithinElement)));
			}
			
		}
		
		
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
	public int getLeftEdgeNr() {
		return leftEdgeNr;
	}
	public void setLeftEdgeNr(int leftEdgeNr) {
		this.leftEdgeNr = leftEdgeNr;
	}
	public int getTopEdgeNr() {
		return topEdgeNr;
	}
	public void setTopEdgeNr(int topEdgeNr) {
		this.topEdgeNr = topEdgeNr;
	}
	public int getRightEdgeNr() {
		return rightEdgeNr;
	}
	public void setRightEdgeNr(int rightEdgeNr) {
		this.rightEdgeNr = rightEdgeNr;
	}
	public int getBotEdgeNr() {
		return botEdgeNr;
	}
	public void setBotEdgeNr(int botEdgeNr) {
		this.botEdgeNr = botEdgeNr;
	}
	public int getInteriorNr() {
		return interiorNr;
	}
	public void setInteriorNr(int interiorNr) {
		this.interiorNr = interiorNr;
	}
	public double[] getBotLeftCoord() {
		return botLeftCoord;
	}
	public double getSize() {
		return size;
	}
	 
}
