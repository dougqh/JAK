package net.dougqh.iterable;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamProvider {
	public InputStream open() throws IOException;
}
