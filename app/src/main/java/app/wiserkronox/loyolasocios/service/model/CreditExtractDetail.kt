package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class CreditExtractDetail {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo
    var id_credit_extract:Int = 0

    @ColumnInfo
    var id_credit_extract_detail:Int = 0

    //DATA
    @ColumnInfo
    var cred_fec_pago:String = ""

    @ColumnInfo
    var cred_nro_trans:Int = 0

    @ColumnInfo
    var cred_monto_capi:Double = 0.0

    @ColumnInfo
    var cred_monto_inte:Double = 0.0

    @ColumnInfo
    var credi_monto_penal:Double = 0.0

    @ColumnInfo
    var credi_monto_cargos:Double = 0.0

    @ColumnInfo
    var credi_total_pago:Double = 0.0

    @ColumnInfo
    var credi_saldo_capi:Double = 0.0
}