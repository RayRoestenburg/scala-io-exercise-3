package com.xebia
package exercise3

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.{Failure, Success}

import akka.actor.{Props, ActorRef}
import akka.util.Timeout

import spray.routing._
import spray.httpx.SprayJsonSupport._
import spray.http.StatusCodes

class Receptionist extends HttpServiceActor
                      with ReverseRoute
                      with ActorContextCreationSupport {
  implicit def executionContext = context.dispatcher

  def receive = runRoute(reverseRoute)
}


trait ReverseRoute extends HttpService with CreationSupport {
  import ReverseActor._

  implicit def executionContext: ExecutionContext

  private val reverseActor = createChild(props, name)

  def reverseRoute:Route = path("reverse") {
    post {
      entity(as[ReverseRequest]) { request =>
        implicit val timeout = Timeout(20 seconds)
        import akka.pattern.ask

        val futureResponse = reverseActor.ask(Reverse(request.value)).mapTo[Result]

        onComplete(futureResponse) {
          case Success(PalindromeResult) => complete(ReverseResponse(request.value, true))
          case Success(ReverseResult(value)) => complete(ReverseResponse(value, false))
          case Success(NotInitialized) => complete(StatusCodes.ServiceUnavailable)
          case Failure(e) => complete(StatusCodes.InternalServerError)
        }
      }
    }
  }
}

