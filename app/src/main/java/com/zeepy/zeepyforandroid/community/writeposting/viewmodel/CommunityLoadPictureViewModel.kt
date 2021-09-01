package com.zeepy.zeepyforandroid.community.writeposting.viewmodel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.controller.WritePostingController
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import com.zeepy.zeepyforandroid.imagecontrol.ImageController
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.network.AmazonS3Retrofit.createAmazonS3ApiService
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.util.FileConverter.asMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Callback
import javax.inject.Inject

@HiltViewModel
class CommunityLoadPictureViewModel @Inject constructor(
    private val imageController: ImageController,
    private val writePostingController: WritePostingController,
    private val zeepyLocalRepository: ZeepyLocalRepository,
) : BaseViewModel() {
    private val disposable = CompositeDisposable()

    private val _uploadBitmapImages = MutableLiveData<List<PictureModel>>()
    val uploadBitmapImages: LiveData<List<PictureModel>>
        get() = _uploadBitmapImages

    private val _requestBodyImages = MutableLiveData<MutableList<MultipartBody.Part?>>(mutableListOf())
    val requestBodyImages: LiveData<MutableList<MultipartBody.Part?>>
        get() = _requestBodyImages

    private val _uploadUriImages = MutableLiveData<MutableList<Uri>?>(mutableListOf())
    val uploadUriImages: LiveData<MutableList<Uri>?>
        get() = _uploadUriImages

    private val _requestBodyUrlPair = MutableLiveData<List<Pair<MultipartBody.Part?, String>>>(listOf())
    val requestBodyUrlPair: LiveData<List<Pair<MultipartBody.Part?, String>>>
        get() = _requestBodyUrlPair

    private val _multipartBodyUrlPair =
        MutableLiveData<List<Pair<MultipartBody.Part?, String>>>(listOf())
    val multipartBodyUrlPair: LiveData<List<Pair<MultipartBody.Part?, String>>>
        get() = _multipartBodyUrlPair

    private val _presignedUrlList = MutableLiveData<MutableList<String>?>(mutableListOf())
    val presignedUrlList: LiveData<MutableList<String>?>
        get() = _presignedUrlList

    private val _requestWritePosting = MutableLiveData<RequestWritePosting?>()
    val requestWritePosting: LiveData<RequestWritePosting?>
        get() = _requestWritePosting

    private val _successUpload = MutableLiveData<Boolean>()
    val successUpload: LiveData<Boolean>
        get() = _successUpload

    fun addRequestBodyList(requestBody: MultipartBody.Part?) {
        val requestImages = _requestBodyImages.value
        requestImages?.add(requestBody)
    }

    fun addUploadUriImages(uri: Uri) {
        val uriList = uploadUriImages.value
        uriList?.add(uri)
        _uploadUriImages.value = uriList
    }

    fun changeRequestPosting(request: RequestWritePosting?) {
        _requestWritePosting.value = request
    }

    fun changeUploadPictures(pictures: List<PictureModel>) {
        _uploadBitmapImages.value = pictures
    }

    fun getPresignedUrl(contentResolver: ContentResolver) {
        val imageCount = uploadBitmapImages.value?.size?.toLong() ?: 0
        _successUpload.value = false

        addDisposable(
            imageController.getPresignedUrl()
                .repeat(imageCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _presignedUrlList.value?.add(it.presignedUrl)
                }, {
                    it.printStackTrace()
                }, {
                    val multipartBodies = requestBodyImages.value?.map { it!! }
                        ?.zip(presignedUrlList.value!!.toList())

                    _requestBodyUrlPair.value = multipartBodies!!

                    val multipartBodyMap = requestBodyUrlPair.value?.map {
//            val requestbody = it.first.toRequestBody("image/jpeg".toMediaTypeOrNull())
//            val multipartBody = MultipartBody.Part.createFormData("imgs","imgs/zeepy",requestbody)
                        it.first to it.second
                    }

                    Log.e("pair paidsaf3112121r", "${multipartBodyUrlPair.value}")
                    _multipartBodyUrlPair.value = multipartBodyMap!!
                    Log.e("lastlast", "${requestBodyUrlPair.value}")
                    uploadToServerAmazonS3(contentResolver)
                })
        )
    }

    fun uploadToServerAmazonS3(contentResolver: ContentResolver) {
        val completable : List<Single<ResponseBody>>? = multipartBodyUrlPair.value?.map {
            Log.e("second", "${it.first} / ${it.second}")

            createAmazonS3ApiService().uploadImageToPresignedUrl(
                    it.second,
                    it.first!!
                )

        }


        completable?.forEach {

            val disposable = CompositeDisposable()
            disposable.add(
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                    },{
                        it.printStackTrace()

                    })
            )

        }


        Log.e("observable List", "${completable}")


//        createAmazonS3ApiService().uploadImageToPresignedUrl(
//            it.second,
//            it.first
//        ).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//
//            }, {
//                it.printStackTrace()
//            })

//
//        disposable.add(
//            Observable.fromCallable {
//                multipartBodyMap
//            }.repeat(1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    Log.e("url", "${it!!.values.first().substringBefore("?").replace(S3_URL, "")}")
//                    uploadEachImageToAmazonS3(it.keys.first()!!, it!!.values.first().replace(S3_URL, ""))
//                },{
//                    it.printStackTrace()
//                }, {
//                    uploadPostingToZeepyServer()
//                    disposable.clear()
//                })
//        )
    }

//    fun uploadEachImageToAmazonS3(multipartBody: MultipartBody.Part, url: String) {
//        val amazonS3ApiService = createAmazonS3ApiService()
//
//        amazonS3ApiService.uploadImageToPresignedUrl(url, multipartBody).enqueue(object : retrofit2.Callback<Any>{
//            override fun onResponse(call: Call<Any>, response: Response<Any>) {
//                Log.e("success amazons3 1", "success")
//                if (response.isSuccessful) {
//                    Log.e("success amazons3 2", "success")
//                }
//            }
//
//            override fun onFailure(call: Call<Any>, t: Throwable) {
//                Log.e("success amazons3 fff", "fail")
//            }
//        })

//            amazonS3ApiService.uploadImageToPresignedUrl(url, multipartBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    Log.e("success amazons3", "success")
//                }, {
//                    it.printStackTrace()
//                    Log.e("fail amazons3", "fail")
//                })
//
//
//    }

    fun uploadPostingToZeepyServer() {
        val requestImageUrls = requestBodyUrlPair.value?.map { it.second.substringBefore("?") }
        Log.e("success posting", "${requestImageUrls}")
        val requestData = requestWritePosting.value
        requestData?.imageUrls = requestImageUrls

        _requestWritePosting.value = requestData
        Log.e("success posting", "${requestWritePosting.value}")

        addDisposable(
            writePostingController.uploadPosting(
                requestWritePosting.value!!
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _successUpload.postValue(true)
                }, {
                    it.printStackTrace()
                    Log.e("fail posting", "fail")
                })
        )

    }
}