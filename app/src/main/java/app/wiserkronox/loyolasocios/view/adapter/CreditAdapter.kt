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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.model.Credit
import app.wiserkronox.loyolasocios.service.repository.CreditRest
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class CreditAdapter(
    private val context: Context,
    private val dataset: List<Credit>


):RecyclerView.Adapter<CreditAdapter.CreditViewHolder>() {

    class CreditViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {

        val text_cred_number: TextView = view.findViewById(R.id.text_credit_number)
        val text_cred_moneda: TextView = view.findViewById(R.id.text_credit_moneda)

        val button_to_detail: Button = view.findViewById(R.id.button_to_credit_detail)
        val button_to_credit_plan_pay: Button = view.findViewById(R.id.button_to_credit_plan_pay)
        val button_to_credit_extract: Button = view.findViewById(R.id.button_to_credit_extract)

        val image_credit_state: ImageView = view.findViewById(R.id.image_status_credit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.credit_item, parent, false)

        return CreditViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        val item = dataset[position]

        holder.text_cred_number.text = item.number.toString()
        holder.text_cred_moneda.text = "Moneda: "+item.disbursed_amount.toString()+" "+item.coin.toString()+"."

        val money = DecimalFormat(
            "#,###.00", DecimalFormatSymbols.getInstance(Locale("es", "BOL")))
        money.roundingMode = RoundingMode.CEILING

        when(item.state) {
            "Activo"-> {
                holder.image_credit_state.setImageResource(R.drawable.ic_round_enabled)
            }
            "Cancelado" -> {
                holder.image_credit_state.setImageResource(R.drawable.ic_round_disabled)
            }
            "Retraso" -> {
                holder.image_credit_state.setImageResource(R.drawable.ic_round_disabled)
            }
            "Ejecución" -> {
                holder.image_credit_state.setImageResource(R.drawable.ic_round_disabled)
            }
            "Castigado" -> {
                holder.image_credit_state.setImageResource(R.drawable.ic_round_disabled)
            }
        }

        holder.button_to_detail.setOnClickListener { view ->
            val bundle = Bundle()

            val amount_desem = money.format(item.disbursed_amount)
            val balance = money.format(item.balance)

            bundle.putString("credId",item.credit_id.toString())
            bundle.putString("credNumero",item.number.toString())
            bundle.putString("credFecDesem",item.disburement_date.toString())
            bundle.putString("credMontoDesem",amount_desem.toString())
            bundle.putString("crediMoneda",item.coin.toString())
            bundle.putString("crediSaldo",balance.toString())
            bundle.putString("crediEstado",item.state.toString())
            bundle.putString("crediFecCancel",item.cancellation_date.toString())

            view.findNavController().navigate(R.id.action_detaill,bundle)
        }

        holder.button_to_credit_plan_pay.setOnClickListener { view ->

            val bundle = Bundle()
            val amount_desem = money.format(item.disbursed_amount)
            val balance = money.format(item.balance)

            bundle.putString("credId",item.credit_id.toString())
            bundle.putString("credNumero",item.number.toString())
            bundle.putString("credFecDesem",item.disburement_date.toString())
            bundle.putString("credMontoDesem",amount_desem.toString())
            bundle.putString("crediMoneda",item.coin.toString())
            bundle.putString("crediSaldo",balance.toString())
            bundle.putString("crediEstado",item.state.toString())
            bundle.putString("crediFecCancel",item.cancellation_date.toString())

            view.findNavController().navigate(R.id.action_plane_pay_credit,bundle)
        }

        holder.button_to_credit_extract.setOnClickListener {view ->

            val bundle = Bundle()
            val amount_desem = money.format(item.disbursed_amount)
            val balance = money.format(item.balance)

            bundle.putString("credId",item.credit_id.toString())
            bundle.putString("credNumero",item.number.toString())
            bundle.putString("credFecDesem",item.disburement_date.toString())
            bundle.putString("credMontoDesem",amount_desem.toString())
            bundle.putString("crediMoneda",item.coin.toString())
            bundle.putString("crediSaldo",balance.toString())
            bundle.putString("crediEstado",item.state.toString())
            bundle.putString("crediFecCancel",item.cancellation_date.toString())

            view.findNavController().navigate(R.id.action_extract_credit,bundle)
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}