package com.studyup.classes.team

var teams = mutableListOf<Team>()

class Team(
    var image: Int,
    var title: String,
    var description: String,
    val id: Int? = teams.size
)