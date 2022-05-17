package com.example.loginapp.fragments.userDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.models.UserModel

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class AttendanceInfo(user: UserModel) : Fragment() {

    companion object {
        fun newInstance(user: UserModel) = AttendanceInfo(user)
    }
    private lateinit var v: View
    lateinit var courseText: TextView
    lateinit var attendancesText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_details_attendance_info, container, false)
        courseText = v.findViewById(R.id.user_detail_course)
        attendancesText = v.findViewById(R.id.user_detail_attendance)
        return v
    }

    override fun onStart() {
        super.onStart()

        courseText.text = "Aplicaciones en Dispositivos"
        attendancesText.text = "27"
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}