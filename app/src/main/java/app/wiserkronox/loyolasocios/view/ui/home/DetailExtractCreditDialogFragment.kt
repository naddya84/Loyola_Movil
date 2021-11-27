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
        val data = args?.get("data")
        val json = JSONObject(data.toString())

        val ncred = view.findViewById<TextView>(R.id.titlecred)
        ncred.text = ("CREDITO\n"+args?.get("nrotrans").toString())

        val moneda = view.findViewById<TextView>(R.id.titlemoneda)
        moneda.text = (args?.get("moneda").toString() + ".")

        val fecha = view.findViewById<TextView>(R.id.txtFechaP)
        val dateformat = SimpleDateFormat("dd-MMM-yyyy")
        val dateparse = SimpleDateFormat("yyyy-MM-dd")
        val date = dateformat.format(dateparse.parse(json.get("credFecPago").toString()))

        fecha.text = date

        val nro = view.findViewById<TextView>(R.id.txtNro)
        nro.text = (json.get("credNroTrans").toString())

        val montocapi = view.findViewById<TextView>(R.id.txtMontoCapi)
        montocapi.text = (json.get("credMontoCapi").toString() + " " + moneda.text)

        val montointer = view.findViewById<TextView>(R.id.txtMontoInteres)
        montointer.text = (json.get("credMontoInte").toString() + " " + moneda.text)

        val montopenal = view.findViewById<TextView>(R.id.txtTotalCuota)
        montopenal.text = (json.get("credMontoPenal").toString() + " " + moneda.text)

        val montocargo = view.findViewById<TextView>(R.id.txtMontoCargos)
        montocargo.text = (json.get("credMontoCargos").toString() + " " + moneda.text)

        val totalpago = view.findViewById<TextView>(R.id.txtTotalpago)
        totalpago.text = (json.get("credTotalPago").toString() + " " + moneda.text)

        val saldocargo = view.findViewById<TextView>(R.id.txtSaldoCredi)
        saldocargo.text = (json.get("credSaldoCapi").toString() + " " + moneda.text)

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