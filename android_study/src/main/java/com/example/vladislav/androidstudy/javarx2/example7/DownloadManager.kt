//package com.example.vladislav.androidstudy.javarx2.example7
//
//import com.example.vladislav.androidstudy.javarx2.example7.DownloadService
//import io.reactivex.Observer
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.Disposable
//import io.reactivex.schedulers.Schedulers
//import okhttp3.OkHttpClient
//import okhttp3.ResponseBody
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory
//
//class DownloadManager(private val listener: ProgressListener) {
//
//    private var retrofit: Retrofit? = null
//    private var downloadService: DownloadService? = null
//
//    init {
//        val client = OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val originalResponse = chain.proceed(chain.request())
//                originalResponse.newBuilder()
//                    .body(ProgressResponseBody(originalResponse.body!!, listener))
//                    .build()
//            }
//            .build()
//
//        retrofit = Retrofit.Builder()
//            .baseUrl("https://example.com/")
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//
//        downloadService = retrofit!!.create(DownloadService::class.java)
//    }
//
//    fun downloadFile(fileUrl: String) {
//        downloadService!!.downloadFile(fileUrl)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<ResponseBody> {
//                override fun onComplete() {
//                    listener.onProgressUpdate(responseBody)
//                }
//
//                override fun onSubscribe(disposable: Disposable) {}
//
//                override fun onNext(responseBody: ResponseBody) {
//                    listener.onProgressUpdate(responseBody)
//                }
//
//                override fun onError(e: Throwable) {
//                    listener.onDownloadFailed(e)
//                }
//            })
//    }
//}
