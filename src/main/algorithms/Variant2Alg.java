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

	private TreeSet<Double[]> ysort = null;
	Comparator<Double[]> compy = new Comparator<Double[]>() {
			@Override
			public int compare(Double[] o1, Double[] o2) {
				if (o1 == null) return -1; // this is root, and root will be lower than hi   in this case
				if (o2 == null) return -1; // this is root, and lo   will be lower than root in this case
				if (o1 == o2) return 0; // case for two null values
				return BigDecimal.valueOf(o1[1]).subtract(BigDecimal.valueOf(o2[1]))
						.setScale(0, RoundingMode.UP).intValue();
//				if (o1==null||o2==null) return 0;
//				if (o1[1]==o2[1]) return 0;
//				return o1[1]<o2[1]?-1:1;
			}
		};
	
	@Override
	public void execute() {
		this.shortestDist = 36d;
				//this.calcDist(this.getPoints().get(0), this.getPoints().get(1));
		this.closestPoints = new Double[2][this.getDim()];
		this.ysort = new TreeSet<Double[]>(compy) {
			private static final long serialVersionUID = 1L;

			@Override
			public String toString() {
				Iterator<Double[]> it = iterator();
		        if (! it.hasNext())
		            return "[]";

		        StringBuilder sb = new StringBuilder();
		        sb.append('[');
		        for (;;) {
		            Double[] e = it.next();
		            sb.append(e[1]);
		            if (! it.hasNext())
		                return sb.append(']').toString();
		            sb.append(',').append(' ');
		        }
			}
		};
		
		// convenience
		List<Double[]> p = this.getPoints();
		
		Double[] floor;
		Double[] ceil;
		Double[] lo;
		Double[] hi;
		SortedSet<Double[]> ylist;
		Iterator<Double[]> iter;
		Double[] next;
		// Loop over all the points, we start from the second point so that
		// we have at least 1 point in front of it, and thus we have an initial distance.
		for (int i = 1; i < this.getPoints().size(); i++) { 
			ysort.add(p.get(i-1));
			
			floor = p.get(i).clone();
			ceil = p.get(i).clone();
			ceil[1] += Math.sqrt(this.shortestDist);
			floor[1] -= Math.sqrt(this.shortestDist);
			
			lo = ysort.ceiling(floor);
			hi = ysort.floor(ceil);
			
			if (compy.compare(lo, hi) > 0) continue;
			
//			System.out.println(String.format("Floor = %f\nCeiling = %f\nTreeset = %s\n\n", floor[1], ceil[1], ysort.toString()));
			
			ylist = ysort.subSet(lo,true,hi,true);
			iter = ylist.iterator();
			next = null;
			while(iter.hasNext()) {
				next = iter.next();
				//check x
				if (p.get(i)[0] - next[0] > Math.sqrt(this.shortestDist)) {
					iter.remove();
					continue;
				}
				
				double currentDist = this.calcDist(p.get(i), next); // This is most intensive operation
				if (shortestDist > currentDist) {
					closestPoints[0] = this.getPoints().get(i);
					closestPoints[1] = next;
					shortestDist = currentDist;
				}
				
			}
			
//			//X direction
//			for(int j = iterfrom; j < i; j++) {
//				//find first eligible point
//				if (p.get(i)[0] - p.get(j)[0] > Math.sqrt(shortestDist)) {
//					iterfrom++;
//					continue;
//				}
//			}
//				
//			//Y direction
//			this.ysorted.addAll(p.subList(iterfrom, i));
//			this.ysorted.sort(compy);
//			
//			for(int j = 0; j < ysorted.size(); j++) {
//				if(Math.abs(p.get(i)[1] - ysorted.get(j)[1]) > shortestDist) continue;
//				
//				// calc dist and check
//				// copied from SimpleAlg.java
//				double currentDist = this.calcDist(p.get(i), ysorted.get(j)); // This is most intensive operation
//				if (shortestDist > currentDist) {
//					closestPoints[0] = this.getPoints().get(i);
//					closestPoints[1] = ysorted.get(j);
//					shortestDist = currentDist;
//				}
//			}
//			this.ysorted.clear();
		}	
		
		ysort.clear();
	}
}
