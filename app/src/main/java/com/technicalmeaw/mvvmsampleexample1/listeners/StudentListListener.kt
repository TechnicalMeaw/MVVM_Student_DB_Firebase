package com.technicalmeaw.mvvmsampleexample1.listeners

import com.technicalmeaw.mvvmsampleexample1.model.DataModel

interface StudentListListener {
    fun onClickedStudent(student: DataModel)
}