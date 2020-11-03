package cofeeHouse
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.{Behavior, DispatcherSelector, SupervisorStrategy, Terminated}
import akka.actor.typed.scaladsl.{Behaviors, Routers}
import cofeeHouse.Barista._

object CoffeHouse {



  def apply(): Behavior[String] =  Behaviors.receive[String] {
    (context,message) =>

      val pool = Routers.pool(poolSize = 12)(Guest())
      val guest = context.spawn(pool, "guest-pool")
      //val serviceky = ServiceKey[Barista.Command]("log-worker")
      //val group = Routers.group(serviceky)
      //val gs  = context.spawn(group,"worker-group")

     // val guest = context.spawn(Guest(),"guest")
      val waiter = context.spawn(Waiter(),"waiter",DispatcherSelector.fromConfig("akka.actor.myDispatcher"))
      val barista = context.spawn(Barista(waiter,guest),"barista")
      barista ! runApp("what do u want?")
    //  context.watch(barista)
      context.log.debug("coffe-house created: "+message)
      Behaviors.same

  }.receiveSignal {
    case (context, Terminated(ref: AnyRef)) => context.log.info("guest riched coffee limit")

      Behaviors.same
  }

}
