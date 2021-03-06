package net.dougqh.jak.jvm.assembler.api;

import static net.dougqh.jak.Jak.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class InvocationTest {
	public final @Test void invokeStatic() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "InvokeStatic" ).implements_( Function.class ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		classWriter.define(
			public_().final_().method( void.class, "invoke" ).args( Object.class ) ).
			invoke( Static.class, "call" ).
			return_();
		
		Function function = classWriter.< Function >newInstance();
		function.invoke( null );
		
		assertTrue( Static.called );
	}
	
	public final @Test void invokeInterface() throws Exception {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "InvokeInterface" ).implements_( Function.class ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		classWriter.define(
			public_().final_().method( void.class, "invoke" ).args( Object.class ) ).
			aload_1().
			checkcast( Interface.class ).
			invoke( Interface.class, "call" ).
			return_();
		
		InterfaceImpl impl = new InterfaceImpl();
		
		Function function = classWriter.< Function >newInstance();
		function.invoke( impl );
		
		assertTrue( impl.called );
	}
	
	public final @Test void invokeVirtual() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "InvokeVirtual" ).implements_( Function.class ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		classWriter.define(
			public_().final_().method( void.class, "invoke" ).args( Object.class ) ).
			aload_1().
			checkcast( Class.class ).
			invoke( Class.class, "call" ).
			return_();
		
		ClassImpl impl = new ClassImpl();
		
		Function function = classWriter.newInstance();
		function.invoke( impl );
		
		assertTrue( impl.called );
	}
	
	public final @Test void newObject() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "NewObject" ).implements_( Function.class ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		JvmCodeWriter codeWriter = classWriter.define( 
			public_().final_().method( void.class, "invoke" ).args( Object.class ) ).
			new_( New.class ).
			invokespecial( New.class, init() ).
			return_();
		
		assertEquals( 1, codeWriter.stackMonitor().maxStack() );
			
		Function function = classWriter.< Function >newInstance();
		function.invoke( null );
		
		assertTrue( New.called );
	}
	
	public static interface Function {
		public abstract void invoke( final Object object );
	}
	
	public static final class Static {
		private static boolean called = false;
		
		public static final void call() {
			called = true;
		}
	}
	
	public static interface Interface {
		public abstract void call();
	}
	
	public static final class InterfaceImpl implements Interface {
		private boolean called = false;
		
		@Override
		public final void call() {
			this.called = true;
		}
	}
	
	public static abstract class Class {
		public abstract void call();
	}
	
	public static final class ClassImpl extends Class {
		private boolean called = false;
		
		@Override
		public final void call() {
			this.called = true;
		}
	}
	
	public static final class New {
		private static boolean called = false;
		
		public New() {
			called = true;
		}
	}
}
