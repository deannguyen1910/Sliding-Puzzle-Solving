import assignment2.MyQueueOperations;
import basicdatastructures.queue.*;
import geomerticshapes.*;

public class TestMyQueueOperations {

	public static void testSize() {
		Queue<Integer> q1 = new QueueLinkedListBased<Integer>();
		q1.enqueue(5);
		q1.enqueue(6);
		q1.enqueue(7);

		if (MyQueueOperations.size(q1) != 3) {
			System.out.println("testSize ERROR 1");
			return;
		}

		q1.enqueue(7);
		if (MyQueueOperations.size(q1) != 4) {
			System.out.println("testSize ERROR 2");
			return;
		}

		Queue<GeometricShape> q2 = new QueueLinkedListBased<GeometricShape>();
		Circle c = new Circle(5, 10, 15);
		for (int i = 0; i < 50; i++)
			q2.enqueue(c);
		for (int i = 0; i < 50; i++)
			q2.enqueue(new Circle(1, 2, 3));

		if (MyQueueOperations.size(q2) != 100) {
			System.out.println("testSize ERROR 3");
			return;
		}

		System.out.println("testSize OK");
	}

	public static void testClone() {
		int a[] = { 5, 50, 500 };
		Queue<Integer> q1 = new QueueLinkedListBased<Integer>();
		for (int i = 0; i < a.length; i++) {
			q1.enqueue(a[i]);
		}

		Queue<Integer> q2 = MyQueueOperations.clone(q1);

		// testing q2
		if (q2 == null) {
			System.out.println("testClone ERROR q2==null");
			return;
		}

		if (q2 == q1) {
			System.out.println("testClone ERROR returned q1");
			return;
		}

		for (int i = 0; i < a.length; i++) {
			if (q2.isEmpty() || q2.dequeue() != a[i]) {
				System.out.println("testClone ERROR in q2");
				return;
			}
		}
		if (!q2.isEmpty()) {
			System.out.println("testClone ERROR in q2");
			return;
		}

		// testing q1 is in the original state
		for (int i = 0; i < a.length; i++) {
			if (q1.isEmpty() || q1.dequeue() != a[i]) {
				System.out.println("testClone ERROR q1 modified");
				return;
			}
		}
		if (!q1.isEmpty()) {
			System.out.println("testClone ERROR q1 is too large");
			return;
		}

		System.out.println("testClone OK");

	}

	public static void testReverse() {
		int a[] = {1,2,3,4,5,5,5,5,5};
		Queue<Integer> q1 = new QueueLinkedListBased<Integer>();
		for (int i = 0; i < a.length; i++)
			q1.enqueue(a[i]);

		MyQueueOperations.reverse(q1);

		for (int i = 0; i < a.length; i++) {
			if (q1.isEmpty() || q1.dequeue() != a[a.length-i-1]) {
				System.out.println("testReverse ERROR");
				return;
			}
		}
		if (!q1.isEmpty()) {
			System.out.println("testReverse ERROR in q2");
			return;
		}

		System.out.println("testReverse OK");
	}


	public static void testAreEqual() {
		Queue<GeometricShape> q1 = new QueueLinkedListBased<GeometricShape>();
		Queue<GeometricShape> q2 = new QueueLinkedListBased<GeometricShape>();
		if (!MyQueueOperations.areEqual(q1, q2)) {
			System.out.println("testAreEqual ERROR 1");
			return;
		}

		GeometricShape a[] = {
				new Square(5, 6, 10),
				new Square(1, 2, 10),
				new Circle(10, 20, 1),
				new Rectangle(0, 0, 2, 1),
				};
		for (int i = 0; i < a.length; i++) {
			q1.enqueue(a[i]);
			q2.enqueue(a[i]);
		}

		if (!MyQueueOperations.areEqual(q1, q2)) {
			System.out.println("testAreEqual ERROR 2");
			return;
		}

		q2.enqueue(new Rectangle(1, 1, 1, 1));
		if (MyQueueOperations.areEqual(q1, q2)) {
			System.out.println("testAreEqual ERROR 3");
			return;
		}


		GeometricShape a4[] = {a[0], new Square(1, 2, 10),a[2],a[3]}; // a4[1] != a[1]

		Queue<GeometricShape> q4 = new QueueLinkedListBased<GeometricShape>();
		for (int i = 0; i < a4.length; i++)
			q4.enqueue(a4[i]);
		if (MyQueueOperations.areEqual(q1, q4)) {
			System.out.println("testAreEqual ERROR 4");
			return;
		}

		System.out.println("testAreEqual OK");
	}

	public static void main(String[] args) {
		testSize();
		testClone();
		testReverse();
		testAreEqual();
	}

}
