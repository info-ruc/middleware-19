var crypto = require('crypto');

function signer(args){
    var algorithm =args[0];
    var key =args[1];
    var data =args[2];
    var sign = crypto.createSign(algorithm);
    sign.update(data);
    var sig = sign.sign(key, 'hex');
    return sig;
}
