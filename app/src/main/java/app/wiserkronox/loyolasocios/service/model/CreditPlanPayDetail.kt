package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class CreditPlanPayDetail {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo
    var id_credit_plan_pay:Int = 0

    @ColumnInfo
    var id_credit_plan_pay_detail:Int = 0

    @ColumnInfo
    var cred_num_cuota:Int = 0

    @ColumnInfo
    var cred_fecha_venc:String = ""

    @ColumnInfo
    var cred_monto_capi:Double = 0.0

    @ColumnInfo
    var cred_monto_inte:Double = 0.0

    @ColumnInfo
    var credi_tota_cuota:Double = 0.0

    @ColumnInfo
    var credi_monto_cargos:Double = 0.0

    @ColumnInfo
    var credi_total_cuota:Double = 0.0

    @ColumnInfo
    var credi_saldo_credi:Double = 0.0
}