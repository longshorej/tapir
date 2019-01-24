package tapir

import java.nio.charset.{Charset, StandardCharsets}

trait MediaType {
  def mediaTypeNoParams: String
  def mediaType: String = mediaTypeNoParams
}

object MediaType {
  case class Json() extends MediaType {
    override val mediaTypeNoParams: String = "application/json"
  }

  case class TextPlain(charset: Charset = StandardCharsets.UTF_8) extends MediaType {
    override val mediaTypeNoParams: String = s"text/plain"
    override val mediaType: String = s"$mediaTypeNoParams; charset=${charset.name()}"
  }

  case class OctetStream() extends MediaType {
    override val mediaTypeNoParams: String = "application/octet-stream"
  }

  case class XWwwFormUrlencoded() extends MediaType {
    override def mediaTypeNoParams: String = "application/x-www-form-urlencoded"
  }
}
