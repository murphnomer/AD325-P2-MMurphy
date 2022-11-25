package Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HashingMain {

    public static List<String> l1 = new ArrayList<>();
    public static List<String> l2 = new ArrayList<>();


    public static void main(String[] args) {
        Random rnd = new Random();

        // Load factor corresponding to avg number of unsuccessful searches 1.5 is 0.3
        // so size of the hash table with 100 entries for this load factor is:
        // 0.3x = 100,  x = 100 / 0.3, x = 333
        LinearProbingWithCount<String, String> lp = new LinearProbingWithCount<>(333);
        DoubleHashingWithCount<String, String> dh = new DoubleHashingWithCount<>(333);
        populateSampleData();

        List<Integer> lpc = new ArrayList<>();
        List<Integer> dhc = new ArrayList<>();


        int n = 0;


        for (int i = 0; i < 100; i++) {
            n = rnd.nextInt(l1.size());
            lp.add(l1.get(n), "");
            dh.add(l1.get(n), "");
        }

        for (int i = 0; i < 1000; i++) {
            n = rnd.nextInt(l2.size());

            lp.contains(l2.get(n));
            dh.contains(l2.get(n));

            lpc.add(lp.comparisons);
            dhc.add(dh.comparisons);

        }


//        lp.displayHashTable();
//        dh.displayHashTable();

        System.out.println("Linear Probing Comparisons Average: " + lpc.stream().mapToInt(a -> a).average());
        System.out.println("Double Hashing Comparisons Average: " + dhc.stream().mapToInt(a -> a).average());
    }

    public static void populateSampleData() {
        Random rnd = new Random();
        for (int i = 0; i < 1000; i++) {
            l1.add("a" + i);
        }
        for (int i = 0; i < 10000; i++) {
            l2.add("b" + i);
        }
    }
}
