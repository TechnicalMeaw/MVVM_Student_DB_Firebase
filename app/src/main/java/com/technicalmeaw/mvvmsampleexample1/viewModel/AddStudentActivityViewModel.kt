package com.technicalmeaw.mvvmsampleexample1.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.technicalmeaw.mvvmsampleexample1.repository.ModelRepository
import com.technicalmeaw.mvvmsampleexample1.model.DataModel

class AddStudentActivityViewModel : ViewModel() {

    fun addNewStudent(student: DataModel, profileImage: Bitmap?){
        val repository = ModelRepository.getRepositoryInstance()
        repository.addNewStudent(student, profileImage)
    }
}