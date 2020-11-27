package lab4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ClosestPair {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			points[i] = new Point(scan.nextInt(), scan.nextInt());
		}
		Point[] pX = Arrays.copyOf(points, n);
		Point[] pY = Arrays.copyOf(points, n);
		Arrays.sort(pX, new XCompare());
		Arrays.sort(pY, new YCompare());
		double delta = closest(pX, pY, n);
		System.out.println((new BigDecimal(delta)).setScale(6, BigDecimal.ROUND_HALF_EVEN));
		scan.close();
	}

	public static double closest(Point[] pX, Point[] pY, int n) {
		if (n == 2) {
			return pX[0].dist(pX[1]);
		} else if (n == 3) {
			return Math.min(pX[0].dist(pX[1]), Math.min(pX[0].dist(pX[2]), pX[2].dist(pX[1])));
		}

		double delta = 0;
		Point[] lX = Arrays.copyOfRange(pX, 0, n / 2);
		Point[] rX = Arrays.copyOfRange(pX, n / 2, n);
		Point[] lY = Arrays.copyOf(lX, lX.length);
		Arrays.sort(lY, new YCompare());
		Point[] rY = Arrays.copyOf(rX, rX.length);
		Arrays.sort(rY, new YCompare());
		double deltaL = closest(lX, lY, lX.length);
		double deltaR = closest(rX, rY, rX.length);
		delta = Math.min(deltaL, deltaR);
		List<Point> sY = new ArrayList<Point>();
		double mid;
		if (n % 2 == 1) {
			mid = pX[n / 2 + 1].getX();
		} else {
			mid = (pX[n / 2].getX() + pX[n / 2 + 1].getX()) / 2.0;
		}
		for (Point p : pY) {
			if (Math.abs(mid - p.getX()) < delta) {
				sY.add(p);
			}
		}
		int C;
		for (int i = 0; i < sY.size()-1; i++) {
			C=Math.min(sY.size()-i, 16);
				for (int j = i + 1; j < i + C; j++) {
					if (sY.get(i).dist(sY.get(j)) < delta) {
						delta = sY.get(i).dist(sY.get(j));
					}
				}
		}
		return delta;
	}

	public static class Point {
		private int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public double dist(Point p) {
			return Math.hypot(x - p.getX(), y - p.getY());
		}
	}

	public static class XCompare implements Comparator<Point> {

		@Override
		public int compare(Point p1, Point p2) {
			return p1.getX() - p2.getX();
		}
	}

	public static class YCompare implements Comparator<Point> {

		@Override
		public int compare(Point p1, Point p2) {
			return p1.getY() - p2.getY();
		}
	}

}
