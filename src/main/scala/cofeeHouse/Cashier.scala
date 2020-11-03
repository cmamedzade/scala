package cofeeHouse

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import cofeeHouse.Barista.{CashierResponse, Command, Debt, Failed}

import scala.concurrent.duration._

object Cashier {

  val duration: FiniteDuration = 1000.millis
  def apply(): Behavior[Command] = Behaviors.setup {
    context =>
      receive(context)
  }

  def receive(context: ActorContext[Command]): Behavior[Command] =  Behaviors.receiveMessage {

    case CashierResponse(to) => to ! Debt( "u have to pay 5$")
      context.log.info("ask received from guest")
      Behaviors.same
      }

}
