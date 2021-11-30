package app.wiserkronox.loyolasocios.view.ui.home

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat


class ExtractCreditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_extract_credit, container, false)

        val args = this.arguments
        val data = args?.get("data")
        val json = JSONObject(data.toString())

        //HEADER INFORMATION
        val lnl_header = view.findViewById<LinearLayout>(R.id.lnl_header_credit_extract)
        val lnl_header_information = view.findViewById<LinearLayout>(R.id.lnl_header_information_credit_extract)

        val bg = GradientDrawable()
        bg.cornerRadii = floatArrayOf(
            0f,0f,
            0f,0f,
            20f,20f,
            20f,20f
        )
        bg.setTint(Color.WHITE)

        lnl_header_information.background = bg

        val shape = GradientDrawable()
        shape.setColor(Color.parseColor("#008945"))
        shape.cornerRadius = 20f
        lnl_header.background = shape

        val text_nro_cred = view.findViewById<TextView>(R.id.text_nro_credit_extract)

        val text_moneda = view.findViewById<TextView>(R.id.text_moneda_credit_extract)
        text_moneda.setTextColor(Color.WHITE)
        text_moneda.setTypeface(null, Typeface.BOLD)
        text_moneda.setBackgroundResource(R.drawable.circle_text_view)

        text_nro_cred.text = json.get("credNumero").toString()
        text_moneda.text = json.get("crediMoneda").toString()

        val user = LoyolaApplication.getInstance()?.user //docu-cage
        val number = json.get("id").toString() //cred-number

        //HEADER PLAN DE PAGOS
        val url = "${getString(R.string.host_service)}services/get_credit_extract.php?docu-cage=${user!!.id_member}&cred-number=${number}"
        val queue = Volley.newRequestQueue(activity)

        val planePayCreditRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {jsonObject->

                val status = jsonObject.get("error")
                if(status == false) {

                    val date_extract = jsonObject.getJSONArray("result")

                    for (i in 0 until date_extract.length()) {
                        val text_capital = view.findViewById<TextView>(R.id.text_capital_credit_extract)
                        val text_plaso = view.findViewById<TextView>(R.id.text_plazo_credit_extract)
                        val text_tasa = view.findViewById<TextView>(R.id.txttasaextract)

                        text_capital.text = (date_extract.getJSONObject(i).get("credMontoDesem").toString() + " "+ text_moneda.text +".")
                        text_plaso.text = date_extract.getJSONObject(i).get("credPlazo").toString()
                        text_tasa.text = date_extract.getJSONObject(i).get("estado").toString()
                    }
                    //CONSULT TABLE
                    val detail = jsonObject.getJSONArray("detail")

                    val lnl_main_body_details = view.findViewById<LinearLayout>(R.id.lnl_body_details_credit_extract)

                    if(detail.length() == 0) {

                        val text_not_data = TextView(activity)
                        text_not_data.text = ("No existen extractos de creditos asignados")
                        text_not_data.setTextColor(Color.RED)
                        text_not_data.textAlignment = View.TEXT_ALIGNMENT_CENTER
                        lnl_main_body_details.addView(text_not_data)
                        val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_extract_credit)
                        progressbar.visibility = View.INVISIBLE
                        val body = view.findViewById<LinearLayout>(R.id.lnl_main_body_credit_extract)
                        body.visibility = View.VISIBLE

                    } else {
                        for (i in 0 until detail.length()) {

                            val lnl_body_details = LinearLayout(activity)
                            lnl_body_details.orientation = LinearLayout.VERTICAL

                            val params_lnl_body = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                340
                            )

                            params_lnl_body.setMargins(20,25,20,25)

                            lnl_body_details.setLayoutParams(params_lnl_body)


                            //HEADER
                            val background_header = GradientDrawable()
                            background_header.cornerRadii = floatArrayOf(
                                20f,20f,
                                20f,20f,
                                0f,0f,
                                0f,0f
                            )
                            background_header.setTint(Color.parseColor("#BCBCBC"))

                            val params_header = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                140
                            )

                            val lnl_body_header = LinearLayout(activity)
                            lnl_body_header.orientation = LinearLayout.VERTICAL
                            lnl_body_header.setLayoutParams(params_header)

                            val lnl_title = LinearLayout(activity)
                            lnl_title.orientation = LinearLayout.HORIZONTAL
                            lnl_title.setPadding(10,10,0,0)

                            val lnl_title_information = LinearLayout(activity)
                            lnl_title_information.orientation = LinearLayout.HORIZONTAL
                            lnl_title_information.setPadding(10,10,0,0)

                            val params_labels = LinearLayout.LayoutParams(
                                620,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                            val label_fecha = TextView(activity)
                            label_fecha.text = ("Fecha de pago").toString()
                            label_fecha.setLayoutParams(params_labels)
                            label_fecha.setTextColor(Color.WHITE)
                            label_fecha.setTypeface(null, Typeface.BOLD)


                            val lblnrotrans = TextView(activity)
                            lblnrotrans.text = ("N°.Transacción").toString()
                            lblnrotrans.setLayoutParams(params_labels)
                            lblnrotrans.setTextColor(Color.WHITE)
                            lblnrotrans.setTypeface(null, Typeface.BOLD)

                            lnl_title.addView(label_fecha)
                            lnl_title.addView(lblnrotrans)

                            //Information
                            val text_fecha = TextView(activity)
                            val date_format = SimpleDateFormat("dd-MMM-yyyy")
                            val date_parse = SimpleDateFormat("yyyy-MM-dd")
                            val date = date_format.format(date_parse.parse(detail.getJSONObject(i).get("credFecPago").toString()))
                            text_fecha.text = date.toString()
                            text_fecha.setTextColor(Color.BLACK)
                            text_fecha.setTypeface(null, Typeface.BOLD)
                            text_fecha.setLayoutParams(params_labels)

                            val text_nro_trans = TextView(activity)
                            text_nro_trans.text = detail.getJSONObject(i).get("credNroTrans").toString()
                            text_nro_trans.setTextColor(Color.BLACK)
                            text_nro_trans.setTypeface(null, Typeface.BOLD)
                            text_nro_trans.setLayoutParams(params_labels)

                            lnl_title_information.addView(text_fecha)
                            lnl_title_information.addView(text_nro_trans)

                            //Create Header
                            lnl_body_header.background = background_header
                            lnl_body_header.addView(lnl_title)
                            lnl_body_header.addView(lnl_title_information)

                            val background_div_body = GradientDrawable()
                            background_div_body.cornerRadii = floatArrayOf(
                                0f,0f,
                                0f,0f,
                                20f,20f,
                                20f,20f
                            )
                            background_div_body.setTint(Color.parseColor("#FFFFFF"))

                            val lnl_div_body = LinearLayout(activity)
                            lnl_div_body.orientation = LinearLayout.HORIZONTAL
                            lnl_div_body.background = background_div_body
                            lnl_div_body.setLayoutParams(
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT
                                )
                            )

                            //INFORMATION PARAMS
                            val params_information = LinearLayout.LayoutParams(
                                350,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                            val params_text = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                            params_text.setMargins(10,2,10,0)

                            //LNL FOR LABELS
                            val lnl_labels = LinearLayout(activity)
                            lnl_labels.orientation = LinearLayout.VERTICAL
                            lnl_labels.setLayoutParams(params_information)

                            val label_capital_detail = TextView(activity)
                            label_capital_detail.text = ("Capital: ").toString()
                            label_capital_detail.setTypeface(null, Typeface.BOLD)
                            label_capital_detail.setTextColor(Color.parseColor("#018645"))
                            label_capital_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                            label_capital_detail.setLayoutParams(params_text)

                            val label_saldo_capital_detail = TextView(activity)
                            label_saldo_capital_detail.text = ("Saldo Capital: ").toString()
                            label_saldo_capital_detail.setTypeface(null, Typeface.BOLD)
                            label_saldo_capital_detail.setTextColor(Color.parseColor("#018645"))
                            label_saldo_capital_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                            label_saldo_capital_detail.setLayoutParams(params_text)

                            //Values
                            val lnl_texts = LinearLayout(activity)
                            lnl_texts.orientation = LinearLayout.VERTICAL
                            lnl_texts.setLayoutParams(params_information)

                            val text_capital_detail = TextView(activity)
                            text_capital_detail.text = (detail.getJSONObject(i).get("credMontoCapi").toString() + " " + text_moneda.text +".")
                            text_capital_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                            text_capital_detail.setTypeface(null, Typeface.BOLD)
                            text_capital_detail.setTextColor(Color.BLACK)
                            text_capital_detail.setLayoutParams(params_text)

                            val text_saldo_capital_detail = TextView(activity)
                            text_saldo_capital_detail.text = (detail.getJSONObject(i).get("crediSaldoCapi").toString() + " " + text_moneda.text + ".")
                            text_saldo_capital_detail.setTypeface(null, Typeface.BOLD)
                            text_saldo_capital_detail.setTextColor(Color.BLACK)
                            text_saldo_capital_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                            text_saldo_capital_detail.setLayoutParams(params_text)

                            val background_shape = GradientDrawable()
                            background_shape.cornerRadius = 25F
                            background_shape.setColor(Color.parseColor("#00AB45"))


                            val icon_detail = ResourcesCompat.getDrawable(resources,R.drawable.icon_detail,context?.theme)
                            icon_detail?.setBounds(0,0,60,60)
                            icon_detail?.setTint(Color.WHITE)

                            val button_open_detail = Button(activity)
                            button_open_detail.text = ("DETALLES").toString()
                            button_open_detail.background = background_shape
                            button_open_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                            button_open_detail.setTextColor(Color.parseColor("#FFFFFD"))
                            button_open_detail.setCompoundDrawables(null,icon_detail,null,null)
                            button_open_detail.setLayoutParams(
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )
                            )

                            button_open_detail.setOnClickListener {
                                val dialog = DetailExtractCreditDialogFragment()
                                val details = detail.getJSONObject(i).toString()
                                val bundle = Bundle()
                                bundle.putString("data",details)
                                bundle.putString("moneda", json.get("crediMoneda").toString())
                                bundle.putString("nrotrans", json.get("credNumero").toString())
                                dialog.arguments = bundle
                                dialog.show(childFragmentManager,"Texto")
                            }

                            lnl_texts.addView(text_capital_detail)
                            lnl_texts.addView(text_saldo_capital_detail)

                            lnl_labels.addView(label_capital_detail)
                            lnl_labels.addView(label_saldo_capital_detail)

                            //Add body
                            lnl_div_body.addView(lnl_labels)
                            lnl_div_body.addView(lnl_texts)
                            lnl_div_body.addView(button_open_detail)
                            //Add header to body
                            lnl_body_details.addView(lnl_body_header,params_header)
                            lnl_body_details.addView(lnl_div_body)
                            //Add Detail to main body
                            lnl_main_body_details.addView(lnl_body_details)
                            val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_extract_credit)
                            progressbar.visibility = View.INVISIBLE
                            val body = view.findViewById<LinearLayout>(R.id.lnl_main_body_credit_extract)
                            body.visibility = View.VISIBLE
                        }
                    }

                } else {
                    val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_extract_credit)
                    progressbar.visibility = View.INVISIBLE
                    val body = view.findViewById<LinearLayout>(R.id.lnl_main_body_credit_extract)
                    body.visibility = View.INVISIBLE
                    findNavController().popBackStack()
                    val msg = jsonObject.get("msg").toString()
                    Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
                }

            },
            {volleyError->
                Toast.makeText(activity,volleyError.message, Toast.LENGTH_SHORT).show()
                println(volleyError.message)
                val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_extract_credit)
                progressbar.visibility = View.INVISIBLE
                val body = view.findViewById<LinearLayout>(R.id.lnl_main_body_credit_extract)
                body.visibility = View.VISIBLE
            })

        queue.add(planePayCreditRequest)


        val icon_download = ResourcesCompat.getDrawable(resources,R.drawable.icon_download,context?.theme)
        icon_download?.setBounds(0,0,50,50)
        icon_download?.setTint(Color.WHITE)

        val icon_whatsapp = ResourcesCompat.getDrawable(resources,R.drawable.icon_whatsapp, context?.theme)
        icon_whatsapp?.setBounds(0,0,50,50)
        icon_whatsapp?.setTint(Color.WHITE)

        val paramsButtons = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            150)

        paramsButtons.setMargins(10,10,5,10)

        val button_get_pdf = view.findViewById<Button>(R.id.button_get_pdf_credit_extract)
        button_get_pdf.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8F)
        button_get_pdf.setTextColor(Color.parseColor("#FFFFFD"))
        button_get_pdf.setLayoutParams(paramsButtons)
        button_get_pdf.setCompoundDrawables(null,icon_download,null,null)


        val button_get_contact = view.findViewById<Button>(R.id.button_get_contact_credit_extract)
        button_get_contact.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8F)
        button_get_contact.setTextColor(Color.parseColor("#FFFFFD"))
        button_get_contact.setLayoutParams(paramsButtons)
        button_get_contact.setCompoundDrawables(null,icon_whatsapp,null,null)

        val background_shape_corner = GradientDrawable()
        background_shape_corner.cornerRadius = 25f
        background_shape_corner.setColor(Color.parseColor("#00AB45"))

        button_get_pdf.background = background_shape_corner
        button_get_contact.background = background_shape_corner

        button_get_pdf.setOnClickListener {

            val STORAGE_PERMISSION_CODE = 101

            if (ContextCompat.checkSelfPermission(context as Activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
            } else {

                var dowloadid:Long = 0
                val new = object: BroadcastReceiver() {
                    override fun onReceive(p0: Context?, p1: Intent?) {
                        val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                        if (id == dowloadid){
                            Toast.makeText(context, "Descarga completada", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                val url_dowload = "${getString(R.string.host_service)}services/get_credit_extract_pdf.php?docu-cage=${user!!.id_member}&cred-number=${number}"

                val cookie = CookieManager.getInstance().getCookie(url_dowload)
                val request = DownloadManager.Request(Uri.parse(url_dowload))
                    .setTitle("creExtractosCredito.pdf")
                    .setDescription("Descargando....")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "creExtractosCredito.pdf")
                    .addRequestHeader("cookie", cookie)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedOverMetered(true)

                val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dowloadid = dm.enqueue(request)

                activity?.registerReceiver(new, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            }

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_extract_credit)
        val body = view.findViewById<LinearLayout>(R.id.lnl_main_body_credit_extract)
        progressbar.bringToFront()
        progressbar.visibility = View.VISIBLE
        body.visibility = View.INVISIBLE
    }

}