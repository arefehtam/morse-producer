package ir.pr.saman.service.morse.producer.moduels

import java.net.ConnectException
import java.util.concurrent.Executors

import com.typesafe.scalalogging.Logger
import ir.pr.saman.service.morse.producer.exception.BadRequestException
import ir.pr.saman.service.morse.producer.exception.ServiceUnavailableException
import ir.pr.saman.service.morse.producer.moduels.config.ConfigModule
import ir.pr.saman.service.morse.producer.moduels.config.sttp.Client
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse
import org.json4s.jackson.Serialization.write
import sttp.client._
import sttp.model.Uri

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

trait SttpModule extends ConfigModule {

  implicit val defaultFormats: DefaultFormats.type = DefaultFormats
  implicit val executionContext: ExecutionContext = ExecutionContext fromExecutor Executors.newCachedThreadPool()
  implicit val sttpBackend: SttpBackend[Future, Nothing, asynchttpclient.WebSocketHandler] = asynchttpclient.future.AsyncHttpClientFutureBackend()
  lazy val logger: Logger = Logger(s"STTP.$serviceName")
  val serviceName: String

  def callWithBody[T <: AnyRef, U](uri: Uri, body: T, headers: Option[Map[String, String]] = None)
                                  (implicit mu: Manifest[U]): Future[U] = {
    val bodyJSON = write[T](body)
    val request = basicRequest post uri
    val requestWithHeader = headers map (request headers _) getOrElse request
    val requestWithBody = requestWithHeader body bodyJSON contentType "application/json"
    logger info s"${requestWithHeader.method.method}: ${requestWithHeader.uri.toString}"
    logger info bodyJSON
    requestWithBody.send() map { r =>
      r.body match {
        case Left(exception) => throw BadRequestException(exception, "With Error code:" + r.code + "-" + r.statusText)
        case Right(value) => parse(value).extract[U]
      }
    } recoverWith {
      case _: ConnectException =>
        Future failed ServiceUnavailableException(serviceName)
      case exception => Future failed exception
    }
  }

}

object SttpModule {


  def client(serviceName: String) = new Client(serviceName.toLowerCase)

}
