package com.ytfu.yuntaifawu.ui.lvshihetong.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;

public class HomeContractBean extends JSectionEntity {
    private boolean isHeader;
    private boolean isAudio;


    private VipPageInfo.ContractBean contractBean;
    private VipPageInfo.AudioBean audioBean;

    public boolean isAudio() {
        return isAudio;
    }

    public VipPageInfo.ContractBean getContractBean() {
        return contractBean;
    }

    public VipPageInfo.AudioBean getAudioBean() {
        return audioBean;
    }

    private ContractGroupHeaderBean groupHeaderBean;

    public ContractGroupHeaderBean getGroupHeaderBean() {
        return groupHeaderBean;
    }

    public HomeContractBean(VipPageInfo.ContractBean contractBean) {
        this.isHeader = false;
        this.isAudio = false;
        this.contractBean = contractBean;
    }

    public HomeContractBean(VipPageInfo.AudioBean audioBean) {
        this.isHeader = false;
        this.isAudio = true;
        this.audioBean = audioBean;
    }


    public HomeContractBean(ContractGroupHeaderBean headerBean) {
        this.isHeader = true;
        this.groupHeaderBean = headerBean;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }

    @Override
    public int getItemType() {
        if (isHeader) {
            return HEADER_TYPE;
        }
        if (isAudio) {
            return 1;
        } else {
            return 2;
        }
    }

}
