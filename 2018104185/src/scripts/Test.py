import requests
import webbrowser
import json
import time

def Enrollusers():
    payload = {'username': 'Jim', 'orgName': 'org1'}
    r = requests.post("http://localhost:4000/users? ", data=payload)
    rtext = r.text
    rjson = json.loads(rtext, strict=False)
    global usersToken
    usersToken = (rjson['token'])
    return usersToken

def Creatchannel(usersToken):
    payload = {'channelName': 'mychannel', 'channelConfigPath': '../artifacts/channel/mychannel.tx'}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/channels? ", data=payload,headers=headers)

def Joinchannel(usersToken):
    payload = {'peers': ['peer1','peer2']}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/channels/mychannel/peers? ", data=payload, headers=headers)

def Installchaincodetask(usersToken,chaincodeName):
    payload = {'peers': ['peer1', 'peer2'],'chaincodeName':chaincodeName,'chaincodePath':'github.com/asset','chaincodeVersion':'v0'}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/chaincodes? ", data=payload,headers=headers)

def Instantiatechaincodetask(usersToken,chaincodeName):
    payload = {'chaincodeName':chaincodeName,'chaincodeVersion':'v0','fcn':'Init','args':''}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/channels/mychannel/chaincodes? ", data=payload,headers=headers)

def createAsset(args,usersToken,taskName):
    payload = {'fcn':'createAsset','args':args}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/channels/mychannel/chaincodes/"+taskName+"/asset?", data=payload,headers=headers)
    rtext=r.text
    return rtext  

def transaction(assetID,usersToken,taskName):
    payload = {'fcn':'createTransaction','args':assetID}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/channels/mychannel/chaincodes/"+taskName+"/transaction?", data=payload,headers=headers)
    rtext=r.text
    return rtext

def createOrgCertificate(args,usersToken,taskName):
    payload = {'fcn':'createAsset','args':args}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/channels/mychannel/chaincodes/"+taskName+"/createOrgCertificate?", data=payload,headers=headers)
    rtext=r.text
    return rtext  

def createIndividualCertificate(args,usersToken,taskName):
    payload = {'fcn':'createAsset','args':args}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.post("http://localhost:4000/channels/mychannel/chaincodes/"+taskName+"/createIndividualCertificate?", data=payload,headers=headers)
    rtext=r.text
    return rtext

def getAsset(usersToken,choosetaskName,assetID):
    payload = {'peer': 'peer1','args':assetID}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel/chaincodes/"+ choosetaskName+"/getAsset?",params=payload, headers=headers)
    rtext=r.text
    return rtext

def getAssetTX(usersToken,choosetaskName,assetID):
    payload = {'peer': 'peer1','args':assetID}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel/chaincodes/"+ choosetaskName+"/getAssetTX?",params=payload, headers=headers)
    rtext=r.text
    return rtext

def getAssetLastTX(usersToken,choosetaskName,assetID):
    payload = {'peer': 'peer1','args':assetID}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel/chaincodes/"+ choosetaskName+"/getAssetLastTX?",params=payload, headers=headers)
    rtext=r.text
    return rtext

def getAssetStatus(usersToken,choosetaskName,assetID):
    payload = {'peer': 'peer1','args':assetID}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel/chaincodes/"+ choosetaskName+"/getAssetStatus?",params=payload, headers=headers)
    rtext=r.text
    return rtext

def getAssetByOrgTime(usersToken,choosetaskName,args):
    payload = {'peer': 'peer1','args':[args[0],args[1],args[2]]}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel/chaincodes/"+ choosetaskName+"/getAssetByOrgTime?",params=payload, headers=headers)
    rtext=r.text
    return rtext

def getAssetByOrgExpiry(usersToken,choosetaskName,assetID):
    payload = {'peer': 'peer1','args':assetID}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel/chaincodes/"+ choosetaskName+"/getAssetByOrgExpiry?",params=payload, headers=headers)
    rtext=r.text
    return rtext

def getTXNum(usersToken,choosetaskName,assetID):
    payload = {'peer': 'peer1','args':assetID}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel/chaincodes/"+ choosetaskName+"/getTXNum?",params=payload, headers=headers)
    rtext=r.text
    return rtext

def getCertificate(usersToken,choosetaskName,assetID):
    payload = {'peer': 'peer1','args':assetID}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel/chaincodes/"+ choosetaskName+"/getCertificate?",params=payload, headers=headers)
    rtext=r.text
    return rtext

def getAssetsByUser(usersToken,choosetaskName,assetID):
    payload = {'peer': 'peer1','args':assetID}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel/chaincodes/"+ choosetaskName+"/getAssetsByUser?",params=payload, headers=headers)
    rtext=r.text
    return rtext

def getBlkNum(usersToken):
    payload = {'peer': 'peer1'}
    headers = {'authorization': 'Bearer '+usersToken}
    r = requests.get("http://localhost:4000/channels/mychannel?",params=payload, headers=headers)
    rtext=r.text
    return rtext

######################################################################################################################
#######################################      main    #################################################################
######################################################################################################################

if __name__ =='__main__':

    chaincodeName='501'
    userToken=Enrollusers()
    Installchaincodetask(usersToken,chaincodeName)
    Instantiatechaincodetask(usersToken,chaincodeName)
    assetID  = 'a6561a8f652cf1fcdc5bfe1901a2712ae44f00701a28d6c699e5a55d37392740'
    account ="ask510"
'''
    Test1data ={"certType":"1","account":account,"desc1":"zhs","desc2":"hszhs","createTime":"2018-03-23 18:10:00"}
    Test1data = json.dumps(Test1data)
    Test1 =createOrgCertificate(Test1data,userToken,chaincodeName)

    Test1args = json.loads(Test1, strict=False)

    issuerPK = Test1args["pubKey"]
    privateKeyPEM =Test1args["privateKeyPEM"]
    certPEM =Test1args["certPEM"]

    Test2data={"assetID":assetID, "issuerPK":issuerPK, "originalNo":"1", "desc":"10", "expiryDate":"2019-03-21 09:30:30","privateKeyPEM":privateKeyPEM}
    Test2args = json.dumps(Test2data)
    Test2 = createAsset(Test2args,usersToken,chaincodeName)

    Test3data={"assetID":assetID, "tx_id":"1668155bb5587db0cedba128ba4cabc18e42dca15460e574bbf4b7eabfa6d69e", "version":"1", "desc":"0", "txType":"1","in":{"prev_out":"1"},"out":issuerPK,"privateKeyPEM":privateKeyPEM}
    Test3args = json.dumps(Test3data)
    Test3 = transaction(Test3args,usersToken,chaincodeName)

    Test4 = getAsset(usersToken,chaincodeName,assetID)
    Test5 = getAssetTX(usersToken,chaincodeName,assetID)
    Test6 = getAssetLastTX(usersToken,chaincodeName,assetID)
    Test7 = getAssetStatus(usersToken,chaincodeName,assetID)

    Test8args=[issuerPK, "2016-10-01 10:00:00", "2020-10-01 10:00:00"]
    Test8 =getAssetByOrgTime(usersToken,chaincodeName,Test8args)

    Test9args=[issuerPK, "1", "2018-10-01 10:00:00"]
    Test9 =getAssetByOrgExpiry(usersToken,chaincodeName,Test9args)
 
    Test10args =[2,1]
    Test10 =getTXNum(usersToken,chaincodeName,Test10args)

    Test11  =getBlkNum(usersToken)
    Test12  = getAssetsByUser(usersToken,chaincodeName,issuerPK)
    Test13 = getCertificate(usersToken,chaincodeName,account)
   
    print Test1
    print Test2
    print Test3
    print Test4
    print Test5
    print Test6
    print Test7
    print Test8
    print Test9
    print Test10
    print Test11
    print Test12
    print Test13
'''
    


