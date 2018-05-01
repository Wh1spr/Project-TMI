package main.algorithms;

import java.io.PrintWriter;
import java.util.List;

public class SimpleAlg extends AbstractAlg {

	public SimpleAlg(List<Double[]> points, int dim, PrintWriter outWriter) {
		super(points, dim, outWriter);
	}

	@Override
	public void execute() {
		
		// DISTANCES ARE SQUARED, ONLY OUTPUT IS CORRECT DISTANCE
		
		this.shortestDist = Double.POSITIVE_INFINITY;
		
		this.closestPoints = new Double[2][this.getDim()];
		
		double currentDist = 0;
		for(int i = 0; i < this.getPoints().size(); i++) {
			for(int j = i+1; j < this.getPoints().size(); j++) {
				
				currentDist = this.calcDist(this.getPoints().get(i), this.getPoints().get(j));
				if (shortestDist > currentDist) {
					// Indexes are switched here because Simple searches to the right of i,
					// While the variants search to the left of index i, making them switch places
					// in the output files
					closestPoints[0] = this.getPoints().get(j);
					closestPoints[1] = this.getPoints().get(i);
					shortestDist = currentDist;
				}
			}
		}
		
		
	}

}
