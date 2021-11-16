package app.wiserkronox.loyolasocios.view.ui.home

import android.os.Bundle
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

        println(json)

        var fecha = view.findViewById<TextView>(R.id.txtFechaP)
        fecha.text = "Fecha de Venct: " + json.get("credFecVenci").toString()

        var nro = view.findViewById<TextView>(R.id.txtNro)
        nro.text = "N°: "+ json.get("credNumCuota").toString()

        var montocapi = view.findViewById<TextView>(R.id.txtMontoCapi)
        montocapi.text = json.get("credMontoCapi").toString()

        var montointer = view.findViewById<TextView>(R.id.txtMontoInteres)
        montointer.text = json.get("credMontoInte").toString()

        var montopenal = view.findViewById<TextView>(R.id.txtTotalCuota)
        montopenal.text = json.get("credTotaCuota").toString()

        var montocargo = view.findViewById<TextView>(R.id.txtMontoCargos)
        montocargo.text = json.get("credMontoCargos").toString()

        var totalpago = view.findViewById<TextView>(R.id.txtTotalpago)
        totalpago.text = json.get("credTotalCuota").toString()

        var saldocargo = view.findViewById<TextView>(R.id.txtSaldoCredi)
        saldocargo.text = json.get("credSaldoCredi").toString()

        var button = view.findViewById<Button>(R.id.btnCloseDialog)

        button.setOnClickListener {
            this.dismiss()
        }

        return view
    }

}