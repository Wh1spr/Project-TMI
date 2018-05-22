package main.algorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import main.Main;

/**
 * Used for getting plotting data
 */
public class Data {

	public static void main(String[] args) {
		ArrayList<Double[]> points = makeRandom(2, 10240);
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter (new FileWriter("data1.csv")));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		AbstractAlg a = null;
		
		out.print("#Points,ExecSimple,ExecVar1\n");
		
		for (int i = 250; i <= 13000; i*=1.1) {
			List<Double[]> p = null;
			out.print(i + ",");
			
			a = new SimpleAlg(p, 2, null);
			
			long start = System.nanoTime();
			for(int execs = 0; execs < 20; execs++) {
				long cnstart = System.nanoTime();
				p = makeRandom(2, i);
				a.setPoints(p);
				long cnstop = System.nanoTime();
				start += cnstop-cnstart;
				a.execute();
			}
			long end = System.nanoTime();
			
			out.print((int)(((double) end-start)/15) + ",");
			
			a = new Variant1Alg(p, 2, null);
			
			start = System.nanoTime();
			for(int execs = 0; execs < 200; execs++) {
				long cnstart = System.nanoTime();
				p = makeRandom(2, i);
				a.setPoints(p);
				long cnstop = System.nanoTime();
				start += cnstop-cnstart;
				a.execute();
			}
			end = System.nanoTime();
			
			out.print((int)(((double) end-start)/200) + "\n");
			
		}
		
		out.flush();
		out.close();
		
	}
	
	private static ArrayList<Double[]> makeRandom(int dim, int size) {
		ArrayList<Double[]> list = new ArrayList<Double[]>();
		Random r = new Random();
		Iterator<Double> i = r.doubles(size * dim, 0.0, 5.0).iterator();
		Double[] point = new Double[dim];
		while(i.hasNext()) {
			point = new Double[dim];
			for (int j = 0; j<dim; j++) {
				point[j] = BigDecimal.valueOf(i.next()).setScale(16, RoundingMode.HALF_UP).doubleValue();
			}
			list.add(point);
		}
		list.sort(new Main.PointSortComp(dim));
		return list;
	}
}
