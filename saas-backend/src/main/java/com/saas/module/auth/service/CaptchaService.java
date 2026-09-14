package com.saas.module.auth.service;

import java.util.Map;

/**
 * 验证码服务接口
 *
 * @author saas
 */
public interface CaptchaService {

    /**
     * 生成验证码
     *
     * @return 验证码Key和Base64图片
     */
    Map<String, String> generateCaptcha();

    /**
     * 验证验证码
     *
     * @param key  验证码Key
     * @param code 用户输入的验证码
     * @return 是否正确
     */
    boolean validateCaptcha(String key, String code);

    /**
     * 删除验证码
     *
     * @param key 验证码Key
     */
    void removeCaptcha(String key);
}
