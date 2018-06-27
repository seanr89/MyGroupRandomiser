package com.example.seanrafferty.mygrouprandomiser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.seanrafferty.mygrouprandomiser.Business.MyGroupManager
import com.example.seanrafferty.mygrouprandomiser.Models.MyGroup
import com.example.seanrafferty.mygrouprandomiser.Utilities.NavigationControls


class AddGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_group)
//        groupImage.setOnClickListener{
//            checkPermissionRG();
//        }
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when(requestCode){
//            MY_REQUEST_GALLERY -> try {
//                val inputStream = contentResolver.openInputStream(data?.getData())
//                filen = getFile()
//                val fileOutputStream = FileOutputStream(filen)
//                val buffer = ByteArray(1024)
//                var bytesRead: Int
//                while(true){
//                    bytesRead = inputStream.read(buffer)
//                    if(bytesRead == -1) break;
//                    fileOutputStream.write(buffer, 0,bytesRead)
//                }
//                fileOutputStream.close()
//                inputStream!!.close()
//                groupImage.setImageURI(Uri.parse("file:///" + filen))
//            }
//            catch (e: Exception)
//            {
//                Log.e("","Error while creating temp file", e)
//            }
//        }
//    }

    /**
     * Create and Save a group to the DB
     * @returns : integer to denote the status of the insert event
     */
    fun CreateAndSaveGroup() : Int
    {
        val nameTextView = findViewById<TextView>(R.id.nameText)
        val name : String = nameTextView.text.toString()

        if(!name.isNullOrBlank())
        {
            var groupManager = MyGroupManager(this)
            return groupManager.CreateGroup(MyGroup(0, name))
        }
        else
        {
            Toast.makeText(this, "No Name Provided", Toast.LENGTH_SHORT).show()
            return -1
        }
    }
}
