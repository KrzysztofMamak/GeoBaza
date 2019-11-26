package com.mamak.geobaza.network

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.Observable

class FirebaseAuthenticationApi {
    fun loginViaEmailAndPassword(email: String, password: String): Observable<Task<AuthResult>> {
        return Observable.fromCallable {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        }
    }

    fun loginViaGoogleAccount(googleSignInAccount: GoogleSignInAccount): Observable<Task<AuthResult>> {
        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        return  Observable.fromCallable {
            FirebaseAuth.getInstance().signInWithCredential(authCredential)
        }
    }

    fun registerViaEmailAndPassword(email: String, password: String): Observable<Task<AuthResult>> {
        return Observable.fromCallable {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        }
    }
}