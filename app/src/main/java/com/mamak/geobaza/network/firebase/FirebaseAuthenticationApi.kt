package com.mamak.geobaza.network.firebase

import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.Callable

class FirebaseAuthenticationApi {
    fun authViaEmailAndPassword(email: String, password: String): Observable<AuthResult> {
        return TaskObservable(Callable<Task<AuthResult>> {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        })
    }

    fun authViaGoogle(authCredential: AuthCredential): Observable<AuthResult> {
        return TaskObservable(Callable<Task<AuthResult>> {
            FirebaseAuth.getInstance().signInWithCredential(authCredential)
        })
    }

    fun registerViaEmailAndPassword(email: String, password: String): Observable<AuthResult> {
        return TaskObservable(Callable<Task<AuthResult>> {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        })
    }

    fun resetPassword(email: String): Completable {
        return TaskCompletable(Callable {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
        })
    }

    fun googleSignOut(googleSigninClient: GoogleSignInClient): Completable {
        return TaskCompletable(Callable {
            googleSigninClient.signOut()
        })
    }
}