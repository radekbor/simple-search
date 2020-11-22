package test

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class WordSpec extends AnyWordSpec with Matchers {

  "Word" should {
    "be recognized as word when non empty" in {
      val word = Word.fromLine("hello").head
      word.raw should be ("hello")
    }

    "split multiple words by space" in {
      val word = Word.fromLine("hello word")
      word.map(_.raw) should be (List("hello", "word"))
    }
  }

}
