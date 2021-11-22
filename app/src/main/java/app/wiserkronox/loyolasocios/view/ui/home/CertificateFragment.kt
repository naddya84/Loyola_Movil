package app.wiserkronox.loyolasocios.view.ui.home

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.model.CertificateRequestModel
import app.wiserkronox.loyolasocios.service.repository.LoyolaService
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class CertificateFragment : Fragment() {

    private lateinit var progressBar:ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var user = LoyolaApplication.getInstance()?.user
        var view = inflater.inflate(R.layout.fragment_certificate, container, false)

        getCertificaCly(user!!.id_member)
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_certificate, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.certificates_progresbar)
        progressBar.visibility = View.VISIBLE
    }

    private fun getCertificaCly(docuCage:String ="") {
        val url: String = " ${getString(R.string.host_service)}${getString(R.string.home_service)}certifica-cly.php?docu-cage=${docuCage}"

        var request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                var responseObject = Gson().fromJson(response.toString(), CertificateRequestModel::class.java)

                val certificates = responseObject.result
                val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.adapter = CertificateAdapter(requireContext(), certificates)
                progressBar.visibility = View.INVISIBLE
            },
            { error ->
                Toast.makeText(activity, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show()
                println(error.message)
                error.printStackTrace()
                progressBar.visibility = View.INVISIBLE
            }
        )
        LoyolaService.getInstance(requireContext()).addToRequestQueue(request)
    }

}