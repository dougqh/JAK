package net.dougqh.jak.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( {
	CodeGenStructuralTest.class,
	CodeGenPackageTest.class,
	CodeGenFieldsTest.class,
	CodeGenModifiersTest.class,
	CodeGenConstantTest.class,
	CodeGenGenericsTest.class,
	CodeGenArithmeticOpsTest.class,
	CodeGenCastTest.class,
	CodeGenInvocationTest.class,
	CodeGenLoadAndStoreTest.class,
	CodeGenBranchTest.class,
	CodeGenExceptionsTest.class,
	CodeGenArraysTest.class,
	CodeGenSynchronizationTest.class,
	CodeGenMacrosTest.class,
	CodeGenClassLoaderTest.class,
	CodeGenAnnotationsTest.class,
	CodeGenStackManipulation.class } )
public class CodeGenApiTestSuite {}
