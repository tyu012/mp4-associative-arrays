package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Tim Yu
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    return null; // STUB
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    if (size == 0) {
      return "{}";
    }

    String ret = "{ ";
    for (int i = 0; i < size; i++) {
      KVPair<K, V> pair = pairs[i];
      ret += pair.key.toString() + ": " + pair.value.toString();
      if (i < size - 1) {
        ret += ", ";
      }
    }
    return ret + " }";
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws NullKeyException {
    if (key.equals(null)) {
      throw new NullKeyException();
    }

    try {
      // Try to overwrite value associated with existing key
      int keyIndex = find(key);
      pairs[keyIndex].value = value; 
    } catch (KeyNotFoundException knf) {
      // If key not found, add new key to list.
      if (size == pairs.length) {
        expand();
      }
      pairs[size] = new KVPair<K, V>(key, value);
      size++;
    }
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    int keyIndex = find(key); // throws KeyNotFoundException
    return pairs[keyIndex].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    if (key.equals(null)) {
      return false;
    }
    try {
      find(key);
    } catch (KeyNotFoundException knf) {
      return false;
    }
    return true;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    try {
      int keyIndex = find(key);
      pairs[keyIndex] = pairs[size - 1];
      pairs[size - 1] = null;
      size--;
    } catch (KeyNotFoundException knf) {
      return;
    }
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   * An entry with key must be stored in [0, size) to be found.
   */
  int find(K key) throws KeyNotFoundException {
    if (key.equals(null)) {
      throw new KeyNotFoundException();
    }
    for (int i = 0; i < size(); i++) {
      if (pairs[i].key.equals(key)) {
        return i;
      }
    }
    throw new KeyNotFoundException();
  } // find(K)

} // class AssociativeArray
