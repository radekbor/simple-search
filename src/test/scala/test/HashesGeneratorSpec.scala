package test

import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.wordspec.AnyWordSpec

class HashesGeneratorSpec extends AnyWordSpec with Matchers {

  private val bits = Table("bits", 20, 30, 40, 50, 60)
  private val hashes = Table("hashes", 3, 4, 5, 6, 7, 8, 9)

  "HashesGenerator" should {
    "generate vector of proper length with given number of bits" in {
      forAll(bits) { numOfBits =>
        forAll(hashes) { numOfHashes =>
          val hashesGenerator = new HashesGenerator(numOfBits, numOfHashes)
          val result = hashesGenerator.calcHashes(Word.fromLine("veryLongText").head)

          result should have size numOfHashes
          result.min should be >= 0
          result.max should be < numOfBits

        }
      }
    }

    "generate different hashes for different words" in {
      val words = io.Source.fromResource("test_2/shakespeare.txt")
        .getLines()
        .flatMap(Word.fromLine)

      val n = words.size.toDouble
      val m = 10000
      val k = 8
      val hashesGenerator = new HashesGenerator(m, k)

      val hashes = words.map(hashesGenerator.calcHashes)

      val p = Math.pow(1 - Math.exp(-k * n / m), k)
      println(f"probability that this won't work $p%1.14f")
      hashes.distinct should have size words.size

    }
  }
}
