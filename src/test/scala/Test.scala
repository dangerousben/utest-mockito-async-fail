import scala.concurrent.{ ExecutionContext, Future }
import scala.util.control.NonFatal
import org.mockito.MockitoScalaSession
import utest._


object Test extends TestSuite {
  override def utestWrap(path: Seq[String], runBody: => concurrent.Future[Any])(implicit ec: ExecutionContext) = {
    val session = MockitoScalaSession()
    session.synchronized {
      try {
        super.utestWrap(path, runBody).transform { result =>
          session.finishMocking(result.toEither.swap.toOption)
          result
        }
      } catch {
        case NonFatal(e) =>
          session.finishMocking(Some(e))
          throw e
      }
    }
  }

  val tests = Tests {
    import scala.concurrent.ExecutionContext.Implicits.global

    test {
      Future(Thread.sleep(10))
    }
    test {
      Future(Thread.sleep(10))
    }
    test {
      Future(Thread.sleep(10))
    }
    test {
      Future(Thread.sleep(10))
    }
    test {
      Future(Thread.sleep(10))
    }
    test {
      Future(Thread.sleep(10))
    }
    test {
      Future(Thread.sleep(10))
    }
    test {
      Future(Thread.sleep(10))
    }
    test {
      Future(Thread.sleep(10))
    }
    test {
      Future(Thread.sleep(10))
    }
  }
}
