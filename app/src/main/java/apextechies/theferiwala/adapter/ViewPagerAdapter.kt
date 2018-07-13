package apextechies.starbasket.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.lang.IndexOutOfBoundsException
import java.util.*

class ViewPagerAdapter  : FragmentPagerAdapter {
    private val mItemList: ArrayList<Fragment>
    private val mTitleList: ArrayList<CharSequence>

    constructor(fm: FragmentManager) : super(fm) {
        mItemList = ArrayList()
        mTitleList = ArrayList()
    }

    constructor(fm: FragmentManager, itemList: ArrayList<Fragment>, titleList: ArrayList<CharSequence>) : super(fm) {
        mItemList = itemList
        mTitleList = titleList
    }

    fun addItem(item: Fragment, title: CharSequence) {
        mItemList.add(item)
        mTitleList.add(title)
        notifyDataSetChanged()
    }

    fun addItem(item: Fragment) {
        mItemList.add(item)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return mItemList[position]
    }

    override fun getCount(): Int {
        return mItemList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        try {
            return mTitleList[position]
        } catch (e: IndexOutOfBoundsException) {
            return null
        }

    }
}
