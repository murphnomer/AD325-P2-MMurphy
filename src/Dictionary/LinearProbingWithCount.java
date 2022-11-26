package Dictionary;

public class LinearProbingWithCount<K, V> extends HashedDictionary<K, V> {

    // variable for storing how many comparisons have bee made this cycle
    public static int comparisons = 1;

    // default constructor
    public LinearProbingWithCount() {
        super();
    }

    // constructor for specific hashtable size
    public LinearProbingWithCount(int capacity) {
        super(capacity);
    }

    // copy of the linearProbe function from the given class, but modified to maintain a count og how many comparisons
    // were made during each probe
    protected int linearProbe(int index, K key)
    {
        // reinitialize counter, we always have one comparison so start there
        comparisons = 1;
        boolean found = false;
        int availableIndex = -1; // Index of first available location (from which an entry was removed)

        while ( !found && (hashTable[index] != null) )
        {
            // each time we probe, increment the counter
            comparisons++;
            if (hashTable[index] != AVAILABLE)
            {
                if (key.equals(hashTable[index].getKey()))
                    found = true; // Key found
                else             // Follow probe sequence
                    index = (index + 1) % hashTable.length;         // Linear probing
            }
            else // Skip entries that were removed
            {
                // Save index of first location in removed state
                if (availableIndex == -1)
                    availableIndex = index;

                index = (index + 1) % hashTable.length;            // Linear probing
            } // end if
        } // end while
        // Assertion: Either key or null is found at hashTable[index]

        if (found || (availableIndex == -1) )
            return index;                                      // Index of either key or null
        else
            return availableIndex;                          // Index of an available location
    } // end linearProbe



}
