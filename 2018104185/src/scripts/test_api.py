# -*- coding: utf-8 -*
import requests
import json
import hashlib
import execjs
import io  
import os
from datetime import datetime, timedelta

URL = 'http://localhost:4000/'
#URL = 'http://114.242.26.112:4000/'
#CHANNEL_NAME = 'airtrip-union'
CHANNEL_NAME = 'mychannel'

#CHAINCODE_NAME = 'airtrip'
CHAINCODE_NAME = '5000003'

#ORG_NAME = 'jetair'
ORG_NAME = 'org1'

CHAINCODE_PATH = 'github.com/asset'

HEADER = ''

def enroll():
    payload = {"username": "admin", "orgName": ORG_NAME}
    r = requests.post(URL+'users', data=payload)
    rjson = r.json()
    return rjson['token']


def create_certificate(desc, feature):
    data = {'desc': desc, 'feature': feature}
    data = json.dumps(data)
    r = requests.post(URL+"channels/"+CHANNEL_NAME+"/chaincodes/"+CHAINCODE_NAME+"/createCertificate", data=data, headers=HEADER)
    return r.json()  


def hash(*args):
    data = ''
    for st in args:
        data = data + st
    res = hashlib.sha256(data.encode()).hexdigest()
    return res


def get_js():    
    f = io.open("./signer.js", 'r', encoding='UTF-8')  
    line = f.readline()  
    htmlstr = ''  
    while line:  
        htmlstr = htmlstr + line  
        line = f.readline()  
    return htmlstr


def sign(data, private_key):
    jsstr = get_js()  
    ctx = execjs.compile(jsstr)
    args = ['ecdsa-with-SHA1', private_key, data]  
    return ctx.call('signer', args)


def create_asset(desc, authorPUBKEY, delcare, feature, picHash, privkey):
    asset_id = hash(feature, desc, authorPUBKEY, picHash)
    sig = sign(asset_id, privkey)
    args = {"ID":asset_id,"desc":desc,"authorPUBKEY":authorPUBKEY,"delcare":delcare,"feature":feature,"picHash":picHash,"sig":sig}
    args = json.dumps({'DATA': json.dumps(args)})
    url = URL + "channels/"+CHANNEL_NAME+"/chaincodes/"+ CHAINCODE_NAME + "/createAsset"
    r = requests.post(url, data=args, headers=HEADER)
    print("\nCreate the asset" + "\n" + r.text + "\n" + asset_id)
    return asset_id


def Installchaincodetask(usersToken,chaincodeName):
    payload = {'peers': ['peer1', 'peer2'],'chaincodeName':chaincodeName,'chaincodePath':'github.com/asset','chaincodeVersion':'v0'}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/chaincodes? ", data=payload,headers=headers)

def Instantiatechaincodetask(usersToken,chaincodeName):
    payload = {'chaincodeName':chaincodeName,'chaincodeVersion':'v0','fcn':'Init','args':''}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/channels/mychannel/chaincodes? ", data=payload,headers=headers)


def retrieve_user_feature(cert):
    data = json.dumps({'cert': cert})
    r = requests.post(URL+"channels/"+CHANNEL_NAME+"/chaincodes/"+CHAINCODE_NAME+"/retrieveUserFeature", data=data, headers=HEADER)
    return r.text

def retrieve_feature(asset_id):
    data = json.dumps({'assetID': asset_id})
    r = requests.post(URL+"channels/"+CHANNEL_NAME+"/chaincodes/"+CHAINCODE_NAME+"/retrieveFeature", data=data, headers=HEADER)
    return r.text

if __name__ == '__main__':
    # enroll
    token = enroll()
    print('token:' + token)
    HEADER = {"authorization": "Bearer "+token, "content-type": "application/json"}
    chaincodeName ="5000003"
    #Installchaincodetask(token,chaincodeName)
    #Instantiatechaincodetask(token,chaincodeName)

    print("Create_certificate:")
    desc ="500azuej!!!!##"
    feature = "12314124124feature"
    d = create_certificate(desc, feature)


    # create cert
    print(d)
    pubkey = d['pubKey']
    privkey = d['privateKeyPEM']
    cert = d["certPEM"]
    print('pubkey:\n' + pubkey)
    print('\nprivateKey:\n' + privkey)
    print('cert:\n' + cert)


    # retrieve user feature: by cert
    print('feature:')
    feature = retrieve_user_feature(cert)
    print(feature)

    # create asset
    asset_id = create_asset('hello', pubkey, 'declare', feature, "adasfasf", privkey)
    print(asset_id)

    # retrieve feature: by assetID
    feature2 = retrieve_feature(asset_id)
    print("feature:")
    print(feature2)

