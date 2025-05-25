import util.MyHashMap;

public class Main {

    public static void main(String[] args) {
        MyHashMap<Integer, String> map = new MyHashMap<>();


        for (int i = 0; i < 100; i++) {
            map.put(i, "test");
        }
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            System.out.println(map.get(i));
        }


    }
}