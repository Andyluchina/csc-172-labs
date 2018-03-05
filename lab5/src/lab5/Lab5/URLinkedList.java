// Written by AJ Jordan, no partner

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import URArrayList;
import URNode;

public class URLinkedList<E> implements URList<E>, Iterable<E> {
	// XXX this doesn't handle the list being mutated in the middle of iteration
	private class URLinkedListIterator<E> implements Iterator<E> {
		boolean firstNext = true;
		URNode<E> node;

		public URLinkedListIterator(URNode<E> head) {
			this.node = head;
		}

		public boolean hasNext() {
			if (firstNext) {
				return node != null;
			}
			return node.next() != null;
		}

		@Override
		public E next() {
			if (firstNext) {
				firstNext = false;
				return node.element();
			}

			if (this.hasNext()) {
				node = node.next();
				return node.element();
			}

			throw new NoSuchElementException();
		}
	}

	URNode<E> head = null;
	URNode<E> tail = null;
	int size = 0;

	@Override
	// Assumption: this should always return true
	public boolean add(E e) {
		if (head == null) {
			head = new URNode<E>(e, null, null);
			tail = head;
		} else {
			// There's an existing list
			URNode<E> node = new URNode<E>(e, tail, null);
			tail.setNext(node);
			tail = node;
		}

		size++;

		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index > size || index < 0) throw new IndexOutOfBoundsException();

		// Right after the list
		if (index == size) {
			this.add(element);
			return;
		}

		URNode<E> node;

		// Beginning of the list
		if (index == 0) {
			node = new URNode<E>(element, null, head);
			head.setPrev(node);
			head = node;
			size++;
			return;
		}

		// Middle of the list
		URNode<E> before = head;
		for (int i = 0; i < index-1; i++) {
			before = before.next();
		}
		URNode<E> after = before.next();

		node = new URNode<E>(element, before, after);
		before.setNext(node);
		// Handles the end of the list
		if (after != null) after.setPrev(node);

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
	// XXX optimize this so it doesn't iterate through so many times
	public boolean addAll(int index, Collection<? extends E> c) {
		int i = index;
		for (E o: c) {
			this.add(i, o);
			i++;
		}
		return true;
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		if (this.indexOf(o) != -1) return true;
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
	// TODO test this
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

	private URNode<E> findIndex(int index) {
		if (index >= size) throw new IndexOutOfBoundsException();

		URNode<E> node = head;
		for (int i = 0; i < index; i++) {
			node = node.next();
		}
		return node;
	}

	@Override
	// XXX optimize this by going from the tail sometimes
	public E get(int index) {
		return findIndex(index).element();
	}

	@Override
	public int indexOf(Object o) {
		URNode<E> node = head;
		for (int i = 0; i < size; i++) {
			if (node.element().equals(o)) return i;
			node = node.next();
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new URLinkedListIterator<E>(head);
	}

	@Override
	public E remove(int index) {
		if (index >= size) throw new IndexOutOfBoundsException();

		URNode<E> oldNode = findIndex(index);
		E old = oldNode.element();

		if (index == 0) {
			head = head.next();
			head.setPrev(null);
		} else if (index == size-1) {
			tail = tail.prev();
			tail.setNext(null);
		} else {
			URNode<E> after = oldNode.next();
			URNode<E> before = oldNode.prev();
			after.setPrev(before);
			before.setNext(after);
		}

		return old;
	}

	@Override
	// Assumption: this returns true if the object was present
	// Assumption: only the first instance is removed
	// XXX optimize this - it's *horribly* inefficient
	public boolean remove(Object o) {
		for (int i = 0; i < size; i++) {
			if (this.get(i).equals(o)) {
				remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	// Assumption: this returns true if *any* of the passed objects were removed
	// Meaning that if some were not present, the method may still return true
	public boolean removeAll(Collection<?> c) {
		boolean r = false;
		for (Object o: c) {
			if (this.remove(o)) r = true;
		}
		return r;
	}

	@Override
	public E set(int index, E element) {
		URNode<E> node = findIndex(index);
		E old = node.element();
		node.setElement(element);
		return old;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	// XXX optimize this
	public URList<E> subList(int fromIndex, int toIndex) {
		if (toIndex < fromIndex || fromIndex > size) throw new IndexOutOfBoundsException();
		URLinkedList<E> l = new URLinkedList<E>();
		for (int i = fromIndex; i < toIndex; i++) {
			l.add(this.get(i));
		}
		return l;
	}

	@Override
	public Object[] toArray() {
		@SuppressWarnings("unchecked")
		E[] arr = (E[]) new Object[size];
		for (int i = 0; i < size; i++) {
			arr[i] = this.get(i);
		}
		return arr;
	}

	public void addFirst(E e) {
		this.add(0, e);
	}

	public void addLast(E e) {
		this.add(e);
	}

	public E peekFirst() {
		if (size == 0) return null;
		return head.element();
	}

	public E peekLast() {
		if (size == 0) return null;
		return tail.element();
	}

	public E pollFirst() {
		if (size == 0) return null;
		if (size == 1) {
			E old = head.element();
			this.clear();
			return old;
		}

		E old = head.element();
		URNode<E> node = head.next();
		node.setPrev(null);
		head = node;
		return old;
	}

	public E pollLast() {
		if (size == 0) return null;
		if (size == 1) {
			E old = tail.element();
			this.clear();
			return old;
		}

		E old = tail.element();
		URNode<E> node = tail.prev();
		node.setNext(null);
		tail = node;
		return old;
	}
}
