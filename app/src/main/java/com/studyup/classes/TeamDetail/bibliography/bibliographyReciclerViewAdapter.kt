package com.studyup.classes.TeamDetail.bibliography

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studyup.R
import com.studyup.classes.NewTeam.bibliography.BibliographyRecycler
import com.studyup.utils.State

class bibliographyReciclerViewAdapter(var myDataset: MutableList<BibliographyRecycler>) : RecyclerView.Adapter<bibliographyReciclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        if (myDataset[position].isButton)
            return 2
        return if (myDataset[position].isBibliography)
            0
        else
            1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_bibliographies_content, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myDataset.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        bindBibliographyAttributes(holder, position)
    }

    private fun bindBibliographyAttributes(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.view.findViewById<TextView>(R.id.bibliography_title).text =
            myDataset[position].bibliography!!.title
        holder.view.findViewById<TextView>(R.id.bibliography_description).text =
            myDataset[position].bibliography!!.description
        holder.view.findViewById<ImageView>(R.id.cancel).visibility = View.INVISIBLE
    }
}