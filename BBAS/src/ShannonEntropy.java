public class ShannonEntropy {
	
	protected double movements;
	protected double[] entropy;
	protected double[] zeroCounter;
	protected double[] oneCounter;
	protected double[] zeroProbability;
	protected double[] oneProbability;
	
	public ShannonEntropy(int dimension) {
		movements = 0;
		zeroCounter = new double[dimension];
		oneCounter = new double[dimension];
		zeroProbability = new double[dimension];
		oneProbability = new double[dimension];
		entropy = new double[dimension];
	}
	
	protected void increaseMovement(){
		movements = movements + 1;
	}

	protected void increaseZeroCounter(int index) {
		zeroCounter[index] = zeroCounter[index] + 1;
	}

	protected void increaseOneCounter(int index) {
		oneCounter[index] = oneCounter[index] + 1;
	}
	
	protected void calculateZeroProbability(int index){
		zeroProbability[index] = zeroCounter[index]/movements;
	}
	
	protected void calculateOneProbability(int index){
		oneProbability[index] = oneCounter[index]/movements;
	}

	protected void calculateEntropy(int index) {
		
		if (zeroProbability[index] == 1 || oneProbability[index] == 1) {
			entropy[index] = 0;
		}else{
			entropy[index] = -( (zeroProbability[index] * log2(zeroProbability[index])) + (oneProbability[index] * log2(oneProbability[index])) );
		}

	}
	
	protected void copy(final Object object) {
		if (object instanceof ShannonEntropy) {
			movements = ((ShannonEntropy) object).movements;
			System.arraycopy(((ShannonEntropy) object).zeroCounter, 0, zeroCounter, 0, zeroCounter.length);
			System.arraycopy(((ShannonEntropy) object).oneCounter, 0, oneCounter, 0, oneCounter.length);
			System.arraycopy(((ShannonEntropy) object).zeroProbability, 0, zeroProbability, 0, zeroProbability.length);
			System.arraycopy(((ShannonEntropy) object).oneProbability, 0, oneProbability, 0, oneProbability.length);
			System.arraycopy(((ShannonEntropy) object).entropy, 0, entropy, 0, entropy.length);
		}
	}

	protected boolean isLocalOptimun(int index, boolean status) {
		if(entropy[index] <= 0.3) { //95% de probabilidad de que no cambie la variable
			status = true;
		}else {
			status = false;
		}
		return status;
	}

	private double log2(double n) {
		return (Math.log(n) / Math.log(2));
	}

	@Override
	public String toString() {
		return String.format("%s", java.util.Arrays.toString(entropy));
	}
}