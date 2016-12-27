class Node {
     int val;
     Node next;
     Node(int x) { val = x; }
  }

public class OddEvenLL {

  public static void main(String[] args) {
    Node head = new Node(1);
    Node current = head;
    int length = (int)(Math.random() * 100);
    for(int i = 2 ; i <= length ; i++){
      current.next = new Node(i);
      current = current.next;
    }

    System.out.print("List Initially: ");
    printList(head);
    System.out.print("\nList after seperating Odd-Even elements: ");
    printList(oddEvenList(head));
  }

    public static void printList(Node head){
      while(head != null){
        System.out.print(head.val + ", ");
        head = head.next;
      }
    }

    public static Node oddEvenList(Node head) {

        // Complexity: Time = O(n); Space = O(1).

        // This can be done by rearranging the pointers.
        // Assume there are two sublists, wherein one contains all the odd nodes and the other contains even nodes respectively.
        // Attach all the odd nodes to the end of the odd list at each iteration through the list.
        // This can be done in O(1) time since we are using LinkedLists, which have insertions and deletions in O(1) time.
        // We require three references, one to the end of the odd list, another to the end of even list
        // , and the last to the traversa in the current list.
        // You do not need to create any new node at any operation during this traversal.

        // return if there is no list.
        if(head == null) return null;

        Node current = head.next;

        // return if it is only one element.
        if(current == null) return head;

        Node oddP = head;
        Node previous = current;
        current = current.next;
        boolean odd = true;
        while(current != null){
            // for all odd positions, attach them to the begining and update the pointers.
            if(odd){
                previous.next = current.next;
                current.next = oddP.next;
                oddP.next = current;
                oddP = current;
                current = previous.next;
                odd = false;
            }else{
                previous = current;
                current = current.next;
                odd = true;
            }
        }
        return head;
    }
}

/*
Problem Statement:

Given a singly linked list, group all odd nodes together followed by the even nodes.
Please note here we are talking about the node number and not the value in the nodes.

Example:

Input: 1 -> 2 -> 3 -> 4
Output: 1 -> 3 -> 2-> 4
*/
