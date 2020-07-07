package com.webview.app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {


    private var url = ""
    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        url = intent.getStringExtra("url")


        initView()
    }

    private fun initView(){
        webView = WebView(this)
        fl_content.addView(webView)


        val webSettings = webView!!.settings
        webSettings.javaScriptEnabled = true
        //设置缓存模式
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.setAppCacheEnabled(false)


        //其他细节操作
        //设置可以访问文件
        webSettings.allowFileAccess = true
        //支持通过JS打开新窗口
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        //支持自动加载图片
        webSettings.loadsImagesAutomatically = true
        //设置编码格式
        webSettings.defaultTextEncodingName = "utf-8"
        webSettings.domStorageEnabled = true

        //水平不显示
        webView!!.isHorizontalScrollBarEnabled = false
        //垂直不显示
        webView!!.isVerticalScrollBarEnabled = false
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                if (url == null) {
                    return false
                }
                try {
                    if (url.startsWith("weixin://") || url.startsWith("alipays://") ||
                        url.startsWith("mailto://") || url.startsWith("tel://")|| url.startsWith("tbopen://")) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }//其他自定义的scheme
                } catch (e: Exception) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false
                }

                //处理http和https开头的url
                webView!!.loadUrl(url)
                Log.d("网页的title" ,view.title)
                return true
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                //页面开始加载
            }

            override fun onPageFinished(view: WebView, url: String) {
                //页面加载完毕
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                //加载出现失败
                super.onReceivedError(view, errorCode, description, failingUrl)
            }

        }

        if (url.startsWith( "http") || url.startsWith("file")) {
            webView!!.loadUrl(url)
        } else {
            webView!!.loadData(url, "text/html", null)
        }

        webView!!.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {

                return super.onConsoleMessage(consoleMessage)
            }
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
