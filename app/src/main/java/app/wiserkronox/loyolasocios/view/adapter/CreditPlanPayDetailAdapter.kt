package app.wiserkronox.loyolasocios.view.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.model.Credit
import app.wiserkronox.loyolasocios.service.model.CreditPlanPay
import app.wiserkronox.loyolasocios.service.model.CreditPlanPayDetail
import app.wiserkronox.loyolasocios.service.repository.CreditRest
import app.wiserkronox.loyolasocios.view.ui.home.DetailExtractCreditDialogFragment
import app.wiserkronox.loyolasocios.view.ui.home.DetailPlanePayCreditDialogFragment
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.w3c.dom.Text
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class CreditPlanPayDetailAdapter(
    private val context: Context,
    private val dataset: List<CreditPlanPayDetail>,
    private val moneda: String,
    private val nrotrans: String,

):RecyclerView.Adapter<CreditPlanPayDetailAdapter.CreditViewHolder>() {

    class CreditViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {

        val lnl_header_information : LinearLayout = view.findViewById(R.id.lnl_header_credit_plan_pay_detail)
        val lnl_body_information : LinearLayout = view.findViewById(R.id.lnl_body_credit_plan_pay_detail)

        //Header
        val text_fecha_pago : TextView = view.findViewById(R.id.text_fecha_pago)
        val text_nro_trans : TextView = view.findViewById(R.id.text_nro_trans)

        //Body
        val text_capital : TextView = view.findViewById(R.id.text_capital_credit_plan_pay_detail)
        val text_saldo_capital : TextView = view.findViewById(R.id.text_saldo_capital_credit_plan_pay_detail)


        //Buttons
        var button_get_details : Button = view.findViewById(R.id.button_credit_plan_pay_dialog)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.credit_plan_pay_detail_item, parent, false)

        return CreditViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        val item = dataset[position]

        val money = DecimalFormat(
            "#,###.00", DecimalFormatSymbols.getInstance(Locale("es", "BOL")))
        money.roundingMode = RoundingMode.CEILING

        val date_format = SimpleDateFormat("dd-MMM-yyyy")
        val date_parse = SimpleDateFormat("yyyy-MM-dd")

        val date = date_format.format(date_parse.parse(item.cred_fecha_venc.toString()))

        //Header
        holder.text_fecha_pago.text = date.toString()
        holder.text_nro_trans.text = item.cred_num_cuota.toString()

        //Body
        holder.text_capital.text = money.format(item.cred_monto_capi).toString() + " " + moneda.toString() + "."
        holder.text_saldo_capital.text = money.format(item.credi_saldo_credi).toString() + " " + moneda.toString() + "."


        val background_shape = GradientDrawable()
        background_shape.cornerRadii = floatArrayOf(
            20f,20f,
            20f,20f,
            0f,0f,
            0f,0f
        )

        background_shape.setColor(Color.parseColor("#BCBCBC"))

        val background_shape_body = GradientDrawable()

        background_shape_body.cornerRadii = floatArrayOf(
            0f,0f,
            0f,0f,
            20f,20f,
            20f,20f
        )

        background_shape_body.setColor(Color.WHITE)

        holder.lnl_header_information.background = background_shape
        holder.lnl_body_information.background = background_shape_body

        //button
        holder.button_get_details.setOnClickListener {

            val dialog = DetailPlanePayCreditDialogFragment()
            val bundle = Bundle()

            bundle.putString("nrotrans", nrotrans.toString())
            bundle.putString("moneda", moneda.toString())

            bundle.putString("credId",item.id_credit_plan_pay_detail.toString())
            bundle.putString("credNumCuota",item.cred_num_cuota.toString())
            bundle.putString("credFecVenci",item.cred_fecha_venc.toString())
            bundle.putString("credMontoCapi",money.format(item.cred_monto_capi).toString())
            bundle.putString("credMontoInte",money.format(item.cred_monto_inte).toString())
            bundle.putString("crediTotaCuota",money.format(item.credi_tota_cuota).toString())
            bundle.putString("crediMontoCargos",money.format(item.credi_monto_cargos).toString())
            bundle.putString("crediTotalCuota",money.format(item.credi_total_cuota).toString())
            bundle.putString("crediSaldoCredi",money.format(item.credi_saldo_credi).toString())

            dialog.arguments = bundle

            dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager,"Texto")

        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}