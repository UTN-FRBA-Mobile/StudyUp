package com.studyup.classes.team

import com.studyup.api.Event
import com.studyup.api.Member
import com.studyup.api.Tag

var teams = mutableListOf<Team>()
var filteredTeams = mutableListOf<Team>()

class Team(
    var id_team:Int,
    var image: Int,
    var title: String,
    var description: String,
    val id: Int? = teams.size
)