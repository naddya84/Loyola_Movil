package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Credit {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    //Atributos de la tabla
    @ColumnInfo
    var credit_id: Int = 0

    @ColumnInfo
    var number:Int = 0

    @ColumnInfo
    var disburement_date:String = ""

    @ColumnInfo
    var disbursed_amount:Double = 0.0

    @ColumnInfo
    var coin:String = ""

    @ColumnInfo
    var balance:Double = 0.0

    @ColumnInfo
    var state: String = ""

    @ColumnInfo
    var cancellation_date:String = ""
}