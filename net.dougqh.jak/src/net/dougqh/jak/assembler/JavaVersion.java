package net.dougqh.jak.assembler;

public enum JavaVersion {
	V1_1( 45, 3 ),
	V1_2( 46, 0 ),
	V1_3( 47, 0 ),
	V1_4( 48, 0 ),
	V1_5( 49, 0 ),
	V1_6( 50, 0 );
	
	private final int major;
	private final int minor;
	
	JavaVersion(
		final int major,
		final int minor )
	{
		this.major = (byte)major;
		this.minor = (byte)minor;
	}
	
	public static final JavaVersion getDefault() {
		return V1_6;
	}
	
	public final int getMajor() {
		return this.major;
	}
	
	public final int getMinor() {
		return this.minor;
	}
	
	public final boolean getSuperFlag() {
		return false;
	}
}
