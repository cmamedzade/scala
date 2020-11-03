package CoffeeHouseTest
import akka.actor.testkit.typed.Effect.Spawned
import akka.actor.testkit.typed.scaladsl.BehaviorTestKit
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.delivery.DurableProducerQueue.MessageSent
import cofeeHouse.{CoffeHouse, Guest, Waiter}
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration._
import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.testkit.typed.scaladsl.ManualTime
import akka.actor.testkit.typed.scaladsl.TestProbe
import akka.actor.testkit.typed.scaladsl.LogCapturing
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import cofeeHouse.Barista.{Command, sendCoffee}
import org.scalatest.wordspec.AnyWordSpecLike

class WaiterSpec extends AnyWordSpec  with BeforeAndAfterAll {

  //val testKit = ActorTestKit()
  // val guest = testKit.spawn(Guest(),"guest")
  // implicit val actor = guest.ref
  // val waiter = testKit.createTestProbe[String]()
  // guest ! Greeted("necesen",waiter.ref)
  //waiter.expectMessage("necesen?")

  // val guest  = testKit.createTestProbe[Greeted]()
  //val waiter = testKit.spawn(Waiter(guest.ref),"waiter")
  // waiter ! "necesen?"
  //guest.expectMessage(Greeted("hallo",waiter.ref))


  // val testB = BehaviorTestKit(Waiter(actor)) //synchrone
  // testB.run("hello")


}



