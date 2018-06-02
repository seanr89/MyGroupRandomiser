package com.example.seanrafferty.mygrouprandomiser

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Business.MyGroupManager
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls
import kotlinx.android.synthetic.main.activity_edit_group.*
import kotlinx.android.synthetic.main.fragment_event_info.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Math.E
import java.util.jar.Manifest

class AddGroup : AppCompatActivity() {
    val MY_REQUEST_CAMERA = 10
    val MY_REQUEST_WRITE_CAMERA = 11
    val CAPTURE_CAMERA = 12

    val MY_REQUEST_READ_GALLERY = 13
    val MY_REQUEST_WRITE_GALLERY = 14
    val MY_REQUEST_GALLERY = 15

    var filen: File? = null
    private val SELECTED_PIC = 1
    private lateinit var GroupImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_group)
        groupImage.setOnClickListener{
            checkPermissionRG();
        }
        //initialise the button and setup the click event
        val btn_save_group = findViewById<Button>(R.id.btn_save_group)
        btn_save_group.setOnClickListener()
        {
            val result = CreateAndSaveGroup()

            if(result >= 1)
            {
                NavigationControls.NavigateToMainActivity(this)
            }
            else
            {
                Toast.makeText(this, "Error - Save Failed!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            MY_REQUEST_GALLERY -> try {
                val inputStream = contentResolver.openInputStream(data?.getData())
                filen = getFile()
                val fileOutputStream = FileOutputStream(filen)
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while(true){
                    bytesRead = inputStream.read(buffer)
                    if(bytesRead == -1) break;
                    fileOutputStream.write(buffer, 0,bytesRead)
                }
                fileOutputStream.close()
                inputStream!!.close()
                groupImage.setImageURI(Uri.parse("file:///" + filen))
            }
            catch (e: Exception)
            {
                Log.e("","Error while creating temp file", e)
            }
        }
    }

    /**
     * Create and Save a group to the DB
     * @returns : integer to denote the status of the insert event
     */
    fun CreateAndSaveGroup() : Int
    {
        val nameTextView = findViewById<TextView>(R.id.nameText)
        val name : String = nameTextView.text.toString()

        var groupManager = MyGroupManager(this)

       return groupManager.CreateGroup(MyGroup(0, name))
    }

    private fun checkPermissionRG(){
        val permissionCheck = ContextCompat.checkSelfPermission(this@AddGroup, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this@AddGroup, arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE), MY_REQUEST_READ_GALLERY)
        }
        else
        {
            catchPhoto()
        }
    }

    private fun checkPermissionWG(){
        val permissionCheck = ContextCompat.checkSelfPermission(this@AddGroup, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this@AddGroup, arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_REQUEST_WRITE_GALLERY)
        }
        else
        {
            getPhotos()
        }
    }


    private fun getPhotos(){
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, MY_REQUEST_GALLERY)
    }
    private fun catchPhoto(){
        filen = getFile()
        if(filen != null){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try
            {
                val photocUri = Uri.fromFile(filen)
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photocUri)
                startActivityForResult(intent, CAPTURE_CAMERA)
            }
            catch(e: ActivityNotFoundException)
            {

            }
        }
        else
        {
            Toast.makeText(this@AddGroup, "Please check your SD card status", Toast.LENGTH_SHORT).show()
        }
    }

    fun getFile(): File?{
        val fileDir = File(""+Environment.getExternalStorageDirectory() + "/Android/data/" + applicationContext.packageName + "/Files")
        if(!fileDir.exists()){
            if(!fileDir.mkdirs())
            {
                return null
            }
        }

        val mediaFile = File(fileDir.getPath() + File.separator + "temp.jpg")
        return mediaFile
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            MY_REQUEST_READ_GALLERY -> checkPermissionWG()
            MY_REQUEST_WRITE_GALLERY -> getPhotos()
        }
    }



}
