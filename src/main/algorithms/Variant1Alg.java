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
		
		this.getOut().println("Dit algoritme is niet geïmplementeerd.");
		this.getOut().flush();
		this.getOut().close();
		System.exit(1);
		
	}

}
