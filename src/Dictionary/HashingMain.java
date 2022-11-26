package Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

public class HashingMain {

    // variable to store list of names that will be hashed and inserted into the hashmap
    public static List<String> l1 = new ArrayList<>();

    // variable to store the disjoint list of names that will always result in unsuccessful searches
    public static List<String> l2 = new ArrayList<>();


    public static void main(String[] args) {
        Random rnd = new Random();

        // Load factor corresponding to avg number of unsuccessful searches 1.5 is 0.3
        // so size of the hash table with 100 entries for this load factor is:
        // 0.3x = 100,  x = 100 / 0.3, x = 333
        LinearProbingWithCount<String, String> lp = new LinearProbingWithCount<>(333);
        DoubleHashingWithCount<String, String> dh = new DoubleHashingWithCount<>(333);

        // Generate the two disjoint lists as specified in the problem description
        populateSampleData();

        // lists to store the number of comparisons for each hashing method when searching
        List<Integer> lpc = new ArrayList<>();
        List<Integer> dhc = new ArrayList<>();

        // lists to store the total number of comparisons for each method over the whole set
        List<Integer> lpcTot = new ArrayList<>();
        List<Integer> dhcTot = new ArrayList<>();


        // temp variable to store the current random number so that both lists contain the same data and are searched
        // for the dame items for comparison purposes
        int n = 0;

        // Insert the same randomly selected values into both hashmaps
        for (int i = 0; i < 100; i++) {
            n = rnd.nextInt(l1.size());
            lp.add(l1.get(n), "");
            dh.add(l1.get(n), "");
        }

        // Search for 1000 randomly selected values from the disjoint list using each probing method and track how many comparisons
        // are made each time
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

        // Run the requested experiment
        for (int i = 0; i < 1000; i++) {
            lp = new LinearProbingWithCount<>(333);
            dh = new DoubleHashingWithCount<>(333);

            lpc.clear();
            dhc.clear();

            // randomly choose and insert 100 names
            for (int j = 0; j < 100; j++) {
                n = rnd.nextInt(l1.size());
                lp.add(l1.get(n), "");
                dh.add(l1.get(n), "");
            }

            // randomly search each msp for 100 names from the disjoint set
            for (int j = 0; j < 100; j++) {
                n = rnd.nextInt(l2.size());

                lp.contains(l2.get(n));
                dh.contains(l2.get(n));

                lpc.add(lp.comparisons);
                dhc.add(dh.comparisons);

            }

            lpcTot.add(lpc.stream().mapToInt(a -> a).sum());
            dhcTot.add(dhc.stream().mapToInt(a -> a).sum());

        }

        System.out.println("Linear probing stats:\nAvg comparisons:\t" + lpcTot.stream().mapToInt(a -> a).average().getAsDouble() + "\n"+
                                                         "StDev Comparisons:\t" + std(lpcTot) + "\n\n");
        System.out.println("Double hashing stats:\nAvg comparisons:\t" + dhcTot.stream().mapToInt(a -> a).average().getAsDouble() + "\n"+
                "StDev Comparisons:\t" + std(dhcTot));
    }

    /**
     * Calculates the standard deviation of a list of Integers. Code adapted from here:
     * https://gist.github.com/asela38/ed553c6908976ffc1398dd9cd83a15e6
     * @param l List of Integers
     * @return the standard deviation of the list
     */
    public static double std(List<Integer> l) {
        double avg = l.stream().mapToInt(a -> a).average().getAsDouble();
        double var = l.stream().map(a -> a - avg).map(a -> a*a).mapToDouble(a -> a).average().getAsDouble();
        return Math.sqrt(var);

    }
    /**
     * Creates a 1000 item list of names and a completely disjoint 10000 item list of names
     */
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
