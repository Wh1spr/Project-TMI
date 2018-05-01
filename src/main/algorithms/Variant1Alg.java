package main.algorithms;

import java.io.PrintWriter;
import java.util.List;

public class Variant1Alg extends AbstractAlg {

	public Variant1Alg(List<Double[]> points, int dim, PrintWriter outWriter) {
		super(points, dim, outWriter);
	}

	@Override
	public void execute() {
		this.shortestDist = Double.POSITIVE_INFINITY;
		this.closestPoints = new Double[2][this.getDim()];
		/*
		 * Kijk naar Abstractalg voor de methods die je kan gebruiken,
		 * enige waar je moet voor zorgen is dat op het einde van execute()
		 * de twee punten in closestPoints zitten en de de afstand tussen die 
		 * twee in shortestDist zit !!GEKWADRATEERDE AFSTAND!!, echte afstand wordt in 
		 * AbstractAlg berekend.
		 */
		
		// convenience
		List<Double[]> p = this.getPoints();
		
		// Points is already sorted by in all it's dimensions, so we can have an int "iterFrom"
		// for the second loop, so it ignores all points with x values smaller than current X - sqrt(shortestDist)
		// This has the added benefit we do not change points anywhere in the algorithm.
		
		int iterfrom = 0;
		
		// Loop over all the points, we start from the second point so that
		// we have at least 1 point in front of it, and thus we have an initial distance.
		for (int i = 1; i < this.getPoints().size(); i++) { 
			
			for(int j = iterfrom; j < i; j++) {
				//find first eligible point
				if (p.get(i)[0] - p.get(j)[0] > Math.sqrt(shortestDist)) {
					iterfrom++;
					continue;
				} 
				
				// calc dist and check
				// copied from SimpleAlg.java
				double currentDist = this.calcDist(p.get(i), p.get(j)); // This is most intensive operation
				if (shortestDist > currentDist) {
					closestPoints[0] = this.getPoints().get(i);
					closestPoints[1] = this.getPoints().get(j);
					shortestDist = currentDist;
				}
				
			}
			
		}
		
	}

}
