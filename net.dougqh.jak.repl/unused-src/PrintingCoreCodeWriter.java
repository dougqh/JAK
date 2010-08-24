package net.dougqh.jak.repl;

import java.io.IOException;
import java.lang.reflect.Type;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.java.meta.types.JavaTypes;

final class PrintingCoreCodeWriter implements JavaCoreCodeWriter {
	public static final String NOP = "nop";
	public static final String ACONST_NULL = "aconst_null";
	
	public static final String ICONST_M1 = "iconst_m1";
	public static final String ICONST_0 = "iconst_0";
	public static final String ICONST_1 = "iconst_1";
	public static final String ICONST_2 = "iconst_2";
	public static final String ICONST_3 = "iconst_3";
	public static final String ICONST_4 = "iconst_4";
	public static final String ICONST_5 = "iconst_5";
	
	public static final String LCONST_0 = "lconst_0";
	public static final String LCONST_1 = "lconst_1";
	
	public static final String FCONST_0 = "fconst_0";
	public static final String FCONST_1 = "fconst_1";
	public static final String FCONST_2 = "fconst_2";
	
	public static final String DCONST_0 = "dconst_0";
	public static final String DCONST_1 = "dconst_1";
	
	public static final String BIPUSH = "bipush";
	public static final String SIPUSH = "sipush";
	
	public static final String LDC = "ldc";
	public static final String LDC_W = "ldc_w";	
	public static final String LDC2_W = "ldc2_w";
	
	public static final String ILOAD = "iload";
	public static final String LLOAD = "lload";
	public static final String FLOAD = "fload";
	public static final String DLOAD = "dload";
	public static final String ALOAD = "aload";
	
	public static final String ILOAD_0 = "iload_0";
	public static final String ILOAD_1 = "iload_1";
	public static final String ILOAD_2 = "iload_2";
	public static final String ILOAD_3 = "iload_3";
	
	public static final String LLOAD_0 = "lload_0";
	public static final String LLOAD_1 = "lload_1";
	public static final String LLOAD_2 = "lload_2";
	public static final String LLOAD_3 = "lload_3";
	
	public static final String FLOAD_0 = "fload_0";
	public static final String FLOAD_1 = "fload_1";
	public static final String FLOAD_2 = "fload_2";
	public static final String FLOAD_3 = "fload_3";
	
	public static final String DLOAD_0 = "dload_0";
	public static final String DLOAD_1 = "dload_1";
	public static final String DLOAD_2 = "dload_2";
	public static final String DLOAD_3 = "dload_3";
	
	public static final String ALOAD_0 = "aload_0";
	public static final String ALOAD_1 = "aload_1";
	public static final String ALOAD_2 = "aload_2";
	public static final String ALOAD_3 = "aload_3";
	
	public static final String IALOAD = "iaload";
	public static final String LALOAD = "laload";
	public static final String FALOAD = "faload";
	public static final String DALOAD = "daload";
	public static final String AALOAD = "aaload";
	public static final String BALOAD = "baload";
	public static final String CALOAD = "caload";
	public static final String SALOAD = "saload";
	
	public static final String ISTORE = "istore";
	public static final String LSTORE = "lstore";
	public static final String FSTORE = "fstore";
	public static final String DSTORE = "dstore";
	public static final String ASTORE = "astore";
	
	public static final String ISTORE_0 = "istore_0";
	public static final String ISTORE_1 = "istore_1";
	public static final String ISTORE_2 = "istore_2";
	public static final String ISTORE_3 = "istore_3";
	
	public static final String LSTORE_0 = "lstore_0";
	public static final String LSTORE_1 = "lstore_1";
	public static final String LSTORE_2 = "lstore_2";
	public static final String LSTORE_3 = "lstore_3";
	
	public static final String FSTORE_0 = "fstore_0";
	public static final String FSTORE_1 = "fstore_1";
	public static final String FSTORE_2 = "fstore_2";
	public static final String FSTORE_3 = "fstore_3";
	
	public static final String DSTORE_0 = "dstore_0";
	public static final String DSTORE_1 = "dstore_1";
	public static final String DSTORE_2 = "dstore_2";
	public static final String DSTORE_3 = "dstore_3";
	
	public static final String ASTORE_0 = "astore_0";
	public static final String ASTORE_1 = "astore_1";
	public static final String ASTORE_2 = "astore_2";
	public static final String ASTORE_3 = "astore_3";
	
	public static final String IASTORE = "iastore";
	public static final String LASTORE = "lastore";
	public static final String FASTORE = "fastore";
	public static final String DASTORE = "dastore";
	public static final String AASTORE = "aastore";
	public static final String BASTORE = "bastore";
	public static final String CASTORE = "castore";
	public static final String SASTORE = "sastore";
	
	public static final String POP = "pop";
	public static final String POP2 = "pop2";
	
	public static final String DUP = "dup";
	public static final String DUP_X1 = "dup_x1";
	public static final String DUP_X2 = "dup_x2";
	public static final String DUP2 = "dup2";
	public static final String DUP2_X1 = "dup2_x1";
	public static final String DUP2_X2 = "dup2_x2";
	
	public static final String SWAP = "swap";
	
	public static final String IADD = "iadd";
	public static final String LADD = "ladd";
	public static final String FADD = "fadd";
	public static final String DADD = "dadd";
	
	public static final String ISUB = "isub";
	public static final String LSUB = "lsub";
	public static final String FSUB = "fsub";
	public static final String DSUB = "dsub";
	
	public static final String IMUL = "imul";
	public static final String LMUL = "lmul";
	public static final String FMUL = "fmul";
	public static final String DMUL = "dmul";
	
	public static final String IDIV = "idiv";
	public static final String LDIV = "ldiv";
	public static final String FDIV = "fdiv";
	public static final String DDIV = "ddiv";
	
	public static final String IREM = "irem";
	public static final String LREM = "lrem";
	public static final String FREM = "frem";
	public static final String DREM = "drem";
	
	public static final String INEG = "ineg";
	public static final String LNEG = "lneg";
	public static final String FNEG = "fneg";
	public static final String DNEG = "dneg";
	
	public static final String ISHL = "ishl";
	public static final String LSHL = "lshl";
	
	public static final String ISHR = "ishr";
	public static final String LSHR = "lshr";
	
	public static final String IUSHR = "iushr";
	public static final String LUSHR = "lushr";
	
	public static final String IAND = "iand";
	public static final String LAND = "land";
	
	public static final String IOR = "ior";
	public static final String LOR = "lor";
	
	public static final String IXOR = "ixor";
	public static final String LXOR = "lxor";
	
	public static final String IINC = "iinc";
	
	public static final String I2L = "i2l";
	public static final String I2F = "i2f";
	public static final String I2D = "i2d";
	
	public static final String L2I = "l2i";
	public static final String L2F = "l2f";
	public static final String L2D = "l2d";
	
	public static final String F2I = "f2i";
	public static final String F2L = "f2l";
	public static final String F2D = "f2d";
	
	public static final String D2I = "d2i";
	public static final String D2L = "d2l";
	public static final String D2F = "d2f";
	
	public static final String I2B = "i2b";
	public static final String I2C = "i2c";
	public static final String I2S = "i2s";
	
	public static final String LCMP = "lcmp";
	public static final String FCMPL = "fcmpl";
	public static final String FCMPG = "fcmpg";
	public static final String DCMPL = "dcmpl";
	public static final String DCMPG = "dcmpg";
	
	public static final String IFEQ = "ifeq";
	public static final String IFNE = "ifne";
	public static final String IFLT = "iflt";
	public static final String IFGE = "ifge";
	public static final String IFGT = "ifgt";
	public static final String IFLE = "ifle";
	
	public static final String IF_ICMPEQ = "if_icmpeq";
	public static final String IF_ICMPNE = "if_cmpne";
	public static final String IF_ICMPLT = "if_icmplt";
	public static final String IF_ICMPGE = "if_icmpge";
	public static final String IF_ICMPGT = "if_icmpgt";
	public static final String IF_ICMPLE = "if_icmple";
	
	public static final String IF_ACMPEQ = "if_acmpeq";
	public static final String IF_ACMPNE = "if_acmpne";
	
	public static final String GOTO = "goto";
	public static final String JSR = "jsr";
	public static final String RET = "ret";
	
	public static final String TABLESWITCH = "tableswitch";
	public static final String LOOKUPSWITCH = "lookupswitch";
	
	public static final String IRETURN = "ireturn";
	public static final String LRETURN = "lreturn";
	public static final String FRETURN = "freturn";
	public static final String DRETURN = "dreturn";
	public static final String ARETURN = "areturn";
	public static final String RETURN = "return";
	
	public static final String GETSTATIC = "getstatic";
	public static final String PUTSTATIC = "putstatic";
	public static final String GETFIELD = "getfield";
	public static final String PUTFIELD = "putfield";
	
	public static final String INVOKEVIRTUAL = "invokevirtual";
	public static final String INVOKESPECIAL = "invokespecial";
	public static final String INVOKESTATIC = "invokestatic";
	public static final String INVOKEINTERFACE = "invokeinterface";
	
	public static final String NEW = "new";
	public static final String NEWARRAY = "newarray";
	public static final String ANEWARRAY = "anewarray";
	public static final String ARRAYLENGTH = "arraylength";
	
	public static final String ATHROW = "athrow";
	public static final String CHECKCAST = "checkcast";
	public static final String INSTANCEOF = "instanceof";
	public static final String MONITORENTER = "monitorenter";
	public static final String MONITOREXIT = "monitorexit";
	
	public static final String WIDE = "wide";
	public static final String MULTIANEWARRAY = "multianewarray";
	
	public static final String IFNULL = "ifnull";
	public static final String IFNONNULL = "ifnonnull";
	
	public static final String GOTO_W = "goto_w";
	public static final String JSR_W = "jsr_w";
	
	private final Appendable appendable;
	
	public PrintingCoreCodeWriter( final Appendable appendable ) {
		this.appendable = appendable;
	}
	
	@Override
	public final PrintingCoreCodeWriter aaload() {
		return this.op( AALOAD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter aastore() {
		return this.op( AASTORE ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter aconst_null() {
		return this.op( ACONST_NULL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter aload( final int slot ) {
		return this.op( ALOAD ).operand( slot ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter aload_0() {
		return this.op( ALOAD_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter aload_1() {
		return this.op( ALOAD_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter aload_2() {
		return this.op( ALOAD_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter aload_3() {
		return this.op( ALOAD_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter anewarray( final Type componentType ) {
		return this.op( ANEWARRAY ).operand( componentType ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter areturn() {
		return this.op( ARETURN ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter arraylength() {
		return this.op( ARRAYLENGTH ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter astore( final int slot ) {
		return this.op( ASTORE ).operand( slot ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter astore_0() {
		return this.op( ASTORE_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter astore_1() {
		return this.op( ASTORE_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter astore_2() {
		return this.op( ASTORE_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter astore_3() {
		return this.op( ASTORE_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter athrow() {
		return this.op( ATHROW ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter baload() {
		return this.op( BALOAD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter bastore() {
		return this.op( BASTORE ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter bipush( final byte value ) {
		return this.op( BIPUSH ).operand( value ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter caload() {
		return this.op( CALOAD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter castore() {
		return this.op( CASTORE ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter checkcast( final Type type ) {
		return this.op( CHECKCAST ).operand( type ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter d2f() {
		return this.op( D2F ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter d2i() {
		return this.op( D2I ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter d2l() {
		return this.op( D2L ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dadd() {
		return this.op( DADD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter daload() {
		return this.op( DALOAD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dastore() {
		return this.op( DASTORE ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dcmpg() {
		return this.op( DCMPG ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dcmpl() {
		return this.op( DCMPL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dconst_0() {
		return this.op( DCONST_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dconst_1() {
		return this.op( DCONST_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ddiv() {
		return this.op( DDIV ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dload( final int slot ) {
		return this.op( DLOAD ).operand( slot ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dload_0() {
		return this.op( DLOAD_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dload_1() {
		return this.op( DLOAD_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dload_2() {
		return this.op( DLOAD_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dload_3() {
		return this.op( DLOAD_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dmul() {
		return this.op( DMUL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dneg() {
		return this.op( DNEG ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter drem() {
		return this.op( DREM ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dreturn() {
		return this.op( DRETURN ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dstore( final int slot ) {
		return this.op( DSTORE ).operand( slot ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dstore_0() {
		return this.op( DSTORE_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dstore_1() {
		return this.op( DSTORE_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dstore_2() {
		return this.op( DSTORE_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dstore_3() {
		return this.op( DSTORE_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dsub() {
		return this.op( DSUB ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dup() {
		return this.op( DUP ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dup2() {
		return this.op( DUP2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dup2_x1() {
		return this.op( DUP2_X1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dup2_x2() {
		return this.op( DUP2_X2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dup_x1() {
		return this.op( DUP_X1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter dup_x2() {
		return this.op( DUP_X2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter f2d() {
		return this.op( F2D ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter f2i() {
		return this.op( F2I ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter f2l() {
		return this.op( F2L ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fadd() {
		return this.op( FADD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter faload() {
		return this.op( FALOAD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fastore() {
		return this.op( FASTORE ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fcmpg() {
		return this.op( FCMPG ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fcmpl() {
		return this.op( FCMPL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fconst_0() {
		return this.op( FCONST_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fconst_1() {
		return this.op( FCONST_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fconst_2() {
		return this.op( FCONST_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fdiv() {
		return this.op( FDIV ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fload( final int slot ) {
		return this.op( FLOAD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fload_0() {
		return this.op( FLOAD_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fload_1() {
		return this.op( FLOAD_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fload_2() {
		return this.op( FLOAD_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fload_3() {
		return this.op( FLOAD_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fmul() {
		return this.op( FMUL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fneg() {
		return this.op( FNEG ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter frem() {
		return this.op( FREM ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter freturn() {
		return this.op( FRETURN ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fstore( final int slot ) {
		return this.op( FSTORE ).operand( slot ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fstore_0() {
		return this.op( FSTORE_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fstore_1() {
		return this.op( FSTORE_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fstore_2() {
		return this.op( FSTORE_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fstore_3() {
		return this.op( FSTORE_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter fsub() {
		return this.op( FSUB ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter getfield(
		final Type targetType,
		final String fieldName,
		final Type fieldType )
	{
		return this.op( GETFIELD ).todo().endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter getstatic(
		final Type targetType,
		final String fieldName,
		final Type fieldType )
	{
		return this.op( GETSTATIC ).todo().endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter goto_( final Jump jump ) {
		return this.op( GOTO ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter handleException( final ExceptionHandler exceptionHandler ) {
		//TODO: handleException
		return this;
	}
	
	@Override
	public final PrintingCoreCodeWriter i2b() {
		return this.op( I2B ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter i2c() {
		return this.op( I2C ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter i2d() {
		return this.op( I2D ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter i2f() {
		return this.op( I2F ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter i2l() {
		return this.op( I2L ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter i2s() {
		return this.op( I2S ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iadd() {
		return this.op( IADD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iaload() {
		return this.op( IALOAD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iand() {
		return this.op( IAND ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iastore() {
		return this.op( IASTORE ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iconst_m1() {
		return this.op( ICONST_M1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iconst_0() {
		return this.op( ICONST_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iconst_1() {
		return this.op( ICONST_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iconst_2() {
		return this.op( ICONST_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iconst_3() {
		return this.op( ICONST_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iconst_4() {
		return this.op( ICONST_4 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iconst_5() {
		return this.op( ICONST_5 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter idiv() {
		return this.op( IDIV ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter if_acmpeq( final Jump jump ) {
		return this.op( IF_ACMPEQ ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter if_acmpne( final Jump jump ) {
		return this.op( IF_ACMPNE ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter if_icmpeq( final Jump jump ) {
		return this.op( IF_ICMPEQ ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter if_icmpge( final Jump jump ) {
		return this.op( IF_ICMPGE ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter if_icmpgt( final Jump jump ) {
		return this.op( IF_ICMPGT ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter if_icmple( final Jump jump ) {
		return this.op( IF_ICMPLE ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter if_icmplt( final Jump jump ) {
		return this.op( IF_ICMPLT ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter if_icmpne( final Jump jump ) {
		return this.op( IF_ICMPNE ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ifeq( final Jump jump ) {
		return this.op( IFEQ ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ifge( final Jump jump ) {
		return this.op( IFGE ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ifgt( final Jump jump ) {
		return this.op( IFGT ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ifle( final Jump jump ) {
		return this.op( IFLE ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iflt( final Jump jump ) {
		return this.op( IFLT ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ifne( final Jump jump ) {
		return this.op( IFNE ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ifnonnull( final Jump jump ) {
		return this.op( IFNONNULL ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ifnull( final Jump jump ) {
		return this.op( IFNULL ).operand( jump ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iinc( final int slot, final int amount ) {
		return this.op( IINC ).operand( slot ).operand( amount ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iload( final int slot ) {
		return this.op( ILOAD ).operand( slot ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iload_0() {
		return this.op( ILOAD_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iload_1() {
		return this.op( ILOAD_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iload_2() {
		return this.op( ILOAD_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iload_3() {
		return this.op( ILOAD_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter imul() {
		return this.op( IMUL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ineg() {
		return this.op( INEG ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter instanceof_( final Type type ) {
		return this.op( INSTANCEOF ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter invokeinterface(
		final Type targetType,
		final String methodName,
		final Type[] argumentTypes,
		final Type returnType )
	{
		return this.op( INVOKEINTERFACE ).todo().endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter invokespecial(
		final Type targetType,
		final String methodName,
		final Type[] argumentTypes,
		final Type returnType )
	{
		return this.op( INVOKESPECIAL ).todo().endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter invokestatic(
		final Type targetType,
		final String methodName,
		final Type[] argumentTypes,
		final Type returnType )
	{
		return this.op( INVOKESTATIC ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter invokevirtual(
		final Type targetType,
		final String methodName,
		final Type[] argumentTypes,
		final Type returnType )
	{
		return this.op( INVOKEVIRTUAL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ior() {
		return this.op( IOR ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter irem() {
		return this.op( IREM ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ireturn() {
		return this.op( IRETURN ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ishl() {
		return this.op( ISHL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ishr() {
		return this.op( ISHR ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter istore( final int index ) {
		return this.op( ISTORE ).operand( index ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter istore_0() {
		return this.op( ISTORE_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter istore_1() {
		return this.op( ISTORE_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter istore_2() {
		return this.op( ISTORE_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter istore_3() {
		return this.op( ISTORE_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter isub() {
		return this.op( ISUB ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter iushr() {
		return this.op( IUSHR ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ixor() {
		return this.op( IXOR ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter l2d() {
		return this.op( L2D ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter l2f() {
		return this.op( L2F ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter l2i() {
		return this.op( L2I ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ladd() {
		return this.op( LADD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter laload() {
		return this.op( LALOAD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter land() {
		return this.op( LAND ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lastore() {
		return this.op( LASTORE ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lcmp() {
		return this.op( LCMP ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lconst_0() {
		return this.op( LCONST_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lconst_1() {
		return this.op( LCONST_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ldc( final int index ) {
		return this.op( LDC ).operand( index ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ldc2_w( final int index ) {
		return this.op( LDC2_W ).operand( index ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter ldc_w( final int index ) {
		return this.op( LDC_W ).operand( index );
	}
	
	@Override
	public final PrintingCoreCodeWriter ldiv() {
		return this.op( LDIV ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lload( final int slot ) {
		return this.op( LLOAD ).operand( slot ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lload_0() {
		return this.op( LLOAD_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lload_1() {
		return this.op( LLOAD_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lload_2() {
		return this.op( LLOAD_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lload_3() {
		return this.op( LLOAD_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lmul() {
		return this.op( LMUL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lneg() {
		return this.op( LNEG ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lor() {
		return this.op( LOR ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lrem() {
		return this.op( LREM ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lreturn() {
		return this.op( LRETURN ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lshl() {
		return this.op( LSHL ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lshr() {
		return this.op( LSHR ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lstore( final int slot ) {
		return this.op( LSTORE ).operand( slot ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lstore_0() {
		return this.op( LSTORE_0 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lstore_1() {
		return this.op( LSTORE_1 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lstore_2() {
		return this.op( LSTORE_2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lstore_3() {
		return this.op( LSTORE_3 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lsub() {
		return this.op( LSUB ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lushr() {
		return this.op( LUSHR ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter lxor() {
		return this.op( LXOR ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter monitorenter() {
		return this.op( MONITORENTER ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter monitorexit() {
		return this.op( MONITOREXIT ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter multianewarray(
		final Type arrayType,
		final int numDimensions )
	{
		return this.op( MULTIANEWARRAY ).operand( arrayType ).operand( numDimensions ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter new_( final Type type ) {
		return this.op( NEW ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter newarray( final Type componentType ) {
		return this.op( NEWARRAY ).operand( componentType ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter nop() {
		return this.op( NOP ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter pop() {
		return this.op( POP ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter pop2() {
		return this.op( POP2 ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter putfield(
		final Type targetType,
		final String fieldName,
		final Type fieldType )
	{
		return this.op( PUTFIELD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter putstatic(
		final Type targetType,
		final String fieldName,
		final Type fieldType )
	{
		return this.op( PUTSTATIC ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter return_() {
		return this.op( RETURN ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter saload() {
		return this.op( SALOAD ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter sastore() {
		return this.op( SASTORE ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter sipush( final short value ) {
		return this.op( SIPUSH ).operand( value ).endl();
	}
	
	@Override
	public final PrintingCoreCodeWriter swap() {
		return this.op( SWAP ).endl();
	}
	
	@Override
	public final int maxLocals() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final int maxStack() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final int pos() {
		throw new UnsupportedOperationException();
	}
	
	private final PrintingCoreCodeWriter op( final String code ) {
		try {
			this.appendable.append( code );
			return this;
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private final PrintingCoreCodeWriter operand( final int operand ) {
		try {
			this.appendable.append( ' ' ).append( Integer.toString( operand ) );
			return this;
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private final PrintingCoreCodeWriter operand( final byte operand ) {
		try {
			//TODO: Verify correctness
			this.appendable.append( ' ' ).append( Integer.toString( operand ) );
			return this;
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private final PrintingCoreCodeWriter operand( final Type operand ) {
		try {
			Class< ? > operandClass = JavaTypes.getRawClass( operand );
			this.appendable.append( ' ' ).append( operandClass.getCanonicalName() );
			return this;
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private final PrintingCoreCodeWriter operand( final Jump jump ) {
		try {
			//TODO: Implement this better
			this.appendable.append( ' ' ).append( jump.toString() );
			return this;
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private final PrintingCoreCodeWriter todo() {
		try {
			//TODO: Replace this for field and method signatures
			this.appendable.append( "TODO" );
			return this;
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private final PrintingCoreCodeWriter endl() {
		try {
			//TODO: Platform specific new line
			this.appendable.append( "\r\n" );
			return this;
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}	
	}
}
