package com.jubaka.sors.appserver.service;

import com.jubaka.sors.appserver.dao.UserLimitsDao;
import com.jubaka.sors.appserver.entities.UserLimits;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by root on 12.05.17.
 */

@Named
@ApplicationScoped
public class UserLimitsService {

    @Inject
    private UserLimitsDao userLimitsDao;

    private UserLimits defaultLims;

    @PostConstruct
    public void init() {
        this.defaultLims = initDefaultLims();
    }

    private UserLimits initDefaultLims() {
        long totalDataLen = -1;
        long totalTaskLen = Long.decode("2147483648");

        UserLimits defaultLims = userLimitsDao.getLimitsByValues(totalTaskLen,totalDataLen);
        if (defaultLims == null) {
            defaultLims = new UserLimits();
            defaultLims.setTotalTasksLen(totalTaskLen);
            defaultLims.setTotalDataLen(totalDataLen);
            defaultLims = userLimitsDao.insert(defaultLims);
        }
        return defaultLims;
    }

    public UserLimits getDefaultLims() {
        return defaultLims;
    }

}
