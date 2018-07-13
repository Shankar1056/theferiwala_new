package apextechies.theferiwala.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import apextechies.theferiwala.R
import apextechies.theferiwala.allinterfaces.AddressResolverService
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity: AppCompatActivity(), OnMapReadyCallback, LocationListener, View.OnClickListener, GoogleMap.OnMapClickListener, PlaceSelectionListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraChangeListener {

    private val TAG = "MapActivity"
    private var mMap: GoogleMap? = null
    private var mCurrentLocation: LatLng? = null
    private var autocompleteFragment: PlaceAutocompleteFragment? = null
    private var mResultReceiver: AddressResultReceiver? = null
    private var classname: String? = null
    private var addressText = ""
    private var city = ""
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocation: Location? = null
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private var lat: Double? = 0.0
    private var lon:Double? = 0.0
    private var postalcode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mLocation = Location("")
        initWidgit()

        save.setOnClickListener {
            val intent = Intent()
            intent.putExtra("MESSAGE", et_address.text.toString().trim())
            intent.putExtra("lat", ""+lat)
            intent.putExtra("lon", ""+lon)
            intent.putExtra("postal", postalcode)
            intent.putExtra("city", city)
            setResult(2, intent)
            finish()
        }
    }

    private fun initWidgit() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        autocompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment
        autocompleteFragment!!.setOnPlaceSelectedListener(this)

        val placeET = autocompleteFragment!!.getView()!!.findViewById<View>(R.id.place_autocomplete_search_input) as EditText
        placeET.textSize = 14f
        mResultReceiver = AddressResultReceiver(Handler())

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.setOnCameraChangeListener(this)
        // Setting a click event handler for the map
        googleMap.setOnMapClickListener(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        googleMap.isMyLocationEnabled = false


        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }

    }


    override fun onLocationChanged(location: Location) {
        onMapClick(LatLng(location.latitude, location.longitude))
    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

    }

    override fun onProviderEnabled(s: String) {

    }

    override fun onProviderDisabled(s: String) {

    }

    override fun onMapClick(latLng: LatLng) {
        mMap!!.clear()
        mCurrentLocation = latLng
        mLocation!!.setLatitude(latLng.latitude)
        mLocation!!.setLongitude(latLng.longitude)
        mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        startIntentService(mLocation!!)
        //drawCircle(latLng);


    }


    override fun onClick(v: View) {
        when (v.id) {

        }
    }


    override fun onPlaceSelected(place: Place) {
        Log.i(TAG, "Place: " + place.name)
        onMapClick(place.latLng)
    }

    override fun onError(status: Status) {

    }

    override fun onCameraMove() {

    }

    override fun onCameraChange(cameraPosition: CameraPosition) {
        mCurrentLocation = cameraPosition.target

        mMap!!.clear()

        try {
            mLocation!!.setLatitude(mCurrentLocation!!.latitude)
            mLocation!!.setLongitude(mCurrentLocation!!.longitude)
            startIntentService(mLocation!!)
            //drawCircle(new LatLng(mCurrentLocation.latitude, mCurrentLocation.longitude));
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

    }

    protected fun startIntentService(mLocation: Location) {
        val intent = Intent(this, AddressResolverService::class.java)
        intent.putExtra(AddressResolverService.RECEIVER, mResultReceiver)
        intent.putExtra(AddressResolverService.LOCATION_DATA_EXTRA, mLocation)

        startService(intent)
    }

    private inner class AddressResultReceiver internal constructor(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            val address = resultData.getString(AddressResolverService.LOCATION_DATA_STREET)
            try {
                lat = resultData.getDouble(AddressResolverService.LATITUTE)
                lon = resultData.getDouble(AddressResolverService.LONGITUTE)
                postalcode = resultData.getString(AddressResolverService.LOCATION_POSTAL_CODE)
                autocompleteFragment!!.setText(address)
                city = resultData.getString(AddressResolverService.LOCATION_DATA_CITY)
                addressText = address
                et_address.setText(resultData.getString(AddressResolverService.LOCATION_DATA_STREET))
                //et_houseFlat.setText(resultData.getString(AddressResolverService.LOCATION_DATA_AREA))
            } catch (e: NullPointerException) {
                Log.e(TAG, e.message)
            }

        }
    }

   override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

   override fun onBackPressed() {
       val intent = Intent()
       intent.putExtra("MESSAGE", et_address.text.toString().trim())
       intent.putExtra("lat", ""+lat)
       intent.putExtra("lon", ""+lon)
       intent.putExtra("postal", postalcode)
       intent.putExtra("city", city)
       setResult(2, intent)
        super.onBackPressed()
        finish()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.getLastLocation()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        mLocation = task.result
                        mCurrentLocation = LatLng(mLocation!!.getLatitude(), mLocation!!.getLongitude())
                        startIntentService(mLocation!!)
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15.0f))
                        //drawCircle(mCurrentLocation);
                    } else {
                        Log.w(TAG, "getLastLocation:exception", task.exception)
                    }
                }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this@MapActivity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE)

    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                   grantResults: IntArray) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation()

            } else {
            }
        }
    }

    /*override fun onResume() {
        val intent = Intent()
        intent.putExtra("MESSAGE", et_address.text.toString().trim())
        intent.putExtra("lat", ""+lat)
        intent.putExtra("lon", ""+lon)
        intent.putExtra("postal", postalcode)
        setResult(2, intent)
        super.onBackPressed()
        finish()
        super.onResume()

    }
*/

}