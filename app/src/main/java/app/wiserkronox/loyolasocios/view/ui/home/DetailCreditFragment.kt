package app.wiserkronox.loyolasocios.view.ui.home

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.view.ui.MainActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class DetailCreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_detail_credit, container, false)
        var args = this.arguments
        var data = args?.get("data")
        var json = JSONObject(data.toString())
        var credNumero = view.findViewById<TextView>(R.id.crednumber)
        var credFecDesem = view.findViewById<TextView>(R.id.credFecDesem)
        var credMontoDesem = view.findViewById<TextView>(R.id.credMontoDesem)
        var crediMoneda = view.findViewById<TextView>(R.id.crediMoneda)
        var crediSaldo = view.findViewById<TextView>(R.id.crediSaldo)
        var crediEstado = view.findViewById<TextView>(R.id.crediEstado)
        var crediFecCancel = view.findViewById<TextView>(R.id.crediFecCancel)
        // Change text
        credNumero.text = "N°CTA: " + json.get("credNumero").toString()
        credFecDesem.text = json.get("credFechaDesem").toString()
        credMontoDesem.text = json.get("credMontoDesem").toString()
        crediMoneda.text = json.get("credMoneda").toString()
        crediSaldo.text = json.get("credSaldo").toString()
        crediEstado.text = json.get("credEstado").toString()
        crediFecCancel.text = json.get("CredFechaCancel").toString()

        return view
    }

}