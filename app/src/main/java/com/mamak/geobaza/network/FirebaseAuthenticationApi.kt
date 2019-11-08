package com.mamak.geobaza.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable

class FirebaseAuthenticationApi {
    fun loginViaEmailAndPassword(email: String, password: String): Observable<Task<AuthResult>> {
        return Observable.fromCallable {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        }
    }

    fun registerViaEmailAndPassword(email: String, password: String): Observable<Task<AuthResult>> {
        return Observable.fromCallable {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        }
    }
}