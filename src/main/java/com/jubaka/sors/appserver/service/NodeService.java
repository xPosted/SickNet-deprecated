package com.jubaka.sors.appserver.service;

import com.jubaka.sors.beans.InfoBean;
import com.jubaka.sors.appserver.dao.NodeDao;
import com.jubaka.sors.appserver.entities.Node;
import com.jubaka.sors.appserver.entities.NodeActiveCheckPoint;
import com.jubaka.sors.appserver.entities.User;
import com.jubaka.sors.appserver.serverSide.ConnectionHandler;
import com.jubaka.sors.appserver.serverSide.NodeServerEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 27.10.16.
 */
@Named
@ApplicationScoped
public class NodeService {

    @Inject
    private NodeDao nodeDao;

    @Inject
    private NodeActiveCheckPointService nodeActiveCheckPointService;

    @Inject
    private ConnectionHandler ch;


    public Node getNodeByUnid(Long unid) {
        return nodeDao.selectById(unid);

    }

    public void handleNewNode(NodeServerEndpoint nodeEndPoint, User user) {
        Long unid = nodeEndPoint.getUnid();
        if (unid==null)  {
            unid = getNewUNID();
            nodeEndPoint.updateUnid(unid);

        }

        InfoBean nodeINfo =  nodeEndPoint.getInfo();
        insertUpdateNode(nodeINfo,user);
    }

    public  void insertUpdateNode(InfoBean info,User user) {
        Node node =  nodeDao.selectById(info.getUnid());
        if (node == null) node = new Node();
        node.setUnid(info.getUnid());
        node.setNodeName(info.getNodeName());
        node.setDescription(info.getDesc());
        node.setMaxMem(info.getMaxMem());
        node.setHomeMax(info.getHomeMax());
        node.setProcCount(info.getProcCount());
        node.setOsArch(info.getOsArch());
        nodeDao.update(node);
        nodeActiveCheckPointService.persistNewActiveCheckPoint(node,info,user);

    }

    public List<Node> selectConnectedNodesByUser() {
        List<NodeActiveCheckPoint> pointList = nodeActiveCheckPointService.selectCheckPointsByUser();
        List<Node> nodeList = new ArrayList<>();
        for (NodeActiveCheckPoint point : pointList) {
            nodeList.add(point.getNode());
        }
        return nodeList;
    }

    public List<NodeServerEndpoint> getConnectedNodeEndPointsByUser() {
        List<Node> nodeList = selectConnectedNodesByUser();
        List<NodeServerEndpoint> endpointList = new ArrayList<>();

        for (Node n : nodeList) {
            NodeServerEndpoint endPoint = ch.getNodeServerEndPoint(n.getUnid());
            if (endPoint == null) {
                nodeActiveCheckPointService.ifExistMoveToHistory(n);
            } else
                endpointList.add(endPoint);
        }
        return endpointList;
    }



    public long getNewUNID() {
        Random r = new Random();
        Long id =(long) -1;
        while (! checkIDExist(id)) {
            id = r.nextLong();
            if (id<0) id=id*(-1);
        }
        return id;

    }

    private boolean checkIDExist(Long value) {
        if (value<0) return false;
        if (getNodeByUnid(value)!=null) return false;
        return true;

    }



}
