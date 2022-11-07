package com.studyup.classes.NewTeam.tags

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studyup.R
import com.studyup.api.APIService
import com.studyup.api.Tag

class TagsFragmentAdapter(private var myDataset: MutableList<Tag>, private val recyler: TagsFragmentList) :
    RecyclerView.Adapter<TagsFragmentAdapter.MyViewHolder>(){

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TagsFragmentAdapter.MyViewHolder {
        val view : View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_new_team_tags_element, parent, false)

        return MyViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.tag_title).text = myDataset[position].title
        holder.view.findViewById<TextView>(R.id.tag_description).text = myDataset[position].description
        val img_android_cancel = holder.view.findViewById<View>(R.id.cancel) as ImageView
        img_android_cancel.setOnClickListener {  view ->
            APIService.deleteTags(myDataset[position].title)
            myDataset= APIService.getTags()
            notifyDataSetChanged()
        }
    }
    override fun getItemCount() = myDataset.size
}