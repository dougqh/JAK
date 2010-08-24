package net.dougqh.jak;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;

import java.io.PrintStream;

import org.junit.Test;

public final class JavaCodeWriterTest {
	public final @Test void localSize() {
//		assertEquals(
//			5,
//			JavaCoreCodeWriterImpl.size( FormalArguments.instance(
//				boolean.class,
//				byte.class,
//				char.class,
//				short.class,
//				int.class ) ) );	
//		
//		JavaCodeWriter floatWriter = new JavaCodeWriter(
//			new ConstantPool( dummy ),
//			virtual_,
//			FormalArguments.instance( float.class, float.class ) );		
//		assertEquals( 3, floatWriter.maxLocals() );
//		
//		JavaCodeWriter longWriter = new JavaCodeWriter(
//			new ConstantPool( dummy ),
//			static_,
//			FormalArguments.instance( long.class, long.class ) );
//		assertEquals( 4, longWriter.maxLocals() );
//		
//		JavaCodeWriter doubleWriter = new JavaCodeWriter(
//			new ConstantPool( dummy ),
//			virtual_,
//			FormalArguments.instance( double.class, double.class, double.class ) );
//		assertEquals( 7, doubleWriter.maxLocals() );
//		
//		JavaCodeWriter objectWriter = new JavaCodeWriter(
//			new ConstantPool( dummy ),
//			static_,
//			FormalArguments.instance( String.class, Throwable.class, Object.class ) );
//		assertEquals( 3, objectWriter.maxLocals() );
//		
//		JavaCodeWriter mixedWriter = new JavaCodeWriter(
//			new ConstantPool( dummy ),
//			virtual_,
//			FormalArguments.instance( String.class, int.class, long.class ) );
//		assertEquals( 5, mixedWriter.maxLocals() );
	}	
	
	public final @Test void invokespecial() {
		ConstantPool constantPool = new ConstantPool();
		JavaCoreCodeWriterImpl writer = new JavaCoreCodeWriterImpl( constantPool, 0 );
		
		writer.getstatic( System.class, field( PrintStream.class, "out" ) );
		assertEquals( 3, writer.pos() );
		
		writer.ldc( (byte)constantPool.addStringConstant( "Hello World" ) );
		assertEquals( 5, writer.pos() );
		
		writer.invokevirtual(
			PrintStream.class,
			method( void.class, "println", String.class ) );
		assertEquals( 8, writer.pos() );
		
		writer.return_();
		assertEquals( 9, writer.codeLength() );
		
		assertEquals( 9, writer.pos() );
		assertEquals( 2, writer.maxStack() );
		assertEquals( 0, writer.maxLocals() );
		
		assertEquals( 0, writer.curStack() );
	}
	
	public final @Test void invokeConstructor() {
		JavaCoreCodeWriterImpl writer = new JavaCoreCodeWriterImpl( new ConstantPool(), 0 );
		
		writer.
			new_( Object.class ).
			invokespecial( Object.class, init() ).
			return_();
		
		assertEquals( 7, writer.codeLength() );
		assertEquals( 0, writer.maxLocals() );
		assertEquals( 1, writer.maxStack() );
		
		assertEquals( 0, writer.curStack() );
	}
}
