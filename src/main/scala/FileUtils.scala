import scala.collection.JavaConverters._
import java.nio.file.Files
import java.nio.file.Path
import java.nio.charset.StandardCharsets
import java.nio.file.StandardOpenOption
import scala.util.Try

object FileUtils {
  def write(path: Path, contents: String) =
    Files.write(path, contents.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE)

  def read(path: Path): Try[String] =
    Try(Files.readAllLines(path, StandardCharsets.UTF_8).asScala.mkString)

  def listFiles(path: Path): List[Path] =
    Files.newDirectoryStream(path).asScala.toList
}