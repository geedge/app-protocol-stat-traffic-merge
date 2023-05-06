package com.zdjizhi;

import org.junit.Test;

/**
 * @author qidaijie
 * @Package com.zdjizhi
 * @Description:
 * @date 2023/4/1810:22
 */
public class FlagsTest {
    /*
     * 参考资料：https://juejin.cn/post/6879226834597691405
     *
     * 会话标记(实际存储为64位无符号整数），32-bit Field标识会话的网络行为，日志记录值和如下值通过Bitwise AND(&)操作进行查询和转换：
     * 0x00000001 - (1) Asymmetric
     * 0x00000002 - (2) Bulky
     * 0x00000004 - (4) CBR Streaming
     * 0x00000008 - (8) Client is Local
     * 0x00000010 - (16) Server is Local
     * 0x00000020 - (32) Download
     * 0x00000040 - (64) Interactive
     * 0x00000080 - (128) Inbound
     * 0x00000100 - (256) Outbound
     * 0x00000200 - (512) Pseudo Unidirectional
     * 0x00000400 - (1024) Streaming
     * 0x00000800 - (2048) Unidirectional
     * 0x00001000 - (4096) Random looking
     * 0x00002000 - (8192) C2S
     * 0x00004000 - (16384) S2C
     */

    @Test
    public void bitwiseAND() {
        Long common_flags = 8200L;
        Long clientIsLocal = 8L;
        Long serverIsLocal = 16L;

        System.out.println("common_flags & clientIsLocal = " + (common_flags & clientIsLocal));
        System.out.println("common_flags & serverIsLocal = " + (common_flags & serverIsLocal)+"\n\n");

        common_flags = 16400L;

        System.out.println("common_flags & clientIsLocal = " + (common_flags & clientIsLocal));
        System.out.println("common_flags & serverIsLocal = " + (common_flags & serverIsLocal));

    }
}
