package com.example.loginapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.example.loginapp.R
import com.example.loginapp.models.CourseModel
import com.example.loginapp.models.EnrollmentModel
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository
import com.example.loginapp.viewmodels.adapter.CourseEnrollmentAdapterViewModel

class CourseEnrollmentRequestsAdapter (
    var context: Context?,
    var enrollmentList : MutableList <EnrollmentModel?>,
    var onAcceptCallback: (String, CourseModel)->Unit,
    var onRejectCallback: (String, CourseModel)->Unit
) : RecyclerView.Adapter<CourseEnrollmentRequestsAdapter.CourseEnrollmentRequestsHolder>() {

    class CourseEnrollmentRequestsHolder (v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var courseModel: CourseModel? = null

        init {
            this.view = v
        }
        fun setTitle (title : String){
            var txtTitle : MaterialTextView = view.findViewById(R.id.course_enrollment_request_text)
            txtTitle.text = title
        }

        fun setAcceptButtonCallback (userId: String, courseModel: CourseModel, callback: (String, CourseModel)->Unit) {
            var button: Button = view.findViewById(R.id.course_enrollment_accept_button)
            button.setOnClickListener(){
                callback(userId, courseModel)
            }
        }

        fun setRejectButtonCallback (userId: String, courseModel: CourseModel, callback: (String, CourseModel)->Unit) {
            var button: Button = view.findViewById(R.id.course_enrollment_reject_button)
            button.setOnClickListener(){
                callback(userId, courseModel)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseEnrollmentRequestsHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.enrollment_request_item,parent,false)
        return (CourseEnrollmentRequestsHolder(view))
    }

    override fun onBindViewHolder(holder: CourseEnrollmentRequestsHolder, position: Int) {
        var enrollment: EnrollmentModel? = enrollmentList[position]
        enrollment?.let {
            holder.setTitle(it.user.name)
            holder.setAcceptButtonCallback(it.user.id, it.course, onAcceptCallback)
            holder.setRejectButtonCallback(it.user.id, it.course, onRejectCallback)
        }
    }

    override fun getItemCount(): Int {
        return enrollmentList.size
    }

}