package com.zwd51.fasteye

import java.io.{File, FileInputStream}
import javax.imageio.ImageIO

import net.semanticmetadata.lire.DocumentBuilderFactory
import net.semanticmetadata.lire.utils.LuceneUtils

import scala.util.{Failure, Success, Try}

/**
  * Created by Connor on 12/13/15.
  */
object Indexer {
  def main(args: Array[String]): Unit = {
    val dir = sys.env("FASTEYE_IMAGES_DIR")
    val file = new File(dir)
    val images = file.list
    val documentBuilder = DocumentBuilderFactory.getCEDDDocumentBuilder
    val iw = LuceneUtils.createIndexWriter(sys.env("FASTEYE_INDEX_DIR"), false, LuceneUtils.AnalyzerType.WhitespaceAnalyzer)
    val startTime = System.currentTimeMillis
    images.foreach { image =>
      println(s"indexing:$image")
      val bufferedImage = ImageIO.read(new FileInputStream(s"$dir/$image"))
      val document = Try(documentBuilder.createDocument(bufferedImage, image.split('.')(0)))
      document match {
        case Success(d) => iw.addDocument(d)
        case Failure(e) => println(s"error:$image")
      }
    }
    iw.close()
    println(s"finished, elapsed time:${System.currentTimeMillis - startTime}")
  }
}
