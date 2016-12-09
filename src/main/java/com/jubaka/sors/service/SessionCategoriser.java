package com.jubaka.sors.service;

import com.jubaka.sors.beans.Category;
import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.beans.branch.SessionLightBean;
import com.jubaka.sors.entities.Session;

import java.util.*;

/**
 * Created by root on 07.12.16.
 */
public class SessionCategoriser {


    public Map<Category,List<SessionBean>> sortByHost(String selectedHost, List<SessionBean> sessions) {
        Map<Category, List<SessionBean>> categories = new HashMap<>();

        String key= null;
        for (SessionBean sesBean : sessions) {

            if (!sesBean.getSrcIP().equals(selectedHost)) {key = sesBean.getSrcIP();}
            if (!sesBean.getDstIP().equals(selectedHost)) {key = sesBean.getDstIP();}
            if (key == null) return categories;
            if (categories.containsKey(key)) {
                List<SessionBean> categorySet = categories.get(key);
                categorySet.add(sesBean);
            } else {
                Category cat = new Category();
                cat.setName(key);
                cat.setCategoryDesc("Desc");
                cat.setType(0);
                cat.setSelectedHost(selectedHost);
                List<SessionBean> categorySet = new ArrayList<>();
                categorySet.add(sesBean);
                categories.put(cat, categorySet);

            }
        }

        calcStatCategorised(categories);
        return categories;

    }

    public Map<Category,List<SessionBean>> sortByPort(String selectedHost, List<SessionBean> sessions) {
        HashMap<Category, List<SessionBean>> categories = new HashMap<>();


        Integer key= null;
        for (SessionBean sesBean : sessions) {

            key = sesBean.getDstP();
            if (key == null) return categories;
            if (categories.containsKey(key)) {
                List<SessionBean> categorySet = categories.get(key);
                categorySet.add(sesBean);
            } else {
                Category cat = new Category();
                cat.setName(key.toString());
                cat.setCategoryDesc("Desc");
                cat.setType(0);
                cat.setSelectedHost(selectedHost);
                List<SessionBean> categorySet = new ArrayList<>();
                categorySet.add(sesBean);
                categories.put(cat,categorySet);

            }
        }


        calcStatCategorised(categories);
        return categories;

    }


    private void calcStatCategorised(Map<Category,List<SessionBean>> map) {
        for (Category cat : map.keySet()) {
            calcStatCategorised(cat,map.get(cat));
        }
    }

    private void calcStatCategorised(Category accordingTo, List<SessionBean> set) {


        for (SessionBean bean : set) {
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
