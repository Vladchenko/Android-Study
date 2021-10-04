package com.example.vladislav.androidstudy.javarx2.example5

import io.reactivex.ObservableEmitter
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable

class ProgressObservableEmitter: ObservableEmitter<String> {

    override fun onNext(p0: String) {
        println(p0)
    }

    override fun onError(p0: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onComplete() {
        TODO("Not yet implemented")
    }

    override fun setDisposable(p0: Disposable?) {
        TODO("Not yet implemented")
    }

    override fun setCancellable(p0: Cancellable?) {
        TODO("Not yet implemented")
    }

    override fun isDisposed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun serialize(): ObservableEmitter<String> {
        TODO("Not yet implemented")
    }
}