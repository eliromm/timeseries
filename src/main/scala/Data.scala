/**
  * Created by e on 14/07/17.
  */
case class Data(double: Double) {
  override def equals(obj: scala.Any): Boolean = this == obj

}

object Data{
  implicit def ordering: Ordering[Data] = Ordering.by(_.double)
}
