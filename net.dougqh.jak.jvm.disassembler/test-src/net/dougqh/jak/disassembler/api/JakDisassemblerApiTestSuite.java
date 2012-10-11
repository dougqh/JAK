package net.dougqh.jak.disassembler.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( {
	BasicTest.class,
	JarTest.class,
	ClassDirTest.class,
	NonTrivialClassTest.class
} )
public final class JakDisassemblerApiTestSuite {}
