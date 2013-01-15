package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;

public abstract class BaseJvmOperationProcessor implements JvmOperationProcessor {
	@Override
	public void nop() {}

	@Override
	public void aconst_null() {}

	@Override
	public void iconst_m1() {}

	@Override
	public void iconst_0() {}

	@Override
	public void iconst_1() {}

	@Override
	public void iconst_2() {}

	@Override
	public void iconst_3() {}

	@Override
	public void iconst_4() {}

	@Override
	public void iconst_5() {}

	@Override
	public void lconst_0() {}

	@Override
	public void lconst_1() {}

	@Override
	public void fconst_0() {}

	@Override
	public void fconst_1() {}

	@Override
	public void fconst_2() {}

	@Override
	public void dconst_0() {}

	@Override
	public void dconst_1() {}

	@Override
	public void bipush(byte value) {}

	@Override
	public void sipush(short value) {}

	@Override
	public void ldc(Type aClass) {}

	@Override
	public void ldc(int value) {}

	@Override
	public void ldc(float value) {}

	@Override
	public void ldc(String value) {}

	@Override
	public void ldc_w(Type aClass) {}

	@Override
	public void ldc_w(int value) {}

	@Override
	public void ldc_w(float value) {}

	@Override
	public void ldc_w(String value) {}

	@Override
	public void ldc2_w(long value) {}

	@Override
	public void ldc2_w(double value) {}

	@Override
	public void iload(int slot) {}

	@Override
	public void iload(Slot slot) {}

	@Override
	public void iload_0() {}

	@Override
	public void iload_1() {}

	@Override
	public void iload_2() {}

	@Override
	public void iload_3() {}

	@Override
	public void lload(int slot) {}

	@Override
	public void lload(Slot slot) {}

	@Override
	public void lload_0() {}

	@Override
	public void lload_1() {}

	@Override
	public void lload_2() {}

	@Override
	public void lload_3() {}

	@Override
	public void fload(int slot) {}

	@Override
	public void fload(Slot slot) {}

	@Override
	public void fload_0() {}

	@Override
	public void fload_1() {}

	@Override
	public void fload_2() {}

	@Override
	public void fload_3() {}

	@Override
	public void dload(int slot) {}

	@Override
	public void dload(Slot slot) {}

	@Override
	public void dload_0() {}

	@Override
	public void dload_1() {}

	@Override
	public void dload_2() {}

	@Override
	public void dload_3() {}

	@Override
	public void aload(int slot) {}

	@Override
	public void aload(Slot slot) {}

	@Override
	public void aload_0() {}

	@Override
	public void aload_1() {}

	@Override
	public void aload_2() {}

	@Override
	public void aload_3() {}

	@Override
	public void iaload() {}

	@Override
	public void laload() {}

	@Override
	public void faload() {}

	@Override
	public void daload() {}

	@Override
	public void aaload() {}

	@Override
	public void baload() {}

	@Override
	public void caload() {}

	@Override
	public void saload() {}

	@Override
	public void istore(int index) {}

	@Override
	public void istore(Slot slot) {}

	@Override
	public void istore_0() {}

	@Override
	public void istore_1() {}

	@Override
	public void istore_2() {}

	@Override
	public void istore_3() {}

	@Override
	public void lstore(int slot) {}

	@Override
	public void lstore(Slot slot) {}

	@Override
	public void lstore_0() {}

	@Override
	public void lstore_1() {}

	@Override
	public void lstore_2() {}

	@Override
	public void lstore_3() {}

	@Override
	public void fstore(int slot) {}

	@Override
	public void fstore(Slot slot) {}

	@Override
	public void fstore_0() {}

	@Override
	public void fstore_1() {}

	@Override
	public void fstore_2() {}

	@Override
	public void fstore_3() {}

	@Override
	public void dstore(int slot) {}

	@Override
	public void dstore(Slot slot) {}

	@Override
	public void dstore_0() {}

	@Override
	public void dstore_1() {}

	@Override
	public void dstore_2() {}

	@Override
	public void dstore_3() {}

	@Override
	public void astore(int slot) {}

	@Override
	public void astore(Slot slot) {}

	@Override
	public void astore_0() {}

	@Override
	public void astore_1() {}

	@Override
	public void astore_2() {}

	@Override
	public void astore_3() {}

	@Override
	public void iastore() {}

	@Override
	public void lastore() {}

	@Override
	public void fastore() {}

	@Override
	public void dastore() {}

	@Override
	public void aastore() {}

	@Override
	public void bastore() {}

	@Override
	public void castore() {}

	@Override
	public void sastore() {}

	@Override
	public void pop() {}

	@Override
	public void pop2() {}

	@Override
	public void dup() {}

	@Override
	public void dup_x1() {}

	@Override
	public void dup_x2() {}

	@Override
	public void dup2() {}

	@Override
	public void dup2_x1() {}

	@Override
	public void dup2_x2() {}

	@Override
	public void swap() {}

	@Override
	public void iadd() {}

	@Override
	public void ladd() {}

	@Override
	public void fadd() {}

	@Override
	public void dadd() {}

	@Override
	public void isub() {}

	@Override
	public void lsub() {}

	@Override
	public void fsub() {}

	@Override
	public void dsub() {}

	@Override
	public void imul() {}

	@Override
	public void lmul() {}

	@Override
	public void fmul() {}

	@Override
	public void dmul() {}

	@Override
	public void idiv() {}

	@Override
	public void ldiv() {}

	@Override
	public void fdiv() {}

	@Override
	public void ddiv() {}

	@Override
	public void irem() {}

	@Override
	public void lrem() {}

	@Override
	public void frem() {}

	@Override
	public void drem() {}

	@Override
	public void ineg() {}

	@Override
	public void lneg() {}

	@Override
	public void fneg() {}

	@Override
	public void dneg() {}

	@Override
	public void ishl() {}

	@Override
	public void lshl() {}

	@Override
	public void ishr() {}

	@Override
	public void lshr() {}

	@Override
	public void iushr() {}

	@Override
	public void lushr() {}

	@Override
	public void iand() {}

	@Override
	public void land() {}

	@Override
	public void ior() {}

	@Override
	public void lor() {}

	@Override
	public void ixor() {}

	@Override
	public void lxor() {}

	@Override
	public void iinc(int slot, int amount) {}

	@Override
	public void i2l() {}

	@Override
	public void i2f() {}

	@Override
	public void i2d() {}

	@Override
	public void l2i() {}

	@Override
	public void l2f() {}

	@Override
	public void l2d() {}

	@Override
	public void f2i() {}

	@Override
	public void f2l() {}

	@Override
	public void f2d() {}

	@Override
	public void d2i() {}

	@Override
	public void d2l() {}

	@Override
	public void d2f() {}

	@Override
	public void i2b() {}

	@Override
	public void i2c() {}

	@Override
	public void i2s() {}

	@Override
	public void lcmp() {}

	@Override
	public void fcmpl() {}

	@Override
	public void fcmpg() {}

	@Override
	public void dcmpl() {}

	@Override
	public void dcmpg() {}

	@Override
	public void ireturn() {}

	@Override
	public void lreturn() {}

	@Override
	public void freturn() {}

	@Override
	public void dreturn() {}

	@Override
	public void areturn() {}

	@Override
	public void return_() {}

	@Override
	public void getstatic(Type targetType, JavaField field) {}

	@Override
	public void putstatic(Type targetType, JavaField field) {}

	@Override
	public void getfield(Type targetType, JavaField field) {}

	@Override
	public void putfield(Type targetType, JavaField field) {}

	@Override
	public void invokevirtual(Type targetType, JavaMethodDescriptor method) {}

	@Override
	public void invokeinterface(Type targetType, JavaMethodDescriptor method) {}

	@Override
	public void invokestatic(Type targetType, JavaMethodDescriptor method) {}

	@Override
	public void invokespecial(Type targetType, JavaMethodDescriptor method) {}

	@Override
	public void new_(Type type) {}

	@Override
	public void newarray(Type componentType) {}

	@Override
	public void anewarray(Type componentType) {}

	@Override
	public void arraylength() {}

	@Override
	public void athrow() {}

	@Override
	public void checkcast(Type type) {}

	@Override
	public void instanceof_(Type type) {}

	@Override
	public void monitorenter() {}

	@Override
	public void monitorexit() {}

	@Override
	public void multianewarray(Type arrayType, int numDimensions) {}

	@Override
	public void ifnull(Jump jump) {}

	@Override
	public void ifnonnull(Jump jump) {}

	@Override
	public void ifeq(Jump jump) {}

	@Override
	public void ifne(Jump jump) {}

	@Override
	public void iflt(Jump jump) {}

	@Override
	public void ifgt(Jump jump) {}

	@Override
	public void ifge(Jump jump) {}

	@Override
	public void ifle(Jump jump) {}

	@Override
	public void if_icmpeq(Jump jump) {}

	@Override
	public void if_icmpne(Jump jump) {}

	@Override
	public void if_icmplt(Jump jump) {}

	@Override
	public void if_icmpgt(Jump jump) {}

	@Override
	public void if_icmpge(Jump jump) {}

	@Override
	public void if_icmple(Jump jump) {}

	@Override
	public void if_acmpeq(Jump jump) {}

	@Override
	public void if_acmpne(Jump jump) {}

	@Override
	public void goto_(Jump jump) {}

	@Override
	public void prepare() {
	}

	@Override
	public void handleException(ExceptionHandler exceptionHandler) {
	}
}
