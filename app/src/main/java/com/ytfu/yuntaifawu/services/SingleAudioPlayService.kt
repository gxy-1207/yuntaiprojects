package com.ytfu.yuntaifawu.services

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.*
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import me.jessyan.autosize.utils.LogUtils
import java.lang.ref.WeakReference


class SingleAudioPlayService : Service() {

    interface OnPlayListener {
        fun onStartPlay(duration: Long)
        fun onProgressPlay(current: Long, duration: Long)
        fun onCompilePlay(duration: Long)
    }

    private var playListener: OnPlayListener? = null
    private var audioUrl: String? = null
    private var isPLaying = false


    private class PlayHandler(service: SingleAudioPlayService) : Handler(Looper.getMainLooper()) {
        companion object {
            const val CODE_PLAY_START = 0
            val CODE_PLAY_PROGRESS = 1
            val CODE_PLAY_COMPILE = 2
        }

        private val wr = WeakReference<SingleAudioPlayService>(service)


        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val service = wr.get() ?: return
            when (msg.what) {
                CODE_PLAY_PROGRESS -> service.onAudioPlayProgress()
            }
        }
    }

    private lateinit var player: ExoPlayer
    private lateinit var dataSourceFactory: DataSource.Factory

    private lateinit var playHandler: PlayHandler

    override fun onBind(intent: Intent?): IBinder? {
        return AudioPlayBinder(this)
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onUnbind(intent: Intent?): Boolean {
        playHandler.removeCallbacksAndMessages(null)
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        playHandler.removeCallbacksAndMessages(null)
    }


    //向客户端返回的IBinder对象
    class AudioPlayBinder(private val service: SingleAudioPlayService) : Binder() {
        fun getSingleAudioService(): SingleAudioPlayService {
            return service
        }
    }

    fun start(audioUrl: String, playListener: OnPlayListener? = null) {
        prepare(audioUrl, playListener)
        start()
    }

    fun start() {
        player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        dataSourceFactory = DefaultDataSourceFactory(this, "ExoPlayer")

        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_IDLE -> {
                    }
                    Player.STATE_BUFFERING -> {
                    }
                    Player.STATE_READY -> {
                        val total = player.duration
                        playListener?.onStartPlay(total)
                        playHandler.sendEmptyMessage(PlayHandler.CODE_PLAY_PROGRESS)
                    }
                    Player.STATE_ENDED -> {
                        LogUtils.e("play ended")
                        prepare(audioUrl ?: "", playListener)
                        player.seekTo(0)
                        pausePlayer()
                        playListener?.onCompilePlay(player.duration)
                    }
                }
            }
        })

        val concatenatingMediaSource = ConcatenatingMediaSource() //创建一个媒体连接源
        val mediaSource = ExtractorMediaSource
                .Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(audioUrl)) //创建一个播放数据源
        concatenatingMediaSource.addMediaSource(mediaSource)
        playHandler = PlayHandler(this)
        concatenatingMediaSource.addEventListener(playHandler, object : DefaultMediaSourceEventListener() {
            override fun onLoadStarted(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?, loadEventInfo: MediaSourceEventListener.LoadEventInfo?, mediaLoadData: MediaSourceEventListener.MediaLoadData?) {
                isPLaying = true
                playHandler.sendEmptyMessage(PlayHandler.CODE_PLAY_START)
            }

        })
        player.playWhenReady = true
        player.prepare(concatenatingMediaSource)
    }

    fun prepare(audioUrl: String, playListener: OnPlayListener? = null) {
        this.audioUrl = audioUrl
        this.playListener = playListener
    }

    fun isPrepare(): Boolean =
            !this.audioUrl.isNullOrEmpty()

    fun isPlaying(): Boolean {
        return isPLaying
    }

    fun pausePlayer() {
        player.playWhenReady = false
        player.playbackState
        isPLaying = false
    }

    fun startPlayer() {
        if (isPlaying()) {
            return
        }
        player.playWhenReady = true
        player.playbackState
        isPLaying = true
    }

    fun seekTo(position: Int) {
        player.seekTo(position.toLong())
    }

    fun releasePlayer() {
        player.stop(true)
        playHandler.removeCallbacksAndMessages(null)
        player.release()
        audioUrl = ""
        isPLaying = false
        stopSelf()
    }


    protected fun onAudioPlayProgress() {
        val currentPosition = player.currentPosition
        val total = player.duration
        playListener?.onProgressPlay(currentPosition, total)

        playHandler.sendEmptyMessageDelayed(PlayHandler.CODE_PLAY_PROGRESS, 200)
    }
}
