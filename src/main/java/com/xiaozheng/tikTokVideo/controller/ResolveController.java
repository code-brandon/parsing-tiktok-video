package com.xiaozheng.tikTokVideo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaozheng.tikTokVideo.domain.ResolveURL;
import com.xiaozheng.tikTokVideo.service.ResolveService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName ResolveController
 * @CreateTime 2021/11/25 10:29
 */
@WebServlet(value = "/resolve",name = "解析抖音Api")
public class ResolveController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResolveService resolveService = new ResolveService();
        request.setCharacterEncoding("UTF-8");
        ResolveURL resolveURL = new ResolveURL();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(resolveURL, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        String json = resolveService.resolve(resolveURL);
        //json数据返回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), json);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
