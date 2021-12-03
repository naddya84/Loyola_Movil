package app.wiserkronox.loyolasocios.view.ui.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import app.wiserkronox.loyolasocios.R
import java.text.SimpleDateFormat
import java.util.*

class DetailCreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_detail_credit, container, false)

        val args = this.arguments
        println(args.toString())

        val cred_id = args?.get("credId")
        val cred_number = args?.get("credNumero")
        val cred_fec_desem = args?.get("credFecDesem")
        val cred_monto_desem = args?.get("credMontoDesem")
        val credi_moneda = args?.get("crediMoneda")
        val credi_saldo = args?.get("crediSaldo")
        val credi_estado = args?.get("crediEstado")
        val credi_fec_cancel = args?.get("crediFecCancel")

        val text_number = root.findViewById<TextView>(R.id.crednumber)
        val text_disbursement_date = root.findViewById<TextView>(R.id.credFecDesem)
        val text_disbursed_amount = root.findViewById<TextView>(R.id.credMontoDesem)
        val text_coin = root.findViewById<TextView>(R.id.crediMoneda)
        val text_principal_balance = root.findViewById<TextView>(R.id.crediSaldo)
        val text_state = root.findViewById<TextView>(R.id.crediEstado)
        val text_cancellation_date = root.findViewById<TextView>(R.id.crediFecCancel)

        //Date format
        val date_format = SimpleDateFormat("dd-MMM-yyyy")
        val date_parse = SimpleDateFormat("yyyy-MM-dd")
        val disbusement_date = date_format.format(date_parse.parse(cred_fec_desem.toString()))
        val cancellation_date = date_format.format(date_parse.parse(credi_fec_cancel.toString()))
        //Number format

        // Change text
        text_number.text = ("N°CTA: " + cred_number.toString())
        text_disbursement_date.text = disbusement_date.toString()
        text_disbursed_amount.text = cred_monto_desem.toString()
        text_coin.text = credi_moneda.toString()
        text_principal_balance.text = credi_saldo.toString()
        text_state.text = credi_estado.toString()
        text_cancellation_date.text = cancellation_date.toString()

        val paramsButtons = LinearLayout.LayoutParams(
            500,
            200)

        paramsButtons.setMargins(15,15,15,15)

        val shape = GradientDrawable()
        shape.cornerRadius = 25F
        shape.setColor(Color.parseColor("#00AB45"))

        val icont_plane_pay = ResourcesCompat.getDrawable(resources,R.drawable.icon_plane_pay,context?.theme)
        icont_plane_pay?.setBounds(0,0,80,80)
        icont_plane_pay?.setTint(Color.WHITE)

        val icon_credit_extract = ResourcesCompat.getDrawable(resources,R.drawable.icon_extract,context?.theme)
        icon_credit_extract?.setBounds(0,0,80,80)
        icon_credit_extract?.setTint(Color.WHITE)

        val button_plan_pay = root.findViewById<Button>(R.id.btn_to_credit_plan_pay)

        button_plan_pay.background = shape
        button_plan_pay.setTextColor(Color.parseColor("#FFFFFD"))
        button_plan_pay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11F)
        button_plan_pay.setCompoundDrawables(null,icont_plane_pay,null,null)
        button_plan_pay.setLayoutParams(paramsButtons)

        button_plan_pay.setOnClickListener { view ->

            val bundle = Bundle()

            bundle.putString("credId",cred_id.toString())
            bundle.putString("credNumero",cred_number.toString())
            bundle.putString("credFecDesem",cred_fec_desem.toString())
            bundle.putString("credMontoDesem",cred_monto_desem.toString())
            bundle.putString("crediMoneda",credi_moneda.toString())
            bundle.putString("crediSaldo",credi_saldo.toString())
            bundle.putString("crediEstado",credi_estado.toString())
            bundle.putString("crediFecCancel",credi_fec_cancel.toString())

            view.findNavController().navigate(R.id.action_plane_pay_credit, bundle)
        }

        val button_to_credit_extract = root.findViewById<Button>(R.id.btn_to_credit_extract)

        button_to_credit_extract.background = shape
        button_to_credit_extract.setTextColor(Color.parseColor("#FFFFFD"))
        button_to_credit_extract.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11F)
        button_to_credit_extract.setCompoundDrawables(null,icon_credit_extract,null,null)
        button_to_credit_extract.setLayoutParams(paramsButtons)

        button_to_credit_extract.setOnClickListener { view->

            val bundle = Bundle()

            bundle.putString("credId",cred_id.toString())
            bundle.putString("credNumero",cred_number.toString())
            bundle.putString("credFecDesem",cred_fec_desem.toString())
            bundle.putString("credMontoDesem",cred_monto_desem.toString())
            bundle.putString("crediMoneda",credi_moneda.toString())
            bundle.putString("crediSaldo",credi_saldo.toString())
            bundle.putString("crediEstado",credi_estado.toString())
            bundle.putString("crediFecCancel",credi_fec_cancel.toString())

            view.findNavController().navigate(R.id.action_extract_credit, bundle)
        }

        return root
    }
}