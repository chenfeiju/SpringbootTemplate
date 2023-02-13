package com.chenfj.util;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/30 15:48
 * @Description:
 * @version: 1.0
 */
public class WebUtil {
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }catch (Exception e){
           e.printStackTrace();

        }
        return;
    }
}
