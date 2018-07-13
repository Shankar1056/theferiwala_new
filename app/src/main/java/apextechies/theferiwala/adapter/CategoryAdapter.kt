package apextechies.theferiwala.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import apextechies.starbasket.model.CategoryDataModel
import apextechies.starbasket.model.CategoryModel
import apextechies.theferiwala.R
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import java.util.ArrayList

public class CategoryAdapter(private val mListener: OnItemClickListener, private val catlist: ArrayList<CategoryDataModel>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = catlist[position]

        holder.titleTV.text = item.name
        if (!TextUtils.isEmpty(item.icon))
        Picasso.with(holder.itemView.context)
                .load(item.icon)
                .fit()
                .centerCrop()
                .into(holder.imageIV)
    }

    override fun getItemCount(): Int {
        return catlist.size
    }


    interface OnItemClickListener {
        fun onItemClick(item: CategoryDataModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageIV: RoundedImageView
        var titleTV: TextView

        init {

            imageIV = itemView.findViewById<View>(R.id.iv_image) as RoundedImageView
            titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView

            itemView.setOnClickListener { mListener.onItemClick(catlist[adapterPosition]) }
        }
    }
}
