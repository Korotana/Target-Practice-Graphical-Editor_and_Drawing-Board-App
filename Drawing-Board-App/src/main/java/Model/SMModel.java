package Model;

import Interface.SMModelListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SMModel {

    public ArrayList<SMStateNode> nodes = new ArrayList<>();
    public ArrayList<SMTransitionLink> links = new ArrayList<>();
    public HashMap<SMStateNode, ArrayList<SMTransitionLink>> nodeLinksStart = new HashMap<>();
    public HashMap<SMStateNode, ArrayList<SMTransitionLink>> nodeLinksEnd = new HashMap<>();
    public HashMap<SMStateNode, SMTransitionLink> circularLink = new LinkedHashMap<>();
    public HashMap<SMTransitionLink, ArrayList<SMTransitionLink>> eventLinksStart = new HashMap<>();
    public HashMap<SMTransitionLink, ArrayList<SMTransitionLink>> eventLinksEnd = new HashMap<>();
    public double lineX, lineY;
    public SMStateNode initialNode;
    public SMTransitionLink initialLink;
    ArrayList<SMModelListener> subscribers = new ArrayList<>();

    public SMModel() {}

    public void createNode(double left, double top) {
        SMStateNode node = new SMStateNode(left - 0.05, top-0.05, 0.25, 0.1);
        nodes.add(node);
        notifySubscribers();
    }

    public boolean checkHit(double x, double y) {
        return nodes.stream()
                .anyMatch(b -> b.checkHit(x,y));
    }

    public boolean checkEventBoxHit(double x, double y) {
        return links.stream().anyMatch(b -> b.checkHit(x,y));
    }

    public SMStateNode whichBox(double x, double y) {
        SMStateNode found = null;
        for (SMStateNode node : nodes) {
            if (node.checkHit(x,y)) {
                found = node;
            }
        }
        return found;
    }

    public SMTransitionLink whichEventBox(double x, double y) {
        SMTransitionLink found = null;
        for (SMTransitionLink link : links) {
            if (link.checkHit(x,y)) {
                found = link;
            }
        }
        return found;
    }

    public void moveBox(SMStateNode node, double dX, double dY) {
        node.move(dX,dY);
        if (circularLink.get(node) != null){
            circularLink.get(node).moveLineStart(dX, dY);
        }
        nodeLinksStart.forEach((startNode,nodelinks) -> {
            if (startNode == node){
                for (SMTransitionLink nodelink: nodelinks) {
                    nodelink.moveLineStart(dX, dY);
                }
            }
        });
        nodeLinksEnd.forEach((endNode,nodelinks) -> {
            if (endNode == node){
                for (SMTransitionLink nodelink: nodelinks) {
                    nodelink.moveLineEnd(dX, dY);
                }
            }
        });

        notifySubscribers();
    }

    public void moveEventBox(SMTransitionLink link, double dX, double dY) {
        link.move(dX,dY);
        eventLinksStart.forEach((startlink,nodelinks) -> {
            if (startlink == link){
                for (SMTransitionLink nodelink: nodelinks) {
                    nodelink.moveLineStart(dX, dY);
                }
            }
        });
        eventLinksEnd.forEach((endlink,nodelinks) -> {
            if (endlink == link){
                for (SMTransitionLink nodelink: nodelinks) {
                    nodelink.moveLineEnd(dX, dY);
                }
            }
        });
        notifySubscribers();
    }

    public void addSubscriber (SMModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }


    public void updateState(String state, SMStateNode selection) {
        selection.state = state;
        notifySubscribers();
    }

    int linkCount = 0;
    public SMTransitionLink createLink(double startX, double startY, double dx, double dy){
        SMTransitionLink link = new SMTransitionLink(lineX,lineY, dx, dy);
        SMStateNode circleBox = whichBox(lineX,lineY);
        if (circleBox == whichBox(dx,dy)){
            //Create Circle
            link = new SMTransitionLink(circleBox.left + circleBox.width + circleBox.width/3, circleBox.top,
                    0.3,0.25);
            if (startX == Double.MAX_VALUE){
                circularLink.put(circleBox,link);
            }
        }else {
            SMStateNode node = whichBox(dx,dy);
            links.add(link);
            notifySubscribers();
            if (startX == Double.MAX_VALUE){
                linkCount+=1;
                if (nodeLinksEnd.containsKey(node)){
                    ArrayList<SMTransitionLink> prevlinks = nodeLinksEnd.get(node);
                    prevlinks.add(links.get(links.size()-1));
                    nodeLinksEnd.put(node,prevlinks);
            }
                else {
                ArrayList<SMTransitionLink> templink = new ArrayList<>();
                templink.add(links.get(links.size()-1));
                nodeLinksEnd.put(node,templink);
                }
            if (nodeLinksStart.containsKey(initialNode)){
                ArrayList<SMTransitionLink> prevlinks = nodeLinksStart.get(initialNode);
                prevlinks.add(links.get(links.size()-1));
                nodeLinksStart.put(initialNode,prevlinks);
            }else {
                ArrayList<SMTransitionLink> templink = new ArrayList<>();
                templink.add(links.get(links.size()-1));
                nodeLinksStart.put(initialNode,templink);
            }
        }else {
            if (links.size() >= linkCount){
                links.remove(link);
            }
        }
        }
        return link;
    }

    public void updateLinkEvent(String event, String context, String effects, SMTransitionLink selectionLink) {
        selectionLink.event = event;
        selectionLink.context = context;
        selectionLink.sideEffects = effects;
        notifySubscribers();
    }

    public void deleteNode(SMStateNode selection) {
        nodes.remove(selection);
        if (links.size() > 0 && nodeLinksStart.get(selection) != null) {
            links.removeAll(nodeLinksStart.get(selection));
            linkCount = links.size();
        }

        if (links.size() > 0 && nodeLinksEnd.get(selection) != null) {
            links.removeAll(nodeLinksEnd.get(selection));
            linkCount = links.size();
        }

        nodeLinksStart.remove(selection);
        nodeLinksEnd.remove(selection);
        notifySubscribers();
    }

    SMStateNode tempCircularNode = null;
    public void deleteLink(SMTransitionLink selectionLink) {
        tempCircularNode = null;
        links.remove(selectionLink);
        circularLink.forEach(((node, link) -> {if (link == selectionLink) tempCircularNode = node;}));
        nodeLinksStart.forEach((node, nodelinks) -> nodelinks.remove(selectionLink));
        nodeLinksEnd.forEach((node, nodelinks) -> nodelinks.remove(selectionLink));
        eventLinksStart.remove(selectionLink);
        eventLinksEnd.remove(selectionLink);
        linkCount = links.size();
        circularLink.remove(tempCircularNode);
        notifySubscribers();

    }

    public Map.Entry<SMStateNode,SMTransitionLink> checkCircleEventBox(double normX, double normY) {

        for (Map.Entry<SMStateNode,SMTransitionLink> elements : circularLink.entrySet()){
            if (elements.getValue().checkCircleHit(normX,normY)) {
                return elements;
            }
        }
        return null;
    }

    public void pan(double dX, double dY) {

        for (SMTransitionLink link: links) {
            link.move(dX,dY);
        }

        eventLinksStart.forEach((startlink,nodelinks) -> {
            for (SMTransitionLink nodelink: nodelinks) {
                nodelink.moveLineStart(dX, dY);
            }
        });
        eventLinksEnd.forEach((endlink,nodelinks) -> {
            for (SMTransitionLink nodelink: nodelinks) {
                nodelink.moveLineEnd(dX, dY);
            }
        });

        nodeLinksStart.forEach((startlink,nodelinks) -> {
            for (SMTransitionLink nodelink: nodelinks) {
                nodelink.moveLineStart(dX, dY);
            }
        });
        nodeLinksEnd.forEach((endlink,nodelinks) -> {
            for (SMTransitionLink nodelink: nodelinks) {
                nodelink.moveLineEnd(dX, dY);
            }
        });

        for (SMStateNode node: nodes) node.move(dX,dY);

        circularLink.forEach((node, link) -> link.moveLineStart(dX,dY));

        notifySubscribers();


    }
}
