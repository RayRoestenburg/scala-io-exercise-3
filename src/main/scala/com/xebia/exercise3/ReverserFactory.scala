package com.xebia.exercise3

import scala.concurrent.{ExecutionContext, Future}

object ReverserFactory {
  type AsyncReverseFunction = String => Future[String]

  //TODO load the AsyncReverseFunction with this async method
  def loadReverser(implicit executionContext:ExecutionContext): Future[AsyncReverseFunction] = {
    Future{
      Thread.sleep(500)
      (value => Future.successful(value.reverse)):AsyncReverseFunction
    }
  }
}