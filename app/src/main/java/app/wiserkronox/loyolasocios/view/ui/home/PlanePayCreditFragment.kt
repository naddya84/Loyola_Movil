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
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.repository.CreditRest
import app.wiserkronox.loyolasocios.view.adapter.CertificateAdapter
import app.wiserkronox.loyolasocios.view.adapter.CreditAdapter
import app.wiserkronox.loyolasocios.view.adapter.CreditPlanPayAdapter
import app.wiserkronox.loyolasocios.view.adapter.CreditPlanPayDetailAdapter
import app.wiserkronox.loyolasocios.view.ui.HomeActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat


class PlanePayCreditFragment : Fragment() {

    private lateinit var progressBar:ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewDetail: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_plane_pay_credit, container, false)

        val args = this.arguments
        val cred_number = args?.get("credNumero").toString()
        val cred_moneda = args?.get("crediMoneda").toString()
        var credit_id = args?.get("credId").toString()// id credit

        progressBar = root.findViewById(R.id.progressbar_credits_plan_pay)
        recyclerView = root.findViewById(R.id.recyclerview_credits_plan_pay)
        recyclerView.adapter = CreditPlanPayAdapter(requireContext(), emptyList(), cred_number, cred_moneda, credit_id)
        recyclerViewDetail = root.findViewById(R.id.recyclerview_credits_plan_pay_detail)
        recyclerViewDetail.adapter = CreditPlanPayDetailAdapter(requireContext(), emptyList(), cred_moneda, cred_number)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.VISIBLE

        val args = this.arguments

        var user = LoyolaApplication.getInstance()?.user
        var credit_id = args?.get("credId")// id credit
        //label title
        val cred_number = args?.get("credNumero").toString()
        val cred_moneda = args?.get("crediMoneda").toString()

        println(credit_id)
        CreditRest(requireActivity()).getCreditsPlanPay(
            user!!.id_member,
            credit_id.toString(),
            {
                creditsplanpay->
                    progressBar.visibility = View.INVISIBLE
                    println(creditsplanpay.toString())
                    if(creditsplanpay != null) {
                        recyclerView.adapter = CreditPlanPayAdapter(requireContext(), creditsplanpay, cred_number, cred_moneda,credit_id.toString())
                    }
            },
            {
                creditsplanpaydetail->
                    progressBar.visibility = View.INVISIBLE
                    println(creditsplanpaydetail.toString())
                    if(creditsplanpaydetail != null) {
                        recyclerViewDetail.adapter = CreditPlanPayDetailAdapter(requireContext(), creditsplanpaydetail, cred_moneda, cred_number)
                    }
            },
            {
                error,creditsplanpay ->
                    Toast.makeText(activity, "Error de conexión con el servidor", Toast.LENGTH_SHORT)
                    progressBar.visibility = View.INVISIBLE
                    if(creditsplanpay?.size == 0) {
                        (activity as HomeActivity).goHome()
                        recyclerView.adapter = CreditPlanPayAdapter(requireContext(), creditsplanpay, cred_number, cred_moneda, credit_id.toString())
                    }
            },
            {
                error,creditsplanpaydetail->
                    Toast.makeText(activity, "Error de conexión con el servidor", Toast.LENGTH_SHORT)
                    progressBar.visibility = View.INVISIBLE
                    if(creditsplanpaydetail?.size == 0) {
                        (activity as HomeActivity).goHome()
                        recyclerViewDetail.adapter = CreditPlanPayDetailAdapter(requireContext(), creditsplanpaydetail, cred_moneda, cred_number)
                    }
            }
        )

    }
}