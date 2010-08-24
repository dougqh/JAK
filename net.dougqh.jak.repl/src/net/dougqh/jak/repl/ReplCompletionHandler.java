package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

import jline.CompletionHandler;
import jline.ConsoleReader;

public final class ReplCompletionHandler implements CompletionHandler {
	@Override
	public final boolean complete(
		final ConsoleReader arg0,
		final List arg1,
		final int arg2 )
		throws IOException
	{
		return false;
	}
}
