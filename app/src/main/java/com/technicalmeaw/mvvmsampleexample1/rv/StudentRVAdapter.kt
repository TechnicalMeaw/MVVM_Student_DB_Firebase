package com.technicalmeaw.mvvmsampleexample1.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.core.Context
import com.squareup.picasso.Picasso
import com.technicalmeaw.mvvmsampleexample1.R
import com.technicalmeaw.mvvmsampleexample1.listeners.StudentListListener
import com.technicalmeaw.mvvmsampleexample1.model.DataModel
import de.hdodenhof.circleimageview.CircleImageView

class StudentRVAdapter(private val context: Fragment, private val listener: StudentListListener) : RecyclerView.Adapter<StudentRVAdapter.StudentViewHolder>() {

    private val allStudent : ArrayList<DataModel> = ArrayList()

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val profileImage: CircleImageView = itemView.findViewById(R.id.userDPImageView)
        val username: TextView = itemView.findViewById(R.id.userNameTextView)
        val rollNumber: TextView = itemView.findViewById(R.id.rollNumberTextView)
        val dept: TextView = itemView.findViewById(R.id.departmentTextView)

        // For whole card
        val student: CardView = itemView.findViewById(R.id.studentCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val viewHolder = StudentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.student_viewholder, parent, false))

        viewHolder.student.setOnClickListener { listener.onClickedStudent(allStudent[viewHolder.adapterPosition]) }

        return viewHolder
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentStudent = allStudent[position]

        // Facing Some Issue
//        Glide.with(context).load(currentStudent.profileImageUrl).into(holder.profileImage)

        Picasso.get().load(currentStudent.profileImageUrl).into(holder.profileImage)
        holder.username.text = "${currentStudent.firstName} ${currentStudent.lastName}"
        holder.rollNumber.text = currentStudent.rollNum
        holder.dept.text = currentStudent.branch
    }

    override fun getItemCount(): Int {
        return  allStudent.size
    }


    fun updateStudents(studentList: List<DataModel>){
        allStudent.clear()
        allStudent.addAll(studentList)
        this.notifyDataSetChanged()
    }
}