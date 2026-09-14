package com.saas.module.auth.service.impl;

import com.saas.module.auth.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 验证码服务实现类（无Redis版本）
 *
 * @author saas
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    // 使用内存缓存替代 Redis
    private final Map<String, CaptchaData> captchaCache = new ConcurrentHashMap<>();

    private static final String CAPTCHA_PREFIX = "saas:captcha:";
    private static final int CAPTCHA_LENGTH = 4;
    private static final int CAPTCHA_WIDTH = 120;
    private static final int CAPTCHA_HEIGHT = 40;
    private static final long CAPTCHA_EXPIRE_MILLIS = 5 * 60 * 1000; // 5分钟

    @Override
    public Map<String, String> generateCaptcha() {
        Map<String, String> result = new HashMap<>();

        // 生成验证码Key
        String key = UUID.randomUUID().toString().replace("-", "");

        // 生成随机验证码
        String code = generateCode();

        // 生成验证码图片
        String base64Image = generateImage(code);

        // 存储到内存
        captchaCache.put(key, new CaptchaData(code, System.currentTimeMillis()));

        result.put("key", key);
        result.put("image", base64Image);

        return result;
    }

    @Override
    public boolean validateCaptcha(String key, String code) {
        if (key == null || code == null) {
            return false;
        }

        CaptchaData cached = captchaCache.get(key);
        if (cached == null) {
            return false;
        }

        // 检查是否过期
        if (System.currentTimeMillis() - cached.createTime > CAPTCHA_EXPIRE_MILLIS) {
            captchaCache.remove(key);
            return false;
        }

        // 验证后删除验证码
        captchaCache.remove(key);

        // 忽略大小写比较
        return cached.code.equalsIgnoreCase(code);
    }

    @Override
    public void removeCaptcha(String key) {
        if (key != null) {
            captchaCache.remove(key);
        }
    }

    /**
     * 验证码数据
     */
    private static class CaptchaData {
        String code;
        long createTime;

        CaptchaData(String code, long createTime) {
            this.code = code;
            this.createTime = createTime;
        }
    }

    /**
     * 生成随机验证码
     */
    private String generateCode() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            int index = (int) (Math.random() * chars.length());
            code.append(chars.charAt(index));
        }
        return code.toString();
    }

    /**
     * 生成验证码图片
     */
    private String generateImage(String code) {
        BufferedImage image = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);

        // 设置字体
        g.setFont(new Font("Arial", Font.BOLD, 24));

        // 绘制验证码
        int x = 10;
        for (int i = 0; i < code.length(); i++) {
            g.setColor(getRandomColor());
            g.drawString(String.valueOf(code.charAt(i)), x, 28);
            x += 25;
        }

        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(getRandomColor());
            g.drawLine(
                    (int) (Math.random() * CAPTCHA_WIDTH),
                    (int) (Math.random() * CAPTCHA_HEIGHT),
                    (int) (Math.random() * CAPTCHA_WIDTH),
                    (int) (Math.random() * CAPTCHA_HEIGHT)
            );
        }

        g.dispose();

        // 转换为Base64
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            byte[] bytes = baos.toByteArray();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            log.error("生成验证码图片失败", e);
            return "";
        }
    }

    /**
     * 获取随机颜色
     */
    private Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }
}
