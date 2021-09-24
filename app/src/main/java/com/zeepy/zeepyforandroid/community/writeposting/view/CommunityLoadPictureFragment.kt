package com.zeepy.zeepyforandroid.community.writeposting.view

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.writeposting.viewmodel.CommunityLoadPictureViewModel
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.databinding.FragmentCommunityLoadPictureBinding
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.review.view.HousePictureFragment.Companion.PERMISSION_REQUESTED
import com.zeepy.zeepyforandroid.review.view.adapter.UploadPictureAdapter
import com.zeepy.zeepyforandroid.util.FileConverter.asBitmap
import com.zeepy.zeepyforandroid.util.FileConverter.asMultipart
import com.zeepy.zeepyforandroid.util.FileConverter.convertMultipart
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.zeepy.zeepyforandroid.util.UriHelper.getFileFromUri
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File

@AndroidEntryPoint
class CommunityLoadPictureFragment: BaseFragment<FragmentCommunityLoadPictureBinding>() {
    private val viewModel: CommunityLoadPictureViewModel by viewModels()
    private val args: CommunityLoadPictureFragmentArgs by navArgs()
    private lateinit var pictureUri: Uri
    private val pictures = mutableListOf<PictureModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityLoadPictureBinding {
        return FragmentCommunityLoadPictureBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.changeRequestPosting(args.requestWritePosting)

        setPictureList()
        setRegisterButton()
        openGallery()
        openCamera()
        stagePictures()
        completeUpload()
        successUpload()
    }

    private fun setPictureList() {
        binding.rvHousePictures.apply {
            adapter = UploadPictureAdapter()
            addItemDecoration(ItemDecoration(0, 18))
        }
    }

    private fun setRegisterButton() {
        binding.btnRegister.apply {
            setText("건너뛰기")
            setCommunityUsableButton()
        }
    }

    private fun openGallery() {
        binding.cslGallery.clipToOutline = false
        binding.cslGallery.setOnClickListener {
            requestGalleryPermission.launch(PERMISSION_REQUESTED)
        }
    }

    private fun openCamera() {
        binding.cslCamera.clipToOutline = true
        binding.cslCamera.setOnClickListener {
            requestCameraPermission.launch(PERMISSION_REQUESTED)
        }
    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.values.filter { it == false }.count() != 0) {
                Toast.makeText(requireContext(), "권한을 모두 허용해주세요", Toast.LENGTH_SHORT).show()
            } else {
                takePicture()
            }
        }

    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.values.filter { allowed -> allowed == false }.count() != 0) {
                Toast.makeText(requireContext(), "권한을 모두 허용해주세요", Toast.LENGTH_SHORT).show()
            } else {
                galleryActivityLauncher.launch("image/*")
            }
        }

    private val galleryActivityLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { imageList ->
            imageList.forEach { uri ->
                val bitmap = PictureModel(uri.asBitmap(requireContext().contentResolver))
                pictures.add(bitmap)
                val bitmapRequestBody = BitmapRequestBody(bitmap.image!!)
                val multipartBody = MultipartBody.Part.createFormData("img", "zeepy", bitmapRequestBody)

                viewModel.addFile(getFileFromUri(requireContext(), uri))
//                viewModel.addUploadMultipartBody(multipartBody)
                viewModel.addUploadMultipartBody(multipartBody)

                viewModel.changeUploadPictures(pictures)
            }
        }

    private val cameraActivityLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            if (isSaved) {
                viewModel.addFile(getFileFromUri(requireContext(), pictureUri))
                val bitmap = pictureUri.asBitmap(requireContext().contentResolver)
                pictures.add(PictureModel(bitmap))
                val bitmapRequestBody = bitmap?.let { BitmapRequestBody(it) }
                if (bitmapRequestBody != null) {
                    viewModel.addUploadMultipartBody(MultipartBody.Part.createFormData("file", "zeepy", bitmapRequestBody))
                }

//                viewModel.addUploadMultipartBody(pictureUri.asMultipart("imgs", requireContext().contentResolver))
//                val multipartBody = MultipartBody.Part.createFormData("file", "zeepy", BitmapRequestBody(bitmap!!))
                viewModel.addUploadMultipartBody(pictureUri.asMultipart(requireContext().contentResolver))
                viewModel.changeUploadPictures(pictures)
            }
        }

    private fun takePicture() {
        val photoFile = File.createTempFile(
            "IMG_",
            ".jpg",
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        pictureUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            photoFile
        )
        cameraActivityLauncher.launch(pictureUri)
    }

    private fun stagePictures() {
        viewModel.bitmapImages.observe(viewLifecycleOwner) {
            (binding.rvHousePictures.adapter as UploadPictureAdapter).apply {
                submitList(viewModel.bitmapImages.value?.toList())
            }
            binding.btnRegister.setText("등록하기")
            changeVisibility()
        }
    }

    private fun changeVisibility() {
        if (viewModel.bitmapImages.value.isNullOrEmpty()) {
            binding.tvUploadImages.visibility = View.GONE
            binding.rvHousePictures.visibility = View.GONE
        } else {
            binding.tvUploadImages.visibility = View.VISIBLE
            binding.rvHousePictures.visibility = View.VISIBLE
        }
    }

    private fun completeUpload() {
        binding.btnRegister.setOnClickListener { showPostingRegisterDialog() }
    }

    private fun showPostingRegisterDialog() {
        val registerReviewDialog = ZeepyDialogBuilder("글을 등록하시겠습니까?", "community")
            .setContent("*공동구매 글의 경우 참여자가 1명 이상일\n경우 글을 삭제하거나 수정하실 수 없습니다.\n\n*허위/중복/성의없는 정보 또는 비방글을\n작성할 경우, 서비스 이용이 제한될 수 있습니다.")
            .setLeftButton(R.drawable.box_grayf9_8dp,"취소")
            .setRightButton(R.drawable.box_green33_8dp,"확인")
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    viewModel.uploadImages()
                    dialog.dismiss()
//                    findNavController().popBackStack()
                }
            }).build()
        registerReviewDialog.show(childFragmentManager, this.tag)
    }

    private fun successUpload() {
        viewModel.successUpload.observe(viewLifecycleOwner) { isSuccessful ->
            if (isSuccessful) {
                viewModel.uploadPostingToZeepyServer()
                findNavController().popBackStack()
            }
        }
    }

    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, sink.outputStream())
        }
    }


    companion object{
        const val PICK_ANY_FILE = 100

        fun Context.getFileExtension(uri: Uri): String? = when (uri.scheme) {
            ContentResolver.SCHEME_FILE -> File(uri.path!!).extension
            ContentResolver.SCHEME_CONTENT -> getCursorContent(uri)
            else -> null
        }

        private fun Context.getCursorContent(uri: Uri): String? = try {
            contentResolver.query(uri, null, null, null, null)?.let { cursor ->
                cursor.run {
                    val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
                    if (moveToFirst()) mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
                    else null
                }.also { cursor.close() }
            }
        } catch (e: Exception) {
            null
        }
    }
}