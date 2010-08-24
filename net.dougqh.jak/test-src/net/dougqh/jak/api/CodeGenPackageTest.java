package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;
import net.dougqh.jak.JavaClassWriter;
import net.dougqh.jak.JavaInterfaceWriter;
import net.dougqh.jak.JavaPackageWriter;

import org.junit.Test;

public final class CodeGenPackageTest {
	private static final Package PACKAGE = CodeGenPackageTest.class.getPackage();
	
	public final @Test void defineClass() {
		JavaPackageWriter packageWriter = define( package_( "foo.bar" ) );
		
		JavaClassWriter classWriter = packageWriter.define( final_().class_( "Foo" ) );
		
		Class< ? > aClass = classWriter.load();
		assertEquals( "foo.bar", aClass.getPackage().getName() );
	}
	
	public final @Test void defineInterface() {
		JavaPackageWriter packageWriter = define( package_( "foo.bar" ) );
		
		JavaInterfaceWriter interfaceWriter = packageWriter.define( interface_( "Bar" ) );
		
		Class< ? > aClass = interfaceWriter.load();
		assertEquals( "foo.bar", aClass.getPackage().getName() );
	}
	
	public final @Test void defineClassWithPreexistingPackage() {
		JavaClassWriter classWriter = define( class_( PACKAGE, "Foo" ) );
		
		Class< ? > aClass = classWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}
	
	public final @Test void defineInterfaceWithPreexistingPackage() {
		JavaInterfaceWriter interfaceWriter = define( interface_( PACKAGE, "Bar" ) );
		
		Class< ? > aClass = interfaceWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}	
	
	public final @Test void defineClassWithPreexistingPackage2() {
		JavaClassWriter classWriter = define( public_().class_( PACKAGE, "Foo" ) );
		
		Class< ? > aClass = classWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}
	
	public final @Test void defineInterfaceWithPreexistingPackage2() {
		JavaInterfaceWriter interfaceWriter = define( public_().interface_( PACKAGE, "Bar" ) );
		
		Class< ? > aClass = interfaceWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}
}
