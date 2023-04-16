package com.technicalmeaw.mvvmsampleexample1.model

data class DataModel (val id: String,
                      val firstName: String,
                      val lastName:String,
                      var profileImageUrl: String,
                      val email:String,
                      val rollNum:String,
                      val regNum:String,
                      val branch: String,
                      val year:String){
    constructor() : this("","","","","","","","","")
}