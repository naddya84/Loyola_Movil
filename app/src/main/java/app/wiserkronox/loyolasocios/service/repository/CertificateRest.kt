package app.wiserkronox.loyolasocios.service.repository

import android.app.Activity
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.model.Certificate
import app.wiserkronox.loyolasocios.service.model.CertificateRequestModel
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CertificateRest(val context: Activity){
    companion object {
        const val GET_CERTIFICATES = "certifica-cly.php"
    }

    private fun getCertificateURL(): String {
        return "${context.getString(R.string.host_service)}${context.getString(R.string.home_service)}$GET_CERTIFICATES"
    }

    fun getCertificates(
        docuCage:String = "",
        onSuccess:(List<Certificate>?)->Unit,
        onError:(err:VolleyError, List<Certificate>?)->Unit
    ) {
        val url = "${getCertificateURL()}?docu-cage=$docuCage"

        var request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                var response = Gson().fromJson(
                    response.toString(),
                    CertificateRequestModel::class.java
                )
                val certificates = response.result.map { certificate ->
                    var newCertificate = Certificate()
                    newCertificate.year = certificate.certGestion.toString()
                    newCertificate.number = certificate.certNumero.toInt()
                    newCertificate.opening_date = certificate.certFecApert
                    newCertificate.amount = certificate.certCanti.toInt()
                    newCertificate.cost = certificate.certMonto.toDouble()
                    newCertificate.state = certificate.certEstado
                    return@map newCertificate
                }
                GlobalScope.launch {
                    LoyolaApplication.getInstance()?.repository?.deleteAllCertificates()
                    LoyolaApplication.getInstance()?.repository?.insertAllCertificates(certificates)
                    var dbCertificates = LoyolaApplication.getInstance()?.repository?.getAllCertificates()
                    context.runOnUiThread {
                        onSuccess(dbCertificates)
                    }
                }
            },
            { error ->
                GlobalScope.launch {
                    var dbCertificates = LoyolaApplication.getInstance()?.repository?.getAllCertificates()
                    context.runOnUiThread {
                        onError(error, dbCertificates)
                    }
                }
            }
        )
        LoyolaService.getInstance(context).addToRequestQueue(request)
    }
}
