
public class Main {
	public static void main(String[] args) {
		try {
			Input.read("resources/MKP17.txt");
			for (int k = 0; k < 1; k++) { //poner 30 para las repeticiones
				Distribution.newSeed();
				(new Swarm()).execute();
			}	
        } catch (Exception e) {
            System.err.println(String.format("%s \n%s", e.getMessage(), e.getCause()));
        }
    }
}