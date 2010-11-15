package net.dougqh.jak.assembler.api;

import static net.dougqh.jak.assembler.JavaAssembler.*;
import static org.junit.Assert.*;
import net.dougqh.jak.assembler.JavaClassWriter;
import net.dougqh.jak.assembler.JavaInterfaceWriter;
import net.dougqh.jak.assembler.JavaPackageWriter;
import net.dougqh.jak.assembler.JavaWriter;

import org.junit.Test;

public final class PackageTest {
	private static final Package PACKAGE = PackageTest.class.getPackage();
	
	public final @Test void defineClass() {
		JavaPackageWriter packageWriter = new JavaWriter().define( package_( "foo.bar" ) );
		
		JavaClassWriter classWriter = packageWriter.define( final_().class_( "Foo" ) );
		
		Class< ? > aClass = classWriter.load();
		assertEquals( "foo.bar", aClass.getPackage().getName() );
	}
	
	public final @Test void defineInterface() {
		JavaPackageWriter packageWriter = new JavaWriter().define( package_( "foo.bar" ) );
		
		JavaInterfaceWriter interfaceWriter = packageWriter.define( interface_( "Bar" ) );
		
		Class< ? > aClass = interfaceWriter.load();
		assertEquals( "foo.bar", aClass.getPackage().getName() );
	}
	
	public final @Test void defineClassWithPreexistingPackage() {
		JavaClassWriter classWriter = new JavaWriter().define( class_( PACKAGE, "Foo" ) );
		
		Class< ? > aClass = classWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}
	
	public final @Test void defineInterfaceWithPreexistingPackage() {
		JavaInterfaceWriter interfaceWriter = new JavaWriter().define( interface_( PACKAGE, "Bar" ) );
		
		Class< ? > aClass = interfaceWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}	
	
	public final @Test void defineClassWithPreexistingPackage2() {
		JavaClassWriter classWriter = new JavaWriter().define( public_().class_( PACKAGE, "Foo" ) );
		
		Class< ? > aClass = classWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}
	
	public final @Test void defineInterfaceWithPreexistingPackage2() {
		JavaInterfaceWriter interfaceWriter = new JavaWriter().define( public_().interface_( PACKAGE, "Bar" ) );
		
		Class< ? > aClass = interfaceWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}
}
