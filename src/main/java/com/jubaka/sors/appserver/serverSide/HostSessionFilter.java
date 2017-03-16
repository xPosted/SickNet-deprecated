package com.jubaka.sors.appserver.serverSide;

import com.jubaka.sors.beans.Category;
import com.jubaka.sors.beans.branch.IPItemLightBean;
import com.jubaka.sors.beans.branch.SessionBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by root on 14.01.17.
 */
public class HostSessionFilter extends CommonFilterUtils implements SmartFilter {
//    private List<Category> categories= new ArrayList<>();
/*
    @Override
    public void clearState() {
        categories.clear();
    }

    @Override
    public List<Category> getAvailableCategories() {
        System.out.println("get FilterCategories");
        return categories;
    }
*/
    @Override
    public List<Category> sort(String selectedIP, List<SessionBean> sessions) {
  //      categories.clear();
   //     categories = sortByHost(selectedIP,sessions);
        return sortByHost(selectedIP,sessions);
    }


    public List<Category> sortByHost(String selectedHost, List<SessionBean> sessions) {
        List<Category> categories = new ArrayList<>();
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


    private List<Category> createHostCategories(Map<String,List<SessionBean>> strCategorised) {
        List<Category> res = Collections.synchronizedList(new ArrayList<>());
        IPItemLightBean ipBean = null;
        for (String strKey : strCategorised.keySet()) {
            Category cat = new Category(this);
            cat.setName(strKey);
            try {
                cat.setCategoryDesc(InetAddress.getByName(strKey).getHostName());
            } catch(UnknownHostException uhe) {
                uhe.printStackTrace();
                cat.setCategoryDesc("-");
            }

            cat.setType(0);
            cat.setSessionList(strCategorised.get(strKey));
            res.add(cat);
        }
        return res;
    }
}
