import org.scalatest._

class Test extends FlatSpec with Matchers {


  "normal data" should "add up properly" in {

    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      PriceAnalyzer.analyzePrices(io.Source.fromFile("src/test/resources/datanormal"))
    }
    stream.toString should be(
      """T          V       N RS       MinV    MaxV
        |---------------------------------------------
        |1 1.5 1 1.5 1.5 1.5
        |2 1.1 2 2.6 1.1 1.5
        |3 3.2 3 5.8 1.1 3.2
        |4 4.3 4 10.1 1.1 4.3
        |""".stripMargin)

  }


  "data with hole" should "not add up" in {

    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      PriceAnalyzer.analyzePrices(io.Source.fromFile("src/test/resources/datahole"))
    }
    stream.toString should be(
      """T          V       N RS       MinV    MaxV
        |---------------------------------------------
        |1 1.0 1 1.0 1.0 1.0
        |2 2.1 2 3.1 1.0 2.1
        |3 3.2 3 6.3 1.0 3.2
        |4 4.3 4 10.6 1.0 4.3
        |91 1.0 1 1.0 1.0 1.0
        |92 2.1 2 3.1 1.0 2.1
        |93 3.2 3 6.3 1.0 3.2
        |94 4.3 4 10.6 1.0 4.3
        |""".stripMargin)

  }


  "data with big hole" should "not add up" in {

    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      PriceAnalyzer.analyzePrices(io.Source.fromFile("src/test/resources/databighole"))
    }
    stream.toString should be(
      """T          V       N RS       MinV    MaxV
        |---------------------------------------------
        |1 1.0 1 1.0 1.0 1.0
        |2 2.1 2 3.1 1.0 2.1
        |3 3.2 3 6.3 1.0 3.2
        |4 4.3 4 10.6 1.0 4.3
        |761 1.0 1 1.0 1.0 1.0
        |762 2.1 2 3.1 1.0 2.1
        |763 3.2 3 6.3 1.0 3.2
        |764 4.3 4 10.6 1.0 4.3
        |""".stripMargin)

  }

  "data with overlap" should "should add up properly" in {

    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      PriceAnalyzer.analyzePrices(io.Source.fromFile("src/test/resources/dataoverlap"))
    }
    stream.toString should be(
      """T          V       N RS       MinV    MaxV
        |---------------------------------------------
        |1 1.0 1 1.0 1.0 1.0
        |2 2.1 2 3.1 1.0 2.1
        |3 3.2 3 6.3 1.0 3.2
        |4 4.3 4 10.6 1.0 4.3
        |61 1.0 4 10.6 1.0 4.3
        |62 2.1 4 10.6 1.0 4.3
        |63 3.2 4 10.6 1.0 4.3
        |64 4.3 4 10.6 1.0 4.3
        |""".stripMargin)

  }
}
