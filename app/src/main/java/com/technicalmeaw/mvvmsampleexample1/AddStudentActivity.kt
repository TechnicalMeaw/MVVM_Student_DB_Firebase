package com.technicalmeaw.mvvmsampleexample1

import android.app.Activity
import android.app.AlertDialog.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.technicalmeaw.mvvmsampleexample1.databinding.ActivityAddStudentBinding
import com.technicalmeaw.mvvmsampleexample1.model.DataModel
import com.technicalmeaw.mvvmsampleexample1.viewModel.AddStudentActivityViewModel
import java.util.*


class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding
    private var profileImageBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get the view model
        val addStudentViewModel = ViewModelProvider(this)[AddStudentActivityViewModel::class.java]


        binding.profileCircleImageView.setOnClickListener {
            chooseImage(this)
        }

        binding.submitButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val roll = binding.rollNumberEditText.text.toString()
            val reg = binding.registrationNumberEditText.text.toString()
            val branch = binding.branchEditText.text.toString()
            val year = binding.yearEditText.text.toString()

            if (roll != "" && firstName != "" && branch != ""){
                Thread{
                    addStudentViewModel.addNewStudent(DataModel(UUID.randomUUID().toString(), firstName, lastName, "",  email, roll, reg, branch, year), profileImageBitmap)
                }.start()
                Toast.makeText(this, "Submitting...", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // function to let's the user to choose image from camera or gallery
    private fun chooseImage(context: Context) {
        val optionsMenu = arrayOf<CharSequence>(
            "Take Photo",
            "Choose from Gallery",
            "Exit"
        ) // create a menuOption Array
        // create a dialog for showing the optionsMenu
        val builder: Builder = Builder(context)
        // set the items in builder
        builder.setItems(optionsMenu,
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (optionsMenu[i] == "Take Photo") {
                    // Open the camera and get the photo
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraResultLauncher.launch(takePicture)
                } else if (optionsMenu[i] == "Choose from Gallery") {
                    // choose from  external storage
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    galleryResultLauncher.launch(pickPhoto)
                } else if (optionsMenu[i] == "Exit") {
                    dialogInterface.dismiss()
                }
            })
        builder.show()
    }


    private val galleryResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data

            if (data != null) {
                val imageUri = data.data
                binding.profileCircleImageView.setImageURI(imageUri)
                binding.profileCircleImageView.invalidate()
                val dr = binding.profileCircleImageView.drawable
                profileImageBitmap = dr.toBitmap()
                Glide.with(this).load(imageUri).into(binding.profileCircleImageView)
            }
        }
    }

    private val cameraResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            if (data != null){
                profileImageBitmap = data.extras?.get("data") as Bitmap
                Glide.with(this).load(profileImageBitmap).into(binding.profileCircleImageView)

            }

        }
    }
}