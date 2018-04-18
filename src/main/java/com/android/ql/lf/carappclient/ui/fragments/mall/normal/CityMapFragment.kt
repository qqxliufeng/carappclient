package com.android.ql.lf.carappclient.ui.fragments.mall.normal

import android.view.View
import com.amap.api.maps2d.AMap
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.activity_city_map_layout.*

/**
 * Created by lf on 18.4.18.
 * @author lf on 18.4.18
 */
class CityMapFragment : BaseNetWorkingFragment() {

    private lateinit var aMap: AMap

    override fun getLayoutId() = R.layout.activity_city_map_layout

    override fun initView(view: View?) {
        aMap = mMapView.map
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroyView() {
        mMapView.onDestroy()
        super.onDestroyView()
    }

}