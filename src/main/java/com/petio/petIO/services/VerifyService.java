package com.petio.petIO.services;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;


@Service
public class VerifyService {
	@Value("${mail.address}")
	public static String mailAddress;
	
	@Value("${mail.host}")
	public static String mailHost;
	
	@Value("${mail.tp}")
	public static String mailTP;
	
	@Value("${mail.password}")
	public static String mailPassword;
	
	public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	private static Random random = new Random();
	
	public String sendMail(String receiver,String subject,String content) {
		if (!valid(receiver)) {
			return "mail address not exist";
		}
		System.out.println("receiver:"+receiver);
		Properties prop = new Properties();
        prop.setProperty("mail.host", mailHost);
		prop.setProperty("mail.transport.protocol", mailTP);
		prop.setProperty("mail.smtp.auth", "true");
		//使用JavaMail发送邮件的5个步骤
		//1、创建session
		Session session = Session.getInstance(prop);
		//开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(true);
		//2、通过session得到transport对象
		Transport ts;
		
		try {
			ts = session.getTransport();
			//3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
			
			ts.connect(mailHost, mailAddress, mailPassword);
			String contentString = "本次操作验证码为： "+"<b>"+ generateVerifyCode(6)+"</b>";
			Message message = createSimpleMail(session,receiver,subject,content);
//			//5、发送邮件
		    ts.sendMessage(message, message.getAllRecipients());
		    ts.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "error occur";
		} 
		return UUID.randomUUID().toString();
	}
	
	public boolean valid(String toMail) {
		String domain = "jootmir.org";
        if(StringUtils.isBlank(toMail) || StringUtils.isBlank(domain)) return false;
        if(!StringUtils.contains(toMail, '@')) return false;
        String host = toMail.substring(toMail.indexOf('@') + 1);
        if(host.equals(domain)) return false;
        Socket socket = new Socket();
        try {
            // 查找mx记录
            Record[] mxRecords = new Lookup(host, Type.MX).run();
            if(ArrayUtils.isEmpty(mxRecords)) return false;
            // 邮件服务器地址
            String mxHost = ((MXRecord)mxRecords[0]).getTarget().toString();
            if(mxRecords.length > 1) { // 优先级排序
                List<Record> arrRecords = new ArrayList<Record>();
                Collections.addAll(arrRecords, mxRecords);
                Collections.sort(arrRecords, new Comparator<Record>() {
                    
                    public int compare(Record o1, Record o2) {
                        return new CompareToBuilder().append(((MXRecord)o1).getPriority(), ((MXRecord)o2).getPriority()).toComparison();
                    }
                    
                });
                mxHost = ((MXRecord)arrRecords.get(0)).getTarget().toString();
            }
            // 开始smtp
            socket.connect(new InetSocketAddress(mxHost, 25));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 超时时间(毫秒)
            long timeout = 6000;
            // 睡眠时间片段(50毫秒)
            int sleepSect = 50;
            
            // 连接(服务器是否就绪)
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 220) {
                return false;
            }
            
            // 握手
            bufferedWriter.write("HELO " + domain + "\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 身份
            bufferedWriter.write("MAIL FROM: <check@" + domain + ">\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 验证
            bufferedWriter.write("RCPT TO: <" + toMail + ">\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 断开
            bufferedWriter.write("QUIT\r\n");
            bufferedWriter.flush();
            return true;
        } catch (NumberFormatException e) {
        } catch (TextParseException e) {
        } catch (IOException e) {
        } catch (InterruptedException e) {
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        return false;
    }
    
    private int getResponseCode(long timeout, int sleepSect, BufferedReader bufferedReader) throws InterruptedException, NumberFormatException, IOException {
        int code = 0;
        for(long i = sleepSect; i < timeout; i += sleepSect) {
            Thread.sleep(sleepSect);
            if(bufferedReader.ready()) {
                String outline = bufferedReader.readLine();
                // FIXME 读完……
                while(bufferedReader.ready())
                    /*System.out.println(*/bufferedReader.readLine()/*)*/;
                /*System.out.println(outline);*/
                code = Integer.parseInt(outline.substring(0, 3));
                break;
            }
        }
        return code;
    }
	public  MimeMessage createSimpleMail(Session session,String receiver,String subject,String content) throws MessagingException
		        {
		    //创建邮件对象
		MimeMessage message = new MimeMessage(session);
		//指明邮件的发件人

			try {
				message.setFrom(new InternetAddress(mailAddress, "PetIO网站管理员", "UTF-8"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			//邮件的标题
			message.setSubject(subject);
			//邮件的文本内容
			message.setContent(content , "text/html;charset=UTF-8");

			//"本次操作验证码为： "+"<b>"+ generateVerifyCode(6)+"</b>"
		//返回创建好的邮件对象
		    return message;
		}
	 
	/**
	 * 使用系统默认字符源生成验证码
	 * @param verifySize    验证码长度
	 * @return
	 */
	public String generateVerifyCode(int verifySize){
	    return generateVerifyCode(verifySize, VERIFY_CODES);
	}
	/**
	 * 使用指定源生成验证码
	 * @param verifySize    验证码长度
	 * @param sources   验证码字符源
	 * @return
	 */
	public String generateVerifyCode(int verifySize, String sources){
	    if(sources == null || sources.length() == 0){
	        sources = VERIFY_CODES;
	    }
	    int codesLen = sources.length();
	    Random rand = new Random(System.currentTimeMillis());
	    StringBuilder verifyCode = new StringBuilder(verifySize);
	    for(int i = 0; i < verifySize; i++){
	        verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));
	    }
	    return verifyCode.toString();
	}
	 
	/**
	 * 生成随机验证码文件,并返回验证码值
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param verifySize
	 * @return
	 * @throws IOException
	 */
	public String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException{
	    String verifyCode = generateVerifyCode(verifySize);
	    outputImage(w, h, outputFile, verifyCode);
	    return verifyCode;
	}
	 
	/**
	 * 输出随机验证码图片流,并返回验证码值
	 * @param w
	 * @param h
	 * @param os
	 * @param verifySize
	 * @return
	 * @throws IOException
	 */
	public String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException{
	    String verifyCode = generateVerifyCode(verifySize);
	    outputImage(w, h, os, verifyCode);
	    return verifyCode;
	}
	 
	/**
	 * 生成指定验证码图像文件
	 * @param w 
	 * @param h
	 * @param outputFile
	 * @param code
	 * @throws IOException
	 */
	public void outputImage(int w, int h, File outputFile, String code) throws IOException{
	    if(outputFile == null){
	        return;
	    }
	    File dir = outputFile.getParentFile();
	    if(!dir.exists()){
	        dir.mkdirs();
	    }
	    try{
	        outputFile.createNewFile();
	        FileOutputStream fos = new FileOutputStream(outputFile);
	        outputImage(w, h, fos, code);
	        fos.close();
	    } catch(IOException e){
	        throw e;
	    }
	}
	 
	/**
	 * 输出指定验证码图片流
	 * @param w
	 * @param h
	 * @param os
	 * @param code
	 * @throws IOException
	 */
	public void outputImage(int w, int h, OutputStream os, String code) throws IOException{
	    int verifySize = code.length();
	    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Random rand = new Random();
	    Graphics2D g2 = image.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	    Color[] colors = new Color[5];
	    Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN,
	            Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
	            Color.PINK, Color.YELLOW };
	    float[] fractions = new float[colors.length];
	    for(int i = 0; i < colors.length; i++){
	        colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
	        fractions[i] = rand.nextFloat();
	    }
	    Arrays.sort(fractions);
	     
	    g2.setColor(Color.GRAY);// 设置边框色
		g2.fillRect(0, 0, w, h);
		 
		Color c = getRandColor(200, 250);
		g2.setColor(c);// 设置背景色
		g2.fillRect(0, 2, w, h-4);
		 
		//绘制干扰线
		Random random = new Random();
		g2.setColor(getRandColor(160, 200));// 设置线条的颜色
		for (int i = 0; i < 20; i++) {
		    int x = random.nextInt(w - 1);
		    int y = random.nextInt(h - 1);
		    int xl = random.nextInt(6) + 1;
		    int yl = random.nextInt(12) + 1;
		    g2.drawLine(x, y, x + xl + 40, y + yl + 20);
		}
		 
		// 添加噪点
		float yawpRate = 0.05f;// 噪声率
		int area = (int) (yawpRate * w * h);
		for (int i = 0; i < area; i++) {
		    int x = random.nextInt(w);
		    int y = random.nextInt(h);
		    int rgb = getRandomIntColor();
		    image.setRGB(x, y, rgb);
		}
		 
		shear(g2, w, h, c);// 使图片扭曲
		 
		        g2.setColor(getRandColor(100, 160));
		        int fontSize = h-4;
		        Font font = new Font("Arial", Font.ITALIC, fontSize);
		g2.setFont(font);
		char[] chars = code.toCharArray();
		for(int i = 0; i < verifySize; i++){
		    AffineTransform affine = new AffineTransform();
		    affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize/2, h/2);
		    g2.setTransform(affine);
		    g2.drawChars(chars, i, 1, ((w-10) / verifySize) * i + 5, h/2 + fontSize/2 - 10);
		}
		 
		g2.dispose();
		ImageIO.write(image, "jpg", os);
	}
     
	private Color getRandColor(int fc, int bc) {
	    if (fc > 255)
	        fc = 255;
	    if (bc > 255)
	        bc = 255;
	    int r = fc + random.nextInt(bc - fc);
	    int g = fc + random.nextInt(bc - fc);
	    int b = fc + random.nextInt(bc - fc);
	    return new Color(r, g, b);
	}
     
    private  int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }
     
    private  int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }
 
    private  void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }
     
    private  void shearX(Graphics g, int w1, int h1, Color color) {
 
		int period = random.nextInt(2);
 
		boolean borderGap = true;
		int frames = 1;
        int phase = random.nextInt(2);
 
        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
 
    }
 
    private  void shearY(Graphics g, int w1, int h1, Color color) {
 
        int period = random.nextInt(40) + 10; // 50;
 
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
 
        }
 
    }
}
