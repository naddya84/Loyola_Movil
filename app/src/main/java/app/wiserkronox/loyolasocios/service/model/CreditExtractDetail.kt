package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class CreditExtractDetail {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    //LLAVES FORANEAS
    @ColumnInfo
    var credit_extract_id:Int = 0

    @ColumnInfo
    var credit_extract_detail_id:Int = 0

    //DATA
    @ColumnInfo
    var payment_date:String = ""

    @ColumnInfo
    var number_transaction:Int = 0

    @ColumnInfo
    var principal_amount:Double = 0.0

    @ColumnInfo
    var interest_amount:Double = 0.0

    @ColumnInfo
    var penalty_amount:Double = 0.0

    @ColumnInfo
    var amount_of_charges:Double = 0.0

    @ColumnInfo
    var total_to_pay:Double = 0.0

    @ColumnInfo
    var principal_balance:Double = 0.0
}