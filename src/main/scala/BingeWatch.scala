import scala.util.Try
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import scala.sys.process._

object BingeWatch extends App {
  val config = ConfigurationParser.makeConfiguration(args)
  val hist = Paths.get(".bw")

  val videos = FileUtils.listFiles(Paths.get(""))
    .map(_.getFileName.toString)
    .filter(isMovie)
  def isMovie(file: String): Boolean =
    List(".mkv", ".mp4", ".avi").exists(file.endsWith)

  val sortedVideos = videos.sortWith(extractNumbers(_) < extractNumbers(_))
  def extractNumbers(fileName: String): BigInt = {
    val digits = fileName.map(_.toInt.toString).flatten.mkString
    BigInt(digits)
  }

  val previousVideo = FileUtils.read(hist).toOption
  val newVideo = previousVideo.map { file =>
    val currentVideos = sortedVideos.dropWhile(_ != file)
    val newVideos = if (config.next) currentVideos.tail else currentVideos
    newVideos.headOption
  }.flatten.orElse(sortedVideos.headOption)

  newVideo match {
    case Some(video) =>
      FileUtils.write(hist, video)
      config.buildSubtitleSeq(video).!
      config.buildVideoSeq(video).!
    case _ =>
      error("Sorry, we can't seem to find a video in this directory!")
  }
}