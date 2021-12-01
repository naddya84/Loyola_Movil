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
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        val view = inflater.inflate(R.layout.fragment_credit, container, false)

        val main_body = view.findViewById<LinearLayout>(R.id.layout_main_body_credit_extract)
        main_body.setPadding(30,30,30,30)

        val user = LoyolaApplication.getInstance()?.user
        val url = "${getString(R.string.host_service)}${getString(R.string.home_aplication)}services/get_credit_history.php?docu-cage=${user!!.id_member}"
        val queue = Volley.newRequestQueue(activity)
        val creditRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {jsonObject->
                val error = jsonObject.get("error")

                if(error == true) {
                    val msg = jsonObject.get("msg").toString()
                    Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
                    val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_credit)
                    progressbar.visibility = View.INVISIBLE
                    findNavController().popBackStack()

                } else {
                    val data = jsonObject.getJSONArray("result")

                    for(i in 0 until data.length()) {

                        val lnl_body = LinearLayout(activity)
                        lnl_body.orientation = LinearLayout.HORIZONTAL

                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            280,
                            1f)

                        params.setMargins(0,30,0,20)

                        val lnl_left_information = LinearLayout(activity)
                        lnl_left_information.orientation = LinearLayout.VERTICAL

                        val text_number = TextView(activity)
                        val icon_state = TextView(activity)

                        val bg = GradientDrawable()
                        bg.cornerRadii = floatArrayOf(
                            20f,20f,
                            0f,0f,
                            0f,0f,
                            20f,20f
                        )
                        bg.setColor(Color.parseColor("#008945"))

                        text_number.text = ("CREDITO\n"+ data.getJSONObject(i).get("credNumero").toString())
                        text_number.gravity = Gravity.CENTER
                        text_number.setTypeface(null, Typeface.BOLD)
                        text_number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                        text_number.setTextColor(Color.parseColor("#FFFFFD"))
                        text_number.setLayoutParams(LinearLayout.LayoutParams(
                            220,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ))

                        icon_state.text = ("Estado\n"+ data.getJSONObject(i).get("crediEstado").toString())
                        icon_state.gravity = Gravity.CENTER
                        icon_state.setTypeface(null, Typeface.BOLD)
                        icon_state.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)

                        if(data.getJSONObject(i).get("crediEstado").toString() == "Castigado") {
                            icon_state.setTextColor(Color.RED)
                        }
                        if(data.getJSONObject(i).get("crediEstado").toString() == "Retraso") {
                            icon_state.setTextColor(Color.RED)
                        }
                        if(data.getJSONObject(i).get("crediEstado").toString() == "Ejecución") {
                            icon_state.setTextColor(Color.RED)
                        }
                        if(data.getJSONObject(i).get("crediEstado").toString() == "Cancelado") {
                            icon_state.setTextColor(Color.RED)
                        }
                        if(data.getJSONObject(i).get("crediEstado").toString() == "Activo") {
                            icon_state.setTextColor(Color.parseColor("#2ed111"))
                        }
                        icon_state.setLayoutParams(LinearLayout.LayoutParams(
                            220,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ))

                        val lnl_section = LinearLayout(activity)
                        lnl_section.orientation = LinearLayout.VERTICAL

                        val params_section = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                        )

                        val params_left = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                        )

                        //Body left
                        lnl_left_information.setPadding(5)
                        lnl_left_information.gravity = Gravity.CENTER
                        lnl_left_information.background = bg
                        lnl_left_information.addView(text_number)
                        lnl_left_information.addView(icon_state)
                        lnl_body.setLayoutParams(params)
                        lnl_body.addView(lnl_left_information,params_left)
                        lnl_body.addView(lnl_section,params_section)

                        val background_moneda = GradientDrawable()
                        background_moneda.cornerRadii = floatArrayOf(
                            0f,0f,
                            20f,20f,
                            0f,0f,
                            0f,0f
                        )
                        background_moneda.setTint(Color.parseColor("#BCBCBC"))

                        val text_moneda = TextView(activity)
                        text_moneda.text = ("Moneda: "+data.getJSONObject(i).get("credMontoDesem").toString()+" "+data.getJSONObject(i).get("crediMoneda").toString()+".")
                        text_moneda.background = background_moneda
                        text_moneda.setPadding(40,0,0,0)
                        text_moneda.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)


                        val background_section = GradientDrawable()
                        background_section.cornerRadii = floatArrayOf(
                            0f,0f,
                            0f,0f,
                            20f,20f,
                            0f,0f
                        )
                        background_section.setTint(Color.parseColor("#EFEFEF"))

                        val lnl_section_information = LinearLayout(activity)
                        lnl_section_information.orientation = LinearLayout.HORIZONTAL
                        lnl_section_information.background = background_section

                        val params_buttons = LinearLayout.LayoutParams(
                            208,
                            LinearLayout.LayoutParams.WRAP_CONTENT)

                        params_buttons.setMargins(15,20,15,20)

                        val shape = GradientDrawable()
                        shape.cornerRadius = 25F
                        shape.setColor(Color.parseColor("#00AB45"))

                        val icon_detail = ResourcesCompat.getDrawable(resources,R.drawable.icon_detail,context?.theme)
                        icon_detail?.setBounds(0,0,63,63)
                        icon_detail?.setTint(Color.WHITE)

                        val button_detail = Button(activity)
                        button_detail.background = shape
                        button_detail.text = ("DETALLE").toString()
                        button_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                        button_detail.setTextColor(Color.parseColor("#FFFFFD"))
                        button_detail.setLayoutParams(params_buttons)
                        button_detail.setCompoundDrawables(null,icon_detail,null,null)

                        button_detail.setOnClickListener {
                            val details = data.getJSONObject(i).toString()
                            val bundle = Bundle()
                            bundle.putString("data",details)
                            view.findNavController().navigate(R.id.action_detaill, bundle)
                        }

                        val icon_plane_play = ResourcesCompat.getDrawable(resources,R.drawable.icon_plane_pay,context?.theme)
                        icon_plane_play?.setBounds(0,0,63,63)
                        icon_plane_play?.setTint(Color.WHITE)

                        val button_plan_pay = Button(activity)
                        button_plan_pay.background = shape
                        button_plan_pay.text = ("PLAN DE PAGOS").toString()
                        button_plan_pay.setTextColor(Color.parseColor("#FFFFFD"))
                        button_plan_pay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                        button_plan_pay.setLayoutParams(params_buttons)
                        button_plan_pay.setCompoundDrawables(null,icon_plane_play,null,null)

                        button_plan_pay.setOnClickListener {
                            val details = data.getJSONObject(i).toString()
                            val bundle = Bundle()
                            bundle.putString("data",details)
                            view.findNavController().navigate(R.id.action_plane_pay_credit, bundle)
                        }

                        val icon_extract = ResourcesCompat.getDrawable(resources,R.drawable.icon_extract,context?.theme)
                        icon_extract?.setBounds(0,0,63,63)
                        icon_extract?.setTint(Color.WHITE)

                        val button_extract = Button(activity)
                        button_extract.background = shape
                        button_extract.text = ("EXTRACTO").toString()
                        button_extract.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                        button_extract.setTextColor(Color.parseColor("#FFFFFD"))
                        button_extract.setLayoutParams(params_buttons)
                        button_extract.setCompoundDrawables(null,icon_extract,null,null)

                        button_extract.setOnClickListener {
                            val details = data.getJSONObject(i).toString()
                            val bundle = Bundle()
                            bundle.putString("data",details)
                            view.findNavController().navigate(R.id.action_extract_credit, bundle)
                        }

                        val icon_check = ImageView(activity)
                        val type = data.getJSONObject(i).get("crediEstado").toString()

                        if(type == "Activo") {
                            icon_check.setBackgroundResource(R.drawable.icon_check)
                        } else {
                            icon_check.setBackgroundResource(R.drawable.icon_no_check)
                        }

                        icon_check.setLayoutParams(LinearLayout.LayoutParams(50,50))

                        //Actions Buttons
                        val params_information = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            265,
                            1f)

                        lnl_section_information.setPadding(10)
                        lnl_section_information.setLayoutParams(params_information)
                        lnl_section_information.addView(button_detail)
                        lnl_section_information.addView(button_plan_pay)
                        lnl_section_information.addView(button_extract)
                        lnl_section_information.addView(icon_check)
                        //Body Right
                        lnl_section.addView(text_moneda)
                        lnl_section.addView(lnl_section_information)
                        //Insert row in table
                        main_body.addView(lnl_body)
                        val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_credit)
                        progressbar.visibility = View.INVISIBLE

                    }
                }

            },
            {volleyError->
                Toast.makeText(activity,volleyError.message, Toast.LENGTH_SHORT).show()
                println(volleyError.message)
                val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_credit)
                progressbar.visibility = View.INVISIBLE
            })

        queue.add(creditRequest)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_credit)
        progressbar.visibility = View.VISIBLE
    }
}