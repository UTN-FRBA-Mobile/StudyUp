package com.studyup.api

class Team {
    var members = mutableListOf<Member>()
    var tags = mutableListOf<Tag>()
    var events = mutableListOf<Event>()

    fun addMember(member: Member) {
        this.members.add(member)
    }
    fun removeMember(member: Member) {
        this.members.remove(member)
    }

    fun addTag(tag: Tag) {
        this.tags.add(tag)
    }
    fun removeTag(tag: Tag) {
        this.tags.remove(tag)
    }
    fun addEvent(event: Event) {
        this.events.add(event)
    }
    fun removeEvent(event: Event) {
        this.events.remove(event)
    }
    fun getActivity(idActivity:Int, idTag:Int): Activity{
        val tag =  tags.find{it.id==idTag}
        return tag!!.Activity.find { it.id==idActivity }!!
    }
    fun getTotalMembers():Int{
        return members.size
    }
}