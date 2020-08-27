package ir.pr.saman.service.morse.producer.usecase

import akka.event.jul.Logger
import ir.pr.saman.service.morse.producer.contract.service.GetMorseObjectService
import ir.pr.saman.service.morse.producer.contract.service.StartService
import ir.pr.saman.service.morse.producer.domain.MorseObject
import ir.pr.saman.service.morse.producer.moduels.CallbackModule.morseServiceCallback

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

trait GetMorseObjectUseCase extends GetMorseObjectService {

  val logger = Logger("GetMorseObject")

  /**
    * It calls morse service and get morse object which contains information of original query in base64 and the morsecode for it
    * @param body.query
    * @param ec: implicit execution context for asynchronous communication with morse service
    * @return MorseObject which is defined in domain package
    */

  override def call(body: StartService.Body)(implicit ec: ExecutionContext): Future[MorseObject] = {
    logger info "Calling Morse Service..."
    morseServiceCallback call body.query
  }

}

object GetMorseObjectUseCase extends GetMorseObjectUseCase
