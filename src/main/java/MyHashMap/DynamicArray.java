package MyHashMap;

public class DynamicArray<T> {
    private static final int DEFAULT_CAPACITY = 16;
    protected int size = 0;

    private T[] values;

    public DynamicArray(int capacity) {
        this.values = (T[]) new Object[capacity];
    }

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    public void put(int slot, T value) {
        if (slot >= values.length) {
            resize(Math.max(values.length * 2, slot + 1));
        }

        if (values[slot] == null) {
            size++;
        }

        values[slot] = value;
    }

    public T get(int ind) {
        if (ind < 0 || ind >= values.length) return null;
        return values[ind];
    }

    public void remove(int ind) {
        if (ind >= 0 && ind < values.length && values[ind] != null) {
            values[ind] = null;
            size--;
        }
    }

    private void resize(int newCapacity) {
        T[] newValues = (T[]) new Object[newCapacity];
        System.arraycopy(values, 0, newValues, 0, values.length);
        values = newValues;
    }

    public int getLength() {
        return values.length;
    }
    

    public int getSize() {
        return size;
    }
}
