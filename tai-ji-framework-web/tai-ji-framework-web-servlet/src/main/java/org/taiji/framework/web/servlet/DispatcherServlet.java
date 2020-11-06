package org.taiji.framework.web.servlet;

import com.alibaba.fastjson.JSON;
import org.taiji.framework.core.web.Request;
import org.taiji.framework.core.web.RequestHandler;
import org.taiji.framework.core.web.RequestHandlerMapping;
import org.taiji.framework.web.servlet.config.WebServletConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取上下文地址（项目名称）
        String contextPath = req.getContextPath();
        // 获取URL地址  先获取URI 然后通过上下文地址截取出URL 再讲多余的/ 去除
        String url = req.getRequestURI().replaceAll(contextPath,"").replaceAll("/+","/");
        // 封装请求
        Request request = encapsulatedRequest(req);
        // 通过HandlerMapping获取到对应的Handler
        url = url.replaceAll("\\s+","");
        RequestHandler requestHandler =  RequestHandlerMapping.getRequestHandler(url);
        // 这是返回字符集
        resp.setCharacterEncoding(WebServletConfiguration.getCharacterEncoding());
        // 当handler不存时返回404
        if(requestHandler == null){
            // TODO 目前为最简单的404实现
            resp.setStatus(404);
            resp.getWriter().write("404 找不到该页面！ ");
        }else {
            try {
                String jsonString = JSON.toJSONString(requestHandler.performMethod(request));
                resp.setStatus(200);
                resp.getWriter().write(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(500);
                resp.getWriter().write(e.toString());
            }
        }
    }

    /**
     * 封装Request
     * @param httpServletRequest httpServletRequest请求
     * @return Request
     */
    private Request encapsulatedRequest(HttpServletRequest httpServletRequest){
        Request request = new Request();
        // 添加请求参数

        request.setRequestParameterMap(httpServletRequest.getParameterMap());
        // 添加请求头
        Map<String,String> requestHeaderMap = new HashMap<>();
        Enumeration<String> headNames = httpServletRequest.getHeaderNames();
        while (headNames.hasMoreElements()){
            String headName = headNames.nextElement();
            requestHeaderMap.put(headName,httpServletRequest.getHeader(headName));
        }
        request.setRequestHeaderMap(requestHeaderMap);
        // 添加请求方式
        request.setRequestMethod(httpServletRequest.getMethod());
        return request;
    }

}
