import scala.reflect.ClassTag
import scala.reflect.classTag

abstract class EntityTP {

  case class Edge(from: ClassTag[_], to: ClassTag[_], input: ClassTag[_])
  val registry = collection.mutable.Buffer.empty[Edge]
  def register[FROM: ClassTag, INPUT: ClassTag, TO: ClassTag](tr: Transformation[FROM, INPUT, TO]) = {
    println(s"${classTag[FROM]} to ${classTag[TO]} with input ${classTag[INPUT]}")
    val edge = Edge(classTag[FROM], classTag[TO], classTag[INPUT])
    registry += edge
  }

  def actionsFrom[FROM: ClassTag] = {
    registry.filter(_.from == classTag[FROM])
  }
  
  final case class Transformation[FROM: ClassTag, I: ClassTag, TO: ClassTag](fn: (FROM, I) => TO) {
    register(this)
    def apply(s: FROM, i: I): TO = fn(s, i)
  }

  final case class Fsm[S](s: S) {
    def transition[I, TO](i: I)(implicit t: Transformation[S, I, TO]): Fsm[TO] = {
      Fsm(t(s, i))
    }
  }

  def init[S: ClassTag](s: S) = {
    println(s"Initial state is ${classTag[S]}")
    Fsm(s)
  }

  def dotGraph: String = {
    def clean(n: ClassTag[_]) = n.toString.stripSuffix("$")
    val edges = registry.map { edge =>
      s""" ${clean(edge.from)} -> ${clean(edge.to)} [label =  "${clean(edge.input)}" ];"""
    }
    s"""digraph ${this.getClass.getSimpleName.stripSuffix("$")} {
    | node [shape = circle];
    | ${edges.mkString("\n")}
    |}""".stripMargin
  }
}

object Lottery2 extends EntityTP {
  val initState = init(NoLottery)

  implicit val start = Transformation[NoLottery.type, StartLottery.type, LotteryStarted] { (_, _ ) => 
    LotteryStarted(Set.empty)
  }
  implicit val add = Transformation[LotteryStarted, AddParticipant, LotteryStarted] { (lottery, add) =>
    lottery.copy(lottery.participants + add.user)
  }
  implicit val draw = Transformation[LotteryStarted, DrawWinner.type, LotteryFinished] { (lottery, _) =>
    LotteryFinished(lottery.participants, lottery.participants.head)
  }
} 


object Main2 extends App {
  println("Main2")
  import Lottery2._
  val lottery = initState
  
  lottery
    .transition(StartLottery)
    .transition(AddParticipant("toto"))
    .transition(AddParticipant("tata"))
    .transition(DrawWinner)

  println(s"Actions from started: ${actionsFrom[LotteryStarted]}")
  println("-----")
  println(Lottery2.dotGraph)
  println("------")
} 




