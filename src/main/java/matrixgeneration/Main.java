package matrixgeneration;

import java.util.LinkedList;
import java.util.List;

public class Main {
	
	public static void main(String[] args){
		
		
		int nrOfTiers = 5; 
		DoubleArgFunction f = new DoubleArgFunction(){

			@Override
			public double computeValue(double x, double y) {
				return 1; 
			}
			
		};
		
		
		MatrixGenerator matrixGenerator = new MatrixGenerator(); 
		List<Tier> tierList = matrixGenerator.createMatrixAndRhs(nrOfTiers, -1, -1, 1,f);
		
		
		
		//wypisanie macierzy poskelajanej z macierzy 8x8 (mozna sprawdzic ze wyochodzi 1)
		int matrixSize = 3 + nrOfTiers*3 + 2 + 1;
		double[][] matrix = new double[matrixSize][matrixSize];
		double[] rhs = new double[matrixSize];
		
		for(Tier tier : tierList)
			tier.fillMatrixAndRhs(matrix, rhs);
		
		
		
		for(int i =0; i< matrixSize; i++){
			System.out.println();
			for(int j=0; j<matrixSize; j++){
				System.out.print(matrix[i][j] + " ");
			}
		}
			
		System.out.println();
		System.out.println();
		for(int i =0; i< matrixSize; i++){
			System.out.println(rhs[i]);
		}
		
		
		
		
	}
	

}
