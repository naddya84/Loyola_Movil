package app.wiserkronox.loyolasocios.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.view.ui.MainActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class CertificateFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var user = LoyolaApplication.getInstance()?.user
        //        Table generate
        var view = inflater.inflate(R.layout.fragment_certificate, container, false)

        var tableLayout = view.findViewById<TableLayout>(R.id.table_certificate)
        tableLayout.setColumnStretchable(0, true)
        tableLayout.setColumnStretchable(1, true)
        tableLayout.setColumnStretchable(2, true)
        tableLayout.setColumnStretchable(3, true)
        tableLayout.setColumnStretchable(4, true)
        tableLayout.setColumnStretchable(5, true)

        val url: String = " ${getString(R.string.host_service)}services/certifica-cly.php?docu-cage=${user!!.id_member}"
        val queque = Volley.newRequestQueue(activity)
        val certificatesRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {jsonObject ->
                // handle JSON response
                var data = jsonObject.getJSONArray("result")
               for( i in 0 until data.length()) {
                   //      Generate textview
                   var tRow = TableRow(activity)
                   var certGestionTxtV = TextView(activity)
                   var certNumeroTxtV = TextView(activity)
                   var certFecApertTxtV = TextView(activity)
                   var certCantiTxtV = TextView(activity)
                   var certMontoTxtV = TextView(activity)
                   var certEstadoTxtV = TextView(activity)
//      Insert values
                   certGestionTxtV.text = data.getJSONObject(i).get("certGestion").toString()
                   certNumeroTxtV.text = data.getJSONObject(i).get("certNumero").toString()
                   certFecApertTxtV.text = data.getJSONObject(i).get("certFecApert").toString()
                   certCantiTxtV.text = data.getJSONObject(i).get("certCanti").toString()
                   certMontoTxtV.text = data.getJSONObject(i).get("certMonto").toString()
                   certEstadoTxtV.text = data.getJSONObject(i).get("certEstado").toString()

//      Add to table row
                   tRow.addView(certGestionTxtV)
                   tRow.addView(certNumeroTxtV)
                   tRow.addView(certFecApertTxtV)
                   tRow.addView(certCantiTxtV)
                   tRow.addView(certMontoTxtV)
                   tRow.addView(certEstadoTxtV)
//      Add row to table
                   tableLayout.addView(tRow)

               }
            },
            {volleyError ->
                // handle error
                Toast.makeText(activity, volleyError.message, Toast.LENGTH_SHORT).show()
                println(volleyError.message)
            })
        queque.add(certificatesRequest)

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_certificate, container, false)
        return view
    }


}