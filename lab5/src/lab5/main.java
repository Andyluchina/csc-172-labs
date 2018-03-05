package lab5;


import lab5.URLinkedList;

public class main {

	public static void main(String[] args) {
		URLinkedList<String> l = new URLinkedList<String>();
		//System.out.println(l.size());
		l.add("foobar");
		l.add("hello");
		l.add("world");
		l.clear();
		//l.add("hello");
		l.add("foo");
		l.add("baz");
		l.add(0, "hello");
		l.add(2, "bar");
		l.add(4, "world");
		//System.out.println(l.size());
		//System.out.println(l.get(2) == "bar");
		//System.out.println(l.contains("hello"));
		//l.add("tmp");
		//System.out.println(l.pollFirst());
		//System.out.println(l.pollFirst());
		/*
		l.subList(2, 4);
*/
		l.remove("foo");
		for (String s: l) System.out.println(s);
		URLinkedList<String> l2 = new URLinkedList<String>();
		l2.add("hello");
		l2.add("foo");
		l2.add("baz");
		l2.add("world");
		l2.add(2, "bar");
		
		System.out.println(l.equals(l2));
	}

}
