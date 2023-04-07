import assignment2.MyLinkedList;
import jdk.dynalink.Operation;
import java.util.LinkedList;
import java.util.Stack;

import java.lang.Integer;
public class testingLinkedList {
    public static void main(String[] args) {
        MyLinkedList<Integer> a = new MyLinkedList<Integer>();
//        a.addLeft(5);
//        a.addLeft(4);
//        a.addLeft(3);
        a.addRight(6);
//        a.addRight(7);
//        a.reverse();
//        a.addLeft(2);
        //a.addLeft(1);
//        a.addRight(8);
//        a.addRight(9);
        a.removeRight();
        //a.removeLeft();
        System.out.println("\n" + a.tail.value);
        System.out.println(a.toString() + "\nSize: " + a.size());
        //System.out.println(a.toStringBackward());
        System.out.println("Middle: " + a.getMiddle() + "  Position: " + a.pos_mid.toString());
        final int SORT = 0;
        Operation b = new Operation(SORT, a.toString());
    }
}