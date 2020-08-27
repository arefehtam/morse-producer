package ir.pr.saman.service.morse.producer.repository.rest.sttp

import ir.pr.saman.service.morse.producer.domain.MorseObject
import ir.pr.saman.service.morse.producer.repository.rest.sttp.adapter.MorseObjectDTO

object Factory {

  def morseObject(dto: MorseObjectDTO): MorseObject = MorseObject(dto.query, dto.morseCode, dto.queryEncoding)

}
