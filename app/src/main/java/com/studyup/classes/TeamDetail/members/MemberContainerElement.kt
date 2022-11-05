package com.studyup.classes.one_team

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.studyup.R
import com.studyup.api.APIService
import com.studyup.api.Member
import com.studyup.classes.members.MembersFragmentAdapter
import com.studyup.classes.members.MembersFragmentList
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class MemberContainerElement(private var myDataset: MutableList<Member>? = null, private val recyler: MemberContainer) :
    RecyclerView.Adapter<MemberContainerElement.MyViewHolder>() {
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MemberContainerElement.MyViewHolder {
        val view : View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_team_detail_member, parent, false)

        return MyViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (myDataset!=null){
            holder.view.findViewById<TextView>(R.id.user_name).text = myDataset!![position].memberName
            val img_android = holder.view.findViewById<View>(R.id.user_profile) as ImageView
            Picasso.get()
                .load(myDataset!![position].ProfileURL)
                .transform(CropCircleTransformation())
                .into(img_android)
            if (myDataset!![position].Status) {
                holder.view.findViewById<ImageView>(R.id.user_status)
                    .setImageResource(R.drawable.active)
            }else{
                holder.view.findViewById<ImageView>(R.id.user_status)
                    .setImageResource(R.drawable.inactive)
            }}
    }
    override fun getItemCount(): Int {
        if (myDataset!=null) {
            return myDataset!!.size
        }else{
            return 0
        }
    }
}