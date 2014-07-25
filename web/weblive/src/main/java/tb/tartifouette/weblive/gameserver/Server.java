package tb.tartifouette.weblive.gameserver;

import java.io.Serializable;

public class Server implements Serializable {

    /** The server name. */
    private String name;

    /** The server IPv4 address. */
    private String ip;

    /** The server port (usually 27960). */
    private String port;

    /** The server login method (ref, mod, rcon). */
    private String loginType;

    private String password;


    /**
     * Constructs a server object given the following parameters:
     *
     * @param name The server name.
     * @param ip The server IPv4 address.
     * @param port The server port, usually just 27960.
     * @param loginType The server login type which is either ref, mod, or rcon.
     * @param password The server password un-encrypted.
     */
    public Server(String name, String ip, String port, String loginType, String password) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.loginType = loginType;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getIP() {
        return this.ip;
    }

    public String getPortAsString() {
        return this.port;
    }

    public int getPortAsInteger() {
        return Integer.parseInt(this.port);
    }

    public String getLoginType() {
        return this.loginType;
    }

    public static String reverseString(String str) {
        StringBuffer buffer = new StringBuffer(str);
        buffer = buffer.reverse();
        return buffer.toString();
    }

    public String getPassword() {
        return password;
    }

    public String[] toArray() {
        String[] server = { this.name, this.ip, this.port, this.loginType, this.password };
        return server;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
