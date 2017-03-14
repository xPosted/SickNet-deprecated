package com.jubaka.sors.serverSide;

import com.jubaka.sors.beans.Category;
import com.jubaka.sors.beans.branch.SessionBean;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 14.01.17.
 */
public class CommonFilterUtils {
    protected void calcStatCategorised(List<Category> categories) {
        for (Category cat : categories) {
            calcStatCategorised(cat);
        }
    }

    protected void calcStatCategorised(Category accordingTo) {


        for (SessionBean bean : accordingTo.getSessionList()) {
            if (bean.getDstIP().equals(accordingTo.getSelectedHost())) {
                accordingTo.setReceivedFrom(accordingTo.getReceivedFrom()+bean.getSrcDataLen());
                accordingTo.setSendTo(accordingTo.getSendTo()+bean.getDstDataLen());
                accordingTo.setSesFrom(accordingTo.getSesFrom()+1);

            } else {
                accordingTo.setReceivedFrom(accordingTo.getReceivedFrom()+bean.getDstDataLen());
                accordingTo.setSendTo(accordingTo.getSendTo()+bean.getSrcDataLen());
                accordingTo.setSesTo(accordingTo.getSesTo()+1);
            }
            if (bean.getClosed()==null) {
                accordingTo.setActiveSes(accordingTo.getActiveSes()+1);
            }
        }


    }
}
