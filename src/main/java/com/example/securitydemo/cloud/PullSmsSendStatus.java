package com.example.securitydemo.cloud;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
// 导入 SMS 模块的 client
import com.tencentcloudapi.sms.v20190711.SmsClient;
// 导入要请求接口对应的 request response 类
import com.tencentcloudapi.sms.v20190711.models.PullSmsSendStatusRequest;
import com.tencentcloudapi.sms.v20190711.models.PullSmsSendStatusResponse;
/**
 * Tencent Cloud Sms PullSmsSendStatus
 * https://cloud.tencent.com/document/product/382/38774
 *
 */
public class PullSmsSendStatus {
    public static void main(String[] args) {
        try {
            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
             * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值
             * 您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人
             * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
             */
            Credential cred = new Credential(ColudConsts.secretId, ColudConsts.secretKey);

            /* 实例化 SMS 的 client 对象
             * 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 */
            SmsClient client = new SmsClient(cred, "");
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 您可以直接查询 SDK 源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用 IDE 进行开发，可以方便地跳转查阅各个接口和数据结构的文档说明 */
            PullSmsSendStatusRequest req = new PullSmsSendStatusRequest();
            /* 填充请求参数，这里 request 对象的成员变量即对应接口的入参
             * 您可以通过官网接口文档或跳转到 request 对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 短信控制台：https://console.cloud.tencent.com/smsv2
             * sms helper：https://cloud.tencent.com/document/product/382/3773 */
            /* 短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 */

            req.setSmsSdkAppid(ColudConsts.appId);
            // 设置拉取最大条数，最多100条
            Long limit = 5L;
            req.setLimit(limit);
            /* 通过 client 对象调用 PullSmsSendStatus 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 PullSmsSendStatusResponse 类的实例，与请求对象对应 */
            PullSmsSendStatusResponse res = client.PullSmsSendStatus(req);
            // 输出 JSON 格式的字符串回包
            System.out.println(PullSmsSendStatusResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}