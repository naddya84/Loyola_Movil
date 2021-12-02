package app.wiserkronox.loyolasocios.service.model

data class CreditRequestModel (
    val error: Boolean,
    val errorMessage:Array<String>,
    val errorCode:Number,
    val result: List<CreditModel>
)