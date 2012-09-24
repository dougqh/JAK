package net.dougqh.jak.repl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationRewriter;
import net.dougqh.jak.jvm.JvmOperationRewritingFilter;
import net.dougqh.jak.jvm.assembler.ConstantEntry;
import net.dougqh.jak.jvm.assembler.JvmCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmTypeWriter;
import net.dougqh.jak.jvm.optimizers.BinaryConstantFolding;
import net.dougqh.jak.jvm.optimizers.UnaryConstantFolding;
import net.dougqh.jak.jvm.rewriters.Normalizer;
import net.dougqh.reflection.Delegate;

final class ReplRecorder {
	private List< Method > methods = new ArrayList< Method >( 32 );
	private List< Object[] > args = new ArrayList< Object[] >( 32 );
	
	private List< Method > checkpointMethods;
	private List< Object[] > checkpointArgs;
	
	ReplRecorder() {
		this.checkpoint();
	}
	
	final void checkpoint() {
		this.checkpointMethods = new ArrayList< Method >( this.methods );
		this.checkpointArgs = new ArrayList< Object[] >( this.args );
	}
	
	final void rollback() {
		this.methods = checkpointMethods;
		this.args = checkpointArgs;
	}
	
	final int size() {
		return this.methods.size();
	}
	
	final void reset() {
		this.methods.clear();
		this.args.clear();
	}
	
	final void record( final Method method, final Object[] args ) {
		this.methods.add( method );
		this.args.add( args );
	}
	
	final void replay(
		final JvmTypeWriter typeWriter,
		final JvmCodeWriter codeWriter )
	{
		Iterator< Method > methodIter = this.methods.iterator();
		Iterator< Object[] > argsIter = this.args.iterator();

		while ( methodIter.hasNext() ) {
			Method method = methodIter.next();
			Object[] args = argsIter.next();
			
			if ( isLdc( method ) ) {
				ldc( codeWriter, args );
			} else {
				invoke( codeWriter, method, args );
			}
		}
	}
	
	private static final boolean isLdc( final Method method ) {
		return method.getName().equals( "ldc" ) ||
			method.getName().equals( "ldc_w" ) ||
			method.getName().equals( "ldc2_w" );
	}
	
	private static final void ldc(
		final JvmCodeWriter codeWriter,
		final Object... args )
	{
		ConstantEntry entry = (ConstantEntry)args[ 0 ];
		Type type = entry.type();
		Object value = entry.value();
		
		if ( type.equals( int.class ) ) {
			codeWriter.ldc( (Integer)value );
		} else if ( type.equals( float.class ) ) {
			codeWriter.ldc( (Float)value );
		} else if ( type.equals( long.class ) ) {
			codeWriter.ldc2_w( (Long)value );
		} else if ( type.equals( double.class ) ) {
			codeWriter.ldc2_w( (Double)value );
		} else if ( type.equals( String.class ) ) {
			codeWriter.ldc( (CharSequence)value );
		} else if ( type.equals( Class.class ) ) {
			codeWriter.ldc( (Class< ? >)value );
		} else {
			throw new IllegalStateException();
		}
	}
	
	private static final void invoke(
		final JvmCodeWriter codeWriter,
		final Method method,
		final Object... args )
	{
		try {
			method.invoke( codeWriter.coreWriter(), args );
		} catch ( IllegalArgumentException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		} catch ( InvocationTargetException e ) {
			throw new IllegalStateException( e );
		}	
	}
	
	final void list( final ReplConsole console ) {
		Iterator< Method > methodIter = this.methods.iterator();
		Iterator< Object[] > argsIter = this.args.iterator();
		
		while ( methodIter.hasNext() ) {
			Method method = methodIter.next();
			Object[] args = argsIter.next();
			
			if ( args.length != 0 ) {
				console.print( method, args );
			} else {
				console.append( method.getName() ).endl();
			}
		}
	}
	
	final void optimize() {
		this.optimize( new Normalizer() );
		this.optimize( new UnaryConstantFolding() );
		this.optimize( new BinaryConstantFolding() );
	}
	
	final void optimize( final JvmOperationRewriter rewriter ) {
		OptimizationRecorder optimizationRecorder = new OptimizationRecorder();
		
		JvmOperationRewritingFilter optimizer = new JvmOperationRewritingFilter(
			optimizationRecorder.getProxy() );
		optimizer.set( rewriter );

		Iterator< Method > methodIter = this.methods.iterator();
		Iterator< Object[] > argsIter = this.args.iterator();
			
		while ( methodIter.hasNext() ) {
			Method method = methodIter.next();
			Object[] args = argsIter.next();
			
			try {
				method.invoke( optimizer, args );
			} catch ( IllegalAccessException e ) {
				throw new IllegalStateException( e );
			} catch ( InvocationTargetException e ) {
				throw new IllegalStateException( e );
			}
		}
		
		this.methods = optimizationRecorder.methods;
		this.args = optimizationRecorder.args;
	}
	
	static final class OptimizationRecorder extends Delegate< JvmCoreCodeWriter > {
		private final List< Method > methods = new ArrayList< Method >();
		private final List< Object[] > args = new ArrayList< Object[] >();
		
		public OptimizationRecorder() {
			super( JvmCoreCodeWriter.class );
		}
		
		@Override
		protected final Object invoke(
			final Method interfaceMethod,
			final Object[] args )
			throws Throwable
		{
			this.methods.add( interfaceMethod );
			this.args.add( args );
			
			//op processor methods all return void
			return null;
		}
		
		private static final boolean isCoreWritingMethod( final Method method ) {
			return method.getDeclaringClass().equals( JvmOperationProcessor.class );
		}
	}
}
