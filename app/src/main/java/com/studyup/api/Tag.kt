package com.studyup.api

class Tag (val id:Int, val title: String, val description: String, val Activity:MutableList<Activity>){
    init {
        Activity.forEach{it.parent=this}
    }
}
class Activity(val id:Int,val title: String, val description: String,val memberComplete: MutableList<Member>, @Transient var parent:Tag?=null){
}
