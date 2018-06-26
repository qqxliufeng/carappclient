package com.android.ql.lf.carappclient.ui.fragments.user.mine

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.android.ql.lf.carappclient.R
import com.android.ql.lf.carappclient.data.ImageBean
import com.android.ql.lf.carappclient.data.UserInfo
import com.android.ql.lf.carappclient.present.UserPresent
import com.android.ql.lf.carappclient.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carappclient.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carappclient.ui.fragments.user.ResetPasswordFragment
import com.android.ql.lf.carappclient.utils.*
import com.soundcloud.android.crop.Crop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.fragment_mine_personal_info_layout.*
import okhttp3.MultipartBody
import org.json.JSONObject
import java.io.File

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MinePersonalInfoFragment : BaseNetWorkingFragment() {

    private val userPresent by lazy {
        UserPresent()
    }

    //0  用户名   1 身份证号    2 头像
    private var currentToken = 0

    override fun getLayoutId() = R.layout.fragment_mine_personal_info_layout

    override fun initView(view: View?) {
        GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().memberPic, mTvPersonalInfoFace)
        mTvPersonalInfoNickName.text = UserInfo.getInstance().memberName
        mTvPersonalInfoPhone.text = UserInfo.getInstance().memberPhone.let { "${it.substring(0, 3)}****${it.substring(7, it.length)}" }
        mTvPersonalInfoIdCard.text = if (!TextUtils.isEmpty(UserInfo.getInstance().memberIdCard)) {
            UserInfo.getInstance().memberIdCard
        } else {
            "暂无"
        }
        mFaceContainer.setOnClickListener {
            currentToken = 2
            openImageChoose(MimeType.ofImage(), 1)
        }
        mNickNameContainer.setOnClickListener {
            currentToken = 0
            showEditInfoDialog("修改昵称", UserInfo.getInstance().memberName)
        }
        mIdCardContainer.setOnClickListener {
            currentToken = 1
            showEditInfoDialog("修改身份证号", UserInfo.getInstance().memberIdCard ?: "")
        }
        mTvPersonalInfoResetPassword.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "修改密码", ResetPasswordFragment::class.java)
        }
    }

    private fun showEditInfoDialog(title: String, oldInfo: String) {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(title)
        val contentView = View.inflate(mContext, R.layout.layout_edit_personal_content_layout, null)
        val content = contentView.findViewById<EditText>(R.id.mEtEditPersonalInfo)
        content.filters = arrayOf(InputFilter.LengthFilter(20))
        content.setText(oldInfo)
        content.setSelection(oldInfo.length)
        builder.setNegativeButton("取消", null)
        builder.setPositiveButton("确定") { _, _ ->
            if (oldInfo == content.text.toString().trim()) {
                return@setPositiveButton
            }
            var nickName = ""
            var idCard = ""
            when (currentToken) {
                0 -> {
                    nickName = content.getTextString()
                }
                1 -> {
                    if (!content.isIdCard()) {
                        toast("请输入正确的身份证号")
                        return@setPositiveButton
                    }
                    idCard = content.getTextString()
                }
            }
            mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_EDIT_PERSONAL,
                    RequestParamsHelper.getEditPersonalParam(account = nickName, idcard = idCard))
        }
        builder.setView(contentView)
        builder.create().show()
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x0) {
            getFastProgressDialog("正在修改……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        if (check != null) {
            if (SUCCESS_CODE == check.code) {
                when (currentToken) {
                    0 -> {
                        val nameResult = (check.obj as JSONObject).optJSONObject("result").optString("users_name")
                        mTvPersonalInfoNickName.text = nameResult
                        userPresent.modifyInfoForName(nameResult)
                    }
                    1 -> {
                        val idCardResult = (check.obj as JSONObject).optJSONObject("result").optString("users_idcard")
                        mTvPersonalInfoIdCard.text = idCardResult
                    }
                    2 -> {
                        val picResult = (check.obj as JSONObject).optJSONObject("result").optString("users_pic")
                        GlideManager.loadFaceCircleImage(mContext, picResult, mTvPersonalInfoFace)
                        userPresent.modifyInfoForPic(picResult)
                    }
                }
            } else {
                toast((check.obj as JSONObject).optString("msg"))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val uris = Matisse.obtainResult(data)
            uris[0].let {
                val dir = File("${Constants.IMAGE_PATH}face/")
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val desUri = Uri.fromFile(File(dir, "${System.currentTimeMillis()}.jpg"))
                Crop.of(it, desUri).start(mContext, this@MinePersonalInfoFragment)
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
            if (data != null) {
                val uri = Crop.getOutput(data)
                ImageUploadHelper(object : ImageUploadHelper.OnImageUploadListener {
                    override fun onActionFailed() {
                        toast("头像上传失败，请稍后重试！")
                    }

                    override fun onActionStart() {
                        getFastProgressDialog("正在上传头像……")
                    }

                    override fun onActionEnd(builder: MultipartBody.Builder) {
                        mPresent.uploadFile(0x1, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_EDIT_PERSONAL, builder.build().parts())
                    }
                }).upload(arrayListOf(ImageBean(null, uri.path)), 100)
            }
        }
    }
}