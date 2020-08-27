package ir.pr.saman.service.morse.producer.exception

case class BadRequestException(which: String, what: String) extends Exception {

  override def getMessage: String = s"$which: $what"

}
