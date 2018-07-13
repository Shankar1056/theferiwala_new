package apextechies.starbasket.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apextechies.starbasket.model.CategorysSubcatModel
import apextechies.theferiwala.R
import com.squareup.picasso.Picasso
import java.util.*

class AllCategoryAdapter (private val mListener: OnItemClickListener) : RecyclerView.Adapter<AllCategoryAdapter.ViewHolder>() {
    private val mItemList = ArrayList<CategorysSubcatModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_all_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItemList[position]
        val context = holder.itemView.context

        holder.titleTV.text = item.name
        Picasso.with(context)
                .load(item.icon)
                .fit()
                .centerInside()
                .into(holder.imageIV)
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun addItem(item: CategorysSubcatModel) {
        mItemList.add(item)
        notifyItemInserted(mItemList.size - 1)
    }

    interface OnItemClickListener {
        fun onItemClick(item: CategorysSubcatModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageIV: ImageView
        var titleTV: TextView

        init {

            imageIV = itemView.findViewById<View>(R.id.iv_image) as ImageView
            titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView

            itemView.setOnClickListener { mListener.onItemClick(mItemList[adapterPosition]) }
        }
    }
}
