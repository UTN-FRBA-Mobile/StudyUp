package com.studyup.classes.team

import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.studyup.R
import com.studyup.databinding.CardCellBinding

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    onItemClick: (Int) -> Unit
): RecyclerView.ViewHolder(cardCellBinding.root) {
    init {
        itemView.findViewById<LinearLayout>(R.id.cardLayout).setOnClickListener{
            onItemClick(getBindingAdapterPosition())
        }
    }

    fun bindTeam(team: Team){
        cardCellBinding.teamImage.setImageResource(team.image)
        cardCellBinding.title.text = team.title
        cardCellBinding.description.text = team.description
    }
}