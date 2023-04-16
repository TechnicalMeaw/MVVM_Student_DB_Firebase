package com.technicalmeaw.mvvmsampleexample1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.technicalmeaw.mvvmsampleexample1.model.DataModel
import com.technicalmeaw.mvvmsampleexample1.repository.ModelRepository

class ShowDetailsFragmentViewModel : ViewModel() {
    fun getStudentDetails(studentId: String) : LiveData<DataModel>{
        val repository = ModelRepository.getRepositoryInstance()
        return repository.getStudentDetails(studentId)


    }
}