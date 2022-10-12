package com.studyup.classes.team

import androidx.recyclerview.widget.RecyclerView
import com.studyup.databinding.CardCellBinding

class CardViewHolder(
    private val cardCellBinding: CardCellBinding
): RecyclerView.ViewHolder(cardCellBinding.root) {

    fun bindTeam(team: Team){
        cardCellBinding.teamImage.setImageResource(team.image)
        cardCellBinding.title.text = team.title
        cardCellBinding.description.text = team.description
    }
}