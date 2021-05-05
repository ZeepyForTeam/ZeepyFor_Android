package com.example.zeepyforandroid.review.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import com.example.zeepyforandroid.BuildConfig
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentHousePictureBinding
import com.example.zeepyforandroid.review.data.dto.HousePictureModel
import com.example.zeepyforandroid.review.view.adapter.HousePictureAdapter
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ItemDecoration
import com.example.zeepyforandroid.util.ReviewNotice
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class HousePictureFragment : BaseFragment<FragmentHousePictureBinding>() {
    private val pictures = mutableListOf<HousePictureModel>()
    private lateinit var pictureUri: Uri
    private val viewModel by activityViewModels<WriteReviewViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHousePictureBinding {
        return FragmentHousePictureBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.changeCurrentFragment(ReviewNotice.LOAD_HOUSE_PICTURE)
        setPictureList()
        setRegisterButton()
        openGallery()
        openCamera()
    }

    private fun setPictureList() {
        binding.rvHousePictures.apply {
            adapter = HousePictureAdapter()
            addItemDecoration(ItemDecoration(0, 18))
        }
    }

    private fun setRegisterButton() {
        binding.btnRegister.setText("동록하기")
    }

    private fun openCamera() {
        binding.ivCamera.setOnClickListener {
            requestCameraPermission.launch(PERMISSION_REQUESTED)
        }
    }

    private fun openGallery() {
        binding.ivGallery.setOnClickListener {
            requestGalleryPermission.launch(PERMISSION_REQUESTED)
        }
    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            takePicture()
        }

    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivity(intent)
        }

    private fun takePicture() {
        val photoFile = File.createTempFile("IMG_", ".jpg", requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        pictureUri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", photoFile)
        cameraActivityLauncher.launch(pictureUri)
    }

    //Todo: SubmitList 이슈 해결하고 notifyDataSetChanged 삭제하기
    val cameraActivityLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            if(isSaved) {
                pictures.add(HousePictureModel(pictureUri))
                viewModel.changeHousePictures(pictures)
                (binding.rvHousePictures.adapter as HousePictureAdapter).apply {
                    submitList(pictures)
                    notifyDataSetChanged()
                }
            }
        }

    companion object {
        private const val PERMISSION_CAMERA = android.Manifest.permission.CAMERA
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSION_READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE

        private val PERMISSION_REQUESTED = arrayOf(
            PERMISSION_CAMERA,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_READ_EXTERNAL_STORAGE
        )
    }
}