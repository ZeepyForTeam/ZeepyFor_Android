package com.zeepy.zeepyforandroid.community.writeposting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.controller.WritePostingController
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import com.zeepy.zeepyforandroid.imagecontrol.ImageController
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CommunityLoadPictureViewModel @Inject constructor(
    private val writePostingController: WritePostingController
) : BaseViewModel() {
    private val _files = MutableLiveData<MutableList<File>?>(mutableListOf())
    val files: LiveData<MutableList<File>?>
        get() = _files

    private val _bitmapImages = MutableLiveData<List<PictureModel>>()
    val bitmapImages: LiveData<List<PictureModel>>
        get() = _bitmapImages

    private val _uploadMultiparts = MutableLiveData<ArrayList<MultipartBody.Part?>>(arrayListOf())
    val uploadMultiparts: LiveData<ArrayList<MultipartBody.Part?>>
        get() = _uploadMultiparts

    private val _presignedUrlList = MutableLiveData<List<String>?>(listOf())
    val presignedUrlList: LiveData<List<String>?>
        get() = _presignedUrlList

    private val _requestWritePosting = MutableLiveData<RequestWritePosting?>()
    val requestWritePosting: LiveData<RequestWritePosting?>
        get() = _requestWritePosting

    private val _successUpload = MutableLiveData<Boolean>()
    val successUpload: LiveData<Boolean>
        get() = _successUpload

    fun addFile(file: File?) {
        val fileList = files.value
        if (file != null) {
            fileList?.add(file)
        }
        _files.value = fileList
    }

    fun fetchPresignedUrl(urlList: List<String>) {
        _presignedUrlList.value = urlList
    }

    fun addUploadMultipartBody(body: MultipartBody.Part?) {
        val parts = uploadMultiparts.value
        if (body != null) {
            parts?.add(body)
        }
        _uploadMultiparts.value = parts!!
    }

    fun changeRequestPosting(request: RequestWritePosting?) {
        _requestWritePosting.value = request
    }

    fun changeUploadPictures(pictures: List<PictureModel>) {
        _bitmapImages.value = pictures
    }

    fun updateUploadContents(urlList: List<String>?) {
        var contentList = requestWritePosting.value
        contentList?.imageUrls = urlList
        _requestWritePosting.value = contentList
    }


    fun uploadImages() {
        val requestList: ArrayList<MultipartBody.Part?> = arrayListOf()

        files.value?.forEach {
            val requestFile = it.asRequestBody(it.extension.toMediaTypeOrNull())

            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", it.name, requestFile)
                .build()

            val multipartBody = MultipartBody.Part.createFormData("file", it.name, requestBody)
            requestList?.add(multipartBody)
        }

        addDisposable(
            writePostingController.uploadImages(
                requestList!!
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fetchPresignedUrl(it.imageUrlResDtos.map { it.imageUrlResDto })
                    uploadPostingToZeepyServer()
                }, {
                    it.printStackTrace()
                })
        )
    }


    fun uploadPostingToZeepyServer() {
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