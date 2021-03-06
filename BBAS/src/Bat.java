public class Bat {
	
	protected final int nVariables = Problem.objectQuantity; 
	
	private double f; 										//Variable para la frecuencia
	private final double[] v = new double[nVariables]; 		//Arreglo de velocidades
	private final double[] x = new double[nVariables]; 		//Arreglo de posiciones
	protected double A; 									//Variable volumen del eco
	private double r; 										//Variable ratio de eco

	protected ShannonEntropy se = null;

	public Bat() {
		se = new ShannonEntropy(nVariables);
		for (int j = 0; j < nVariables; j++) {
			v[j] = Distribution.uniform(); 			
			x[j] = Distribution.uniform(2); 		
			A = Distribution.uniform(); 		
			r = Distribution.uniform(); 		
		}
	}

	protected boolean isBetterThan(final Bat g) { 
		return fitness() > g.fitness();	//NOTA: CAMBIAR A MIN O MAX DEPENDIENDO DE LO QUE SE NECESITE
	}

	protected float fitness(){
		float sum = 0;
		for(int i = 0; i < nVariables; i++){
			sum += x[i] *  Problem.prices[i];
		}
		return sum;
	}

	protected void repare() {
		int worstContributionIndex = 0;
		double sum;
		double worstContribution = 0;
		double contributionLevel[] = new double [nVariables];

		for(int j = 0; j < contributionLevel.length; j++) {
			sum = 0;
			for(int i = 0; i < Problem.b.length; i++) {
				sum += Problem.weights[i][j];
			}
			contributionLevel[j] = (Problem.prices[j] / sum);
		}

		do {
			worstContribution = contributionLevel[worstContributionIndex];
			for(int j = 0; j < contributionLevel.length; j++) {
				if (contributionLevel[j] < worstContribution) {
					worstContribution = contributionLevel[j];
					worstContributionIndex = j;
				}
			}
			x[worstContributionIndex] = 0;
			contributionLevel[worstContributionIndex] = Double.MAX_VALUE;
			worstContributionIndex = 0;
		} while(!isFeasible());
	}

	protected boolean isStagnation() {
		int j = 0;
		boolean status = true;
		while (j < nVariables && status) {
			status = se.isLocalOptimun(j, status);
			j++;
		}
		return status;
	}

	protected void shannonMove() {
		for (int j = 0; j < nVariables; j++) {
			x[j] = BinarizationStrategy.toBinary(x[j] + (se.entropy[j] * (Distribution.uniform(3) - 1)) );
		}
	}

	protected void entropy() {
		se.increaseMovement();
		for (int j = 0; j < nVariables; j++) {
			if (x[j] == 1) {
				se.increaseOneCounter(j);
			} else { 
				se.increaseZeroCounter(j);
			}
			se.calculateZeroProbability(j);
			se.calculateOneProbability(j);
			se.calculateEntropy(j);
		}
	}
	
	protected void move(final Bat g, final double fmin, final double fmax) {
		final double beta = Distribution.uniform();
		f = fmin + (fmax - fmin) * beta;
		for (int j = 0; j < nVariables; j++) {
			v[j] = v[j] + (x[j] - g.x[j]) * f;
			x[j] = BinarizationStrategy.toBinary(x[j] + v[j]);
		}
	}

	protected void copy(final Object object) {
		if (object instanceof Bat) {
			System.arraycopy(((Bat) object).v, 0, v, 0, nVariables);
			System.arraycopy(((Bat) object).x, 0, x, 0, nVariables);
			A = ((Bat) object).A;
			r = ((Bat) object).r;
			se.copy(((Bat)object).se);
		}
	}

	protected boolean isFeasible() {
		int i = 0;
    	int j = 0;
    	double sum = 0;
    	boolean feasible = true;
        while(feasible == true && i < Problem.m) {
        	sum = 0;
    		for(j = 0; j < Problem.objectQuantity; j++) {
        		sum += x[j] * Problem.weights[i][j];

        	}
    		
    		if(sum > Problem.b[i]) {
    			feasible = false;
    		}
        	i++;
        	
        }
        return feasible;
    }

	private float optimal() {
		return Problem.optimal;
	}

	private float diff() {
		return optimal() - fitness();
	}

	private float rpd() {
		return diff() / optimal() * 100f;
	}

	protected boolean hasExploration() {
		return r < Distribution.uniform();
	}

	protected boolean hasExploitation() {
		return A > Distribution.uniform();
	}

	protected void decreasesLoudness(final double alpha) {
		A = alpha * A;
	}

	protected void increasesPulseEmission(final double gamma, final int t) {
		r = r * (1 - (Math.pow(Math.E, (-gamma * t))));
	}

	protected void randomWalk(final double epsilon, final double avgA) {
		for (int j = 0; j < nVariables; j++) {
			x[j] = BinarizationStrategy.toBinary(x[j] + epsilon * avgA);
		}
	}

	@Override
	public String toString() {
		return String.format(/*
			"optimal value: %.1f, fitness: %.1f, diff: %s, rpd: %.2f%%, x: %s", 
			optimal(), 
			fitness(), 
			diff(),
			rpd(),
			java.util.Arrays.toString(x)*/
			"%.1f\n%.1f\n%s\n%.2f%%",//\n%s", 
			optimal(), 
			fitness(), 
			diff(),
			rpd()//,
			//java.util.Arrays.toString(x)
		);
		//return String.format("%.1f", fitness());
	}
}