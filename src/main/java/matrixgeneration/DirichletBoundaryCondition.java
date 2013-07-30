package matrixgeneration;

import java.util.List;

public class DirichletBoundaryCondition {
	
	public void applyDirichletBoundaryCondition(double[][] matrix, double[] rhs, List<Tier> tierList,
			int[] functionNumbers, double value){
		//apply dirichlet boundary condition to global matrix
		for(int i : functionNumbers){
			for(int j = 0; j < matrix.length; j++)
				matrix[i][j] = 0; 
			matrix[i][i] = 1; 
			rhs[i] = value;
		}
		
		//apply dirichlet boundary condition to frontal matrices
		Tier firstTier = tierList.get(0);
		for(int i : functionNumbers){
			if(i > 8)
				throw new UnsupportedOperationException();
		}
		firstTier.applyDirichletBoundaryCondition(functionNumbers, value);
		
	}
}
