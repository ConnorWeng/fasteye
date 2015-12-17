package com.zwd51.fasteye.webapi

import javax.servlet.annotation.WebServlet
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.zwd51.fasteye.Searcher

/**
  * Created by Connor on 12/13/15.
  */
@WebServlet(Array("SimilarImages"))
class SimilarImagesServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("text/json")
    resp.setCharacterEncoding("UTF-8")
    val url = req.getParameter("url")
    if (url != null) {
      SourceImage(url).withBufferedImage { bufferedImage =>
        val goodIds = Searcher.search(bufferedImage).mkString(",")
        println(s"$url returns: [$goodIds]")
        resp.getWriter.write(s"[$goodIds]")
      }
    } else {
      resp.getWriter.write("[]")
    }
  }
}
