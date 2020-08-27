package ir.pr.saman.service.morse.producer.contract.service

import akka.Done
import ir.pr.saman.service.morse.producer.domain.MorseObject

trait PublishMorseObjectService extends Service[MorseObject, Done]
