package com.kunlunsoft.sms.util;

import com.common.bean.BaseResponseDto;
import com.common.bean.exception.LogicBusinessException;
import com.file.hw.props.GenericReadPropsUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.io.hw.json.HWJacksonUtils;
import com.kunlunsoft.sms.dto.SmsParam;
import org.apache.log4j.Logger;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/***
 * 腾讯云,发送短信<br />
 * 配置:https://console.cloud.tencent.com/sms/smsSign/1400091325/0/10<br />
 *
 */
public class SmsSendUtil {
    public static Logger logger = Logger.getLogger(SmsSendUtil.class);

    public static BaseResponseDto sendSimpleSms(SmsParam smsParam, boolean real) {
        System.out.println(" :" + HWJacksonUtils.getJsonP(smsParam));
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        if (real) {
            try {
                SmsSingleSender ssender = new SmsSingleSender(smsParam.getAppid(), smsParam.getAppkey());
                String smsSign = "143491";
                ArrayList<String> params = new ArrayList<>();
                params.add(" \"##[ " + smsParam.getMsg() + " ]##\" ");
                SmsSingleSenderResult result = ssender.sendWithParam("86", smsParam.getMobile(),
                        119232, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
//            SmsSingleSenderResult result = ssender.send(0, "86", "18701670186",
//                    "【腾讯云】您的验证码是: 这是测试", "", "");
                System.out.print(result);
                if (result.result == 0 && result.errMsg.equals("OK")) {
                    baseResponseDto.setResult(true).setValue(result);
                } else {
                    baseResponseDto.setErrorMessage(result.errMsg);
                }
                return baseResponseDto;
//                return HWJacksonUtils.getJsonP(result);
            } catch (HTTPException e) {
                // HTTP响应码错误
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            } catch (JSONException e) {
                // json解析错误
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            } catch (IOException e) {
                // 网络IO错误
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
            return baseResponseDto.setErrorMessage("fail");
        } else {
            return baseResponseDto.setResult(true).setHint("没有真正发送短信");
        }

    }

    /***
     * 使用腾讯云的短信服务
     * @return
     */
    public static SmsParam getSmsConf() {
        String configFile = "config/qq.sms.properties";
        Properties properties = null;
        try {
            properties = GenericReadPropsUtil.getProperties(true, configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == properties) {
            String errorMessage = "读取配置文件失败:" + configFile;
            System.out.println("errorMessage :" + errorMessage);
            LogicBusinessException.throwException("1001", errorMessage);
        }
        SmsParam smsParam = new SmsParam();

        int appid = Integer.parseInt(properties.getProperty("appid"));
        String appkey = properties.getProperty("appkey");
        smsParam.setAppid(appid)
                .setAppkey(appkey);
        return smsParam;
    }

}
