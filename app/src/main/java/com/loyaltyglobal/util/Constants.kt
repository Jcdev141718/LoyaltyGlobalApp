package com.loyaltyglobal.util

/**
 * Created by Abhin.
 */

object Constants {
    const val BASE_URL = "https://v2dev.walletly.ai/api/v4/"
    const val AGENCY_ID = "WQeEgJhH0SirUT531Rqm"
    const val HEADER_AUTH_TOKEN = "x-auth-token"
    const val AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im1tQGlzeXN0ZW1hdGljLmNvbSIsInJvbGVzIjpbInN1YnNjcmliZXIiXSwiX2lkIjoia1VSbFR5TzBneHRvTzBNUnpyREYiLCJpYXQiOjE2MzY3MjI1MjF9.cl6FcewzFV8hYwaf2jntFNyz1NZEop8QpHPl9_QzJBE"
    const val UNAUTHORISED = 401
    const val DATABASE_NAME ="LoyaltyGlobalDB"

    const val REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}"
    const val PASSWORD_PATTERN_WITH_ONE_SPECIAL_CHARS = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}\$"
    const val MOBILE_NUMBER_KEY = "MOBILE_NUMBER_KEY"
    const val MINIMUM_LENGTH_OF_NUMBER = 10
    const val PERMISSIONS_REQUEST_CODE = 10003
}

