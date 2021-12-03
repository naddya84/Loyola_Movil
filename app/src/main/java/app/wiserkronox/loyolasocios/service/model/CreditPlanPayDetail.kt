package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class CreditPlanPayDetail {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    //PRIMARY KEY
    @ColumnInfo
    var credit_plan_pay_id:Int = 0

    //FK KEY
    @ColumnInfo
    var credit_plan_pay_detail_id:Int = 0

    //DATA
    @ColumnInfo
    var installment_number:Int = 0

    @ColumnInfo
    var due_date:String = ""

    @ColumnInfo
    var principal_amount:Double = 0.0

    @ColumnInfo
    var interest_amount:Double = 0.0

    @ColumnInfo
    var tota_fee:Double = 0.0

    @ColumnInfo
    var amount_of_charges:Double = 0.0

    @ColumnInfo
    var total_fee:Double = 0.0

    @ColumnInfo
    var principal_balance:Double = 0.0
}