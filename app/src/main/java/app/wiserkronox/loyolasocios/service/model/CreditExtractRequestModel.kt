package app.wiserkronox.loyolasocios.service.model

data class CreditExtractRequestModel (
    val error: Boolean,
    val errorMessage: Array<String>,
    val errorCode: Number,
    val result: List<CreditExtractModel>,
    val detail: List<CreditExtractDetailModel>
)