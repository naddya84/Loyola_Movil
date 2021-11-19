package app.wiserkronox.loyolasocios.view.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.setPadding
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

        var mainBody = view.findViewById<LinearLayout>(R.id.layoutmainbody)
        mainBody.setPadding(30,30,30,30)

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

                    var lnlBody = LinearLayout(activity)
                    lnlBody.orientation = LinearLayout.HORIZONTAL

                    var params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        280,
                        1f)

                    params.setMargins(0,30,0,20)

                    var number = TextView(activity)
                    var bg = resources.getDrawable(R.drawable.tags_rounded_corners)
                    bg.setTint(Color.parseColor("#008945"))
                    number.background = bg
                    number.text = "CREDITO\n"+ data.getJSONObject(i).get("credNumero").toString()
                    number.gravity = Gravity.CENTER
                    number.setTypeface(null, Typeface.BOLD)
                    number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                    number.setTextColor(Color.parseColor("#FFFFFD"))
                    number.setLayoutParams(LinearLayout.LayoutParams(
                        230,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    ))

                    var lnlsection = LinearLayout(activity)
                    lnlsection.orientation = LinearLayout.VERTICAL

                    var paramsSection = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0f
                    )
                    //Body left
                    lnlBody.setLayoutParams(params)
                    lnlBody.addView(number)
                    lnlBody.addView(lnlsection,paramsSection)


                    var bgmoneda = resources.getDrawable(R.drawable.tags_rounded_corners_top_right)
                    bgmoneda.setTint(Color.parseColor("#BCBCBC"))

                    var moneda = TextView(activity)
                    moneda.text = "Moneda: "+data.getJSONObject(i).get("credMoneda").toString()+"."
                    moneda.background = bgmoneda
                    moneda.setPadding(40,0,0,0)
                    moneda.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)


                    var bgSection = resources.getDrawable(R.drawable.tags_rounded_corners_bottom_right)
                    bgSection.setTint(Color.parseColor("#EFEFEF"))

                    var lnlSectionInformation = LinearLayout(activity)
                    lnlSectionInformation.orientation = LinearLayout.HORIZONTAL
                    lnlSectionInformation.background = bgSection

                    var paramsButtons = LinearLayout.LayoutParams(
                        208,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                    paramsButtons.setMargins(15,20,15,20)

                    var shape = GradientDrawable()
                    shape.cornerRadius = 25F
                    shape.setColor(Color.parseColor("#00AB45"))

                    var icon_detail = resources.getDrawable(R.drawable.icon_detail)
                    icon_detail.setBounds(0,0,63,63)
                    icon_detail.setTint(Color.WHITE)

                    var btnDetalle = Button(activity)
                    btnDetalle.background = shape
                    btnDetalle.text = "DETALLE"
                    btnDetalle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                    btnDetalle.setTextColor(Color.parseColor("#FFFFFD"))
                    btnDetalle.setLayoutParams(paramsButtons)
                    btnDetalle.setCompoundDrawables(null,icon_detail,null,null)

                    btnDetalle.setOnClickListener {
                        val details = data.getJSONObject(i).toString()
                        val bundle = Bundle()
                        bundle.putString("data",details)
                        view.findNavController().navigate(R.id.action_detaill, bundle)
                    }

                    var icon_plane_play = resources.getDrawable(R.drawable.icon_plane_pay)
                    icon_plane_play.setBounds(0,0,63,63)
                    icon_plane_play.setTint(Color.WHITE)

                    var btnPlanPagos = Button(activity)
                    btnPlanPagos.background = shape
                    btnPlanPagos.text = "PLAN DE PAGOS"
                    btnPlanPagos.setTextColor(Color.parseColor("#FFFFFD"))
                    btnPlanPagos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                    btnPlanPagos.setLayoutParams(paramsButtons)
                    btnPlanPagos.setCompoundDrawables(null,icon_plane_play,null,null)

                    btnPlanPagos.setOnClickListener {
                        val details = data.getJSONObject(i).toString()
                        val bundle = Bundle()
                        bundle.putString("data",details)
                        view.findNavController().navigate(R.id.action_plane_pay_credit, bundle)
                    }

                    var icon_extract = resources.getDrawable(R.drawable.icon_extract)
                    icon_extract.setBounds(0,0,63,63)
                    icon_extract.setTint(Color.WHITE)

                    var btnExtracto = Button(activity)
                    btnExtracto.background = shape
                    btnExtracto.text = "EXTRACTO"
                    btnExtracto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                    btnExtracto.setTextColor(Color.parseColor("#FFFFFD"))
                    btnExtracto.setLayoutParams(paramsButtons)
                    btnExtracto.setCompoundDrawables(null,icon_extract,null,null)

                    btnExtracto.setOnClickListener {
                        val details = data.getJSONObject(i).toString()
                        val bundle = Bundle()
                        bundle.putString("data",details)
                        view.findNavController().navigate(R.id.action_extract_credit, bundle)
                    }

                    var check = ImageView(activity)
                    var type = data.getJSONObject(i).get("credEstado").toString()
                    if(type == "Vigente") {
                        check.setBackgroundResource(R.drawable.icon_check)
                    } else {
                        check.setBackgroundResource(R.drawable.icon_no_check)
                    }

                    check.setLayoutParams(LinearLayout.LayoutParams(50,50))

                    //Actions Buttons
                    var paramsInformation = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        265,
                        1f)

                    lnlSectionInformation.setPadding(10)
                    lnlSectionInformation.setLayoutParams(paramsInformation)
                    lnlSectionInformation.addView(btnDetalle)
                    lnlSectionInformation.addView(btnPlanPagos)
                    lnlSectionInformation.addView(btnExtracto)
                    lnlSectionInformation.addView(check)
                    //Body Right
                    lnlsection.addView(moneda)
                    lnlsection.addView(lnlSectionInformation)
                    //Insert row in table
                    mainBody.addView(lnlBody)

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