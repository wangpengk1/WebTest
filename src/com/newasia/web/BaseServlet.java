package com.newasia.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

public class BaseServlet extends HttpServlet {
    protected HttpServletRequest mLatestReq;
    protected HttpServletResponse mLatestResp;
    protected ArrayList<Cookie> mCookieList = new ArrayList<>();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mLatestReq = req;
        mLatestResp = resp;
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("UTF-8");
        checkCookie();
        if(isParamersEmpty())
        {
            returnDeafaultHTML();
        }
    }


    protected void checkCookie()
    {
        if(mLatestReq==null) return;
        mCookieList.clear();
        Cookie[] cookies = mLatestReq.getCookies();
        for(Cookie cookie:cookies)
        {
            mCookieList.add(cookie);
        }
     }


    protected boolean isParamersEmpty()
    {
        if(mLatestReq==null) return true;
        return mLatestReq.getParameterMap().isEmpty();
    }

    protected void returnDeafaultHTML() throws IOException
    {
        if(mLatestResp==null) return;
        ServletContext context = getServletConfig().getServletContext();
        String html = context.getInitParameter(this.getClass().getSimpleName());
        if(html==null) return;
        InputStream inputStream = context.getResourceAsStream(html);
        if(inputStream==null) return;

        OutputStream outputStream = mLatestResp.getOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        while ((len = inputStream.read(data))>0)
        {
            outputStream.write(data,0,len);
        }
    }

    protected void printParamers()
    {
        if(mLatestReq==null) return;
        Enumeration<String> paramers = mLatestReq.getParameterNames();
        while (paramers.hasMoreElements())
        {
            System.out.println(paramers.nextElement());
        }
    }


    protected void createSection(PageContext context) throws IOException
    {
        JspWriter outer = context.getOut();
        for(int i=0;i<10;i++)
        {
            outer.println("<tr>");
            outer.append("  ");
            for(int j=0;j<10;++j)
            {
                outer.println("<td>");
                outer.append("row"+i+"column"+j);
                outer.append("</td>");
            }
            outer.println("  ");
            outer.append("</tr>");

        }
    }

}
