package ir.pr.saman.service.morse.producer.contract.service

trait StartService extends Service[StartService.Body, Unit]

object StartService {

  case class Body(query: String)

}
