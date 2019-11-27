package com.mamak.geobaza.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable

class FirebaseAuthenticationApi {
    fun authViaEmailAndPassword(email: String, password: String): Observable<Task<AuthResult>> {
        return Observable.fromCallable {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        }
    }

    fun authViaGoogle(authCredential: AuthCredential): Observable<Task<AuthResult>> {
        return  Observable.fromCallable {
            FirebaseAuth.getInstance().signInWithCredential(authCredential)
        }
    }

    fun registerViaEmailAndPassword(email: String, password: String): Observable<Task<AuthResult>> {
        return Observable.fromCallable {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        }
    }

    fun resetPassword(email: String): Observable<Task<Void>> {
        return Observable.fromCallable {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
        }
    }
}