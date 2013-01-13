package net.dougqh.jak.disassembler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.List;

import net.dougqh.jak.Flags;
import net.dougqh.java.meta.types.JavaTypes;

public abstract class JvmType implements JavaType {
	public static final JvmType create( final File file )
		throws IOException
	{
		FileInputStream in = new FileInputStream( file );
		try {
			return create( in );
		} finally {
			in.close();
		}
	}
	
	public static final JvmType create( final byte[] bytes ) {
		try {
			return create( new ByteArrayInputStream( bytes ) );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	public static final JvmType create( final InputStream in )
		throws IOException
	{
		JvmTypeInternals typeReader = new JvmTypeInternals( in );
		int flags = typeReader.flags();
		//DQH - Order is important here - annotations are interfaces
		if ( Flags.isAnnotation( flags ) ) {
			return new JvmAnnotation( typeReader );
		} else if ( Flags.isEnum( flags ) ) {
			return new JvmEnum( typeReader );
		} else if ( Flags.isInterface( flags ) ) {
			return new JvmInterface( typeReader );
		} else {
			return new JvmClass( typeReader );
		}
	}
	
	protected final JvmTypeInternals type;
	
	protected JvmType( final JvmTypeInternals type ) {
		this.type = type;
	}
	
	@Override
	public final String getName() {
		return this.type.getClassName();
	}
	
	@Override
	public final Type getParentType() {
		return JavaTypes.objectTypeName( this.type.getSuperName() );
	}
	
	@Override
	public final int getFlags() {
		return this.type.flags();
	}
	
	@Override
	public final boolean isPublic() {
		return Flags.isPublic( this.type.flags() );
	}
	
	@Override
	public final boolean isDefault() {
		return Flags.isDefault( this.type.flags() );
	}
	
	@Override
	public final boolean isProtected() {
		return Flags.isProtected( this.type.flags() );
	}
	
	@Override
	public final boolean isPrivate() {
		return Flags.isPrivate( this.type.flags() );
	}
	
	@Override
	public final List< Type > getInterfaces() {
		return new TypeList( this.type.getInterfaceNames() );
	}
	
	public final JvmMethodSet getAllMethods() {
		return new JvmMethodSet(this.type.getAllMethods());
	}
	
	public final JvmMethod getMethod(final String name) {
		return this.getAllMethods().get(name);
	}
	
	private static final class TypeList extends AbstractList< Type > {
		private final List< String > names;
		
		TypeList( final List< String > names ) {
			this.names = names;
		}
		
		@Override
		public final int size() {
			return this.names.size();
		}
		
		@Override
		public final Type get( final int index ) {
			return JavaTypes.objectTypeName( this.names.get( index ) );
		}
	}
}
