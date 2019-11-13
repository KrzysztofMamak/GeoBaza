package com.mamak.geobaza.utils.manager

import java.util.regex.Pattern

object ValidationManager {
    fun validateEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"
        val pattern = Pattern.compile(emailRegex)

        return pattern.matcher(email).matches()
    }
}