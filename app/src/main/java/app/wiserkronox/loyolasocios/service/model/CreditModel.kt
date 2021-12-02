package app.wiserkronox.loyolasocios.service.model

data class CreditModel (
    val id: Number,
    val credNumero: Number,
    val credFechaDesem: String,
    val credMontoDesem: Number,
    val crediMoneda: Number,
    val crediSaldo: Number,
    val crediEstado: String,
    val crediFecCancel: String
)