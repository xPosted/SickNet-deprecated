package com.jubaka.sors.beans.branch;

import com.jubaka.sors.beans.Bean;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 07.10.16.
 */
public class BranchLightBean extends Bean implements Serializable{
    private BranchInfoBean bib =null;
    private List<SubnetLightBean> subnetsLight = new ArrayList<>();


    public BranchInfoBean getBib() {
        return bib;
    }
    public void setBib(BranchInfoBean bib) {
        this.bib = bib;
    }
    public SubnetLightBean getSubnetByAddr(InetAddress addr) {
        for (SubnetLightBean sb : subnetsLight) {
            if (sb.getSubnet().equals(addr))
                return sb;
        }
        return null;
    }
    public List<SubnetLightBean> getSubnetsLight() {
        return subnetsLight;
    }
    public void setSubnetsLight(List<SubnetLightBean> subnetsLight) {
        this.subnetsLight = subnetsLight;
    }

    public List<SubnetLightBean> getSubnetsLightBeanList() {
        return new ArrayList<SubnetLightBean>(subnetsLight);
    }
}
