package com.example.loginapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.example.loginapp.R
import com.example.loginapp.models.UserModel
import com.example.loginapp.repository.UserRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UsersAdapter (
    var context: Context?,
    var userList : MutableList <UserModel?>,
    var onClick : (UserModel) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UserHolder>() {

    private lateinit var userRepository: UserRepository

    class UserHolder (v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        init {
            this.view = v
        }
        fun setTitle (title : String){
            var txtTitle : MaterialTextView = view.findViewById(R.id.user_item_text)
            txtTitle.text = title
        }

        fun getCardView () : CardView {
            return view.findViewById(R.id.user_item)
        }

        fun getDeleteButtonView() : ImageButton {
            return view.findViewById(R.id.delete_item_button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return (UserHolder(view))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        var user: UserModel? = userList[position]
        user?.let { holder.setTitle(it.name) }
        holder.getCardView().setOnClickListener {
            user?.let{
                var userData = UserModel(user.id, user.name, user.email, user.phone)
                onClick(userData)
            }
        }
        holder.getDeleteButtonView().setOnClickListener{
            if(context != null){
                MaterialAlertDialogBuilder(context!!)
                .setTitle("Delete User?")
                .setNegativeButton("Cancel") { dialog, which ->
                // Respond to negative button press
                }.setPositiveButton("Delete") { dialog, which ->
//                        userRepository = UserRepository(UserDaoFB())
//                    val db = AppDatabase.getAppDataBase(context!!)
//                    if (db != null) {
//                        userRepository = UserRepository(db.userDao())
//                    }
                    user?.let{
//                        userRepository.deleteUserByID(user.id)
//                        userList.removeAt(position)
//                        notifyItemRemoved(position)
//                        notifyItemRangeChanged(position,userList.size)
                    }
                }.show()
            }

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}