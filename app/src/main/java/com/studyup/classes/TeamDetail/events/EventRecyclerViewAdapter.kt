package com.studyup.classes.TeamDetail.events

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.studyup.R
import com.studyup.api.APIService

class EventRecyclerViewAdapter(public var myDataset: MutableList<EventRecycler>, private val recycler: EventsFragmentList) : RecyclerView.Adapter<EventRecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        if (myDataset[position].isButton)
            return 2
        return if (myDataset[position].isEvent)
            0
        else
            1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventRecyclerViewAdapter.MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_events_content, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        bindEventAttributes(holder, position)

        val img_android_cancel = holder.view.findViewById<View>(R.id.cancel) as ImageView

        img_android_cancel.setOnClickListener { _ ->
            APIService.deleteEvent(myDataset[position].event!!.title)
            myDataset = APIService.getEvents()
                .map { EventRecycler(it) } as MutableList<EventRecycler>
            notifyDataSetChanged()
        }
    }

    private fun bindEventAttributes(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.view.findViewById<TextView>(R.id.event_title).text =
            myDataset[position].event!!.title
        holder.view.findViewById<TextView>(R.id.start_date).text =
            holder.view.findViewById<TextView>(R.id.start_date).text.toString() + myDataset[position].event!!.start_date
        holder.view.findViewById<TextView>(R.id.end_date).text =
            holder.view.findViewById<TextView>(R.id.end_date).text.toString() + myDataset[position].event!!.end_date
    }

}