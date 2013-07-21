package matrixgeneration;

import java.util.Map;

public class Tier {
	
	private Element botLeftElement; 
	private Element topLeftElement; 
	private Element topRightElement; 
	private Element botRightElement;
	private int startNrAdj; 
	
	private double tierMatrix[][];
	private double rhs[];
	
	public Tier(Element topLeftElement, Element topRightElement, Element botLeftElement, Element botRightElement, DoubleArgFunction f){
		this.botLeftElement = botLeftElement; 
		this.botRightElement = botRightElement;
		this.topLeftElement = topLeftElement; 
		this.topRightElement = topRightElement;
		/*
		tierMatrix = new double[21][21];
		rhs = new double[21];
		
		startNrAdj = botLeftElement.getBotLeftVertexNr();
		botLeftElement.fillTierMatrix(tierMatrix, rhs, f, startNrAdj);
		topLeftElement.fillTierMatrix(tierMatrix, rhs, f, startNrAdj);
		if(botRightElement != null)
			botRightElement.fillTierMatrix(tierMatrix, rhs, f, startNrAdj);
		topRightElement.fillTierMatrix(tierMatrix, rhs, f, startNrAdj);
		*/
	}

	public double[][] getMatrix(){
		return tierMatrix;
	}
	
	public double[] getRhs(){
		return rhs; 
	}
	
	public void fillMatrixAndRhs(double[][] matrix, double[] rhs){
		
		for(int i=0; i<tierMatrix.length; i++)
			for(int j=0; j<tierMatrix.length; j++){
				if(i+startNrAdj < matrix.length && j+startNrAdj < matrix.length)
				matrix[i+startNrAdj][j+startNrAdj] += tierMatrix[i][j];
			}
		
		for(int i =0; i<this.rhs.length; i++)
			if(i+startNrAdj < rhs.length)
				rhs[i+startNrAdj] += this.rhs[i]; 
		
	}
	
	public void setCoefficients(Map<Integer, Double> nodeNrCoefficientMap){
		botLeftElement.setCoefficients(nodeNrCoefficientMap);
		topLeftElement.setCoefficients(nodeNrCoefficientMap);
		topRightElement.setCoefficients(nodeNrCoefficientMap);
		if(botRightElement != null)
			botRightElement.setCoefficients(nodeNrCoefficientMap);
	}
	
	public void checkInterpolationCorectness(DoubleArgFunction f){
		botLeftElement.checkInterpolationCorectness(f);
		topLeftElement.checkInterpolationCorectness(f);
		topRightElement.checkInterpolationCorectness(f);
		if(botRightElement != null)
			botRightElement.checkInterpolationCorectness(f);
	}
	
	
}
