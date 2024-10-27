package ru.citycheck.core.application.phone.utils

// Regular expression for capturing phone number components
const val RUSSIAN_PHONE_REGEX =
    "^(\\+7|8)[\\- ]?\\(?(\\d{3})\\)?[\\- ]?(\\d{3})[\\- ]?(\\d{2})[\\- ]?(\\d{2})$"