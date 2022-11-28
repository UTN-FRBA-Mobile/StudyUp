package com.studyup.classes.NewTeam.tags

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.studyup.R
import com.studyup.api.APIService
import com.studyup.api.Activity
import com.studyup.api.Member
import com.studyup.api.Tag
import com.studyup.utils.State

class TagsFragmentAdapter(public var myDataset: MutableList<TagRecycler>, private val recyler: TagsFragmentList) :
    RecyclerView.Adapter<TagsFragmentAdapter.MyViewHolder>(){

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun getItemViewType(position: Int): Int {
        if(myDataset[position].isButton)
            return 2
        return if(myDataset[position].isTag)
            0
        else
            1
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TagsFragmentAdapter.MyViewHolder {
        if(viewType==2) {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_new_team_tags_element_add_button, parent, false)
            return MyViewHolder(view)
        }else{
            if (viewType == 0) {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_new_team_tags_element, parent, false)
                return MyViewHolder(view)
            } else {
                val view: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_new_team_tags_element_activity, parent, false)
                return MyViewHolder(view)
            }
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
        myDataset.add(++nextPosition,TagRecycler(currentBoardingRow.tag, null, false,false, true))
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
        myDataset.removeAt(position + 1)
            notifyDataSetChanged()

    }
    fun newDialog(context: Context, parent:TagRecycler){
        val viewDialog = LayoutInflater.from(context).inflate(R.layout.fragment_new_team_tags_dialog,null)
        val dialogNewTag = MaterialAlertDialogBuilder(context)
            .setTitle("Nueva Activity")
            .setView(viewDialog)
            .show()
        viewDialog.findViewById<Button>(R.id.button_save).setOnClickListener{
            var text_title = viewDialog.findViewById<TextInputLayout>(R.id.title).editText?.text.toString()
            var text_description = viewDialog.findViewById<TextInputLayout>(R.id.description).editText?.text.toString()
            if (text_title!="" && text_description !=""){

                viewDialog.findViewById<TextInputLayout>(R.id.title).error = null
                viewDialog.findViewById<TextInputLayout>(R.id.description).error = null
                val new_activity =Activity(4,text_title,text_description,mutableListOf<Member>(),parent.tag!!)
                addActivityToTag(new_activity)
                dialogNewTag.cancel()
                //collapseParentRow(parent_index)
                myDataset.add(myDataset.indexOf(parent),TagRecycler(null, new_activity, false,false))
                notifyDataSetChanged()
                //myDataset[parent_index].tag!!.Activity.add(new_activity)
                //expandParentRow(parent_index)
                //this.fragmentRecicler!!.notify_update()

            }else{
                viewDialog.findViewById<TextInputLayout>(R.id.title).error = null
                viewDialog.findViewById<TextInputLayout>(R.id.description).error = null
                if (text_title=="")
                    viewDialog.findViewById<TextInputLayout>(R.id.title).error = "Completar el campo"
                if (text_description=="")
                    viewDialog.findViewById<TextInputLayout>(R.id.description).error = "Completar el campo"
            }
        }
        viewDialog.findViewById<Button>(R.id.button_cancel).setOnClickListener{
            dialogNewTag.cancel()
        }
    }

    private fun addActivityToTag(new_activity: Activity) {
        APIService.insertActivity(new_activity)
        val tag =
            State.newTeam.tags.filter { it.title == new_activity.parent?.title } as MutableList<Tag>
        tag.first().Activity.add(new_activity)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (myDataset[position].isButton){
            holder.view.findViewById<Button>(R.id.containedButton).setOnClickListener {
                newDialog(holder.view.context, myDataset[position])
            }
        }else {
            if (myDataset[position].isTag) {
                holder.view.findViewById<TextView>(R.id.tag_title).text =
                    myDataset[position].tag!!.title
                holder.view.findViewById<TextView>(R.id.tag_description).text =
                    myDataset[position].tag!!.description
                val img_android_cancel = holder.view.findViewById<View>(R.id.cancel) as ImageView
                img_android_cancel.setOnClickListener { _ ->
                    APIService.deleteTags(myDataset[position].tag!!.title)
                    State.newTeam.removeTag(myDataset[position].tag!!.title)
                    myDataset = APIService.getTags()
                        .map { TagRecycler(it, null, true, false) } as MutableList<TagRecycler>
                    notifyDataSetChanged()
                }
                holder.itemView.setOnClickListener {
                    expandOrCollapseParentItem(myDataset[position], position)
                }
            } else {
                holder.view.findViewById<TextView>(R.id.tag_title).text =
                    myDataset[position].activity!!.title
                holder.view.findViewById<TextView>(R.id.tag_description).text =
                    myDataset[position].activity!!.description
                val img_android_cancel = holder.view.findViewById<View>(R.id.cancel) as ImageView
                img_android_cancel.setOnClickListener { _ ->
                    APIService.deleteActivity(myDataset[position].activity!!)
                    State.newTeam.removeActivity(myDataset[position].activity!!)
                    myDataset.removeAt(position)
                    notifyDataSetChanged()
                }
            }
        }
    }
    override fun getItemCount() = myDataset.size
}