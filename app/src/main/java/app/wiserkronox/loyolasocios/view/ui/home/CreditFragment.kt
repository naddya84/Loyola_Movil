package app.wiserkronox.loyolasocios.view.ui.home

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.view.ui.MainActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class CreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_credit, container, false)

        var tableLayout = view.findViewById<TableLayout>(R.id.tableCredit)
        tableLayout.setColumnStretchable(0,true)
        tableLayout.setColumnStretchable(1,true)
        tableLayout.setColumnStretchable(2,true)
        tableLayout.setColumnStretchable(3,true)

        var user = LoyolaApplication.getInstance()?.user
        val url: String = "${getString(R.string.host_service)}services/historia-cre-cly.php?docu-cage=${user!!.id_member}"
        val queue = Volley.newRequestQueue(activity);
        val creditRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {jsonObject->

                var data = jsonObject.getJSONArray("result")

                for(i in 0 until data.length()) {

                    var trow = TableRow(activity)
                    var number = TextView(activity)
                    var moneda = TextView(activity)
                    var estado = TextView(activity)
                    var button = Button(activity)

                    //Inser Value
                    number.text = data.getJSONObject(i).get("credNumero").toString()
                    moneda.text = data.getJSONObject(i).get("crediMoneda").toString()
                    estado.text = data.getJSONObject(i).get("crediEstado").toString()
                    button.text = "Ver Detalles"
                    //Insert Row
                    var params = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                    params.setMargins(13,10,13,10)

                    trow.addView(number,params)
                    trow.addView(moneda,params)
                    trow.addView(estado,params)
                    trow.addView(button,params)
                    button.setOnClickListener {
                        val details = data.getJSONObject(i).toString()
                        val bundle = Bundle()
                        bundle.putString("data",details)
                        view.findNavController().navigate(R.id.action_detaill, bundle)
                    }
                    //Insert row in table
                    tableLayout.addView(trow)
                }
            },
            {volleyError->
                Toast.makeText(activity,volleyError.message, Toast.LENGTH_SHORT).show()
                println(volleyError.message)
            })

        queue.add(creditRequest)
        // Inflate the layout for this fragment
        return view
    }

}