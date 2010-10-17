package net.dougqh.jak.repl;

final class ReplConfig {	
	private boolean echo = true;
	
	public final boolean echo() {
		return this.echo;
	}
	
	public final void echo( final boolean isOn ) {
		this.echo = isOn;
	}
}
