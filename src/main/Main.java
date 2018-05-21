package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import main.algorithms.*;

public class Main {
	
	private static PrintWriter out = null; 
	private static List<String> in = null;
	private static List<Double[]> points = null;
	private static int dimension;
	
	private static AbstractAlg alg = null;
	
	public static void main(String[] args) {
		
		if (args[0].equals("RANDOM") && args.length < 6)
			System.err.println(String.join("\n"
				, "I'm missing some arguments!"
				, "The syntax for random input is:"
				, "    java -jar <jarname> RANDOM <in path> <out path> <1,2,3 for algorithm> <dimension of points> <amount of points>"
				, "Make sure the input and output paths include the names of the files. (both files will be created but need a name)"));
		else
			if (args.length < 2)
			System.err.println(String.join("\n"
					, "I'm missing some arguments!"
					, "The syntax is:"
					, "    java -jar <jarname> <in path> <out path>"
					, "Make sure the input and output paths include the names of the files. (Outfile will be created but needs a name)"));
		
		if (args[0].equals("RANDOM")) {
			try {
				makeRandom(new PrintWriter(new BufferedWriter (new FileWriter(args[1]))), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
			} catch (NumberFormatException | IOException e) {
				System.err.println("Error while trying to make random input.");
				e.printStackTrace();
				System.exit(1);
			}
			//move over input and output files, continue "normal" execution
			args[0] = args[1];
			args[1] = args[2]; 
		}
		
		
		if (args[1].equals("norun")) {
			System.out.println("norun option was called, not executing.");
			return;
		}
		
		
		// args[0] - in path
		// args[1] - out path
		// if not exists IN => crash
		
		try {
			out = new PrintWriter(new BufferedWriter (new FileWriter(args[1])));
			in = Files.readAllLines(Paths.get(args[0]));
		} catch (IOException e) {
			System.err.println("Error while trying to initialize input and output.");
			e.printStackTrace();
			System.exit(1);
		}
		
		dimension = Integer.parseInt(in.get(1));
		if (dimension < 2) System.err.println("Dimension was smaller than 2, could not start program.");
		
		//get le input
		try { // to identify problems if they should arise
		int numberOfPoints = Integer.parseInt(in.get(2));
		if (numberOfPoints < 2) {
			System.err.println("There need to be at least two points. Given value was: " + numberOfPoints);
			System.exit(1);
		}
		System.out.println("Reading in " + numberOfPoints + " points.");
		points = new ArrayList<Double[]>(numberOfPoints + 10); //+ 10 to have a chance to not have the array expand
		
		String[] b = null;
		Double[] p = null;
		for (int i = 3; i < numberOfPoints+3; i++) {
			b = in.get(i).split(" ");
			p = new Double[dimension];
			
			for (int j = 0; j < dimension; j++) {
				p[j] = Double.parseDouble(b[j]);
			}
			
			points.add(p);
		}
		points.sort(new PointSortComp(dimension));
		
		} catch (Exception e) {
			System.err.println("Error while trying to read input points.");
			e.printStackTrace();
			System.exit(1);
		}
		
		switch(in.get(0)) {
		case "1":
			//eenvoudig algoritme
			System.out.println("Executing with SimpleAlgorithm.");
			alg = new SimpleAlg(points, dimension, out);
			break;
		case "2":
			//variant 1
			System.out.println("Executing with Variant 1.");
			alg = new Variant1Alg(points, dimension, out);
			break;
		case "3":
			//variant 2
			System.out.println("Executing with Variant 2.");
			alg = new Variant2Alg(points, dimension, out);
			break;
		default:
			System.err.println(String.format("The selected algorithm \"%s\" does not exist. Please select '1' for Simple, '2' for Variant 1 or '3' for Variant 2.", in.get(0)));
			System.exit(1);
		}
		
		// Time and execute
		int execs = 0;
		System.out.println("Starting execution, I will run for 10 seconds and report my results.");
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < 10000) {
			execs += 1;
			alg.execute();
		}
		long end = System.currentTimeMillis();
		int millis = (int) (((double) end-start)/1.0);
		int millisAvg = (int) (((double) end-start)/execs);
		System.out.println("Stopped executing.");
		System.out.println(String.format("I ran for %d ms (%d ms average), and executed %d times.", millis, millisAvg, execs));
		
		String outString = "";
		for (Double[] point : alg.getClosest()) {
			outString = "";
			for (Double coord : point) {
				outString += String.format("%17.16f ", coord);
			}
			out.println(outString);
		}
		out.println(alg.getDist());
		
		out.println(millisAvg);
		out.flush();
		out.close();
		
		System.out.println("You can find the output @ " + args[1]);
	}
	
	private static void makeRandom(PrintWriter inFile, int alg, int dim, int size) {
		System.out.println("Making input file with alg " + alg + ", dimension " + dim + " and a size of " + size + " points.");
		inFile.println(alg);
		if (dim < 2) {
			System.err.println("Dimension needs to be greater or equal to 2.");
			System.exit(1);
		}
		inFile.println(dim);
		inFile.println(size);
		
		Random r = new Random();
		Iterator<Double> i = r.doubles(size * dim, 0.0, 5.0).iterator();
		
		String outString = "";
		while(i.hasNext()) {
			outString = "";
			for (int j = 0; j<dim; j++) {
				outString += String.format(Locale.US, "%17.16f ", i.next());
			}
			inFile.println(outString);
		}
		
		inFile.flush();
		inFile.close();
	}

	private static class PointSortComp implements Comparator<Double[]> {

		private int dim = 0;
		
		public PointSortComp(int dim) {
			this.dim = dim;
		}
		
		@Override
		public int compare(Double[] a, Double[] b) {
			for (int d = 0; d < dim; d++) {
				if (a[d] < b[d]) return -1;
				if (a[d] > b[d]) return 1;
				else continue;
			}
			return 0;
		}
		
	}
}
