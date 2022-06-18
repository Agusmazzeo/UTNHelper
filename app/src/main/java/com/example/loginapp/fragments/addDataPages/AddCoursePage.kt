package com.example.loginapp.fragments.addDataPages

import android.content.Context
import android.content.Intent
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
import com.example.loginapp.viewmodels.addDataPages.addCourseViewModel


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with course interaction.
 */
class AddCoursePage : Fragment() {
    private lateinit var v : View
    private val viewModel: addCourseViewModel by viewModels()
    private lateinit var course_image: ImageView
    private lateinit var new_name_view : EditText
    private lateinit var reset_button: Button
    private lateinit var submit_button: Button
    private lateinit var activityResult: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_add_course_page, container, false)
        course_image = v.findViewById(R.id.course_image_view)
        reset_button = v.findViewById(R.id.btn_reset)
        submit_button = v.findViewById(R.id.btn_submit)
        new_name_view = v.findViewById(R.id.new_course_name)

        activityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.loadCourseImage(result)
        }
        return v

    }

    override fun onStart() {
        super.onStart()
        val context = getContext()

        val activity = (activity as MainActivity)

        if (activity != null) {
            activity.setTitleText("Add Course")
        }

        viewModel.course_image_uri.observe(viewLifecycleOwner, Observer { result ->
            Glide.with(context!!).load(result).into(course_image)
        })

        reset_button.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            new_name_view.setText("")
        }

        // set on-click listener
        submit_button.setOnClickListener {
            var new_name = new_name_view.text.toString()

            viewModel.createCourse(new_name){
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