package test

case class Word private[Word] (raw: String) extends AnyVal
object Word {

  /**
    * TODO
    * What constitutes two words being equal (and matching)?
    * */
  private def normalize(word: String): Word = Word(word)

  /**
    * TODO
    * What constitutes a word?
    * */
  private def isAWord(word: String): Boolean = {
    word.nonEmpty
  }

  def fromLine(line: String): Seq[Word] =
    line
      .split(" ")
      .toList
      .filter(isAWord)
      .map(normalize)

}
