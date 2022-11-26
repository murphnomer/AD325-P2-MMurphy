package Dictionary;

public class DoubleHashingWithCount<K, V> extends HashedDictionary<K, V>{
    public static int comparisons = 1;

    public DoubleHashingWithCount() {
        super();
    }

    // constructor for specific hashtable size
    public DoubleHashingWithCount(int capacity) {
        super(capacity);
    }

    // copy of function from default code, modified to use double hashing
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

    /**
     * Returns the next hash index based on the second hash function x = P - (P % k), where P is the
     * next prime number lower than the current size of the map,
     * which shiuld also be prime
     * @param index is the index we’re starting with that resulted in a collision
     * @param key the key from which we’re building the hash
     * @return is the next available index after probing
     */
    protected int getSecondHashIndex(int index, K key) {
        // reset the counter to 1
        comparisons = 1;

        // get the next lower prime number
        int p = getNextLowerPrime(hashTable.length);

        // get the hashcode for the key
        int hc = key.hashCode();

        // size of the probing increment as given by the secondary hashing function
        int incr = p - (hc % p);

        // tracking whether we’ve found an available spot or not
        boolean found = false;

        // if we find one marked as AVAILABLE, that means a value was here previously but has been deleted, so we have
        // to keep probing
        int availableIndex = -1; // Index of first available location (from which an entry was removed)

        // keep going until find the value we’re looking for or a position that hasn’t been written to
        while ( !found && (hashTable[index] != null) )
        {
            // increment the counter
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

    /**
     * Returns he next prime number lower than a given integer
     * @param n the integer in question
     * @return the next lower prime
     */
    public int getNextLowerPrime(int n) {
        // in case n is even, move to an odd number, since primes must be odd (except 2, in which case there is no
        // lower prime
        n = n - (n % 2 == 0 ? 1 : 2);

        // keep going til you get to a prime number or 3
        while (n >= 3) {
            if (isPrime(n)) break;
            n -= 2;
        }

        // and return the resulting prime
        return n;
    }

}
