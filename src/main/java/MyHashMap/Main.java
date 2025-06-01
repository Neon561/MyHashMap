package MyHashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        Set set = new TreeSet<>();
        List list = new LinkedList();
        set.add("qwe");
        list.add("qwe");
        System.out.println(list.equals(set));

    }
}