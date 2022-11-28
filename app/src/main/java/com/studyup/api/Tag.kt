package com.studyup.api

class Tag (val id:Int, val title: String, val description: String, val Activity:MutableList<Activity>){
    init {
        Activity.forEach{it.parent=this}
    }
    constructor() : this(0,"","", mutableListOf<Activity>()) {}
    fun checkActivity(){
        Activity.forEach{it.parent=this}
    }

}
class Activity(val id:Int,val title: String, val description: String,var memberComplete: MutableList<Member>, @Transient var parent:Tag?=null){
    constructor() : this(0,"","", mutableListOf<Member>()) {}
}
