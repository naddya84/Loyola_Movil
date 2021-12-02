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
        val cred_num_cuota = args?.get("credNumCuota")
        val cred_fec_venci = args?.get("credFecVenci")
        val cred_monto_capi = args?.get("credMontoCapi")
        val cred_monto_inte = args?.get("credMontoInte")
        val credi_tota_cuota = args?.get("crediTotaCuota")
        val credi_monto_cargos = args?.get("crediMontoCargos")
        val credi_total_cuota = args?.get("crediTotalCuota")
        val credi_saldo_credi = args?.get("crediSaldoCredi")

        val text_ncred = view.findViewById<TextView>(R.id.titlecred)
        text_ncred.text = ("CREDITO\n"+args?.get("nrotrans").toString())

        val text_moneda = view.findViewById<TextView>(R.id.titlemoneda)
        text_moneda.text = (args?.get("moneda").toString() + ".")

        val text_fecha = view.findViewById<TextView>(R.id.txtFechaP)
        val date_format = SimpleDateFormat("dd-MMM-yyyy")
        val date_parse = SimpleDateFormat("yyyy-MM-dd")
        val date = date_format.format(date_parse.parse(cred_fec_venci.toString()))

        text_fecha.text = date

        val text_nro = view.findViewById<TextView>(R.id.txtNro)
        text_nro.text = cred_num_cuota.toString()

        val text_montocapi = view.findViewById<TextView>(R.id.txtMontoCapi)
        text_montocapi.text = (cred_monto_capi.toString() + " " + text_moneda.text)

        val text_montointer = view.findViewById<TextView>(R.id.txtMontoInteres)
        text_montointer.text = (cred_monto_inte.toString()+ " " + text_moneda.text)

        val text_montopenal = view.findViewById<TextView>(R.id.txtTotalCuota)
        text_montopenal.text = (credi_tota_cuota.toString()+ " " + text_moneda.text)

        val text_montocargo = view.findViewById<TextView>(R.id.txtMontoCargos)
        text_montocargo.text = (credi_monto_cargos.toString()+ " " + text_moneda.text)

        val text_totalpago = view.findViewById<TextView>(R.id.txtTotalpago)
        text_totalpago.text = (credi_total_cuota.toString()+ " " + text_moneda.text)

        val text_saldocargo = view.findViewById<TextView>(R.id.txtSaldoCredi)
        text_saldocargo.text = (credi_saldo_credi.toString()+ " " + text_moneda.text)

        val spanbutton = GradientDrawable()
        spanbutton.cornerRadius = 20f
        spanbutton.setColor(Color.parseColor("#00AB45"))

        val button = view.findViewById<Button>(R.id.btnCloseDialog)
        button.text = ("CERRAR").toString()
        button.setTypeface(null, Typeface.BOLD)
        button.setTextColor(Color.parseColor("#FFFFFF"))
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
        button.background = spanbutton

        button.setOnClickListener {
            this.dismiss()
        }

        return view
    }

}