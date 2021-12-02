package app.wiserkronox.loyolasocios.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.repository.CertificateRest
import app.wiserkronox.loyolasocios.service.repository.CreditRest
import app.wiserkronox.loyolasocios.view.adapter.CertificateAdapter
import app.wiserkronox.loyolasocios.view.adapter.CreditAdapter
import app.wiserkronox.loyolasocios.view.ui.HomeActivity

class CreditFragment : Fragment() {

    private lateinit var progressBar:ProgressBar
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_credit, container, false)

        progressBar = view.findViewById(R.id.progressbar_credits)
        recyclerView = view.findViewById(R.id.recyclerview_credits)
        recyclerView.adapter = CreditAdapter(requireContext(), emptyList())

        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.VISIBLE

        var user = LoyolaApplication.getInstance()?.user
        CreditRest(requireActivity()).getCredits(
            user!!.id_member,
            {
                credits->
                progressBar.visibility = View.INVISIBLE
                if(credits != null) {
                    recyclerView.adapter = CreditAdapter(requireContext(), credits)
                }
            },
            {
                error,credits ->
                Toast.makeText(activity, "Error de conexión con el servidor", Toast.LENGTH_SHORT)
                progressBar.visibility = View.INVISIBLE
                if(credits?.size == 0) {
                    (activity as HomeActivity).goHome()
                    recyclerView.adapter = CreditAdapter(requireContext(), credits)
                }
            }
        )
    }
}