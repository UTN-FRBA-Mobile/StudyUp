package com.studyup.api

class Team {
    var members = mutableListOf<Member>()
    var tags = mutableListOf<Tag>()
    var events = mutableListOf<Event>()
    var bibliography = mutableListOf<Bibliography>()

    fun addMember(member: Member) {
        this.members.add(member)
    }
    fun removeMember(name: String) {
        this.members = this.members.filter{it.memberName!=name} as MutableList<Member>
    }

    fun addTag(tag: Tag) {
        this.tags.add(tag)
    }
    fun removeTag(title: String) {
        this.tags = this.tags.filter{it.title!=title} as MutableList<Tag>
    }
    fun removeActivity(activity: Activity){
        val tag = this.tags.filter{it==activity.parent} as MutableList<Tag>
        tag.first().Activity.remove(activity)
    }
    fun addEvent(event: Event) {
        this.events.add(event)
    }
    fun removeEvent(title: String) {
        this.events = this.events.filter{it.title!=title} as MutableList<Event>
    }
    fun getActivity(idActivity:Int, idTag:Int): Activity{
        val tag =  this.tags.find{it.id==idTag}
        return tag!!.Activity.find { it.id==idActivity }!!
    }
    fun getTotalMembers():Int{
        return members.size
    }
    fun addBibliography(bibliography: Bibliography) {
        this.bibliography.add(bibliography)
    }
    fun removeBibliography(title: String) {
        this.bibliography =
            this.bibliography.filter { it.title != title } as MutableList<Bibliography>
    }
}