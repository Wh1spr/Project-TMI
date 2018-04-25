package main.algorithms;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Variant2Alg extends AbstractAlg {

	public Variant2Alg(List<Double[]> points, int dim, PrintWriter outWriter) {
		super(points, dim, outWriter);
	}

	/**
	 * this arraylist keeps the items sorted on y-value
	 */
	private List<Double[]> status = new ArrayList<Double[]>() {
		private static final long serialVersionUID = 1L;

		@Override
	    public boolean add(Double[] mt) {
	         super.add(mt);
	         Collections.sort(this, new Comparator<Double[]>() {
				@Override
				public int compare(Double[] o1, Double[] o2) {
					return o1[1]<o2[1]?-1:1;
				}
	         });
	         return true;
	    }
	}; 
	
	//remove and add operation are the remove and add operations on status.
	@Override
	public void execute() {
		status.clear();
		if (this.getDim() != 2) {
			this.getOut().println("Dit algoritme is niet geïmplementeerd voor dimensies niet gelijk aan 2. Gegeven dimensie: " + this.getDim());
			this.getOut().flush();
			this.getOut().close();
			System.exit(1);
		}
		
		this.shortestDist = Double.POSITIVE_INFINITY;
		this.closestPoints = new Double[2][this.getDim()];
		
		//algoritme	
	}
	
	private Double[] above(Double[] a) {
		status.add(a);
		Double[] result = null;
		try {
			result = status.get(status.indexOf(a)+1);
		} catch (Exception e) {
			status.remove(a);
			return null;
		}
		status.remove(a);
		return result;
	}

	private Double[] under(Double[] a) {
		status.add(a);
		Double[] result = null;
		try {
			result = status.get(status.indexOf(a)-1);
		} catch (Exception e) {
			status.remove(a);
			return null;
		}
		status.remove(a);
		return result;
	}
}
