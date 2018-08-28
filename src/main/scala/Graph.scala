case class Edge[FROM, CMD, TO](fn: (FROM, CMD) => TO)

case class FSM[STATE](edges: Seq[Edge[STATE, _, STATE]])

object Lottery3 {
  // FSM[Lottery](Seq(
  //   Edge[NoLottery.type, StartLottery.type, LotteryStarted]{ (_, _ ) => LotteryStarted(Set.empty)}
  // ))
}

