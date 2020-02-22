/**
 * Write a class called QuadProbingHT that implements a linear probe hashtable. 
 * 
 * @author Brett Perry
 * @version 1.0, 25 February 2020
 */


public class QuadProbingHT<Key, Value> 
extends LinearProbingHT<Key, Value> implements SymbolTable<Key, Value> {
	
	//declare a private variable
	private int M;
	
	//default constructor
	public QuadProbingHT() {
		this(997);	
	}
	
	//parameterized constructor
	public QuadProbingHT(int M) {
		super(M);
		this.M = M;
	}
	
	//hash function
	public int hash(Key key, int i) {
		return (((key.hashCode() & 0x7fffffff) + i*i) % M);
	}
	
}