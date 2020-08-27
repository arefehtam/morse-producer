package ir.pr.saman.service.morse.producer.exception

case class ServiceUnavailableException(what: String) extends Exception {

  override def getMessage: String = what + " is not available"
}
