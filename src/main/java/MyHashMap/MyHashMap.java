package MyHashMap;

public class MyHashMap<K, V> {

    private DynamicArray<DynamicArray<Node<K, V>>> array;
    private int size = 0;
    private static final double LOAD_FACTOR = 0.75;
    public MyHashMap() {
        this.array = new DynamicArray<DynamicArray<Node<K,V>>>();
    }

    public void put(K key, V value) {
        if ((double) size / array.getLength() >= LOAD_FACTOR) {
            resize();
        }

        int slot = getSlot(key);
        DynamicArray<Node<K, V>> bucket = array.get(slot);

        if (bucket == null) {
            bucket = new DynamicArray<Node<K, V>>();
            array.put(slot, bucket);
        }

        for (int i = 0; i < bucket.getLength(); i++) {
            Node<K, V> node = bucket.get(i);
            if (node != null && node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        bucket.put(bucket.getLength(), new Node<>(key, value));
        size++;
    }

    public V get(K key) {
        int slot = getSlot(key);
        DynamicArray<Node<K, V>> bucket = array.get(slot);

        if (bucket != null) {
            for (int i = 0; i < bucket.getLength(); i++) {
                Node<K, V> node = bucket.get(i);
                if (node != null && node.key.equals(key)) {
                    return node.value;
                }
            }
        }

        return null;
    }

    public void remove(K key) {
        int slot = getSlot(key);
        DynamicArray<Node<K, V>> bucket = array.get(slot);

        if (bucket != null) {
            for (int i = 0; i < bucket.getLength(); i++) {
                Node<K, V> node = bucket.get(i);
                if (node != null && node.key.equals(key)) {
                    bucket.remove(i);
                    return;
                }
            }
        }
    }
    private void resize() {
        DynamicArray<DynamicArray<Node<K, V>>> oldArray = this.array;
        this.array = new DynamicArray<DynamicArray<Node<K,V>>>(oldArray.getLength() * 2);

        for (int i = 0; i < oldArray.getLength(); i++) {
            DynamicArray<Node<K, V>> bucket = oldArray.get(i);
            if (bucket != null) {
                for (int j = 0; j < bucket.getLength(); j++) {
                    Node<K, V> node = bucket.get(j);
                    if (node != null) {
                        int newSlot = Math.abs(node.key.hashCode()) % this.array.getLength();

                        DynamicArray<Node<K, V>> newBucket = this.array.get(newSlot);
                        if (newBucket == null) {
                            newBucket = new DynamicArray<Node<K, V>>();
                            this.array.put(newSlot, newBucket);
                        }

                        newBucket.put(newBucket.getLength(), node);
                    }
                }
            }
        }
    }

    private int getSlot(K key) {
        int hash = Math.abs(key.hashCode());
        return hash % array.getLength();
    }
}
