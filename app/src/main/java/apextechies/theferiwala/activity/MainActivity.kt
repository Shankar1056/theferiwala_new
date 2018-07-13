package apextechies.theferiwala.activity

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import apextechies.starbasket.adapter.SubCategoryAdapter
import apextechies.starbasket.adapter.ViewPagerAdapter
import apextechies.starbasket.fragment.ImageFragment
import apextechies.starbasket.model.CategoryDataModel
import apextechies.starbasket.model.CategorysSubcatModel
import apextechies.theferiwala.R
import apextechies.theferiwala.R.id.vp_banner
import apextechies.theferiwala.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener , Runnable, CategoryAdapter.OnItemClickListener,  SubCategoryAdapter.OnItemClickListener, ViewPager.OnPageChangeListener{
    private var isAscending = true
    private var userScrollChange = false
    private var isBackPressed:Boolean = false
    private var prevPos = 0
    private var previousState = ViewPager.SCROLL_STATE_IDLE
    private var mAdapter: ViewPagerAdapter?= null

    private var mBannerHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        mAdapter = ViewPagerAdapter(getSupportFragmentManager());
        vp_banner.setScrollDurationFactor(3.0)
        vp_banner.setAdapter(mAdapter)
        vp_banner.addOnPageChangeListener(this)
        mBannerHandler = Handler()

        rv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_subcategory.isNestedScrollingEnabled = false
        tab_layout.setupWithViewPager(vp_banner)

        getBanner()
        gethomeCategory()
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getBanner() {
        /*retrofitDataProvider!!.homeBanner(object : DownlodableCallback<HomeBannerModel> {
            override fun onSuccess(result: HomeBannerModel?) {

                for (i in 0 until result!!.data!!.size) {
                    mAdapter!!.addItem(ImageFragment.newInstance(result.data!![i].banner!!))
                }
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }
        })*/

        var bannerList = ArrayList<String>()
        bannerList.add("http://theferiwala.com/image/cache/catalog/demo/slideshow/home1/slider-3-883x355.jpg")
        bannerList.add("http://theferiwala.com/image/cache/catalog/demo/slideshow/home1/slider-883x355.jpg")
        bannerList.add("http://theferiwala.com/image/cache/catalog/demo/slideshow/home1/slider-3-883x355.jpg")
        bannerList.add("http://theferiwala.com/image/cache/catalog/demo/slideshow/home1/slider-883x355.jpg")
        bannerList.add("http://theferiwala.com/image/cache/catalog/demo/slideshow/home1/slider-2-883x355.jpg")
        bannerList.add("http://theferiwala.com/image/cache/catalog/demo/slideshow/home1/slider-3-883x355.jpg")
        bannerList.add("http://theferiwala.com/image/cache/catalog/demo/slideshow/home1/slider-883x355.jpg")
        for (i in 0 until bannerList.size) {
            mAdapter!!.addItem(ImageFragment.newInstance(bannerList[i]))
        }
    }

    var calList = ArrayList<CategoryDataModel>()
    var subcalList = ArrayList<CategorysSubcatModel>()
    private fun gethomeCategory() {
      /*  retrofitDataProvider!!.category(object : DownlodableCallback<CategoryModel> {
            override fun onSuccess(result: CategoryModel?) {
                rv_category.adapter = CategoryAdapter(this@MainActivity, result!!.data!!)
                rv_subcategory.adapter = SubCategoryAdapter(this@MainActivity, result!!.data!!)
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }
        })*/
            subcalList.add(CategorysSubcatModel("", "", "Laptop", "http://theferiwala.com/image/cache/catalog/demo/macbook_air_1-600x600.jpg"))
            calList.add(CategoryDataModel("name", "Laptop", "http://theferiwala.com/image/cache/catalog/demo/macbook_air_1-600x600.jpg", subcalList))
        subcalList.add(CategorysSubcatModel("", "", "Mobile", "https://www.91-img.com/pictures/125689-v7-vivo-v9-mobile-phone-large-1.jpg"))
        calList.add(CategoryDataModel("name", "Mobile", "https://www.91-img.com/pictures/125689-v7-vivo-v9-mobile-phone-large-1.jpg", subcalList))
        subcalList.add(CategorysSubcatModel("", "", "Mens Wear", "https://3.imimg.com/data3/ND/LQ/MY-9211778/mens-wear-500x500.jpg"))
        calList.add(CategoryDataModel("name", "Mens Wear", "https://3.imimg.com/data3/ND/LQ/MY-9211778/mens-wear-500x500.jpg", subcalList))
        subcalList.add(CategorysSubcatModel("", "", "Womens Wear", "http://www.metromela.com/wp-content/uploads/2017/04/pjimage-2-300x300.jpg"))
        calList.add(CategoryDataModel("name", "Womens Wear", "http://www.metromela.com/wp-content/uploads/2017/04/pjimage-2-300x300.jpg", subcalList))
        subcalList.add(CategorysSubcatModel("", "", "Offer", "https://www.earticleblog.com/wp-content/uploads/2016/08/airtel-hanset-special-offers.png"))
        calList.add(CategoryDataModel("name", "Offer", "https://www.earticleblog.com/wp-content/uploads/2016/08/airtel-hanset-special-offers.png", subcalList))
        subcalList.add(CategorysSubcatModel("", "", "Deal", "http://theferiwala.com/image/cache/catalog/demo/macbook_air_1-600x600.jpg"))
        calList.add(CategoryDataModel("name", "Deal", "http://theferiwala.com/image/cache/catalog/demo/macbook_air_1-600x600.jpg", subcalList))

        rv_category.adapter = CategoryAdapter(this@MainActivity, calList)
        rv_subcategory.adapter = SubCategoryAdapter(this@MainActivity, calList)
    }

    override fun onStop() {
        super.onStop()
        mBannerHandler!!.removeCallbacks(this)
    }

    override fun run() {

        try {
            val nextPos: Int

            if (isAscending) {
                nextPos = (vp_banner.getCurrentItem() + 1) % mAdapter!!.getCount()
                isAscending = nextPos < mAdapter!!.getCount() - 1
            } else {
                nextPos = (vp_banner.getCurrentItem() - 1) % mAdapter!!.getCount()
                isAscending = nextPos < 1
            }

            vp_banner.setCurrentItem(nextPos, true)
            mBannerHandler!!.postDelayed(this, 3000)
        } catch (e: ArithmeticException) {
        }

    }

    override fun onPageScrollStateChanged(state: Int) {
        if (previousState == ViewPager.SCROLL_STATE_DRAGGING && state == ViewPager.SCROLL_STATE_SETTLING) {
            userScrollChange = true
        } else if (previousState == ViewPager.SCROLL_STATE_SETTLING && state == ViewPager.SCROLL_STATE_IDLE) {
            userScrollChange = false
        }

        previousState = state
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (userScrollChange) {
            isAscending = prevPos < position && position < mAdapter!!.getCount() - 1 && position > 0
        }

        prevPos = position
    }

    override fun onItemClick(item: CategorysSubcatModel) {
        val intent = Intent(this, ProductDetails::class.java)
        intent.putExtra(NfcAdapter.EXTRA_DATA, item)
        startActivity(intent)

    }

    override fun onViewAll(item: CategoryDataModel) {

        /* val intent = Intent(this, CategoryActivity::class.java)
         intent.putExtra(EXTRA_DATA, item)
         startActivity(intent)*/
    }

    override fun onItemClick(item: CategoryDataModel) {
        val intent = Intent(this, ProductDetails::class.java)
        intent.putExtra(NfcAdapter.EXTRA_DATA, item)
        startActivity(intent)

    }
}
