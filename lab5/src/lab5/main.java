package lab5;

import lab5.URArrayList;

public class main {

	public static void main(String[] args) {
		URArrayList<String> l = new URArrayList<String>();
		System.out.println(l.size());
		l.add("foobar");
		l.add("hello");
		l.add("world");
		l.clear();
		l.add("hello");
		l.add("foo");
		l.add("baz");
		l.add("world");
		l.add(2, "bar");
		l.subList(2, 4);
		for (String s: l) System.out.println(s);

		URArrayList<String> l2 = new URArrayList<String>();
		l2.ensureCapacity(1000);
		l2.add("hello");
		l2.add("foo");
		l2.add("baz");
		l2.add("world");
		l2.add(2, "bar");
		
		System.out.println(l.equals(l2));
	}

}
