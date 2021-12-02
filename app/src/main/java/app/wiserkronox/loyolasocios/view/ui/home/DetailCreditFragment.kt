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
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DetailCreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_credit, container, false)
        val args = this.arguments

        val cred_id = args?.get("id")
        val cred_number = args?.get("credNumero")
        val cred_fec_desem = args?.get("credFecDesem")
        val cred_monto_desem = args?.get("credMontoDesem")
        val credi_moneda = args?.get("crediMoneda")
        val credi_saldo = args?.get("crediSaldo")
        val credi_estado = args?.get("crediEstado")
        val credi_fec_cancel = args?.get("crediFecCancel")

        val credNumero = view.findViewById<TextView>(R.id.crednumber)
        val credFecDesem = view.findViewById<TextView>(R.id.credFecDesem)
        val credMontoDesem = view.findViewById<TextView>(R.id.credMontoDesem)
        val crediMoneda = view.findViewById<TextView>(R.id.crediMoneda)
        val crediSaldo = view.findViewById<TextView>(R.id.crediSaldo)
        val crediEstado = view.findViewById<TextView>(R.id.crediEstado)
        val crediFecCancel = view.findViewById<TextView>(R.id.crediFecCancel)
        //Date format
        val dateformat = SimpleDateFormat("dd-MMM-yyyy")
        val dateparse = SimpleDateFormat("yyyy-MM-dd")
        val datefechdesem = dateformat.format(dateparse.parse(cred_fec_desem.toString()))
        val datefechcancel = dateformat.format(dateparse.parse(credi_fec_cancel.toString()))
        //Number format

        // Change text
        credNumero.text = ("N°CTA: " + cred_number.toString())
        credFecDesem.text = datefechdesem.toString()
        credMontoDesem.text = cred_monto_desem.toString()
        crediMoneda.text = credi_moneda.toString()
        crediSaldo.text = credi_saldo.toString()
        crediEstado.text = credi_estado.toString()
        crediFecCancel.text = datefechcancel.toString()

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

        val btnplanepay = view.findViewById<Button>(R.id.btntocreditplanepay)
        btnplanepay.background = shape
        btnplanepay.setTextColor(Color.parseColor("#FFFFFD"))
        btnplanepay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11F)
        btnplanepay.setCompoundDrawables(null,icont_plane_pay,null,null)
        btnplanepay.setLayoutParams(paramsButtons)

        btnplanepay.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("id",cred_id.toString())
            bundle.putString("number",cred_number.toString())
            bundle.putString("credFechDesem",cred_fec_desem.toString())
            bundle.putString("credmontodesem",cred_monto_desem.toString())
            bundle.putString("crediMoneda",credi_moneda.toString())
            bundle.putString("crediSaldo",credi_saldo.toString())
            bundle.putString("crediEstado",credi_estado.toString())
            bundle.putString("crediFecCancel",credi_fec_cancel.toString())

            view.findNavController().navigate(R.id.action_plane_pay_credit, bundle)
        }

        val btncreditextract = view.findViewById<Button>(R.id.btntocreditextract)
        btncreditextract.background = shape
        btncreditextract.setTextColor(Color.parseColor("#FFFFFD"))
        btncreditextract.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11F)
        btncreditextract.setCompoundDrawables(null,icon_credit_extract,null,null)
        btncreditextract.setLayoutParams(paramsButtons)

        btncreditextract.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("id",cred_id.toString())
            bundle.putString("number",cred_number.toString())
            bundle.putString("credFechDesem",cred_fec_desem.toString())
            bundle.putString("credmontodesem",cred_monto_desem.toString())
            bundle.putString("crediMoneda",credi_moneda.toString())
            bundle.putString("crediSaldo",credi_saldo.toString())
            bundle.putString("crediEstado",credi_estado.toString())
            bundle.putString("crediFecCancel",credi_fec_cancel.toString())

            view.findNavController().navigate(R.id.action_extract_credit, bundle)
        }

        return view
    }
}