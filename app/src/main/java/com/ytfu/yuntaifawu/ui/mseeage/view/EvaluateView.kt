package com.ytfu.yuntaifawu.ui.mseeage.view

import com.core.ui.mvp.view.BasicMVPRefreshView
import com.ytfu.yuntaifawu.ui.mseeage.bean.EvaluateClassBean

import com.ytfu.yuntaifawu.ui.mseeage.bean.Shuju

interface EvaluateView : BasicMVPRefreshView<Shuju> {
    fun onEvaluateClass(evaluateClassBean: EvaluateClassBean) {}
    fun onEvaluateError(error: String) {}
}