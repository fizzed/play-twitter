package com.fizzed.play.twitter;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class CachedObj<T> {
	private final AtomicLong ts;
	private final AtomicReference<T> valueRef;
	
	public CachedObj() {
		this(null);
	}
	
	public CachedObj(T value) {
		this.ts = new AtomicLong(System.currentTimeMillis());
		this.valueRef = new AtomicReference<T>(value);
	}

	public long timestamp() {
		return ts.get();
	}
	
	public long age() {
		return System.currentTimeMillis() - ts.get();
	}

	public T value() {
		return valueRef.get();
	}
	
	public boolean hasValue() {
		return valueRef.get() != null;
	}
	
	public void set(T value) {
		this.valueRef.set(value);
		this.ts.set(System.currentTimeMillis());
	}
}
