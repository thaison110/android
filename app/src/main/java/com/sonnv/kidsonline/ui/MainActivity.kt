package com.sonnv.kidsonline.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sonnv.kidsonline.databinding.ActivityMainBinding
import com.sonnv.kidsonline.extension.setOnClickAffect
import com.sonnv.kidsonline.ui.activity.BaseActivity
import com.sonnv.kidsonline.ui.dialog.ContactSupportDialog
import com.sonnv.kidsonline.ui.dialog.ProgressDialog


class MainActivity : BaseActivity() {
    private val progressDialog: ProgressDialog by lazy { ProgressDialog() }
    private var mBinding: ActivityMainBinding? = null

    private var permissionCallback: (() -> Unit) ?= null

    fun setPermissionCallback(permissionCallback: (() -> Unit) ?= null) {
        this.permissionCallback = permissionCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        initView()
    }

    private fun initView() {
        mBinding?.apply {
            imgCall.setOnClickAffect {
                ContactSupportDialog.newInstance()
                    .show(supportFragmentManager, ContactSupportDialog.TAG)
            }
        }
    }

    private fun hideActionBar() {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun callPhone(phoneNumber: String) {
//        val intent = Intent(Intent.ACTION_CALL)
//        intent.data = Uri.parse("tel:$phoneNumber")
//        startActivity(intent)
        showMessage("Comming soon!")
    }

    fun showMessage(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
    }

    fun showProgress() {
        progressDialog.show(supportFragmentManager)
    }

    fun hideProgress() {
        if (progressDialog.isAdded) {
            progressDialog.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionCallback?.invoke()
                } else {
//                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT)
//                        .show()
                }
            }
        }
    }


    fun checkPermission(): Boolean {
        return if (SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result1 =
                ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            result1 == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
                startActivityForResult(intent, PERMISSION_REQUEST_CODE)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, PERMISSION_REQUEST_CODE)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                this,
                arrayOf(WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 12345
    }
}