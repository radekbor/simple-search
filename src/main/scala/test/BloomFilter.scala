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

        def add(item: Word): Unit = {
          logger.debug(s"Going to generate hashes for '${item.raw}'")
          val hashes = hashesGenerator.calcHashes(item)
          logger.debug(s"Hashes for '${item.raw}': $hashes")
          hashes.foreach(hash => data(hash) = true)
        }

        source.getLines
          .flatMap(Word.fromLine)
          .foreach(add)

        new BloomFilter(data.toVector, hashesGenerator)
      }
  }

}

class BloomFilter(vector: Vector[Boolean], hashesGenerator: HashesGenerator) {

  def isIn(word: Word): Boolean = {

    val hashes = hashesGenerator.calcHashes(word)
    hashes.forall(hash => vector(hash))
  }

}
