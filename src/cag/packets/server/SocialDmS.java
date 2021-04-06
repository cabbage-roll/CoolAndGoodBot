package cag.packets.server;

import java.io.IOException;

import org.msgpack.core.MessageUnpacker;

import cag.Main;
import cag.packets.client.SocialDmC;
import cag.packets.client.SocialPresenceC;

public class SocialDmS {
    private String stream;
    private String content;
    private String content_safe;
    private String user;
    private String role;
    private boolean supporter;
    private int supporter_tier;
    private boolean verified;
    private boolean system;
    private String ts;
    private String _id;
    private static String helpString = "get real - go online\n" + "get fake - go offline\n"
            + "toString() - call toString method for sent message\n" + "help - this";

    public SocialDmS(MessageUnpacker unpacker) throws IOException {
        unpacker.unpackMapHeader();
        unpacker.unpackString();
        stream = unpacker.unpackString();
        unpacker.unpackString();
        unpacker.unpackMapHeader();
        unpacker.unpackString();
        content = unpacker.unpackString();
        unpacker.unpackString();
        content_safe = unpacker.unpackString();
        unpacker.unpackString();
        user = unpacker.unpackString();
        unpacker.unpackString();
        unpacker.unpackMapHeader();
        unpacker.unpackString();
        role = unpacker.unpackString();
        unpacker.unpackString();
        supporter = unpacker.unpackBoolean();
        unpacker.unpackString();
        supporter_tier = unpacker.unpackInt();
        unpacker.unpackString();
        verified = unpacker.unpackBoolean();
        unpacker.unpackString();
        system = unpacker.unpackBoolean();
        unpacker.unpackString();
        ts = unpacker.unpackString();
        unpacker.unpackString();
        _id = unpacker.unpackString();
    }

    @Override
    public String toString() {
        return "SocialDm [stream=" + stream + ", content=" + content + ", content_safe=" + content_safe + ", user="
                + user + ", role=" + role + ", supporter=" + supporter + ", supporter_tier=" + supporter_tier
                + ", verified=" + verified + ", system=" + system + ", ts=" + ts + ", _id=" + _id + "]";
    }

    public void processMessage() throws IOException {
        switch (content) {
        case "get real":
            Main.instance.sendPacket(new SocialPresenceC("online", "").makePacket());
            break;
        case "get fake":
            Main.instance.sendPacket(new SocialPresenceC("offline", null).makePacket());
            break;
        case "help":
            Main.instance.sendPacket(new SocialDmC(getSender(), helpString).makePacket());
            break;
        case "toString()":
            Main.instance.sendPacket(new SocialDmC(getSender(), toString()).makePacket());
            break;
        }
    }

    private String getSender() {
        return stream.split(":")[0];
    }

}
