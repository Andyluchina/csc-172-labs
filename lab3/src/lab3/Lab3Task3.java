package lab3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;

public class Lab3Task3 {
	public static void printArrayListBasicLoop(ArrayList<Integer> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}
	
	public static void printArrayListEnhancedLoop(ArrayList<Integer> arr) {
		for (int num: arr) {
			System.out.println(num);
		}
	}
	
	public static void printArrayListForLoopListIterator(ArrayList<Integer> arr) {
		for (Iterator<Integer> i = arr.iterator(); i.hasNext();) {
			System.out.println(i.next());
		}
	}
	
	public static void printArrayListWhileLoopListIterator(ArrayList<Integer> arr) {
		Iterator<Integer> i = arr.iterator();
		while (i.hasNext()) {
			System.out.println(i.next());
		}
	}
	
	public static void printArrayListLambda(ArrayList<Integer> arr) {
		/*
		 *  TIL Java 8 lambdas are completely broken.
		 *  
		 *  If they weren't, I would be able to do this:
		 *  
		 *      arr.forEach(System.out.println);
		 *  
		 *  But I can't. Apparently Java 8 did not go back and make all existing
		 *  functions Function objects. Instead, I'm forced to make this useless
		 *  anonymous function wrapper, just so the argument type is Function.
		 *  
		 *  If you're confused as to how the above snippet works, understand that
		 *  the point of lambdas is that functions are first-class objects. They're
		 *  a language primitive in the data type sense. That is, just like there
		 *  are types for Numbers, Booleans, Strings, etc., there's also a type for
		 *  Function. And these are passed around as regular objects.
		 *  
		 *  Note that in the snippet I am not calling System.out.println. If I called
		 *  it, I would be passing the return value of that function (void), but
		 *  instead I am passing _the function itself_. What ArrayList#forEach is
		 *  doing is saying, "for each element in this ArrayList, do this," where
		 *  "this" is some action expressed as code. So I should be able to say,
		 *  print it. Instead I'm saying, call this print function.
		 *  
		 *  Hence, the claim that this wrapper is pointless.
		 */
		
		arr.forEach(num -> System.out.println(num));
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		
		//printArrayListBasicLoop(list);
		//printArrayListEnhancedLoop(list);
		//printArrayListForLoopListIterator(list);
		printArrayListWhileLoopListIterator(list);
		//printArrayListLambda(list);
	}

}
