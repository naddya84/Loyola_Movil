package app.wiserkronox.loyolasocios.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import app.wiserkronox.loyolasocios.R
import org.json.JSONObject

class DetailCreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_credit, container, false)
        val args = this.arguments
        val data = args?.get("data")
        val json = JSONObject(data.toString())
        val credNumero = view.findViewById<TextView>(R.id.crednumber)
        val credFecDesem = view.findViewById<TextView>(R.id.credFecDesem)
        val credMontoDesem = view.findViewById<TextView>(R.id.credMontoDesem)
        val crediMoneda = view.findViewById<TextView>(R.id.crediMoneda)
        val crediSaldo = view.findViewById<TextView>(R.id.crediSaldo)
        val crediEstado = view.findViewById<TextView>(R.id.crediEstado)
        val crediFecCancel = view.findViewById<TextView>(R.id.crediFecCancel)
        // Change text
        credNumero.text = ("N°CTA: " + json.get("credNumero").toString())
        credFecDesem.text = json.get("credFechaDesem").toString()
        credMontoDesem.text = json.get("credMontoDesem").toString()
        crediMoneda.text = json.get("credMoneda").toString()
        crediSaldo.text = json.get("credSaldo").toString()
        crediEstado.text = json.get("credEstado").toString()
        crediFecCancel.text = json.get("credFechaCancel").toString()

        return view
    }
}