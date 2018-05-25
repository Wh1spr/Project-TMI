package main.algorithms;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Variant2Alg extends AbstractAlg {

	public Variant2Alg(List<Double[]> points, int dim, PrintWriter outWriter) {
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
	
	Comparator<Double[]> compy = new Comparator<Double[]>() {
		@Override
		public int compare(Double[] o1, Double[] o2) {
			return Double.compare(o1[1], o2[1]);
		}
	};
	
	private TreeSet<Double[]> ysort = new TreeSet<Double[]>(compy);
	
	@Override
	public void execute() {
		this.shortestDist = 36d;
		this.closestPoints = new Double[2][this.getDim()];
		
		this.kmax = 0d;
		this.kavg = 0d;
		this.ktotal = 0d;
		double kthis = 0d;
		// convenience
		List<Double[]> p = this.getPoints();
		
		Double[] next;
		// Loop over all the points, we start from the second point so that
		// we have at least 1 point in front of it, and thus we have an initial distance.
		for (int i = 1; i < this.getPoints().size(); i++) { 
			kthis = 0;
			ysort.add(p.get(i-1));
			
			Double[] lo = new Double[] {p.get(i)[0], p.get(i)[1] - Math.sqrt(this.shortestDist)};
			
			next = ysort.higher(lo);
			while(next != null && next[1] < p.get(i)[1] + Math.sqrt(this.shortestDist)) {
				if (next[0] < p.get(i)[0] - Math.sqrt(this.shortestDist)) {
					ysort.remove(next);
					next = ysort.higher(next);
					continue;
				}
				ktotal++;
				kthis++;
				double currentDist = this.calcDist(p.get(i), next); // This is most intensive operation
				if (shortestDist > currentDist) {
					closestPoints[0] = this.getPoints().get(i);
					closestPoints[1] = next;
					shortestDist = currentDist;
				}
				next = ysort.higher(next);
			}
			if (kthis > kmax) kmax = kthis;
			
		}
		kavg = ktotal / ((double)(p.size()));
		ysort.clear();
	}
	
}
