package com.example.posttraining

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.posttraining.database.DatabaseHelper
import com.example.posttraining.databinding.ActivityInsertBinding
import com.example.posttraining.model.Product
import java.lang.Exception

class activity_insert : AppCompatActivity() {
    private lateinit var binding: ActivityInsertBinding
    private lateinit var imageResultActivity : ActivityResultLauncher<Intent>
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(this)

        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productImageIv.setOnClickListener{
            checkExternalStoragePermission()
            checkSMSPermission()
        }


        imageResultActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            res ->
            try {
                val imageUri = res.data?.data
                binding.productImageIv.setImageURI(imageUri)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

        binding.insertProductBtn.setOnClickListener {
            val name = binding.productNameEditText.text.toString()
            val type = binding.ProductTypeEditText.text.toString()
            val image = (binding.productImageIv.drawable as BitmapDrawable).bitmap

            databaseHelper.insertProduct( Product(
                0,
                name,
                image,
                type
            ))

            val intent = Intent(this, HomeActivity::class.java)

            sendSMS()

            finishAndRemoveTask() // jadinya kalo back dia udah langsung balik karena dibilang activity ini udah kelar
            startActivity(intent)
        }
    }

    private fun checkSMSPermission() {
        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                131
            ) else {
            sendSMS()
        }
    }

    private fun checkExternalStoragePermission() {
        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                101
            ) else {
                openGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 101  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            openGallery()
        } else if (requestCode == 131  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sendSMS()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        imageResultActivity.launch(galleryIntent)
    }

    private fun sendSMS(){
        val smsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(
            null,
            null,
            "Insert Success",
            null,
            null);
    }
}