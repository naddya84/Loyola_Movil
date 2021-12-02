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

class DetailExtractCreditDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.WRAP_CONTENT)

        val snap = GradientDrawable()
        snap.cornerRadius = 60F

        dialog?.window?.setBackgroundDrawable(snap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_detail_extract_credit_dialog, container, false)

        val args = this.arguments
        val cred_fec_pago = args?.get("credFecPago").toString()
        val cred_nro_trans = args?.get("credNroTrans").toString()
        val cred_monto_capi = args?.get("credMontoCapi").toString()
        val cred_monto_inte = args?.get("credMontoInte").toString()
        val credi_monto_penal = args?.get("crediMontoPenal").toString()
        val credi_monto_cargos = args?.get("crediMontoCargos").toString()
        val credi_total_pago = args?.get("crediTotalpago").toString()
        val credi_saldo_capi = args?.get("crediSaldoCapi").toString()

        val text_ncred = view.findViewById<TextView>(R.id.titlecred)
        text_ncred.text = ("CREDITO\n"+args?.get("nrotrans").toString())

        val text_moneda = view.findViewById<TextView>(R.id.titlemoneda)
        text_moneda.text = (args?.get("moneda").toString() + ".")

        val text_fecha = view.findViewById<TextView>(R.id.txtFechaP)
        val date_format = SimpleDateFormat("dd-MMM-yyyy")
        val date_parse = SimpleDateFormat("yyyy-MM-dd")
        val date = date_format.format(date_parse.parse(cred_fec_pago))

        text_fecha.text = date

        val text_nro = view.findViewById<TextView>(R.id.txtNro)
        text_nro.text = cred_nro_trans

        val text_montocapi = view.findViewById<TextView>(R.id.txtMontoCapi)
        text_montocapi.text = (cred_monto_capi + " " + text_moneda.text)

        val text_montointer = view.findViewById<TextView>(R.id.txtMontoInteres)
        text_montointer.text = (cred_monto_inte + " " + text_moneda.text)

        val text_montopenal = view.findViewById<TextView>(R.id.txtTotalCuota)
        text_montopenal.text = (credi_monto_penal + " " + text_moneda.text)

        val text_montocargo = view.findViewById<TextView>(R.id.txtMontoCargos)
        text_montocargo.text = (credi_monto_cargos + " " + text_moneda.text)

        val text_totalpago = view.findViewById<TextView>(R.id.txtTotalpago)
        text_totalpago.text = (credi_total_pago + " " + text_moneda.text)

        val text_saldocargo = view.findViewById<TextView>(R.id.txtSaldoCredi)
        text_saldocargo.text = (credi_saldo_capi + " " + text_moneda.text)

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