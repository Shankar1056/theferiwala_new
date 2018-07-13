package apextechies.starbasket.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apextechies.starbasket.model.CategoryDataModel
import apextechies.starbasket.model.CategorysSubcatModel
import apextechies.theferiwala.R
import com.squareup.picasso.Picasso
import java.util.*

class SubCategoryAdapter (private val mListener: OnItemClickListener, private val list: ArrayList<CategoryDataModel>) : RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_subcategory, parent, false)
        return ViewHolder(itemView)
    }


    interface OnItemClickListener {
        fun onViewAll(item: CategoryDataModel)

        fun onItemClick(item: CategorysSubcatModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTV: TextView
        var viewAllTV: TextView
        var groupRV: RecyclerView
        var mAdapter: GroupAdapter? = null

        init {

            titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView
            viewAllTV = itemView.findViewById<View>(R.id.tv_view_all) as TextView
            groupRV = itemView.findViewById<View>(R.id.rv_group) as RecyclerView
            groupRV.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            viewAllTV.setOnClickListener { mListener.onViewAll(list[adapterPosition]) }
        }
    }

    inner class GroupAdapter internal constructor(private var mItemList: ArrayList<CategorysSubcatModel>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var imageIV: ImageView
            var titleTV: TextView

            init {

                imageIV = itemView.findViewById<View>(R.id.iv_image) as ImageView
                titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView

                itemView.setOnClickListener { mListener.onItemClick(mItemList[adapterPosition]) }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.item_group, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mItemList[position]

            holder.titleTV.text = item.name
            if (!TextUtils.isEmpty(item.icon))
            Picasso.with(holder.itemView.context)
                    .load(item.icon)
                    .fit()
                    .centerInside()
                    .into(holder.imageIV)
        }

        override fun getItemCount(): Int {
            return mItemList.size
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.titleTV.text = item.name
        holder.mAdapter = GroupAdapter(item.subcat!!)
        holder.groupRV.adapter = holder.mAdapter
    }


}
