package com.studyup.api

class Team {
    private var members = mutableListOf<Member>()
    private var tags = mutableListOf<Tag>()
    init {
        for (number in 1..3) {
            members.add(Member("User$number","https://i.pinimg.com/564x/77/7e/a9/777ea9dbf01b32c122f38339297f5298.jpg", true))
        }
        for (number in 1..3) {
            members.add(Member("Test$number", "https://img.freepik.com/vector-premium/lindo-koala-dormir-icono-ilustracion-estilo-plano-dibujos-animados_138676-1232.jpg",false))
        }
        for (number in 1..3) {
            members.add(Member("Ejemplo$number", "https://img.freepik.com/vector-gratis/ejemplo-lindo-icono-vector-historieta-pie-tigre-concepto-icono-naturaleza-animal-aislado-vector-premium-estilo-dibujos-animados-plana_138676-3797.jpg",false))
        }
        tags.add(Tag(1,"test", "Esto es un test",mutableListOf<Activity>(
            Activity(1,"Activity1", "Description", members.subList(0,6)),
            Activity(2,"Activity2", "Description",
                members.subList(0,3))) ))
        tags.add(Tag(2,"test1", "Esto es un test",mutableListOf<Activity>() ))
        tags.add(Tag(3,"test2", "Esto es un test",mutableListOf<Activity>() ))
    }
    fun getMembers(): MutableList<Member> {
        return this.members
    }
    fun getTags(): MutableList<Tag>{
        return this.tags
    }
    fun getActivity(idActivity:Int, idTag:Int): Activity{
        val tag =  tags.find{it.id==idTag}
        return tag!!.Activity.find { it.id==idActivity }!!
    }
    fun getTotalMembers():Int{
        return members.size
    }
}