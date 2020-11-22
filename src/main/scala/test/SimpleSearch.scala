package test
import java.io.File

import scala.util.Try

object SimpleSearch extends App {
  Program
    .readFile(args)
    .fold(println, file => Program.iterate(Program.index(file)))
}

object Program {
  private val vectorSize = 400000000
  private val shifts = 5
  import scala.io.StdIn.readLine

  case class Index(fileIndexes: List[(String, BloomFilter)])

  sealed trait ReadFileError

  case object MissingPathArg extends ReadFileError
  case class NotDirectory(error: String) extends ReadFileError
  case class FileNotFound(t: Throwable) extends ReadFileError

  def readFile(args: Array[String]): Either[ReadFileError, File] = {
    for {
      path <- args.headOption.toRight(MissingPathArg)
      file <- Try(new java.io.File(path))
        .fold(
          throwable => Left(FileNotFound(throwable)),
          file =>
            if (file.isDirectory) Right(file)
            else Left(NotDirectory(s"Path [$path] is not a directory"))
        )
    } yield file
  }

  // TODO: Index all files in the directory
  def index(file: File): Index = {
    val bloomFilterFactory = BloomFilter.build(vectorSize, shifts)
    if (file.isDirectory) {
      val l = file
        .listFiles()
        .map(
          file => (file.getName, bloomFilterFactory(io.Source.fromFile(file)))
        )
      Index(l.toList)
    } else {
      Index(List())
    }

  }

  def iterate(indexedFiles: Index): Unit = {
    print(s"search> ")
    val searchString = readLine()
    if (searchString != ":quit") {
      val message = indexedFiles.fileIndexes
        .map {
          case (fileName, bloomFilter) =>
            val words = Word.fromLine(searchString)

            val (totalCount, totalSum) = words
              .map(bloomFilter.isIn)
              .map {
                case true  => 100
                case false => 0
              }
              .foldLeft((0, 0)) {
                case ((count, sum), current) => (count + 1, sum + current)
              }

            (fileName, totalSum / totalCount.toFloat)
        }
        .sortBy(_._1)
        .take(10)
        .map {
          case (name, percentage) => s"$name : $percentage%"
        }
        .mkString(" ")
      println(message)
      iterate(indexedFiles)
    }

  }
}
