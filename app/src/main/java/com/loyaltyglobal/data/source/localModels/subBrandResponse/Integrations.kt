package com.loyaltyglobal.data.source.localModels.subBrandResponse

import androidx.room.Embedded

data class Integrations(
    @Embedded(prefix = "emailService_") var emailService: EmailService? = null,
    @Embedded(prefix = "manychat_") var manychat: Manychat? = null,
    @Embedded(prefix = "webhook_")var webhook: Webhook? = null
)