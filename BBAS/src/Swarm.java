import java.util.ArrayList;

	//=============================================================================

public class Swarm {

	private final int nBats = 25;
	private final int T = 1000;
	private final double fmin = 0.75;
	private final double fmax = 1.25;
	private final double alpha = 0.9;
	private final double gamma = 0.9;
	private ArrayList<Bat> swarm = null;
	private Bat g = null;
	private long sTime, eTime;

	//=============================================================================

	public void execute() {
		startTime();	
		initRandom();
		run();
		endTime();
		log();	
	}
	
	//=============================================================================

	private void startTime() {
		sTime = System.currentTimeMillis();
	}

	//=============================================================================

	private void initRandom() {
		
		swarm = new ArrayList<>();
		g = new Bat();
		Bat b;
		for (int i = 0; i < nBats; i++) {
			
			b = new Bat();
        	if(!b.isFeasible()) {
        		b.repare();
        	}		
			
			b.entropy(); 
			swarm.add(b); 						
		}
		
		g.copy(swarm.get(0));
		
		for (int i = 1; i < nBats; i++) { 
			if (swarm.get(i).isBetterThan(g)) { 
				g.copy(swarm.get(i));
			}
		}
		
	}

	//=============================================================================

	private void run() {

		int t = 1; 
		Bat b = new Bat(); 
		
		while (t <= T) {
			
			for (int i = 0; i < nBats; i++) {
				b.copy(swarm.get(i));
				
				if(t != 1 && b.isStagnation()) {
					b.shannonMove();
					if (!b.isFeasible()) {
						b.repare();
					}
					b.entropy();
					swarm.get(i).copy(b);							
				}
				
				b.move(g, fmin, fmax);
				
				if (!b.isFeasible()) {
					b.repare();
				}
				b.entropy();
				
				if (b.hasExploitation() && (!b.isBetterThan(g))) {
					b.decreasesLoudness(alpha);
					b.increasesPulseEmission(gamma, t);
				}
				
				if (b.isBetterThan(g)) {
					g.copy(b);
				}
				
				swarm.get(i).copy(b);
				
				System.out.println(g.fitness());
				
			}
			
			t++;
		}
	}

	//=============================================================================
	
	private void endTime() {
		eTime = System.currentTimeMillis();
	}

	//=============================================================================

	private void log() {
		System.out.println(
			//String.format("%s, s: %s, t: %s", g, Distribution.getSeed(), (eTime - sTime))
			String.format("%s\n%s\n%s", g, Distribution.getSeed(), (eTime - sTime))
		);
		System.out.println("=========================");
	}

	//=============================================================================

}