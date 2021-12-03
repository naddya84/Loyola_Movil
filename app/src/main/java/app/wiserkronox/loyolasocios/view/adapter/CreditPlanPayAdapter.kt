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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.model.Credit
import app.wiserkronox.loyolasocios.service.model.CreditPlanPay
import app.wiserkronox.loyolasocios.service.repository.CertificateRest
import app.wiserkronox.loyolasocios.service.repository.CreditRest
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class CreditPlanPayAdapter(
    private val context: Context,
    private val dataset: List<CreditPlanPay>,
    private val cred_number: String,
    private val credi_moneda: String,
    private val cred_id: String,

):RecyclerView.Adapter<CreditPlanPayAdapter.CreditViewHolder>() {

    class CreditViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {

        //Titles

        val lnl_header_information : LinearLayout = view.findViewById(R.id.lnl_header_plan_pay)
        val lnl_body_information : LinearLayout = view.findViewById(R.id.lnl_header_information)

        val text_nro_credit : TextView = view.findViewById(R.id.text_number_credit_plan_pay)
        var text_moneda_credit : TextView = view.findViewById(R.id.text_moneda_credit_plan_pay)

        //Infomration
        var text_capital : TextView = view.findViewById(R.id.text_capital)
        var text_tasa : TextView = view.findViewById(R.id.text_tasa)
        var text_forma_pago : TextView = view.findViewById(R.id.text_forma_pago)

        //Buttons
        var button_get_pdf : Button = view.findViewById(R.id.button_get_pdf)
        var button_get_contact : Button = view.findViewById(R.id.button_get_contact)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.credit_plan_pay_item, parent, false)

        return CreditViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        val item = dataset[position]

        val money = DecimalFormat(
            "#,###.00", DecimalFormatSymbols.getInstance(Locale("es", "BOL")))
        money.roundingMode = RoundingMode.CEILING

        holder.text_capital.text = money.format(item.disbursed_amount).toString()
        holder.text_forma_pago.text = item.way_to_pay.toString()
        holder.text_tasa.text = money.format(item.term).toString()

        holder.text_nro_credit.text = cred_number
        holder.text_moneda_credit.text = credi_moneda

        val background_shape = GradientDrawable()
        background_shape.cornerRadii = floatArrayOf(
            20f,20f,
            20f,20f,
            0f,0f,
            0f,0f
        )

        background_shape.setColor(Color.parseColor("#008945"))

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

        holder.button_get_pdf.setOnClickListener {
            var user = LoyolaApplication.getInstance()?.user
            CreditRest(context as Activity).getCreditPlanPayPdf(user!!.id_member,cred_id.toString())
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}