package app.wiserkronox.loyolasocios.service.model

data class CreditExtractModel (
    //credit plan pay
    val id: Number,
    val id_credit: Number,
    val user_id: Number,
    val credMontoDesem: Double,
    val credPlazo: String,
    val estado: String,
)