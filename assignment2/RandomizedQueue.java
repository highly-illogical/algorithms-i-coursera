import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rq;
    private int size;
    private int capacity = 4;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rq = (Item[]) new Object[capacity];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        } else {
            rq[size++] = item;
            if (size >= capacity) {
                Item[] rqNew = (Item[]) new Object[capacity * 2];
                for (int i = 0; i < capacity; i++) {
                    rqNew[i] = rq[i];
                }
                rq = rqNew;
                capacity = capacity * 2;
            }
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            int i = StdRandom.uniform(0, size);
            Item item = rq[i];
            rq[i] = rq[size - 1];
            size--;

            if (size < capacity / 4) {
                Item[] rqNew = (Item[]) new Object[capacity / 4];
                for (int j = 0; j < size; j++) {
                    rqNew[j] = rq[j];
                }
                rq = rqNew;
                capacity = capacity / 4;
            }
            return item;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        else {
            int i = StdRandom.uniform(0, size);
            return rq[i];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] p = StdRandom.permutation(size);
        private int next = 0;

        public boolean hasNext() {
            return next < size;
        }

        public Item next() {
            if (next < size)
                return rq[p[next++]];
            else
                throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("abc");
        queue.enqueue("def");
        queue.enqueue("ghi");
        queue.enqueue("jkl");
        queue.enqueue("mn");
        queue.enqueue("op");
        queue.enqueue("qr");
        queue.enqueue("st");
        queue.enqueue("abc");
        queue.enqueue("def");
        queue.enqueue("ghi");
        queue.enqueue("jkl");
        queue.enqueue("mn");
        queue.enqueue("op");
        queue.enqueue("qr");
        queue.enqueue("st");
        Iterator<String> iter1 = queue.iterator();
        while (iter1.hasNext()) {
            System.out.printf("%s ", iter1.next());
        }
        System.out.print("\n");
        Iterator<String> iter2 = queue.iterator();
        while (iter2.hasNext()) {
            System.out.printf("%s ", iter2.next());
        }
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
    }

}
