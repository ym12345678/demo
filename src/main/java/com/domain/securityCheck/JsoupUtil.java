package com.domain.securityCheck;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * xss非法标签过滤工具类
 * 过滤html中的xss字符
 *
 * @author: LJ
 * @create: 2018-12-06
 **/
public class JsoupUtil {
    /**
     * 使用自带的basicWithImages 白名单
     * 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
     * strike,strong,sub,sup,u,ul,img
     * 以及a标签的href,img标签的src,align,alt,height,width,title属性
     */
    private static final Whitelist whitelist = Whitelist.basicWithImages();
    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    private static Logger log = LogManager.getLogger(JsoupUtil.class);

    static {
        // 富文本编辑时一些样式是使用style来进行实现的
        // 比如红色字体 style="color:red;"
        // 所以需要给所有标签添加style属性
        whitelist.addAttributes(":all", "style");
    }

    public static String clean(String content, boolean method) {
        if (method) {
            if (StringUtils.isNotBlank(content)) content = content.trim();
            content = Jsoup.clean(content, "", whitelist, outputSettings);
        } else {
            content = content.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
            content = content.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
            content = content.replaceAll("'", "& #39;");
            content = content.replaceAll("eval\\((.*)\\)", "");
            content = content.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            content = content.replaceAll("script", "");
            content = content.replaceAll("[*]", "[" + "*]");
            content = content.replaceAll("[+]", "[" + "+]");
            content = content.replaceAll("[?]", "[" + "?]");
        }
        String[] values = content.split(" ");
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|,|like|//|/|%|#";
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            for (int j = 0; j < values.length; j++) {
                if (values[j].equalsIgnoreCase(badStrs[i])) {
                    values[j] = "forbid";
                    log.debug("防sql注入；参数中包含的字段：{" + badStrs[i] + "}" + "替换后的字段：forbid；");
                    log.debug("参数内容："+content);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i == values.length - 1) {
                sb.append(values[i]);
            } else {
                sb.append(values[i] + " ");
            }
        }
        content = sb.toString();
        return content;
    }

   /* public static void main(String[] args) throws IOException {
        String text = "   <a href=\"http://www.baidu.com/a\" onclick=\"alert(1);\">sss</a><script>alert(0);</script>sss   ";
        String testHtml = "<h1 onclick='alert(1);'class='' style=''title=''>h1 内容 </h1><div class=''>div 内容 </div><p class=''style='color:red;' >p 内容 </p>";
        // System.out.println(clean(testHtml, false));
        // System.out.println(clean(text, false));
    }*/
}
