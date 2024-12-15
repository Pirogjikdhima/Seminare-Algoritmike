package Seminare.Sem4;

class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;      // Array to store elements
    private int N;         // Number of elements in the priority queue
    private int minIndex;  // Pointer to the minimum key

    // Constructor
    public MaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity + 1];
        N = 0;
        minIndex = -1;
    }

    // Insert an element
    public void insert(Key v) {
        // Resize if necessary
        if (N >= pq.length - 1) {
            pq = resize(pq, 2 * pq.length);
        }

        // Add the new key and swim up
        pq[++N] = v;
        swim(N);

        // Mark the minimum
        if (minIndex == -1 || v.compareTo(pq[minIndex]) < 0) {
            minIndex = N;
        }
    }

    // Remove and return the maximum element
    public Key delMax() {
        if (N == 0) {
            throw new RuntimeException("Priority Queue Underflow");
        }

        // Save the max value, exchange with the last, and sink down
        Key max = pq[1];
        exch(1, N--);
        pq[N + 1] = null; // Avoid loitering
        sink(1);

        // Check if resizing is necessary
        if (N > 0 && N == (pq.length - 1) / 4) {
            pq = resize(pq, pq.length / 2);
        }

        // Recompute minIndex
        if (N == 0) {
            minIndex = -1;
        }

        return max;
    }

    // Get the minimum element
    public Key min() {
        if (minIndex != -1) {
            return pq[minIndex];
        }
        return null;
    }

    // Swim operation to restore heap order
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    // Sink operation to restore heap order
    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    // Resize the array
    private Key[] resize(Key[] array, int capacity) {
        Key[] temp = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= N; i++) {
            temp[i] = array[i];
        }
        return temp;
    }

    // Comparison helper - checks if key at index i is less than the key at index j
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    // Exchange elements at indices i and j
    private void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private boolean isMaxHeap() {
        for (int i = 1; i <= N; i++) {
            if (pq[i] == null) return false;
        }
        for (int i = N + 1; i < pq.length; i++) {
            if (pq[i] != null) return false;
        }
        if (pq[0] != null) return false;
        return isMaxHeapOrdered(1);
    }

    // Check if the tree rooted at k is a max-heap
    private boolean isMaxHeapOrdered(int k) {
        if (k > N) {
            return true;
        }

        int left = 2 * k;       // Left child index
        int right = 2 * k + 1;  // Right child index

        // Check the left child
        if (left <= N && less(k, left)) {
            return false;
        }
        // Check the right child
        if (right <= N && less(k, right)) {
            return false;
        }
        // Recursively check for left and right subtrees
        return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
    }

}