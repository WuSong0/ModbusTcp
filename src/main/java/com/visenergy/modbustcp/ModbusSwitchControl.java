package com.visenergy.modbustcp;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author WuSong
 * @Date 2017-08-22
 * @Time 13:54:24
 */
public class ModbusSwitchControl {
    private static SocketIOServer server =  ModbusReceiveAnalysis.server;
    private static Logger log = Logger.getLogger(ModbusSwitchControl.class);
    private static Properties properties = new Properties();
    static {
        InputStream is = ModbusSwitchControl.class.getClassLoader().getResourceAsStream("registerAddress.properties");
        try {
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
            properties.load(isr);
        } catch (IOException e) {
            log.debug("加载配置文件出错",e);
        }
    }

    /**
     * 九路开关控制
     */
    public static void SwitchCommand(){
       server.addEventListener("switch", String.class, new DataListener<String>() {
           public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
               if (s.equals("FZ1_ON")){
                   String address = properties.getProperty("一路负载投入");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ1_OFF")) {
                   String address = properties.getProperty("一路负载切除");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ2_ON")) {
                   String address = properties.getProperty("二路负载投入");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ2_OFF")) {
                   String address = properties.getProperty("二路负载切除");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ3_ON")) {
                   String address = properties.getProperty("三路负载投入");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ3_OFF")) {
                   String address = properties.getProperty("三路负载切除");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ4_ON")) {
                   String address = properties.getProperty("四路负载投入");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ4_OFF")) {
                   String address = properties.getProperty("四路负载切除");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ5_ON")) {
                   String address = properties.getProperty("五路负载投入");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ5_OFF")) {
                   String address = properties.getProperty("五路负载切除");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ6_ON")) {
                   String address = properties.getProperty("六路负载投入");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("FZ6_OFF")) {
                   String address = properties.getProperty("六路负载切除");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("SD_ON")) {
                   String address = properties.getProperty("市电投入");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("SD_OFF")) {
                   String address = properties.getProperty("市电切除");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("GF_ON")) {
                   String address = properties.getProperty("光伏投入");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("GF_OFF")) {
                   String address = properties.getProperty("光伏切除");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("CN_ON")) {
                   String address = properties.getProperty("储能投入");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else if (s.equals("CN_OFF")) {
                   String address = properties.getProperty("储能切除");
                   SocketClient.os.write(Utils.hexStringToBytes(GenerateCommand(address)));
               } else {
                   server.getBroadcastOperations().sendEvent("error","开关参数异常！");
                   throw new Exception("开关参数异常！");
               }
           }
       });
    }

    /**
     * 开关控制命令公共部分处理
     * @param val2 寄存器地址
     * @return
     */
    public static String GenerateCommand(String val2){
        int val = Integer.parseInt(val2);
        String address = Integer.toHexString(val).length() == 1 ? "0".concat(Integer.toHexString(val)) : Integer.toHexString(val);
        StringBuffer sbr = new StringBuffer(ModbusSendGenerate.TcpHead);
        sbr.append("01");
        sbr.append("06");
        sbr.append("00");
        sbr.append(address);
        sbr.append("00");
        sbr.append("01");
        return sbr.toString();
    }

}
