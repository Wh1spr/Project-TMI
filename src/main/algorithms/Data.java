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
		
		data1();
		System.out.println("Data 1 done");
		data2();
		System.out.println("Data 2 done");
//		data3();
//		System.out.println("Data 3 done");
		
	}
	
	private static boolean writeRand = true;
	
	private static void data1() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter (new FileWriter("data1.csv")));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		AbstractAlg a = null;
		
		out.print("#Points,sortTimeNano,ExecSimpleNano,ExecVar1Nano,Kmax,Kavg\n");
		
		for (int i = 250; i <= 13000; i*=1.1) {
			List<Double[]> p = null;
			out.print(i + ",");
			
			a = new SimpleAlg(p, 2, null);
			
			writeRand = true;
			long start = System.nanoTime();
			for(int execs = 0; execs < 10; execs++) {
				long cnstart = System.nanoTime();
				p = makeRandom(2, i, out);
				a.setPoints(p);
				long cnstop = System.nanoTime();
				start += cnstop-cnstart;
				a.execute();
			}
			long end = System.nanoTime();
			
			out.print((int)(((double) end-start)/10) + ",");
			
			Variant1Alg b = new Variant1Alg(p, 2, null);
			
			double kmaxavg = 0;
			double kavgavg = 0;
			
			start = System.nanoTime();
			for(int execs = 0; execs < 30; execs++) {
				long cnstart = System.nanoTime();
				p = makeRandom(2, i, out);
				b.setPoints(p);
				long cnstop = System.nanoTime();
				start += cnstop-cnstart;
				b.execute();
				kmaxavg += b.getkmax();
				kavgavg += b.getkavg();
			}
			end = System.nanoTime();
			
			kmaxavg = kmaxavg / 30d;
			kavgavg = kavgavg / 30d;
			
			out.print((int)(((double) end-start)/30) + ",");
			out.print(String.format("%12f,%12f\n", kmaxavg, kavgavg));
			System.out.println("Current points: " + i + " - " + p.size());
			out.flush();
		}
		out.flush();
		out.close();
	}
	
	private static void data2() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter (new FileWriter("data6.csv")));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Variant1Alg a = null;
		SimpleAlg b = null;
		
		out.print("#Points,Dimension,sorttime,ExecBruteNano,ExecVar1Nano,Kmax,Kavg\n");
		
		List<Double[]> p = null;
		for (int dim = 2; dim < 45; dim++) {
			writeRand = false;
			p = makeRandom(dim, 300, out);
			a = new Variant1Alg(p, dim, null);
			b = new SimpleAlg(p,dim,null);
			
			out.print("200,"+dim + ",");
			
			double kmaxavg = 0;
			double kavgavg = 0;
			
			writeRand = true;
			long start = System.nanoTime();
			for(int execs = 0; execs < 5; execs++) {
				long cnstart = System.nanoTime();
				p = makeRandom(dim, 300, out);
				b.setPoints(p);
				b.setDim(dim);
				long cnstop = System.nanoTime();
				start += cnstop-cnstart;
				b.execute();
			}
			long end = System.nanoTime();
			
			out.print((int)(((double) end-start)/5) + ",");
			
			start = System.nanoTime();
			for(int execs = 0; execs < 10; execs++) {
				long cnstart = System.nanoTime();
				p = makeRandom(dim, 300, out);
				a.setPoints(p);
				a.setDim(dim);
				long cnstop = System.nanoTime();
				start += cnstop-cnstart;
				a.execute();
				kmaxavg += a.getkmax();
				kavgavg += a.getkavg();
			}
			end = System.nanoTime();
			
			kmaxavg = kmaxavg / 10d;
			kavgavg = kavgavg / 10d;
			
			out.print((int)(((double) end-start)/10) + ",");
			out.print(String.format("%12f,%12f\n", kmaxavg, kavgavg));
			
			System.out.println("current dim: " + dim);
		}
		
		out.flush();
		out.close();
	}
	
	private static void data3() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter (new FileWriter("data3.csv")));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Variant2Alg a = null;
		
		out.print("#Points,Dimension,sorttime,ExecVar2Nano,Kmax,Kavg\n");
		
		List<Double[]> p = null;
		for (int dim = 2; dim < 30; dim++) {
			writeRand = false;
			p = makeRandom(dim, 2500, out);
			a = new Variant2Alg(p, dim, null);
			
			out.print("2500,"+dim + ",");
			
			double kmaxavg = 0;
			double kavgavg = 0;
			
			writeRand = true;
			long start = System.nanoTime();
			for(int execs = 0; execs < 20; execs++) {
				long cnstart = System.nanoTime();
				p = makeRandom(dim, 2500, out);
				a.setPoints(p);
				a.setDim(dim);
				long cnstop = System.nanoTime();
				start += cnstop-cnstart;
				a.execute();
				kmaxavg += a.getkmax();
				kavgavg += a.getkavg();
			}
			long end = System.nanoTime();
			
			kmaxavg = kmaxavg / 20d;
			kavgavg = kavgavg / 20d;
			
			out.print((int)(((double) end-start)/20) + ",");
			out.print(String.format("%12f,%12f\n", kmaxavg, kavgavg));
			
			System.out.println("current dim: " + dim);
			out.flush();
		}
		
		out.flush();
		out.close();
	}
	
	private static ArrayList<Double[]> makeRandom(int dim, int size, PrintWriter out) {
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
		long start = System.nanoTime();
		list.sort(new Main.PointSortComp(dim));
		long end = System.nanoTime();
		if (writeRand) {
			out.print((end-start) + ",");
			writeRand = false;
		}
		
		return list;
	}
}
