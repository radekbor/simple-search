package test

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BloomFilterSpec extends AnyWordSpec with Matchers {

  private def createSource(words: Seq[String]): BloomFilter.Source = new Object {
    def getLines(): Iterator[String] = words.iterator
  }

  implicit def toWord(word: String): Word = Word.fromLine(word).head

  "BloomFilter build based on 10 and 3" should {
    val filterFactory = BloomFilter.build(10, 3)
    "return true for added item" in {

      val filter = filterFactory(createSource(Seq("A")))

      filter.isIn("A") should be (true)
    }

    "return false when no items added" in {
      val filter = filterFactory(createSource(Seq()))

      filter.isIn("A") should be (false)
    }

    "be able to check word existence in large file" in {
      val words = io.Source.fromResource("test_1")

      val filter = filterFactory(words)

      filter.isIn("Poland") should be (true)
      filter.isIn("poland") should be (false)
    }

  }
}
