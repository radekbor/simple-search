package test
import org.slf4j.LoggerFactory

object BloomFilter {

  private val logger = LoggerFactory.getLogger(getClass.getSimpleName)

  type Source = { def getLines(): Iterator[String] }
  def build(bits: Int, hashes: Int): Source => BloomFilter = {
    val hashesGenerator = new HashesGenerator(bits, hashes)
    source =>
      {
        val data = Array.ofDim[Boolean](bits)

        def add(item: String): Unit = {
          logger.debug(s"Going to generate hashes for '$item'")
          val hashes = hashesGenerator.calcHashes(item)
          logger.debug(s"Hashes for '$item': $hashes")
          hashes.foreach(hash => data(hash) = true)
        }

        source.getLines.flatMap(_.split(" ").filter(_.nonEmpty)).foreach(add)

        new BloomFilter(data.toVector, hashesGenerator)
      }
  }
}

class BloomFilter(vector: Vector[Boolean], hashesGenerator: HashesGenerator) {

  def isIn(item: String): Boolean = {
    val hashes = hashesGenerator.calcHashes(item)
    hashes.forall(hash => vector(hash))
  }

}
