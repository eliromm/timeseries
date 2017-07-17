import java.text.DecimalFormat

import scala.collection.mutable
import scala.io.BufferedSource

object PriceAnalyzer {

  def analyzePrices(bufferedSource: BufferedSource): Unit = {
    val df = new DecimalFormat(".#####")
    val windowSize = 60

    println(s"T          V       N RS       MinV    MaxV")
    println("---------------------------------------------")

    val window = Array.fill(windowSize)(new mutable.ListBuffer[Data])
    var sum = 0.0
    var count = 0
    val tree = new mutable.TreeSet[Data]()

    var lastTimestamp: Long = 0
    var lastIndex = 0

    for (line <- bufferedSource.getLines) {
      val cols = line.split("\\s")
      val timestamp = cols(0).toLong

      val price = cols(1).toDouble

      val index = (timestamp % windowSize).toInt
      val diff = timestamp - lastTimestamp

      val iterations = (diff % windowSize + (if (diff > windowSize) windowSize else 0)).toInt
      for (i <- 0 until iterations) {
        lastIndex = (lastIndex + 1) % windowSize

        val datas = window(lastIndex)
        datas.foreach(oldData => {
          tree -= oldData
          count -= 1
          sum -= oldData.double
        })

        datas.clear()
      }

      lastTimestamp = timestamp
      count += 1
      sum += price

      val data = Data(price)
      tree += data
      window(index) += data

      println(f"$timestamp ${df.format(price)} $count ${df.format(sum)} ${df.format(tree.min.double)} ${df.format(tree.max.double)}")
    }

    bufferedSource.close
  }


  def main(args: Array[String]): Unit = {

//    "src/main/resources/data"
    analyzePrices(io.Source.fromFile(args(0)))
  }
}