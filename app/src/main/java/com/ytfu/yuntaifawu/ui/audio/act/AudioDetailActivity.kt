package com.ytfu.yuntaifawu.ui.audio.act

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils
import android.widget.SeekBar
import com.github.lee.annotation.InjectLayout
import com.github.lee.annotation.InjectPresenter
import com.lxj.xpopup.util.XPopupUtils
import com.ytfu.yuntaifawu.R
import com.ytfu.yuntaifawu.base.BaseActivity
import com.ytfu.yuntaifawu.services.SingleAudioPlayService
import com.ytfu.yuntaifawu.ui.audio.adapter.AudioDetailPageAdapter
import com.ytfu.yuntaifawu.ui.audio.p.AudioDetailPresenter
import com.ytfu.yuntaifawu.ui.audio.v.AudioDetailView
import com.ytfu.yuntaifawu.ui.home.bean.AudioDetailsBean
import com.ytfu.yuntaifawu.utils.GlideManager
import com.ytfu.yuntaifawu.utils.LoginHelper
import kotlinx.android.synthetic.main.activity_audio_detail.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import qiu.niorgai.StatusBarCompat

/**音频详情*/
@InjectLayout(R.layout.activity_audio_detail, toolbarLayoutId = R.layout.layout_toolbar_center_title)
@InjectPresenter(AudioDetailPresenter::class)
class AudioDetailActivity : BaseActivity<AudioDetailView, AudioDetailPresenter>(), AudioDetailView {

    private lateinit var adapter: AudioDetailPageAdapter
    private lateinit var conn: ServiceConnection
    private lateinit var audioPlayService: SingleAudioPlayService
    private var isBindService = false
    private var audioUrl = ""

    private var currentAudioId = ""

    private lateinit var onPlayListener: SingleAudioPlayService.OnPlayListener


    companion object {
        private const val KEY_AUDIO_ID = "AUDIO_ID"
        fun start(context: Context, audioId: String) {
            val bundle = Bundle()
            bundle.putString(KEY_AUDIO_ID, audioId)
            val starter = Intent(context, AudioDetailActivity::class.java)
            starter.putExtras(bundle)
            context.startActivity(starter)
        }
    }
    //===Desc:================================================================================

    override fun init() {
        super.init()
        onPlayListener = object : SingleAudioPlayService.OnPlayListener {
            override fun onStartPlay(duration: Long) {
                //                if (audioPlayService.isPlaying()) {
                //                    iv_audio_detail_controller.setImageResource(R.mipmap.icon_audio_play)
                //                } else {
                //                    iv_audio_detail_controller.setImageResource(R.mipmap.icon_audio_pause)
                //                }
                sb_audio_detail_progress.max = duration.toInt()
                sb_audio_detail_progress.progress = 0
                tv_audio_detail_current.text = formatTime(0)
            }

            override fun onProgressPlay(current: Long, duration: Long) {
                tv_audio_detail_current.text = formatTime(current)
                tv_audio_detail_total.text = formatTime(duration)
                sb_audio_detail_progress.max = duration.toInt()
                sb_audio_detail_progress.progress = current.toInt()
            }

            override fun onCompilePlay(duration: Long) {
                iv_audio_detail_controller.setImageResource(R.mipmap.icon_audio_play)
                tv_audio_detail_current.text = formatTime(0L)
                tv_audio_detail_total.text = formatTime(duration)
                sb_audio_detail_progress.max = duration.toInt()
                sb_audio_detail_progress.progress = 0
            }
        }

        conn = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                isBindService = false
            }

            override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
                if (service is SingleAudioPlayService.AudioPlayBinder) {
                    audioPlayService = service.getSingleAudioService()
                    isBindService = true
                    if (audioPlayService.isPrepare() && !audioPlayService.isPlaying()) {
                        audioPlayService.start()
                    } else {
                        audioPlayService.start(audioUrl, onPlayListener)
                    }
                }
            }
        }
        val intentService = Intent(mContext, SingleAudioPlayService::class.java)
        startService(intentService)
        bindService(intentService, conn, Context.BIND_AUTO_CREATE)
    }

    override fun setViewListener() {
        super.setViewListener()
        iv_audio_detail_controller.setOnClickListener {
            if (isBindService) {
                if (audioPlayService.isPlaying()) {
                    iv_audio_detail_controller.setImageResource(R.mipmap.icon_audio_play)
                    //pause
                    audioPlayService.pausePlayer()
                } else {
                    iv_audio_detail_controller.setImageResource(R.mipmap.icon_audio_pause)
                    //play
                    audioPlayService.startPlayer()
                }
            }
        }

        sb_audio_detail_progress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    audioPlayService.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        tv_audio_detail_like.setOnClickListener {
            if (TextUtils.isEmpty(currentAudioId)) {
                presenter.keepAudio(getBundleString(KEY_AUDIO_ID, ""))
            } else {
                presenter.keepAudio(currentAudioId)
            }
        }
    }

    override fun initData() {
        super.initData()
        StatusBarCompat.setStatusBarColor(this, Color.WHITE)
        changeStatusBarTextColor(true)

        setToolbarBackgroud(Color.WHITE)
        setToolbarLeftImage(R.drawable.fanhui_hui) { onBackPressed() }

        setToolbarTextColor(R.id.tv_global_title, Color.parseColor("#333333"))
        setToolbarText(R.id.tv_global_title, "音频详情")

        //获取详情
        showLoading()
        val audioId = getBundleString(KEY_AUDIO_ID, "")
        presenter.getAudioDetail(LoginHelper.getInstance().loginUserId, audioId)
    }

    override fun onStop() {
        super.onStop()
        if (isBindService) {
            audioPlayService.pausePlayer()
        }

    }

    override fun onResume() {
        super.onResume()
        if (isBindService) {
            audioPlayService.startPlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayService.releasePlayer()
        unbindService(conn)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //获取详情
        showLoading()
        audioPlayService.releasePlayer()
        val audioId = getBundleString(intent, KEY_AUDIO_ID, "")
        currentAudioId = audioId
        presenter.getAudioDetail(LoginHelper.getInstance().loginUserId, audioId)
    }

    //===Desc:================================================================================
    override fun onGetAudioDetail(data: AudioDetailsBean) {

        abl_audio_detail_top.setExpanded(true)
        adapter = AudioDetailPageAdapter(supportFragmentManager, data)
        vp_audio_detail_content.adapter = adapter

        val detail = data.list
        presenter.addUpPlay(LoginHelper.getInstance().loginUserId, detail.id)

        GlideManager.loadImageByUrl(mContext, detail.post_img, iv_audio_detail_img)
        tv_audio_detail_title.text = detail.post_title
        tv_audio_detail_count.text = "已有%s人购买".format(detail.order_count)

        val cn = CommonNavigator(mContext)
        cn.isAdjustMode = true
        cn.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, p1: Int): IPagerTitleView {
                val view = SimplePagerTitleView(mContext)
                view.textSize = 13f
                view.normalColor = Color.parseColor("#A1A1A1")
                view.selectedColor = Color.parseColor("#2998F5")
                view.text = if (p1 == 0) "音频简介" else "同类音频"
                view.setOnClickListener { vp_audio_detail_content.currentItem = p1 }
                return view
            }

            override fun getCount(): Int = 2

            override fun getIndicator(p0: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(mContext)
                indicator.lineHeight = XPopupUtils.dp2px(mContext, 2f).toFloat()
                indicator.setColors(Color.parseColor("#289AF6"))
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }
        }
        mi_audio_detail_titles.navigator = cn
        ViewPagerHelper.bind(mi_audio_detail_titles, vp_audio_detail_content)


        //设置音频
        tv_audio_detail_current.text = formatTime(0)
        tv_audio_detail_total.text = detail.time

        this.audioUrl = detail.post_audio
        //开始自动播放
        if (isBindService) {
            audioPlayService.start(audioUrl, onPlayListener)
        }


    }

    //===Desc:================================================================================

    fun formatTime(ms: Long): String {
        val totalSecond = ms / 1000
        val minute = totalSecond / 60
        val second = totalSecond % 60
        val minuteText = if (minute < 10) {
            "0$minute"
        } else {
            minute.toString()
        }
        val secondText = if (second < 10) {
            "0$second"
        } else {
            second.toString()
        }
        return "$minuteText:$secondText"
    }
}



