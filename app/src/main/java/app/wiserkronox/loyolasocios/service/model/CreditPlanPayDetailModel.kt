package app.wiserkronox.loyolasocios.service.model

data class CreditPlanPayDetailModel (
    //Credit plan pay detail
    val id: Number,
    val id_credit_plan_pay: Number,
    val credNumCuota: Number,
    val credFecVenci: String,
    val credMontoCapi: Double,
    val credMontoInte: Double,
    val crediTotaCuota: Double,
    val crediMontoCargos: Double,
    val crediTotalCuota: Double,
    val crediSaldoCredi: Double,

)