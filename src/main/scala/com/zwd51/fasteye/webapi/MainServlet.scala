package com.zwd51.fasteye.webapi

import javax.servlet.annotation.WebServlet
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

/**
  * Created by Connor on 12/13/15.
  */
@WebServlet(Array("main.do"))
class MainServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("text/html")
    resp.setCharacterEncoding("UTF-8")
    resp.getWriter.write("<h1>hello world</h1>")
  }
}
