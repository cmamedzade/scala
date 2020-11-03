package cofeeHouse

import akka.actor.typed.{ActorRef, Behavior, DeathPactException, SupervisorStrategy, Terminated}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}

object Barista {

  sealed trait Command
  case class orderCoffee(text: String, to: ActorRef[Command]) extends Command
  case class sendCoffee(text: String, to: ActorRef[Command]) extends Command
  case class runApp(text:String) extends Command
  case class Failed(text:String) extends Command
  case class resetCounter(counter: Int) extends Command
  case class CashierResponse(to: ActorRef[Command]) extends Command
  case class Debt(text:String) extends Command

  case class coffeeLimit(to:ActorRef[Command]) extends IllegalStateException("too many coffee")

  def apply(waiter: ActorRef[Command],guest: ActorRef[Command]): Behavior[Command] =
    Behaviors.setup {
      context =>
        Behaviors.supervise{
          Behaviors.supervise(receive(context, waiter, guest)).onFailure(SupervisorStrategy.restart)
        receive(context, waiter, guest)}
          .onFailure[IllegalStateException](SupervisorStrategy.restart)
    }

  var counter:Int = 0
  def receive(context: ActorContext[Command], waiter: ActorRef[Command], guest:ActorRef[Command]): Behavior[Command] =
    Behaviors.receiveMessage[Command] {

    case sendCoffee(text,to) => waiter ! sendCoffee(text,context.self)
      Behaviors.same
    case orderCoffee(text,to) => guest ! orderCoffee(text,context.self)
      Behaviors.same
    case runApp(text) => waiter ! sendCoffee("run",context.self)
      Behaviors.same
    case Failed(text) => throw coffeeLimit(context.self)


   // Behaviors.supervise(recieve(context,waiter,guest)).onFailure[coffeLimit](SupervisorStrategy.resume)
  }.receiveSignal{
    case (context,Terminated(ref)) => guest ! resetCounter(0)
      Behaviors.same
  }
}
