package eldis.react.interop

import scalajs.js
import js.|

@js.native
trait FunctionalComponent[-P <: js.Any] extends js.Function1[P, ReactNode]

object FunctionalComponent {

  def apply[P <: js.Any](name: String)(f: Function1[P, ReactNode]): FunctionalComponent[P] = {
    var jf = f: js.Function1[P, ReactNode]
    jf.asInstanceOf[js.Dynamic].displayName = name
    jf.asInstanceOf[FunctionalComponent[P]]
  }

  def apply[P <: js.Any](f: Function1[P, ReactNode]): FunctionalComponent[P] =
    apply("FunctionalComponent")(f)

  @js.native
  trait WithChildren[-P <: js.Any] extends js.Function2[P, PropsChildren, ReactNode]

  def withChildren[P <: js.Any](name: String)(f: Function2[P, PropsChildren, ReactNode]): WithChildren[P] = {
    var proxy = ((p: P) => {
      val children = p.asInstanceOf[js.Dynamic].children.asInstanceOf[PropsChildren]
      f(p, if (js.Array.isArray(children)) children else js.Array(children.asInstanceOf[ReactNode]))
    }): js.Function1[P, ReactNode]
    proxy.asInstanceOf[js.Dynamic].displayName = name
    proxy.asInstanceOf[WithChildren[P]]
  }

  def withChildren[P <: js.Any](f: Function2[P, PropsChildren, ReactNode]): WithChildren[P] =
    withChildren("FunctionalComponent")(f)
}
