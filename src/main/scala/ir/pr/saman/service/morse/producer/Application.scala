package ir.pr.saman.service.morse.producer

import com.typesafe.scalalogging.Logger
import ir.pr.saman.service.morse.producer.contract.service.StartService
import ir.pr.saman.service.morse.producer.moduels.ServiceModule.startService
import ir.pr.saman.service.morse.producer.moduels.config.akka.ActorModule.ec
/**
  * Application is the main object. It shows a user each time to type something as a query in a never ending circuit until
  * a user press "y" to exit the program
  * any error with is this process is captured and shown
*/

object Application extends App {
  val logger = Logger("Root App")

  do {
    println("Enter a query string:")
    val query = scala.io.StdIn.readLine()
    startService call StartService.Body(query) map { _ =>
      print("Do you wan to exit[y|N]:")
    } recover {
      case err => logger error (err.getMessage + "\n" + err.getStackTrace.toString)
    }
  } while (scala.io.StdIn.readLine != "y")
  println("Exiting Producer Scope...")
}
