package com.bignerdranch.android.fuck3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

private const val ARG_IMDB_URL = "imdb_url"

class MovieSite : Fragment() {
    private var imdbUrl: String? = null
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imdbUrl = it.getString(ARG_IMDB_URL)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_site, container, false)
        webView = rootView.findViewById(R.id.webView)

        imdbUrl?.let {
            webView.loadUrl(it)
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(imdbUrl: String): MovieSite {
            return MovieSite().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMDB_URL, imdbUrl)
                }
            }
        }
    }
}
