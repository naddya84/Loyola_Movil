package app.wiserkronox.loyolasocios.service.model

data class CreditExtractDetailModel (
    //Credit extract detail
    val id: Number,
    val id_credit_extract: Number,
    val credFecPago: String,
    val credNroTrans: Number,
    val credMontoCapi: Double,
    val crediMontoInte: Double,
    val crediMontoPenal: Double,
    val crediMontoCargos: Double,
    val crediTotalPago: Double,
    val crediSaldoCapi: Double,

)