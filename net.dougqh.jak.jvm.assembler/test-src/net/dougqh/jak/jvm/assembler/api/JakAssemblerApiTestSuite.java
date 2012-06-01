package net.dougqh.jak.jvm.assembler.api;

import net.dougqh.jak.jvm.assembler.macros.api.MacrosTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( {
	StructuralTest.class,
	PackageTest.class,
	FieldsTest.class,
	ModifiersTest.class,
	ConstantTest.class,
	GenericsTest.class,
	ArithmeticOpsTest.class,
	CastTest.class,
	InvocationTest.class,
	LoadAndStoreTest.class,
	BranchTest.class,
	ExceptionsTest.class,
	ArraysTest.class,
	SynchronizationTest.class,
	MacrosTest.class,
	ClassLoaderTest.class,
	AnnotationsTest.class,
	EnumTest.class,
	JakTypeStackTest.class,
	StackManipulationTest.class,
	MacrosTestSuite.class } )
public class JakAssemblerApiTestSuite {}
