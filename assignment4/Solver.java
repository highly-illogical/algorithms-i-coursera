import java.util.Iterator;
import java.util.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int m;
    private Stack<Board> s;

    private class Node implements Comparable {
        private Board b;
        private int moves;
        private int priority;
        private Node previous;

        private Node(Board board, int m, Node prev) {
            b = board;
            moves = m;
            priority = b.manhattan() + moves;
            previous = prev;
        }

        public int compareTo(Object o) {
            Node n = (Node) o;
            if (priority < n.priority)
                return -1;
            else if (priority == n.priority)
                return 0;
            else
                return 1;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        s = new Stack<>();

        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> pqTwin = new MinPQ<>();

        pq.insert(new Node(initial, 0, null));
        pqTwin.insert(new Node(initial.twin(), 0, null));

        Node min = null;
        Node minTwin = null;
        Node neighbor = null;
        Iterator<Board> it;
        while ((!pq.min().b.isGoal()) && (!pqTwin.min().b.isGoal())) {
            min = pq.delMin();

            it = min.b.neighbors().iterator();
            while (it.hasNext()) {
                neighbor = new Node(it.next(), min.moves + 1, min);
                if (min.previous == null)
                    pq.insert(neighbor);
                else {
                    if (!neighbor.b.equals(min.previous.b))
                        pq.insert(neighbor);
                }
            }

            min = pqTwin.delMin();

            it = min.b.neighbors().iterator();
            while (it.hasNext()) {
                neighbor = new Node(it.next(), min.moves + 1, min);
                if (min.previous == null)
                    pqTwin.insert(neighbor);
                else {
                    if (!neighbor.b.equals(min.previous.b))
                        pqTwin.insert(neighbor);
                }
            }
        }

        if (pq.min().b.isGoal()) {
            m = pq.min().moves;

            min = pq.min();
            while (min != null) {
                s.push(min.b);
                min = min.previous;
            }
        } else if (pqTwin.min().b.isGoal()) {
            m = -1;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return (m != -1);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return m;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (m == -1)
            return null;
        else {
            Stack<Board> stack = new Stack<>();
            while (!s.isEmpty())
                stack.push(s.pop());
            return stack;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}