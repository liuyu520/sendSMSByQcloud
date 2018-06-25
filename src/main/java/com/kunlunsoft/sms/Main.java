package com.kunlunsoft.sms;

import com.kunlunsoft.sms.dto.SmsParam;
import com.kunlunsoft.sms.util.SmsSendUtil;

/***
 * z主程序
 */
public class Main {
    public static void main(String[] args) {
        String mobile = args[0];
        String message = args[1];
        SmsParam smsParam = SmsSendUtil.getSmsConf();
        smsParam.setMobile(mobile)
                .setMsg(message);
        SmsSendUtil.sendSimpleSms(smsParam, true).toJson();
    }
}
