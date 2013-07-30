package matrixgeneration;


public enum Direction {
	DX {
		@Override
		public double getChi1(double var, int varNr, double size,
				double[] botLeftCoord) {
			if(isDifferentialRequired(varNr,this))
				return 1.0/size;
			return D.getChi1(var, varNr, size, botLeftCoord);
		}

		@Override
		public double getChi2(double var, int varNr, double size,
				double[] botLeftCoord) {
			if(isDifferentialRequired(varNr, this))
				return -1.0/size;
			return D.getChi2(var, varNr, size, botLeftCoord);
		}

		@Override
		public double getChi3(double var, int varNr, double size,
				double[] botLeftCoord) {
			if(isDifferentialRequired(varNr, this))
				return 1.0/size - 2*D.getChi1(var, varNr, size, botLeftCoord)/size;
			return D.getChi3(var, varNr, size, botLeftCoord);
		}
	},
	DY {
		@Override
		public double getChi1(double var, int varNr, double size,
				double[] botLeftCoord) {
			if(isDifferentialRequired(varNr, this))
				return 1.0/size;
			return D.getChi1(var, varNr, size, botLeftCoord);
		}

		@Override
		public double getChi2(double var, int varNr, double size,
				double[] botLeftCoord) {
			if(isDifferentialRequired(varNr, this))
				return -1.0/size;
			return D.getChi2(var, varNr, size, botLeftCoord);
		}

		@Override
		public double getChi3(double var, int varNr, double size,
				double[] botLeftCoord) {
			if(isDifferentialRequired(varNr, this))
				return 1.0/size - 2*D.getChi1(var, varNr, size, botLeftCoord)/size;
			return D.getChi3(var, varNr, size, botLeftCoord);
		}
	},
	DX_AND_DY{

		@Override
		public double getChi1(double var, int varNr, double size, double[] botLeftCoord) {
			throw new RuntimeException(); 
		}

		@Override
		public double getChi2(double var, int varNr, double size, double[] botLeftCoord) {
			throw new RuntimeException(); 
		}

		@Override
		public double getChi3(double var, int varNr, double size, double[] botLeftCoord) {
			throw new RuntimeException();
		}
		
	},
	D {
		@Override
		public double getChi1(double var, int varNr, double size,
				double[] botLeftCoord) {
			return (var - botLeftCoord[varNr])/size;
		}

		@Override
		public double getChi2(double var, int varNr, double size,
				double[] botLeftCoord) {
			return 1 - getChi1(var, varNr, size, botLeftCoord);
		}

		@Override
		public double getChi3(double var, int varNr, double size,
				double[] botLeftCoord) {
			return getChi1(var, varNr, size, botLeftCoord)*getChi2(var, varNr, size, botLeftCoord);
		}
	};
	public abstract double getChi1(double var, int varNr, double size, double[] botLeftCoord);
	public abstract double getChi2(double var, int varNr, double size, double[] botLeftCoord);
	public abstract double getChi3(double var, int varNr, double size, double[] botLeftCoord);
	
	private static boolean isDifferentialRequired(int varNr, Direction direction){
		if(direction == DX)
			return varNr == 0; 
		if(direction == DY)
			return varNr == 1;
		throw new RuntimeException();
	}
	
	
	
	/*
	private double getChi1(double var, int varNr, boolean derivative){
		if(!derivative)
			return (var - botLeftCoord[varNr])/size;
		return 1.0/size; 
	}
	
	private double getChi2(double var, int varNr, boolean derivative){
		if(!derivative)
			return 1 - getChi1(var, varNr, false);
		return -1.0/size;
		
	}
	
	private double getChi3(double var, int varNr, boolean derivative){
		if(!derivative)
			return getChi1(var, varNr,false)*getChi2(var, varNr,false);
		
		return 1.0/size - 2*getChi1(var, varNr, false)/size;
	}
	*/
}
