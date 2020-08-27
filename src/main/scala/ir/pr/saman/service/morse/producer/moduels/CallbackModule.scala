package ir.pr.saman.service.morse.producer.moduels

import ir.pr.saman.service.morse.producer.contract.callback.MorseServiceCallback
import ir.pr.saman.service.morse.producer.repository.rest.sttp.MorseServiceRepository

object CallbackModule {

  val morseServiceCallback: MorseServiceCallback = MorseServiceRepository

}
