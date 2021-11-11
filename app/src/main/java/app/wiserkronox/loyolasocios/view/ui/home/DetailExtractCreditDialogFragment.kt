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

class DetailExtractCreditDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_detail_extract_credit_dialog, container, false)

        var args = this.arguments
        var data = args?.get("data")
        var json = JSONObject(data.toString())

        var fecha = view.findViewById<TextView>(R.id.txtFechaP)
        fecha.text = "Fecha: " + json.get("credFecPago").toString()

        var nro = view.findViewById<TextView>(R.id.txtNro)
        nro.text = "N°: "+ json.get("credNroTrans").toString()

        var montocapi = view.findViewById<TextView>(R.id.txtMontoCapi)
        montocapi.text = json.get("credMontoCapi").toString()

        var montointer = view.findViewById<TextView>(R.id.txtMontoInteres)
        montointer.text = json.get("credMontoInte").toString()

        var montopenal = view.findViewById<TextView>(R.id.txtMontoPenal)
        montopenal.text = json.get("credMontoPenal").toString()

        var montocargo = view.findViewById<TextView>(R.id.txtMontoCargos)
        montocargo.text = json.get("credMontoCargos").toString()

        var totalpago = view.findViewById<TextView>(R.id.txtTotalpago)
        totalpago.text = json.get("credTotalpago").toString()

        var saldocargo = view.findViewById<TextView>(R.id.txtSaldoCapital)
        saldocargo.text = json.get("credSaldoCapi").toString()

        var button = view.findViewById<Button>(R.id.btnCloseDialog)

        button.setOnClickListener {
            this.dismiss()
        }

        return view
    }

}