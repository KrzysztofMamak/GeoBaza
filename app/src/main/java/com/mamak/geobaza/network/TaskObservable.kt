package com.mamak.geobaza.network

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.functions.ObjectHelper
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicBoolean

internal class TaskObservable<T>(private val callable: Callable<Task<T>>) : Observable<T>() {
    init {
        ObjectHelper.requireNonNull(callable, "Null Callable Received")
    }

    override fun subscribeActual(observer: Observer<in T>) {
        ObjectHelper.requireNonNull(observer, "Null Observer Received")
        val taskListener = TaskListener(observer)
        observer.onSubscribe(taskListener)
        val task: Task<T>
        try {
            task = callable.call()
                .addOnFailureListener(taskListener)
                .addOnSuccessListener(taskListener)
                .addOnCompleteListener(taskListener)
            ObjectHelper.requireNonNull(task, "Null Task Received")
        } catch (e: Exception) {
            if (!taskListener.isDisposed) {
                observer.onError(e)
            }
        }

    }

    private class TaskListener<T> internal constructor(private val observer: Observer<in T>) : OnCompleteListener<T>,
        Disposable, OnFailureListener, OnSuccessListener<T> {
        private val isDispose = AtomicBoolean(false)

        override fun dispose() {
            isDispose.compareAndSet(false, true)
        }

        override fun isDisposed(): Boolean {
            return isDispose.get()
        }

        override fun onComplete(task: Task<T>) {
            if (!isDisposed) {
                observer.onComplete()
            }
        }

        override fun onFailure(e: Exception) {
            if (!isDisposed) {
                observer.onError(e)
            }
        }

        override fun onSuccess(o: T) {
            if (!isDisposed) {
                observer.onNext(o)
            }
        }
    }
}