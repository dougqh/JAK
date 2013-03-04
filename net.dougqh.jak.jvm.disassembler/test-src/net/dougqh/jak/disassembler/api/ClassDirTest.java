package net.dougqh.jak.disassembler.api;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmReader;
import net.dougqh.jak.disassembler.JvmType;
import net.dougqh.jak.disassembler.Tests;

import org.junit.Test;

public final class ClassDirTest {
	private static final File CLASS_DIR = Tests.dir("classdir");
	
	public final @Test void testRetrieval() throws IOException {
		JvmReader reader = new JvmReader().addDir(CLASS_DIR);
		
		JvmClass annotatedClass = (JvmClass)reader.read("AnnotatedClass");
		assertThat( annotatedClass.getName(), is("AnnotatedClass") );
	}
	
	public final @Test void testRetrievalInPackage() throws IOException {
		JvmReader reader = new JvmReader().addDir(CLASS_DIR);
		
		JvmClass barClass = (JvmClass)reader.read("foo.bar.Bar");
		assertThat( barClass.getName(), is("foo.bar.Bar") );
	}
	
	public final @Test void testIteration() throws IOException {
		JvmReader reader = new JvmReader().addDir(CLASS_DIR);
		
		int count = 0;
		for ( JvmType type : reader.classes() ) {
			count += 1;
		}
		assertThat( count, is( 40 ) );
	}
}
