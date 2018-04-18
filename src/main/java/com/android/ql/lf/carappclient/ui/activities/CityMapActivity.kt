package com.android.ql.lf.carappclient.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdate
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.android.ql.lf.carappclient.R
import kotlinx.android.synthetic.main.activity_city_map_layout.*
import com.amap.api.maps2d.model.MyLocationStyle


/**
 * Created by lf on 18.4.18.
 * @author lf on 18.4.18
 */
class CityMapActivity : BaseActivity() {

    private lateinit var aMap: AMap

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
        addMarker()
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
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14.0f))
    }

    private fun addMarker() {
        val latLng = LatLng(39.906901, 116.397972)
        aMap.addMarker(MarkerOptions().position(latLng).title("北京"))
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