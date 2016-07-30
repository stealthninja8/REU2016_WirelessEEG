package eeggui;

public class Pair<F, T> {
	
	private F first;
	private T second;
	
	public Pair(F f, T t) {
		this.first = f;
		this.second = t;
	}

	public F getFirst() {
		return first;
	}

	public void setFirst(F first) {
		this.first = first;
	}

	public T getSecond() {
		return second;
	}

	public void setSecond(T second) {
		this.second = second;
	}
}
