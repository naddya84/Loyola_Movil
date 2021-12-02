package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Credit {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo
    var credId: Int = 0

    @ColumnInfo
    var number:Int = 0

    @ColumnInfo
    var date_desem:String = ""

    @ColumnInfo
    var amount_desem:Double = 0.0

    @ColumnInfo
    var coin:String = ""

    @ColumnInfo
    var balance:Double = 0.0

    @ColumnInfo
    var state: String = ""

    @ColumnInfo
    var date_cancel:String = ""
}