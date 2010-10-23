package net.dougqh.jak.repl;

enum OnOff implements ReplEnum< OnOff > {
	ON( "on", true ),
	OFF( "off", false );
	
	private final String id;
	private final boolean value;
	
	OnOff( final String id, final boolean value ) {
		this.id = id;
		this.value = value;
	}
	
	public static final OnOff parse( final String stringValue ) {
		return ReplFormatter.parse( OnOff.class, stringValue );
	}
	
	public static final OnOff valueOf( final boolean booleanValue ) {
		for ( OnOff value : values() ) {
			if ( value.booleanValue() == booleanValue ) {
				return value;
			}
		}
		throw new IllegalStateException();
	}
	
	public final String id() {
		return this.id;
	}
	
	public final boolean booleanValue() {
		return this.value;
	}
	
	@Override
	public final String toString() {
		return this.id.toUpperCase();
	}
}
