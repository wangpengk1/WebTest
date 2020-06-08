package com.newasia.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/cookie")
public class CookieTest extends BaseServlet
{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        resp.setHeader("content-type","text/html;charset=utf-8");
        HttpSession session = req.getSession();
        session.setAttribute("data","I comming");
        if(!session.isNew())
        {
            resp.getWriter().append("<p>session is not new</>");
        }
        if(mCookieList.size()>0)
        {
            for(Cookie cookie:mCookieList)
            {
                if(cookie.getName().equalsIgnoreCase("LastVisitTime"))
                {
                    Date data = new Date(Long.parseLong(cookie.getValue()));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                    resp.getWriter().append("<p>您上一次的访问时间是:"+format.format(data)+"</p>");
                }
            }
        }
        else
        {
            resp.getWriter().append("<p>您是第一访问本网站</>");
        }

        Cookie cookie = new Cookie("LastVisitTime",System.currentTimeMillis()+"");
        cookie.setMaxAge(60*60*24);
        resp.addCookie(cookie);
    }
}
