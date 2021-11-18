package app.wiserkronox.loyolasocios.view.ui.home

import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
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

        var lnlheader = view.findViewById<LinearLayout>(R.id.lnlheader_extract)
        var lnlheaderinformation = view.findViewById<LinearLayout>(R.id.lnlheaderinformation)

        var bg = resources.getDrawable(R.drawable.tags_rounded_corners_bottoms)
        bg.setTint(Color.WHITE)

        lnlheaderinformation.background = bg

        var shape = GradientDrawable()
        shape.setColor(Color.parseColor("#008945"))
        shape.cornerRadius = 20f
        lnlheader.background = shape

        var nrocred = view.findViewById<TextView>(R.id.txtnrocredito)

        var bg_moneda = resources.getDrawable(R.drawable.circle_text_view)
        bg_moneda.setBounds(0,0,100,100)

        var moneda = view.findViewById<TextView>(R.id.txtmonedacredito)
        moneda.setTextColor(Color.WHITE)
        moneda.setTypeface(null, Typeface.BOLD)
        moneda.setBackgroundResource(R.drawable.circle_text_view)



        nrocred.text = json.get("credNumero").toString()
        moneda.text = json.get("credMoneda").toString()


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

                    for (i in 0 until data.length()) {

                        var capital = view.findViewById<TextView>(R.id.txtcapital)
                        var plaso = view.findViewById<TextView>(R.id.txttasa)
                        var formpago = view.findViewById<TextView>(R.id.txtformapago)

                        capital.text = data.getJSONObject(i).get("credMontoDesem").toString() + " "+ moneda.text +"."
                        plaso.text = data.getJSONObject(i).get("credPlazo").toString()
                        formpago.text = data.getJSONObject(i).get("credForPago").toString()

                    }
                    //CONSULT TABLE
                    var detail = jsonObject.getJSONArray("detail");
                    var lnlmainbodydetails = view.findViewById<LinearLayout>(R.id.lnlbodydetails)

                    for (i in 0 until detail.length()) {

                        var lnlbodydetails = LinearLayout(activity)
                        lnlbodydetails.orientation = LinearLayout.VERTICAL

                        var paramslnlody = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            340
                        )

                        paramslnlody.setMargins(20,25,20,25)

                        lnlbodydetails.setLayoutParams(paramslnlody)


                        //HEADER
                        var bgheader = resources.getDrawable(R.drawable.tags_rounded_corners_tops)
                        bgheader.setTint(Color.parseColor("#BCBCBC"))

                        var paramsHeader = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            140
                        )

                        var lnlbodyhearder = LinearLayout(activity)
                        lnlbodyhearder.orientation = LinearLayout.VERTICAL
                        lnlbodyhearder.setLayoutParams(paramsHeader)

                        var lnltitle = LinearLayout(activity)
                        lnltitle.orientation = LinearLayout.HORIZONTAL
                        lnltitle.setPadding(10,10,0,0)

                        var lnltitleinformation = LinearLayout(activity)
                        lnltitleinformation.orientation = LinearLayout.HORIZONTAL
                        lnltitleinformation.setPadding(10,10,0,0)

                        var paramslbls = LinearLayout.LayoutParams(
                            620,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )

                        var lblfecha = TextView(activity)
                        lblfecha.text = "Fecha de Vencimiento"
                        lblfecha.setLayoutParams(paramslbls)
                        lblfecha.setTextColor(Color.WHITE)
                        lblfecha.setTypeface(null, Typeface.BOLD)


                        var lblnrotrans = TextView(activity)
                        lblnrotrans.text = "N°.Cuota"
                        lblnrotrans.setLayoutParams(paramslbls)
                        lblnrotrans.setTextColor(Color.WHITE)
                        lblnrotrans.setTypeface(null, Typeface.BOLD)

                        lnltitle.addView(lblfecha)
                        lnltitle.addView(lblnrotrans)

                        //Information
                        var txtfecha = TextView(activity)
                        txtfecha.text = detail.getJSONObject(i).get("credFecVenci").toString()
                        txtfecha.setTextColor(Color.BLACK)
                        txtfecha.setTypeface(null, Typeface.BOLD)
                        txtfecha.setLayoutParams(paramslbls)

                        var txtnrotrans = TextView(activity)
                        txtnrotrans.text = detail.getJSONObject(i).get("credNumCuota").toString()
                        txtnrotrans.setTextColor(Color.BLACK)
                        txtnrotrans.setTypeface(null, Typeface.BOLD)
                        txtnrotrans.setLayoutParams(paramslbls)

                        lnltitleinformation.addView(txtfecha)
                        lnltitleinformation.addView(txtnrotrans)

                        //Create Header
                        lnlbodyhearder.background = bgheader
                        lnlbodyhearder.addView(lnltitle)
                        lnlbodyhearder.addView(lnltitleinformation)


                        var bgdivbody = resources.getDrawable(R.drawable.tags_rounded_corners_bottoms)
                        bgdivbody.setTint(Color.parseColor("#FFFFFF"))

                        var lnldivbody = LinearLayout(activity)
                        lnldivbody.orientation = LinearLayout.HORIZONTAL
                        lnldivbody.background = bgdivbody
                        lnldivbody.setLayoutParams(
                            LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                            )
                        )

                        //INFORMATION PARAMS
                        var paramsinformation = LinearLayout.LayoutParams(
                            350,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )

                        var paramstxts = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )

                        paramstxts.setMargins(10,2,10,0)

                        //LNL FOR LABELS
                        var lnllabels = LinearLayout(activity)
                        lnllabels.orientation = LinearLayout.VERTICAL
                        lnllabels.setLayoutParams(paramsinformation)

                        var lbldetailcapital = TextView(activity)
                        lbldetailcapital.text = "Capital: "
                        lbldetailcapital.setTypeface(null, Typeface.BOLD)
                        lbldetailcapital.setTextColor(Color.parseColor("#018645"))
                        lbldetailcapital.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                        lbldetailcapital.setLayoutParams(paramstxts)

                        var lbldetailsaldocapital = TextView(activity)
                        lbldetailsaldocapital.text = "Saldo Credito:"
                        lbldetailsaldocapital.setTypeface(null, Typeface.BOLD)
                        lbldetailsaldocapital.setTextColor(Color.parseColor("#018645"))
                        lbldetailsaldocapital.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                        lbldetailsaldocapital.setLayoutParams(paramstxts)

                        //Values
                        var lnltexts = LinearLayout(activity)
                        lnltexts.orientation = LinearLayout.VERTICAL
                        lnltexts.setLayoutParams(paramsinformation)

                        var txtdetailCapital = TextView(activity)
                        txtdetailCapital.text = detail.getJSONObject(i).get("credMontoCapi").toString() + " " + moneda.text +"."
                        txtdetailCapital.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                        txtdetailCapital.setTypeface(null, Typeface.BOLD)
                        txtdetailCapital.setTextColor(Color.BLACK)
                        txtdetailCapital.setLayoutParams(paramstxts)

                        var txtsaldocapitaldetail = TextView(activity)
                        txtsaldocapitaldetail.text = detail.getJSONObject(i).get("credSaldoCredi").toString() + " " + moneda.text + "."
                        txtsaldocapitaldetail.setTypeface(null, Typeface.BOLD)
                        txtsaldocapitaldetail.setTextColor(Color.BLACK)
                        txtsaldocapitaldetail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                        txtsaldocapitaldetail.setLayoutParams(paramstxts)

                        var shape = GradientDrawable()
                        shape.cornerRadius = 25F
                        shape.setColor(Color.parseColor("#00AB45"))


                        var icon_detail = resources.getDrawable(R.drawable.icon_detail)
                        icon_detail.setBounds(0,0,60,60)
                        icon_detail.setTint(Color.WHITE)

                        var btnDetalle = Button(activity)
                        btnDetalle.text = "DETALLES"
                        btnDetalle.background = shape
                        btnDetalle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                        btnDetalle.setTextColor(Color.parseColor("#FFFFFD"))
                        btnDetalle.setCompoundDrawables(null,icon_detail,null,null)
                        btnDetalle.setLayoutParams(
                            LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                        )

                        btnDetalle.setOnClickListener {
                            var dialog = DetailPlanePayCreditDialogFragment()
                            val details = detail.getJSONObject(i).toString()
                            val bundle = Bundle()
                            bundle.putString("data",details)
                            bundle.putString("moneda", json.get("credMoneda").toString())
                            bundle.putString("nrotrans", json.get("credNumero").toString())
                            dialog.arguments = bundle
                            dialog.show(childFragmentManager,"Texto")
                        }

                        lnltexts.addView(txtdetailCapital)
                        lnltexts.addView(txtsaldocapitaldetail)

                        lnllabels.addView(lbldetailcapital)
                        lnllabels.addView(lbldetailsaldocapital)

                        //Add body
                        lnldivbody.addView(lnllabels)
                        lnldivbody.addView(lnltexts)
                        lnldivbody.addView(btnDetalle)
                        //Add header to body
                        lnlbodydetails.addView(lnlbodyhearder,paramsHeader)
                        lnlbodydetails.addView(lnldivbody)
                        //Add Detail to main body
                        lnlmainbodydetails.addView(lnlbodydetails)

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

        var icon_download = resources.getDrawable(R.drawable.icon_download)
        icon_download.setBounds(0,0,50,50)
        icon_download.setTint(Color.WHITE)

        var paramsButtons = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            150)

        paramsButtons.setMargins(10,10,5,10)

        var btnGenerarPdf = view.findViewById<Button>(R.id.btngenerarpdf)
        btnGenerarPdf.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8F)
        btnGenerarPdf.setTextColor(Color.parseColor("#FFFFFD"))
        btnGenerarPdf.setLayoutParams(paramsButtons)
        btnGenerarPdf.setCompoundDrawables(null,icon_download,null,null)


        var btnContactar = view.findViewById<Button>(R.id.btncontactar)
        btnContactar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8F)
        btnContactar.setTextColor(Color.parseColor("#FFFFFD"))
        btnContactar.setLayoutParams(paramsButtons)
        btnContactar.setCompoundDrawables(null,icon_download,null,null)

        var shapeCorner = GradientDrawable()
        shapeCorner.cornerRadius = 25f
        shapeCorner.setColor(Color.parseColor("#00AB45"))

        btnGenerarPdf.background = shapeCorner
        btnContactar.background = shapeCorner

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