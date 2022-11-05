package com.studyup.api

import com.studyup.exceptions.MemberAlreadyExists
import com.studyup.exceptions.MemberNotFound

object APIService{
    private var datasetAll = mutableListOf<Member>()
    private var dataset = mutableListOf<Member>()

    init {
        for (number in 1..3) {
            datasetAll.add(Member("User$number",randomImage(number), true))
        }
        for (number in 1..3) {
            datasetAll.add(Member("Test$number",randomImage(number),false))
        }
        for (number in 1..3) {
            datasetAll.add(Member("Ejemplo$number",randomImage(number),false))
        }
    }
    fun randomImage(number: Int):String {
        if (number % 5 == 0) {
            return "https://i.pinimg.com/564x/77/7e/a9/777ea9dbf01b32c122f38339297f5298.jpg"
        }else{
            if (number % 2 == 0)
                return "https://img.freepik.com/vector-premium/lindo-koala-dormir-icono-ilustracion-estilo-plano-dibujos-animados_138676-1232.jpg"
            else
                return "https://img.freepik.com/vector-gratis/ejemplo-lindo-icono-vector-historieta-pie-tigre-concepto-icono-naturaleza-animal-aislado-vector-premium-estilo-dibujos-animados-plana_138676-3797.jpg"
        }
    }
    fun getMembersAll(search: String): MutableList<Member>{
        var lista= this.datasetAll.filter {search.lowercase() in it.memberName.lowercase()}as MutableList<Member>
        lista = lista.filter{!(it.memberName in this.dataset.map{it.memberName})} as MutableList<Member>
        if(lista.size>3)
            return lista.subList(0,3)
        else
            return lista
    }
    fun getMembers(): MutableList<Member> {
        return this.dataset
    }
    fun insertMember(name: String){
        val members_exist = this.dataset.filter{it.memberName.lowercase()==name.lowercase()} as MutableList<Member>
        if (members_exist.size>0)
            throw MemberAlreadyExists()
        val members = this.datasetAll.filter{it.memberName.lowercase()==name.lowercase()} as MutableList<Member>
        if(members.size != 0)
            this.dataset.add(members.first())
        else
            throw MemberNotFound()
    }
    fun deleteMembers(name: String){
        this.dataset = this.dataset.filter{it.memberName!=name} as MutableList<Member>
    }
}