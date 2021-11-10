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
import org.json.JSONObject
import org.w3c.dom.Text

class PlanePayCreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_plane_pay_credit, container, false)

        var args = this.arguments
        var data = args?.get("data")
        var json = JSONObject(data.toString())


        var tableParams = TableLayout.LayoutParams()
        tableParams.setMargins(5,12,0,5)

        var tableLayout = view.findViewById<TableLayout>(R.id.tblPlanePayCredit)
        tableLayout.setColumnStretchable(0,true)
        tableLayout.setColumnStretchable(1,true)
        tableLayout.setColumnStretchable(2,true)
        tableLayout.setColumnStretchable(3,true)
        tableLayout.setColumnStretchable(4,true)
        tableLayout.setColumnStretchable(5,true)
        tableLayout.setColumnStretchable(6,true)
        tableLayout.setColumnStretchable(7,true)

        var user = LoyolaApplication.getInstance()?.user //docu-cage
        var number = json.get("credNumero").toString() //cred-number

        //HEADER PLAN DE PAGOS
        val url: String = "${getString(R.string.host_service)}services/plan_pago_cly.php?docu-cage=${user!!.id_member}&cred-number=${number}"
        val queue = Volley.newRequestQueue(activity)

        val planePayCreditRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {jsonObject->

                var data = jsonObject.getJSONArray("result");
                //HEADER INFORMATION
                var nroPrest = view.findViewById<TextView>(R.id.txtNroPrest)
                var capital = view.findViewById<TextView>(R.id.txtCapital)
                var plazo = view.findViewById<TextView>(R.id.txtPlazo)
                var tasa = view.findViewById<TextView>(R.id.txtTasa)
                var ppago = view.findViewById<TextView>(R.id.txtPPago)
                var fpago = view.findViewById<TextView>(R.id.txtFPago)
                for(i in 0 until data.length()) {

                    nroPrest.text = data.getJSONObject(i).get("id_credito").toString()
                    capital.text = data.getJSONObject(i).get("credMontoDesem").toString()
                    plazo.text = data.getJSONObject(i).get("credPlazo").toString()
                    tasa.text = data.getJSONObject(i).get("credTasa").toString()
                    ppago.text = data.getJSONObject(i).get("credPeriPago").toString()
                    fpago.text = data.getJSONObject(i).get("credForPago").toString()

                }
                //CONSULT TABLE
                var detail = jsonObject.getJSONArray("detail");

                for(i in 0 until detail.length()) {

                    var trow = TableRow(activity)
                    var tableParams = TableLayout.LayoutParams()
                    tableParams.setMargins(5,12,0,5)
                    trow.layoutParams = tableParams

                    var nro = TextView(activity)
                    nro.gravity = Gravity.LEFT
                    nro.setTypeface(null, Typeface.BOLD)
                    nro.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    nro.text = detail.getJSONObject(i).get("credNumCuota").toString()

                    var fechavct = TextView(activity)
                    fechavct.gravity = Gravity.LEFT
                    fechavct.setTypeface(null, Typeface.BOLD)
                    fechavct.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    fechavct.text = detail.getJSONObject(i).get("credFecVenci").toString()

                    var capital = TextView(activity)
                    capital.gravity = Gravity.CENTER
                    capital.setTypeface(null, Typeface.BOLD)
                    capital.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    capital.text = detail.getJSONObject(i).get("credMontoCapi").toString()

                    var interes = TextView(activity)
                    interes.gravity = Gravity.CENTER
                    interes.setTypeface(null, Typeface.BOLD)
                    interes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    interes.text = detail.getJSONObject(i).get("credMontoInte").toString()

                    var cuota = TextView(activity)
                    cuota.gravity = Gravity.CENTER
                    cuota.setTypeface(null, Typeface.BOLD)
                    cuota.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    cuota.text = detail.getJSONObject(i).get("credTotaCuota").toString()

                    var cargo = TextView(activity)
                    cargo.gravity = Gravity.CENTER
                    cargo.setTypeface(null, Typeface.BOLD)
                    cargo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    cargo.text = detail.getJSONObject(i).get("credMontoCargos").toString()

                    var total = TextView(activity)
                    total.gravity = Gravity.CENTER
                    total.setTypeface(null, Typeface.BOLD)
                    total.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    total.text = detail.getJSONObject(i).get("credTotalCuota").toString()

                    var saldo = TextView(activity)
                    saldo.gravity = Gravity.CENTER
                    saldo.setTypeface(null, Typeface.BOLD)
                    saldo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    saldo.text = detail.getJSONObject(i).get("credSaldoCredi").toString()

                    trow.addView(nro)
                    trow.addView(fechavct)
                    trow.addView(capital)
                    trow.addView(interes)
                    trow.addView(cuota)
                    trow.addView(cargo)
                    trow.addView(total)
                    trow.addView(saldo)

                    trow.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    tableLayout.addView(trow)
                }

            },
            {volleyError->
                Toast.makeText(activity,volleyError.message, Toast.LENGTH_SHORT).show()
                println(volleyError.message)
            })

        queue.add(planePayCreditRequest)


        return view
    }

}