package com.c23pc607.q_rice.ui.detail

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.c23pc607.q_rice.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO = "extra_photo"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvDetailName: TextView = binding.tvDetailName
        val tvDetailDescription: TextView = binding.textDescription
        val ivDetailPhoto: ImageView = binding.ivDetailPhoto

        /*
        Data dari intent bisa kita dapatkan dengan memanggil get dan disesuaikan dengan tipe datanya
         */
        val name = intent.getStringExtra(EXTRA_NAME)
        val byteArray = intent.getByteArrayExtra(EXTRA_PHOTO)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray?.size ?: 0)

        tvDetailName.text = name
        ivDetailPhoto.setImageBitmap(bitmap)

        val description: String = when (tvDetailName.text.toString()) {
            "Rice Variety Detection" -> "riceVariety"
            "Disease Detection in Rice Plants" -> "riceDisease"
            "Nutrient Deficiency Detection" -> "nutrientDeficiency"
            else -> "seedQuality"
        }
    }
}