package app.wiserkronox.loyolasocios.view.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
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
        val url: String = "${getString(R.string.host_service)}services/historial_cred_cly.php?docu-cage=${user!!.id_member}"
        val queue = Volley.newRequestQueue(activity);
        val creditRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {jsonObject->

                var data = jsonObject.getJSONArray("result");

                for(i in 0 until data.length()) {

                    var trow = TableRow(activity)
                    var tableParams = TableLayout.LayoutParams()
                    tableParams.setMargins(5,12,0,5)

                    trow.layoutParams = tableParams

                    var number = TextView(activity)
                    number.gravity = Gravity.LEFT
                    number.setTypeface(null, Typeface.BOLD)
                    number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)

                    var moneda = TextView(activity)
                    moneda.gravity = Gravity.CENTER
                    moneda.setTypeface(null, Typeface.BOLD)
                    moneda.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)


                    var estado = TextView(activity)
                    estado.gravity = Gravity.CENTER
                    estado.setTypeface(null, Typeface.BOLD)
                    estado.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)

                    var list = LinearLayout(activity)

                    var btndetail = TextView(activity)
                    btndetail.gravity = Gravity.CENTER
                    btndetail.setTypeface(null, Typeface.BOLD)
                    btndetail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)

                    var btnplanpagos = TextView(activity)
                    btnplanpagos.gravity = Gravity.CENTER
                    btnplanpagos.setTypeface(null, Typeface.BOLD)
                    btnplanpagos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)

                    var btnextractos = TextView(activity)
                    btnextractos.gravity = Gravity.CENTER
                    btnextractos.setTypeface(null, Typeface.BOLD)
                    btnextractos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    //Inser Value

                    number.text = data.getJSONObject(i).get("credNumero").toString()
                    moneda.text = data.getJSONObject(i).get("credMoneda").toString()
                    estado.text = data.getJSONObject(i).get("credEstado").toString()

                    btndetail.text = "Ver Detalle"
                    btndetail.setTextColor(Color.parseColor("#FF3700B3"))

                    btnplanpagos.text = "Plan Pagos"
                    btnplanpagos.setTextColor(Color.parseColor("#FF3700B3"))

                    btnextractos.text = "Extractos"
                    btnextractos.setTextColor(Color.parseColor("#FF3700B3"))

                    //Insert Row

                    var paramsl = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                    )

                    list.orientation = LinearLayout.VERTICAL

                    list.addView(btndetail,paramsl)
                    list.addView(btnplanpagos,paramsl)
                    list.addView(btnextractos,paramsl)

                    trow.addView(number)
                    trow.addView(moneda)
                    trow.addView(estado)
                    trow.addView(list)

                    btndetail.setOnClickListener {
                        val details = data.getJSONObject(i).toString()
                        val bundle = Bundle()
                        bundle.putString("data",details)
                        view.findNavController().navigate(R.id.action_detaill, bundle)
                    }

                    btnplanpagos.setOnClickListener {
                        val details = data.getJSONObject(i).toString()
                        val bundle = Bundle()
                        bundle.putString("data",details)
                        view.findNavController().navigate(R.id.action_plane_pay_credit, bundle)
                    }

                    btnextractos.setOnClickListener {
                        val details = data.getJSONObject(i).toString()
                        val bundle = Bundle()
                        bundle.putString("data",details)
                        view.findNavController().navigate(R.id.action_extract_credit, bundle)
                    }
                    //Insert row in table
                    trow.setBackgroundColor(Color.parseColor("#FFFFFF"))
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