package main.algorithms;

import java.io.PrintWriter;
import java.util.List;

public class Variant2Alg extends AbstractAlg {

	public Variant2Alg(List<Double[]> points, int dim, PrintWriter outWriter) {
		super(points, dim, outWriter);
	}

	@Override
	public void execute() {
		this.shortestDist = Double.POSITIVE_INFINITY;
		this.closestPoints = new Double[2][this.getDim()];
		
		this.getOut().println("Dit algoritme is niet geïmplementeerd.");
		this.getOut().flush();
		this.getOut().close();
		System.exit(1);
		
	}

}
