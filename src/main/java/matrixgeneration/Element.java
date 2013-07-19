package matrixgeneration;

import java.util.Map;
import java.util.Random;

import pl.edu.agh.mes.gg.MatrixUtils;


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
	
	
	private DoubleArgFunction topLeftFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			
			return (1-(x-botLeftCoord[0])/size)*(y-botLeftCoord[1])/size;
		}
	};
	
	private DoubleArgFunction topRightFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){

			return (x-botLeftCoord[0])/size*(y-botLeftCoord[1])/size;			
		}
		
	};
	
	private DoubleArgFunction botLeftFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			
			return (1-(x-botLeftCoord[0])/size)*(1-(y-botLeftCoord[1])/size);
		}
		
	};
	
	private DoubleArgFunction botRightFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			
			return (x-botLeftCoord[0])/size*(1-(y-botLeftCoord[1])/size);
		}
		
	};
	
	private DoubleArgFunction leftFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			double value = (1-(x-botLeftCoord[0])/size)*(1-(y-botLeftCoord[1])/size)*(y-botLeftCoord[1])/size;
			switch(position){
			case BOT_LEFT:
				if(firstTier)
					return value; 
				return (value + topLeftFunction.computeValue(x, y))/4.0;
			case TOP_LEFT:
				if(firstTier)
					return value; 
				return (value + botLeftFunction.computeValue(x, y))/4.0;
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
			double value = (1-(x-botLeftCoord[0])/size)*(x-botLeftCoord[0])/size*(y-botLeftCoord[1])/size;
			switch(position){
			case BOT_LEFT:
				return value;
			case TOP_LEFT:
				if(firstTier)
					return value; 
				return (value + topRightFunction.computeValue(x, y))/4.0;
			case TOP_RIGHT:
				if(firstTier)
					return value; 
				return (value + topLeftFunction.computeValue(x, y))/4.0;
			case BOT_RIGHT:
				return value; 
			default:
				throw new RuntimeException(); 
			}
			
		}
	};
	
	private DoubleArgFunction rightFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			return (x-botLeftCoord[0])/size*(1-(y-botLeftCoord[1])/size)*(y-botLeftCoord[1])/size;
		}
	};
	
	private DoubleArgFunction botFunction = new DoubleArgFunction(){
		
		public double computeValue(double x, double y){
			return (1-(x-botLeftCoord[0])/size)*(x-botLeftCoord[0])/size*(1-(y-botLeftCoord[1])/size);
		}
	};
	
	private DoubleArgFunction interiorFunction = new DoubleArgFunction(){
		public double computeValue(double x, double y){
			return (1-(x-botLeftCoord[0])/size)*(x-botLeftCoord[0])/size*(1-(y-botLeftCoord[1])/size)*(y-botLeftCoord[1])/size;
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
		

		double  m[][] = new double[4][4];
		double r[] = new double[4];
		fillTierMatrix(m, r, new DoubleArgFunction() {
			
			@Override
			public double computeValue(double x, double y) {
				
				return x*y;
			}
		}, 26);
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
			
			
			double result = topLeftFunction.computeValue(randomXWithinElement, randomYWithinElement)*
					this.nodeNrCoefficientMap.get(this.topLeftVertexNr);
			result += botLeftFunction.computeValue(randomXWithinElement, randomYWithinElement)*
					this.nodeNrCoefficientMap.get(this.botLeftVertexNr);
			result += topRightFunction.computeValue(randomXWithinElement, randomYWithinElement)*
					this.nodeNrCoefficientMap.get(this.topRightVertexNr);
			result += botRightFunction.computeValue(randomXWithinElement, randomYWithinElement)*
					this.nodeNrCoefficientMap.get(this.botRightVertexNr);
			
			if( Math.abs((result - f.computeValue(randomXWithinElement, randomYWithinElement)))  > 0.0001){
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
