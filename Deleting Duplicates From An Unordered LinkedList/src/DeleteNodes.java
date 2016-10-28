import java.util.HashMap;

class Node{
	
	Node next;
	char character;
	
	public Node(char value){
		character = value;
		next = null;
	}
	
	public boolean insert(char value){
		Node node = new Node(value);
		Node n = this;
		while(n.next != null){
			n = n.next;
		}
		
		n.next = node;
		
		if(n.next != null){
			return true;
		}else{
			return false;
		}
	}
	
	public void deleteNode(Node head){
		if(head == this){
			head = head.next;
		}
		
		while(head.next.next != this){
			head = head.next;
		}
		
		head.next = head.next.next;
	}
}

public class DeleteNodes {
	
	/*Printing the linked list with head - head*/
	static void printList(Node head){
		Node iterator = head;
		
		while(iterator.next != null){
			System.out.print(iterator.character);
			iterator = iterator.next;
		}
		
		System.out.println();
	}
	
	static void deleteDuplicatesWithExtraBuffer(Node head){
		/*Deleting the duplicates with extra buffer*/
		HashMap<Character, Boolean> nodeMap = new HashMap<>();
		
		Node iterator = head;
		Node prev = null;
		while(iterator.next != null){
			
			if(!nodeMap.containsKey(iterator.character)){
				nodeMap.put(iterator.character, true);
				prev = iterator;
			}else{
				prev.next = iterator.next;
			}
			
			iterator = iterator.next;			
		}
	}
	
	public static void deleteDuplicatesWithNoBuffer(Node head){
		/*Uses two pointers*/
		while(head.next != null){
			Node prevToTail = head;
			Node tail = head.next;
			while(tail.next != null){
				if(tail.character == head.character){
					prevToTail.next = tail.next;
				}else{
					prevToTail = tail;
				}
				tail = tail.next;
			}
			head = head.next;
		}
	}
	
	public static Node createList(String string){
		
		char stringArr[] = string.substring(1).toCharArray();
		
		/*Creating the Linked List*/
		Node head = new Node(string.charAt(0));
		
		for(char c : stringArr){
			head.insert(c);
		}
		
		return head;
	}
	
	public static char findKthNodeValue(int k, Node head){
		Node kthNode = head;
		
		int diff = 0;
		
		while(head.next != null){
			if(diff < k){
				diff++;
			}
			if(diff == k){
				kthNode = kthNode.next;
			}
			head = head.next;
		}
		if(diff < k){
			return '\0';//The length of LL is shorter!
		}else{
			return kthNode.character;
		}
	}
	
	public static void main(String[] args){
		
		Node head = createList("Follow Up");
		
		printList(head);
		
		deleteDuplicatesWithExtraBuffer(head);
		
		printList(head);
		
		head = createList("Follow Up");
		
		printList(head);
		
		deleteDuplicatesWithNoBuffer(head);
		
		printList(head);
		
		head = createList("Follow Up");
		
		char kValue = findKthNodeValue(78, head);
		
		if(kValue == '\0'){
			System.out.println("The kth element was not found! The LL is shorter for it!");
		}else{
			System.out.println(kValue);
		}
	}
}
