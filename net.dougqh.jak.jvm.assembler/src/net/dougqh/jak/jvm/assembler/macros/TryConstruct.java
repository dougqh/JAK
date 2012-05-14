package net.dougqh.jak.jvm.assembler.macros;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.dougqh.jak.jvm.assembler.JvmMacro;

public final class TryConstruct extends JvmMacro {
	//TODO: Replace with a proper "any" type
	private static final Class<?> any_ = Throwable.class;
	
	private final stmt tryBody;
	private final List< Catch > catches = new ArrayList< Catch >( 4 );
	private stmt finallyBody = null;
	
	public TryConstruct( final stmt tryBody ) {
		this.tryBody = tryBody;
	}
	
	@Override
	protected final boolean defer() {
		return true;
	}
	
	public final TryConstruct addCatch(
		final Type exceptionType, final String var,
		final stmt body )
	{
		this.catches.add( new Catch( exceptionType, var, body ) );
		return this;
	}
	
	public final TryConstruct addFinally( final stmt body ) {
		this.finallyBody = body;
		return this;
	}
	
	@Override
	protected final void write() {
		startLabelScope();
		try {
			boolean hasFinally = ( this.finallyBody != null );
			
			section( "try", "endTry", null, this.tryBody );
			
			for ( ListIterator< Catch > catchIter = this.catches.listIterator();
				catchIter.hasNext(); )
			{
				int catchIndex = catchIter.nextIndex();
				Catch catch_ = catchIter.next();
				
				catch_( "try", "endTry", catch_.exceptionType );
				section( "catch" + catchIndex, "endCatch" + catchIndex, catch_.var, catch_.body );
			}
			
			if ( hasFinally ) {
				catch_( "try", "endTry", any_ );
				for ( int i = 0; i < this.catches.size(); ++i ) {
					catch_( "catch" + i, "endCatch" + i, Throwable.class );
				}
				startScope();
				try {
					adeclare( "tempException" );
					astore( "tempException" );
					macro( this.finallyBody );
					athrow( "tempException" );
				} finally {
					endScope();
				}
			}
			label( "endTryCatchFinally" );
		} finally {
			endLabelScope();
		}
	}
	
	private final void section( 
		final String startLabel,
		final String endLabel,
		final String exceptionVar,
		final stmt stmt )
	{
		boolean hasFinally = ( this.finallyBody != null );
		startScope();
		try {
			if ( hasFinally ) {
				trapReturn();
			}

			label( startLabel );
			if ( exceptionVar != null ) {
				adeclare( exceptionVar );
				astore( exceptionVar );
			}
			macro( stmt );
			label( endLabel );
			
			if ( hasFinally ) {
				macro( this.finallyBody );
				releaseReturn();
			}
			
			if ( ! this.terminal() ) {
				goto_( "endTryCatchFinally" );
			}
		} finally {
			endScope();
		}
	}
	
	protected static final class Catch {
		final Type exceptionType;
		final String var;
		final stmt body;
		
		Catch( final Type exceptionType, final String var, final stmt body ) {
			this.exceptionType = exceptionType;
			this.var = var;
			this.body = body;
		}
	}
}
