package com.loyaltyglobal.util

/**
 * Created by Abhin.
 */

object Constants {
    const val BASE_URL = "https://v2dev.walletly.ai/api/v4/"
    const val QUERY_EMAIL: String = "email"
    const val AGENCY_ID = "WQeEgJhH0SirUT531Rqm"
    const val UNAUTHORISED = 401
    const val RC_SIGN_IN = 1005
    const val REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}"
    const val PASSWORD_PATTERN_WITH_ONE_SPECIAL_CHARS = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}\$"
}

