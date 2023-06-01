package com.example.ubergo.config;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {
    @Value("${{spring.alipay.app-id}}")
    private  String appId;
    @Value("${{spring.alipay.merchant-private-key}}")
    private  String merchantPrivateKey;
    @Value("${{spring.alipay.merchant-public-key}}")
    private  String merchantPublicKey;
    @Value("${{spring.alipay.encrypt-key}}")
    private  String encryptKey;
    @Bean
    public  Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";
        config.appId = this.appId;
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = this.merchantPrivateKey;

         config.alipayPublicKey = this.merchantPublicKey;
        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        config.encryptKey = encryptKey;
        Factory.setOptions(getOptions());
        return config;
    }
}
