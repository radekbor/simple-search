package test

import org.scalatest.wordspec.AnyWordSpec

object BloomFilterSpec extends AnyWordSpec {

  "BloomFilter" should {
    "return true for added item" in {


      BloomFilter.build(10, 3)

    }
  }
}
