package main.algorithms;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Variant2Alg extends AbstractAlg {

	public Variant2Alg(List<Double[]> points, int dim, PrintWriter outWriter) {
		super(points, dim, outWriter);
	}

	/**
	 * this arraylist keeps the items sorted on y-value
	 */
	private List<Double[]> ysorted = null;
	Comparator<Double[]> compy = new Comparator<Double[]>() {
			@Override
			public int compare(Double[] o1, Double[] o2) {
				return o1[1]<o2[1]?-1:1;
			}
		};
	
	@Override
	public void execute() {
		this.shortestDist = Double.POSITIVE_INFINITY;
		this.closestPoints = new Double[2][this.getDim()];
		this.ysorted = new ArrayList<Double[]>();
		
		// convenience
		List<Double[]> p = this.getPoints();
		
		int iterfrom = 0;
		
		// Loop over all the points, we start from the second point so that
		// we have at least 1 point in front of it, and thus we have an initial distance.
		for (int i = 1; i < this.getPoints().size(); i++) { 
			//X direction
			for(int j = iterfrom; j < i; j++) {
				//find first eligible point
				if (p.get(i)[0] - p.get(j)[0] > Math.sqrt(shortestDist)) {
					iterfrom++;
					continue;
				}
			}
				
			//Y erection
			this.ysorted.addAll(p.subList(iterfrom, i+1));
			this.ysorted.sort(compy);
			
			for(int j = 0; j < ysorted.size(); j++) {
				if(Math.abs(p.get(i)[1] - ysorted.get(j)[1]) > shortestDist) continue;
				
				// calc dist and check
				// copied from SimpleAlg.java
				double currentDist = this.calcDist(p.get(i), ysorted.get(j)); // This is most intensive operation
				if (shortestDist > currentDist) {
					closestPoints[0] = this.getPoints().get(i);
					closestPoints[1] = ysorted.get(j);
					shortestDist = currentDist;
				}
			}
			this.ysorted.clear();
		}	
	}
}
