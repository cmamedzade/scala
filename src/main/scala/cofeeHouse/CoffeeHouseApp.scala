package cofeeHouse

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import com.typesafe.config.ConfigFactory
import sun.security.tools.PathList

object CoffeeHouseApp {


  def apply(): Behavior[String] = Behaviors.setup {
    context =>
      val coffee = context.spawn(CoffeHouse(),"coffee-house")
      coffee ! "my app started"
      Behaviors.same

  }

  val config = ConfigFactory.load("application.conf")

 // config.withFallback(ConfigFactory.defaultReference())
  val system = ActorSystem(CoffeeHouseApp(),"my-system",config)



  def main(args: Array[String]): Unit = {
   system
  }

}
