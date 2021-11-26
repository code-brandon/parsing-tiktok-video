package com.xiaozheng.tikTokVideo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaozheng.tikTokVideo.domain.SaveVideoVo;
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
 * @ClassName ${NAME}
 * @CreateTime 2021/11/25 18:59
 */
@WebServlet(value = "/download",name = "解析视频 or 保存到minio")
public class DownloadController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResolveService resolveService = new ResolveService();
        request.setCharacterEncoding("UTF-8");
        SaveVideoVo saveVideoVo = new SaveVideoVo();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(saveVideoVo, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        String json = resolveService.downloadVideo(saveVideoVo);
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
