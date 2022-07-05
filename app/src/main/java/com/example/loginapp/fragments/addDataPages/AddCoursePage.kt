package com.example.loginapp.fragments.addDataPages

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.viewmodels.addDataPages.AddCourseViewModel


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with course interaction.
 */
class AddCoursePage : Fragment() {
    private lateinit var v : View
    private val viewModel: AddCourseViewModel by viewModels()
    private lateinit var course_image: ImageView
    private lateinit var nameView : EditText
    private lateinit var linkView : EditText
    private lateinit var codeView : EditText
    private lateinit var reset_button: Button
    private lateinit var submit_button: Button
    private lateinit var activityResult: ActivityResultLauncher<Intent>
    private lateinit var userInfo: SharedPreferences
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_add_course_page, container, false)
        course_image = v.findViewById(R.id.course_image_view)
        reset_button = v.findViewById(R.id.btn_reset)
        submit_button = v.findViewById(R.id.btn_submit)
        nameView = v.findViewById(R.id.new_course_name)
        codeView = v.findViewById(R.id.new_course_code)
        linkView = v.findViewById(R.id.new_course_link)

        activityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.loadCourseImage(result)
        }
        return v

    }

    override fun onStart() {
        super.onStart()
        val context = context

        if(context != null){
            userInfo = context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE)
            userId = userInfo.getString("UUID","")!!
        }
        val activity = (activity as MainActivity)

        if (activity != null) {
            activity.setTitleText("Add Course")
        }


        viewModel.courseImageUri.observe(viewLifecycleOwner, Observer { result ->
            Glide.with(context!!).load(result).into(course_image)
        })

        reset_button.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            nameView.setText("")
        }

        // set on-click listener
        submit_button.setOnClickListener {
            var name = nameView.text.toString()
            var code = codeView.text.toString()
            var classLink = linkView.text.toString()
            viewModel.createCourse(userId, name, code, classLink){
                findNavController().popBackStack()
            }

        }

        course_image.setOnClickListener{
            if (context != null) {
                activity.openGalleryForImage(activityResult, context)
            }
        }

    }



    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}