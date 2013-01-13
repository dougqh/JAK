package net.dougqh.jak;

public interface JavaFilter<T> {
	public abstract boolean matches(final T value);
}
