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
    var credit_id:Int = 0

    @ColumnInfo
    var credit_plan_pay_id:Int = 0

    @ColumnInfo
    var disbursed_amount:Double = 0.0

    @ColumnInfo
    var term:Int = 0

    @ColumnInfo
    var rate:Double = 0.0

    @ColumnInfo
    var payment_period:String = ""

    @ColumnInfo
    var way_to_pay:String = ""

}