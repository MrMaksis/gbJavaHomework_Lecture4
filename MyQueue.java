import java.util.LinkedList;

public class MyQueue {

    static LinkedList<Integer> elements = new LinkedList<Integer>();

    public static void main(String[] args) {
        elements.add(1);
        elements.add(2);
        elements.add(3);
        elements.add(4);
        elements.add(5);

        System.out.println(printLinkedList(reversLinkedList()));

        enqueue(8);
        System.out.println(printLinkedList(elements));
        System.out.println(dequeue().toString());
        System.out.println(printLinkedList(elements));
        System.out.println(first().toString());
    }
    /*
     * Methods 1 tasks
     */

    static LinkedList<Integer> reversLinkedList(){
        LinkedList<Integer> reversedList = new LinkedList<Integer>();

        for(int i = 0; i < elements.size(); i++){
            reversedList.add(i, elements.size() - i);
        }
        return reversedList;
    }

    static String printLinkedList(LinkedList<Integer> printLinkedList){
        StringBuffer strBuffer = new StringBuffer();

        for(int i = 0; i < printLinkedList.size(); i++){
            strBuffer.append(printLinkedList.get(i).toString());
        }
        return strBuffer.toString();
    }

    /*
     * Methods 2 tasks
     */

    static void enqueue(int item) {
        elements.addLast(item);
    }

    static Integer dequeue(){
        return elements.removeFirst();
    }

    static Integer first(){
        return elements.getFirst();
    }
}