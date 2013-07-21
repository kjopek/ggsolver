package matrixgeneration;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mes.gg.MatrixUtils;

public class Main {
	
	public static void main(String[] args){
		
		Element elment = new Element(new double[]{0,0}, 1, Position.BOT_RIGHT);
		elment.setBotEdgeNr(0);
		elment.setBotLeftVertexNr(1);
		elment.setBotRightVertexNr(2);
		elment.setInteriorNr(3);
		elment.setLeftEdgeNr(4);
		elment.setRightEdgeNr(5);
		elment.setTopEdgeNr(6);
		elment.setTopLeftVertexNr(7);
		elment.setTopRightVertexNr(8);
		
		Element elment2 = new Element(new double[]{1,0},0.5,Position.BOT_LEFT);
		elment2.setBotEdgeNr(9);
		elment2.setBotLeftVertexNr(2);
		elment2.setBotRightVertexNr(10);
		elment2.setInteriorNr(11);
		elment2.setLeftEdgeNr(5);
		elment2.setRightEdgeNr(12);
		elment2.setTopEdgeNr(13);
		elment2.setTopLeftVertexNr(8);
		elment2.setTopRightVertexNr(14);
		
		Element elment3 = new Element(new double[]{1,0.5},0.5,Position.TOP_LEFT);
		elment3.setBotEdgeNr(13);
		elment3.setBotLeftVertexNr(2);
		elment3.setBotRightVertexNr(14);
		elment3.setInteriorNr(15);
		elment3.setLeftEdgeNr(5);
		elment3.setRightEdgeNr(16);
		elment3.setTopEdgeNr(17);
		elment3.setTopLeftVertexNr(8);
		elment3.setTopRightVertexNr(18);
		
		DoubleArgFunction f = new DoubleArgFunction() {
			
			@Override
			public double computeValue(double x, double y) {
				return y*x + 3 + x + y;
			}
		};
		double[][] matrix = new double[19][19];
		double[] rhs = new double[19];
		elment.fillTierMatrix(matrix, rhs, f,0);
		elment2.fillTierMatrix(matrix, rhs, f, 0);
		elment3.fillTierMatrix(matrix, rhs, f, 0);
		MatrixUtils.printMatrix(matrix, rhs);
		MatrixUtils.eliminate(19, matrix, rhs);
		Map<Integer,Double> solution = MatrixUtils.getSolutionThroughBackwardSubstitution(matrix, rhs);
		for(Map.Entry<Integer, Double> entry : solution.entrySet()){
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		elment.setCoefficients(solution);
		elment2.setCoefficients(solution);
		elment3.setCoefficients(solution);
		elment.checkInterpolationCorectness(f);
		elment2.checkInterpolationCorectness(f);
		elment3.checkInterpolationCorectness(f);
		System.out.println("ok");
	}
}
