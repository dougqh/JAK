import java.io.PrintStream;


public class SourceWriter {
	private final PrintStream printStream;
	private int indent = 0;
	
	public SourceWriter() {
		this( System.out );
	}
	
	public SourceWriter( final PrintStream printStream ) {
		this.printStream = printStream;
	}
	
	public final void indent() {
		++this.indent;
	}
	
	public final void writeln() {
		this.writeln("");
	}
	
	public final void writeln(final String format, final Object... args) {
		for ( int i = 0; i < this.indent; ++i ) {
			this.printStream.append("    ");
		}
		this.printStream.append(String.format(format + "%n", args));
	}
	
	public final void indent( final Section section ) {
		this.indent();
		try {
			section.write( this );
		} finally {
			this.unindent();
		}
	}
	
	public final void unindent() {
		--this.indent;
	}
}
