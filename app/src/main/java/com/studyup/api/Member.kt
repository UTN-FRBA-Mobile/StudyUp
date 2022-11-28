package com.studyup.api

class Member (val id:Int, val memberName: String, val ProfileURL: String, val Status:Boolean){
    constructor() : this(1,"","", true) {}
}
