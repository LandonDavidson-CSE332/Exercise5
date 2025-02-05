import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChainingHashTable <K,V> implements DeletelessDictionary<K,V>{
    private List<Item<K,V>>[] table; // the table itself is an array of linked lists of items.
    private int size;
    private static int[] primes = {11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853};

    public ChainingHashTable(){
        table = (LinkedList<Item<K,V>>[]) Array.newInstance(LinkedList.class, primes[0]);
        for(int i = 0; i < table.length; i++){
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }    

    public int size(){
        return size;
    }

    // Function to resize the hash table
    private void resize() {
        // TODO
    }

    public V insert(K key, V value){
        // Resize if load factor is greater than or equal to 3
        if (size / table.length >= 3) {
            resize();
        }

        // Find the bucket for the given key
        List<Item<K, V>> bucket = table[key.hashCode() % table.length];
        // Iterate through bucket looking for key, if we find it update value and return
        for (Item<K, V> item : bucket) {
            if (item.key == key) {
                V tmp = item.value;
                item.value = value;
                return tmp;
            }
        }
        // If we made it through the loop then the key doesn't exist yet and we can add it to the head of the bucket
        Item<K, V> new_item = new Item<K, V>(key, value);
        bucket.add(new_item);

        // There was no old value so return null
        return null;
    }

    public V find(K key){
        // Find the key's bucket 
        int bucket = key.hashCode() % table.length;
        // Iterate through the given LinkedList until we find and return the value
        for (Item<K, V> item : table[bucket]) {
            if (item.key == key) {
                return item.value;
            }
        }

        // If we didn't find the key then it isn't in the table and return null
        return null;
    }

    public boolean contains(K key){
        // If the key is found in the table (not null) return true, otherwise it isn't contained
        return find(key) != null ? true : false;
    }

    public List<K> getKeys(){
        // Initialize an ArrayList to store each key in
        ArrayList<K> keys = new ArrayList<>();
        // For each bucket in the table loop through each of its elements
        for (List<Item<K, V>> bucket : table) {
            for (Item<K, V> item : bucket) {
                keys.add(item.key);
            }
        }

        // Return the filled list of keys
        return keys;
    }

    public List<V> getValues(){
        // Initialize an ArrayList to store each value in
        ArrayList<V> values = new ArrayList<>();
        // For each bucket in the table loop through each of its elements
        for (List<Item<K, V>> bucket : table) {
            for (Item<K, V> item : bucket) {
                values.add(item.value);
            }
        }

        // Return the filled list of values
        return values;
    }

    public String toString(){
        String s = "{";
        s += table[0];
        for(int i = 1; i < table.length; i++){
            s += "," + table[i];
        }
        return s+"}";
    }

}
