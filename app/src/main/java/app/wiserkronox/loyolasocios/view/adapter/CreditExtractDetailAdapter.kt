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
import app.wiserkronox.loyolasocios.service.model.CreditExtractDetail
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

class CreditExtractDetailAdapter(
    private val context: Context,
    private val dataset: List<CreditExtractDetail>,
    private val moneda: String,
    private val nrotrans: String,

):RecyclerView.Adapter<CreditExtractDetailAdapter.CreditViewHolder>() {

    class CreditViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {

        val lnl_header_information : LinearLayout = view.findViewById(R.id.lnl_header_credit_extract_detail)
        val lnl_body_information : LinearLayout = view.findViewById(R.id.lnl_body_credit_extract_detail)

        //Header
        val text_fecha_pago : TextView = view.findViewById(R.id.text_fecha_pago_credit_extract)
        val text_nro_trans : TextView = view.findViewById(R.id.text_nro_trans_credit_extract)

        //Body
        val text_capital : TextView = view.findViewById(R.id.text_capital_credit_extract_detail)
        val text_saldo_capital : TextView = view.findViewById(R.id.text_saldo_capital_credit_extract_detail)


        //Buttons
        var button_get_details : Button = view.findViewById(R.id.button_credit_extract_dialog)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.credit_extract_detail_item, parent, false)

        return CreditViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        val item = dataset[position]

        val money = DecimalFormat(
            "#,###.00", DecimalFormatSymbols.getInstance(Locale("es", "BOL")))
        money.roundingMode = RoundingMode.CEILING

        val date_format = SimpleDateFormat("dd-MMM-yyyy")
        val date_parse = SimpleDateFormat("yyyy-MM-dd")

        val date = date_format.format(date_parse.parse(item.payment_date.toString()))

        //Header
        holder.text_fecha_pago.text = date.toString()
        holder.text_nro_trans.text = item.number_transaction.toString()

        //Body
        holder.text_capital.text = money.format(item.principal_amount).toString() + " " + moneda.toString() + "."
        holder.text_saldo_capital.text = money.format(item.principal_balance).toString() + " " + moneda.toString() + "."


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

            val dialog = DetailExtractCreditDialogFragment()
            val bundle = Bundle()

            bundle.putString("nrotrans", nrotrans.toString())
            bundle.putString("moneda", moneda.toString())

            bundle.putString("credId",item.credit_extract_detail_id.toString())
            bundle.putString("credNroTrans",item.number_transaction.toString())
            bundle.putString("credFecPago",item.payment_date.toString())
            bundle.putString("credMontoCapi",money.format(item.principal_amount).toString())
            bundle.putString("credMontoInte",money.format(item.interest_amount).toString())
            bundle.putString("crediMontoPenal",money.format(item.penalty_amount).toString())
            bundle.putString("crediMontoCargos",money.format(item.amount_of_charges).toString())
            bundle.putString("crediTotalpago",money.format(item.total_to_pay).toString())
            bundle.putString("crediSaldoCapi",money.format(item.principal_balance).toString())

            dialog.arguments = bundle
            dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager,"Texto")

        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}