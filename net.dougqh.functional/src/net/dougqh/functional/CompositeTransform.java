package net.dougqh.functional;

final class CompositeTransform<I, T, O> extends Transform<I, O> {
	private final Transform<? super I, ? extends T> firstTransform;
	private final Transform<? super T, ? extends O> secondTransform;
	
	public CompositeTransform(
		final Transform<? super I, ? extends T> firstTransform,
		final Transform<? super T, ? extends O> secondTransform)
	{
		this.firstTransform = firstTransform;
		this.secondTransform = secondTransform;
	}
	
	public final O transform(final I input) throws Exception {
		return this.secondTransform.transform(this.firstTransform.transform(input));
	}
}
