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

  //TODO add NotInitialized message to indicate the ReverseActor is not ready yet,
  // which extends Result trait like the other Result messages
}

class ReverseActor extends Actor {
  import ReverseActor._

  import context._

  //TODO send Init to self

  // TODO the receive method should be set to the uninitialized Receive function

  def receive = uninitialized

  // TODO create an uninitialized Receive method
  def uninitialized:Receive = {
  }

  def initialized(reverse: AsyncReverseFunction):Receive = {
  }

}
