package com.loyaltyglobal.ui.main.view.fragment

import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.FragmentWebViewBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.view.activity.MainActivity
import com.loyaltyglobal.util.Constants
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show

/**
 * Created by Abhin.
 */

class WebViewFragment : BaseFragment() {

    private lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.run {
            getString(Constants.KEY_WEB_URL)?.let {
                webLoad(it)
            }
            getString(Constants.KEY_TITLE)?.let {
                binding.tbWebView.txtTitle.text = it
            }
        }

        binding.tbWebView.imgBack.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
                binding.webView.show()
            } else {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun webLoad(Url: String?) {
        try {
            val webSettings = binding.webView.settings
            binding.webView.clearCache(true)
            CookieManager.getInstance().flush()
            webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webSettings.domStorageEnabled = true
            webSettings.useWideViewPort = true
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                webSettings.safeBrowsingEnabled = true
            }
            binding.webView.webChromeClient = TripShiftChromeClient()
            binding.webView.webViewClient = TripShiftClient()
            Url?.let { binding.webView.loadUrl(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private inner class TripShiftChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            binding.progressbar.progress = newProgress
            if (newProgress == 100) {
                binding.progressbar.hide()
            }
        }
    }


    private inner class TripShiftClient : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.webView.clearCache(true)
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?,
        ) {
            super.onReceivedSslError(view, handler, error)
            handler?.cancel()

            val builder: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
            var dialog: AlertDialog? = null
            builder?.setTitle(resources.getString(R.string.ssl_certificate_error))
            builder?.setMessage(resources.getString(R.string.certificate_authority_error))
            builder?.setPositiveButton(resources.getText(R.string.ok)) { _, _ ->
                dialog?.dismiss()
                activity?.supportFragmentManager?.popBackStack()
            }
            dialog = builder?.create()
            dialog?.show()
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            binding.progressbar.show()
            view.loadUrl(url)
            return true
        }
    }

    override fun onDestroyView() {
        if (activity is MainActivity) (activity as MainActivity).showHideBottomNavigationBar(true)
        super.onDestroyView()
    }
}