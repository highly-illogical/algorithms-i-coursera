import java.util.Arrays;

public class BruteCollinearPoints {
    private int n;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        n = 0;
        lineSegments = new LineSegment[100];

        if (points == null)
            throw new IllegalArgumentException();
        else {
            Point[] pointsTemp = points.clone();

            for (int a = 0; a < pointsTemp.length; a++) {
                if (pointsTemp[a] == null)
                    throw new IllegalArgumentException();
            }

            Arrays.sort(pointsTemp);

            for (int a = 1; a < pointsTemp.length; a++) {
                if (pointsTemp[a - 1].compareTo(pointsTemp[a]) == 0)
                    throw new IllegalArgumentException();
            }

            for (int i = 0; i < pointsTemp.length; i++) {
                for (int j = i + 1; j < pointsTemp.length; j++) {
                    for (int k = j + 1; k < pointsTemp.length; k++) {
                        for (int l = k + 1; l < pointsTemp.length; l++) {
                            double slope = pointsTemp[i].slopeTo(pointsTemp[j]);
                            if ((slope == pointsTemp[j].slopeTo(pointsTemp[k]))
                                    && (slope == pointsTemp[k].slopeTo(pointsTemp[l]))) {
                                lineSegments[n++] = new LineSegment(pointsTemp[i], pointsTemp[l]);
                            }
                        }
                    }
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
}