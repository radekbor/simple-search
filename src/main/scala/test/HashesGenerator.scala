package test

protected object Rotator {
  def shift(in: String, x: Int): String = {
    val n = x % in.length
    in.substring(n, in.length) + in.substring(0, n)
  }
}

class HashesGenerator(val k: Int, val n: Int) {

  def calcHashes(item: String): Seq[Int] = {
    Range(0, n)
      .map(p => Rotator.shift(item, p))
      .map(str => str.hashCode % k)
      .map(Math.abs)
  }

}
