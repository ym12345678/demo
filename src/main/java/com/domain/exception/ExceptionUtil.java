package com.domain.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author: LJ
 * @create: 2019-02-28
 **/
public class ExceptionUtil {
    /** 获取异常详细信息
     * @auther: LJ
     * @param ex
     * @return:
     */
    public static String getExceptionDetail(Exception ex) {
        String ret = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = new String(out.toByteArray());
            pout.close();
            out.close();
        } catch (Exception e) {
        }
        return ret;
    }
}
