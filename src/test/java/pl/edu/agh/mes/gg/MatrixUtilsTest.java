package pl.edu.agh.mes.gg;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class MatrixUtilsTest {
	private static final double epsilon = 1e-10;
	
	public MatrixUtilsTest() {
		
	}
	
	@Test
	public void testSwapCols() {
		double matrix[][] = {{1.0, 2.0, 3.0},
							 {4.0, 5.0, 6.0},
							 {7.0, 8.0, 9.0}};
		MatrixUtils.swapCols(0, 1, matrix);
		assertTrue(Math.abs(matrix[0][0]-2.0) < epsilon);
		assertTrue(Math.abs(matrix[0][1]-1.0) < epsilon);
		
		assertTrue(Math.abs(matrix[1][0]-5.0) < epsilon);
		assertTrue(Math.abs(matrix[1][1]-4.0) < epsilon);
		
		assertTrue(Math.abs(matrix[2][0]-8.0) < epsilon);
		assertTrue(Math.abs(matrix[2][1]-7.0) < epsilon);
	}
	
	@Test
	public void testSwapRows() {
		double matrix[][] = {{1.0, 2.0, 3.0},
				 {4.0, 5.0, 6.0},
				 {7.0, 8.0, 9.0}};
		double rhs[] = {1,2,3};
		
		MatrixUtils.swapRows(0, 1, matrix, rhs);
		
		assertTrue(Math.abs(matrix[0][0]-4.0) < epsilon);
		assertTrue(Math.abs(matrix[1][0]-1.0) < epsilon);
		
		assertTrue(Math.abs(matrix[0][1]-5.0) < epsilon);
		assertTrue(Math.abs(matrix[1][1]-2.0) < epsilon);
		
		assertTrue(Math.abs(matrix[0][2]-6.0) < epsilon);
		assertTrue(Math.abs(matrix[1][2]-3.0) < epsilon);		
		
		assertTrue(Math.abs(rhs[0] - 2.0) < epsilon);
		assertTrue(Math.abs(rhs[1] - 1.0) < epsilon);

	}
	
	@Test
	public void testEliminate() {
		double matrix[][] = {{2.0, 4.0, 6.0},
				 {1.0, 3.0, 1.0},
				 {7.0, 8.0, 9.0}};
		double rhs[] = {1.0, 1.0, 1.0};

		MatrixUtils.eliminate(3, matrix, rhs);

		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix.length; j++) {
				System.out.print(matrix[i][j]+ " ");
			}
			System.out.print ("| "+rhs[i]);
			System.out.println();
		}
		
		assertTrue(Math.abs(matrix[1][0]) < epsilon);
		assertTrue(Math.abs(matrix[2][0]) < epsilon);
	}
}
