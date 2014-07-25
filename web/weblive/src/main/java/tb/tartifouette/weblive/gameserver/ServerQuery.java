package tb.tartifouette.weblive.gameserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copied from https://code.google.com/p/1upmodrcon/source/browse/trunk/1upModRcon/src/modrcon/BowserQuery.java
 *
 */
public class ServerQuery {

    /** The password for use with rcon/mod/ref. */
    private String password;

    /** The out-of-bound data to send prior to commands. */
    private byte oob;

    /** The Server Port. */
    private int port;

    /** The Client Socket Pointer. */
    private DatagramSocket ds;

    /** The UDP Datagram Packet. */
    private DatagramPacket dp;

    /** The Internet Address Object. */
    private InetAddress ia;

    /** Enable/Disable Raw Server Output */
    private boolean rawOutput;

    /** The login method to the server (ref, mod, rcon). */
    private String method;

    /** The last command sent. */
    private String lastCommand;

    /** Buffer used to store output from the server. */
    private String output;

    public ServerQuery(Server s) throws Exception {
        this.oob = (byte)0xff;
        this.port = s.getPortAsInteger();
        this.method = s.getLoginType();
        this.password = s.getPassword();
        this.ds = new DatagramSocket();
        //this.ds.setSoTimeout(2000); // not sure if this has any effect here.
        this.ia = InetAddress.getByName(s.getIP());
    }

    public void setRawOutput(boolean flag) {
        this.rawOutput = flag;
    }

    public boolean getRawOutput() {
        return this.rawOutput;
    }

    /**
     * Sends an authenticated command to the server.
     *
     * @param cmd The command to send.
     */
    public void sendCmd(String cmd) {
        this.lastCommand = cmd;
        if (this.method.equals("ref")) {
            this.ref(cmd);
        } else if (this.method.equals("mod")) {
            this.mod(cmd);
        } else {
            this.rcon(cmd);
        }
    }

    public void forceTeam(String player, String team) {
        this.sendCmd("forceteam " + player + " " + team);
    }

    private void rcon(String command) {
        this.send("rcon " + this.password + " " + command);
    }

    private void mod(String command) {
        this.send("mod " + this.password + " " + command);
    }

    private void ref(String command) {
        this.send("ref " + this.password + " " + command);
    }

    public void say(String message) {
        this.sendCmd("say ^1" + message);
        String resp = this.getResponse();
        System.out.println("say : " + message);
        System.out.println("received answer : " + resp);
    }

    public void kick(int clientNumber, String message) {
        String command =
                "sendclientcommand " + clientNumber + " disconnect " + String.valueOf(34) + message
                        + String.valueOf(34);
        this.sendCmd(command);
    }

    public void crash(int clientNumber) {
        String command = "sendclientcommand " + clientNumber + " cs 400";
        this.sendCmd(command);
    }

    public void renamePlayer(int clientNumber, String newName) {
        String command =
                "forcecvar " + clientNumber + " name " + String.valueOf(34) + newName
                        + String.valueOf(34);
        this.sendCmd(command);
    }

    private void send(String data) {
        try {

            String out = "xxxx" + data;
            byte[] buff = out.getBytes();
            buff[0] = this.oob;
            buff[1] = this.oob;
            buff[2] = this.oob;
            buff[3] = this.oob;
            this.dp = new DatagramPacket(buff, buff.length, this.ia, this.port);
            this.ds.send(this.dp);
        } catch (Exception e) {
            System.out.println("Send method in BowserQuery Failed with: " + e.getMessage());
        }
    }

    public String getResponse() {
        DatagramPacket dpacket;
        byte[] buffer = new byte[2048];
        this.output = "";
        while (true) {
            try {
                dpacket = new DatagramPacket(buffer, buffer.length);
                // Decrease value speeds things up, increase slows things down.
                this.ds.setSoTimeout(100);
                this.ds.receive(dpacket);
                String packet = new String(dpacket.getData(), 0, dpacket.getLength());
                this.output += packet;
            } catch (IOException e) {
                if (this.rawOutput) {
                    return this.output;
                } else {
                    //todo: replace blank player name with "UnknownPlayer"
                    String purdy = this.output;
                    purdy = stripPrintCommands(purdy);
                    //purdy = stripColors(purdy);
                    return purdy;
                }
            }
        } // end while
    }

    /**
     * Remove print commands.
     *
     * @param input The input string buffer.
     * @return      The buffer without print commands.
     */
    private String stripPrintCommands(String input) {
        Pattern r = Pattern.compile("....print\n");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
    }

    private String stripColors(String input) {
        Pattern r = Pattern.compile("\\^.");
        Matcher m = r.matcher(input);
        return m.replaceAll("");
    }

    /**
     * Gets the current gear setting.
     *
     * @return The gear setting integer.
     */
    public int getGearSetting() {
        return getGearSetting(this.getstatus());
    }

    public int getGearSetting(String getStatusOutput) {
        String gear = "";
        String[] lines = getStatusOutput.split("\\\\");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals("g_gear")) {
                gear = lines[i + 1];
                break;
            }
        }
        try {
            return Integer.parseInt(gear);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getAllowVoteSetting() {
        return this.getAllowVoteSetting(this.getstatus());
    }

    public int getAllowVoteSetting(String getStatusOutput) {
        String vote = "";
        String[] lines = getStatusOutput.split("\\\\");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals("g_allowvote")) {
                vote = lines[i + 1];
                break;
            }
        }
        try {
            return Integer.parseInt(vote);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getstatus() {
        this.send("getstatus");
        String resp = this.getResponse();
        // Remove OOB command
        Pattern r = Pattern.compile("....statusResponse\n");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");
        return resp;
    }

    /**
     * Send Connection-less Packet Commands to the Server.
     *
     * @param cmd The connection-less packet command to send.
     */
    public void sendConnectionlessPacket(String cmd) {
        this.send(cmd);
    }

    public ArrayList getMapList() {
        ArrayList mapList = new ArrayList();
        this.sendCmd("dir maps .bsp");
        String resp = this.getResponse();
        // Remove .bsp extension from file names
        Pattern r = Pattern.compile("\\.bsp");
        Matcher m = r.matcher(resp);
        resp = m.replaceAll("");
        String[] lines = resp.split("\\n");
        if (lines.length > 1) {
            for (int i = 2; i < lines.length; i++) {
                mapList.add(lines[i]);
            }
        }
        return mapList;
    }

    public int getMaxClients(String getStatusOutput) {
        String maxClients = "";
        String[] lines = getStatusOutput.split("\\\\");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals("sv_maxclients")) {
                maxClients = lines[i + 1];
                break;
            }
        }
        try {
            return Integer.parseInt(maxClients);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getMaxClients() {
        return this.getMaxClients(this.getstatus());
    }

    /**
     * Gets the output of the getinfo command,
     * and parses it into a useful data structure.
     *
     * @return The output in a HashMap
     */
    public Map getServerInfo() {
        this.send("getinfo");
        String resp = this.getResponse();

        // what if the response is null or nothing?
        if (!resp.equals("")) {

            // Remove OOB command
            Pattern r = Pattern.compile("....infoResponse\n");
            Matcher m = r.matcher(resp);
            resp = m.replaceAll("");

            //System.out.println(resp);

            // Remove the first character
            resp = resp.substring(1);
            // Split String by Single Backslash
            String[] foo = resp.split("\\\\");

            // Now Separate Keys and Values
            ArrayList<String> keys = new ArrayList();
            ArrayList<String> vals = new ArrayList();
            boolean direction = true;
            for (String element : foo) {
                if (direction) {
                    keys.add(element);
                } else {
                    vals.add(element);
                }
                direction = (direction) ? false : true;
            }

            // Create Associative Array of Key/Value Pairs.
            Map<String, String> map = new HashMap();
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String val = vals.get(i);
                map.put(key, val);
            }
            return map;
        } else {
            // No Response From Server, return null.
            return null;
        }
    }

}
