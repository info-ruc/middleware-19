var crypto = require('crypto');

var createHASH = function (content){
	var sha256 = crypto.createHash('sha256');
 	sha256.update(content);  	 
	return sha256.digest('hex');
};
exports.createHASH = createHASH;
