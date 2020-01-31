package store.awesm.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 *
 * @author zhishangli
 * @time 21:27 2020-01-23
 */
@Component
public class EmailUtils {

    private static String fromEmail;
    private static String authCode;

    private EmailUtils() {}

    public static boolean sendEmail(String emailaddress, String code){
        try {
            SimpleEmail email = new SimpleEmail();//不用更改
            email.setHostName("smtp.126.com");//需要修改，126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com
            email.setCharset("UTF-8");
            email.addTo(emailaddress);// 收件地址

            email.setFrom(fromEmail, "Awesm");//此处填邮箱地址和用户名,用户名可以任意填写

            email.setAuthentication(fromEmail, authCode);//此处填写邮箱地址和客户端授权码

            email.setSubject("Awesm注册");//此处填写邮件名，邮件名可任意填写
            email.setMsg("尊敬的用户您好,您的验证码是:" + code);//此处填写邮件内容

            try {
                email.send();
            } catch (EmailException e) {
                return false;
            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static String getRandomCode(int number){
        StringBuilder codeNum = new StringBuilder();
        int [] code = new int[3];
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int num = random.nextInt(10) + 48;
            int uppercase = random.nextInt(26) + 65;
            int lowercase = random.nextInt(26) + 97;
            code[0] = num;
            code[1] = uppercase;
            code[2] = lowercase;
            codeNum.append((char) code[random.nextInt(3)]);
        }
        return codeNum.toString();
    }

    @Value("${email.from}")
    public void setFromEmail(String fromEmail) {
        EmailUtils.fromEmail = fromEmail;
    }

    @Value("${email.auth-code}")
    public void setAuthCode(String authCode) {
        EmailUtils.authCode = authCode;
    }
}
