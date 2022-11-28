package com.studyup.utils

import com.studyup.api.Team

object State {
    var newTeam: Team = Team()
    fun cleanTeam(){
        this.newTeam = Team()
    }
}