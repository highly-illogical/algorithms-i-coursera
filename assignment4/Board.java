import java.util.Iterator;

public class Board {
    private int[][] board;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        board = tiles;
        n = board.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder(100);

        s.append(String.format("%d", n));
        for (int i = 0; i < n; i++) {
            s.append('\n');
            for (int j = 0; j < n; j++) {
                s.append(String.format(" %d ", board[i][j]));
            }
        }
        s.append('\n');
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int h = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] != (i * n + j + 1) % (n * n))
                        h++;
                }
            }
        }
        return h;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int m = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) {
                    m = m + Math.abs(i - (board[i][j] - 1) / n);
                    m = m + Math.abs(j - (board[i][j] - 1) % n);
                }
            }
        }
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (!(y instanceof Board)) {
            return false;
        }

        Board b = (Board) y;

        if (b.dimension() != dimension())
            return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != b.board[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                int x_blank = -1;
                int y_blank = -1;
                int squares = 2;

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (board[i][j] == 0) {
                            if (i % (n - 1) != 0)
                                squares++;
                            if (j % (n - 1) != 0)
                                squares++;
                            x_blank = i;
                            y_blank = j;
                        }
                    }
                }
                final int sq = squares;
                final int xb = x_blank;
                final int yb = y_blank;

                return new Iterator<Board>() {
                    private int s = sq;
                    private boolean[] tried = { false, false, false, false };
                    private int xB = xb;
                    private int yB = yb;

                    public boolean hasNext() {
                        return (s > 0);
                    }

                    public Board next() {
                        int[][] t = new int[n][n];

                        for (int i = 0; i < n; i++) {
                            for (int j = 0; j < n; j++) {
                                t[i][j] = board[i][j];
                            }
                        }
                        if ((xB - 1 >= 0) && (!tried[0])) {
                            int temp = t[xB - 1][yB];
                            t[xB - 1][yB] = 0;
                            t[xB][yB] = temp;
                            tried[0] = true;
                        } else if ((yB - 1 >= 0) && (!tried[1])) {
                            int temp = t[xB][yB - 1];
                            t[xB][yB - 1] = 0;
                            t[xB][yB] = temp;
                            tried[1] = true;
                        } else if ((xB + 1 < n) && (!tried[2])) {
                            int temp = t[xB + 1][yB];
                            t[xB + 1][yB] = 0;
                            t[xB][yB] = temp;
                            tried[2] = true;
                        } else if ((yB + 1 >= 0) && (!tried[3])) {
                            int temp = t[xB][yB + 1];
                            t[xB][yB + 1] = 0;
                            t[xB][yB] = temp;
                            tried[3] = true;
                        }
                        s--;
                        return new Board(t);
                    }
                };
            }

        };
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newBoard = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = board[i][j];
            }
        }

        if (newBoard[0][0] != 0) {
            int temp = newBoard[0][0];
            newBoard[0][0] = newBoard[0][1];
            newBoard[0][1] = temp;
        } else {
            int temp = newBoard[1][0];
            newBoard[1][0] = newBoard[1][1];
            newBoard[1][1] = temp;
        }
        return new Board(newBoard);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = { { 0, 8, 3 }, { 4, 1, 2 }, { 7, 6, 5 } };
        int[][] tiles2 = { { 8, 0, 3 }, { 4, 1, 2 }, { 7, 6, 5 } };
        Board b = new Board(tiles);
        Board b2 = new Board(tiles2);
        Iterator<Board> it = b.neighbors().iterator();

        System.out.println(b.toString());
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
        System.out.println(b.equals(b2));
        System.out.println(b.twin().toString());
        while (it.hasNext())
            System.out.println(it.next().toString());
    }

}