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
		System.out.println(botLeftCoord[0] + " " + botLeftCoord[1] + " " + size);
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
		
		
//		this.botLeftCoord[1] -= size; 
//		this.size*=2; 
//		this.position = Position.BOT_RIGHT;
		System.out.println("size " + size);
		System.out.println(botLeftVertexNr);
		System.out.println(topLeftVertexNr);
		System.out.println(topRightVertexNr);
		System.out.println(botRightVertexNr);
		System.out.println(botLeftCoord[0]);
		System.out.println(botLeftCoord[1] + " Kkkk");
		
		double  m[][] = new double[4][4];
		double r[] = new double[4];
		fillTierMatrix(m, r, new DoubleArgFunction() {
			
			@Override
			public double computeValue(double x, double y) {
				
				return x*y;
			}
		}, 26);
		System.out.println("test ---------------");
		MatrixUtils.printMatrix(m, r);
		System.out.println("test ---------------");
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
	public double[] getBotLeftCoord() {
		return botLeftCoord;
	}
	public double getSize() {
		return size;
	}
	 
	
}
