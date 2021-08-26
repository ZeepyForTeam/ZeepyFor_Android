package com.zeepy.zeepyforandroid.review.view

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
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.databinding.FragmentHousePictureBinding
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.review.view.adapter.HousePictureAdapter
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.zeepy.zeepyforandroid.util.FileConverter.asBitmap
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.zeepy.zeepyforandroid.util.ReviewNotice
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
        binding.btnRegister.setOnClickListener { showReviewRegisterDialog() }
        binding.tvSkip.setOnClickListener { showReviewRegisterDialog() }
    }

    fun showReviewRegisterDialog() {
        val parent = (parentFragment as NavHostFragment).parentFragment
        val registerReviewDialog = ZeepyDialogBuilder("리뷰를 등록하시겠습니까?", null)
            .setContent(resources.getString(R.string.write_review_notice_message))
            .setLeftButton(R.drawable.box_grayf9_8dp,"취소")
            .setRightButton(R.drawable.box_blue_59_8dp,"확인")
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    parent?.findNavController()?.popBackStack()
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    viewModel.postReviewToServer()
                    parent?.findNavController()?.popBackStack()
                    dialog.dismiss()
                }
            }).build()
        registerReviewDialog.show(childFragmentManager, this.tag)
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