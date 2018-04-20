package com.android.ql.lf.carappclient.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.*
import com.android.ql.lf.carappclient.R
import kotlinx.android.synthetic.main.activity_city_map_layout.*


/**
 * Created by lf on 18.4.18.
 * @author lf on 18.4.18
 */
class CityMapActivity : BaseActivity() {

    companion object {
        fun startMapActivity(mContext: Context, name: String, latLng: LatLng) {
            val intent = Intent(mContext, CityMapActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("lalng", latLng)
            mContext.startActivity(intent)
        }
    }

    private lateinit var aMap: AMap

    private val latlngs = arrayListOf<LatLng>()

    private val lalng by lazy {
        intent.getParcelableExtra<LatLng>("lalng")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMapView.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }

    override fun getLayoutId() = R.layout.activity_city_map_layout

    override fun initView() {
        setSupportActionBar(mToolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolBar.setNavigationOnClickListener { finish() }
        aMap = mMapView.map
        location()

    }

    private fun location() {
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.strokeColor(Color.parseColor("#55FD9433"))
        myLocationStyle.radiusFillColor(Color.parseColor("#55FD9433"))
        myLocationStyle.interval(2000)
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked))
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER)
        myLocationStyle.showMyLocation(true)
        aMap.setMyLocationStyle(myLocationStyle)
        aMap.isMyLocationEnabled = true
        aMap.setOnMyLocationChangeListener {
            addMarker(it.latitude,it.longitude)
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14.0f))
        aMap.addMarker(MarkerOptions().position(lalng).title(intent.getStringExtra("name")))
    }

    private fun addMarker(la:Double,lng:Double) {
        latlngs.clear()
        latlngs.add(LatLng(la,lng))
        latlngs.add(lalng)
        aMap.addPolyline(PolylineOptions()
                .addAll(latlngs)
                .width(10.0f)
                .color(Color.parseColor("#55FD9433")))
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }
}