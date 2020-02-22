/**
 * Write a class called TwoProbeChainHT that implements a two-probe separate
 * chaining hashtable. Two-probe hashing means that you will hash to two
 * positions, and insert the key in the shorter of the two chains. 
 *
 * @author Brett Perry
 * @version 1.0, 25 February 2020
 */

import java.util.LinkedList;

public class TwoProbeChainHT<Key, Value> implements SymbolTable<Key, Value> {

	private int N; // Number of Key Value pairs
	private int M; // Size of array and divisor of hash modulus methods
	private LinkedList<Entry>[] entries; // Array for Key, Value pairs

	public class Entry<EntryKey, EntryValue> {
		private Key key;
		private Value val;

		//constructor
		public Entry(Key key, Value val) {
			if (val == null || key == null)
				throw new IllegalArgumentException("val or key can't be null");
			this.key = key;
			this.val = val;
		}
	}

	//constructor
	public TwoProbeChainHT() {
		this(23);
	}

	public TwoProbeChainHT(int M) {
		this.M = M;
		this.N = 0;

		//create array of size M
		entries = (LinkedList<Entry>[]) new LinkedList[M];

		for (int i = 0; i < M; i++)
			entries[i] = new LinkedList<Entry>();
	}

	//hash function
	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}

	//hash function #2
	private int hash2(Key key) {
		return ((key.hashCode() & 0x7fffffff) % M) * 31 % M;
	}

	//put key-value pair into the table
	@Override
	public void put(Key key, Value val) {
		if (updatePut(key, val))
			return;
		smallerList(key).add(new Entry(key, val));
		N++;
	}
	
	//return true if the keys are equal, else return false
	private boolean updatePut(Key key, Value val) {
		for (int i = 0; i < entries[hash(key)].size(); i++) {
			if (entries[hash(key)].get(i).key.equals(key)) {
				entries[hash(key)].get(i).val = val;
				return true;
			}
		}

		for (int i = 0; i < entries[hash2(key)].size(); i++) {
			if (entries[hash2(key)].get(i).key.equals(key)) {
				entries[hash2(key)].get(i).val = val;
				return true;
			}
		}
		return false;
	}
	
	//Use Java's LinkedList class to store each chain
	private LinkedList<Entry> smallerList(Key key) {
		if (entries[hash(key)].size() > entries[hash2(key)].size())
			return entries[hash2(key)];
		return entries[hash(key)];
	}

	//get value paired with key
	@Override
	public Value get(Key key) {
		for (int i = 0; i < entries[hash(key)].size(); i++) {
			if (entries[hash(key)].get(i).key.equals(key))
				return (Value) entries[hash(key)].get(i).val;
		}
		for (int i = 0; i < entries[hash2(key)].size(); i++) {
			if (entries[hash2(key)].get(i).equals(key))
				return (Value) entries[hash2(key)].get(i).val;
		}
		return null;
	}
	
	//remove key (and its value) from table
	@Override
	public void delete(Key key) {
		for (int i = 0; i < entries[hash(key)].size(); i++) {
			if (entries[hash(key)].get(i).key.equals(key)) {
				entries[hash(key)].remove(i);
				N--;
			}
		}

		for (int i = 0; i < entries[hash2(key)].size(); i++) {
			if (entries[hash2(key)].get(i).equals(key)) {
				entries[hash2(key)].remove(i);
				N--;
			}
		}
	}
	
	//is there a value paired with key?
	@Override
	public boolean contains(Key key) {
		if (get(key) == null)
			return false;
		return true;
	}
	
	//is the table empty?
	@Override
	public boolean isEmpty() {
		if (N == 0)
			return true;
		else
			return false;
	}

	//number of key-value pairs
	@Override
	public int size() {
		return N;
	}
	
	//all keys in the table
	@Override
	public Iterable<Key> keys() {
		LinkedList<Key> keys = new LinkedList<Key>();
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < entries[i].size(); j++)
				keys.add((Key) entries[i].get(j).key);
		}
		return keys;
	}
}





