Exercise 3
==========

In this exercise we continue with the end result of [exercise 2](http://github/RayRoestenburg/scala-io-exercise-2).

In the previous exercise we ended up with a **Receptionist** that could be unit tested with a FakeReverseActor. 

But we have a problem. Obviously we're not making any money with calling **String.reverse**! It's time to bring in a shiny new non-blocking async library to do the work for us.  

In this exercise we (will stretch our imagination even further and) use a powerful async client library for reversing strings that is just off the charts! The code now has a ReverserFactory which can load an AsyncReverseFunction (which is a type alias for type String => Future[String]) which can be used by the ReverseActor.

###Objective

The objective of this exercise is to learn how to initialize an actor with some state that can only be 'loaded'/initialized asynchronously. We want to prevent using a var which is initially not filled, and having to deal with that fact all the time in the receive function. 

Instead we will define two states / Receive functions, **uninitialized** and **initialized**. The receive function will first be assigned **uninitialized**. In the uninitialized state the loading of the AsyncReverseFunction is started. Once the Future completes the actor **becomes** initialized with the become method. Since the Receive type is a PartialFunction we can simply pass the AsyncReverseFunction to the **initialized** Receive function by defining an argument for it: 

    def initialized(reverse:AsyncReverseFunction):Receive = ...

A real world example could be a fully asynchronous database driver for Redis or Mongo where authentication is required before using any operations and where the authentication returns some Future result. 


###What is already prepared

The end result of exercise 2 is the starting point of this exercise. 
Some changes have been made to the Receptionist (since we know have to deal with the fact that the AsyncReverseFunction is not ready yet) and all Results of the ReverseActor are now extending a sealed trait Result to simplify processing in the Receptionist. The Receptionist now also uses the onComplete directive because it has several different types of values that it completes with.

###The Exercise

Add Init message which the ReverseActor sends to itself to start off initialization.

Add NotInitialized message to indicate the ReverseActor is not ready yet.

From the ReverseActor constructor (or preStart) send Init to self.

The receive method should be set to the uninitialized Receive function.
Create an uninitialized Receive method.
Send back NotInitialized on a Reverse message in the uninitialized state / receive function.
Load the AsyncReverseFunction using the ReverserFactory on receiving Init. Once the Future completes successfully call become to transition to initialized state. pass through the AsyncReverseFunction to the initialized Receive function instead of using a var.

Implement the initialized Receive function, call the AsyncReverseFunction, on completion of the Future send back to a **captured sender** (do not close over sender but create a val which contains the sender at the time of receiving the Reverse message). 





