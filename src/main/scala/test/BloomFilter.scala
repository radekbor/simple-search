package test

object BloomFilter {

  type Source = { def getLines(): Iterator[String] }
  def build(k: Int, n: Int): Source => BloomFilter = {
    val hashesGenerator = new HashesGenerator(k, n)
    source =>
      {
        val data = Array.ofDim[Boolean](k)

        def add(item: String): Unit = {
          val hashes = hashesGenerator.calcHashes(item)
          println(s"hashes for $item $hashes ")
          hashes.foreach(hash => data(hash) = true)
        }

        source.getLines().flatMap(_.split(" ")).foreach(add)

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
