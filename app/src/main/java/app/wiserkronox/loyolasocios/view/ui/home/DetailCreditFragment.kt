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
import org.json.JSONObject
import java.text.SimpleDateFormat

class DetailCreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_detail_credit, container, false)
        val args = this.arguments
        val data = args?.get("data")
        val json = JSONObject(data.toString())

        val text_cred_numero = view.findViewById<TextView>(R.id.text_cred_number_detail)
        val text_cred_fech_desem = view.findViewById<TextView>(R.id.text_fecha_desem_detail)
        val text_cred_monto_desem = view.findViewById<TextView>(R.id.text_mono_desem_detail)
        val text_cred_moneda = view.findViewById<TextView>(R.id.text_cred_moneda_detail)
        val text_cred_saldo = view.findViewById<TextView>(R.id.text_saldo_detail)
        val text_cred_estado = view.findViewById<TextView>(R.id.text_cred_state_detail)
        val text_fech_cancel = view.findViewById<TextView>(R.id.text_fecha_cancel_detail)

        //Date format
        val date_format = SimpleDateFormat("dd-MMM-yyyy")
        val date_parse = SimpleDateFormat("yyyy-MM-dd")
        val date_fecha_desem = date_format.format(date_parse.parse(json.get("credFechaDesem").toString()))
        val date_fech_cancel = date_format.format(date_parse.parse(json.get("crediFechaCancel").toString()))

        // Change text
        text_cred_numero.text = ("N°CTA: " + json.get("credNumero").toString())
        text_cred_fech_desem.text = date_fecha_desem.toString()
        text_cred_monto_desem.text = json.get("credMontoDesem").toString()
        text_cred_moneda.text = json.get("crediMoneda").toString()
        text_cred_saldo.text = json.get("crediSaldo").toString()
        text_cred_estado.text = json.get("crediEstado").toString()
        text_fech_cancel.text = date_fech_cancel.toString()

        val params_buttons = LinearLayout.LayoutParams(
            500,
            200)

        params_buttons.setMargins(15,15,15,15)

        val background_shape = GradientDrawable()
        background_shape.cornerRadius = 25F
        background_shape.setColor(Color.parseColor("#00AB45"))

        val icont_plane_pay = ResourcesCompat.getDrawable(resources,R.drawable.icon_plane_pay,context?.theme)
        icont_plane_pay?.setBounds(0,0,80,80)
        icont_plane_pay?.setTint(Color.WHITE)

        val icon_credit_extract = ResourcesCompat.getDrawable(resources,R.drawable.icon_extract,context?.theme)
        icon_credit_extract?.setBounds(0,0,80,80)
        icon_credit_extract?.setTint(Color.WHITE)

        val button_plan_pay = view.findViewById<Button>(R.id.button_to_credit_plan_pay)
        button_plan_pay.background = background_shape
        button_plan_pay.setTextColor(Color.parseColor("#FFFFFD"))
        button_plan_pay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11F)
        button_plan_pay.setCompoundDrawables(null,icont_plane_pay,null,null)
        button_plan_pay.setLayoutParams(params_buttons)

        button_plan_pay.setOnClickListener {
            val details = data.toString()
            val bundle = Bundle()
            bundle.putString("data",details)
            view.findNavController().navigate(R.id.action_plane_pay_credit, bundle)
        }

        val button_credit_extract = view.findViewById<Button>(R.id.button_to_credit_extract)
        button_credit_extract.background = background_shape
        button_credit_extract.setTextColor(Color.parseColor("#FFFFFD"))
        button_credit_extract.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11F)
        button_credit_extract.setCompoundDrawables(null,icon_credit_extract,null,null)
        button_credit_extract.setLayoutParams(params_buttons)

        button_credit_extract.setOnClickListener {
            val details = data.toString()
            val bundle = Bundle()
            bundle.putString("data",details)
            view.findNavController().navigate(R.id.action_extract_credit, bundle)
        }

        return view
    }
}