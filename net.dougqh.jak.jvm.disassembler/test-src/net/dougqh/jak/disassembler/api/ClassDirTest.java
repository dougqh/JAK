package net.dougqh.jak.disassembler.api;

import java.io.File;
import java.io.IOException;

import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmReader;
import net.dougqh.jak.disassembler.JvmType;
import net.dougqh.jak.disassembler.JvmTypeSet;
import net.dougqh.jak.disassembler.Tests;

import org.junit.Test;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public final class ClassDirTest {
	private static final File CLASS_DIR = Tests.dir("classdir");
	
	@Test
	public final void retrieval() throws IOException {
		JvmReader reader = new JvmReader().addDir(CLASS_DIR);
		
		JvmClass annotatedClass = (JvmClass)reader.read("AnnotatedClass");
		assertThat( annotatedClass.getName(), is("AnnotatedClass") );
	}
	
	@Test
	public final void retrievalInPackage() throws IOException {
		JvmReader reader = new JvmReader().addDir(CLASS_DIR);
		
		JvmClass barClass = (JvmClass)reader.read("foo.bar.Bar");
		assertThat( barClass.getName(), is("foo.bar.Bar") );
	}
	
	@Test
	public final void iteration() throws IOException {
		JvmReader reader = new JvmReader().addDir(CLASS_DIR);
		
		int count = 0;
		for ( JvmType type : reader.classes() ) {
			count += 1;
		}
		assertThat( count, is(40) );
	}
	
	@Test
	public final void filtering() throws IOException {
		JvmReader reader = new JvmReader().addDir(CLASS_DIR);
		
		JvmTypeSet publicTypes = reader.classes().filter(public_());
		
		int count = 0;
		for ( JvmType type: publicTypes ) {
			count += 1;
		}
		assertThat( count, is(36) );
	}
}
