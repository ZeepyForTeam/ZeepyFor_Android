package com.example.zeepyforandroid.review.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentHousePictureBinding
import com.example.zeepyforandroid.review.data.entity.PictureModel
import com.example.zeepyforandroid.review.view.adapter.HousePictureAdapter
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.FileConverter.asBitmap
import com.example.zeepyforandroid.util.ItemDecoration
import com.example.zeepyforandroid.util.ReviewNotice
import java.io.File

class HousePictureFragment : BaseFragment<FragmentHousePictureBinding>() {
    private lateinit var pictureUri: Uri
    private val pictures = mutableListOf<PictureModel>()
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = { requireParentFragment().requireParentFragment() })


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
                val bitmap = uri.asBitmap(requireContext().contentResolver)
                pictures.add(PictureModel(bitmap))
                viewModel.changeHousePictures(pictures)
                Log.e("pictures", "${viewModel.bitmapImages.value}")
            }
        }

    private val cameraActivityLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            if (isSaved) {
                pictures.add(PictureModel(pictureUri.asBitmap(requireContext().contentResolver)))
                viewModel.changeHousePictures(pictures)
                Log.e("pictures", "${viewModel.bitmapImages.value}")

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
            (binding.rvHousePictures.adapter as HousePictureAdapter).apply {
                submitList(viewModel.bitmapImages.value?.toList())
            }
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
        val parent = (parentFragment as NavHostFragment).parentFragment
        binding.btnRegister.setOnClickListener { parent?.findNavController()?.popBackStack() }
        binding.tvSkip.setOnClickListener { parent?.findNavController()?.popBackStack() }
    }

    companion object {
        private const val PERMISSION_CAMERA = android.Manifest.permission.CAMERA
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE =
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSION_READ_EXTERNAL_STORAGE =
            android.Manifest.permission.READ_EXTERNAL_STORAGE

        private val PERMISSION_REQUESTED = arrayOf(
            PERMISSION_CAMERA,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_READ_EXTERNAL_STORAGE
        )
    }
}