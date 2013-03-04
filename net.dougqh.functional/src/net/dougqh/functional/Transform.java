package net.dougqh.functional;

public abstract class Transform<I, O> {
	public abstract O transform(final I input) throws Exception;
	
	public final <T> Transform<I, T> chain(final Transform<? super O, ? extends T> transform) {
		return new CompositeTransform<I, O, T>(this, transform);
	}
}
