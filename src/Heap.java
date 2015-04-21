/**
 * Copyright (c) 2008-2010  Morten Silcowitz.
 *
 * This file is part of the Jinngine physics library
 *
 * Jinngine is published under the GPL license, available 
 * at http://www.gnu.org/copyleft/gpl.html. 
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Minimum heap implementation. See [Cormen et al 1999] for formal theory. 
 * Maintains all elements in a min-heap, such that the minimum element will
 * be the top-most node in the heap at all times. Among many other uses, heaps are ideal for 
 * representing priority queues. 
 * 
 * @param <T>
 */
public class Heap<T> {
	private int size;
	final private List<Node> heap;
	final private Comparator<T> comparator;
	
	private class Node {
		public T element;
		public int position;
	}    

	/**
	 * Constructor for heap.
	 * 
	 * @param comparator
	 */
	public Heap(Comparator<T> comparator) {
		size = 0;
		heap = new ArrayList<Node>();
		this.comparator = comparator;
	}

	/**
	 * Insert element into the heap.
	 *   
	 * @param element new element to be inserted
	 */
	public void insert(final T element) {
		size++;
		Node node = new Node();
		node.element = element;
		node.position = size-1;
		heap.add(node);
		decreaseKey(node);
	}

	public final void clear() {
		heap.clear();
		size = 0;
	}

	/**
	 * Return a reference to the top-most element on the heap.
	 * 
	 * @return Reference to top-most element of heap
	 */
	public final T top() {
		return heap.get(0).element;
	}

	/**
	 * Pop an element of the heap.
	 */
	public T pop() {
		T returnNode = top();
		exchange(0, size-1);
		heap.remove(size-1);
		size--;
		if (size>0) {
			minHeapify(heap.get(0));
		}
		    
		return returnNode;
	}

	public final int size() {
		return size;
	}

	private final boolean decreaseKey(final Node node) {
		int index = node.position;
		boolean modified = false;
		
		while (index>0 && comparator.compare(heap.get(parent(index)).element, heap.get(index).element) >= 0) {
			exchange( index, parent(index) );
			index = parent(index);
			modified = true;
		}
		
		return modified;
	}

	private final void minHeapify(final Node node) {
		int smallest;
		int index = node.position;
		int left = left(index);
		int right = right(index);
		
		if (left<size && comparator.compare(heap.get(left).element, heap.get(index).element) <= 0)
			smallest= left;
		else
			smallest = index;
		
		if (right<size && comparator.compare(heap.get(right).element, heap.get(smallest).element) <=0)
			smallest= right;
		if (smallest!= index) {
			exchange(index, smallest);
			minHeapify(heap.get(smallest));
		}
	}

	private final void exchange(final int index, final int index2) {
		Node temp = heap.get(index);
		temp.position = index2;
		
		Node temp2 = heap.get(index2);
		temp2.position = index;
		
		heap.set(index, temp2 );
		heap.set( index2, temp);
	}

	private final int parent(final int i) {
		return i/2;
	}
	
	private final int left(final int i) {
		return 2*i;
	}
	
	private final int right(final int i) {
		return 2*i+1;
	}

	/**
	 * Returns an iterator that iterates over all elements of the heap, in no particular order.
	 * 
	 * @return iterator
	 */
	public final Iterator<T> iterator() {
		return new Iterator<T>() {
			private Iterator<Node> iterator = heap.iterator(); 
			@Override
			public boolean hasNext() { return iterator.hasNext(); }
			@Override
			public T next() { return iterator.next().element; }
			@Override
			public void remove() { }
		};
	}
}