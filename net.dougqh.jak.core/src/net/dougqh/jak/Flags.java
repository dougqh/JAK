package net.dougqh.jak;


public final class Flags {
	public static final int NO_FLAGS = 0x0;
	
	public static final int PUBLIC = 0x0001;
	public static final int PRIVATE = 0x0002;
	public static final int PROTECTED = 0x0004;
	
	public static final int STATIC = 0x0008;
	public static final int FINAL = 0x0010;

	public static final int VOLATILE = 0x0040;
	public static final int TRANSIENT = 0x0080;
	public static final int VAR_ARGS = 0x0080;
	public static final int NATIVE = 0x0100;
	public static final int ABSTRACT = 0x0400;
	public static final int STRICTFP = 0x0800;

	public static final int SYNCHRONIZED = 0x0020;
	
	public static final int SUPER = 0x0020;
	public static final int INTERFACE = 0x0200;
	public static final int ANNOTATION = 0x2000;
	public static final int ENUM = 0x4000;
	
	public static final boolean isInterface( final int flags ) {
		return isSet( flags, INTERFACE );
	}
	
	public static final boolean isEnum( final int flags ) {
		return isSet( flags, ENUM );
	}
	
	public static final boolean isAnnotation( final int flags ) {
		return isSet( flags, ANNOTATION );
	}
	
	public static final boolean isClass( final int flags ) {
		return ! isInterface( flags ) &&
			! isAnnotation( flags ) &&
			! isEnum( flags );
	}
	
	public static final boolean isAbstract( final int flags ) {
		return isSet( flags, ABSTRACT );
	}
	
	public static final boolean isNative( final int flags ) {
		return isSet( flags, NATIVE );
	}
	
	public static final boolean isStatic( final int flags ) {
		return isSet( flags, STATIC );
	}
	
	public static final boolean isPublic( final int flags ) {
		return isSet( flags, PUBLIC );
	}
	
	public static final boolean isProtected( final int flags ) {
		return isSet( flags, PROTECTED );
	}
	
	public static final boolean isPrivate( final int flags ) {
		return isSet( flags, PRIVATE );
	}
	
	public static final boolean isDefault( final int flags ) {
		return ! isSet( flags, PUBLIC | PROTECTED | PRIVATE );
	}
	
	private static final boolean isSet( final int flags, final int bit ) {
		return ( ( flags & bit ) != 0 );		
	}
	
	private Flags() {}
}
