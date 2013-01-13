package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.JvmOperationHydrator;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;
import net.dougqh.jak.jvm.operations.JvmOperation;
import static net.dougqh.jak.jvm.operations.JvmOperation.*;

final class CodeAttribute {
	private final ConstantPool constantPool;
	
	private final int maxStack;
	private final int maxLocals;
	
	private final int codeLength;
	private final JvmInputStream codeIn;
	
	CodeAttribute(
		final ConstantPool constantPool,
		final JvmInputStream in )
		throws IOException
	{
		this.constantPool = constantPool;
		
		this.maxStack = in.u2();
		this.maxLocals = in.u2();
		
		this.codeLength = in.u4();
		this.codeIn = in.readSubStream(codeLength);
		this.codeIn.enableReset();
		
	//	u2 exception_table_length;
	//	{    	u2 start_pc;
	//	      	u2 end_pc;
	//	      	u2  handler_pc;
	//	      	u2  catch_type;
	//	}	exception_table[exception_table_length];
	//	u2 attributes_count;
	//	attribute_info attributes[attributes_count];
	}
	
	final void process( final JvmOperationProcessor processor ) {
		try {
			Decoder decoder = new Decoder(this.constantPool, this.codeIn);
			while ( decoder.hasNext() ) {
				decoder.read(processor);
			}
		} catch ( IOException e ) {
			throw new DisassemblerException(e);
		}
	}
	
	final Iterable< JvmOperation > operations() {
		return new IterableImpl(new Decoder(this.constantPool, this.codeIn));
	}
	
	final int length() {
		return this.codeLength;
	}
	
	final int maxStack() {
		return this.maxStack;
	}
	
	final int maxLocals() {
		return this.maxLocals;
	}
	
	private static final class Decoder {
		private final ConstantPool constantPool;
		private final JvmInputStream codeIn;
		
		private int opPos;
		
		Decoder( final ConstantPool constantPool, final JvmInputStream codeIn ) {
			this.constantPool = constantPool;
			this.codeIn = codeIn;
			this.codeIn.reset();
			
			this.opPos = 0;
		}
		
		final boolean hasNext() {
			return ! this.codeIn.isEof();
		}
		
		final void read( final JvmOperationProcessor processor ) throws IOException {
			this.opPos = this.codeIn.pos();
			if ( processor instanceof JvmOperationProcessor.PositionAware ) {
				((JvmOperationProcessor.PositionAware)processor).pos(opPos);
			}
			
			byte op = this.codeIn.u1();
			switch ( op ) {
				case NOP:
				processor.nop();
				break;

				case ACONST_NULL:
				processor.aconst_null();
				break;

				case ICONST_M1:
				processor.iconst_m1();
				break;

				case ICONST_0:
				processor.iconst_0();
				break;

				case ICONST_1:
				processor.iconst_1();
				break;

				case ICONST_2:
				processor.iconst_2();
				break;

				case ICONST_3:
				processor.iconst_3();
				break;

				case ICONST_4:
				processor.iconst_4();
				break;

				case ICONST_5:
				processor.iconst_5();
				break;

				case LCONST_0:
				processor.lconst_0();
				break;

				case LCONST_1:
				processor.lconst_1();
				break;

				case FCONST_0:
				processor.fconst_0();
				break;

				case FCONST_1:
				processor.fconst_1();
				break;

				case FCONST_2:
				processor.fconst_2();
				break;

				case DCONST_0:
				processor.dconst_0();
				break;

				case DCONST_1:
				processor.dconst_1();
				break;

				case BIPUSH:
				processor.bipush( this.readByte() );
				break;

				case SIPUSH:
				processor.sipush( this.readShort() );
				break;

				case LDC:
				throw new IllegalStateException("unimplemented");
				//processor.ldc( this.readConstant() );
				//break;

				case LDC_W:
				throw new IllegalStateException("unimplemented");
				//processor.ldc_w( this.readConstantWide() );
				//break;

				case LDC2_W:
				throw new IllegalStateException("unimplemented");
				//processor.ldc2_w( this.readConstant2Wide() );
				//break;

				case ILOAD:
				processor.iload( this.readIndex() );
				break;

				case LLOAD:
				processor.lload( this.readIndex() );
				break;

				case FLOAD:
				processor.fload( this.readIndex() );
				break;

				case DLOAD:
				processor.dload( this.readIndex() );
				break;

				case ALOAD:
				processor.aload( this.readIndex() );
				break;

				case ILOAD_0:
				processor.iload_0();
				break;

				case ILOAD_1:
				processor.iload_1();
				break;

				case ILOAD_2:
				processor.iload_2();
				break;

				case ILOAD_3:
				processor.iload_3();
				break;

				case LLOAD_0:
				processor.lload_0();
				break;

				case LLOAD_1:
				processor.lload_1();
				break;

				case LLOAD_2:
				processor.lload_2();
				break;

				case LLOAD_3:
				processor.lload_3();
				break;

				case FLOAD_0:
				processor.fload_0();
				break;

				case FLOAD_1:
				processor.fload_1();
				break;

				case FLOAD_2:
				processor.fload_2();
				break;

				case FLOAD_3:
				processor.fload_3();
				break;

				case DLOAD_0:
				processor.dload_0();
				break;

				case DLOAD_1:
				processor.dload_1();
				break;

				case DLOAD_2:
				processor.dload_2();
				break;

				case DLOAD_3:
				processor.dload_3();
				break;

				case ALOAD_0:
				processor.aload_0();
				break;

				case ALOAD_1:
				processor.aload_1();
				break;

				case ALOAD_2:
				processor.aload_2();
				break;

				case ALOAD_3:
				processor.aload_3();
				break;

				case IALOAD:
				processor.iaload();
				break;

				case LALOAD:
				processor.laload();
				break;

				case FALOAD:
				processor.faload();
				break;

				case DALOAD:
				processor.daload();
				break;

				case AALOAD:
				processor.aaload();
				break;

				case BALOAD:
				processor.baload();
				break;

				case CALOAD:
				processor.caload();
				break;

				case SALOAD:
				processor.saload();
				break;

				case ISTORE:
				processor.istore( this.readIndex() );
				break;

				case LSTORE:
				processor.lstore( this.readIndex() );
				break;

				case FSTORE:
				processor.fstore( this.readIndex() );
				break;

				case DSTORE:
				processor.dstore( this.readIndex() );
				break;

				case ASTORE:
				processor.astore( this.readIndex() );
				break;

				case ISTORE_0:
				processor.istore_0();
				break;

				case ISTORE_1:
				processor.istore_1();
				break;

				case ISTORE_2:
				processor.istore_2();
				break;

				case ISTORE_3:
				processor.istore_3();
				break;

				case LSTORE_0:
				processor.lstore_0();
				break;

				case LSTORE_1:
				processor.lstore_1();
				break;

				case LSTORE_2:
				processor.lstore_2();
				break;

				case LSTORE_3:
				processor.lstore_3();
				break;

				case FSTORE_0:
				processor.fstore_0();
				break;

				case FSTORE_1:
				processor.fstore_1();
				break;

				case FSTORE_2:
				processor.fstore_2();
				break;

				case FSTORE_3:
				processor.fstore_3();
				break;

				case DSTORE_0:
				processor.dstore_0();
				break;

				case DSTORE_1:
				processor.dstore_1();
				break;

				case DSTORE_2:
				processor.dstore_2();
				break;

				case DSTORE_3:
				processor.dstore_3();
				break;

				case ASTORE_0:
				processor.astore_0();
				break;

				case ASTORE_1:
				processor.astore_1();
				break;

				case ASTORE_2:
				processor.astore_2();
				break;

				case ASTORE_3:
				processor.astore_3();
				break;

				case IASTORE:
				processor.iastore();
				break;

				case LASTORE:
				processor.lastore();
				break;

				case FASTORE:
				processor.fastore();
				break;

				case DASTORE:
				processor.dastore();
				break;

				case AASTORE:
				processor.aastore();
				break;

				case BASTORE:
				processor.bastore();
				break;

				case CASTORE:
				processor.castore();
				break;

				case SASTORE:
				processor.sastore();
				break;

				case POP:
				processor.pop();
				break;

				case POP2:
				processor.pop2();
				break;

				case DUP:
				processor.dup();
				break;

				case DUP_X1:
				processor.dup_x1();
				break;

				case DUP_X2:
				processor.dup_x2();
				break;

				case DUP2:
				processor.dup2();
				break;

				case DUP2_X1:
				processor.dup2_x1();
				break;

				case DUP2_X2:
				processor.dup2_x2();
				break;

				case SWAP:
				processor.swap();
				break;

				case IADD:
				processor.iadd();
				break;

				case LADD:
				processor.ladd();
				break;

				case FADD:
				processor.fadd();
				break;

				case DADD:
				processor.dadd();
				break;

				case ISUB:
				processor.isub();
				break;

				case LSUB:
				processor.lsub();
				break;

				case FSUB:
				processor.fsub();
				break;

				case DSUB:
				processor.dsub();
				break;

				case IMUL:
				processor.imul();
				break;

				case LMUL:
				processor.lmul();
				break;

				case FMUL:
				processor.fmul();
				break;

				case DMUL:
				processor.dmul();
				break;

				case IDIV:
				processor.idiv();
				break;

				case LDIV:
				processor.ldiv();
				break;

				case FDIV:
				processor.fdiv();
				break;

				case DDIV:
				processor.ddiv();
				break;

				case IREM:
				processor.irem();
				break;

				case LREM:
				processor.lrem();
				break;

				case FREM:
				processor.frem();
				break;

				case DREM:
				processor.drem();
				break;

				case INEG:
				processor.ineg();
				break;

				case LNEG:
				processor.lneg();
				break;

				case FNEG:
				processor.fneg();
				break;

				case DNEG:
				processor.dneg();
				break;

				case ISHL:
				processor.ishl();
				break;

				case LSHL:
				processor.lshl();
				break;

				case ISHR:
				processor.ishr();
				break;

				case LSHR:
				processor.lshr();
				break;

				case IUSHR:
				processor.iushr();
				break;

				case LUSHR:
				processor.lushr();
				break;

				case IAND:
				processor.iand();
				break;

				case LAND:
				processor.land();
				break;

				case IOR:
				processor.ior();
				break;

				case LOR:
				processor.lor();
				break;

				case IXOR:
				processor.ixor();
				break;

				case LXOR:
				processor.lxor();
				break;

				case IINC:
				processor.iinc( this.readIndex(), this.u1() );
				break;

				case I2L:
				processor.i2l();
				break;

				case I2F:
				processor.i2f();
				break;

				case I2D:
				processor.i2d();
				break;

				case L2I:
				processor.l2i();
				break;

				case L2F:
				processor.l2f();
				break;

				case L2D:
				processor.l2d();
				break;

				case F2I:
				processor.f2i();
				break;

				case F2L:
				processor.f2l();
				break;

				case F2D:
				processor.f2d();
				break;

				case D2I:
				processor.d2i();
				break;

				case D2L:
				processor.d2l();
				break;

				case D2F:
				processor.d2f();
				break;

				case I2B:
				processor.i2b();
				break;

				case I2C:
				processor.i2c();
				break;

				case I2S:
				processor.i2s();
				break;

				case LCMP:
				processor.lcmp();
				break;

				case FCMPL:
				processor.fcmpl();
				break;

				case FCMPG:
				processor.fcmpg();
				break;

				case DCMPL:
				processor.dcmpl();
				break;

				case DCMPG:
				processor.dcmpg();
				break;

				case IFEQ:
				processor.ifeq(this.readRelativeJump());
				break;

				case IFNE:
				processor.ifne(this.readRelativeJump());
				break;

				case IFLT:
				processor.iflt(this.readRelativeJump());
				break;

				case IFGE:
				processor.ifge(this.readRelativeJump());
				break;

				case IFGT:
				processor.ifgt(this.readRelativeJump());
				break;

				case IFLE:
				processor.ifle(this.readRelativeJump());
				break;

				case IF_ICMPEQ:
				processor.if_icmpeq(this.readRelativeJump());
				break;

				case IF_ICMPNE:
				processor.if_icmpne(this.readRelativeJump());
				break;

				case IF_ICMPLT:
				processor.if_icmplt(this.readRelativeJump());
				break;

				case IF_ICMPGE:
				processor.if_icmpge(this.readRelativeJump());
				break;

				case IF_ICMPGT:
				processor.if_icmpgt(this.readRelativeJump());
				break;

				case IF_ICMPLE:
				processor.if_icmple(this.readRelativeJump());
				break;

				case IF_ACMPEQ:
				processor.if_acmpeq(this.readRelativeJump());
				break;

				case IF_ACMPNE:
				processor.if_acmpne(this.readRelativeJump());
				break;

				case GOTO:
				processor.goto_(this.readRelativeJump());
				break;

				case JSR:
				throw new IllegalStateException("unsupported");
				//TODO: Support this at some point
				//processor.jsr();
				//break;

				case RET:
				throw new IllegalStateException("unsupported");
				//TODO: Support this at some point
				//processor.ret();
				//break;

				case TABLESWITCH:
				throw new IllegalStateException("unsupported");
				//TODO: Support this at some point
				//processor.tableswitch();
				//break;

				case LOOKUPSWITCH:
				throw new IllegalStateException("unsupported");
				//TODO: Support this at some point
				//processor.lookupswitch();
				//break;

				case IRETURN:
				processor.ireturn();
				break;

				case LRETURN:
				processor.lreturn();
				break;

				case FRETURN:
				processor.freturn();
				break;

				case DRETURN:
				processor.dreturn();
				break;

				case ARETURN:
				processor.areturn();
				break;

				case RETURN:
				processor.return_();
				break;

				case GETSTATIC: {
					int refIndex = this.readIndex();
					processor.getstatic(
						this.constantPool.targetType(refIndex),
						this.constantPool.field(refIndex));
					break;
				}

				case PUTSTATIC: {
					int refIndex = this.readIndex();
					processor.putstatic(
						this.constantPool.targetType(refIndex),
						this.constantPool.field(refIndex));
					break;
				}

				case GETFIELD: {
					int refIndex = this.readIndex();
					processor.getfield(
						this.constantPool.targetType(refIndex),
						this.constantPool.field(refIndex));
					break;
				}

				case PUTFIELD: {
					int refIndex = this.readIndex();
					processor.putfield(
						this.constantPool.targetType(refIndex),
						this.constantPool.field(refIndex));
					break;
				}

				case INVOKEVIRTUAL: {
					int refIndex = this.readIndex();
					processor.invokevirtual(
						this.constantPool.targetType(refIndex),
						this.constantPool.methodDescriptor(refIndex));
					break;
				}

				case INVOKESPECIAL: {
					int refIndex = this.readIndex();
					processor.invokespecial(
						this.constantPool.targetType(refIndex),
						this.constantPool.methodDescriptor(refIndex));
					break;
				}

				case INVOKESTATIC: {
					int refIndex = this.readIndex();
					processor.invokestatic(
						this.constantPool.targetType(refIndex),
						this.constantPool.methodDescriptor(refIndex));
					break;
				}

				case INVOKEINTERFACE: {
					int refIndex = this.readIndex();
					processor.invokeinterface(
						this.constantPool.targetType(refIndex),
						this.constantPool.methodDescriptor(refIndex));
					break;
				}

				case NEW:
				processor.new_( this.readType() );
				break;

				case NEWARRAY:
				processor.newarray( this.readArrayType() );
				break;

				case ANEWARRAY:
				processor.anewarray( this.readType() );
				break;

				case ARRAYLENGTH:
				processor.arraylength();
				break;

				case ATHROW:
				processor.athrow();
				break;

				case CHECKCAST:
				processor.checkcast( this.readType() );
				break;

				case INSTANCEOF:
				processor.instanceof_( this.readType() );
				break;

				case MONITORENTER:
				processor.monitorenter();
				break;

				case MONITOREXIT:
				processor.monitorexit();
				break;

				case WIDE:
				//processor.wide();
				break;

				case MULTIANEWARRAY:
				processor.multianewarray( this.readArrayType(), this.u1() );
				break;

				case IFNULL:
				processor.ifnull(this.readRelativeJump());
				break;

				case IFNONNULL:
				processor.ifnonnull(this.readRelativeJump());
				break;

				case GOTO_W:
				//TODO: Implement this later
				//processor.goto_w();
				break;

				case JSR_W:
				//TODO: Implement this later
				//processor.jsr_w();
				break;
			}
		}
		
		private final int u1() throws IOException {
			return this.codeIn.u1();
		}
		
		private final short u2() throws IOException {
			return this.codeIn.u2();
		}
		
		private final byte readByte() throws IOException {
			return this.codeIn.u1();
		}
		
		private final short readShort() throws IOException {
			return this.codeIn.u2();
		}
		
		private final int readIndex() throws IOException {
			return this.codeIn.u2();
		}
		
		private final Object readConstant() throws IOException {
			return null;
		}
		
		private final Object readConstantWide() throws IOException {
			return null;
		}
		
		private final Object readConstant2Wide() throws IOException {
			return null;
		}
		
		private final Jump readRelativeJump() throws IOException {
			final int opPos = this.opPos;
			final int relativeOffset = this.codeIn.u2();
			
			return new Jump() {
				@Override
				public final Integer pos() {
					return opPos + relativeOffset;
				}
				
				@Override
				public final String toString() {
					if ( relativeOffset < 0 ) {
						return String.valueOf(relativeOffset);
					} else {
						return "+" + relativeOffset;
					}
				}
			};
		}
		
		private final Type readType() throws IOException {
			int index = this.readIndex();
			return this.constantPool.targetType(index);
		}
		
		private final Type readArrayType() throws IOException {
			return null;
		}
	}
	
	private static final class IterableImpl implements Iterable<JvmOperation> {
		private final Decoder decoder;
		
		IterableImpl(final Decoder decoder) {
			this.decoder = decoder;
		}
		
		@Override
		public final Iterator<JvmOperation> iterator() {
			return new IteratorImpl(this.decoder);
		}
	}
	
	private static final class IteratorImpl implements Iterator<JvmOperation> {
		private final Decoder decoder;
		private final JvmOperationHydratorImpl hydrator;
		
		IteratorImpl(final Decoder decoder) {
			this.decoder = decoder;
			this.hydrator = new JvmOperationHydratorImpl();
		}
		
		@Override
		public final boolean hasNext() {
			return this.decoder.hasNext();
		}
		
		@Override
		public final JvmOperation next() {
			try {
				this.decoder.read(this.hydrator);
				return this.hydrator.operation;
			} catch (IOException e) {
				throw new DisassemblerException(e);
			}
		}
		
		@Override
		public final void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private static final class JvmOperationHydratorImpl extends JvmOperationHydrator {
		JvmOperation operation;
		
		@Override
		protected final void add(final JvmOperation operation) {
			this.operation = operation;
		}
	}
}
