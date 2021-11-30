package app.wiserkronox.loyolasocios.view.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import app.wiserkronox.loyolasocios.R
import org.json.JSONObject
import java.text.SimpleDateFormat

class DetailPlanePayCreditDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        val snapbg = GradientDrawable()
        snapbg.cornerRadius = 60F

        dialog?.window?.setBackgroundDrawable(snapbg)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_detail_plane_pay_credit_dialog, container, false)

        val args = this.arguments
        val data = args?.get("data")
        val json = JSONObject(data.toString())

        val text_ncred = view.findViewById<TextView>(R.id.label_title_nro_credit_plane_pay_dialog)
        text_ncred.text = ("CREDITO\n"+args?.get("nrotrans").toString())

        val text_moneda = view.findViewById<TextView>(R.id.label_title_moneda_credit_plane_pay_dialog)
        text_moneda.text = (args?.get("moneda").toString() + ".")

        val text_fecha = view.findViewById<TextView>(R.id.text_fecha_pago_credit_plane_pay_dialog)
        val date_format = SimpleDateFormat("dd-MMM-yyyy")
        val date_parse = SimpleDateFormat("yyyy-MM-dd")
        val date = date_format.format(date_parse.parse(json.get("credFecVenci").toString()))

        text_fecha.text = date

        val text_nro = view.findViewById<TextView>(R.id.text_nro_trans_credit_plane_pay_dialog)
        text_nro.text = (json.get("credNumCuota").toString())

        val text_monto_capi = view.findViewById<TextView>(R.id.text_monto_capi_credit_plane_pay_dialog)
        text_monto_capi.text = (json.get("credMontoCapi").toString() + " " + text_moneda.text)

        val text_monto_interes = view.findViewById<TextView>(R.id.text_monto_interes_credit_plane_pay_dialog)
        text_monto_interes.text = (json.get("credMontoInte").toString()+ " " + text_moneda.text)

        val text_monto_penal = view.findViewById<TextView>(R.id.text_total_cuota_credit_plane_pay_dialog)
        text_monto_penal.text = (json.get("crediTotaCuota").toString()+ " " + text_moneda.text)

        val text_monto_cargo = view.findViewById<TextView>(R.id.text_monto_cargos_credit_plane_pay_dialog)
        text_monto_cargo.text = (json.get("crediMontoCargos").toString()+ " " + text_moneda.text)

        val text_total_pago = view.findViewById<TextView>(R.id.text_total_pago_credit_plane_pay_dialog)
        text_total_pago.text = (json.get("crediTotalCuota").toString()+ " " + text_moneda.text)

        val text_saldo_cargo = view.findViewById<TextView>(R.id.text_saldo_credi_credit_plane_pay_dialog)
        text_saldo_cargo.text = (json.get("crediSaldoCredi").toString()+ " " + text_moneda.text)

        val background_shape = GradientDrawable()
        background_shape.cornerRadius = 20f
        background_shape.setColor(Color.parseColor("#00AB45"))

        val button_close = view.findViewById<Button>(R.id.button_close_plane_pay_dialog)
        button_close.text = ("CERRAR").toString()
        button_close.setTypeface(null, Typeface.BOLD)
        button_close.setTextColor(Color.parseColor("#FFFFFF"))
        button_close.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
        button_close.background = background_shape

        button_close.setOnClickListener {
            this.dismiss()
        }

        return view
    }

}