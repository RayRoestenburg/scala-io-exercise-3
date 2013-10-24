package com.xebia.exercise3

import scala.concurrent.{ExecutionContext, Future}

object ReverserFactory {
  type ReverseFunction = String => String

  //TODO use the ReverseFunction which takes some time to initialize (asynchronously)
  def loadReverser(implicit executionContext:ExecutionContext): Future[ReverseFunction] = {
    Future{
      Thread.sleep(500)
      (value => value.reverse):ReverseFunction
    }
  }
}