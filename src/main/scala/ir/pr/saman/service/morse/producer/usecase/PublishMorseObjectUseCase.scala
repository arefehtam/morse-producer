package ir.pr.saman.service.morse.producer.usecase

import java.net.ConnectException

import akka.Done
import akka.stream.alpakka.amqp._
import akka.stream.alpakka.amqp.scaladsl.AmqpFlow
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.typesafe.scalalogging.Logger
import ir.pr.saman.service.morse.producer.contract.service.PublishMorseObjectService
import ir.pr.saman.service.morse.producer.domain.MorseObject
import ir.pr.saman.service.morse.producer.exception
import ir.pr.saman.service.morse.producer.moduels.config.akka.ActorModule.actorSystem
import ir.pr.saman.service.morse.producer.moduels.config.akka.queue.StreamClient._
import org.json4s.DefaultFormats
import org.json4s.jackson.Json

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration._

trait PublishMorseObjectUseCase extends PublishMorseObjectService {

  val logger = Logger("Publisher")

  override def call(body: MorseObject)(implicit ec: ExecutionContext): Future[Done] = {
    val connectionProvider = AmqpLocalConnectionProvider
    val qName = queueName + System.currentTimeMillis()
    val queueDeclaration = QueueDeclaration(qName)
    val settings = AmqpWriteSettings(connectionProvider)
      .withRoutingKey(qName)
      .withDeclaration(queueDeclaration)
      .withBufferSize(bufferSize)
      .withConfirmationTimeout(200.millis)
    val amqpFlow: Flow[WriteMessage, WriteResult, Future[Done]] = AmqpFlow.withConfirm(settings)

    logger.info("Publishing to RabbitMQ")
    Source(Vector(body))
      .map(message => createMessage(message))
      .via(amqpFlow)
      .runWith(Sink.ignore)
      .recoverWith {
        case exp: ConnectException => Future failed exception.ServiceUnavailableException(exp + "\n" + queueName)
      }
  }

  private def createMessage(body: MorseObject): WriteMessage = {
    val jsonFormat: Json = Json(DefaultFormats)
    WriteMessage(ByteString(jsonFormat.write(body)))
  }
}

object PublishMorseObjectUseCase extends PublishMorseObjectUseCase
