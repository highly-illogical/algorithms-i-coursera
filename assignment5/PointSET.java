import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.Iterator;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return set.contains(p);
    }

    public void draw() {
        Iterator<Point2D> it = set.iterator();
        while (it.hasNext())
            it.next().draw();

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Stack<Point2D> stack = new Stack<>();
        Iterator<Point2D> it = set.iterator();
        Point2D current = null;

        while (it.hasNext()) {
            current = it.next();
            if (rect.contains(current))
                stack.push(current);
        }
        return stack;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        Point2D nearest = null;
        Point2D current = null;
        double d = Double.POSITIVE_INFINITY;
        double dNew = Double.POSITIVE_INFINITY;
        Iterator<Point2D> it = set.iterator();

        while (it.hasNext()) {
            current = it.next();
            dNew = p.distanceSquaredTo(current);
            if (dNew < d) {
                d = dNew;
                nearest = current;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {

    }
}