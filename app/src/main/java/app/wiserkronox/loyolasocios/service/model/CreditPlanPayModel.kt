package app.wiserkronox.loyolasocios.service.model

data class CreditPlanPayModel (
    //credit plan pay
    val id: Number,
    val id_credit: Number,
    val id_user: Number,
    val credMontoDesem: Double,
    val credPlazo: Number,
    val credTasa: Double,
    val credPeriPago: String,
    val credForPago: String,
)