package app.wiserkronox.loyolasocios.view.ui.home

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.model.CertificateModel

class CertificateAdapter(
    private val context: Context,
    private val dataset: List<CertificateModel>
):RecyclerView.Adapter<CertificateAdapter.CertificateViewHolder>() {


    class CertificateViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {
        val labelGestion: TextView = view.findViewById(R.id.label_gestion)
        val textGestion: TextView = view.findViewById(R.id.textview_gestion)

        val textCerNumber: TextView = view.findViewById(R.id.certificate_number)
        val txtFecAport: TextView = view.findViewById(R.id.certificate_fec_aporte)
        val textCantidad: TextView = view.findViewById(R.id.certificate_cant)
        val textMonto: TextView = view.findViewById(R.id.certificate_monto)

        val imageViewStatus: ImageView = view.findViewById(R.id.certificate_status)
        val exportPdfButton: Button = view.findViewById(R.id.export_pdf_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.certificate_item, parent, false)

        return CertificateViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        val item = dataset[position]
        holder.textGestion.text = item.certGestion.toString()

        holder.textCerNumber.text = item.certNumero.toString()
        holder.txtFecAport.text = item.certFecApert
        holder.textCantidad.text = item.certCanti.toString()
        holder.textMonto.text = item.certMonto

        when(item.certEstado) {
            "VIGENTE"-> {
                holder.imageViewStatus.setImageResource(R.drawable.ic_round_enabled)
            }
            "CADUCO" -> {
                // card disabled styles
                holder.imageViewStatus.setImageResource(R.drawable.ic_round_disabled)

                holder.labelGestion.setTextColor(Color.parseColor("#FF80C4A5"))
                holder.textGestion.setTextColor(Color.parseColor("#FF80C4A5"))


                holder.textCerNumber.setTextColor(Color.parseColor("#FF77BB9C"))

                holder.txtFecAport.setTextColor(Color.parseColor("#FFA0A0A0"))

                holder.textCantidad.setTextColor(Color.parseColor("#FFA0A0A0"))

                holder.textMonto.setTextColor(Color.parseColor("#FFA0A0A0"))

                holder.exportPdfButton.setBackgroundColor(Color.parseColor("#FF77CB9E"))
            }
        }

        holder.exportPdfButton.setOnClickListener {
            println("Download")
            val STORAGE_PERMISSION_CODE = 101
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
            } else {
                var user = LoyolaApplication.getInstance()?.user
                this.getCertificaClyPdf(user!!.id_member.toString())

            }

        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    private fun getCertificaClyPdf(docuCage:String =""):Long {
        var dowloadid:Long = 0
        val new = object: BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == dowloadid){
                    Toast.makeText(context, "Descarga completada", Toast.LENGTH_SHORT).show()
                }
            }

        }

        val uri ="${context.resources.getString(R.string.host_service)}services/certifica-cly-pdf.php?docu-cage=${docuCage}"
        var request = DownloadManager.Request(Uri.parse(uri))
            .setTitle("certificados-loyola.pdf")
            .setDescription("Descargando....")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "certificados-loyola.pdf")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)

        val dowloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dowloadid = dowloadManager.enqueue(request)

        context.registerReceiver(new, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        return  dowloadid
    }

}