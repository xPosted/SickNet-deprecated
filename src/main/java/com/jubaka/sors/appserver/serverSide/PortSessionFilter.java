package com.jubaka.sors.serverSide;

import com.jubaka.sors.beans.Category;
import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.service.PortServiceService;

import java.util.*;

/**
 * Created by root on 14.01.17.
 */
public class PortSessionFilter extends CommonFilterUtils implements SmartFilter {
 //   private List<Category> categories = new ArrayList<>();
    private PortServiceService portService;

    public PortSessionFilter(PortServiceService portService) {
        this.portService = portService;
    }
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
 //       categories.clear();
 //       categories = sortByPort(selectedIP,sessions);
        return sortByPort(selectedIP,sessions);
    }



    public List<Category> sortByPort(String selectedHost, List<SessionBean> sessions) {
        Map<Integer, List<SessionBean>> intCategorised = new HashMap<>();
        List<Category> categories = new ArrayList<>();


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


    private List<Category> createPortCategories(Map<Integer,List<SessionBean>> strCategorised) {
        List<Category> res = Collections.synchronizedList(new ArrayList<>());
        for (Integer intKey : strCategorised.keySet()) {
            Category cat = new Category(this);
            cat.setName(intKey.toString());
            cat.setCategoryDesc(portService.getServiceName(intKey));
            cat.setType(1);
            cat.setSessionList(strCategorised.get(intKey));
            res.add(cat);
        }
        return res;
    }

}
