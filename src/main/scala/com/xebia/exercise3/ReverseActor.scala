package com.xebia.exercise3

import akka.actor.{Actor, Props}
import com.xebia.exercise3.ReverserFactory.ReverseFunction
import scala.concurrent.duration._
import scala.util.{Success, Failure}

object ReverseActor {
  def props = Props[ReverseActor]
  def name = "reverser"

  case class Reverse(value:String)
  case class ReverseResult(value:String)
  case object PalindromeResult

  //TODO add Init message which the ReverseActor sends to itself once
  // the ReverseFunction is available.
  case object Init
  case object NotInitialized
}

class ReverseActor extends Actor {
  import ReverseActor._

  import context._

  //TODO send Init to self
  self ! Init


  //TODO Use the ReverseFunction that is loaded by the ReverserFactory
  // asynchronously,
  // which will give us 'ultimate speed' ;-)
  // TODO create an uninitialized Receive method
  // TODO the receive method should be set to the uninitialized Receive function

  def receive = uninitialized

  def uninitialized:Receive = {
    case Init => ReverserFactory.loadReverser.onComplete {
      case Success(reverse) => context.become(initialized(reverse))
      case Failure(e)       => context.system.scheduler.scheduleOnce(1 seconds, self, Init)
    }
    //TODO send back NotInitialized
    case Reverse(value) => sender ! NotInitialized
  }

  //TODO change the initialized Receive function to take a reverserFunction
  // argument
  def initialized(reverse: ReverseFunction):Receive = {
    case Reverse(value) =>
      val reversed = reverse(value)
      if(reversed == value) sender ! PalindromeResult
      else sender ! ReverseResult(reversed)
  }

}
