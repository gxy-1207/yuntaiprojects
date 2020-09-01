package com.ytfu.yuntaifawu.ui.chatroom.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.github.lee.annotation.InjectLayout
import com.github.lee.annotation.InjectPresenter
import com.yancy.gallerypick.config.GalleryConfig
import com.yancy.gallerypick.config.GalleryPick
import com.yancy.gallerypick.inter.IHandlerCallBack
import com.yancy.gallerypick.inter.ImageLoader
import com.yancy.gallerypick.widget.GalleryImageView
import com.yanzhenjie.permission.Permission
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.BaseActivity
import com.ytfu.yuntaifawu.ui.chatroom.p.RefundCommitPresenter
import com.ytfu.yuntaifawu.ui.chatroom.v.RefundCommitView
import com.ytfu.yuntaifawu.utils.AndPermissionUtil
import com.ytfu.yuntaifawu.utils.GlideManager
import com.ytfu.yuntaifawu.utils.LoginHelper
import kotlinx.android.synthetic.main.activity_refund_commit.*
import qiu.niorgai.StatusBarCompat
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.util.*

/**
 * 退款提交界面
 */
@InjectLayout(value = R.layout.activity_refund_commit, toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(RefundCommitPresenter::class)
class RefundCommitActivity : BaseActivity<RefundCommitView, RefundCommitPresenter>(), RefundCommitView {

    private var selectFile: File? = null

    companion object {
        private const val KEY_TO_USER_ID = "TO_USER_ID"
        fun start(context: Context, toUserId: String) {
            val bundle = Bundle()
            bundle.putString(KEY_TO_USER_ID, toUserId)
            val starter = Intent(context, RefundCommitActivity::class.java)
            starter.putExtras(bundle)
            context.startActivity(starter)
        }
    }

    //===Desc:================================================================================
    override fun setViewListener() {
        super.setViewListener()
        et_refund_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            @Suppress("DEPRECATION")
            override fun afterTextChanged(editable: Editable) {
                val s = "<font color='#FF0000'>%d</font>/200".format(et_refund_input.text.toString().trim { it <= ' ' }.length)
                tv_refund_number.text = Html.fromHtml(s)
            }
        })
        ll_refund_select.setOnClickListener {
            AndPermissionUtil.getInstance().requestPermissions(mContext, {
                GalleryPick.getInstance().setGalleryConfig(createConfig()).open(this@RefundCommitActivity)
            }, *Permission.Group.STORAGE)
        }
        cv_refund_commit.setOnClickListener {
            val input = et_refund_input.text.toString().trim()
            if (TextUtils.isEmpty(input)) {
                showToast("请输入退款原因")
                return@setOnClickListener
            }
            val userId = LoginHelper.getInstance().loginUserId
            val lawyerId = getBundleString(KEY_TO_USER_ID, "")
            presenter?.refund(userId, lawyerId, input, selectFile) ?: showToast("应用程序出现错误，请稍后重试")
        }
    }

    @Suppress("DEPRECATION")
    override fun initData() {
        super.initData()
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.transparent_4c))
        setToolbarLeftImage(R.drawable.fanhui_bai) { onBackPressed() }
        setToolbarText(R.id.tv_global_title, "申请退款")
        val s = "<font color='#FF0000'>%d</font>/200".format(0)
        tv_refund_number.text = Html.fromHtml(s)
    }

    //===Desc:================================================================================
    private val iHandlerCallBack: IHandlerCallBack = object : IHandlerCallBack {
        override fun onStart() {
        }

        override fun onSuccess(photoList: MutableList<String>?) {
            if (photoList.isNullOrEmpty()) {
                showToast("应用程序出现错误，请稍后重试")
                return
            }
            val selectedFile = File(photoList[0])
            iv_refund_img.visibility = View.VISIBLE
            selectFile = selectedFile
            GlideManager.loadLocalFile(mContext, selectedFile, iv_refund_img)
            // 压缩
            Luban.with(mContext).load(selectedFile).ignoreBy(100).setTargetDir(cacheDir.absolutePath).filter { path: String? -> !TextUtils.isEmpty(path) }.setCompressListener(object : OnCompressListener {
                override fun onStart() {}
                override fun onSuccess(file: File) {
                    //回显
                    iv_refund_img.visibility = View.VISIBLE
                    selectFile = file
                    GlideManager.loadLocalFile(mContext, file, iv_refund_img)
                }

                override fun onError(e: Throwable) {
                    showToast("头像压缩失败，请稍后重试")
                }
            }).launch()
        }

        override fun onCancel() {
        }

        override fun onFinish() {
        }

        override fun onError() {
        }
    }
    private val selectedPath: List<String> = ArrayList()
    private fun createConfig(): GalleryConfig {
        return GalleryConfig.Builder().imageLoader(object : ImageLoader {
            override fun displayImage(activity: Activity, context: Context, path: String, galleryImageView: GalleryImageView, width: Int, height: Int) {
                GlideManager.loadLocalFile(context, File(path), galleryImageView)
            }

            override fun clearMemoryCache() {}
        }).iHandlerCallBack(iHandlerCallBack) // 监听接口（必填）
                .provider(mContext.packageName + ".fileprovider") // provider (必填)s
                .pathList(selectedPath) // 记录已选的图片
                .multiSelect(false).crop(false) // 是否多选   默认：false
                .filePath("/Gallery/Pictures") // 图片存放路径
                .isOpenCamera(false).isShowCamera(false).build()
    }

    override fun onRefundSuccess() {
        // MyRefundActivity.start(mContext)
        showToast("申请退款成功")
        finish()
    }
}