import java.util.Random;

class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
  }

public class AddLL {

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

    public void printList(ListNode head){
      while(head != null){
        System.out.print(head.val + "->");
        head = head.next;
      }
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

    public int getLength(ListNode head){
        int l = 0;
        while(head != null){
            l++;
            head = head.next;
        }
        return l;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int lenl1 = getLength(l1);
        int lenl2 = getLength(l2);
        if(lenl1 > lenl2) return addNums(lenl1 - lenl2, l1, l2).node;
        else return addNums(lenl2 - lenl1, l2, l1).node;
    }

    public MyNode addNums(int diff, ListNode l1, ListNode l2){
        if(l1 == null){
            return new MyNode();
        }
        if(diff == 0){
            MyNode myNode = addNums(diff, l1.next, l2.next);
            int sum = l1.val + l2.val + myNode.carry;
            ListNode node = new ListNode(sum % 10);
            node.next = myNode.node;
            int carry = sum / 10;
            return new MyNode(node, carry);
        }else{
            MyNode myNode = addNums(diff - 1, l1.next, l2);
            int sum = l1.val + myNode.carry;
            ListNode node = new ListNode(sum % 10);
            node.next = myNode.node;
            int carry = sum / 10;
            return new MyNode(node, carry);
        }
    }

    private class MyNode{
        ListNode node;
        int carry;

        public MyNode(){
            carry = 0;
            node = null;
        }

        public MyNode(ListNode node, int c){
            this.node = node;
            carry = c;
        }
    }
}

/*

Add two linked list that represent a number.
Eg.
L1: 1->2->3
L2: 4->6

Then Number(L1) = 123
Number(L2) = 46

Sum = 123 + 46 = 169

*/
