package Dictionary;

public class LinearProbingWithCount<K, V> extends HashedDictionary<K, V> {

    public static int comparisons = 1;

    public LinearProbingWithCount() {
        super();
    }

    protected int linearProbe(int index, K key)
    {

        comparisons = 1;
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
