import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        String s;

        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            q.enqueue(s);
        }

        Iterator<String> it = q.iterator();

        for (int i = 0; i < k; i++) {
            System.out.println(it.next());
        }
    }
}