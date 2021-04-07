package cag;

import java.io.IOException;

import org.msgpack.core.MessagePack.UnpackerConfig;

import cag.packets.bidi.SocialDm;
import cag.packets.server.SocialOnline;

import org.msgpack.core.MessageUnpacker;

public class BasePacket {
    private Integer id;
    private String command;
    private Object data;

    public BasePacket(byte[] packet) throws Exception {
        MessageUnpacker unpacker = new UnpackerConfig().newUnpacker(packet);
        int mapHeader = unpacker.unpackMapHeader();
        for (int i = 0; i < mapHeader; i++) {
            String key = unpacker.unpackString();
            switch (key) {
            case "id":
                id = unpacker.unpackInt();
                break;
            case "command":
                command = unpacker.unpackString();
                break;
            case "data":
                data = unpackData(unpacker);
                break;
            default:
                System.out.println("Unknown map key " + key);
                System.out.println("Next format: " + unpacker.getNextFormat());
                System.out.println("Next value: " + unpacker.unpackValue());
                throw new Exception("asd");
            }
        }
        unpacker.close();
    }

    private Object unpackData(MessageUnpacker unpacker) throws IOException {
        switch (command) {
        case "social.dm":
            SocialDm dm = new SocialDm(unpacker);
            return dm;
        case "social.online":
            return new SocialOnline(unpacker);
        default:
            System.out.println("Unknown command " + command);
            return null;
        }
    }

}
