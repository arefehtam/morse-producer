package ir.pr.saman.service.morse.producer.usecase

import com.typesafe.scalalogging.Logger
import ir.pr.saman.service.morse.producer.contract.service.StartService
import ir.pr.saman.service.morse.producer.moduels.ServiceModule._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

trait StartUseCase extends StartService {

  val logger = Logger("Starter")

  override def call(body: StartService.Body)(implicit ec: ExecutionContext): Future[Unit] = for {
    morseObject <- getMorseObjectService call body
    _ <- publishMorseObjectService call morseObject
  } yield logger info "Done Publishing Morse Object"

}

object StartUseCase extends StartUseCase
