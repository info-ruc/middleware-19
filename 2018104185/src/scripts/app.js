/**
 * Copyright 2017 IBM All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an 'AS IS' BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
'use strict';
var log4js = require('log4js');
var iconv = require('iconv-lite');
var logger = log4js.getLogger('SampleWebApp');
var express = require('express');
var session = require('express-session');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var http = require('http');
var util = require('util');
var app = express();
var expressJWT = require('express-jwt');
var jwt = require('jsonwebtoken');
var bearerToken = require('express-bearer-token');
var cors = require('cors');


require('./config.js');
var hfc = require('fabric-client');

var helper = require('./app/helper.js');
var channels = require('./app/create-channel.js');
var join = require('./app/join-channel.js');
var install = require('./app/install-chaincode.js');
var instantiate = require('./app/instantiate-chaincode.js');
var invoke = require('./app/invoke-transaction.js');
var crypto = require('./app/cryptoTools.js');
var query = require('./app/query.js');
var host = process.env.HOST || hfc.getConfigSetting('host');
var port = process.env.PORT || hfc.getConfigSetting('port');
var fs = require('fs-extra');
var cryptos = require('crypto');
var sleep = require('sleep');



function verify(algorithm,pubkey,sig,data){
    
    var verify = cryptos.createVerify(algorithm);
    verify.update(data);
    var rlt = verify.verify(pubkey, sig, 'hex');
    logger.debug("verify result=" + rlt);
    return rlt;
}

//根据用户名和组织名获取用户证书
function getUserKey(json_user, userOrg) {
	var org;
	if (userOrg = "org1"){
		org = "Org1";
	}else if (userOrg ="org2"){
		org = "Org2";
	}
	  
	/*
{"name":"Barry","mspid":"Org2MSP","roles":null,"affiliation":"","enrollmentSecret":"VpRuPVgfkkzL","enrollment":{"signingIdentity":"c5daae11bd410d55eecc44c85fd60f5eab5d20522c8aea180d06ae5d9d8bee20","identity":{"certificate":"-----BEGIN CERTIFICATE-----\nMIIB8DCCAZegAwIBAgIUL0OgMo0P07Gs2YYNzDrl7Io8HzkwCgYIKoZIzj0EAwIw\nczELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDVNh\nbiBGcmFuY2lzY28xGTAXBgNVBAoTEG9yZzIuZXhhbXBsZS5jb20xHDAaBgNVBAMT\nE2NhLm9yZzIuZXhhbXBsZS5jb20wHhcNMTgwMjAzMTQwNzAwWhcNMTkwMjAzMTQw\nNzAwWjAQMQ4wDAYDVQQDEwVCYXJyeTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IA\nBLJDpksAzf1jP+RBaq57AcyHc7mswEK/gPg6bG15Fd7NvSGMGIIgM73d3J7dEa3u\nHLqLfZjTe1RR1jdpq0gxm8qjbDBqMA4GA1UdDwEB/wQEAwIHgDAMBgNVHRMBAf8E\nAjAAMB0GA1UdDgQWBBR4m+yFW5XQFwvOghwEIvN4aXeDszArBgNVHSMEJDAigCCn\n1H76Rqa6B3MMhQ/tLBN13yc2DXIn9IzcL4DlBWeABTAKBggqhkjOPQQDAgNHADBE\nAiBYi8/84UY71r7BDFzxYL1S7mg9X0Oorlde6KX7T7PbKAIgafFWVrruudeQggRD\nPC6MRPIXwhIAoHQYOmgV5nLwE7Q=\n-----END CERTIFICATE-----\n"}}}
*/
	var signingIdentity = json_user.enrollment.signingIdentity;
	var certPEM = json_user.enrollment.identity.certificate.toString();
	var keyPath = "/tmp/fabric-client-kvs_peer" + org + "\/" + signingIdentity + '-priv';
	var keyPEM = fs.readFileSync(keyPath).toString();
	var cryptoContent = {
	  privateKeyPEM: keyPEM,
          signedCertPEM: certPEM,
	};

	logger.debug('getUserKey: cryptoContent : ' + JSON.stringify(cryptoContent));
	return cryptoContent;

}

 
//TODO  we need to use pub_key and private_key to replace req
function packageTX(assetID, req){
	var cryptoContent = helper.getKey(req.username, req.orgname);
	var cer = cryptoContent.signedCertPEM;

	var timeStamp = new Date().getTime();
 
	var json_tx = {"tx_id": "", 
     		"asset_id": "",
     		"version": "1.0",
     		"tx_in_count": 1,
     		"tx_out_count": 1,
     		"tx_in":{"hash":"genesis_tx","index":0,"scriptsig_r":"","scriptsig_s":""},
     		"tx_out":[{"value":1,"certificate":""}],
		"tx_time":""
    	};

	var txID = crypto.createHASH(JSON.stringify(json_tx));
	json_tx["tx_id"] = txID;	
	json_tx["tx_time"] = String(timeStamp);
	json_tx["asset_id"] = assetID;	

	var rsSig = crypto.createECDSASig(JSON.stringify(json_tx), req.username, req.orgname);

	var json_sig = JSON.parse(rsSig);
	json_tx["tx_in"]["scriptsig_r"] = json_sig["r"];
	json_tx["tx_in"]["scriptsig_s"] = json_sig["s"];
	json_tx["tx_out"][0]["certificate"] = cer;

	var str_tx = JSON.stringify(json_tx); 
	
	logger.debug('full tx : ' + str_tx);
	
	return str_tx;
}
 
//TODO  we need to use pub_key and private_key to replace req
function packageAsset(asset_id, desc, authorPUBKEY, feature,picHash ,sig,req){
	var json_asset = {
     		"asset_id": "",
     		"desc": "",
     		"authorPUBKEY": "",
     		"feature": "",
     		"picHash":"",     	
		"sig": "", 
    	};

	json_asset["asset_id"] = asset_id; 
	json_asset["desc"] = desc;
	json_asset["authorPUBKEY"] = authorPUBKEY;
	json_asset["feature"] = feature;
	json_asset["picHash"] = picHash;
	json_asset["sig"] = sig;

 
	logger.debug("the full asset is :" + JSON.stringify(json_asset));
	return JSON.stringify(json_asset);
}
 

///////////////////////////////////////////////////////////////////////////////
//////////////////////////////// SET CONFIGURATONS ////////////////////////////
///////////////////////////////////////////////////////////////////////////////
app.options('*', cors());
app.use(cors());
//support parsing of application/json type post data
app.use(bodyParser.json());
//support parsing of application/x-www-form-urlencoded post data
app.use(bodyParser.urlencoded({
	extended: false
}));
// set secret variable
app.set('secret', 'thisismysecret');
//app.use(expressJWT({
//	secret: 'thisismysecret'
//}).unless({
//	path: ['/users']
//}));


///////////////////////////////////////////////////////////////////////////////
//////////////////////////////// START SERVER /////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
var server = http.createServer(app).listen(port, function() {});
logger.info('****************** SERVER STARTED ************************');
logger.info('**************  http://' + host + ':' + port +
	'  ******************');
server.timeout = 240000;

function getErrorMessage(field) {
	var response = {
		success: false,
		message: field + ' field is missing or Invalid in the request'
	};
	return response;
}

///////////////////////////////////////////////////////////////////////////////
///////////////////////// REST ENDPOINTS START HERE ///////////////////////////
///////////////////////////////////////////////////////////////////////////////
// Register and enroll user
app.post('/users', function(req, res) {
	var username = req.body.username;
	var orgName = req.body.orgName;
	logger.debug('End point : /users');
	logger.debug('User name : ' + username);
	logger.debug('Org name  : ' + orgName);
	if (!username) {
		res.json(getErrorMessage('\'username\''));
		return;
	}
	if (!orgName) {
		res.json(getErrorMessage('\'orgName\''));
		return;
	}
	var token = jwt.sign({
		exp: Math.floor(Date.now() / 1000) + parseInt(hfc.getConfigSetting('jwt_expiretime')),
		username: username,
		orgName: orgName
	}, app.get('secret'));
	helper.getRegisteredUsers(username, orgName, true).then(function(response) {
		//if (response && typeof response !== 'string') {
			//console.dir(response)
			var json_res = JSON.parse(response);
			//var json_res=response.token
			json_res.token = token;
			res.send(JSON.stringify(json_res));
		//} else {
		//	res.json({
		//		success: false,
		//		message: response
		//	});
		//}
	});
});

// Create Channel
app.post('/channels', function(req, res) {
	req.username = "admin"
	req.orgname = "org1"
	logger.info('<<<<<<<<<<<<<<<<< C R E A T E  C H A N N E L >>>>>>>>>>>>>>>>>');
	logger.debug('End point : /channels');
	var channelName = req.body.channelName;
	var channelConfigPath = req.body.channelConfigPath;
	logger.debug('Channel name : ' + channelName);
	logger.debug('channelConfigPath : ' + channelConfigPath); //../artifacts/channel/mychannel.tx
	if (!channelName) {
		res.json(getErrorMessage('\'channelName\''));
		return;
	}
	if (!channelConfigPath) {
		res.json(getErrorMessage('\'channelConfigPath\''));
		return;
	}

	channels.createChannel(channelName, channelConfigPath, req.username, req.orgname)
	.then(function(message) {
		res.send(message);
	});
});
// Join Channel
app.post('/channels/:channelName/peers', function(req, res) {
	req.username = "admin"
	req.orgname = "org1"
	logger.info('<<<<<<<<<<<<<<<<< J O I N  C H A N N E L >>>>>>>>>>>>>>>>>');
	var channelName = req.params.channelName;
	var peers = req.body.peers;
	logger.debug('channelName : ' + channelName);
	logger.debug('peers : ' + peers);
	if (!channelName) {
		res.json(getErrorMessage('\'channelName\''));
		return;
	}
	if (!peers || peers.length == 0) {
		res.json(getErrorMessage('\'peers\''));
		return;
	}

	join.joinChannel(channelName, peers, req.username, req.orgname)
	.then(function(message) {
		res.send(message);
	});
});
// Install chaincode on target peers
app.post('/chaincodes', function(req, res) {
	logger.debug('==================== INSTALL CHAINCODE ==================');
	req.username = "admin"
	req.orgname = "org1"
	var peers = req.body.peers;
	var chaincodeName = req.body.chaincodeName;
	var chaincodePath = req.body.chaincodePath;
	var chaincodeVersion = req.body.chaincodeVersion;
	logger.debug('peers : ' + peers); // target peers list
	logger.debug('chaincodeName : ' + chaincodeName);
	logger.debug('chaincodePath  : ' + chaincodePath);
	logger.debug('chaincodeVersion  : ' + chaincodeVersion);
	if (!peers || peers.length == 0) {
		res.json(getErrorMessage('\'peers\''));
		return;
	}
	if (!chaincodeName) {
		res.json(getErrorMessage('\'chaincodeName\''));
		return;
	}
	if (!chaincodePath) {
		res.json(getErrorMessage('\'chaincodePath\''));
		return;
	}
	if (!chaincodeVersion) {
		res.json(getErrorMessage('\'chaincodeVersion\''));
		return;
	}

	install.installChaincode(peers, chaincodeName, chaincodePath, chaincodeVersion, req.username, req.orgname)
	.then(function(message) {
		res.send(message);
	});
});
// Instantiate chaincode on target peers
app.post('/channels/:channelName/chaincodes', function(req, res) {
	logger.debug('==================== INSTANTIATE CHAINCODE ==================');
	req.username = "admin"
	req.orgname = "org1"
	var chaincodeName = req.body.chaincodeName;
	var chaincodeVersion = req.body.chaincodeVersion;
	var channelName = req.params.channelName;
	var fcn = req.body.fcn;
	logger.debug('channelName  : ' + channelName);
	logger.debug('chaincodeName : ' + chaincodeName);
	logger.debug('chaincodeVersion  : ' + chaincodeVersion);
	logger.debug('fcn  : ' + fcn);
	if (!chaincodeName) {
		res.json(getErrorMessage('\'chaincodeName\''));
		return;
	}
	if (!chaincodeVersion) {
		res.json(getErrorMessage('\'chaincodeVersion\''));
		return;
	}
	if (!channelName) {
		res.json(getErrorMessage('\'channelName\''));
		return;
	}
	logger.debug(req.orgname + "123")
	instantiate.instantiateChaincode(channelName, chaincodeName, chaincodeVersion, fcn, req.username, req.orgname)
	.then(function(message) {
		res.send(message);
	});
});



// 创建资产
app.post('/channels/:channelName/chaincodes/:chaincodeName/createAsset', function(req, res) {
	logger.debug('==================== create asset ==================');
	req.username = "admin"
	req.orgname = "org1"
	var peers = req.body.peers;
	var chaincodeName = req.params.chaincodeName;
	var channelName = req.params.channelName;
	var fcn = req.body.fcn;
	var args = req.body.DATA;
    console.dir(req.body)
    var args =JSON.parse(args);
	logger.debug('channelName  : ' + channelName);
	logger.debug('chaincodeName : ' + chaincodeName);
	logger.debug('fcn  : ' + fcn);
	logger.debug('args  : ' + args);

	if (!chaincodeName) {
		res.json(getErrorMessage('\'chaincodeName\''));
		return;
	}
	if (!channelName) {
		res.json(getErrorMessage('\'channelName\''));
		return;
	}
	if (!args) {
		res.json(getErrorMessage('\'args\''));
		return;
	}


        var asset_id = args["ID"]
        var desc = args["desc"]
        var authorPUBKEY = args["authorPUBKEY"]
        var feature = args["feature"]
        var picHash = args["picHash"]
        var sig = args["sig"]
        var delcare=args["delcare"]
        logger.debug(sig+ "zzzzz")
        var data= JSON.stringify(desc)+authorPUBKEY+delcare+feature+picHash;
        var sha256=crypto.createHASH(data);
        logger.debug('sha256  : ' + sha256);

	if (sha256 !== asset_id){
		res.json((getErrorMessage('assetID'))); 
		logger.debug('Hash wrong')
       return;
	}
query.queryChaincode(peers, channelName, chaincodeName, authorPUBKEY, 'getCertificateByPK', req.username, req.orgname)
	.then(function(issuercert) {
             // 用公钥进行验证签名
             logger.debug(sig)
              var algorithm = 'ecdsa-with-SHA1';
            var result = verify(algorithm,issuercert,sig,asset_id);       
	      if (!result){
		      res.json((getErrorMessage('verify false')));
                      logger.debug('sha256  : ' + result);
		      return;
	         }
	var str_asset = packageAsset(asset_id, desc, authorPUBKEY, feature,picHash ,sig,req); 
              logger.debug('str_asset:'+str_asset);
	      invoke.invokeChaincode(peers, channelName, chaincodeName, "createAsset", str_asset, req.username, req.orgname)
	             .then(function(message) {
		         res.send({'code': '200'});
	      });
	});                
});





//接口

//retrieveFeature

app.post('/channels/:channelName/chaincodes/:chaincodeName/retrieveFeature', function(req, res) {
//	logger.debug('==================== INVOKE ON CHAINCODE ==================');
//app.get('/channels/:channelName/chaincodes/:chaincodeName/retrieveFeature', function(req, res) {
	logger.debug('==================== QUERY BY CHAINCODE ==================');
	req.username = "admin"
	req.orgname = "org1"
	var channelName = req.params.channelName;
	var chaincodeName = req.params.chaincodeName;
	// let args = req.query.args;
	
	var assetID = req.body.assetID;
	var peer = req.body.peer;
	
	logger.debug('channelName : ' + channelName);
	logger.debug('chaincodeName : ' + chaincodeName);
	logger.debug('assetID : ' + assetID);
	logger.debug('peer:' + peer);

	if (!chaincodeName) {
		res.json(getErrorMessage('\'chaincodeName\''));
		return;
	}
	if (!channelName) {
		res.json(getErrorMessage('\'channelName\''));
		return;
	}
	if (!assetID) {
		res.json(getErrorMessage('\'assetID\''));
		return;
	}
 
	//args = args.replace(/'/g, '"');
	//args = JSON.parse(args);

	query.queryChaincode(peer, channelName, chaincodeName, assetID, "getAsset", req.username, req.orgname)
	.then(function(message) {
                logger.debug("result message is:" + message);
		var json_asset = JSON.parse(message);	
		var rlt = json_asset["feature"];
		logger.debug("feature is:" + rlt);
		//json_asset["feature"]="123";
		//logger.debug("response is:" + JSON.stringify(json_asset));
		//res.send(rlt);
     
        var r = JSON.stringify({code:"200", feature: rlt})
		res.send(r);
	});
});


//createCertificate
app.post('/channels/:channelName/chaincodes/:chaincodeName/createCertificate', function(req, res) {
         logger.debug('==================== CREATE CERTIFICATE ==================');
         req.username = "admin"
	req.orgname = "org1"
	 var peers = req.body.peers;
	 var chaincodeName = req.params.chaincodeName;
	 var channelName = req.params.channelName;
        
         var desc = req.body.desc;	
         var name = req.body.name;
         logger.debug(name+'!   name!!!!!!!!');
         var feature = req.body.feature;
         var userOrg = req.orgname; 
         helper.getRegisteredUsers(name, userOrg, true).then(function(response) {
             var json_user = JSON.parse(response);
             var signingIdentity = json_user.enrollment.signingIdentity;
             var cert =json_user.enrollment.identity.certificate.toString();
            query.queryChaincode(peers, channelName, chaincodeName, cert, "getPubKey", req.username, req.orgname)
                 .then(function(certs){
                        var org = "Org1";
                       	logger.debug('pubKey:###############' + certs); 
                        var pubKey  = iconv.decode(certs, 'hex');	
                        var keyPath = "/tmp/fabric-client-kvs_peer" + org + "\/" + signingIdentity + '-priv';
                        var keyPEM = fs.readFileSync(keyPath).toString();
                        var cryptoContent = {
                                    publicKey: pubKey,
                                    privateKey: keyPEM,
                                    certificate: cert,
                                    code:"200"
                                    };
                        logger.debug('getUserKey: cryptoContent : ' + JSON.stringify(cryptoContent));
                        var certs =JSON.stringify(cryptoContent);
                      return certs;    
               }).then(function(certs){
                  var json_certs = JSON.parse(certs)
                  var certData =　{
                            cert:json_certs["certificate"],
                            pubkey:json_certs["publicKey"],
                            desc:desc,
   	                    feature:feature,
                           };

                    var args = JSON.stringify(certData);
                   logger.debug(args + "@@@@@@@@@@########!!!!!!!!!!!!")
                   invoke.invokeChaincode(peers, channelName, chaincodeName, "saveCertificate", args, req.username, userOrg)
                         .then(function(message) {
                         		logger.debug(message + "saveCertificate!!!!!")


                                res.send(certs); 	
                           });
              });                 
        }); 
});



//接口

//retrieveUserFeature

app.post('/channels/:channelName/chaincodes/:chaincodeName/retrieveUserFeature', function(req, res) {
//	logger.debug('==================== INVOKE ON CHAINCODE ==================');
//app.get('/channels/:channelName/chaincodes/:chaincodeName/retrieveFeature', function(req, res) {
	logger.debug('==================== RETRIEVE USER FEATURE ==================');
	req.username = "admin"
	req.orgname = "org1"
	var channelName = req.params.channelName;
	var chaincodeName = req.params.chaincodeName;
	// let args = req.query.args;
	
	var certPEM = req.body.cert;
	var peer = req.body.peer;
	
	logger.debug('channelName : ' + channelName);
	logger.debug('chaincodeName : ' + chaincodeName);
	logger.debug('certPEM : ' + certPEM);
	logger.debug('peer:' + peer);

	if (!chaincodeName) {
		res.json(getErrorMessage('\'chaincodeName\''));
		return;
	}
	if (!channelName) {
		res.json(getErrorMessage('\'channelName\''));
		return;
	}
	if (!certPEM) {
		res.json(getErrorMessage('\'certPEM\''));
		return;
	}

	logger.debug("certPEM:" + certPEM);		
	var idx = certPEM.indexOf("CERTIFICATE");
	var strPEM = certPEM.substring(idx);
	idx = strPEM.indexOf("-----");
	strPEM = strPEM.substring(idx+5);
	idx = strPEM.indexOf("-----");
	strPEM = strPEM.substring(0, idx);
	strPEM = strPEM.replace(/\r/g,"");
        strPEM = strPEM.replace(/\n/g,"");
	strPEM = strPEM.replace(/ /g, "+");
	idx = strPEM.indexOf("MII");
	strPEM = strPEM.substr(idx,30);
	logger.debug("raw cert of certPEM:" + strPEM);
	var user_key = crypto.createHASH(strPEM); 
	logger.debug("user_key is:" + user_key);
	query.queryChaincode(peer, channelName, chaincodeName, certPEM, "getUserFeature", req.username, req.orgname)
	.then(function(message) {
                logger.debug("result feature is:" + message);
		var json_feature = JSON.parse(message)
                var feature = json_feature["feature"];
		//json_asset["feature"]="123";
		//logger.debug("response is:" + JSON.stringify(json_asset));
		//res.send(rlt);
		res.send(new Buffer(feature));
	});
});

//  Query Get Block by BlockNumber
app.get('/channels/:channelName/blocks/:blockId', function(req, res) {
	logger.debug('==================== GET BLOCK BY NUMBER ==================');
	req.username = "admin"
	req.orgname = "org1"
	let blockId = req.params.blockId;
	let peer = req.query.peer;
	logger.debug('channelName : ' + req.params.channelName);
	logger.debug('BlockID : ' + blockId);
	logger.debug('Peer : ' + peer);
	if (!blockId) {
		res.json(getErrorMessage('\'blockId\''));
		return;
	}

	query.getBlockByNumber(peer, blockId, req.username, req.orgname)
		.then(function(message) {
			res.send(message);
		});
});
// Query Get Transaction by Transaction ID
app.get('/channels/:channelName/transactions/:trxnId', function(req, res) {
	req.username = "admin"
	req.orgname = "org1"
	logger.debug(
		'================ GET TRANSACTION BY TRANSACTION_ID ======================'
	);
	logger.debug('channelName : ' + req.params.channelName);
	let trxnId = req.params.trxnId;
	let peer = req.query.peer;
	if (!trxnId) {
		res.json(getErrorMessage('\'trxnId\''));
		return;
	}

	query.getTransactionByID(peer, trxnId, req.username, req.orgname)
		.then(function(message) {
			res.send(message);
		});
});
// Query Get Block by Hash
app.get('/channels/:channelName/blocks', function(req, res) {
	logger.debug('================ GET BLOCK BY HASH ======================');
	logger.debug('channelName : ' + req.params.channelName);
	let hash = req.query.hash;
	let peer = req.query.peer;
	if (!hash) {
		res.json(getErrorMessage('\'hash\''));
		return;
	}

	query.getBlockByHash(peer, hash, req.username, req.orgname).then(
		function(message) {
			res.send(message);
		});
});
//Query for Channel Information
app.get('/channels/:channelName', function(req, res) {
	logger.debug(
		'================ GET CHANNEL INFORMATION ======================');
	logger.debug('channelName : ' + req.params.channelName);
	let peer = req.query.peer;

	query.getChainInfo(peer, req.username, req.orgname).then(
		function(message) {
			res.send(message);
		});
});
// Query to fetch all Installed/instantiated chaincodes
app.get('/chaincodes', function(req, res) {
	var peer = req.query.peer;
	var installType = req.query.type;
	//TODO: add Constnats
	if (installType === 'installed') {
		logger.debug(
			'================ GET INSTALLED CHAINCODES ======================');
	} else {
		logger.debug(
			'================ GET INSTANTIATED CHAINCODES ======================');
	}

	query.getInstalledChaincodes(peer, installType, req.username, req.orgname)
	.then(function(message) {
		res.send(message);
	});
});
// Query to fetch channels
app.get('/channels', function(req, res) {
	logger.debug('================ GET CHANNELS ======================');
	logger.debug('peer: ' + req.query.peer);
	var peer = req.query.peer;
	if (!peer) {
		res.json(getErrorMessage('\'peer\''));
		return;
	}

	query.getChannels(peer, req.username, req.orgname)
	.then(function(
		message) {
		res.send(message);
	});
});

// GetAsset
app.post('/channels/:channelName/chaincodes/:chaincodeName/getGrade', function(req, res) {
	logger.debug('==================== GetAsset ==================');
	var channelName = req.params.channelName;
	var chaincodeName = req.params.chaincodeName;
	let args = req.body.assetID;
	var peerName = "peer1";
	req.username = "admin"
	req.orgname = "org1"
	// let peer = req.query.peer;
	logger.debug('channelName : ' + channelName);
	logger.debug('chaincodeName : ' + chaincodeName);
	logger.debug('args : ' + args);

	if (!chaincodeName) {
		res.json(getErrorMessage('\'chaincodeName\''));
		return;
	}
	if (!channelName) {
		res.json(getErrorMessage('\'channelName\''));
		return;
        }
	if (!args) {
		res.json(getErrorMessage('\'args\''));
		return;
	}
	logger.debug(args);

	query.queryChaincode(peerName, channelName, chaincodeName, args, 'getAsset', req.username, req.orgname)
	.then(function(message) {
        logger.debug("result message is:" + message);
        if (message[0] == 'E') {
            res.status(500).send(message);
        }
		else {
            message = JSON.parse(message);
            message.code = '200';
            message = JSON.stringify(message)
			res.status(200).send(message);
        }
	});
});
