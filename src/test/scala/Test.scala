import java.util.concurrent.Executors.newSingleThreadExecutor

import org.mockito.MockitoScalaSession
import utest._

import scala.concurrent.{ExecutionContext, Future}


object Test extends TestSuite {
  //  This works too, but not sure if at some point threads may be reused
  //  val ec2 = ExecutionContext.fromExecutor(newCachedThreadPool())

  override def utestWrap(path: Seq[String], runBody: => concurrent.Future[Any])(implicit ec: ExecutionContext): Future[Any] = {
    // For some reason when the provided ec is used everything runs on the main thread,
    // even the future that starts the session, no idea why, but that's the main problem

    //   Maybe this will fail with actual tests as the future in the test will be using a different ec,
    //   sadly the scala Futures are not pure and we can't manipulate them without starting its execution,
    //   maybe it could be tried to lift everything into Task or something like that?
    val ec2 = ExecutionContext.fromExecutor(newSingleThreadExecutor())

    for {
      session <- Future(MockitoScalaSession())(ec2)
      result <- super.utestWrap(path, runBody)(ec2).transform { result =>
        session.finishMocking(result.toEither.swap.toOption)
        result
      }(ec2)
    } yield result
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
