package net.dougqh.jak.repl;

final class ReplConfig {	
	private boolean echo = true;
	private boolean includeSynthetic = true;
	private boolean autoRun = true;
	private boolean autoShowLocals = true;
	private boolean autoShowStack = true;
	
	public final boolean echo() {
		return this.echo;
	}
	
	public final void echo( final boolean isOn ) {
		this.echo = isOn;
	}
	
	public final boolean includeSynthetic() {
		return this.includeSynthetic;
	}
	
	public final void includeSynthetic( final boolean isOn ) {
		this.includeSynthetic = isOn;
	}
	
	public final boolean autoRun() {
		return this.autoRun;
	}
	
	public final void autoRun( final boolean isOn ) {
		this.autoRun = isOn;
	}
	
	public final void autoShowLocals( final boolean isOn ) {
		this.autoShowLocals = isOn;
	}
	
	public final boolean autoShowLocals() {
		return this.autoShowLocals;
	}
	
	public final void autoShowStack( final boolean isOn ) {
		this.autoShowStack = isOn;
	}
	
	public final boolean autoShowStack() {
		return this.autoShowStack;
	}
}
