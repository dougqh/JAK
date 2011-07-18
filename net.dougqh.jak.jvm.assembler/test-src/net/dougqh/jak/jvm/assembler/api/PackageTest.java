package net.dougqh.jak.jvm.assembler.api;

import static net.dougqh.jak.Jak.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmInterfaceWriter;
import net.dougqh.jak.jvm.assembler.JvmPackageWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class PackageTest {
	private static final Package PACKAGE = PackageTest.class.getPackage();
	
	public final @Test void defineClass() {
		JvmPackageWriter packageWriter = new JvmWriter().define( package_( "foo.bar" ) );
		
		JvmClassWriter classWriter = packageWriter.define( final_().class_( "Foo" ) );
		
		Class< ? > aClass = classWriter.load();
		assertEquals( "foo.bar", aClass.getPackage().getName() );
	}
	
	public final @Test void defineInterface() {
		JvmPackageWriter packageWriter = new JvmWriter().define( package_( "foo.bar" ) );
		
		JvmInterfaceWriter interfaceWriter = packageWriter.define( interface_( "Bar" ) );
		
		Class< ? > aClass = interfaceWriter.load();
		assertEquals( "foo.bar", aClass.getPackage().getName() );
	}
	
	public final @Test void defineClassWithPreexistingPackage() {
		JvmClassWriter classWriter = new JvmWriter().define( class_( PACKAGE, "Foo" ) );
		
		Class< ? > aClass = classWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}
	
	public final @Test void defineInterfaceWithPreexistingPackage() {
		JvmInterfaceWriter interfaceWriter = new JvmWriter().define( interface_( PACKAGE, "Bar" ) );
		
		Class< ? > aClass = interfaceWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}	
	
	public final @Test void defineClassWithPreexistingPackage2() {
		JvmClassWriter classWriter = new JvmWriter().define( public_().class_( PACKAGE, "Foo" ) );
		
		Class< ? > aClass = classWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}
	
	public final @Test void defineInterfaceWithPreexistingPackage2() {
		JvmInterfaceWriter interfaceWriter = new JvmWriter().define( public_().interface_( PACKAGE, "Bar" ) );
		
		Class< ? > aClass = interfaceWriter.load();
		assertEquals( PACKAGE, aClass.getPackage() );
	}
}
