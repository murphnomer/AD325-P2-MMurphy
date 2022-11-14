package Dictionary;

public class DoubleHashingWithCount<K, V> extends HashedDictionary<K, V>{
    public static int comparisons = 1;

    public DoubleHashingWithCount() {
        super();
    }

    protected int getHashIndex(K key)
    {
        int hashIndex = key.hashCode() % hashTable.length;

        if (hashIndex < 0)
        {
            hashIndex = hashIndex + hashTable.length;
        } // end if

        // Check for and resolve collision
        hashIndex = getSecondHashIndex(hashIndex, key);
//    hashIndex = quadraticProbe(hashIndex, key);

        return hashIndex;
    } // end getHashIndex

    protected int getSecondHashIndex(int index, K key) {
        comparisons = 1;
        int p = getNextLowerPrime(hashTable.length);
        int hc = key.hashCode();

        int incr = p - (hc % p);

        boolean found = false;
        int availableIndex = -1; // Index of first available location (from which an entry was removed)

        while ( !found && (hashTable[index] != null) )
        {
            comparisons++;
            if (hashTable[index] != AVAILABLE)
            {
                if (key.equals(hashTable[index].getKey()))
                    found = true; // Key found
                else             // Follow probe sequence
                    index = (index + incr) % hashTable.length;         // Linear probing
            }
            else // Skip entries that were removed
            {
                // Save index of first location in removed state
                if (availableIndex == -1)
                    availableIndex = index;

                index = (index + incr) % hashTable.length;            // Linear probing
            } // end if
        } // end while
        // Assertion: Either key or null is found at hashTable[index]

        if (found || (availableIndex == -1) )
            return index;                                      // Index of either key or null
        else
            return availableIndex;                          // Index of an available location

    }

    public int getNextLowerPrime(int n) {
        n = n - (n % 2 == 0 ? 1 : 2);
        while (n >= 3) {
            if (isPrime(n)) break;
            n -= 2;
        }
        return n;
    }

}
