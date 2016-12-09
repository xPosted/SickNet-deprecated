package com.jubaka.sors.service;

import com.jubaka.sors.beans.InfoBean;
import com.jubaka.sors.dao.NodeActiveCheckPointDao;
import com.jubaka.sors.dao.NodeCheckPointDao;
import com.jubaka.sors.entities.Node;
import com.jubaka.sors.entities.NodeActiveCheckPoint;
import com.jubaka.sors.entities.NodeCheckPoint;
import com.jubaka.sors.entities.User;
import com.jubaka.sors.managed.LoginBean;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by root on 28.10.16.
 */
@Named
@ApplicationScoped
public class NodeActiveCheckPointService {

    @Inject
    private LoginBean loginBean;
    @Inject
    private NodeActiveCheckPointDao nodeActiveCheckPointDao;
    @Inject
    private NodeCheckPointDao nodeCheckPointDao;

    public void ifExistMoveToHistory(Node node) {
        NodeActiveCheckPoint activeCheckPoint =  nodeActiveCheckPointDao.selectByNodeId(node);
        if (activeCheckPoint != null) {

            nodeCheckPointDao.insert(activeCheckPoint);
            nodeActiveCheckPointDao.remove(activeCheckPoint);

        }
    }

    public void persistNewActiveCheckPoint(Node node, InfoBean info, User user) {
        // some LogicHere
        NodeActiveCheckPoint activeCheckPoint = new NodeActiveCheckPoint();
        activeCheckPoint.setDate(new Timestamp(info.getConnectedDate().getTime()));
        activeCheckPoint.setIp(info.getPubAddr().getHostAddress());
        activeCheckPoint.setUser(user);
        activeCheckPoint.setNode(node);
        ifExistMoveToHistory(node);
        nodeActiveCheckPointDao.insert(activeCheckPoint);

    }

    public List<NodeActiveCheckPoint> selectCheckPointsByUser() {
        User u = loginBean.getUser();
        return nodeActiveCheckPointDao.selectCheckPointsByUser(u);
    }

}
