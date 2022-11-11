package com.studyup.classes.TeamDetail.tags

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.studyup.R
import com.studyup.api.Member
import com.studyup.classes.one_team.MemberContainerElement
import com.studyup.classes.team.Team
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class TagsFragmentDialogMembers(private var myDataset: MutableList<Member>? = null) :
    RecyclerView.Adapter<TagsFragmentDialogMembers.MyViewHolder>() {
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TagsFragmentDialogMembers.MyViewHolder {
        val view : View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_team_detail_member, parent, false)

        return MyViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (myDataset!=null) {
            holder.view.findViewById<TextView>(R.id.user_name).text =
                myDataset!![position].memberName
            val img_android = holder.view.findViewById<View>(R.id.user_profile) as ImageView
            Picasso.get()
                .load(myDataset!![position].ProfileURL)
                .transform(CropCircleTransformation())
                .into(img_android)
            holder.view.findViewById<ImageView>(R.id.user_status)
                .setImageResource(R.drawable.active)
        }
    }
    override fun getItemCount(): Int {
        if (myDataset!=null) {
            return myDataset!!.size
        }else{
            return 0
        }
    }
    fun filterList(filteredList: MutableList<Member>) {
        myDataset = filteredList
        notifyDataSetChanged()
    }
}