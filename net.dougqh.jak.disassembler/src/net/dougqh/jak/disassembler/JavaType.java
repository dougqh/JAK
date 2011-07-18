package net.dougqh.jak.disassembler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.dougqh.jak.core.Flags;

public abstract class JavaType {
	public static final JavaType create( final File file )
		throws IOException
	{
		FileInputStream in = new FileInputStream( file );
		try {
			return create( in );
		} finally {
			in.close();
		}
	}
	
	public static final JavaType create( final byte[] bytes ) {
		try {
			return create( new ByteArrayInputStream( bytes ) );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	public static final JavaType create( final InputStream in )
		throws IOException
	{
		TypeInternals typeReader = new TypeInternals( in );
		int flags = typeReader.flags();
		//DQH - Order is important here - annotations are interfaces
		if ( Flags.isAnnotation( flags ) ) {
			return new JavaAnnotation( typeReader );
		} else if ( Flags.isEnum( flags ) ) {
			return new JavaEnum( typeReader );
		} else if ( Flags.isInterface( flags ) ) {
			return new JavaInterface( typeReader );
		} else {
			return new JavaClass( typeReader );
		}
	}
	
	protected final TypeInternals type;
	
	protected JavaType( final TypeInternals type ) {
		this.type = type;
	}
	
	public final String getName() {
		return this.type.getClassName();
	}
	
	public final String getSuperName() {
		return this.type.getSuperName();
	}
	
	public final List< String > getInterfaceNames() {
		return this.type.getInterfaceNames();
	}
	
	public final List< JavaMethod > getAllMethods() {
		return this.type.getAllMethods();
	}
}
