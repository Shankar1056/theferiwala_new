package apextechies.starbasket.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso

import apextechies.theferiwala.R


class ImageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false) as ImageView

        Picasso.with(context)
                .load(arguments!!.getString(ARG_URL))
                .fit()
                .centerCrop()
                .into(view)

        return view
    }

    companion object {
        private val ARG_URL = "arg_url"

        fun newInstance(url: String): ImageFragment {
            val args = Bundle()
            args.putString(ARG_URL, url)
            val fragment = ImageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
