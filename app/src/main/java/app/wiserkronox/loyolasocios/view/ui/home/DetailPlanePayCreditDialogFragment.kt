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

class DetailPlanePayCreditDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        var snap = GradientDrawable()
        snap.cornerRadius = 60F

        dialog?.window?.setBackgroundDrawable(snap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_detail_plane_pay_credit_dialog, container, false)

        var args = this.arguments
        var data = args?.get("data")
        var json = JSONObject(data.toString())

        var ncred = view.findViewById<TextView>(R.id.titlecred)
        ncred.text = "CREDITO\n"+args?.get("nrotrans").toString()

        var moneda = view.findViewById<TextView>(R.id.titlemoneda)
        moneda.text = args?.get("moneda").toString() + "."

        var fecha = view.findViewById<TextView>(R.id.txtFechaP)
        fecha.text = "Fecha: " + json.get("credFecVenci").toString()

        var nro = view.findViewById<TextView>(R.id.txtNro)
        nro.text = "N°: "+ json.get("credNumCuota").toString()

        var montocapi = view.findViewById<TextView>(R.id.txtMontoCapi)
        montocapi.text = json.get("credMontoCapi").toString() + " " + moneda.text

        var montointer = view.findViewById<TextView>(R.id.txtMontoInteres)
        montointer.text = json.get("credMontoInte").toString()+ " " + moneda.text

        var montopenal = view.findViewById<TextView>(R.id.txtTotalCuota)
        montopenal.text = json.get("credTotaCuota").toString()+ " " + moneda.text

        var montocargo = view.findViewById<TextView>(R.id.txtMontoCargos)
        montocargo.text = json.get("credMontoCargos").toString()+ " " + moneda.text

        var totalpago = view.findViewById<TextView>(R.id.txtTotalpago)
        totalpago.text = json.get("credTotalCuota").toString()+ " " + moneda.text

        var saldocargo = view.findViewById<TextView>(R.id.txtSaldoCredi)
        saldocargo.text = json.get("credSaldoCredi").toString()+ " " + moneda.text


        var spanbutton = GradientDrawable()
        spanbutton.cornerRadius = 20f
        spanbutton.setColor(Color.parseColor("#00AB45"))

        var button = view.findViewById<Button>(R.id.btnCloseDialog)
        button.text = "CERRAR"
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