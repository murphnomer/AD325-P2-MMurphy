package Dictionary;

public class DoubleHashingWithCount<K, V> extends HashedDictionary<K, V>{
    public static int comparisons = 1;

    public DoubleHashingWithCount() {
        super();
    }

    public int getSecondHashIndex(K key) {
    return 0;
    }

}
