package com.xebia
package exercise3

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import spray.can.Http.Bind

object Main extends App {

  implicit val system = ActorSystem("exercise-3")

  val receptionist = system.actorOf(Props[Receptionist], "receptionist")

  IO(Http) ! Bind(listener= receptionist, interface = "0.0.0.0", port=8000)
}

