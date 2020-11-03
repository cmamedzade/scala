package cofeeHouse
import akka.actor.typed.{ Behavior, Terminated}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import cofeeHouse.Barista._
import scala.concurrent.duration._

object Waiter {


  def apply(): Behavior[Command] = Behaviors.setup[Command] {
    context =>
      receive(context)
    // Behaviors.same
  }

  val duration: FiniteDuration = 10000.millis

  def receive(context: ActorContext[Command]): Behavior[Command] = Behaviors.withTimers {
    timer =>
    Behaviors.receiveMessage[Command] {

      // context.log.info("message from mian: "+message.text)
      case sendCoffee(text, to) => to ! orderCoffee("from waiter:  what do u drink?", context.self)
        Behaviors.same

        Behaviors.receiveMessage[Command] {
          case sendCoffee(text, to) =>
            context.log.info(text)
            context.watch(to)
            Behaviors.same
        }

    }.receiveSignal {
      case (context, Terminated(ref)) => context.log.info("guest terminated")
        Behaviors.same
    }

  }
}
