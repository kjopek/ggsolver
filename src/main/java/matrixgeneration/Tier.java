package matrixgeneration;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Tier {
	
	private Element botLeftElement; 
	private Element topLeftElement; 
	private Element topRightElement; 
	private Element botRightElement;
	private int startNrAdj; 
	
	private double tierMatrix[][];
	private double rhs[];
	
	public Tier(Element topLeftElement, Element topRightElement, Element botLeftElement, Element botRightElement, 
			DoubleArgFunction f, NeumanBoundaryCondition neumanBoundaryCondition){
		this.botLeftElement = botLeftElement; 
		this.botRightElement = botRightElement;
		this.topLeftElement = topLeftElement; 
		this.topRightElement = topRightElement;
		
		tierMatrix = new double[21][21];
		rhs = new double[21];
		
		startNrAdj = botLeftElement.getBotLeftVertexNr();
		botLeftElement.fillTierMatrix(tierMatrix, rhs, f, startNrAdj, neumanBoundaryCondition);
		topLeftElement.fillTierMatrix(tierMatrix, rhs, f, startNrAdj, neumanBoundaryCondition);
		if(botRightElement != null)
			botRightElement.fillTierMatrix(tierMatrix, rhs, f, startNrAdj, neumanBoundaryCondition);
		topRightElement.fillTierMatrix(tierMatrix, rhs, f, startNrAdj, neumanBoundaryCondition);
		
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
	
	public double checkInterpolationCorectness(DoubleArgFunction f){
		double r = 0.0;
		r += botLeftElement.checkInterpolationCorectness(f);
		r += topLeftElement.checkInterpolationCorectness(f);
		r += topRightElement.checkInterpolationCorectness(f);
		if(botRightElement != null)
			r += botRightElement.checkInterpolationCorectness(f);
		return r;
	}

	
	public void applyDirichletBoundaryCondition(int[] functionNumbers, double value){
		if(!topLeftElement.isFirstTier())
			throw new UnsupportedOperationException(); 
		for(int i : functionNumbers){
			for(int j = 0; j<tierMatrix.length; j++)
				tierMatrix[i][j] = 0; 
			tierMatrix[i][i] = 1; 
			rhs[i] = value;
		}
	}
	
	public void getSolution(Map<double[],int[]> points, double[][] solution){
		
		for(Iterator<double[]> iterator = points.keySet().iterator(); iterator.hasNext(); ){
			double[] point = iterator.next();
			int[] indices = points.get(point);
			double x = point[0];
			double y = point[1];
			if(topLeftElement.isWithinElement(x, y)){
				solution[indices[0]][indices[1]] = topLeftElement.getSolution(x, y);
				System.out.println(x + " "  + y + " " + solution[indices[0]][indices[1]]);
				iterator.remove();
				continue;
			}
			if(topRightElement.isWithinElement(x, y)){
				solution[indices[0]][indices[1]] = topRightElement.getSolution(x, y);
				System.out.println(x + " "  + y + " " + solution[indices[0]][indices[1]]);
				iterator.remove();
				continue;
			}
			if(botLeftElement.isWithinElement(x, y)){
				solution[indices[0]][indices[1]] = botLeftElement.getSolution(x, y);
				System.out.println(x + " "  + y + " " + solution[indices[0]][indices[1]]);
				iterator.remove();
				continue;
			}
			if(botRightElement != null && botRightElement.isWithinElement(x, y)){
				solution[indices[0]][indices[1]] = topLeftElement.getSolution(x, y);
				System.out.println(x + " "  + y + " " + solution[indices[0]][indices[1]]);
				iterator.remove();
				continue;
			}
		}
		
	}
	
}
