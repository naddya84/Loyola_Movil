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
    var id_credit:Int = 0

    @ColumnInfo
    var id_credit_extract:Int = 0

    @ColumnInfo
    var cred_numero:Int = 0

    @ColumnInfo
    var cred_plazo:String = ""


    @ColumnInfo
    var cred_monto_desem:Double = 0.0

    @ColumnInfo
    var cred_estado:String = ""


}