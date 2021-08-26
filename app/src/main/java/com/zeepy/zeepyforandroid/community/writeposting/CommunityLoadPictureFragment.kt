package com.zeepy.zeepyforandroid.community.writeposting

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
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
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
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
        viewModel.changeRequestPosting(args.requestWritePosting)

        setPictureList()
        setRegisterButton()
        openGallery()
        openCamera()
        stagePictures()
        completeUpload()
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
                val bitmap = uri.asBitmap(requireContext().contentResolver)
                pictures.add(PictureModel(bitmap))
                viewModel.changeUploadPictures(pictures)
            }
        }

    private val cameraActivityLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            if (isSaved) {
                pictures.add(PictureModel(pictureUri.asBitmap(requireContext().contentResolver)))
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
        viewModel.uploadBitmapImages.observe(viewLifecycleOwner) {
            (binding.rvHousePictures.adapter as UploadPictureAdapter).apply {
                submitList(viewModel.uploadBitmapImages.value?.toList())
            }
            changeVisibility()
        }
    }

    private fun changeVisibility() {
        if (viewModel.uploadBitmapImages.value.isNullOrEmpty()) {
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

    private fun showReviewRegisterDialog() {
        val parent = (parentFragment as NavHostFragment).parentFragment
        val registerReviewDialog = ZeepyDialogBuilder("리뷰를 등록하시겠습니까?", true)
            .setContent("*공동구매 글의 경우 참여자가 1명 이상일\n경우 글을 삭제하거나 수정하실 수 없습니다.\n\n*허위/중복/성의없는 정보 또는 비방글을\n작성할 경우, 서비스 이용이 제한될 수 있습니다.")
            .setLeftButton(R.drawable.box_grayf9_8dp,"취소")
            .setRightButton(R.drawable.box_green33_8dp,"확인")
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    parent?.findNavController()?.popBackStack()
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    //Todo: 커뮤니티 글 업로드 서버통신
                    parent?.findNavController()?.popBackStack()
                    dialog.dismiss()
                }
            }).build()
        registerReviewDialog.show(childFragmentManager, this.tag)
    }
}