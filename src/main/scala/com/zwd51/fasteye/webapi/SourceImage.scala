package com.zwd51.fasteye.webapi

import java.awt.image.BufferedImage
import java.io.{File, FileInputStream, FileOutputStream}
import java.net.URL
import javax.imageio.ImageIO

import scala.util.Random

/**
  * Created by Connor on 12/15/15.
  */
case class SourceImage(url: String) {
  def withBufferedImage(f: (BufferedImage) => Unit) = {
    val path = download()
    f(ImageIO.read(new FileInputStream(path)))
    new File(path).delete()
  }

  def download() = {
    val downloadUrl = new URL(url)
    val conn = downloadUrl.openConnection()
    conn.setConnectTimeout(5*1000)
    val is = conn.getInputStream
    val buf: Array[Byte] = new Array[Byte](1024*100)
    val dir = new File(sys.env("FASTEYE_DOWNLOADS_DIR"))
    if (!dir.exists()) {
      dir.mkdirs()
    }
    val imagePath = dir.getPath + s"/${System.currentTimeMillis() + Random.nextInt}.jpg"
    val os = new FileOutputStream(imagePath)
    var len = is.read(buf)
    while (len != -1) {
      os.write(buf, 0, len)
      len = is.read(buf)
    }
    os.close()
    is.close()
    imagePath
  }
}
