import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private class Node {
        private Node parent;
        private Node left;
        private Node right;
        private Point2D key;
        private RectHV r;

        private Node(Point2D p, RectHV rect) {
            key = p;
            left = null;
            right = null;
            parent = null;
            r = rect;
        }

        private void draw(boolean levelX) {
            if (left != null) {
                left.draw(!levelX);
            }
            StdDraw.setPenRadius(0.002);
            if (levelX) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(key.x(), r.ymin(), key.x(), r.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(r.xmin(), key.y(), r.xmax(), key.y());
            }
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(key.x(), key.y());
            if (right != null) {
                right.draw(!levelX);
            }
        }

        private boolean find(Point2D p, boolean levelX) {
            if (key.equals(p))
                return true;
            if (levelX) {
                if (key.x() < p.x()) {
                    if (left != null)
                        return left.find(p, !levelX);
                } else {
                    if (right != null)
                        return right.find(p, !levelX);
                }
            } else {
                if (key.y() < p.y()) {
                    if (left != null)
                        return left.find(p, !levelX);
                } else {
                    if (right != null)
                        return right.find(p, !levelX);
                }
            }
            return false;
        }

        private int insert(Point2D p, double x, double y, boolean levelX, int s) {
            if (!key.equals(p)) {
                if (levelX) {
                    if (key.x() < x) {
                        if (left == null) {
                            left = new Node(p, new RectHV(key.x(), r.ymin(), r.xmax(), r.ymax()));
                            s++;
                        } else
                            s = left.insert(p, x, y, !levelX, s);
                    } else {
                        if (right == null) {
                            right = new Node(p, new RectHV(r.xmin(), r.ymin(), key.x(), r.ymax()));
                            s++;
                        } else
                            s = right.insert(p, x, y, !levelX, s);
                    }

                } else {
                    if (key.y() < y) {
                        if (left == null) {
                            left = new Node(p, new RectHV(r.xmin(), key.y(), r.xmax(), r.ymax()));
                            s++;
                        } else
                            s = left.insert(p, x, y, !levelX, s);
                    } else {
                        if (right == null) {
                            right = new Node(p, new RectHV(r.xmin(), r.ymin(), r.xmax(), key.y()));
                            s++;
                        } else
                            s = right.insert(p, x, y, !levelX, s);
                    }
                }
            }
            return s;
        }

        private void range(RectHV rect, Stack<Point2D> s) {
            if (rect.contains(key))
                s.push(key);
            if (left != null) {
                if (left.r.intersects(rect)) {
                    left.range(rect, s);
                }
            }
            if (right != null) {
                if (right.r.intersects(rect)) {
                    right.range(rect, s);
                }
            }
        }

        private Point2D nearest(Point2D p, Point2D n) {
            if (n == null)
                n = key;
            double dist = n.distanceSquaredTo(p);
            if (key.distanceSquaredTo(p) < dist)
                n = key;
            if ((right == null) && (left != null)) {
                if (left.r.distanceSquaredTo(p) <= dist)
                    n = left.nearest(p, n);
            } else if ((left == null) && (right != null)) {
                if (right.r.distanceSquaredTo(p) <= dist)
                    n = right.nearest(p, n);
            } else if ((left != null) && (right != null)) {
                double leftDist = left.r.distanceSquaredTo(p);
                double rightDist = right.r.distanceSquaredTo(p);
                if (leftDist < rightDist) {
                    if (leftDist < dist)
                        n = left.nearest(p, n);
                    if (rightDist < dist)
                        n = right.nearest(p, n);
                } else {
                    if (rightDist < dist)
                        n = right.nearest(p, n);
                    if (leftDist < dist)
                        n = left.nearest(p, n);
                }
            }
            return n;
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        /*
         * Node n = new Node(p); Node pos = root; Node next = root; boolean levelX =
         * true; boolean left = true; if (next == null) root = n; else { while (next !=
         * null) { pos = next; left = (levelX ? (next.key.x() > n.key.x()) :
         * (next.key.y() > n.key.y())); if (left) next = next.left; else next =
         * next.right; levelX = (!levelX); } if (left) pos.left = n; else pos.right = n;
         * n.parent = pos; }
         */
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null) {
            root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0));
            size = 1;
        } else {
            double x = p.x();
            double y = p.y();
            size = root.insert(p, x, y, true, size);
        }
    }

    public boolean contains(Point2D p) {
        /*
         * Node pos = root; Node next = root; boolean levelX = true; boolean left =
         * true; boolean found = false;
         * 
         * while ((next != null) && (!found)) { if ((next.key.x() == p.x()) &&
         * (next.key.y() == p.y())) found = true; pos = next; left = (levelX ?
         * (next.key.x() > p.x()) : (next.key.y() > p.y())); if (left) next = next.left;
         * else next = next.right; levelX = (!levelX); } return found;
         */
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null)
            return false;
        return root.find(p, true);
    }

    public void draw() {
        root.draw(true);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Stack<Point2D> stack = new Stack<>();
        if (root != null)
            root.range(rect, stack);
        return stack;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        Point2D n = null;
        if (root == null)
            return null;
        return root.nearest(p, n);
    }

    public static void main(String[] args) {
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.7, 0.2));
        kd.insert(new Point2D(0.9, 0.6));
        kd.insert(new Point2D(0.9, 0.5));
        kd.insert(new Point2D(0.4, 0.6));
        kd.insert(new Point2D(0.5, 0.4));
        kd.insert(new Point2D(0.2, 0.3));
        kd.insert(new Point2D(0.4, 0.7));
        kd.insert(new Point2D(0.9, 0.6));
        System.out.println(kd.contains(new Point2D(0.1, 0.3)));
        System.out.println(kd.size());
        kd.draw();
    }
}
