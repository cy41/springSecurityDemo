# springSecurityDemo

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
  "code": 1,
  "message": "success",
  "token": "asdfasdfwerwerwe3094"
}

{
  "code": -1,
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
  "code": 1,
  "message": "success",
  "verify_code": 1234
}

```

securityConfig -> authManager
filter -> authManager
securityConfig -> filterConfig
filterConfig -> filter



# 修改信息

## 修改密码

新密码 与 验证码

pwd/sendPhone 获取验证码



pwd/setPwd 修改

```json
request
{
	"phone": "121231232",
	"new_pwd": "adfasdf",
	"verify_code": "1234"
}

response
{
	"code": 1/-1,
	"message": ""
}
```



# 修改手机号



# TODO

1. 验证码生成，短信发码
2. user/userDetails 一体 或 包含
3. 类名修改
4. jwt success response