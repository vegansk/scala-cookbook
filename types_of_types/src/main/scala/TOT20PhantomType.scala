object TOT20PhantomType extends App {

  sealed trait ServiceState

  final class Started extends ServiceState
  final class Stopped extends ServiceState

  class Service[State <: ServiceState] private() {

    def start[T >: State <: Stopped]() = this.asInstanceOf[Service[Started]]
    def stop[T >: State <: Started]() = this.asInstanceOf[Service[Stopped]]

  }

  object Service {
    def create() = new Service[Stopped]()
  }

}
