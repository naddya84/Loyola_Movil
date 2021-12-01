package app.wiserkronox.loyolasocios.service.repository

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        const val GET_CERTIFICATES = "get_certificates.php"
        const val GET_CERTIFICATE_PDF = "get_certificate_pdf.php"
        const val GET_CERTIFICATES_PDF = "get_certificates_pdf.php"
        const val STORAGE_PERMISSION_CODE = 101
    }

    private fun getCertificateURL(): String {
        return "${context.getString(R.string.host_service)}${context.getString(R.string.home_service)}$GET_CERTIFICATES"
    }

    fun getCertificates(
        docuCage:String = "",
        onSuccess:(List<Certificate>?)->Unit,
        onError:(err:VolleyError, List<Certificate>?)->Unit
    ) {
        val url = "${getCertificateURL()}?docu_cage=$docuCage"

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

    fun getListCertificatesToPdf(docuCage: String ="") {
        var dowloadid:Long = 0
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        } else {
            val new = object: BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (id == dowloadid){
                        Toast.makeText(context, "Descarga completada", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            val uri ="${context.resources.getString(R.string.host_service)}${context.getString(R.string.home_service)}$GET_CERTIFICATES_PDF?docu_cage=${docuCage}"
            val request = DownloadManager.Request(Uri.parse(uri))
                .setTitle("certificados-loyola.pdf")
                .setDescription("Descargando....")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "certificados-loyola.pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            val dowloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dowloadid = dowloadManager.enqueue(request)

            context.registerReceiver(new, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
    }

    fun getCertificatePdf(certificateNumber: String ="") {
        var dowloadid:Long = 0
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        } else {
            val new = object: BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (id == dowloadid){
                        Toast.makeText(context, "Descarga completada", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            val uri ="${context.resources.getString(R.string.host_service)}${context.getString(R.string.home_service)}$GET_CERTIFICATE_PDF?certificate_number=${certificateNumber}"
            val request = DownloadManager.Request(Uri.parse(uri))
                .setTitle("certificado-$certificateNumber.pdf")
                .setDescription("Descargando....")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "certificado-$certificateNumber.pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            val dowloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dowloadid = dowloadManager.enqueue(request)

            context.registerReceiver(new, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
    }

}
