package com.mamak.geobaza.network.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.internal.functions.ObjectHelper
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

internal class TaskCompletable(private val callable: Callable<Task<Void>>) : Completable() {
    init {
        ObjectHelper.requireNonNull(callable, "Null Callable Received")
    }

    override fun subscribeActual(observer: CompletableObserver) {
        ObjectHelper.requireNonNull(observer, "Null Observer Received")
        val taskListener = TaskListener(observer)
        observer.onSubscribe(taskListener)
        val task: Task<Void>
        try {
            task = callable.call().addOnCompleteListener(taskListener)
            ObjectHelper.requireNonNull(task, "Null Task Received")
        } catch (e: Exception) {
            if (!taskListener.isDisposed) {
                observer.onError(e)
            }
        }
    }

    private class TaskListener internal constructor(private val observer: CompletableObserver) :
            OnCompleteListener<Void>, Disposable {
        private val isDispose = AtomicBoolean(false)

        override fun dispose() {
            isDispose.compareAndSet(false, true)
        }

        override fun isDisposed(): Boolean {
            return isDispose.get()
        }

        override fun onComplete(task: Task<Void>) {
            if (!isDisposed) {
                if (task.isSuccessful) {
                    observer.onComplete()
                } else {
                    try {
                        task.exception?.let { observer.onError(it) }
                    } catch (e: Exception) {
                        observer.onError(CompositeException(e))
                    }
                }
            }
        }
    }
}