package main.algorithms;

import java.io.PrintWriter;
import java.util.List;

public class Variant1Alg extends AbstractAlg {

	public Variant1Alg(List<Double[]> points, int dim, PrintWriter outWriter) {
		super(points, dim, outWriter);
	}

	private double kmax = 0d;
	private double kavg = 0d;
	private double ktotal = 0d;
	
	public double getkmax() {
		return this.kmax;
	}
	public double getkavg() {
		return this.kavg;
	}
	public double getktotal() {
		return this.ktotal;
	}
	
	@Override
	public void execute() {
		this.shortestDist = Double.POSITIVE_INFINITY;
		this.closestPoints = new Double[2][this.getDim()];
		this.kmax = 0d;
		this.kavg = 0d;
		this.ktotal = 0d;
		double kthis = 0d;
		// convenience
		List<Double[]> p = this.getPoints();
		
		// Loop over all the points, we start from the second point so that
		// we have at least 1 point in front of it, and thus we have an initial distance.
		for (int i = 1; i < this.getPoints().size(); i++) {
			kthis = 0;
			
			for (int j = i-1; j >= 0; j--) {
				if (p.get(i)[0] - p.get(j)[0] > Math.sqrt(shortestDist)) {
					break;
				}
				// calc dist and check
				// copied from SimpleAlg.java
				ktotal++;
				kthis++;
				double currentDist = this.calcDist(p.get(i), p.get(j)); // This is most intensive operation
				if (shortestDist > currentDist) {
					closestPoints[0] = this.getPoints().get(i);
					closestPoints[1] = this.getPoints().get(j);
					shortestDist = currentDist;
				}
				
			} 
			if (kthis > kmax) kmax = kthis;
		}
		kavg = ktotal / ((double)(p.size()));
	}

}
