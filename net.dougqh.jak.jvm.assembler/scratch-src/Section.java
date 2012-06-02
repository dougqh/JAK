
public abstract class Section {
	private SourceWriter writer = null;
	
	final void write( final SourceWriter writer ) {
		this.writer = writer;
		try {
			this.write();
		} finally {
			this.writer = null;
		}
	}
	
	public abstract void write();
	
	protected final void writeln() {
		this.writer.writeln();
	}
	
	protected final void writeln(final String format, final Object... args) {
		this.writer.writeln(format, args);
	}
}