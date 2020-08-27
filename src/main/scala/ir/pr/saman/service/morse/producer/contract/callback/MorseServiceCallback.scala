package ir.pr.saman.service.morse.producer.contract.callback

import ir.pr.saman.service.morse.producer.domain.MorseObject

import scala.concurrent.Future

trait MorseServiceCallback {

  def call(query: String): Future[MorseObject]

}
