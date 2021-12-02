package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class CreditPlanPay {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo
    var id_credit:Int = 0

    @ColumnInfo
    var id_credit_plan_pay:Int = 0

    @ColumnInfo
    var amount_desem:Double = 0.0

    @ColumnInfo
    var plazo:Int = 0

    @ColumnInfo
    var tasa:Double = 0.0

    @ColumnInfo
    var periodo_pago:String = ""

    @ColumnInfo
    var for_pago:String = ""

}