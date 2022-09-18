package com.onp24.onlinenewsportal24.ui.details

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.onp24.onlinenewsportal24.MainActivity
import com.onp24.onlinenewsportal24.R
import com.onp24.onlinenewsportal24.databinding.FragmentDetailsBinding
import com.onp24.onlinenewsportal24.repository.NewsDetailsRepository
import com.onp24.onlinenewsportal24.repository.NewsHomeRepository
import com.onp24.onlinenewsportal24.ui.home.NewsHomeViewModel
import com.onp24.onlinenewsportal24.ui.home.NewsViewModelFactory
import com.onp24.onlinenewsportal24.util.*


class DetailsFragment : Fragment(), OperationCallBack {
    lateinit var binding: FragmentDetailsBinding
    lateinit var model: NewsDetailsViewModel

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        print("onResume() call")
        val repository = NewsDetailsRepository()
        val factory = NewsDetailsViewModelFactory(repository, args.news.detailsLink)
        var isNet: Boolean = activity?.isInternetAvailable()!!
        checkInternetAndAction(isNet)
        if (isNet) {
            model = ViewModelProvider(this, factory).get(NewsDetailsViewModel::class.java)
            model.listener = this
            model.newsDetails.observe(viewLifecycleOwner, Observer {
                binding.newsDetail = it
                binding.llRoot.show()
                binding.splash.rlSpl.hide()
                binding.internet.noInternetConnection.hide()
            })


        }
        var liow  = this
        binding.apply {
            binding.news = args.news
            toolbarRaw.activity = activity as MainActivity
            toolbarRaw.toolbarTitle.text = ""
            lifecycleOwner = liow
            //llRoot.hide()
        }

    }


    private fun checkInternetAndAction(available: Boolean) {
        if (!available) {
            binding.apply {
                llRoot.hide()
                splash.rlSpl.hide()
                internet.noInternetConnection.show()
                internet.tryAgain.setOnClickListener {
                    onResume()
                }
            }
        }
    }


    private fun print(s: String){
        Log.d("Danger",s)
    }

    override fun onError(s: String) {
       binding.llMainRoot.toastSnack(s)
    }

}