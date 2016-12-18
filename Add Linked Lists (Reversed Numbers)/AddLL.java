import java.util.Random;

 class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
  }

public class AddLL {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null) return null;
        int n1 = 0, n2 = 0;
        if(l1 != null) n1 = l1.val;
        if(l2 != null) n2 = l2.val;
        int sum = n1 + n2;
        ListNode head = new ListNode(sum % 10);
        add(l1 != null ? l1.next : l1, l2 != null ? l2.next : l2, sum > 9 ? 1 : 0, head);
        return head;
    }

    private void add(ListNode l1, ListNode l2, int carry, ListNode tail){
        if(l1 == null && l2 == null){
            if(carry == 1)
                tail.next = new ListNode(1);
            return;
        }

        int n1 = 0, n2 = 0;

        if(l1 != null) n1 = l1.val;
        if(l2 != null) n2 = l2.val;

        int sum = n1 + n2 + carry;
        //System.out.println(sum);
        tail.next = new ListNode(sum % 10);
        add(l1 == null ? null : l1.next, l2 == null ? null : l2.next, sum > 9 ? 1 : 0, tail.next);
    }

    public void printList(ListNode head){
      if(head == null){
        System.out.println();
        return;
      }
      while(head.next != null){
        System.out.print(head.val + "->");
        head = head.next;
      }
      System.out.println(head.val);
      System.out.println();
    }

    public static void main(String[] args){
      AddLL tester = new AddLL();
      ListNode list1 = tester.makeRandomList();
      ListNode list2 = tester.makeRandomList();
      ListNode addedList = tester.addTwoNumbers(list1, list2);

      System.out.println("List 1: ");tester.printList(list1);
      System.out.println("List 2: ");tester.printList(list2);
      System.out.println("Added List: ");tester.printList(addedList);
    }

    public ListNode makeRandomList(){
      Random random = new Random();
      int length = random.nextInt(20) + 1;
      ListNode previous = null;
      while(length > 0){
        ListNode current = new ListNode(random.nextInt(9));
        current.next = previous;
        previous = current;
        length--;
      }
      return previous;
    }

}

/*

////////// Leet Code Question //////////////

You are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8

*/
