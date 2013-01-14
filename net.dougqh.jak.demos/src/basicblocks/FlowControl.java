package basicblocks;

public class FlowControl {
	private FlowControl() {}
	
	public static final int signum( final int x ) {
		if ( x < 0 ) {
			return -1;
		} else if ( x == 0 ) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public static final int sum( final int min, final int max ) {
		int sum = 0;
		for ( int x = min; x < max; ++x ) {
			sum +=x;
		}
		return sum;
	}
	
	public static final int gauss() {
		int sum = 0;
		for ( int i = 0; i < 100; ++i ) {
			sum += i;
		}
		return sum;
	}
}
