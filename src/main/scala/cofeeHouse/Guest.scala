package cofeeHouse


import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.util.Timeout
import cofeeHouse.Barista._

import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Guest {
  val duration: FiniteDuration = 1000.millis
  def apply(): Behavior[Any] = Behaviors.setup {
    context =>
      val cashier = context.spawn(Cashier(),"cashier")
      receive(context,cashier)
  }

  var counter = 0
  // make sure the workers are restarted if they fail
  def receive(context: ActorContext[Any],cashier: ActorRef[Command]): Behavior[Any] =  Behaviors.receiveMessage {

    case resetCounter(cnt) => counter = cnt
      Behaviors.same
    case orderCoffee(text,to) =>
      context.log.info(text)
      Behaviors.same
      Behaviors.withTimers {
        timers =>
          timers.startSingleTimer("count",orderCoffee(text,to),duration)
          implicit val timeout: Timeout = 3.seconds
         // println("message sent to waiter")
          counter += 1
          if (counter > 8) {
            context.ask(cashier,CashierResponse){
              case Success(value) => context.log.info("cashier told price")
              case Failure(exception) => context.log.error("cashier didnt response")
            }

            to ! Failed("enough coffee. thanks")
            counter = 0
          }
          else{
            to ! sendCoffee("from guest: I want any type of coffee. It doesnt matter",context.self)
          }
          Behaviors.same
      }
  }
}
