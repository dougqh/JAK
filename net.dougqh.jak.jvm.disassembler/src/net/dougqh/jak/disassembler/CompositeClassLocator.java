package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.dougqh.iterable.CompositeIterable;
import net.dougqh.iterable.TransformIterable;

final class CompositeClassLocator implements ClassLocator {
	private final ArrayList< ClassLocator > locators = new ArrayList< ClassLocator >( 8 );
	
	final void add( final ClassLocator locator ) {
		this.locators.add( locator );
	}
	
	final boolean isEmpty() {
		return this.locators.isEmpty();
	}
	
	@Override
	public final Iterable< InputStream > list() throws IOException {
		Iterable< Iterable< InputStream > > iterables = 
			new TransformIterable< ClassLocator, Iterable< InputStream > >( this.locators ) {
				protected final Iterable< InputStream > transform( final ClassLocator classLocator ) {
					try {
						return classLocator.list();
					} catch ( IOException e ) {
						throw new IllegalStateException( e );
					}
				}
			};
		
		return new CompositeIterable< InputStream >( iterables );
	}
	
	@Override
	public final InputStream load( final String className ) throws IOException {
		for ( ClassLocator locator : this.locators ) {
			InputStream in = locator.load( className );
			if ( in != null ) {
				return in;
			}
		}
		return null;
	}
}
