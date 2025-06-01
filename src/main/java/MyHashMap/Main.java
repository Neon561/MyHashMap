package MyHashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
    MyHashMap<Integer,String>map = new MyHashMap<Integer,String>();

        for (int i = 0; i < 100; i++) {
            map.put(i, "banana " + i);
        }
        for (int i = 0; i < 100; i++) {
            System.out.println(map.get(i));
        }


    }
}