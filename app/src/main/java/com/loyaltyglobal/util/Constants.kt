package com.loyaltyglobal.util

/**
 * Created by Abhin.
 */

object Constants {
    const val OneSignalAppId = "0eb5e848-2ca5-451d-a1b1-fbd20ef02e1b"
    const val BASE_URL = "https://v2dev.walletly.ai/api/v4/"
    const val AGENCY_ID = "WQeEgJhH0SirUT531Rqm"
    const val HEADER_AUTH_TOKEN = "x-auth-token"
    const val AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im1tQGlzeXN0ZW1hdGljLmNvbSIsInJvbGVzIjpbInN1YnNjcmliZXIiXSwiX2lkIjoia1VSbFR5TzBneHRvTzBNUnpyREYiLCJpYXQiOjE2MzY3MjI1MjF9.cl6FcewzFV8hYwaf2jntFNyz1NZEop8QpHPl9_QzJBE"
    const val QUERY_EMAIL: String = "email"
    const val UNAUTHORISED = 401
    const val DATABASE_NAME ="LoyaltyGlobalDB"

    const val RC_SIGN_IN = 1005
    const val REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}"
    const val PASSWORD_PATTERN_WITH_ONE_SPECIAL_CHARS = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}\$"
    const val MOBILE_NUMBER_KEY = "MOBILE_NUMBER_KEY"
    const val USER_NAME_KEY = "USER_NAME_KEY"
    const val MINIMUM_LENGTH_OF_NUMBER = 10
    const val OTP_LENGTH = 6
    const val PERMISSIONS_REQUEST_CODE = 10003
    const val IS_USER_LOGIN_KEY = "IS_USER_LOGIN_KEY"

    const val PREF_PLAYER_ID  = "PLAYER_ID"
    const val CHANNEL_ID = "LoyaltyChannel"
    const val CHANNEL_NAME = "LoyaltyGlobal"
    const val NOTIFICATION_TYPE_TEXT = "textNotification"
    const val NOTIFICATION_TYPE_IMAGE = "imageNotification"
    const val CUSTOM_FIELD_TYPE_IMAGE = "imageUrl"
    const val KEY_WEB_URL = "KEY_WEB_URL"
    const val KEY_TITLE = "KEY_TITLE"
}

