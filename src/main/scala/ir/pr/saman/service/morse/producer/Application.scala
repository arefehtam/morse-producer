package ir.pr.saman.service.morse.producer

import com.typesafe.scalalogging.Logger
import ir.pr.saman.service.morse.producer.contract.service.StartService
import ir.pr.saman.service.morse.producer.moduels.ServiceModule.startService
import ir.pr.saman.service.morse.producer.moduels.config.akka.ActorModule.ec

object Application extends App {
  val logger = Logger("Root App")
  println("Enter a query string:")

  val query = scala.io.StdIn.readLine()
  startService call StartService.Body(query) recover {
    case err => logger error err.getMessage
  }
}
