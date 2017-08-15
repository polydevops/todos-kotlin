package todos.util.mvp.rx

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by connor on 7/25/17.
 */
open class RxPresenter {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun handleError(error: Throwable) {
        error.printStackTrace()
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }
}