package app.wiserkronox.loyolasocios.view.ui.home

import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


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

                var status = jsonObject.get("error")
                if(status == false) {

                    var data = jsonObject.getJSONArray("result");
                    //HEADER INFORMATION
                    var nroPrest = view.findViewById<TextView>(R.id.txtNroPrest)
                    var capital = view.findViewById<TextView>(R.id.txtMontoDesm)
                    var plazo = view.findViewById<TextView>(R.id.txtPlazo)
                    var tasa = view.findViewById<TextView>(R.id.txtEstado)
                    var ppago = view.findViewById<TextView>(R.id.txtPPago)
                    var fpago = view.findViewById<TextView>(R.id.txtFPago)
                    for (i in 0 until data.length()) {

                        nroPrest.text = data.getJSONObject(i).get("id_credito").toString()
                        capital.text = data.getJSONObject(i).get("credMontoDesem").toString()
                        plazo.text = data.getJSONObject(i).get("credPlazo").toString() + " MES(ES)"
                        tasa.text = data.getJSONObject(i).get("credTasa").toString()
                        ppago.text = data.getJSONObject(i).get("credPeriPago").toString() + " DIAS"
                        fpago.text = data.getJSONObject(i).get("credForPago").toString()

                    }
                    //CONSULT TABLE
                    var detail = jsonObject.getJSONArray("detail");

                    for (i in 0 until detail.length()) {

                        var trow = TableRow(activity)
                        var tableParams = TableLayout.LayoutParams()
                        tableParams.setMargins(5, 12, 0, 5)
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

                        var total = TextView(activity)
                        total.gravity = Gravity.CENTER
                        total.setTypeface(null, Typeface.BOLD)
                        total.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                        total.text = detail.getJSONObject(i).get("credTotalCuota").toString()

                        var btnDetallado = TextView(activity)
                        btnDetallado.gravity = Gravity.CENTER
                        btnDetallado.setTypeface(null, Typeface.BOLD)
                        btnDetallado.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                        btnDetallado.text = "Ver Detallado"
                        btnDetallado.setTextColor(Color.parseColor("#4674D4"))
                        btnDetallado.setPadding(25,25,25,25)

                        var icon = resources.getDrawable(R.drawable.btn_info)
                        icon.setBounds(0,0,10*5,10*5)
                        btnDetallado.setCompoundDrawables(icon,null,null,null)


                        btnDetallado.setOnClickListener {
                            var dialog = DetailPlanePayCreditDialogFragment()
                            val details = detail.getJSONObject(i).toString()
                            val bundle = Bundle()
                            bundle.putString("data",details)
                            dialog.arguments = bundle
                            dialog.show(childFragmentManager,"Texto")
                        }

                        trow.addView(nro)
                        trow.addView(fechavct)
                        trow.addView(total)
                        trow.addView(btnDetallado)

                        trow.setBackgroundColor(Color.parseColor("#FFFFFF"))
                        tableLayout.addView(trow)
                    }
                } else {
                    findNavController().popBackStack()
                    var msg = jsonObject.get("msg").toString()
                    Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
                }

            },
            {volleyError->
                Toast.makeText(activity,volleyError.message, Toast.LENGTH_SHORT).show()
                println(volleyError.message)
            })

        queue.add(planePayCreditRequest)

        var btnGenerarPdf = view.findViewById<Button>(R.id.btnGenerarPdf)

        btnGenerarPdf.setOnClickListener {
            var urlDowload : String = "${getString(R.string.host_service)}services/generate_pdf_plan_pagos.php?docu-cage=${user!!.id_member}&cred-number=${number}"

            var cookie = CookieManager.getInstance().getCookie(urlDowload.toString())
            var request = DownloadManager.Request(Uri.parse(urlDowload))
                .setTitle("crePlanPagos.pdf")
                .setDescription("Descargando....")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "crePlanPagos.pdf")
                .addRequestHeader("cookie", cookie)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            var dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
        }

        return view
    }

}