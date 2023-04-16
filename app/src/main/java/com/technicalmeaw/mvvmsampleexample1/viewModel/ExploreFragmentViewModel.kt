package com.technicalmeaw.mvvmsampleexample1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technicalmeaw.mvvmsampleexample1.repository.ModelRepository
import com.technicalmeaw.mvvmsampleexample1.model.DataModel

class ExploreFragmentViewModel : ViewModel() {

//    private var data: MutableLiveData<List<DataModel>> = MutableLiveData<List<DataModel>>()

    fun getAllData() : LiveData<List<DataModel>>{
        val repository = ModelRepository.getRepositoryInstance()
        return repository.getAllRepoData()
    }
}