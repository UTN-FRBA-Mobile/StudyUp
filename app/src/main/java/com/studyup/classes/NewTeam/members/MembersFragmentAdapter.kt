package com.studyup.classes.NewTeam.members

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
import com.studyup.utils.State
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class MembersFragmentAdapter(private var myDataset: MutableList<Member>, private val recyler: MembersFragmentList) :
    RecyclerView.Adapter<MembersFragmentAdapter.MyViewHolder>(){

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MembersFragmentAdapter.MyViewHolder {
        val view : View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.members_element, parent, false)

        return MyViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.user_name).text = myDataset[position].memberName
        val img_android = holder.view.findViewById<View>(R.id.user_profile) as ImageView
        Picasso.get()
            .load(myDataset[position].ProfileURL)
            .transform(CropCircleTransformation())
            .into(img_android)
        val img_android_cancel = holder.view.findViewById<View>(R.id.cancel) as ImageView
        img_android_cancel.setOnClickListener {  _ ->
            State.newTeam.removeMember(myDataset[position].memberName)
            myDataset=State.newTeam.members
            notifyDataSetChanged()
        }
    }
    override fun getItemCount() = myDataset.size
}
