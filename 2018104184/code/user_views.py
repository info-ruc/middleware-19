
#coding=utf-8
import random
import re
 
import logging
from flask import Blueprint, jsonify
from flask import current_app
from flask import make_response
from flask import request
from flask import session
from qiniu_sdk import put_qiniu
 
from captcha.captcha import captcha
from models import User
from status_code import RET, ret_map
from ytx_sdk.ytx_send import sendTemplateSMS
from my_decorators import is_login
 
user_blueprint=Blueprint('user',__name__)
 
 
#验证码
@user_blueprint.route('/yzm')
def yzm():
    name,text,image=captcha.generate_captcha()
    session['image_yzm']=text
    response=make_response(image)
    response.headers['Content-Type']='image/jpeg'
    return response
 
#短信发送设置
@user_blueprint.route('/send_sms')
def send_sms():
    #接收请求的数据
    dict=request.args
    mobile=dict.get('mobile')
    imageCode=dict.get('imageCode')
 
    #验证参数是否存在
    if not all([mobile,imageCode]):
        return jsonify(code=RET.PARAMERR,msg=ret_map[RET.PARAMERR])
    #验证手机号格式是否正确
    if not re.match(r'^1[345789]\d{9}$',mobile):
        return jsonify(code=RET.PARAMERR,msg=u'手机号格式不正确')
    #验证手机号是否存在
    if User.query.filter_by(phone=mobile).count():
        return jsonify(code=RET.PARAMERR,msg=u'手机号已存在')
    #验证图片验证码
    if imageCode!=session['image_yzm']:
        return jsonify(code=RET.PARAMERR,msg=u'图片验证码错误')
    #通过云通讯函数进行短信发送
    sms_code=random.randint(1000,9999)
    session['sms_yzm']=sms_code
    print(sms_code)
    result='000000'
    # result=sendTemplateSMS(mobile,[sms_code,'5'],1)
    #根据云通讯返回的结果进行相应
    if result=='000000':
        return jsonify(code=RET.OK,msg=ret_map[RET.OK])
    else:
        return jsonify(code=RET.UNKOWNERR,msg=u'短信发送失败')
 
#用户注册
@user_blueprint.route('/',methods=["POST"])
def user_register():
    #接收参数
    dict=request.form
    mobile=dict.get('mobile')
    imagecode=dict.get('imagecode')
    phonecode=dict.get('phonecode')
    password=dict.get('password')
    password2=dict.get('password2')
    #验证参数是否存在
    if not all([mobile,imagecode,phonecode,password,password2]):
        return jsonify(code=RET.PARAMERR,msg=ret_map[RET.PARAMERR])
    #验证图片验证码
    if imagecode!=session['image_yzm']:
        return jsonify(code=RET.PARAMERR,msg=u'图片验证码错误')
    #验证短信验证码
    if int(phonecode)!=session['sms_yzm']:
        return jsonify(code=RET.PARAMERR,msg=u'短信验证码错误')
    #验证手机号格式是否正确
    if not re.match(r'^1[345789]\d{9}$',mobile):
        return jsonify(code=RET.PARAMERR,msg=u'手机格式错误')
    #验证手机号是否存在
    if User.query.filter_by(phone=mobile).count():
        return jsonify(code=RET.PARAMERR,msg=u'手机号码存在')
    #保存用户对象
    user=User()
    user.phone=mobile
    user.name=mobile
    user.password=password
 
    try:
        user.add_update()
        return jsonify(code=RET.OK,msg=ret_map[RET.OK])
    except:
        logging.ERROR(u'用户注册更新数据库失败，手机号：%s,密码：%s'%(mobile,password))
        return jsonify(code=RET.DBERR,msg=ret_map[RET.DBERR])
 
@is_login
@user_blueprint.route('/',methods=['GET'])
def user_my():
    #获取当前登录的用户
    user_id=session['user_id']
    #查询当前用户的头像/用户名/手机号/并返回
    user=User.query.get(user_id)
    return jsonify(user=user.to_basic_dict())
 
@is_login
#个人信息获取用户名
@user_blueprint.route('/auth',methods=['GET'])
def user_auth():
    #获取当前登录用户的编号
    user_id=session['user_id']
    #根据编号查询当前用户
    user=User.query.get(user_id)
    #返回用户的真实姓名，身份证号
    return jsonify(user.to_auth_dict())
 
@is_login
#上传头像
@user_blueprint.route('/',methods=['PUT'])
def user_profile():
    dict=request.form
    if 'avatar1' in dict:
        try:
            #获取头像文件
            f1=request.files['avatar']
            # print(f1)
            # print(type(f1))
            # from werkzeug.datastructures import FileStorage
            #mime-type:国际规范，表示文件的类型，如text/html,text/xml,image/png,image/jpeg..
            if not re.match('image/.*',f1.mimetype):
                return jsonify(code=RET.PARAMERR)
        except:
            return jsonify(code=RET.PARAMERR)
        # 上传到七牛云
        # access_key = 'H999S3riCJGPiJOity1GsyWufw3IyoMB6goojo5e'
        # secret_key = 'uOZfRdFtljIw7b8jr6iTG-cC6wY_-N19466PXUAb'
        # # 空间名称
        # bucket_name = 'itcast20171104'
        url=put_qiniu(f1)
        #如果未出错
        #保存用户的头像信息
        try:
            user=User.query.get(session['user_id'])
            user.avatar=url
            user.add_update()
        except:
            logging.ERROR(u'数据库访问失败')
            return jsonify(code=RET.DBERR)
        # 则返回图片信息
        return jsonify(code=RET.OK,
                       url=current_app.config['QINIU_URL'] +  url)
    elif 'name' in dict:
        #修改用户名
        name=dict.get('name')
        #判断用户名是否存在
        if User.query.filter_by(name=name).count():
            return jsonify(code=RET.DATAEXIST)
        else:
            user=User.query.get(session['user_id'])
            user.name=name
            user.add_update()
            return jsonify(code=RET.OK)
    else:
        return jsonify(code=RET.PARAMERR,msg=ret_map[RET.PARAMERR])
 
@is_login
#实名认证
@user_blueprint.route('/auth',methods=['PUT'])
def user_auth_set():
    #接受参数
    dict=request.form
    id_name=dict.get('id_name')
    id_card=dict.get('id_card')
    #验证参数合法性
    if not all([id_name,id_card]):
        return jsonify(code=RET.PARAMERR,msg=ret_map[RET.SESSIONERR])
    #验证身份合法性
    if not re.match(r'^[1-9]\d{5}(19|20)\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$', id_card):
        return jsonify(code=RET.PARAMERR, msg=ret_map[RET.PARAMERR])
    #判断身份证号是否存在
    #修改数据对象
    try:
        user=User.query.get(session['user_id'])
    except:
        logging.ERROR(u'查询用户失败')
        return jsonify(code=RET.DBERR)
 
    try:
        user.id_card=id_card
        user.id_name=id_name
        user.add_update()
    except:
        logging.ERROR(u'修改用户姓名/身份证号失败')
        return jsonify(code=RET.DBERR)
 
    #返回数据
    return jsonify(code=RET.OK)
 
#用户注册
@user_blueprint.route('/session',methods=['POST'])
def user_login():
    #接收参数
    dict=request.form
    mobile=dict.get('mobile')
    password=dict.get('password')
    #验证非空
    if not all([mobile,password]):
        return jsonify(code=RET.PARAMERR,msg=ret_map[RET.PARAMERR])
    #验证手机号格式是否正确
    if not re.match(r'^1[345789]\d{9}$',mobile):
        return jsonify(code=RET.PARAMERR,msg=u'手机号格式错误')
    #数据处理
    try:
        user=User.query.filter_by(phone=mobile).first()
    except:
        logging.ERROR('用户登录--数据库出错')
        return jsonify(code=RET.DBERR,msg=ret_map[RET.DBERR])
    #判断手机号是否存在
    if user:
        #判断密码是否正确
        if user.check_pwd(password):
            session['user_id']=user.id
            return jsonify(code=RET.OK,msg=u'ok')
        else:
            return jsonify(code=RET.PARAMERR,msg=u'密码不正确')
    else:
        return jsonify(code=RET.PARAMERR,mag=u'手机号不存在')
 
#用户登录
@user_blueprint.route('/session',methods=['GET'])
def user_is_login():
    if 'user_id' in session:
        user=User.query.filter_by(id=session['user_id']).first()
        return jsonify(code=RET.OK,name=user.name)
    else:
        return jsonify(code=RET.DATAERR)
 
#用户退出
@user_blueprint.route('/session',methods=['DELETE'])
def user_logout():
    session.clear()
    return jsonify(code=RET.OK)
