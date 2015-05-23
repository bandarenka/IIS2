import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JFrame;

public class Main {

	static int I = 3;
	static int n = 5;
	static Vector<Vector<Integer[]>> xTeach = new Vector<Vector<Integer[]>>(I);

	Vector<Integer[]> xRecognize = new Vector<Integer[]>();
	static double[][] b = new double[I][n];
	static double[][] a = new double[I][n];
	static double[] bt = new double[n];
	static double[] muV = new double[I];
	static ArrayList<String> classNames = new ArrayList<String>();

	public Main(int I, int n){
		this.I = I;
		this.n = n;
		
		xTeach = new Vector<Vector<Integer[]>>(I);

		xRecognize = new Vector<Integer[]>();
		b = new double[I][n];
		a = new double[I][n];
		bt = new double[n];
		muV = new double[I];
	}
	public static void readData() {

		try {
			BufferedReader in = new BufferedReader(new FileReader("input.txt"));
			String s;
			while ((s = in.readLine()) != null) {
				classNames.add(s);
				s = in.readLine();
				Vector<Integer[]> Xi = new Vector<Integer[]>();
				while (s != null && !"".equals(s)) {
					Xi.addElement(parseBinaryString(s));
					s = in.readLine();

				}
				xTeach.addElement(Xi);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Integer[] parseBinaryString(String str) {
		Integer[] x = new Integer[n];
		StringTokenizer tok = new StringTokenizer(str);
		for (int i = 0; i < n; ++i) {
			x[i] = Integer.parseInt(tok.nextToken());
		}
		return x;
	}

	public static void learn() {
		for (int i = 0; i < I; ++i) {
			for (int t = 0; t < n; ++t) {
				Vector<Integer[]> Xi = xTeach.elementAt(i);
				int mi = Xi.size();
				double sum = 0;
				for (Integer[] x : Xi) {
					sum += (double) x[t];
				}
				b[i][t] = sum / mi;
			}
		}

		for (int t = 0; t < n; ++t) {
			double sum = 0;
			for (int i = 0; i < I; ++i) {
				sum += b[i][t];
			}
			bt[t] = sum / I;
			for (int i = 0; i < I; ++i) {
				a[i][t] = Math.abs(b[i][t] - bt[t]);
			}
		}
	}

	public static void recognize(Integer[] x) {

		for (int i = 0; i < I; ++i) {
			Vector<Integer[]> Xi = xTeach.elementAt(i);
			double maxMu = 0.0;
			for (Integer[] xToCompare : Xi) {
				double mu = getMu(x, xToCompare, i);
				if (mu > maxMu)
					maxMu = mu;
			}
			muV[i] = maxMu;
		}
	}

	private static double getMu(Integer[] x, Integer[] xToCompare, int i) {
		double sumUp = 0;
		double sumDown = 0;
		for (int j = 1; j < n; ++j) {
			/*sumUp += getSign(x, xToCompare, i) * a[j][i];
			sumDown += a[j][i];*/
			sumUp += getSign(x, xToCompare, i) * a[i][j];
			sumDown += a[i][j];
		}

		return Math.max(0.0, sumUp / sumDown);
	}

	private static int getSign(Integer[] x, Integer[] xToCompare, int j) {
		if (x[j] != xToCompare[j])
			return -1;
		else
			return 1;
	}

	public static void printArray(double[] muV2) {
		for (int i = 0; i < muV2.length; ++i)
			System.out.println(muV2[i] + " ");
		System.out.println();
	}
	
	public static String getClassOfElement(){
		double max = 0.0;
		int k = 0;
		for(int i =0; i<I;++i){
			if(muV[i]>max){
				max = muV[i];
				k = i;
			}
		}
		return classNames.get(k);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*Integer[] x = { 1,1, 0, 0, 1 };

		readData();
		learn();
		recognize(x);
		printArray(muV);*/
		
		RecognizerFrame frame = new RecognizerFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
