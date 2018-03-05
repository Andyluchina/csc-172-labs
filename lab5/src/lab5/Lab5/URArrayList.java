// Written by AJ Jordan, no partner

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;

import URList;



public class URArrayList<E> implements URList<E>, Iterable<E> {
	// XXX this doesn't handle the list being mutated in the middle of iteration
	private class URArrayListIterator<E> implements Iterator<E> {
		int idx = 0;
		URArrayList<E> l;

		public URArrayListIterator(URArrayList<E> list) {
			this.l = list;
		}

		public boolean hasNext() {
			if (idx+1 < l.size()) return true;
			return false;
		}

		@Override
		public E next() {
			if (this.hasNext()) {
				idx++;
				return l.get(idx-1);
			}
			throw new NoSuchElementException();
		}
	}

	@SuppressWarnings("unchecked")
	private E[] arr = (E[]) new Object[1];
	private int size = 0;

	@Override
	// Assumption: this is always supposed to return true
	public boolean add(E e) {
		this.ensureCapacity(this.size + 1);
		// Because arrays start from 0, not 1
		arr[this.size] = e;
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		// XXX this is inefficient
		int endLength = size-(size-index);
		this.ensureCapacity(size+1);
		Object[] earr = Arrays.copyOfRange(arr, index, arr.length-1);
		arr[index] = element;
		System.arraycopy(earr, 0, arr, index+1, earr.length);
		size++;
	}

	@Override
	// Assumption: this is supposed to always return true
	public boolean addAll(Collection<? extends E> c) {
		for (E o: c) {
			this.add(o);
		}
		return true;
	}

	@Override
	// Assumption: this is supposed to always return true
	public boolean addAll(int index, Collection<? extends E> c) {
		int i = index;
		for (E o: c) {
			this.add(i, o);
			i++;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		arr = (E[]) new Object[1];
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		for (Object i: arr) {
			if (i.equals(o)) return true;
		}
		return false;
	}

	@Override
	// TODO test this
	// XXX optimize this
	public boolean containsAll(Collection<?> c) {
		for (Object i: c) {
			if (!this.contains(i)) return false;
		}
		return true;
	}

	@Override
	// TODO
	public boolean equals(Object o) {
		if (!(o instanceof URList)) return false;

		@SuppressWarnings("unchecked")
		URList<E> l = (URList<E>) o;

		if (l.size() != this.size()) return false;

		Iterator<E> i = l.iterator();
		for (E el: this) {
			if (!i.hasNext()) return false;
			if (!i.next().equals(el)) return false;
		}

		return true;
	}

	@Override
	public E get(int index) {
		return arr[index];
	}

	@Override
	public int indexOf(Object o) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(o)) return i;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new URArrayListIterator<E>(this);
	}

	@Override
	// XXX this is inefficient
	// XXX set the end to null so it gets garbage-collected
	public E remove(int index) {
		E old = arr[index];
		System.arraycopy(arr, index+1, arr, index, size-1-index);
		size--;
		return old;
	}

	@Override
	// Assumption: this returns true if the object was present
	// Assumption: only the first instance is removed
	public boolean remove(Object o) {
		for (int i = 0; i < size; i++) {
			if (arr[i].equals(o)) {
				remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	// Assumption: this returns true if *any* of the passed objects were removed
	// Meaning that if some were not present, the method may still return true
	// XXX this is inefficient
	public boolean removeAll(Collection<?> c) {
		boolean r = false;
		for (Object o: c) {
			if (this.remove(o)) r = true;
		}
		return r;
	}

	@Override
	// Assumption: you're supposed to return the old element
	public E set(int index, E element) {
		E old = arr[index];
		arr[index] = element;
		return old;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public URArrayList<E> subList(int fromIndex, int toIndex) {
		if (toIndex < fromIndex) throw new IndexOutOfBoundsException();
		URArrayList<E> nlist = new URArrayList<E>();

		if (fromIndex == toIndex) return nlist;

		nlist.ensureCapacity(toIndex - fromIndex);
		for (int i = fromIndex; i < toIndex; i++) {
			nlist.add(arr[i]);
		}

		return nlist;
	}

	@Override
	public E[] toArray() {
		// https://stackoverflow.com/a/4439612/1198896
		return Arrays.copyOfRange(arr, 0, this.size);
	}

	@SuppressWarnings("unchecked")
	public void ensureCapacity(int minCapacity) {
		if (arr.length > minCapacity) return;

		Object[] narr = new Object[minCapacity];
		System.arraycopy(arr, 0, narr, 0, arr.length);

		arr = (E[]) narr;
	}

	public int getCapacity() {
		return arr.length;
	}
}
