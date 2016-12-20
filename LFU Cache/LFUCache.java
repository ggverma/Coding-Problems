import java.util.HashMap;

public class LFUCache{

  private int capacity, cached_objects;

  private int min_freq;

  // cache_metadata:
  // 1st index stores frequency.
  private HashMap<Integer, Root> cache_metadata;

  // key -> value
  private HashMap<Integer, Data> cache;


  public LFUCache(int capacity){
    this.capacity = capacity;

    cached_objects = 0;
    min_freq = 1;

    cache = new HashMap<>();
    cache_metadata = new HashMap<>();
  }

  public int get(int key){
    if(capacity == 0) return -1;
    // System.out.println("Getting key " + key);
    if(cache.containsKey(key)){
        // Update metadata
        // cache_metadata must have its root.
        Data data = cache.get(key);
        // System.out.println("-->size = " + cache_metadata.get(data.getFrequency()).size);
        cache_metadata.get(data.getFrequency()).removeKey(key);
        // System.out.println("-->size = " + cache_metadata.get(data.getFrequency()).size);
        // Remove this root if its size is 0
        if(cache_metadata.get(data.getFrequency()).size == 0){
          // System.out.println("\tNode Freq 0");
          if(min_freq == data.getFrequency()){
            // System.out.println("\tUpdating Min Freq");
            min_freq++;
          }
          cache_metadata.remove(data.getFrequency());
        }

        data.incrementFrequency();
        if(!cache_metadata.containsKey(data.getFrequency())){
          // System.out.println("\tCreating New Root");
          cache_metadata.put(data.getFrequency(), new Root());
        }
        cache_metadata.get(data.getFrequency()).addKey(key);

        return cache.get(key).value;
    }else{
      // System.out.println("\tNot Found!\n----");
      return -1;
    }
  }

  public void set(int key, int value){
    if(capacity == 0) return;
    // System.out.println("Setting...");
    if(cache.containsKey(key)){
      // Update of existing data
      Data data = cache.get(key);
      int dataFreq = data.getFrequency();

      // Remove from old frequency in metadata
      cache_metadata.get(data.getFrequency()).removeKey(key);

      // Remove this root if its size is 0
      if(cache_metadata.get(data.getFrequency()).size == 0){
        if(min_freq == dataFreq){
          // System.out.println("\tUpdating min freq");
          min_freq++;
        }
        cache_metadata.remove(data.getFrequency());
      }

      // Increment Frequency
      data.incrementFrequency();
      dataFreq++;

      // Update data value
      data.value = value;

      // Add to new frequency metadata
      if(!cache_metadata.containsKey(dataFreq)){
        // System.out.println("\tCreating new root");
        cache_metadata.put(dataFreq, new Root());
      }
      cache_metadata.get(dataFreq).addKey(key);
    }else{
      // New Data
      if(cached_objects == capacity){
        // Cache is full!
        // System.out.println("\tCache Full!");
        // for (HashMap.Entry<Integer, Root> entry : cache_metadata.entrySet()) {
        //   System.out.println("\t" + entry.getKey() + " " + cache_metadata.get(entry.getKey()).size);
        // }
        // System.out.println("\t--- mf: " + min_freq);

        int keyToRemove = cache_metadata.get(min_freq).removeAndGetLRUKey();
        // System.out.println("-->keyToRemove = " + keyToRemove);
        cache.remove(keyToRemove);
        cached_objects--;
      }
      if(!cache_metadata.containsKey(1)){
        cache_metadata.put(1, new Root());
      }
      min_freq = 1;
      cache_metadata.get(1).addKey(key);
      cached_objects++;
      cache.put(key, new Data(value));
    }
  }

  public int getCacheSize(){
    return cached_objects;
  }

  private class Data{
    int value;
    int frequency;

    Data(int v){
      value = v;
      frequency = 1;
    }

    public void incrementFrequency(){
      frequency++;
    }

    public int getFrequency(){
      return frequency;
    }
  }

  private class Root{
    Node next;
    Node tail;

    int size;

    // key -> Node
    HashMap<Integer, Node> metadata;

    public Root(){
      next = null;
      tail = null;
      size = 0;
      metadata = new HashMap<Integer, Node>();
    }

    public void removeKey(int key){
      if(next == null) return;
      size--;
      if(next.key == key){
        // This is first child
        // System.out.println("\t\tRemoving first child with key " + next.key);
        Node n = next;
        next = next.next;
        // System.out.println("\t\tRoot now points to key " + (next != null ? next.key : "null"));
        n.next = null;
        metadata.remove(key);
      }else{
        // Multiple keys with this frequency. But this is not the first child.
        Node node = metadata.get(key);
        // System.out.println("\t\tRemoving some middle child with key " + node.key);
        if(node.next != null) node.next.previous = node.previous;
        node.previous.next = node.next;
        node.previous = null;
        node.next = null;
        metadata.remove(key);
      }
    }

    public int removeAndGetLRUKey(){
      size--;
      // System.out.println("\t\tRAGLRUKEY");
      int key = next.key;
      Node n = next;
      next = next.next;
      n.next = null;
      return key;
    }

    public void addKey(int key){
      size++;
      if(next == null){
        // first child
        next = new Node(key);
        // System.out.println("\t\tAdding first child with key " + next.key);
        metadata.put(key, next);

        tail = next;
      }else{
        // not first child
        Node node = new Node(key);
        // System.out.println("\t\tAdding some middle child with key " + node.key);
        metadata.put(key, node);

        tail.next = node;
        node.previous = tail;

        tail = node;
      }
    }
  }

  private class Node{
    int key;
    Node next;
    Node previous;

    public Node(int k){
      key = k;
      next = previous = null;
    }
  }

  public static void main(String[] args) {
    LFUCache cache = new LFUCache(10);
    String msg = "";

    msg += cache.get(1) + " ";

    cache.set(10, 13);
    msg += cache.get(1) + " ";

    cache.set(3, 17);
    msg += cache.get(2) + " ";

    cache.set(6, 11);
    msg += cache.get(1) + " ";

    cache.set(10, 5);
    msg += cache.get(2) + " ";

    cache.set(9, 10);
    msg += cache.get(13) + " ";

    cache.set(2, 19);
    msg += cache.get(2) + " ";

    cache.set(6, 1313);
    msg += cache.get(3) + " ";

    cache.set(5, 25);
    msg += cache.get(8) + " ";

    System.out.println("Filled Cache Size: " + cache.getCacheSize());
    System.out.println(msg);
  }
}

/*

Leet Code Problem: Time Complexity: O(1)

Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and set.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
set(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

*/
