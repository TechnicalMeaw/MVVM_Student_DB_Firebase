package com.technicalmeaw.mvvmsampleexample1.repository

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.technicalmeaw.mvvmsampleexample1.imageCompression.ImageConversion
import com.technicalmeaw.mvvmsampleexample1.model.DataModel
import java.util.*
import kotlin.collections.HashMap

class ModelRepository {

    companion object{
        private var repo : ModelRepository? = null
        private val dataset : HashMap<String, DataModel> = HashMap()
        var data: MutableLiveData<List<DataModel>> = MutableLiveData()
        var studentDetails : MutableLiveData<DataModel> = MutableLiveData()

        fun getRepositoryInstance(): ModelRepository {
            return if (repo == null){
                repo = ModelRepository()
                repo as ModelRepository
            }else{
                repo as ModelRepository
            }
        }
    }

    fun getAllRepoData(): MutableLiveData<List<DataModel>> {
        getData()
        setValue()
        return data
    }


    fun getStudentDetails(studentId: String) : LiveData<DataModel>{
        FirebaseDatabase.getInstance("https://student-db-8717c-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("students").child(studentId).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val student = snapshot.getValue(DataModel::class.java)!!
                        updateStudentDetails(student)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        return studentDetails
    }

    private fun updateStudentDetails(student: DataModel){
        studentDetails.value = student
    }

    fun addNewStudent(student: DataModel, bitmap: Bitmap?){

        if (bitmap != null){
            val ref = FirebaseStorage.getInstance().getReference("students").child(student.rollNum)
            ref.putBytes(ImageConversion.bitmapToByteArray(bitmap, 250000))
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        student.profileImageUrl = it.toString()
                        updateStudents(student)
                    }

                }
        }
    }



    private fun updateStudents(student: DataModel){
        FirebaseDatabase.getInstance("https://student-db-8717c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("students").child(student.id)
            .setValue(student).addOnSuccessListener {
            Log.d("Repository", "Student data added")
        }
    }



    private fun setValue() {
        data.value = dataset.values.toList()
    }

    private fun getData() {
        FirebaseDatabase.getInstance("https://student-db-8717c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("students").addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()){
                    val dataModel = snapshot.getValue(DataModel::class.java)
                    if (dataModel != null){
                        dataset[dataModel.id] = dataModel
                        setValue()
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()){
                    val dataModel = snapshot.getValue(DataModel::class.java)
                    if (dataModel != null){
                        dataset[dataModel.rollNum] = dataModel
                        setValue()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val dataModel = snapshot.getValue(DataModel::class.java)
                    if (dataModel != null){
                        if (dataset.containsKey(dataModel.rollNum)){
                            dataset.remove(dataModel.rollNum)
                            setValue()
                        }
                    }

                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}