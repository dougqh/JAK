package net.dougqh.jak.assembler.api;

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
	JakTypeStackTest.class,
	StackManipulationTest.class } )
public class PublicApiTestSuite {}
