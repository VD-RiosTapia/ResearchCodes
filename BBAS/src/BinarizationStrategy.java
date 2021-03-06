public class BinarizationStrategy {

    /* Trasnfer function S-Shape */
	
    private static double sShape1(double x) {
        return (1 / (1 + Math.pow(Math.E, -2 * x)));
    }

    @SuppressWarnings("unused")
    private static double sShape2(double x) {
        return (1 / (1 + Math.pow(Math.E, -x)));
    }

    @SuppressWarnings("unused")
    private static double sShape3(double x) {
        return (1 / (1 + Math.pow(Math.E, -x / 2)));
    }

    @SuppressWarnings("unused")
    private static double sShape4(double x) {
        return (1 / (1 + Math.pow(Math.E, -x / 3)));
    }

    /* Trasnfer function V-Shape */
    
    @SuppressWarnings("unused")
    private static double vShape1(double x) {
        return Math.abs(erf((Math.sqrt(Math.PI) / 2) * x));
    }

    @SuppressWarnings("unused")
    private static double vShape2(double x) {
        return Math.abs(Math.tanh(x));
    }
    
    @SuppressWarnings("unused")
    private static double vShape3(double x) {
        return Math.abs(x / Math.sqrt(1 + Math.pow(x, 2)));
    }

    @SuppressWarnings("unused")
    private static double vShape4(double x) {
        return Math.abs((2 / Math.PI) * Math.atan((Math.PI / 2) * x));
    }

    /* Discretization methods */
    
    @SuppressWarnings("unused")
    private static int basic(double x) {
        return 0.5 <= x ? 1 : 0;
    }
    
    private static int standard(double x) {
        return Distribution.uniform() <= x ? 1 : 0;
    }

    @SuppressWarnings("unused")
    private static int complement(double x) {
        return Distribution.uniform() <= x ? standard(1 - x) : 0;
    }

    @SuppressWarnings("unused")
    private static int staticProbability(double x, double alpha) {
        return alpha >= x ? 0 : (alpha < x && x <= ((1 + alpha) / 2)) ? standard(x) : 1;
    }

    private static int elitist(double x) {
        return Distribution.uniform() < x ? standard(x) : 0;
    }

    private static double erf(double z) {
        double q = 1.0 / (1.0 + 0.5 * Math.abs(z));

        double ans = 1 - q * Math.exp(-z * z - 1.26551223
                + q * (1.00002368
                + q * (0.37409196
                + q * (0.09678418
                + q * (-0.18628806
                + q * (0.27886807
                + q * (-1.13520398
                + q * (1.48851587
                + q * (-0.82215223
                + q * (0.17087277))))))))));

        return z >= 0 ? ans : -ans;
    }
    
    protected static int toBinary(double x) {
        return elitist(sShape1(x));
    }
}