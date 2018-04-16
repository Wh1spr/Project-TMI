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
	
	public abstract void execute();
}
