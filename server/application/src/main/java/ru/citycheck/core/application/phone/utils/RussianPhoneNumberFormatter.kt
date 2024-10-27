package ru.citycheck.core.application.phone.utils

import ru.citycheck.core.application.phone.utils.RussianPhoneNumberFormatter.formatRussianPhoneNumber
import java.util.regex.Pattern

object RussianPhoneNumberFormatter {
    fun formatRussianPhoneNumber(phoneNumber: String): String {
        val pattern = Pattern.compile(RUSSIAN_PHONE_REGEX)
        val matcher = pattern.matcher(phoneNumber)

        // Check if the phone number matches the regex
        if (matcher.matches()) {
            // Capture the groups
            val areaCode = matcher.group(2)
            val firstPart = matcher.group(3)
            val secondPart = matcher.group(4)
            val thirdPart = matcher.group(5)


            // Reformat to +7 (XXX) XXX-XX-XX
            return String.format(
                "+7 (%s) %s-%s-%s",  /* Extract area code without any leading character */areaCode,
                firstPart, secondPart, thirdPart
            )
        } else {
            // If it doesn't match, return the original phone number
            return phoneNumber
        }
    }
}