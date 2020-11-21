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
          val result = hashesGenerator.calcHashes("veryLongText")

          result should have size numOfHashes
          result.min should be >= 0
          result.max should be < numOfBits

        }
      }
    }

    "generate different hashes for different words" in {
      val words = io.Source.fromResource("test_2/plain_unique_words.txt")
        .getLines()
        .flatMap(_.split(" "))
        .filter(_.nonEmpty)
        .toList
        .distinct

      val hashesGenerator = new HashesGenerator(4000, 23)

      val hashes = words.map(hashesGenerator.calcHashes)

      hashes.distinct should have size words.size

    }
  }
}
