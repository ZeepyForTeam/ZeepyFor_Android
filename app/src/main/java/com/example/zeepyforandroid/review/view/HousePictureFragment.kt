package com.example.zeepyforandroid.review.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentHousePictureBinding
import com.example.zeepyforandroid.review.data.dto.PictureModel
import com.example.zeepyforandroid.review.view.adapter.HousePictureAdapter
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ItemDecoration
import com.example.zeepyforandroid.util.ReviewNotice
import java.io.File
import java.util.*

class HousePictureFragment : BaseFragment<FragmentHousePictureBinding>() {
    private lateinit var pictureUri: Uri
    private val pictures = mutableListOf<PictureModel>()
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})


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
        changeVisibility()
        setPictureList()
        setRegisterButton()
        openGallery()
        openCamera()
        stagePictures()
        completeUpload()

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
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            takePicture()
        }

    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            getHousePicture.launch(intent)
        }

    private fun takePicture() {
        val photoFile = File.createTempFile("IMG_", ".jpg", requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        pictureUri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", photoFile)
        cameraActivityLauncher.launch(pictureUri)
    }

    private val cameraActivityLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            if(isSaved) {
                pictures.add(PictureModel(pictureUri))
                viewModel.changeHousePictures(pictures)
            }
        }

    private val getHousePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if(result.data != null) {
            pictures.add(PictureModel(result.data?.data))
            viewModel.changeHousePictures(pictures)
        }
    }

    private fun stagePictures() {
        viewModel.pictures.observe(viewLifecycleOwner){
            (binding.rvHousePictures.adapter as HousePictureAdapter).apply {
                submitList(viewModel.pictures.value?.toList())
            }
            changeVisibility()
        }
    }

    private fun changeVisibility() {
        if(viewModel.pictures.value.isNullOrEmpty()) {
            binding.tvUploadImages.visibility = View.GONE
            binding.rvHousePictures.visibility = View.GONE
        } else {
            binding.tvUploadImages.visibility = View.VISIBLE
            binding.rvHousePictures.visibility = View.VISIBLE
        }
    }

    private fun completeUpload() {
        val parent = (parentFragment as NavHostFragment).parentFragment
        binding.btnRegister.setOnClickListener { parent?.findNavController()?.popBackStack() }
        binding.tvSkip.setOnClickListener { parent?.findNavController()?.popBackStack() }
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