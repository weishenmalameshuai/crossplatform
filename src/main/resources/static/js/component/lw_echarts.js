function getSize() {
    var body = document.body;
    var bodyWidth = body.scrollWidth;
    var bodyHeight = body.scrollHeight;
    return {
        width: bodyWidth,
        height: bodyHeight
    }
}
function sm(mes) {
    if (!mes) {
        mes = "请选择一行";
    }
    new Vue().$message.info({
        showClose: true,
        message: mes
    });
}

function sw(mes) {
    if (!mes) {
        mes = "操作失败";
    }
    new Vue().$message.warning({
        showClose: true,
        message: mes
    });
}

function se(mes) {
    if (!mes) {
        mes = "操作失败";
    }
    new Vue().$message.error({
        showClose: true,
        message: mes
    });
}

function ss(mes) {
    if (!mes) {
        mes = "操作成功";
    }
    new Vue().$message.success({
        showClose: true,
        message: mes
    });
}
function ff(src){ 
	 return Math.round(src*Math.pow(10, 2))/Math.pow(10, 2); 
 } 
function getJson(url,data,forceFlag){
	var obj;
	jQuery.ajax({
		async:false,
		data:(function(){
			if(data){
				return JSON.stringify(data);
			}else{
				return "{}";
			}
		})(),
		url:url,
		dataType:"json",
        contentType:"application/json",
		type:"post",
		success:function(resultObj){
			if(resultObj&&resultObj.code=="00000"){
				if(forceFlag){
					obj=resultObj;
				}else{
					obj=resultObj.result;
				}
			}else{
				if(forceFlag){
					obj=resultObj;
				}else{
					se("错误码:"+resultObj.code+",错误信息:"+resultObj.msg);
					obj=null;
				}
			}
		},
        error:function(errorData){
        	if(errorData.status!=200){
			    se('后端服务出错，请联系管理员')
				console.log(arguments);
        	}
		}
	});
	return obj;
}
Vue.component("fu", {
    props: {
        value: {
            type: String,
            default: "上传文件"
        },
        completeupload: {
            type: Function
        },
        filelimit: {
            type: String
        },
        tip: {
            type: String,
            default: "请上传文件"
        },
        action: {
            type: String,
            default: "../file/saveFile"
        },
        limit: {
            type: Number,
            default: 5
        }
    },
    data: function () {
        return {
            fileLength: 0,
            files: [],
            dShow: false,
            uploadLogo: 0,
            fileData: new FormData(),
            buttonS: false
        };
    },
    methods: {
        submitUpload: function () {
            this.fileData = new FormData();
            this.$refs.upload.submit();
            if (this.fileLength == 0) {
                return;
            }
            this.uploadLogo = "1";
            var that = this;
            that.buttonS = true;
            $.ajax({
                type: "post",
                url: that.action,
                data: that.fileData,
                contentType: false,
                processData: false,
                dataType: 'json',
                success: function (data) {
                    that.buttonS = false;
                    if (data) {
                        if (data.state == 1) {
                            that.uploadLogo = 2;
                            if (that.completeupload) {
                                that.completeupload(data, that);
                            }
                            that.files = [];
                            that.fileLength = 0;
                            that.dShow = false;
                        } else {
                            that.uploadLogo = 3;
                        }
                    } else {
                        that.uploadLogo = 3;
                    }
                },
                error: function (error) {
                    that.buttonS = false;
                    that.uploadLogo = 3;
                }
            });
        },
        uploadFile: function (file) {
            this.fileData.append(file.file.name, file.file);
            this.fileLength++;
        },
        close: function () {
            this.dShow = false;
        },
        open: function () {
            this.uploadLogo = 0;
            this.dShow = true;
            this.fileLength = 0;
            this.files = [];
            this.buttonS = false;
        },
        fileTypeHandler: function (file) {
            if (this.filelimit) {
                var fs = this.filelimit.split(",");
                var flag = false;
                for (var ele in fs) {
                    if (file.name.endWith("." + fs[ele])) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    se("不是" + this.filelimit + "文件");
                    return false;
                }
            }
            return true;
        }
    },
    template: '<div style="display:inline;margin:0px 10px" class="fu">\
				<el-button @click="open" type="primary" size="mini">{{value}}</el-button>\
				<el-dialog :visible.sync="dShow" height="300px">\
					<el-upload ref="upload" :before-upload="fileTypeHandler" :http-request="uploadFile" :auto-upload="false" :limit = "limit" :multiple="true" :file-list="files" :action="action">\
						<el-button slot="trigger" :disabled.sync="buttonS" size="small" type="primary">请选择文件</el-button>\
						<el-button size="small":disabled.sync="buttonS" type="success" @click="submitUpload">上传</el-button>\
						<div slot="tip">{{tip}}</div>\
						<el-tag v-show="uploadLogo==\'1\'" >上传中</el-tag>\
					    <el-tag v-show="uploadLogo==\'2\'" type="success">上传成功</el-tag>\
					    <el-tag v-show="uploadLogo==\'3\'" type="danger">上传失败</el-tag>\
					</el-upload>\
				</el-dialog>\
		</div>'
});