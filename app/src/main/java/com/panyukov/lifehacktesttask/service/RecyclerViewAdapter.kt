package com.panyukov.lifehacktesttask.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.panyukov.lifehacktesttask.R
import com.panyukov.lifehacktesttask.model.Company
import kotlinx.android.synthetic.main.company_row.view.*

class RecyclerViewAdapter(
    private val companies: List<Company>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var onItemClick: ((Company) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.company_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_, name, imgUrl) = companies[position]
        holder.textCompanyName.text = name
        ImagePuller.pullImage(imgUrl)?.into(holder.imageCompany)
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textCompanyName: TextView = itemView.text_company_name

        val imageCompany: ImageView = itemView.image_company

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(companies[adapterPosition])
            }
        }
    }
}
