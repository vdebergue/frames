
sealed trait Lottery
case object NoLottery extends Lottery
case class LotteryStarted(participants: Set[String]) extends Lottery
case class LotteryFinished(participants: Set[String], winner: String) extends Lottery

sealed trait LotteryCmd
case object StartLottery extends LotteryCmd
case class AddParticipant(user: String) extends LotteryCmd
case object DrawWinner extends LotteryCmd

/*
 NoLottery -> (Start Lottery) -> LotteryStarted
 LotteryStarted -> (Add Participant) -> LotteryStarted
 LotteryStarted -> (DrawWinner) -> LotteryFinished
*/

// object Lottery {
//   val entity = Entity[Lottery]
//     .initState(NoLottery)
//     .addTransition[StartLottery, NoLottery] { (start, _) => LotteryStarted(Set.empty) }
//     .addTransition[AddParticipant, LotteryStarted]  { (add, lottery) => lottery.copy(lottery.participants + add.user) }
//     .addTransition[DrawWinner, LotteryStarted] { (_, lottery) => 
//       if (lottery.participants.isEmpty) {
//         lottery
//       } else {
//         LotteryFinished(lottery.participants, lottery.participants.head) 
//       } 
//     }
//     .build()
// }


// object Main extends App {
//   val lottery = Lottery.entity.bootstrap()

//   lottery.sendCommand(StartLottery)
//   lottery.sendCommand(StartLottery)
//   lottery.sendCommand(AddParticipant("toto"))
//   lottery.sendCommand(AddParticipant("tata"))
//   lottery.sendCommand(DrawWinner)
// }
