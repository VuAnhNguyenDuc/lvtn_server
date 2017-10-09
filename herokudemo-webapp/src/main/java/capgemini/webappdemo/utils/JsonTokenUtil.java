package capgemini.webappdemo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.security.Key;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class JsonTokenUtil {
    private static final Key key = MacProvider.generateKey();

    static final private Base64 base64 = new Base64();

    private String serializeObjectToString(TokenPayload object) throws IOException {
        try (
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(arrayOutputStream);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);) {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            gzipOutputStream.close();
            return new String(base64.encode(arrayOutputStream.toByteArray()));
        }
    }

    private TokenPayload deserializeObjectFromString(String objectString) throws IOException, ClassNotFoundException {
        try (
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(base64.decode(objectString));
                GZIPInputStream gzipInputStream = new GZIPInputStream(arrayInputStream);
                ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream)) {
            return (TokenPayload) objectInputStream.readObject();
        }
    }

    public String createJWT(String subject){
        String compactJws = Jwts.builder().setSubject(subject).signWith(SignatureAlgorithm.HS256,key).compact();
        return compactJws;
    }

    public boolean validateKey(String jsonKey){
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(jsonKey);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

    public TokenPayload parsePayload(String payload){
        try {

            return deserializeObjectFromString(payload);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String createPayload(TokenPayload tokenPayload){
        try {
            return serializeObjectToString(tokenPayload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPayloadFromKey(String key){
        DecodedJWT jwt = null;
        try {
            jwt = JWT.decode(key);
        } catch (JWTDecodeException e) {
            return e.getMessage();
        }
        return jwt.getSubject();
    }

    public static void main(String[] args){
        JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

        TokenPayload subject = new TokenPayload(1,"anthony","zen","emp");

        String payload = jsonTokenUtil.createPayload(subject);

        String jsonKey = jsonTokenUtil.createJWT(payload);

        String decoded = jsonTokenUtil.getPayloadFromKey(jsonKey);

        TokenPayload temp = jsonTokenUtil.parsePayload(decoded);

        System.out.println(jsonKey);
    }
}
