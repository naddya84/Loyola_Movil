package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class CreditExtract {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo
    var credit_id:Int = 0

    @ColumnInfo
    var credit_extract_id:Int = 0

    @ColumnInfo
    var number:Int = 0

    @ColumnInfo
    var term:String = ""

    @ColumnInfo
    var disbursed_amount:Double = 0.0

    @ColumnInfo
    var state:String = ""


}