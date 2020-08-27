package ir.pr.saman.service.morse.producer.moduels

import ir.pr.saman.service.morse.producer.contract.service.GetMorseObjectService
import ir.pr.saman.service.morse.producer.contract.service.PublishMorseObjectService
import ir.pr.saman.service.morse.producer.contract.service.StartService
import ir.pr.saman.service.morse.producer.usecase.GetMorseObjectUseCase
import ir.pr.saman.service.morse.producer.usecase.PublishMorseObjectUseCase
import ir.pr.saman.service.morse.producer.usecase.StartUseCase

object ServiceModule {

  val getMorseObjectService: GetMorseObjectService = GetMorseObjectUseCase
  val publishMorseObjectService: PublishMorseObjectService = PublishMorseObjectUseCase
  val startService: StartService = StartUseCase

}
