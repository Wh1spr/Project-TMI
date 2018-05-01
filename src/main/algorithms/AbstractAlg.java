package main.algorithms;

import java.io.PrintWriter;
import java.util.List;

public abstract class AbstractAlg {
	
	private List<Double[]> points = null;
	private int dim = 0;
	private PrintWriter out = null;
	
	protected Double[][] closestPoints = null;
	protected Double shortestDist = null;
	
	public AbstractAlg(List<Double[]> points, int dim, PrintWriter outWriter) {
		this.points = points;
		this.dim = dim;
		this.out = outWriter;
	}
	
	public List<Double[]> getPoints() {
		return this.points;
	}
	public int getDim() {
		return this.dim;
	}
	public PrintWriter getOut() {
		return this.out;
	}
	
	public Double[][] getClosest() {
		return closestPoints;
	}
	public Double getDist() {
		return Math.sqrt(shortestDist);
	}
	
	/**
	 * Calculates the distance between two points squared.
	 * @param a The first point
	 * @param b The second point
	 * @param d The amount of dimensions in a and b
	 * @return distance between the two points squared.
	 */
	protected Double calcDist(Double[] a, Double[] b) {
		double currentDist = 0;
		for(int dim = 0; dim < this.getDim(); dim++) {
			currentDist += Math.pow((a)[dim] - b[dim], 2);
		}
		return currentDist;
	}
	
	public abstract void execute();
}
