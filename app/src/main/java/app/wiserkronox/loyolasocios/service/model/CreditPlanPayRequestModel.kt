package app.wiserkronox.loyolasocios.service.model

data class CreditPlanPayRequestModel (
    val error: Boolean,
    val errorMessage: Array<String>,
    val errorCode: Number,
    val result: List<CreditPlanPayModel>,
    val detail: List<CreditPlanPayDetailModel>
)