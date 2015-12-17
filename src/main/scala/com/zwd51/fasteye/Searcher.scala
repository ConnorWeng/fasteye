package com.zwd51.fasteye

import java.awt.image.BufferedImage
import java.io.{File, FileInputStream}
import javax.imageio.ImageIO

import net.semanticmetadata.lire.DocumentBuilder
import net.semanticmetadata.lire.imageanalysis.CEDD
import net.semanticmetadata.lire.impl.GenericFastImageSearcher
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.store.FSDirectory

/**
  * Created by Connor on 12/13/15.
  */
object Searcher {
  val ir = DirectoryReader.open(FSDirectory.open(new File(sys.env("FASTEYE_INDEX_DIR"))))
  val searcher = new GenericFastImageSearcher(6, classOf[CEDD])

  def search(image: BufferedImage): Seq[String] = {
    val hits = searcher.search(image, ir)
    val goodIds = for (i <- 0 to hits.length - 1)
      yield s"'${hits.doc(i).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)(0)}'"
    goodIds
  }

  def close() = {
    ir.close()
  }

  def main(args: Array[String]) {
    val dir = sys.env("FASTEYE_IMAGES_DIR")
    val file = new File(dir)
    val images = file.list
    println(s"searching $dir/${images(3)}")
    val bufferedImage = ImageIO.read(new FileInputStream(s"$dir/${images(3)}"))
    val ir = DirectoryReader.open(FSDirectory.open(new File(sys.env("FASTEYE_INDEX_DIR"))))
    val searcher = new GenericFastImageSearcher(5, classOf[CEDD])
    val hits = searcher.search(bufferedImage, ir)
    for (i <- 0 to hits.length - 1) {
      val fileName = hits.doc(i).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)(0)
      println(s"$fileName:${hits.score(i)}")
    }
    ir.close()
    println("finish")
  }
}
