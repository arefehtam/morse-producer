package ir.pr.saman.service.morse.producer.moduels.config.sttp

import ir.pr.saman.service.morse.producer.moduels.config.ConfigModule

class Client(name: String) extends ConfigModule {

  lazy val host: String = config.getString(s"$prefixPath.$name.host")
  lazy val port: Int = config.getInt(s"$prefixPath.$name.port")
  private lazy val prefixPath: String = "sttp.rest.client"

}
