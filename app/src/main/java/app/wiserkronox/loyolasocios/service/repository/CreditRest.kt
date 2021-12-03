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
import app.wiserkronox.loyolasocios.service.model.*
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreditRest(val context: Activity){

    companion object {
        const val GET_CREDITS = "get_credit_history.php"
        const val GET_CREDITS_PLAN_PAY = "get_credit_plan_pay.php"
        const val GET_CREDITS_PLAN_PAY_PDF = "get_credit_plan_pay_pdf.php"
        const val GET_CREDITS_EXTRACT = "get_credit_extract.php"
        const val GET_CREDITS_EXTRACTS_PDF = "get_credit_extract_pdf.php"
        const val STORAGE_PERMISSION_CODE = 101
    }

    private fun getCreditURL(): String {
        return "${context.getString(R.string.host_service)}${context.getString(R.string.home_service)}$GET_CREDITS"
    }

    private fun getCreditPlanPayURL(): String{
        return "${context.getString(R.string.host_service)}${context.getString(R.string.home_service)}$GET_CREDITS_PLAN_PAY"
    }

    private fun getCreditExtractURL(): String{
        return "${context.getString(R.string.host_service)}${context.getString(R.string.home_service)}$GET_CREDITS_EXTRACT"
    }

    fun getCredits(
        docuCage:String = "",
        onSuccess:(List<Credit>?)->Unit,
        onError:(err:VolleyError, List<Credit>?)->Unit
    ) {
        val url = "${getCreditURL()}?docu_cage=$docuCage"

        var request = StringRequest(
            Request.Method.GET,
            url,
            { response ->

                var response = Gson().fromJson(
                    response.toString(),
                    CreditRequestModel::class.java
                )

                val credits = response.result.map { credit ->
                    var new_credit = Credit()
                    new_credit.credit_id = credit.id.toInt()
                    new_credit.number = credit.credNumero.toInt()
                    new_credit.disburement_date = credit.credFechaDesem.toString()
                    new_credit.disbursed_amount = credit.credMontoDesem.toDouble()
                    new_credit.coin = credit.crediMoneda.toString()
                    new_credit.balance = credit.crediSaldo.toDouble()
                    new_credit.state = credit.crediEstado.toString()
                    new_credit.cancellation_date = credit.crediFecCancel.toString()
                    return@map new_credit
                }
                GlobalScope.launch {
                    LoyolaApplication.getInstance()?.repository?.deleteAllCredits()
                    LoyolaApplication.getInstance()?.repository?.insertAllCredits(credits)
                    var dbCredits = LoyolaApplication.getInstance()?.repository?.getAllCredits()
                    context.runOnUiThread {
                        onSuccess(dbCredits)
                    }
                }
            },
            { error ->
                GlobalScope.launch {
                    var dbCredits = LoyolaApplication.getInstance()?.repository?.getAllCredits()
                    context.runOnUiThread {
                        onError(error, dbCredits)
                    }
                }
            }
        )
        LoyolaService.getInstance(context).addToRequestQueue(request)
    }

    fun getCreditsPlanPay(
        docuCage:String = "",
        credNumber:String = "",
        onSuccess:(List<CreditPlanPay>?)->Unit,
        onSuccessDetail: (List<CreditPlanPayDetail>?) -> Unit,
        onError:(err:VolleyError, List<CreditPlanPay>?)->Unit,
        onErrorDetail: (err: VolleyError, List<CreditPlanPayDetail>?) -> Unit
    ) {
        val url = "${getCreditPlanPayURL()}?docu_cage=$docuCage&cred_number=$credNumber"

        println(url);
        var request = StringRequest(
            Request.Method.GET,
            url,
            { response ->

                println(response.toString())
                var response = Gson().fromJson(
                    response.toString(),
                    CreditPlanPayRequestModel::class.java
                )

                if(response.error) {

                    Toast.makeText(context, response.errorMessage[1].toString(), Toast.LENGTH_SHORT)

                } else {

                    val credits_plan_pay = response.result.map { credit_plan_pay ->
                        var new_credit_plan_pay = CreditPlanPay()
                        new_credit_plan_pay.credit_id = credit_plan_pay.id_credit.toInt()
                        new_credit_plan_pay.credit_plan_pay_id = credit_plan_pay.id.toInt()
                        new_credit_plan_pay.disbursed_amount = credit_plan_pay.credMontoDesem.toDouble()
                        new_credit_plan_pay.term = credit_plan_pay.credPlazo.toInt()
                        new_credit_plan_pay.rate = credit_plan_pay.credTasa.toDouble()
                        new_credit_plan_pay.payment_period = credit_plan_pay.credPeriPago.toString()
                        new_credit_plan_pay.way_to_pay = credit_plan_pay.credForPago.toString()
                        return@map new_credit_plan_pay
                    }

                    val credits_plan_pay_detail = response.detail.map{ credit_plan_pay_detail ->
                        var new_credit_plan_pay_detail = CreditPlanPayDetail()
                        new_credit_plan_pay_detail.credit_plan_pay_detail_id = credit_plan_pay_detail.id.toInt()
                        new_credit_plan_pay_detail.credit_plan_pay_id = credit_plan_pay_detail.id_credit_plan_pay.toInt()
                        new_credit_plan_pay_detail.installment_number = credit_plan_pay_detail.credNumCuota.toInt()
                        new_credit_plan_pay_detail.due_date = credit_plan_pay_detail.credFecVenci.toString()
                        new_credit_plan_pay_detail.principal_amount = credit_plan_pay_detail.credMontoCapi.toDouble()
                        new_credit_plan_pay_detail.interest_amount = credit_plan_pay_detail.credMontoInte.toDouble()
                        new_credit_plan_pay_detail.tota_fee = credit_plan_pay_detail.crediTotaCuota.toDouble()
                        new_credit_plan_pay_detail.amount_of_charges = credit_plan_pay_detail.crediMontoCargos.toDouble()
                        new_credit_plan_pay_detail.total_fee = credit_plan_pay_detail.crediTotalCuota.toDouble()
                        new_credit_plan_pay_detail.principal_balance = credit_plan_pay_detail.crediSaldoCredi.toDouble()
                        return@map new_credit_plan_pay_detail
                    }


                    GlobalScope.launch {
                        //CREDITOS PLAN DE PAGOS
                        LoyolaApplication.getInstance()?.repository?.deleteAllCreditsPlanPay()
                        LoyolaApplication.getInstance()?.repository?.insertAllCreditsPlanPay(credits_plan_pay)
                        //CREDITOS PLAN DE PAGOS DETALLE
                        LoyolaApplication.getInstance()?.repository?.deleteAllCreditsPlanPayDetail()
                        LoyolaApplication.getInstance()?.repository?.insertAllCreditsPlanPayDetail(credits_plan_pay_detail)

                        var dbCredits = LoyolaApplication.getInstance()?.repository?.getAllCreditsPlanPay()
                        var dbCreditsDetail = LoyolaApplication.getInstance()?.repository?.getAllCreditsPlanPayDetail()

                        context.runOnUiThread {
                            onSuccess(dbCredits)
                            onSuccessDetail(dbCreditsDetail)

                        }
                    }
                }

            },
            { error ->
                GlobalScope.launch {
                    var dbCredits = LoyolaApplication.getInstance()?.repository?.getAllCreditsPlanPay()
                    var dbCreditsDetail = LoyolaApplication.getInstance()?.repository?.getAllCreditsPlanPayDetail()

                    context.runOnUiThread {
                        onError(error, dbCredits)
                        onErrorDetail(error, dbCreditsDetail)
                    }
                }
            }
        )
        LoyolaService.getInstance(context).addToRequestQueue(request)
    }

    fun getCreditsExtract(
        docuCage:String = "",
        credNumber:String = "",
        onSuccess:(List<CreditExtract>?)->Unit,
        onSuccessDetail: (List<CreditExtractDetail>?) -> Unit,
        onError:(err:VolleyError, List<CreditExtract>?)->Unit,
        onErrorDetail: (err: VolleyError, List<CreditExtractDetail>?) -> Unit
    ) {
        val url = "${getCreditExtractURL()}?docu_cage=$docuCage&cred_number=$credNumber"
        var request = StringRequest(
            Request.Method.GET,
            url,
            { response ->

                var response = Gson().fromJson(
                    response.toString(),
                    CreditExtractRequestModel::class.java
                )

                if(response.error) {

                    Toast.makeText(context, response.errorMessage[1].toString(), Toast.LENGTH_SHORT)

                } else {

                    val credits_extract = response.result.map { credit_extract ->

                        var new_credit_extract = CreditExtract()
                        new_credit_extract.credit_id = credit_extract.id_credit.toInt()
                        new_credit_extract.credit_extract_id = credit_extract.id.toInt()
                        new_credit_extract.disbursed_amount = credit_extract.credMontoDesem.toDouble()
                        new_credit_extract.term = credit_extract.credPlazo.toString()
                        new_credit_extract.state = credit_extract.estado.toString()
                        return@map new_credit_extract
                    }

                    val credits_extract_detail = response.detail.map{ credit_extract_detail ->

                        var new_credit_extract_detail = CreditExtractDetail()
                        new_credit_extract_detail.credit_extract_detail_id = credit_extract_detail.id.toInt()
                        new_credit_extract_detail.credit_extract_id = credit_extract_detail.id_credit_extract.toInt()
                        new_credit_extract_detail.payment_date = credit_extract_detail.credFecPago.toString()
                        new_credit_extract_detail.number_transaction = credit_extract_detail.credNroTrans.toInt()
                        new_credit_extract_detail.principal_amount = credit_extract_detail.credMontoCapi.toDouble()
                        new_credit_extract_detail.interest_amount = credit_extract_detail.crediMontoInte.toDouble()
                        new_credit_extract_detail.penalty_amount = credit_extract_detail.crediMontoPenal.toDouble()
                        new_credit_extract_detail.amount_of_charges = credit_extract_detail.crediMontoCargos.toDouble()
                        new_credit_extract_detail.total_to_pay = credit_extract_detail.crediTotalPago.toDouble()
                        new_credit_extract_detail.principal_balance = credit_extract_detail.crediSaldoCapi.toDouble()

                        return@map new_credit_extract_detail
                    }


                    GlobalScope.launch {
                        //CREDITOS PLAN DE PAGOS
                        LoyolaApplication.getInstance()?.repository?.deleteAllCreditsExtract()
                        LoyolaApplication.getInstance()?.repository?.insertAllCreditsExtract(credits_extract)
                        //CREDITOS PLAN DE PAGOS DETALLE
                        LoyolaApplication.getInstance()?.repository?.deleteAllCreditsExtractDetail()
                        LoyolaApplication.getInstance()?.repository?.insertAllCreditsExtractDetail(credits_extract_detail)

                        var dbCredits = LoyolaApplication.getInstance()?.repository?.getAllCreditsExtract()
                        var dbCreditsDetail = LoyolaApplication.getInstance()?.repository?.getAllCreditsExtractDetail()

                        context.runOnUiThread {
                            onSuccess(dbCredits)
                            onSuccessDetail(dbCreditsDetail)

                        }
                    }
                }

            },
            { error ->
                GlobalScope.launch {
                    var dbCredits = LoyolaApplication.getInstance()?.repository?.getAllCreditsExtract()
                    var dbCreditsDetail = LoyolaApplication.getInstance()?.repository?.getAllCreditsExtractDetail()

                    context.runOnUiThread {
                        onError(error, dbCredits)
                        onErrorDetail(error, dbCreditsDetail)
                    }
                }
            }
        )
        LoyolaService.getInstance(context).addToRequestQueue(request)
    }

    fun getCreditPlanPayPdf(docuCage: String ="",credNumber: String = ""):Long {
        var dowloadid:Long = 0
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                CreditRest.STORAGE_PERMISSION_CODE
            )
        } else {
            val new = object: BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (id == dowloadid){
                        Toast.makeText(context, "Descarga completada", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            val uri ="${context.resources.getString(R.string.host_service)}${context.getString(R.string.home_service)}$GET_CREDITS_PLAN_PAY_PDF?docu_cage=${docuCage}&cred_number=${credNumber}"
            println(uri);
            val request = DownloadManager.Request(Uri.parse(uri))
                .setTitle("creditos-plan-pagos-$credNumber.pdf")
                .setDescription("Descargando....")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "creditos-plan-pagos-$credNumber.pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            val dowloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dowloadid = dowloadManager.enqueue(request)

            context.registerReceiver(new, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
        return  dowloadid
    }

    fun getCreditExtractPdf(docuCage: String ="",credNumber: String = ""):Long {
        var dowloadid:Long = 0
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                CreditRest.STORAGE_PERMISSION_CODE
            )
        } else {
            val new = object: BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (id == dowloadid){
                        Toast.makeText(context, "Descarga completada", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            val uri ="${context.resources.getString(R.string.host_service)}${context.getString(R.string.home_service)}$GET_CREDITS_EXTRACTS_PDF?docu_cage=${docuCage}&cred_number=${credNumber}"
            println(uri);
            val request = DownloadManager.Request(Uri.parse(uri))
                .setTitle("creditos-extractos-$credNumber.pdf")
                .setDescription("Descargando....")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "creditos-extractos-$credNumber.pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            val dowloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dowloadid = dowloadManager.enqueue(request)

            context.registerReceiver(new, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
        return  dowloadid
    }
}
