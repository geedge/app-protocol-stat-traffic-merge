package com.zdjizhi;

import com.zdjizhi.utils.StringUtil;
import org.junit.Test;

import java.util.Arrays;


/**
 * @author qidaijie
 * @Package com.zdjizhi
 * @Description:
 * @date 2023/1/617:54
 */
public class ConventionalTest {

    @Test
    public void protocolTreeTest() {
        String groupKey = "ETHERNET.IPv4.TCP.UNCATEGORIZED.qq_r2@4";
        String protocol = groupKey.substring(0, groupKey.indexOf("@"));
        System.out.println(protocol);
        StringBuffer stringBuffer = new StringBuffer();
        String appName = "qq_r2";
        String[] protocolIds = protocol.split("\\.");
        for (String proto : protocolIds) {
            if (StringUtil.isBlank(stringBuffer.toString())) {
                stringBuffer.append(proto);
                System.out.println(stringBuffer.toString());
            } else {
                stringBuffer.append(".").append(proto);
                if (proto.equals(appName)) {
                    System.out.println(stringBuffer.toString() + "---" + appName);
                } else {
                    System.out.println(stringBuffer.toString());
                }
            }
        }
    }

    @Test
    public void SplitTest() {
        String str = "[.]";
        String protocol = "ETHERNET.IPv4.TCP.http.test";

        System.out.println(Arrays.toString(protocol.split(str)));

        String str2 = "\\.";
        System.out.println(Arrays.toString(protocol.split(str2)));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < protocol.split(str).length - 1; i++) {
            String value = protocol.split(str)[i];
            if (StringUtil.isBlank(stringBuilder.toString())) {
                stringBuilder.append(value);
                System.out.println(stringBuilder.toString());
            } else {
                stringBuilder.append(".").append(value);
                System.out.println(stringBuilder.toString());
            }
        }
        System.out.println("\n\n\n");
        protocol = "ETHERNET.IPv4.TCP";
        String app = "http.test";
        System.out.println(Arrays.toString(app.split(str2)));
        System.out.println(app.substring(app.lastIndexOf(".") + 1));
        System.out.println(protocol.concat(".").concat(app));

        System.out.println("\n\n\n");
        app = "test";
        System.out.println(Arrays.toString(app.split(str2)));
        System.out.println(app.substring(app.lastIndexOf(".") + 1));
        System.out.println(protocol.concat(".").concat(app));

    }

    @Test
    public void removeDuplicateProtocolTest() {
        String str = "[.]";
        String protocol = "ETHERNET.IPv4.TCP.http";
        String[] protocolSplits = protocol.split(str);
        String endProtocol = protocolSplits[protocolSplits.length -1];
        System.out.println(endProtocol);

        String app = "http.test";
        String[] appSplits = app.split(str);
        String firstAppProtocol = appSplits[0];
        System.out.println(firstAppProtocol);


        System.out.println(app.substring(app.lastIndexOf(".") + 1));
        System.out.println(app.lastIndexOf(".") + 1);
        System.out.println(protocol.substring(0,protocol.lastIndexOf(".")));

    }


}
