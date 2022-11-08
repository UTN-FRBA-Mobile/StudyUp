package com.studyup.api

class Tag (val title: String, val description: String, val Activity:MutableList<Activity>){
    init {
        Activity.forEach{it.parent=this}
    }
}
class Activity(val title: String, val description: String, var parent:Tag?=null)
