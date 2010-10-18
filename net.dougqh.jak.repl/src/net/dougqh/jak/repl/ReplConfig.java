package net.dougqh.jak.repl;

final class ReplConfig {	
	private boolean echo = true;
	private boolean includeSynthetic = true;
	
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
}
