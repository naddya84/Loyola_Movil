package app.wiserkronox.loyolasocios.view.ui.home

import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
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
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class ExtractCreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_extract_credit, container, false)

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

        var user = LoyolaApplication.getInstance()?.user //docu-cage
        var number = json.get("credNumero").toString() //cred-number

        //HEADER PLAN DE PAGOS
        val url: String = "${getString(R.string.host_service)}services/extracto_credito.php?docu-cage=${user!!.id_member}&cred-number=${number}"
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
                    var estado = view.findViewById<TextView>(R.id.txtEstado)
                    for (i in 0 until data.length()) {

                        nroPrest.text = data.getJSONObject(i).get("id_credito").toString()
                        capital.text = data.getJSONObject(i).get("credMontoDesem").toString()
                        plazo.text = data.getJSONObject(i).get("credPlazo").toString()
                        estado.text = data.getJSONObject(i).get("estado").toString()

                    }
                    //CONSULT TABLE
                    var detail = jsonObject.getJSONArray("detail");

                    for (i in 0 until detail.length()) {

                        var trow = TableRow(activity)
                        var tableParams = TableLayout.LayoutParams()
                        tableParams.setMargins(5, 12, 0, 5)
                        trow.layoutParams = tableParams

                        var fechavct = TextView(activity)
                        fechavct.gravity = Gravity.LEFT
                        fechavct.setTypeface(null, Typeface.BOLD)
                        fechavct.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                        fechavct.text = detail.getJSONObject(i).get("credFecPago").toString()

                        var nro = TextView(activity)
                        nro.gravity = Gravity.LEFT
                        nro.setTypeface(null, Typeface.BOLD)
                        nro.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                        nro.text = detail.getJSONObject(i).get("credNroTrans").toString()

                        var saldo = TextView(activity)
                        saldo.gravity = Gravity.CENTER
                        saldo.setTypeface(null, Typeface.BOLD)
                        saldo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                        saldo.text = detail.getJSONObject(i).get("credSaldoCapi").toString()

                        var btndetalle = TextView(activity)
                        btndetalle.gravity = Gravity.CENTER
                        btndetalle.setTypeface(null, Typeface.BOLD)
                        btndetalle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                        btndetalle.text = "Ver detallado"
                        btndetalle.setTextColor(Color.parseColor("#4674D4"))

                        var icon = resources.getDrawable(R.drawable.btn_info)
                        icon.setBounds(0,0,10*5,10*5)
                        btndetalle.setCompoundDrawables(icon,null,null,null)

                        btndetalle.setOnClickListener {
                            var dialog = DetailExtractCreditDialogFragment()
                            val details = detail.getJSONObject(i).toString()
                            val bundle = Bundle()
                            bundle.putString("data",details)
                            dialog.arguments = bundle
                            dialog.show(childFragmentManager,"Texto")
                        }

                        trow.addView(fechavct)
                        trow.addView(nro)
                        trow.addView(saldo)
                        trow.addView(btndetalle)

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
            var urlDowload : String = "${getString(R.string.host_service)}services/generate_pdf_extractos.php?docu-cage=${user!!.id_member}&cred-number=${number}"

            var cookie = CookieManager.getInstance().getCookie(urlDowload.toString())
            var request = DownloadManager.Request(Uri.parse(urlDowload))
                .setTitle("creExtractosCredito.pdf")
                .setDescription("Descargando....")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "creExtractosCredito.pdf")
                .addRequestHeader("cookie", cookie)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            var dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
        }

        return view
    }

}