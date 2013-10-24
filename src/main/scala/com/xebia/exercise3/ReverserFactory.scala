package com.xebia.exercise3

import scala.concurrent.{ExecutionContext, Future}

object ReverserFactory {
  type AsyncReverseFunction = String => Future[String]

  //TODO use the ReverseFunction which takes some time to initialize (asynchronously)
  def loadReverser(implicit executionContext:ExecutionContext): Future[AsyncReverseFunction] = {
    Future{
      Thread.sleep(500)
      (value => Future.successful(value.reverse)):AsyncReverseFunction
    }
  }
}