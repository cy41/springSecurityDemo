# springtSecurityDemo

# 登陆方式

## phone and pwd

```json
pwd/login
{
	"phone": 123456789,
  "pwd": "123412342314"
}
```



## phone and sms

sms/login 携带手机号验证码登陆返回token

```json
request:
{
	"phone": 1234545565,
	"verify_code": 1234
}
response:
{
  "code": 1
  "message": "success"
  "token": "asdfasdfwerwerwe3094"
}

{
  "code": -1
  "message": "fail"
}
```



sms/sendPhone 获取验证码

```json
request:
{
  "phone": 123345455
}

response:
{
  "code": 1
  "message": "success"
  "verify_code": 1234
}

```

