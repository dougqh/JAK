package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;


public abstract class Operation {
	public static final byte NOP = 0x00;
	public static final byte ACONST_NULL = 1;
	
	public static final byte ICONST_M1 = 2;
	public static final byte ICONST_0 = 3;
	public static final byte ICONST_1 = 4;
	public static final byte ICONST_2 = 5;
	public static final byte ICONST_3 = 6;
	public static final byte ICONST_4 = 7;
	public static final byte ICONST_5 = 8;
	
	public static final byte LCONST_0 = 9;
	public static final byte LCONST_1 = 10;
	
	public static final byte FCONST_0 = 11;
	public static final byte FCONST_1 = 12;
	public static final byte FCONST_2 = 13;
	
	public static final byte DCONST_0 = 14;
	public static final byte DCONST_1 = 15;
	
	public static final byte BIPUSH = 16;
	public static final byte SIPUSH = 17;
	
	public static final byte LDC = 18;
	public static final byte LDC_W = 19;	
	public static final byte LDC2_W = 20;
	
	public static final byte ILOAD = 21;
	public static final byte LLOAD = 22;
	public static final byte FLOAD = 23;
	public static final byte DLOAD = 24;
	public static final byte ALOAD = 25;
	
	public static final byte ILOAD_0 = 26;
	public static final byte ILOAD_1 = 27;
	public static final byte ILOAD_2 = 28;
	public static final byte ILOAD_3 = 29;
	
	public static final byte LLOAD_0 = 30;
	public static final byte LLOAD_1 = 31;
	public static final byte LLOAD_2 = 32;
	public static final byte LLOAD_3 = 33;
	
	public static final byte FLOAD_0 = 34;
	public static final byte FLOAD_1 = 35;
	public static final byte FLOAD_2 = 36;
	public static final byte FLOAD_3 = 37;
	
	public static final byte DLOAD_0 = 38;
	public static final byte DLOAD_1 = 39;
	public static final byte DLOAD_2 = 40;
	public static final byte DLOAD_3 = 41;
	
	public static final byte ALOAD_0 = 42;
	public static final byte ALOAD_1 = 43;
	public static final byte ALOAD_2 = 44;
	public static final byte ALOAD_3 = 45;
	
	public static final byte IALOAD = 46;
	public static final byte LALOAD = 47;
	public static final byte FALOAD = 48;
	public static final byte DALOAD = 49;
	public static final byte AALOAD = 50;
	public static final byte BALOAD = 51;
	public static final byte CALOAD = 52;
	public static final byte SALOAD = 53;
	
	public static final byte ISTORE = 54;
	public static final byte LSTORE = 55;
	public static final byte FSTORE = 56;
	public static final byte DSTORE = 57;
	public static final byte ASTORE = 58;
	
	public static final byte ISTORE_0 = 59;
	public static final byte ISTORE_1 = 60;
	public static final byte ISTORE_2 = 61;
	public static final byte ISTORE_3 = 62;
	
	public static final byte LSTORE_0 = 63;
	public static final byte LSTORE_1 = 64;
	public static final byte LSTORE_2 = 65;
	public static final byte LSTORE_3 = 66;
	
	public static final byte FSTORE_0 = 67;
	public static final byte FSTORE_1 = 68;
	public static final byte FSTORE_2 = 69;
	public static final byte FSTORE_3 = 70;
	
	public static final byte DSTORE_0 = 71;
	public static final byte DSTORE_1 = 72;
	public static final byte DSTORE_2 = 73;
	public static final byte DSTORE_3 = 74;
	
	public static final byte ASTORE_0 = 75;
	public static final byte ASTORE_1 = 76;
	public static final byte ASTORE_2 = 77;
	public static final byte ASTORE_3 = 78;
	
	public static final byte IASTORE = 79;
	public static final byte LASTORE = 80;
	public static final byte FASTORE = 81;
	public static final byte DASTORE = 82;
	public static final byte AASTORE = 83;
	public static final byte BASTORE = 84;
	public static final byte CASTORE = 85;
	public static final byte SASTORE = 86;
	
	public static final byte POP = 87;
	public static final byte POP2 = 88;
	
	public static final byte DUP = 89;
	public static final byte DUP_X1 = 90;
	public static final byte DUP_X2 = 91;
	public static final byte DUP2 = 92;
	public static final byte DUP2_X1 = 93;
	public static final byte DUP2_X2 = 94;
	
	public static final byte SWAP = 95;
	
	public static final byte IADD = 96;
	public static final byte LADD = 97;
	public static final byte FADD = 98;
	public static final byte DADD = 99;
	
	public static final byte ISUB = 100;
	public static final byte LSUB = 101;
	public static final byte FSUB = 102;
	public static final byte DSUB = 103;
	
	public static final byte IMUL = 104;
	public static final byte LMUL = 105;
	public static final byte FMUL = 106;
	public static final byte DMUL = 107;
	
	public static final byte IDIV = 108;
	public static final byte LDIV = 109;
	public static final byte FDIV = 110;
	public static final byte DDIV = 111;
	
	public static final byte IREM = 112;
	public static final byte LREM = 113;
	public static final byte FREM = 114;
	public static final byte DREM = 115;
	
	public static final byte INEG = 116;
	public static final byte LNEG = 117;
	public static final byte FNEG = 118;
	public static final byte DNEG = 119;
	
	public static final byte ISHL = 120;
	public static final byte LSHL = 121;
	
	public static final byte ISHR = 122;
	public static final byte LSHR = 123;
	
	public static final byte IUSHR = 124;
	public static final byte LUSHR = 125;
	
	public static final byte IAND = 126;
	public static final byte LAND = 127;
	
	public static final byte IOR = (byte)128;
	public static final byte LOR = (byte)129;
	
	public static final byte IXOR = (byte)130;
	public static final byte LXOR = (byte)131;
	
	public static final byte IINC = (byte)132;
	
	public static final byte I2L = (byte)133;	
	public static final byte I2F = (byte)134;
	public static final byte I2D = (byte)135;
	
	public static final byte L2I = (byte)136;
	public static final byte L2F = (byte)137;
	public static final byte L2D = (byte)138;
	
	public static final byte F2I = (byte)139;
	public static final byte F2L = (byte)140;
	public static final byte F2D = (byte)141;
	
	public static final byte D2I = (byte)142;
	public static final byte D2L = (byte)143;
	public static final byte D2F = (byte)144;
	
	public static final byte I2B = (byte)145;
	public static final byte I2C = (byte)146;
	public static final byte I2S = (byte)147;
	
	public static final byte LCMP = (byte)148;
	public static final byte FCMPL = (byte)149;
	public static final byte FCMPG = (byte)150;
	public static final byte DCMPL = (byte)151;
	public static final byte DCMPG = (byte)152;
	
	public static final byte IFEQ = (byte)153;
	public static final byte IFNE = (byte)154;
	public static final byte IFLT = (byte)155;
	public static final byte IFGE = (byte)156;
	public static final byte IFGT = (byte)157;
	public static final byte IFLE = (byte)158;
	
	public static final byte IF_ICMPEQ = (byte)159;
	public static final byte IF_ICMPNE = (byte)160;
	public static final byte IF_ICMPLT = (byte)161;
	public static final byte IF_ICMPGE = (byte)162;
	public static final byte IF_ICMPGT = (byte)163;
	public static final byte IF_ICMPLE = (byte)164;
	
	public static final byte IF_ACMPEQ = (byte)165;
	public static final byte IF_ACMPNE = (byte)166;
	
	public static final byte GOTO = (byte)167;
	public static final byte JSR = (byte)168;
	public static final byte RET = (byte)169;
	
	public static final byte TABLESWITCH = (byte)170;
	public static final byte LOOKUPSWITCH = (byte)171;
	
	public static final byte IRETURN = (byte)172;
	public static final byte LRETURN = (byte)173;
	public static final byte FRETURN = (byte)174;
	public static final byte DRETURN = (byte)175;
	
	public static final byte ARETURN = (byte)176;
	public static final byte RETURN = (byte)177;
	
	public static final byte GETSTATIC = (byte)178;
	public static final byte PUTSTATIC = (byte)179;
	public static final byte GETFIELD = (byte)180;
	public static final byte PUTFIELD = (byte)181;
	
	public static final byte INVOKEVIRTUAL = (byte)182;
	public static final byte INVOKESPECIAL = (byte)183;
	public static final byte INVOKESTATIC = (byte)184;
	public static final byte INVOKEINTERFACE = (byte)185;
	
	public static final byte NEW = (byte)187;
	public static final byte NEWARRAY = (byte)188;
	public static final byte ANEWARRAY = (byte)189;
	public static final byte ARRAYLENGTH = (byte)190;
	
	public static final byte ATHROW = (byte)191;
	public static final byte CHECKCAST = (byte)192;
	public static final byte INSTANCEOF = (byte)193;
	public static final byte MONITORENTER = (byte)194;
	public static final byte MONITOREXIT = (byte)195;
	
	public static final byte WIDE = (byte)196;
	public static final byte MULTIANEWARRAY = (byte)197;
	
	public static final byte IFNULL = (byte)198;
	public static final byte IFNONNULL = (byte)199;
	
	public static final byte GOTO_W = (byte)200;
	public static final byte JSR_W = (byte)201;
	
	protected static final Class< ? >[] NO_ARGS = {};
	protected static final Class< ? >[] NO_RESULTS = {};
	
	protected Operation() {}
	
	public abstract String getId();
	
	public abstract int getCode();
	
	public boolean isPolymorphic() {
		return false;
	}
	
	public abstract Class< ? >[] getCodeOperandTypes();
	
	public abstract Class< ? >[] getStackOperandTypes();
	
	public abstract Class< ? >[] getStackResultTypes();
	
	public abstract void write( final JavaCoreCodeWriter coreWriter );
}
