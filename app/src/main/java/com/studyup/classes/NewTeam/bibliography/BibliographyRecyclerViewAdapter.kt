package com.studyup.classes.NewTeam.bibliography

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.studyup.R
import com.studyup.api.APIService

class BibliographyRecyclerViewAdapter(var myDataset: MutableList<BibliographyRecycler>) : RecyclerView.Adapter<BibliographyRecyclerViewAdapter.MyViewHolder>() {

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

        val img_android_cancel = holder.view.findViewById<View>(R.id.cancel) as ImageView

        img_android_cancel.setOnClickListener { _ ->
            APIService.deleteBibliographies(myDataset[position].bibliography!!.title)
            myDataset = APIService.getBibliographies()
                .map { BibliographyRecycler(it) } as MutableList<BibliographyRecycler>
            notifyDataSetChanged()
        }
    }

    private fun bindBibliographyAttributes(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.view.findViewById<TextView>(R.id.bibliography_title).text =
            myDataset[position].bibliography!!.title
        holder.view.findViewById<TextView>(R.id.bibliography_description).text =
            myDataset[position].bibliography!!.description
    }

}