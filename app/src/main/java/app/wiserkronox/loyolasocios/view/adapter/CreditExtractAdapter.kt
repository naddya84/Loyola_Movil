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
import app.wiserkronox.loyolasocios.service.model.CreditExtract
import app.wiserkronox.loyolasocios.service.model.CreditPlanPay
import app.wiserkronox.loyolasocios.service.repository.CertificateRest
import app.wiserkronox.loyolasocios.service.repository.CreditRest
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class CreditExtractAdapter(
    private val context: Context,
    private val dataset: List<CreditExtract>,
    private val cred_number: String,
    private val credi_moneda: String,
    private val cred_id: String,

):RecyclerView.Adapter<CreditExtractAdapter.CreditViewHolder>() {

    class CreditViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {

        //Titles

        val lnl_header_information : LinearLayout = view.findViewById(R.id.lnl_header_credit_extract)
        val lnl_body_information : LinearLayout = view.findViewById(R.id.lnl_body_credit_extract)

        val text_nro_credit : TextView = view.findViewById(R.id.text_number_credit_extract)
        var text_moneda_credit : TextView = view.findViewById(R.id.text_moneda_credit_extract)

        //Infomration
        var text_capital : TextView = view.findViewById(R.id.text_capital_credit_extract)
        var text_plazo : TextView = view.findViewById(R.id.text_plazo_credit_extract)
        var text_tasa : TextView = view.findViewById(R.id.text_tasa_credit_extract)

        //Buttons
        var button_get_pdf : Button = view.findViewById(R.id.button_get_pdf)
        var button_get_contact : Button = view.findViewById(R.id.button_get_contact)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.credit_extract_item, parent, false)

        return CreditViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        val item = dataset[position]

        val money = DecimalFormat(
            "#,###.00", DecimalFormatSymbols.getInstance(Locale("es", "BOL")))
        money.roundingMode = RoundingMode.CEILING

        holder.text_capital.text = money.format(item.cred_monto_desem).toString()
        holder.text_plazo.text = item.cred_plazo.toString()
        holder.text_tasa.text = item.cred_estado.toString()

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
            CreditRest(context as Activity).getCreditExtractPdf(user!!.id_member,cred_id.toString())
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}