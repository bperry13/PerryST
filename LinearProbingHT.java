/**
 * Write a class called LinearProbingHT that implements a linear 
 * probe hashtable. 
 * 
 * @author Brett Perry
 * @version 1.0, 25 February 2020
 */

import java.util.LinkedList;

public class LinearProbingHT<Key, Value> implements SymbolTable<Key, Value> {

	private int N;
	private int M;
	private Entry<Key, Value>[] entries;

	public class Entry<EntryKey, EntryValue> {
		private Key key;
		private Value val;

		public Entry(Key key, Value val) {
			if (val == null || key == null)
				throw new IllegalArgumentException("val or key can't be null");
			this.key = key;
			this.val = val;
		}
	}

	//constructor
	public LinearProbingHT() {
		this(997);
	}

	public LinearProbingHT(int M) {
		this.M = M;
		this.N = 0;
		this.entries = new Entry[M];
	}
	
	//hash function
	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}


	//put key-value pair into the table
	@Override
	public void put(Key key, Value val) {
		if (N > M * .75)
			throw new ArrayStoreException("The hash table is too full");
		int i = 0;
		for (i = hash(key); entries[i] != null; i = (i + 1) % M) {
			//check if empty
			if (entries[i].key.equals(key)) {
					entries[i].val = val;
					return;
			}
		}
		entries[i] = new Entry<Key, Value>(key, val);
		N++;
	}

	//get value paired with key
	@Override
	public Value get(Key key) {
		for (int i = hash(key); entries[i] != null; i = (i + 1) % M) {
			//check if empty
			if(entries[i].key.equals(key)) 
				return entries[i].val;
		}
		return null;
	}
	
	//remove key (and its value) from table
	@Override
	public void delete(Key key) {
		for (int i = hash(key); entries[i] != null; i = (i + 1) % M) {
			// check if empty
			if (entries[i].key.equals(key)) {
				for (int j = i + 1; 
					entries[j] != null && j > hash(entries[j].key); 
					j = (j + 1) % M) {
						entries[i] = entries[j];
						i = (i + 1) % M;
					}
					entries[i] = null;
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

	//isEmpty method
	@Override
	public boolean isEmpty() {
		return N == 0;
	}
	
	//is the table empty?
	@Override
	public int size() {
		return N;
	}

	//all keys in the table
	@Override
	public Iterable<Key> keys() {
		LinkedList<Key> list = new LinkedList<Key>();
		for (int i = 0; i < M; i++) {
			if (entries[i] != null)
				list.add(entries[i].key);
		}
		return list;
	}
}
