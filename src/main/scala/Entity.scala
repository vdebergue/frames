// object Entity {
//   def apply[T, S] = EntityBuilder[T, S]
// }

// trait Entity[STATE, COMMAND] {
//   def sendCommand(cmd: COMMAND): STATE
//   def currentState(): STATE
// }

// case class EntityBuilder[STATE](
//   initState: STATE,
// ) {
//   def build(): Entity[STATE, COMMAND]
//   def addTransition[S <: COMMAND]
//   def initState(state: STATE)
// }

