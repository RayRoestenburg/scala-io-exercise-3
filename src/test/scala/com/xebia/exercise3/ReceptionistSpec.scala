package com.xebia.exercise3

import spray.testkit.Specs2RouteTest
import org.specs2.mutable.Specification

import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._

import akka.actor.{Actor, Props, ActorRef, ActorRefFactory}

class ReceptionistSpec extends Specification with Specs2RouteTest {

  trait TestCreationSupport extends CreationSupport {
    def createChild(props: Props, name: String): ActorRef = system.actorOf(Props[FakeReverseActor], "fakereverse")

    def getChild(name: String): Option[ActorRef] = None
  }

  val subject = new ReverseRoute with TestCreationSupport {
    implicit def actorRefFactory: ActorRefFactory = system
  }

  "The Receptionist" should {
    "Respond with a JSON response that contains a reversed string value" in {

      Post("/reverse", ReverseRequest("some text to reverse")) ~> subject.reverseRoute ~> check {
        status === StatusCodes.OK
        val response = entityAs[ReverseResponse]
        response.value must beEqualTo("esrever ot txet emos")
        response.isPalindrome must beFalse
      }

      Post("/reverse", ReverseRequest("akka")) ~> subject.reverseRoute ~> check {
        status === StatusCodes.OK
        val response = entityAs[ReverseResponse]
        response.value must beEqualTo("akka")
        response.isPalindrome must beTrue
      }

    }
  }
}

class FakeReverseActor extends Actor {
  import ReverseActor._

  def receive = {
    case Reverse("akka") => sender ! PalindromeResult
    case Reverse("some text to reverse") => sender ! ReverseResult("esrever ot txet emos")
  }
}
