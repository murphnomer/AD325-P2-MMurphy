package Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HashingMain {

    public static List<String> l1 = new ArrayList<>();
    public static List<String> l2 = new ArrayList<>();


    public static void main(String[] args) {
        Random rnd = new Random();
        LinearProbingWithCount<String, String> lp = new LinearProbingWithCount<>();
        populateSampleData();
       for (int i = 0; i < 10; i++) {
            lp.add(l1.get(rnd.nextInt(l1.size())),"");
           System.out.println(LinearProbingWithCount.comparisons);
        }

        lp.displayHashTable();
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
