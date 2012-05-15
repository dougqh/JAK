package net.dougqh.jak.jvm.assembler.macros;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static net.dougqh.jak.assembler.JakAsm.*;
import net.dougqh.jak.assembler.JakCondition;
import net.dougqh.jak.jvm.assembler.JvmMacro;

public final class IfConstruct extends JvmMacro {
	private final JakCondition ifCondition;
	private final stmt ifBody;
	private final List< ElseIf > elseIfs = new ArrayList< ElseIf >( 4 );
	private stmt elseBody = null;
	
	public IfConstruct(
		final JakCondition condition,
		final stmt body )
	{
		this.ifCondition = condition;
		this.ifBody = body;
	}
	
	public final IfConstruct addElseIf(
		final JakCondition condition,
		final stmt body )
	{
		this.elseIfs.add( new ElseIf( condition, body ) );
		return this;
	}
	
	public final IfConstruct addElse( final stmt body ) {
		this.elseBody = body;
		return this;
	}
	
	@Override
	protected final boolean defer() {
		//DQH - The code writing below needs to inject goto-s in the case that 
		//if construct is not the last piece of code in the method.
		//However, those same jumps will jump to inaccessible point if the 
		//if construct is the last piece of code in the method.
		//To solve this, if rendering is deferred until we know if the if construct
		//is terminal.
		return true;
	}
	
	@Override
	protected void write() {
		//DQH - Note this algorithm orders if/else if/else constructs in
		//natural order, unlike the algorithm that javac uses which orders
		//them in reverse natural order.
		
		//Overall, this approach results in longer byte-code.
		//May consider revisiting at a later date.
		
		startLabelScope();
		try {
			int elseIndex = 0;
			if_( not( this.ifCondition ), "else" + elseIndex );

			startScope();
			macro( this.ifBody );
			if ( ! this.terminal() && this.elseIfs.isEmpty() && this.elseBody == null ) {
				goto_( "endif" );
			}
			endScope();
			
			for ( Iterator< ElseIf > elseIfIter = this.elseIfs.iterator();
				elseIfIter.hasNext(); )
			{
				ElseIf elseIf = elseIfIter.next();
			
				label( "else" + ( elseIndex++ ) );
				if_( not( elseIf.condition ), "else" + elseIndex );
				
				startScope();
				macro( elseIf.stmt );
				if ( ! this.terminal() && this.elseBody != null || elseIfIter.hasNext() ) {
					goto_( "endif" );
				}
				endScope();
			}
			
			//DQH - regardless of whether there is an else, generate
			//a label to act as the target of the if.
			label( "else" + elseIndex );
			if ( this.elseBody != null ) {
				startScope();
				macro( this.elseBody );
				endScope();
			}
			label( "endif" );
		} finally {
			endLabelScope();
		}
	}
	
	protected static final class ElseIf {
		final JakCondition condition;
		final stmt stmt;
		
		ElseIf( final JakCondition condition, final stmt body ) {
			this.condition = condition;
			this.stmt = body;
		}
	}
}
