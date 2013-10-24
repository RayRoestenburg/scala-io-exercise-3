package com.xebia.exercise3

import akka.actor.{Actor, Props}
import com.xebia.exercise3.ReverserFactory.AsyncReverseFunction
import scala.concurrent.duration._
import scala.util.{Success, Failure}

object ReverseActor {
  def props = Props[ReverseActor]
  def name = "reverser"

  sealed trait Result
  case class Reverse(value:String)

  case class ReverseResult(value:String) extends Result

  case object PalindromeResult extends Result

  //TODO add Init message which the ReverseActor sends to itself once
  // the ReverseFunction is available.
  case object Init

  //TODO add NotInitialized message to indicate the ReverseActor is not ready yet,
  // which extends Result trait like the other Result messages
  case object NotInitialized extends Result
}

class ReverseActor extends Actor {
  import ReverseActor._

  import context._

  //TODO send Init to self
  self ! Init




  // TODO the receive method should be set to the uninitialized Receive function

  def receive = uninitialized

  // TODO create an uninitialized Receive method
  def uninitialized:Receive = {
    // TODO when Init is received call loadReverser on  the ReverserFactory and once
    // the future successfully completes become initialized

    case Init => ReverserFactory.loadReverser.onComplete {
      case Success(reverse) => context.become(initialized(reverse))
      case Failure(e)       => context.system.scheduler.scheduleOnce(1 seconds, self, Init)
    }
    //TODO send back NotInitialized
    case Reverse(value) => sender ! NotInitialized
  }

  def initialized(reverse: AsyncReverseFunction):Receive = {
    case Reverse(value) =>
      // TODO 'capture' the sender
      val theSender = sender

      // TODO Use the ReverseFunction that is loaded by the ReverserFactory asynchronously
      reverse(value).map { reversed =>
        if(reversed == value) theSender ! PalindromeResult
        else theSender ! ReverseResult(reversed)
      }
  }

}
