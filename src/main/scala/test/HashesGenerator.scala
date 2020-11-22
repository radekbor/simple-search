package test

protected object Rotator {
  def shift(in: String, x: Int): String = {
    val n = x % in.length
    in.substring(n, in.length) + in.substring(0, n)
  }
}

class HashesGenerator(val m: Int, val k: Int) {

  def calcHashes(item: Word): Seq[Int] = {
    Range(0, k)
      .map(p => Rotator.shift(item.raw, p))
      .map(str => str.hashCode % m)
      .map(Math.abs)
  }

}
