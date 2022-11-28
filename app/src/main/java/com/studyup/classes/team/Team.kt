package com.studyup.classes.team

import com.studyup.R
import com.studyup.api.Bibliography
import com.studyup.api.Event
import com.studyup.api.Member
import com.studyup.api.Tag
import com.studyup.api.Team as TeamDetails
import java.lang.reflect.Constructor

var teams = mutableListOf<Team>()
var filteredTeams = mutableListOf<Team>()
class TeamDetail(
    var members:MutableList<Member>,
    var tags:MutableList<Tag>,
    var events:MutableList<Event>,
    var bibliography:MutableList<Bibliography>

    ){
    constructor() : this(mutableListOf<Member>(), mutableListOf<Tag>(), mutableListOf<Event>(), mutableListOf<Bibliography>()) {}
}
class Team(
    var id_team:Int,
    var image: Int,
    var title: String,
    var description: String,
    var details:TeamDetails,
    val id: Int? = teams.size,
    ){
    constructor() : this(0, R.drawable.placeholder, "","", TeamDetails()) {}
}

class MockTeam(
    var id: Int,
    var description: String,
    var title: String,
    var details:TeamDetails
){
    constructor() : this(0, "", "", TeamDetails()) {}
}