package main

import (
	"fmt"
	"encoding/json"
//    "crypto/sha256"
//    "crypto/x509"
//    "encoding/pem"
//    "crypto/ecdsa"
//    "math/big"
//    "time"
//    "bytes"
 	"crypto/x509"
        "encoding/pem"
        "crypto/ecdsa"   

	"github.com/hyperledger/fabric/core/chaincode/shim"
	sc "github.com/hyperledger/fabric/protos/peer"
)

type DigitalAsset struct {
}


type Certificate struct {
        Cert		string	`json:"cert"`
	Desc		string	`json:"desc"`
	Feature		string	`json:"feature"`
	Pubkey		string	`json:"pubkey"`
}





//endorsement:{
//    declare: “源背书，比如画家的声明或鉴定机构的声明”，
//    sig:“对源背书的签名”，
//    certificate:“签名者证书”
//}

//type endorsement   struct {
//	Declare      string       `json:"delcare"`
//	ScriptSig_r  string       `json:"scriptsig_r"`
//	ScriptSig_s  string       `json:"scriptsig_s"`
//	Certificate  string       `json:"certificate"`
//}


//asset_id: 系统生成的唯一字符串，
//feature_size: “特征值的大小，占多少字节，整数”，
//feature: “该数字资产的特征，二进制”,
//pic_hash: “完整照片的哈希值”，
//description: “画的基本描述，JSON格式字符串”，
//endorsement: “背书”

type Asset   struct {
        ID          string        `json:"asset_id"`
        Desc        Desc           `json:"desc"` 
	AuthorPUBKEY      string       `json:"authorPUBKEY"`
	Feature  string       `json:"feature"`        
        PicHash     string        `json:"picHash"`	
	Sig      string       `json:"sig"`
}

type Desc struct {
	Store_workName	string	`json:"store_workName"` 
	Store_workSize	string	`json:"store_workSize"`
	CreationYear	string	`json:"creationYear"`
	ClassificationWork   string   `json:"classificationWork"`
	MaterialWork   string   `json:"materialWork"`
	SubjectWork	string	`json:"subjectWork"`  
}
	                             
type TxIn   struct {
	Hash         string       `json:"hash"`  //所引用的output所在的交易编号
	Index        int          `json:"index"`  //所引用的output在该交易中的位置
	ScriptSig_r  string       `json:"scriptsig_r"`  //用相应的私钥 对 (tx_id + 对所引用output 公钥) 进行加密之后的密文
	ScriptSig_s  string       `json:"scriptsig_s"`
}

type TxOut  struct {
	Value        float64          `json:"value"`  //output所得到的资产份额
	Certificate  string       `json:"certificate"`  //output拥有者的数字证书
}

type Transaction struct {
    TxID         string      `json:"tx_id"`  //SHA256(SHA256(本交易字符串，但是tx_id 以及 TxIn中 ScriptSig 为空)),
    AssetID     string      `json:"asset_id"`  //交易所指向的资产ID
    Version       string      `json:"version"`
    TxInCount   int         `json:"tx_in_count"`
    TxOutCount  int         `json:"tx_out_count"`
    TxIn        TxIn      `json:"tx_in"`
    TxOutList     []TxOut     `json:"tx_out"`
    TimeStamp          string      `json:"tx_time"`    //交易发生的时间
}

//type TransactionData struct {
//    Tx_id         string      `json:"tx_id"`
//    Copyright     string      `json:"copyright"`
//    Version       string      `json:"version"`
//    Tx_in_count   int         `json:"tx_in_count"`
//    Tx_out_count  int         `json:"tx_out_count"`
//    In            []In        `json:"in"`
//    Out           []Out       `json:"out"`
//    Time          string      `json:"time"`
//    Use           bool        `json:"use"`
//}



func (s *DigitalAsset) Init(APIstub shim.ChaincodeStubInterface) sc.Response {
    return shim.Success(nil)
}

func (s *DigitalAsset) Invoke(APIstub shim.ChaincodeStubInterface) sc.Response {
     
    function, args := APIstub.GetFunctionAndParameters()
     
    if function == "createAsset" {
        return s.createAsset(APIstub, args)
    }else if function =="getAsset"{
    	return s.getAsset(APIstub, args)
    }else if function =="createTX" {
    	return s.createTX(APIstub, args)
    }else if function =="createUserFeature" {
    	return s.createUserFeature(APIstub, args)
    }else if function =="saveCertificate" {
    	return s.saveCertificate(APIstub, args)
    }else if function =="getCertificate" {
    	return s.getCertificate(APIstub, args)
    }else if function =="getPubKey" {
    	return s.getPubKey(APIstub, args)
    }else if function =="getCertificateByPK" {
    	return s.getCertificateByPK(APIstub, args)
    }else if function =="getUserFeature" {
    	return s.getUserFeature(APIstub, args)
    }


    return shim.Error("asset.go: Invalid function name.")
}

func (s *DigitalAsset) spliceParameter(APIstub shim.ChaincodeStubInterface, args []string) string {
	var str_in string
	str_in = ""

	for _ , value := range args{
		str_in = str_in + value
	}
	
	return str_in
}


func (s *DigitalAsset) getPubKey(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	certPEM := s.spliceParameter(APIstub,args)
        
	block, _ := pem.Decode([]byte(certPEM))
        if block == nil {
           return shim.Error("asset.go: getPubKey: block nil")
        }
        cert, err := x509.ParseCertificate(block.Bytes)
        if err != nil {
           return shim.Error("asset.go: getPubKey: x509 parse err!")
        }
        pub := cert.PublicKey.(*ecdsa.PublicKey)
	pubKey,_ :=  x509.MarshalPKIXPublicKey(pub)
	return shim.Success(pubKey)
}


func (s *DigitalAsset) createAsset(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	//if len(args) != 1 {
	//	return shim.Error("asset.go: createAsset: Incorrect number of arguments. Expecting one.")
	//}

	var str_ast string
	str_ast = ""

	for _ , value := range args{
		str_ast = str_ast + value
	}

	var ast Asset
    	err := json.Unmarshal([]byte(str_ast), &ast)
    	if err != nil {
        	return shim.Error("asset.go: createAsset: JSON cannot parse the input asset data! The input is:" + str_ast)
    	}

	//TODO we need to generate a gloable unique ID for this asset
	
	//var id string
	//id = "asset_1"
	//ast.AssetID = id

	AssetAsBytes, _ := json.Marshal(ast)
	APIstub.PutState(ast.ID, AssetAsBytes)

	str_ast = str_ast + "|" + ast.ID + "|" + ast.AuthorPUBKEY  

	return shim.Success([]byte(ast.ID))
}


func (s *DigitalAsset) saveCertificate(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	str_tx := s.spliceParameter(APIstub,args)

	var cert Certificate
    	err := json.Unmarshal([]byte(str_tx), &cert)
    	if err != nil {
        	return shim.Error("asset.go: saveCertificate: JSON cannot parse the input certificate data! ")//The input is:" + cert.CertData)
    	}
	CertAsBytes, _ := json.Marshal(cert)
	keyData := "ORG-certificate####" + cert.Cert
	certValue , _ := APIstub.GetState(keyData)
	if certValue == nil {
		APIstub.PutState(keyData, CertAsBytes)
		return shim.Success(nil)
	}else {
		return shim.Error("asset.go: saveCertificate: The certificate has already exsited:" + keyData)
	}
}





func (s *DigitalAsset) getCertByPK(APIstub shim.ChaincodeStubInterface, pubKey string) ([]byte,[]byte) {
	queryString := fmt.Sprintf("{\"selector\":{\"pubkey\":\"%s\"}}", pubKey)
	resultsIterator, err := APIstub.GetQueryResult(queryString)
	if err != nil {
	    //return shim.Error("asset.go: saveCertificate: JSON cannot parse the input certificate data! " + queryString)
	    return nil,[]byte("asset.go: getCertByPK: The queryString error"+ queryString)
	}
	defer resultsIterator.Close()
	
	var crt_temp Certificate
	
	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return nil,[]byte("asset.go: getCertByPK: resultsIterator.Next() error")
		}
		json.Unmarshal(queryResponse.Value, &crt_temp)
	}
	CertAsBytes, _ := json.Marshal(crt_temp)
	return CertAsBytes,nil	
}


func (s *DigitalAsset) getCertificateByPK(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	pubKey := s.spliceParameter(APIstub,args)
	
	result,err := s.getCertByPK(APIstub,pubKey)
	if err != nil {
		return shim.Error(string(err))
	}
	var crt_temp Certificate
	json.Unmarshal(result, &crt_temp)
	return shim.Success([]byte(crt_temp.Cert))	
}

func (s *DigitalAsset) getCertificate(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	account := s.spliceParameter(APIstub,args)

	keyData := "ORG-certificate####" + account
    	certAsBytes, _ := APIstub.GetState(keyData)
	if certAsBytes == nil {
		return shim.Error("asset.go: getCertificate: The account doesn't exsit:" + account)
	}else {
		var cert Certificate
    		err := json.Unmarshal(certAsBytes, &cert)
		if err != nil {
        		return shim.Error("asset.go: getCertificate: JSON cannot parse the certificate!")
    		}
		return shim.Success(certAsBytes)
	}    	
}


func (s *DigitalAsset) getAsset(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	//if len(args) != 1 {
	//	return shim.Error("asset.go: getAsset: Incorrect number of arguments. Expecting one.")
	//}

	var id string
	id = ""
	for _ , value := range args{
		id = id + value
	}
	
	//fmt.Printf("id=%s", id) //DEBUG

    	assetAsBytes, _ := APIstub.GetState(id)
    	return shim.Success(assetAsBytes)
}

func (s *DigitalAsset) createTX(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	
	var str_tx string
	str_tx = ""

	for _ , value := range args{
		str_tx = str_tx + value
	}

	var tx Transaction
    	err := json.Unmarshal([]byte(str_tx), &tx)
    	if err != nil {
        	return shim.Error("asset.go: createTX: JSON cannot parse the input transaction data! The input is:" + str_tx)
    	}

	TxAsBytes, _ := json.Marshal(tx)
	APIstub.PutState(tx.TxID, TxAsBytes)

	//str_tx = str_tx + "|" + tx.TxIn + "|" + tx.TxOut  

	return shim.Success([]byte(str_tx))


    	//err := json.Unmarshal([]byte(args[0]), &asset)
    	//if err != nil {
        //	return shim.Error("asset.go: createAsset: JSON cannot parse the input asset data!")
    	//}

	//TODO we need to generate a gloable unique ID for this asset
	//var ast Asset
	//var id string
	//id = "asset_1"
	//ast.AssetID = id

	//AssetAsBytes, _ := json.Marshal(ast)
	//APIstub.PutState(id, AssetAsBytes)

	//return shim.Success([]byte(id))
}


func (s *DigitalAsset) createUserFeature(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	if len(args) != 2 {
		return shim.Error("asset.go: createUserFeature: Incorrect number of arguments. Expecting two.")
	}

	var user_pki = args[0]

	user_pki = "UserFeature_" + user_pki

	var user_feature = args[1]
 
	 
	APIstub.PutState(user_pki, []byte(user_feature))

	return shim.Success([]byte(user_pki))
}

func (s *DigitalAsset) getUserFeature(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	//if len(args) != 1 {
	//	return shim.Error("asset.go: getAsset: Incorrect number of arguments. Expecting one.")
	//}

	var user_pki string
	user_pki = ""
	for _ , value := range args{
		user_pki = user_pki + value
	}
	
	user_pki = "ORG-certificate####"+ user_pki

	//fmt.Printf("id=%s", id) //DEBUG

    	featureAsBytes, _ := APIstub.GetState(user_pki)
    	return shim.Success(featureAsBytes)
}
 

func main() {
    err := shim.Start(new(DigitalAsset))
    if err != nil {
        fmt.Printf("Error creating new Smart Contract: %s", err)
    }
}
