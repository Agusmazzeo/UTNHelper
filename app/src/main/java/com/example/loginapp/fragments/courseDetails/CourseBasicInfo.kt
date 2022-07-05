package com.example.loginapp.fragments.courseDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.loginapp.R
import com.example.loginapp.models.CourseModel
import com.example.loginapp.repository.CourseRepository
import com.example.loginapp.viewmodels.userDetails.BasicInfoViewModel
import com.example.loginapp.viewmodels.userDetails.CourseBasicInfoViewModel

class CourseBasicInfo(course: CourseModel) : Fragment() {

    companion object {
        fun newInstance(course: CourseModel) = CourseBasicInfo(course)
    }
    private lateinit var v: View
    var course : CourseModel = course
    lateinit var name: TextView
    lateinit var code: TextView
    lateinit var owner: TextView
    private val viewModel: CourseBasicInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_course_details_basic_info, container, false)
        name = v.findViewById(R.id.course_detail_name)
        code = v.findViewById(R.id.course_detail_code)
        owner = v.findViewById(R.id.course_detail_owner)
        return v
    }

    override fun onStart() {
        super.onStart()

        viewModel.getCourseOwnerName(course)
        name.text = course.name.toString()
        code.text = course.code.toString()

        viewModel.ownerName.observe(viewLifecycleOwner, Observer { result: String ->
            if(result != null){
                owner.text = result
            }
        })
        owner.text = course.owner.toString()
    }

}