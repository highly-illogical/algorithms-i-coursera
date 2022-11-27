import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node head;
    private Node tail;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        else {
            Node node = new Node();

            node.item = item;
            node.previous = null;
            node.next = head;

            if (head != null) {
                head.previous = node;
            } else {
                tail = node;
            }
            head = node;

            size++;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        else {
            Node node = new Node();

            node.item = item;
            node.next = null;
            node.previous = tail;

            if (tail != null) {
                tail.next = node;
            } else {
                head = node;
            }
            tail = node;

            size++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException();
        else {
            Node node = new Node();

            node = head;
            head = head.next;
            if (head != null)
                head.previous = null;

            size--;

            if (size == 0)
                tail = null;

            return node.item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException();
        else {
            Node node = new Node();

            node = tail;
            tail = tail.previous;
            if (tail != null)
                tail.next = null;

            size--;

            if (size == 0)
                head = null;

            return node.item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException();
            else {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<String> d = new Deque<>();
        d.addFirst("a");
        d.isEmpty();
        d.addFirst("b");
        d.addLast("cd");
        d.addLast("fg");

        Iterator<String> it = d.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());
        System.out.println(d.size());
        System.out.println(d.isEmpty());

        Deque<Integer> deque = new Deque<Integer>();
        System.out.println(deque.isEmpty());// ==> true
        deque.addFirst(2);
        deque.addFirst(3);
        System.out.println(deque.isEmpty());// ==> false
        System.out.println(deque.removeLast());// ==> 2
        deque.addFirst(6);
        System.out.println(deque.removeLast());// ==> 3
        deque.addFirst(8);
        System.out.println(deque.removeLast());// ==> 6
        deque.removeLast();
        Iterator<Integer> iter = deque.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next());
        }
    }

}