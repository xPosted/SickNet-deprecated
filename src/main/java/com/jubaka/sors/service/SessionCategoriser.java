package com.jubaka.sors.service;

import com.jubaka.sors.beans.Category;
import com.jubaka.sors.beans.branch.*;
import com.jubaka.sors.entities.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by root on 07.12.16.
 */


public class SessionCategoriser {

/*
    private PortServiceService portService;

    public SessionCategoriser(PortServiceService portService) {
        this.portService = portService;

    }


    public Map<Category,List<SessionBean>> sortByHost(String selectedHost, List<SessionBean> sessions) {
        Map<Category, List<SessionBean>> categories = new HashMap<>();
        Map<String,List<SessionBean>> stringCategorised = new HashMap<String,List<SessionBean>>();

        String key= null;
        for (SessionBean sesBean : sessions) {

            if (!sesBean.getSrcIP().equals(selectedHost)) {key = sesBean.getSrcIP();}
            if (!sesBean.getDstIP().equals(selectedHost)) {key = sesBean.getDstIP();}
            if (key == null) return categories;
            if (stringCategorised.containsKey(key)) {
                List<SessionBean> categorySet = stringCategorised.get(key);
                categorySet.add(sesBean);
            } else {

                List<SessionBean> categorySet = new ArrayList<>();
                categorySet.add(sesBean);
                stringCategorised.put(key, categorySet);

            }
        }

        categories = createHostCategories(stringCategorised);
        calcStatCategorised(categories);
        return categories;

    }


    private Map<Category,List<SessionBean>> createHostCategories(Map<String,List<SessionBean>> strCategorised) {
        Map<Category,List<SessionBean>> res = new HashMap<>();
        IPItemLightBean ipBean = null;
        for (String strKey : strCategorised.keySet()) {
            Category cat = new Category();
            cat.setName(strKey);
            try {
                cat.setCategoryDesc(InetAddress.getByName(strKey).getHostName());
            } catch(UnknownHostException uhe) {
                uhe.printStackTrace();
                cat.setCategoryDesc("-");
            }

            cat.setType(0);
            res.put(cat,strCategorised.get(strKey));
        }
          return res;
    }

    private Map<Category,List<SessionBean>> createPortCategories(Map<Integer,List<SessionBean>> strCategorised) {
        Map<Category,List<SessionBean>> res = new HashMap<>();
        for (Integer intKey : strCategorised.keySet()) {
            Category cat = new Category();
            cat.setName(intKey.toString());
            cat.setCategoryDesc(portService.getServiceName(intKey));
            cat.setType(1);
            res.put(cat,strCategorised.get(intKey));
        }
        return res;
    }

    public Map<Category,List<SessionBean>> sortByPort(String selectedHost, List<SessionBean> sessions) {
        Map<Integer, List<SessionBean>> intCategorised = new HashMap<>();
        Map<Category, List<SessionBean>> categories = new HashMap<>();


        Integer key= null;
        for (SessionBean sesBean : sessions) {

            key = sesBean.getDstP();
            if (key == null) return categories;
            if (intCategorised.containsKey(key)) {
                List<SessionBean> categorySet = intCategorised.get(key);
                categorySet.add(sesBean);
            } else {

                List<SessionBean> categorySet = new ArrayList<>();
                categorySet.add(sesBean);
                intCategorised.put(key,categorySet);

            }
        }


        categories = createPortCategories(intCategorised);
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

    */
}
