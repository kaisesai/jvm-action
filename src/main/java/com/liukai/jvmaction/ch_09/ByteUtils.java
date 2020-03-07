package com.liukai.jvmaction.ch_09;

/**
 * Bytes数组处理工具
 */
public class ByteUtils {

  /*
  1. 以 byte a = (byte)0xa3; 为例
    首先byte类型是8 bit (8个0/1) , "A3"  --> 1010 0011 ,  jvm虚拟机内部存储的是int类型（4字节 32bit）时是直接将最高位（就是最左边）的值补到高位的24bit，
    于是“A3”  --> 1111 1111 1111  1111 1111 1111 1010 0011 (这里是将最高位的1 补到24bit高位).
    这个二进制表示是计算机内部存储地表示，而计算机内部是以补码的方式存储地，也就是说这个不是真正的值，只有将这个转换成原码才是真正的值。

    补码： 1111 1111 1111  1111 1111 1111 1010 0011    —>   -1
    反码： 1111 1111 1111  1111 1111 1111 1010 0010    —>  除最高位  全取反
    原码 ：1000 0000 0000 0000 0000 0000 0101 1101   ——> -（64+16+8+4+1） = -93

    这就是为什么“A3”的byte直接赋值给int后会是 -93的原因。

    而我们经过之前的分析会发现，当byte的最高位为1时，也就是以上的情况时，byte的值就会从正整数变为负整数了自然也就是错了。
    当byte的最高位为0时，因为byte存储时补齐24bit高位时用的是0补了24个0 值也就没有变，数值也就是正常的。

    >>>>
    计算机内存存储方式：
    简单说明一下原码，反码，补码。
    正整数的原码，反，补码都是一样的，不用进行转换。
    负整数的原码，最高位时1，这个不变，将除最高位意外的全部取反，1变成0 ，0变成1，就是反码了，然后将反码+1（以二进制的形式计算）得到的就是补码。
    那么从补码返回到原码就是先-1，然后将除最高位以外的都取反得到原码。

  2. &运算符  | 运算符
    & 这个其实也比较的简单：
    1 & 1  --> 1
    (1 & 0)  (0 & 1)  (0 & 0)  --> 0
    也就是说只有1 & 1 时候才为1 只要有0 结果都是0

    | 这个就相反的了
    0 | 0 --> 0
    (1 | 0)  (0 | 1)  (1 | 1)  --> 1
    也就是说只有0 | 0 时候才为0 只要有1 结果都是1

    3. &0xFF的意义
    其实到了这个时候，我们都明白就是要将当byte最高位为1时，补的24bit高位的1转换成0 那么值就是正确的。
    而 &0xFF（这是个int类型的值 ）这个操作就是起到这个作用（0xFF -->  0000 0000 0000 0000 0000 0000 1111 1111）（谢谢 评论里的回复）：

    1111 1111 1111  1111 1111 1111 1010 0011 
    &
    0000 0000 0000 0000 0000 0000 1111 1111
    >>结果为：
    0000 0000 0000 0000 0000 0000 1010 0011

    这个的计算结果就是 24bit高位全部为0 ，而8bit低位保持原样。

    嗯，byte 转成 int 就完了。

    还有一个int 转换成 byte的情况，不过呢因为byte只有8bit因此需要byte[4]数组存储这个int，我就记录一下：

    int temp = 1009020;
    byte[0] = (byte)(temp >> 24 & 0xFF);
    byte[1] = (byte)(temp >> 16 & 0xFF);
    byte[3] = (byte)(temp >> 8 & 0xFF);
    byte[4] = (byte)(temp  & 0xFF);
    //0为高位 2,3,4依次为低位

    原理其实就是将32bit , 以8bit为一段分割了一下， 也就是 >> 8( 倍数）移位了一下，然后其他就如之前一样。

    记录一下几个重要的点

    * 计算机内部存储时是以补码的形式存储，如果是负整数，需要转换
    * jvm虚拟机存储byte类型值是以4个字节存的，也就是会在24bit高位补byte最高位的值
    * &运算符的规则

 */

  public static int bytes2Int(byte[] b, int start, int len) {

    int sum = 0;
    int end = start + len;
    // 比如这里的常量数十六进制表示方式为 0x4E20，十进制表示为 20000，二进制表示为 0100 1110 0010 0000
    // 这里的第一个 byte 十六进制表示为 0x4e，二进制表示为 0100 1110，第二个 byte 十六进制为 0x20，二进制位 0010 0000，我们要对其合并为 0x4E20 表示，
    // 注意位运算是按照二进制方式计算，我们要对第一个 byte 进行位移 8 位，得到的值用二进制表示为 0100 1110 0000 0000，十进制为 19968，
    // 再与第二个 byte 值 二进制表示为 0010 0000，十进制为 32 ，进行十进制的表示与加法计算，最终得到值 19968 + 32 = 20000
    for (int i = start; i < end; i++) {
      // 这里的作用是 byte 转为 int，将 byte值 进行 &0xff 即可将高 24位值设置为 0
      int n = ((int) b[i]) & 0xff;
      // 左移 --len * 8
      n <<= (--len) * 8;
      sum = n + sum;
    }
    return sum;
  }

  public static byte[] int2Bytes(int value, int len) {
    // 这里是将 int 值转为 byte 数组，因为一个 int 用了 32 位即 4byte 存储，所最多需要将 int 用 4 个 byte 存储。即 byte[4]
    // 将 32 位的 int 拆分为 4 个 8 位 的 byte，
    byte[] b = new byte[len];
    for (int i = 0; i < len; i++) {
      b[len - i - 1] = (byte) ((value >> 8 * i) & 0xff);
    }
    return b;
  }

  public static String bytes2String(byte[] b, int start, int len) {
    return new String(b, start, len);
  }

  public static byte[] string2Bytes(String str) {
    return str.getBytes();
  }

  public static byte[] bytesReplace(byte[] originalBytes, int offset, int len,
                                    byte[] replaceBytes) {
    // 创建新的字节码数组
    byte[] newBytes = new byte[originalBytes.length + (replaceBytes.length - len)];
    // 拷贝替换的字节码之前的数据
    System.arraycopy(originalBytes, 0, newBytes, 0, offset);
    // 设置替换的字节码到新字节数组
    System.arraycopy(replaceBytes, 0, newBytes, offset, replaceBytes.length);
    // 拷贝替换的字节码之后的数据到新字节数组
    System.arraycopy(originalBytes, offset + len, newBytes, offset + replaceBytes.length,
                     originalBytes.length - offset - len);
    return newBytes;
  }

}
