package com.zeepy.zeepyforandroid.community.writeposting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import com.zeepy.zeepyforandroid.imagecontrol.ImageController
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.network.AmazonS3Retrofit.createAmazonS3Retrofit
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CommunityLoadPictureViewModel @Inject constructor(
    private val imageController: ImageController,
    private val zeepyLocalRepository: ZeepyLocalRepository,
) : BaseViewModel() {
    private val _uploadBitmapImages = MutableLiveData<List<PictureModel>>()
    val uploadBitmapImages: LiveData<List<PictureModel>>
        get() = _uploadBitmapImages

    private val _presignedUrlList = MutableLiveData<MutableList<String?>?>(mutableListOf())
    val presignedUrlList: LiveData<MutableList<String?>?>
        get() = _presignedUrlList

    private val _requestWritePosting = MutableLiveData<RequestWritePosting?>()
    val requestWritePosting: LiveData<RequestWritePosting?>
        get() = _requestWritePosting

    fun changeRequestPosting(request: RequestWritePosting?) {
        _requestWritePosting.value = request
    }

    fun changeUploadPictures(pictures: List<PictureModel>) {
        _uploadBitmapImages.value = pictures
    }

    fun addPresignedUrl(url: String) {
        val urlList = presignedUrlList.value
        urlList?.add(url)
        _presignedUrlList.value = urlList
    }

    fun getPresignedUrl() {
        val repeatCount = uploadBitmapImages.value?.size ?: 0
        addDisposable(
            imageController.getPresignedUrl()
                .repeat(repeatCount.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    addPresignedUrl(it.presignedUrl)
                }, {
                   it.printStackTrace()
                }, {

                })
        )
    }

    fun uploadToServerAmazonS3() {
        presignedUrlList.value?.forEach { presignedUrl ->
            val amazonS3ApiService = presignedUrl?.let { createAmazonS3Retrofit(it) }
            //Todo: s3에 올리는거 마무리하기
//            amazonS3ApiService.uploadImageToPresignedUrl()
        }

    }

//    fun uploadPosting() {
//        addDisposable(
//            writePostingController.uploadPosting(
//                RequestWritePosting(
//                    selectedAddress.value!!,
//                    PostingType.JOINTPURCHASE.name,
//                    content.value,
//                    uploadImages.value!!,
//                    instructions.value,
//                    productName.value,
//                    productPrice.value,
//                    purchaseSite.value,
//                    sharingMethod.value,
//                    targetCount.value?.toInt(),
//                    title.value
//                )
//            ).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    Log.e("success posting", "success")
//                }, {
//                    it.printStackTrace()
//                    Log.e("fail posting", "fail")
//                })
//        )
//   }
}