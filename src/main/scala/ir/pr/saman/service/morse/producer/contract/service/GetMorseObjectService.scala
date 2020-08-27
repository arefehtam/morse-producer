package ir.pr.saman.service.morse.producer.contract.service

import ir.pr.saman.service.morse.producer.domain.MorseObject

trait GetMorseObjectService extends Service[StartService.Body, MorseObject]
