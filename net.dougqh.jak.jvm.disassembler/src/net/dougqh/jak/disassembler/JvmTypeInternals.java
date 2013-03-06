package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

final class JvmTypeInternals {
	private static final String NOT_CLASS_MESSAGE = "Not a Java class";
	
	private static final byte CA = (byte)0xCA;
	private static final byte FE = (byte)0xFE;
	private static final byte BA = (byte)0xBA;
	private static final byte BE = (byte)0xBE;
	
	private final int minorVersion;
	private final int majorVersion;

	private final ConstantPool constantPool;
	
	private final int thisClass;
	private final int superClass;
	
	private final Interfaces interfaces;
	private final Fields fields;
	private final Methods methods;
	private final Attributes attributes;
	
	private final int flags;
	
	JvmTypeInternals( final InputStream in ) throws IOException {
		this( new JvmInputStream( in ) );
	}
	
	JvmTypeInternals( final JvmInputStream in ) throws IOException {
		readMagic( in );
		this.minorVersion = in.u2();
		this.majorVersion = in.u2();
		
		this.constantPool = new ConstantPool(in);

		this.flags = in.u2();
		
		this.thisClass = in.u2();
		this.superClass = in.u2();
		
		this.interfaces = new Interfaces(this.constantPool, in);
		this.fields = new Fields(this.constantPool, in);
		this.methods = new Methods(this.constantPool, in);
		this.attributes = new Attributes(this.constantPool, in);
		
		//TODO: DQH - restore assertion
		//in.assertDone();
	}
	
	final int flags() {
		return this.flags;
	}
	
	final String getClassName() {
		return this.constantPool.typeName( this.thisClass );
	}
	
	final String getSuperName() {
		return this.constantPool.typeName( this.superClass );
	}
	
	final List<String> getInterfaceNames() {
		return this.interfaces.getNames();
	}
	
	final List<JvmField> getFields() {
		return this.fields.getFields();
	}
	
	final JvmMethod getClassInitializer() {
		return this.methods.getClassInitializer();
	}
	
	final List<JvmMethod> getConstructors() {
		return this.methods.getConstructors();
	}
	
	final List<JvmMethod> getMethods() {
		return this.methods.getMethods();
	}
	
	final List<? extends JvmMethod> getAllMethods() {
		return this.methods.getAllMethods();
	}
	
	private static final void readMagic( final JvmInputStream in )
		throws IOException
	{
		byte ca = in.u1();
		byte fe = in.u1();
		byte ba = in.u1();
		byte be = in.u1();
		
		if ( ca != CA ||
			fe != FE ||
			ba != BA ||
			be != BE )
		{
			throw new ClassFileFormatException( NOT_CLASS_MESSAGE );
		}
	}
}
