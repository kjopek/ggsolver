package pl.edu.agh.mes.gg;

public class MatrixUtils {
	private static final double epsilon = 1e-10;
	
	public static void swapRows(int i, int j, double matrix[][], double rhs[]) {
		double tmp;
		for (int k = 0; k<matrix.length;k++) {
			tmp = matrix[i][k];
			matrix[i][k] = matrix[j][k];
			matrix[j][k] = tmp;
		}
		tmp = rhs[i];
		rhs[i] = rhs[j];
		rhs[j] = tmp;
	}
	
	public static void swapCols(int i, int j, double matrix[][]) {
		double tmp;
		for (int k = 0; k<matrix[i].length; k++) {
			tmp = matrix[k][i];
			matrix[k][i] = matrix[k][j];
			matrix[k][j] = tmp;
		}
		
	}
	
	public static void eliminate(int rowsToElim, double matrix[][], double rhs[]) {
		// TODO: pivoting!!
		double x;
		for (int i=0;i<rowsToElim;i++) {
			  
			  x = matrix[i][i];
			  
			  matrix[i][i] /= x;
			  
			  for (int j=i+1;j<matrix.length;j++) {
				  // on diagonal - only 1.0
				  matrix[i][j] /= x;
			  }
			  
			  rhs[i] /= x; 
			  
			  for (int j=i+1; j<matrix.length; j++) {
				  x = matrix[j][i];
				  for (int k=i; k<matrix.length; k++) {
					  matrix[j][k] -= x*matrix[i][k];
				  }
				  rhs[j] -= x*rhs[i];
				  matrix[j][i] = 0.0;
			  }
			  
		  }
		
	}
	
	public static void printMatrix(double matrix[][], double rhs[]) {
		assert matrix.length == rhs.length;
		final int N = matrix.length;
		
		for (int i=0; i<N; i++) {
			for (int j=0; j<N; j++) {
				System.out.print(String.format("% .15f ", matrix[i][j]));
			}
			System.out.println(String.format(" | % .15f", rhs[i]));
		}
		
	}
}
