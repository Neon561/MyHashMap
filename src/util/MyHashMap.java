package util;

public class MyHashMap<K, V> {

    private DynamicArray<DynamicArray<Node<K, V>>> array;

    public MyHashMap() {
        this.array = new DynamicArray<>();
    }

    public void put(K key, V value) {
        int slot = getSlot(key);
        DynamicArray<Node<K, V>> bucket = array.get(slot);

        if (bucket == null) {
            bucket = new DynamicArray<>();
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
        this.array = new DynamicArray<>(oldArray.getLength() * 2);

        for (int i = 0; i < oldArray.getLength(); i++) {
            DynamicArray<Node<K, V>> bucket = oldArray.get(i);
            if (bucket != null) {
                for (int j = 0; j < bucket.getLength(); j++) {
                    Node<K, V> node = bucket.get(j);
                    if (node != null) {
                        int newSlot = Math.abs(node.key.hashCode()) % this.array.getLength();

                        DynamicArray<Node<K, V>> newBucket = this.array.get(newSlot);
                        if (newBucket == null) {
                            newBucket = new DynamicArray<>();
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
