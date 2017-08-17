import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sasha on 17.08.17.
 */
@Entity
@Table(name = "users")
public class LabUser implements Serializable {
    @Id
    @Column(name="id")
    private long id;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    public LabUser(){}

    public LabUser(String login,String password){
        this.login=login;
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes(Charset.forName("UTF8")));
            byte[] resultByte=messageDigest.digest();
            this.password= new String(Hex.encodeHex(resultByte));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
