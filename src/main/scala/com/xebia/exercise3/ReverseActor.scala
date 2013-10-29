package com.xebia
package exercise3

import scala.concurrent.duration._
import scala.util.{Success, Failure}

import akka.actor.{Actor, Props}

import ReverserFactory.AsyncReverseFunction

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

  //TODO send Init to self (right here or override preStart)

  // TODO the receive method should be set to the uninitialized Receive function

  def receive:Receive = {

  }

  // TODO create an uninitialized Receive method
  // Send back NotInitialized on a Reverse message.
  // Load the AsyncReverseFunction using the ReverserFactory on receiving Init.
  // call become to transition to initialized state once the Future completes successfully.
  // pass through the AsyncReverseFunction to the initialized Receive function instead of using a var.

  def uninitialized:Receive = {
  }

  //TODO Implement the initialized Receive function:
  // call the AsyncReverseFunction,
  // on completion of the Future send back to a **captured sender**
  // (do not close over sender but create a val which contains the sender at the time of receiving the Reverse message).
  def initialized(reverse: AsyncReverseFunction):Receive = {
  }

}
