package com.mamak.geobaza.network.firebase

import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import java.net.ConnectException
import java.net.SocketTimeoutException

class GeoBazaException(throwable: Throwable) : Exception(throwable.message, throwable.cause) {
    val errorCode: Int?
    var internalErrorCode: String? = null
    var internalErrorInfo: String? = null

    init {
        when (throwable) {
            is FirebaseNetworkException -> {
                errorCode = FIREBASE_NETWORK_EXCEPTION
            }
            is FirebaseApiNotAvailableException -> {
                errorCode = FIREBASE_API_NOT_AVAILABLE_EXCEPTION
            }
            is FirebaseTooManyRequestsException -> {
                errorCode = FIREBASE_TOO_MANY_REQUESTS_EXCEPTION
            }
            is FirebaseAuthActionCodeException -> {
                errorCode = FIREBASE_AUTH_ACTION_CODE_EXCEPTION
                internalErrorCode = throwable.errorCode
            }
            is FirebaseAuthUserCollisionException -> {
                errorCode = FIREBASE_AUTH_USER_COLLISION_EXCEPTION
                internalErrorCode = throwable.errorCode
                internalErrorInfo = throwable.email
            }
            is FirebaseAuthInvalidUserException -> {
                errorCode = FIREBASE_AUTH_INVALID_USER_EXCEPTION
                internalErrorCode = throwable.errorCode
            }
            is FirebaseAuthInvalidCredentialsException -> {
                errorCode = FIREBASE_AUTH_INVALID_CREDENTIALS_EXCEPTION
                internalErrorCode = throwable.errorCode
            }
            is FirebaseAuthEmailException -> {
                errorCode = FIREBASE_AUTH_EMAIL_EXCEPTION
                internalErrorCode = throwable.errorCode
            }
            is FirebaseAuthRecentLoginRequiredException -> {
                errorCode = FIREBASE_AUTH_RECENT_LOGIN_REQUIRED_EXCEPTION
                internalErrorCode = throwable.errorCode
            }
            is FirebaseAuthWeakPasswordException -> {
                errorCode = FIREBASE_AUTH_WEAK_PASSWORD_EXCEPTION
                internalErrorCode = throwable.errorCode
                internalErrorInfo = throwable.reason
            }
            is FirebaseNoSignedInUserException -> {
                errorCode = FIREBASE_NO_SIGNED_IN_USER_EXCEPTION
            }
            is FirebaseAuthException -> {
                errorCode = FIREBASE_AUTH_EXCEPTION
                internalErrorCode = throwable.errorCode
            }
            is FirebaseException -> {
                errorCode = FIREBASE_EXCEPTION
            }
            is SocketTimeoutException -> {
                errorCode = SOCKET_TIMEOUT_EXCEPTION
            }
            is ConnectException -> {
                errorCode = CONNECT_EXCEPTION
            }
            else -> {
                errorCode = UNKNOWN_EXCEPTION
            }
        }
    }

    companion object ErrorCode {
        const val FIREBASE_EXCEPTION = 13000
        const val FIREBASE_NETWORK_EXCEPTION = 13001
        const val FIREBASE_API_NOT_AVAILABLE_EXCEPTION = 13002
        const val FIREBASE_TOO_MANY_REQUESTS_EXCEPTION = 13003
        const val FIREBASE_AUTH_ACTION_CODE_EXCEPTION = 13004
        const val FIREBASE_AUTH_USER_COLLISION_EXCEPTION = 13005
        const val FIREBASE_AUTH_INVALID_USER_EXCEPTION = 13006
        const val FIREBASE_AUTH_EMAIL_EXCEPTION = 13007
        const val FIREBASE_AUTH_RECENT_LOGIN_REQUIRED_EXCEPTION = 13008
        const val FIREBASE_AUTH_WEAK_PASSWORD_EXCEPTION = 13009
        const val FIREBASE_NO_SIGNED_IN_USER_EXCEPTION = 13010
        const val FIREBASE_AUTH_INVALID_CREDENTIALS_EXCEPTION = 13011
        const val FIREBASE_AUTH_EXCEPTION = 13012
        const val SOCKET_TIMEOUT_EXCEPTION = 12000
        const val CONNECT_EXCEPTION = 12001
        const val UNKNOWN_EXCEPTION = 11000

        const val ERROR_USER_DISABLED = "ERROR_USER_DISABLED"
        const val ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
        const val ERROR_WRONG_PASSWORD = "ERROR_WRONG_PASSWORD"
        const val ERROR_OPERATION_NOT_ALLOWED = "ERROR_OPERATION_NOT_ALLOWED"
    }
}