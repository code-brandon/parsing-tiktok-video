/*
 * 项目名： gulimall-product
 * 文件名： ThreadConfig.java
 * 模块说明：
 * 修改历史:
 * 2021-9-29 17:48:25 - 小政同学i丷 - 创建.
 */

package com.xiaozheng.tikTokVideo.utils;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * What -- 配置线程池
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName ThreadConfig
 * @CreateTime 2021/9/29 17:15
 */
public class ThreadConfig {
    public static ThreadPoolExecutor threadPoolExecutor() {
        /**
         * if (corePoolSize < 0 ||
         *    maximumPoolSize <= 0 ||
         *    maximumPoolSize < corePoolSize ||
         *    keepAliveTime < 0)
         *  hrow new IllegalArgumentException();
         */
        return new ThreadPoolExecutor(
                // 要保留在池中的线​​程数
                20,
                // 池中允许的最大线程数
                200,
                // 当线程数大于核心数时
                10,
                // 指定时间单位
                TimeUnit.SECONDS,
                // 创建具有给定（固定）容量的LinkedBlockingDeque
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 获取web客户端
     * @return
     */
    public static WebClient getWebClient() {
        //新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        //不启用ActiveX
        webClient.getOptions().setActiveXNative(false);
        //是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setCssEnabled(false);
        //很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(true);
        //不下载图片
        webClient.getOptions().setDownloadImages(false);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        return webClient;
    }
}
