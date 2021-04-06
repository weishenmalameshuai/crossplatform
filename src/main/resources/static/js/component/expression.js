var _regExp_={
	"required":{
		m:"该字段必填"
	},
	"r":{
		m:"该字段必填"
	},
	"int":{
		p:/^(-?[1-9]\d*)|0$/,
		m:"请输入整数"
	},
	"int+":{
		p:/^([1-9]\d*)|0$/,
		m:"请输入正整数"
	},
	"int-":{
		p:/^(-[1-9]\d*)|0$/,
		m:"请输入负整数"
	},
	"double":{
		p:/^-?([1-9]\d*.\d{0,2}|0.\d{0,2}|0?.\d{0,2}|0)$/,
		m:"请输入两位小数"
	},
	"double+":{
		p:/^([1-9]\d*.\d{0,2}|0.\d{0,2}|0?.\d{0,2}|0)$/,
		m:"请输入正两位小数"
	},
	"double-":{
		p:/^-([1-9]\d*.\d{0,2}|0.\d{0,2}|0?.\d{0,2}|0)$/,
		m:"请输入负两位小数"
	},
	"password":{
		p:/^[a-zA-Z][\w\_]{5,19}$/,
		m:"请输入6-20位字母开头密码"
	},
	"zh":{
		p:/^[^u4e00-u9fa5]*$/,
		m:"请输入汉字"
	},
	"en":{
		p:/^[A-Za-z]*$/,
		m:"请输入英文"
	},
	"email":{
		p:/^[a-zA-Z]+\w+\@\w+\.\w+$/,
		m:"请输入email"
	},
	"url":{
		p:/^((http|https)\:(\/\/)?)?(\w+\.)?\w+\.\w+(\/\w*)*$/,
		m:"请输入url"
	},
	"tel":{
		p:/^(\d{3,4}\-)?\d{7,8}$/,
		m:"请输入电话号码"
	},
	"idcard":{
		p:/^\d{14}(\d{1}|\X|\x|\d{4}|\d{3}X|\d{3}x)$/,
		m:"请输入身份证"
	},
	"mobile":{
		p:/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/,
		m:"请输入11位手机号"
	},
	"date":{
		p:/^(19|20)\d{2}-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|1\d|2\d|3[0-1])$/,
		m:"请输入yyyy-MM-dd格式日期"
	},
	"datetime":{
		p:/^(19|20)\d{2}-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|1\d|2\d|3[0-1])\s(\d|0\d|1\d|2[0-4]):[0-6]?\d:[0-6]?\d$/,
		m:"请输入yyyy-MM-dd HH:mm:ss格式时间"
	},
	"time":{
		p:/^(\d|0\d|1\d|2[0-4]):[0-6]?\d:[0-6]?\d$/,
		m:"请输入HH:mm:ss格式时间"
	}
};
function _sv(type){
	var list=type.split(",");
	var f=function(rule,value,callback){
		var rule=rule.rule.split(",");
		var msg="";
		var flag=true;
		for(var ele in rule){
			var valFlag=rule[ele];
			if(valFlag=="r"||valFlag=="required"){
				if(!value){
					flag=false;
					msg+="必填,";
				}
			}else{
				if(_regExp_[valFlag]){
					var exp=_regExp_[valFlag];
					if(!exp.p.test(value)){
						msg+=exp.m+",";
						flag=false;
					}
				}
			}
		}
		if(flag){
			callback();
		}else{
			callback(new Error(msg.substring(0,msg.length-1))); 
		}
	}
	var rule=type.split(",");
	var required=false;
	var message="";
	for(var ele in rule){
		if(rule[ele]=="r"||rule[ele]=="required"){
			required=true;
			message="必填";
		}
	}
	return {
		validator:f,trigger:"blur",rule:type,required:required,message:message
	};
}