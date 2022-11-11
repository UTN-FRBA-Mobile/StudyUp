package com.studyup.classes.TeamDetail.tags

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.studyup.R
import com.studyup.api.APIService
import com.studyup.api.Activity
import com.studyup.classes.NewTeam.tags.TagRecycler
import com.studyup.classes.NewTeam.tags.TagsFragmentAdapter
import com.studyup.classes.NewTeam.tags.TagsFragmentList

class TagsFragmentElement(public var myDataset: MutableList<TagRecycler>, private val recyler: TagsFragment) :
    RecyclerView.Adapter<TagsFragmentElement.MyViewHolder>(){
    private val dialogActivity:TagsFragmentDialog = TagsFragmentDialog(recyler)
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun getItemViewType(position: Int): Int {
        if (myDataset[position].isEmpty)
            return 3
        if(myDataset[position].isButton)
            return 2
        return if(myDataset[position].isTag)
            0
        else
            1
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TagsFragmentElement.MyViewHolder {
        if (viewType == 3){
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_team_detail_tags_activity_empty, parent, false)
            return MyViewHolder(view)
        }
        if (viewType == 0) {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_team_details_tags, parent, false)
                return MyViewHolder(view)
            } else {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_team_detail_tags_activity, parent, false)
                return MyViewHolder(view)
            }



    }

    private fun expandOrCollapseParentItem(singleTag: TagRecycler, position: Int) {

        if (singleTag.isExpanded) {
            collapseParentRow(position)
        } else {
            expandParentRow(position)
        }
    }

    private fun expandParentRow(position: Int){
        val currentBoardingRow = myDataset[position]
        val services = currentBoardingRow.tag!!.Activity
        currentBoardingRow.isExpanded = true
        var nextPosition = position

        services.forEach { service ->
            val parentModel =  TagRecycler(null, service, false,false)
            //myDataset = myDataset.filter{it.isTag} as MutableList<TagRecycler>
            myDataset.add(++nextPosition,parentModel)
        }
        if (services.size == 0){
        val parentModel =  TagRecycler(null, null, false,false,false,true)
        myDataset.add(++nextPosition, parentModel)}
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun collapseParentRow(position: Int){
        val currentBoardingRow = myDataset[position]
        val services = currentBoardingRow.tag!!.Activity
        currentBoardingRow.isExpanded = false
        services.forEach { _ ->
            myDataset.removeAt(position + 1)
        }
        if (services.size == 0)
            myDataset.removeAt(position + 1)
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            if (myDataset[position].isTag) {
                holder.view.findViewById<TextView>(R.id.tag_title).text =
                    myDataset[position].tag!!.title
                holder.view.findViewById<TextView>(R.id.tag_description).text =
                    myDataset[position].tag!!.description
                holder.view.findViewById<CardView>(R.id.card).setOnClickListener {
                    expandOrCollapseParentItem(myDataset[position], position)
                }
            } else {
                if(!myDataset[position].isEmpty){
                holder.view.findViewById<TextView>(R.id.tag_title).text =
                    myDataset[position].activity!!.title
                holder.view.findViewById<TextView>(R.id.tag_description).text =
                    myDataset[position].activity!!.description

                holder.view.findViewById<CardView>(R.id.card).setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("title", myDataset[position].activity!!.title)
                    bundle.putInt("idActivity", myDataset[position].activity!!.id)
                    bundle.putInt("idTag", myDataset[position].activity!!.parent!!.id)

                    val transaction =   recyler.childFragmentManager.beginTransaction()
                    // For a little polish, specify a transition animation
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    var detailsFrame: View? = recyler.requireActivity().findViewById(R.id.dialogContainer)
                    detailsFrame?.visibility = View.VISIBLE

                    dialogActivity.arguments = bundle

                    transaction
                        .replace(R.id.dialogContainer, dialogActivity)
                        .addToBackStack(null)
                        .commit()


                    detailsFrame = recyler.requireActivity().findViewById(R.id.my_recycler_view)
                    detailsFrame?.visibility = View.GONE
                    // To make it fullscreen, use the 'content' root view as the container
                    // for the fragment, which is always the root view for the activity


                }
                }
            }

    }
    override fun getItemCount() = myDataset.size

}