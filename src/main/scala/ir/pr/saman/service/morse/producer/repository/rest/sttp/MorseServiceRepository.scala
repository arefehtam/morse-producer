package ir.pr.saman.service.morse.producer.repository.rest.sttp

import ir.pr.saman.service.morse.producer.contract.callback.MorseServiceCallback
import ir.pr.saman.service.morse.producer.domain.MorseObject
import ir.pr.saman.service.morse.producer.moduels.SttpModule
import ir.pr.saman.service.morse.producer.repository.rest.sttp.adapter.MorseObjectDTO
import ir.pr.saman.service.morse.producer.repository.rest.sttp.adapter.TranslateBodyDTO
import sttp.client.UriContext

import scala.concurrent.Future

trait MorseServiceRepository extends MorseServiceCallback with SttpModule {

  override val serviceName = "morse-service"
  private val client = SttpModule.client(serviceName)

  override def call(query: String): Future[MorseObject] = {
    val uri = uri"""http://${client.host}:${client.port}/api/v1/services/codes/morse/translate"""
    val bodyDTO = TranslateBodyDTO(query)
    callWithBody[TranslateBodyDTO, MorseObjectDTO](uri, bodyDTO) map Factory.morseObject
  }
}

object MorseServiceRepository extends MorseServiceRepository
