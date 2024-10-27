package ru.citycheck.core.application.phone.utils

import java.util.regex.Pattern

object RussianPhoneNumberValidator {
    fun isValidRussianPhoneNumber(phoneNumber: String): Boolean {
        val pattern = Pattern.compile(RUSSIAN_PHONE_REGEX)
        val matcher = pattern.matcher(phoneNumber)
        return matcher.matches()
    }
}