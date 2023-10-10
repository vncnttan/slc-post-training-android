package com.example.posttraining

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.posttraining.databinding.ActivityMainBinding
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var regisBtn: Button
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Method 1
//        usernameEt = findViewById(R.id.usernameEditText)
//        passwordEt = findViewById(R.id.passwordEditText)
//        regisBtn = findViewById(R.id.registerButton)

        // Method 2
        usernameEt = binding.usernameEditText
        passwordEt = binding.passwordEditText
        regisBtn = binding.registerButton

        // var -> bisa ganti intinya
        // val -> gabisa ganti isinya

        regisBtn.setOnClickListener{
            val username = usernameEt.text.toString()
            val password = passwordEt.text.toString()

            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(
                    this,
                    "Fill all the blanks",
                    Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("usernameExtra", username)
                finishAndRemoveTask() // jadinya kalo back dia udah langsung balik karena dibilang activity ini udah kelar
                startActivity(intent)
            }
        }

        setUpMap()
    }
    private fun setUpMap() {
        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )

        Configuration.getInstance().userAgentValue = "com.example.posttraining"

        val mapView = binding.mapView
        val geoPoint = GeoPoint(-6.2019, 106.180)

        val controller = mapView.controller
        controller.setZoom(17.0)
        controller.setCenter(geoPoint)

        val marker = Marker(mapView)
        marker.position = geoPoint
        marker.title = "Mungkin BINUS"
        mapView.overlays.add(marker)
    }
}