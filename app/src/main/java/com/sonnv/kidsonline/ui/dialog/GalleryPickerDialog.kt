package com.sonnv.kidsonline.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentUris
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonnv.kidsonline.R
import com.sonnv.kidsonline.databinding.FragmentGalleryBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.model.ImageModel
import com.sonnv.kidsonline.ui.adapter.GalleryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class GalleryPickerDialog : BottomSheetDialogFragment() {

    private var mBinding: FragmentGalleryBinding? = null
    private val mAdapter by lazy { GalleryAdapter() }

    private var dataCallback: ((List<ImageModel>) -> Unit) ?= null

    private var isSingleChoice = false

    fun setCallback(dataCallback: ((List<ImageModel>) -> Unit) ?= null) {
        this.dataCallback = dataCallback
    }

    fun applySingleChoice() {
        isSingleChoice = true
        mAdapter.setSingleChoice(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentGalleryBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding?.btnCancel?.setOnClickAffect {
            dismiss()
        }

        mBinding?.btnCOntinue?.setOnClickAffect {
            backToPrevious()
        }

        mBinding?.apply {
            rcv.layoutManager = GridLayoutManager(context, 4)
            rcv.adapter = mAdapter

            mAdapter.setCountCallback {
                mBinding?.btnCOntinue?.text = if (it == 0) getString(R.string.label_continue)
                else "${getString(R.string.label_continue)} ($it)"
            }
        }

        loadImageFromGallery()
    }

    private fun backToPrevious() {
        val data = mAdapter.getImageSelected()
        dataCallback?.invoke(data)
        dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun loadImageFromGallery() {
        GlobalScope.launch(Dispatchers.IO) {
            val data = queryImages(100)
            GlobalScope.launch(Dispatchers.Main) {
                mAdapter.setData(data.toMutableList())
            }
        }
    }

    private suspend fun queryImages(
        numberImage: Int = 0, loadAllImage: Boolean = true
    ): List<ImageModel> {
        val images = mutableListOf<ImageModel>()
        withContext(Dispatchers.IO) {

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.HEIGHT,
                MediaStore.Images.Media.WIDTH,
            )

            val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"

            val selectionArgs = arrayOf(
                // Release day of the G1. :)
                dateToTimestamp(day = 1, month = 9, year = 2022).toString()
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} ASC"

            context?.contentResolver?.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateModifiedColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                val displayNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
                val widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)


                while (cursor.moveToNext() && (images.size < numberImage || loadAllImage)) {
                    val image = addImageResource(
                        cursor,
                        idColumn,
                        dateModifiedColumn,
                        displayNameColumn,
                        sizeColumn,
                        heightColumn,
                        widthColumn
                    )
                    image?.let {
                        images += image
                    }
                }
            }
        }

        return images
    }

    @Suppress("SameParameterValue")
    @SuppressLint("SimpleDateFormat")
    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
        }

    private fun addImageResource(
        cursor: Cursor,
        idColumn: Int,
        dateModifiedColumn: Int,
        displayNameColumn: Int,
        sizeColumn: Int,
        heightColumn: Int,
        widthColumn: Int,
    ): ImageModel? {
        val id = cursor.getLong(idColumn)
        val contentUri = ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            id
        )
        val size = cursor.getInt(sizeColumn)
        val height = cursor.getInt(heightColumn)
        val width = cursor.getInt(widthColumn)
        if (size == 0 || height == 0 || width == 0) {
            return null
        }

        return ImageModel("", contentUri, false)
    }

    companion object {
        val TAG = NotificationDetailDialog.javaClass.simpleName

        fun newInstance(): GalleryPickerDialog {
            val args = Bundle()
            val fragment = GalleryPickerDialog()
            fragment.arguments = args
            return fragment
        }
    }
}