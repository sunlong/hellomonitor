package com.github.sunlong.hellomonitor.common;

import com.github.sunlong.hellomonitor.monitor.model.DeviceProperty;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 下午4:45
 */

public class SnmpUtil {
    private SnmpUtil(){}

    public static SnmpUtil getInstance(){
        return SnmpUtilHolder.INSTANCE;
    }

    private static class SnmpUtilHolder{
        private static final SnmpUtil INSTANCE = new SnmpUtil();
    }

    private static ConcurrentHashMap<String, Snmp> snmpMap = new ConcurrentHashMap<String, Snmp>();
    private static ConcurrentHashMap<String, CommunityTarget> targetMap = new ConcurrentHashMap<String, CommunityTarget>();

    public void send(String ip, String oid) throws IOException {

        // 创建 PDU
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));

        // MIB的访问方式
        pdu.setType(PDU.GET);

        // 向Agent发送PDU，并接收Response
        ResponseEvent responseEvent = snmpMap.get(ip).send(pdu, targetMap.get(ip));
        // 解析Response
        if (responseEvent != null && responseEvent.getResponse() != null) {
            Vector<? extends VariableBinding> recVBs = responseEvent.getResponse().getVariableBindings();
            for (int i = 0; i < recVBs.size(); i++) {
                VariableBinding recVB = recVBs.elementAt(i);
                System.out.println(recVB.getOid() + " : " + recVB.getVariable());
            }
        }
    }

    public void init(DeviceProperty deviceProperty) throws IOException {
        String ip = deviceProperty.getIp();
        Snmp snmp = snmpMap.get(ip);
        if(snmp == null){
            Address targetAddress = GenericAddress.parse("udp:"+ ip +"/161");
            TransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();
            snmpMap.put(ip, snmp);

            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(deviceProperty.getSnmpCommunity()));
            target.setAddress(targetAddress);
            target.setRetries(deviceProperty.getSnmpTries());
            target.setTimeout(deviceProperty.getSnmpTimeout());
            target.setVersion(deviceProperty.getSnmpIntVersion());
            targetMap.putIfAbsent(ip, target);
        }
    }
}
