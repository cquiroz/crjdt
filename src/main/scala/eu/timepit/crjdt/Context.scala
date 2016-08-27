package eu.timepit.crjdt

import eu.timepit.crjdt.Context.Tagged.{ListT, MapT, RegT}
import eu.timepit.crjdt.Operation.Mutation.AssignM
import eu.timepit.crjdt.Val.{EmptyList, EmptyMap}

sealed trait Context extends Product with Serializable {
  def applyOp(op: Operation): Context =
    op.cur match {
      case Cursor(Vector(), kn) =>
        op.mut match {
          case AssignM(EmptyMap) => // EMPTY-MAP
            val tagged = MapT(kn)
            ???
          case AssignM(EmptyList) => // EMPTY-LIST
            val tagged = ListT(kn)
            ???
          case AssignM(v) => // ASSIGN
            val tagged = RegT(kn)
            ???
          case _ =>
            ???
        }
      case Cursor(ks, kn) => ???
    }
}

object Context {
  def empty: Context = Empty

  case object Empty extends Context
  final case class Leaf(value: Value) extends Context
  final case class Assoc(key: Tagged, ctx: Context, pres: Set[Id])
      extends Context
  final case class Many(assocs: List[Assoc]) extends Context

  sealed trait Value
  object Value {
    final case class ValV(v: Val) extends Value
    final case class IdV(id: Id) extends Value
    case object Tail extends Value
  }

  sealed trait Tagged extends Product with Serializable
  object Tagged {
    final case class MapT(key: Key) extends Tagged
    final case class ListT(key: Key) extends Tagged
    final case class RegT(key: Key) extends Tagged
    final case class Next(key: Key) extends Tagged
  }
}
