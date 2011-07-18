package testdata;

import java.io.Serializable;

public final class NonTrivialClass
	extends Object
	implements Serializable, Runnable
{
	public static final int CONSTANT = 20;
	public static boolean DEBUG = false;
	
	private final int x;
	private int y;
	
	NonTrivialClass( final int x, final int y ) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public final void run() {
		System.out.println();
	}
}
