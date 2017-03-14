package com.jubaka.sors.serverSide;

import com.jubaka.sors.beans.Category;
import com.jubaka.sors.beans.branch.SessionBean;
import com.jubaka.sors.entities.Session;

import java.util.List;
import java.util.Map;

/**
 * Created by root on 14.01.17.
 */
public interface SmartFilter {
 //   public void clearState();
 //   public List<Category> getAvailableCategories();
    public List<Category> sort(String selectedIP, List<SessionBean> sessions);

}
