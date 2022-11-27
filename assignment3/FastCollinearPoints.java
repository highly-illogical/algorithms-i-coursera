import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private int n;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        n = 0;
        lineSegments = new LineSegment[100];

        if (points == null)
            throw new IllegalArgumentException();

        Point[] pointsTemp = points.clone();

        for (int b = 0; b < pointsTemp.length; b++) {
            if (pointsTemp[b] == null)
                throw new IllegalArgumentException();
        }

        Arrays.sort(pointsTemp);

        for (int a = 1; a < pointsTemp.length; a++) {
            if (pointsTemp[a - 1].compareTo(pointsTemp[a]) == 0)
                throw new IllegalArgumentException();
        }

        if (pointsTemp.length > 3) {
            Point[] pointsBySlope = pointsTemp.clone();

            for (int i = 0; i < pointsTemp.length; i++) {
                Point p = pointsTemp[i];
                Comparator<Point> c = p.slopeOrder();
                Arrays.sort(pointsBySlope, c);

                int num_collinear = 2;
                int j = 1;
                Point end1 = p;
                Point end2 = pointsBySlope[1];

                if (end1.compareTo(end2) > 0) {
                    end1 = pointsBySlope[1];
                    end2 = p;
                }

                while (j < pointsBySlope.length) {
                    if (c.compare(pointsBySlope[j - 1], pointsBySlope[j]) == 0) {
                        if (end1.compareTo(pointsBySlope[j]) > 0)
                            end1 = pointsBySlope[j];
                        if (end2.compareTo(pointsBySlope[j]) < 0)
                            end2 = pointsBySlope[j];
                        num_collinear++;
                    } else {
                        if (num_collinear >= 4) {
                            boolean found = false;
                            LineSegment s = new LineSegment(end1, end2);
                            for (int a = 0; a < n; a++) {
                                if (s.toString().equals(lineSegments[a].toString()))
                                    found = true;
                            }
                            if (!found)
                                lineSegments[n++] = s;
                        }
                        end1 = p;
                        end2 = pointsBySlope[j];
                        if (end1.compareTo(end2) > 0) {
                            end1 = pointsBySlope[j];
                            end2 = p;
                        }
                        num_collinear = 2;
                    }
                    j++;
                }
                if (num_collinear >= 4) {
                    boolean found = false;
                    LineSegment s = new LineSegment(end1, end2);
                    for (int a = 0; a < n; a++) {
                        if (s.toString().equals(lineSegments[a].toString()))
                            found = true;
                    }
                    if (!found)
                        lineSegments[n++] = s;
                }
            }
        }
    }

    public int numberOfSegments() {
        return n;
    }

    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[n];

        for (int i = 0; i < n; i++) {
            lines[i] = lineSegments[i];
        }

        return lines;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}