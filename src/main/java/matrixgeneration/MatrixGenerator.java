package matrixgeneration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MatrixGenerator {
	
	private List<Element> elementsList; 
	private int nr; 
	
	private double[][] matrix; 
	private double[] rhs; 
	
	public List<Tier> createMatrixAndRhs(int nrOfTiers, double botLeftX, double botLeftY, double size, DoubleArgFunction f){
		
		if(nrOfTiers == 1)
			throw new RuntimeException("co najmniej 2");
		
		nr = 0; 
		elementsList = new LinkedList<Element>();
		Element element = new Element(new double[] {botLeftX, botLeftY + size / 2.0 } , size / 2.0, Position.TOP_LEFT);
		
		Element[] elements = element.createFirstTier(nr);
		
		elementsList.add(element);
		elementsList.add(elements[0]);
		elementsList.add(elements[1]);
		nr+=5; 
		
		double x = element.getBotLeftCoord()[0];
		double y = element.getBotLeftCoord()[1];
		double s = element.getSize();
		
		for(int i = 1; i < nrOfTiers ; i++){
			
			x = x + s; 
			y = y - s / 2.0;
			s = s /2.0;
			element = new Element(new double[] { x, y}, s, Position.TOP_LEFT);
			elements = element.createAnotherTier(nr);
			
			elementsList.add(element);
			elementsList.add(elements[0]);
			elementsList.add(elements[1]);
			nr+=3;
			
			
			
		}
		
		x = x + s; 
		y = y - s;
		//s = s /2.0;
		element = new Element(new double[] { x, y}, s, Position.BOT_RIGHT);
		element.createLastTier(nr);
		elementsList.add(element);
		
		int matrixSize = 3 + nrOfTiers*3 + 2 + 1; 
		matrix = new double[matrixSize][matrixSize];
		rhs = new double[matrixSize];

		for(Element matrixCreationElement : elementsList){
			matrixCreationElement.fillMatrix(matrix);
			matrixCreationElement.fillRhs(rhs, f);
		}
		
		
		List<Tier> tierList = new ArrayList<Tier>(nrOfTiers);
		
		for(int i =0; i<nrOfTiers; i++){
			Tier tier; 
			if(i == nrOfTiers -1){
				tier = new Tier(elementsList.get(i*3),elementsList.get(i*3 + 1),elementsList.get(i*3 + 2),elementsList.get(i*3 + 3),f);
				
			}
			else{
				tier = new Tier(elementsList.get(i*3),elementsList.get(i*3 + 1),elementsList.get(i*3 + 2),null,f);
				
			}
			tierList.add(tier);
		}
		
		
		
		return tierList;
		
	}

	public double[][] getMatrix() {
		return matrix;
	}

	public double[] getRhs() {
		return rhs;
	}
	
	
	

}
