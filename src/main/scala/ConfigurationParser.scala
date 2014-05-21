object ConfigurationParser {
  case class Configuration(
      mediaPlayer: String = "mpv",
      subtitleDownloader: String = "subberthehut -qsf",
      next: Boolean = true) {

    def buildVideoSeq(video: String): Seq[String] =
      mediaPlayer.split(" ") :+ video

    def buildSubtitleSeq(video: String): Seq[String] =
      subtitleDownloader.split(" ") :+ video
  }

  def makeConfiguration(args: Array[String]): Configuration = {
    def parseArgs(args: List[String], config: Configuration): Configuration = {
      args match {
        case ("--media-player" | "-m") :: mp :: rest =>
          parseArgs(rest, config.copy(mediaPlayer = mp))
        case ("--subtitle-downloader" | "-s") :: sd :: rest =>
          parseArgs(rest, config.copy(subtitleDownloader = sd))
        case ("--no-skip" | "-n") :: rest =>
          parseArgs(rest, config.copy(next = false))
        case _ :: rest => parseArgs(rest, config)
        case Nil       => config
      }
    }
    parseArgs(args.toList, Configuration())
  }

}