package com.c23pc607.q_rice.ui.service

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.c23pc607.q_rice.R
import com.c23pc607.q_rice.data.Service
import com.c23pc607.q_rice.databinding.ActivityServiceBinding
import androidx.core.content.FileProvider
import com.c23pc607.q_rice.data.remote.response.ServiceResponse
import com.c23pc607.q_rice.ml.BirdsModel
import com.c23pc607.q_rice.ui.detail.DetailActivity
import com.c23pc607.q_rice.ui.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import java.io.*

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var tvOutput: TextView
    private lateinit var tvDetailName: TextView
    private lateinit var selectedImagePath: String
    private val viewModel by viewModels<ServiceViewModel>()
    private val GALLERY_REQUEST_CODE = 123

    companion object {
        const val KEY_SERVICE = "key_service"
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataService = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Service>(KEY_SERVICE, Service::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Service>(KEY_SERVICE)
        }

        tvDetailName = findViewById(R.id.tv_detail_name)

        if (dataService != null) {
            tvDetailName.text = dataService.name
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
        val token = SessionManager.getToken(this)
        binding.uploadButton.setOnClickListener {
            uploadImage()

            /*
            Intent untuk mengirimkan data ke activity lain
            */
            val moveWithDataIntent = Intent(this@ServiceActivity, DetailActivity::class.java)

            val drawable: BitmapDrawable = binding.previewImageView.drawable as BitmapDrawable
            val bitmap = drawable.bitmap

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            val myFile = File(selectedImagePath)

            val model: String = when (tvDetailName.text.toString()) {
                "Rice Variety Detection" -> "riceVariety"
                "Disease Detection in Rice Plants" -> "riceDisease"
                "Nutrient Deficiency Detection" -> "nutrientDeficiency"
                else -> "seedQuality"
            }

            // Check if the file size is not 0
            val fileSize = myFile.length()
            Log.d("ServiceActivity", " $myFile.path File size: $fileSize bytes")

            Log.d("ServiceActivity", "Prediction String: $myFile.path")

            CoroutineScope(Dispatchers.Main).launch {
                if (!token.isNullOrBlank()) {
                    Log.d("ServiceActivity", "token: $token")
                    viewModel.postPredict(token, myFile, model)
                }
            }

            viewModel.result.observe(this) {
                moveWithDataIntent.putExtra(DetailActivity.EXTRA_NAME, it?.predictionResult)
            }

            moveWithDataIntent.putExtra(DetailActivity.EXTRA_PHOTO, byteArray)
            startActivity(moveWithDataIntent)
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ServiceActivity,
                "com.c23pc607.q_rice",
                it
            )
            selectedImagePath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")

        createCustomTempFile(application).also {
            selectedImagePath = it.absolutePath
        }
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadImage() {
        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(selectedImagePath)

            myFile.let { file ->
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@ServiceActivity)
                val fileSize = myFile?.length() ?: 0L // Get the file size or assign 0 if myFile is null
                if (fileSize > 0) {
                    binding.previewImageView.setImageURI(uri)
                    Log.d("ServiceActivity", "Selected image file size: $fileSize bytes")
                    selectedImagePath = myFile?.path ?: "" // Update the selectedImagePath with the correct file path
                } else {
                    Log.d("ServiceActivity", "Selected image file size is 0")
                    // Handle the case when the file size is 0 (optional)
                }
            }
        }
    }
}
