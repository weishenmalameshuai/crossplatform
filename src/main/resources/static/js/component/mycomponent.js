String.prototype.endWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substring(this.length - str.length) == str)
        return true;
    else
        return false;
    return true;
}

function copy(obj) {
    var result = {};
    for (var ele in obj) {
        result[ele] = obj[ele];
    }
    return result;
}

String.prototype.startWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substr(0, str.length) == str)
        return true;
    else
        return false;
    return true;
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
Vue.component("fu2", {
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
    template: '<div style="display:inline" class="fu">\
				<el-upload ref="upload" :before-upload="fileTypeHandler" :http-request="uploadFile" :auto-upload="false" :limit = "limit" :multiple="true" :file-list="files" :action="action">\
						<el-button slot="trigger" :disabled.sync="buttonS" size="small" type="primary">请选择文件</el-button>\
						<el-button size="small":disabled.sync="buttonS" type="success" @click="submitUpload">上传</el-button>\
						<div slot="tip">{{tip}}</div>\
						<el-tag v-show="uploadLogo==\'1\'" >上传中</el-tag>\
					    <el-tag v-show="uploadLogo==\'2\'" type="success">上传成功</el-tag>\
					    <el-tag v-show="uploadLogo==\'3\'" type="danger">上传失败</el-tag>\
				</el-upload>\
		</div>'
});
Vue.component('datatable', {
    props: {
        showsort: {
            type: Boolean,
            default: true
        },
        sortby: {
            type: String
        },
        orderby: {
            type: String
        },
        url: {
            type: String,
            default: "getData"
        },
        cols: {
            type: Array,
            required: true
        },
        colwidth: {
            type: String,
            default: null
        },
        rows: {
            type: Array,
            default: function () {
                return [];
            }
        },
        currentPage: {
            type: Number,
            default: 1
        },
        pageSize: {
            type: Number,
            default: 10
        },
        total: {
            type: Number,
            default: 0
        },
        parameter: {
            type: Object,
            default: function () {
                return {};
            }
        },
        tableheight: {
            type: String,
            default: "100%"
        },
        selection: {
            type: Boolean,
            default: false
        },
        autoheight: {
            type: Boolean,
            default: true
        },
        playout: {
            type: String,
            default: "total, sizes, prev, pager, next, jumper"
        },
        rowclassname: {
            type: Function
        },
        otherelid: {//非table项占用高度的$el id
            type: Array,
            default: function () {
                return ["queryformwrap"];
            }
        },
        qfid: {//queryform id
            type: String,
            default: "queryformwrap"
        },
        selectable: {
            type: Function
        }
    },
    mounted: function () {
        if (this.autoheight) {
            var _this = this;
            setTableHeight_(_this);
            window.onresize = function () {
                setTableHeight_(_this);
            }
        }
    },
    data: function () {
        return {
            current: null,
            mapperMap: null,
            selectList: [],
            editIdx: null,
            editRow: null,
            pdfShowFlag: false,
            pdfUrl: "",
            pdfHeight: "460px"
        }
    },
    watch: {
        currentPage: function () {
            this.flushData();
        },
        pageSize: function () {
            this.flushData();
        }
    },
    methods: {
        sortTable: function (data) {
            if (data.prop && data.order) {
                this.parameter._sort_ = {
                    sort: data.prop.endsWith("_name_") ? data.prop.replace("_name_", "") : data.prop,
                    order: data.order.replace("ending", "")
                };
                this.flushData();
            } else {
                delete this.parameter._sort_;
                this.flushData();
            }
        },
        pdf: function (classInfo, title, fileName, detailData) {
            var result = getJson("../pdfUtil/getPdfPage", {
                classInfo: classInfo,
                colInfo: this.cols,
                title: title || null,
                fileName: fileName || null,
                detailData: detailData || null,
                parameterInfo: $.extend(this.formatParameter(this.parameter), {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                })
            });
            $("body").append('<form id="tempFile" target="_blank" action="../downloadUtil/downloadFromTemp" style="display:none"><input name="newname" value="' + result.newname + '"><input name="oldname" value="' + result.oldname + '"></form>');
            $("#tempFile").submit();
            $("#tempFile").remove();
        },
        previewPDF: function (classInfo, detailData) {
            var result = getJson("../pdfUtil/getPdfPage", {
                classInfo: classInfo,
                colInfo: this.cols,
                detailData: detailData || null,
                parameterInfo: $.extend(this.formatParameter(this.parameter), {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                })
            });
            this.pdfShowFlag = true;
            this.pdfHeight = (document.getElementsByTagName("html")[0].offsetHeight - 76) * 0.9 + "px";
            this.pdfUrl = "../pdf/web/viewer.html?file=../../pdfUtil/getPdfData/" + result.newname;
        },
        excel: function (classInfo, title, fileName, detailData) {
            if (!classInfo) {
                var routeStr = window.location.href;
                routeStr = routeStr.substring(7, routeStr.length);
                var routeArr = routeStr.split("/");
                classInfo = routeArr[routeArr.length - 2] + "." + this.url;
            }
            var result = getJson("../excelUtil/getExcelPage", {
                classInfo: classInfo,
                colInfo: this.cols,
                title: title || null,
                fileName: fileName || null,
                detailData: detailData || null,
                parameterInfo: $.extend(this.formatParameter(this.parameter), {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                })
            });
            $("body").append('<form id="tempFile" target="_blank" action="../downloadUtil/downloadFromTemp" style="display:none"><input name="newname" value="' + result.newname + '"><input name="oldname" value="' + result.oldname + '"></form>');
            $("#tempFile").submit();
            $("#tempFile").remove();
        },
        formatParameter: function (data) {
            var result = {};
            for (var ele in data) {
                var key = ele;
                var value = data[ele];
                if (value) {
                    if (value.constructor == Array) {
                        if ((key + "").endsWith("_last")) {
                            result[key.replace("_last", "")] = value[value.length - 1];
                        } else {
                            result[key + "_gteq"] = value[0];
                            result[key + "_lteq"] = value[1];
                        }
                    } else {
                        result[key] = value;
                    }
                }
            }
            ;
            return result;
        },
        handleSizeChange: function (pSize) {
            this.pageSize = pSize;
        },
        handleCurrentChange: function (cPage) {
            this.currentPage = cPage;
        },
        flushData: function () {
            var result = getJson(this.url, $.extend(this.formatParameter(this.parameter), {
                currentPage: this.currentPage,
                pageSize: this.pageSize
            }));
            if (result.total <= this.pageSize && this.currentPage != 1) {
                this.currentPage = 1;
                this.flushData();
                return false;
            }
            if (this.mapperMap) {
                for (var ele in this.cols) {
                    if (this.cols[ele].mapper) {
                        for (var ele1 in result.rows) {
                            result.rows[ele1][this.cols[ele].key] = this.mapperMap[this.cols[ele].mapper][result.rows[ele1][this.cols[ele].oldkey]];
                        }
                    }
                }
            }
            ;
            this.rows = result.rows;
            this.total = result.total;
            this.current = null;
        },
        selectRow: function (newRow, oldRow) {
            this.current = newRow;
            this.$emit("current-change", newRow, oldRow);
        },
        handleSelectionChange: function (val) {
            this.selectList = val;
        },
        setEdit: function (idx, row, data) {
            this.editIdx = idx;
            this.editRow = row;
        },
        getCurrent: function () {
            return this.current;
        },
        setParameter: function (data) {
            this.parameter = data;
        },
        getData: function () {
            return this.rows
        }
    },
    template: '<div class="datatablewrap">\
		<el-table @sort-change="sortTable" class="datatable" :row-class-name="rowclassname" :data="rows" style="width:100%" :height="tableheight" size="mini" stripe border highlight-current-row @current-change="selectRow" @selection-change="handleSelectionChange" tooltip-effect="light">\
			<el-table-column type="selection" width="55" v-if="selection" :selectable="selectable">\
			</el-table-column>\
			<el-table-column :sortable="showsort?\'custom\':false" v-for="(col1,key,index1) in cols" :width="col1.width?col1.width:colwidth"  :prop="col1.key" :label="col1.value" :show-overflow-tooltip="col1.tip?true:false">\
				<templete slot-scope="scope">\
					<el-button v-if="col1.type==\'button\'&&(!bu.filter||bu.filter(scope.row))"  v-for="(bu,key1) in col1.buttons" size="mini" @click="bu.handler(scope.$index, scope.row)">{{bu.value}}</el-button>\
					<div v-if="!col1.type||(editIdx!=scope.$index||editRow!=col1.key)" @click="setEdit(scope.$index,col1.key,scope)" :style="col1.style?col1.style(scope.row[col1.key]):\'\'">{{scope.row[col1.key]===null||scope.row[col1.key]===undefined||scope.row[col1.key]===""?"&nbsp;&nbsp;":(col1.formatter?col1.formatter(scope.row[col1.key]):scope.row[col1.key])}}</div>\
					<el-input size="mini" v-if="col1.type==\'string\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-input>\
					<el-date-picker type="date" placeholder="选择日期" size="mini" v-if="col1.type==\'date\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-date-picker>\
					<el-date-picker type="datetime" placeholder="选择日期时间" size="mini" v-if="col1.type==\'datetime\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-date-picker>\
					<el-input-number controls-position="right" size="mini" v-if="col1.type==\'number\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-input-number>\
				</templete>\
				<el-table-column :sortable="showsort?\'custom\':false" v-if="col1.children.length>0" v-for="col2 in col1.children" :prop="col2.key" :label="col2.value">\
					<templete slot-scope="scope">\
						<el-button v-if="col1.type==\'button\'&&(!bu.filter||bu.filter(scope.row))"  v-for="(bu,key1) in col1.buttons" size="mini" @click="bu.handler(scope.$index, scope.row)">{{bu.value}}</el-button>\
						<div v-if="!col1.type||(editIdx!=scope.$index||editRow!=col1.key)" @click="setEdit(scope.$index,col1.key,scope)">{{scope.row[col1.key]===null||scope.row[col1.key]===undefined||scope.row[col1.key]===""?"&nbsp;&nbsp;":scope.row[col1.key]}}</div>\
						<el-input size="mini" v-if="col1.type==\'string\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-input>\
						<el-date-picker type="date" placeholder="选择日期" size="mini" v-if="col1.type==\'date\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-date-picker>\
						<el-date-picker type="datetime" placeholder="选择日期时间" size="mini" v-if="col1.type==\'datetime\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-date-picker>\
						<el-input-number controls-position="right" size="mini" v-if="col1.type==\'number\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-input-number>\
					</templete>\
				</el-table-column>\
			</el-table-column>\
		</el-table>\
		<el-pagination  @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :layout="playout" :total="total">\
		</el-pagination>\
		<el-dialog title="导出PDF预览" :visible.sync="pdfShowFlag" top="15px" width="80%">\
			<iframe :src="pdfUrl" width="100%" :height.sync="pdfHeight"></iframe>\
		</el-dialog>\
		</div>\
	</div>',
    created: function () {
        var mapperStr = [];
        if (this.sortby && this.orderby) {
            this.parameter._sort_ = {
                sort: this.sortby,
                order: this.orderby
            };
        }
        for (var i in this.cols) {
            if (this.cols[i].mapper) {
                mapperStr.push(this.cols[i].mapper);
                this.cols[i].oldkey = this.cols[i].key;
                this.cols[i].key = this.cols[i].key + "_name_";
            }
        }
        if (mapperStr.length > 0) {
            this.mapperMap = getJson("../dataUtil/getMapper", mapperStr);
        }
        this.flushData();
    }
});
Vue.component("queryform", {
    props: {
        items: {
            type: Array,
            required: true
        },
        data: {
            type: Object,
            default: function () {
                return {};
            }
        },
        buttons: {
            type: Array
        },
        labelwidth: {
            type: String,
            default: "140px"
        }
    },
    template: '<div id="queryformwrap">\
		<el-form class="queryform" label-width="labelwidth" :inline="true" :model="data" @submit.native.prevent>\
			<el-form-item v-for="item in items" :label="item.value">\
				<el-input size="mini" v-if="!item.type" v-model="data[item.key]" :placeholder="item.value" :clearable="item.clearable?item.clearable:true"></el-input>\
				<el-select size="mini" v-if="item.type==\'select\'" v-model="data[item.key]" filterable placeholder="请选择" :clearable="item.clearable?item.clearable:true">\
					<el-option v-for="option in item.children" :key="option.key" :label="option.value" :value="option.key"></el-option>\
				</el-select>\
				<el-select size="mini" collapse-tags multiple v-if="item.type==\'selectMultiple\'" v-model="data[item.key]" filterable placeholder="请选择" :clearable="item.clearable?item.clearable:true">\
					<el-option v-for="option in item.children" :key="option.key" :label="option.value" :value="option.key"></el-option>\
				</el-select>\
				<el-cascader size="mini" :show-all-levels="false" v-if="item.type==\'cascader\'" :disabled="data.disabled||item.disabled" :props="{expandTrigger:\'hover\',label:\'value\',value:\'key\',emitPath:false,checkStrictly:true}" :options="item.options||item.children" v-model="data[item.key]"  :clearable="item.clearable?item.clearable:true"></el-cascader>\
				<el-date-picker size="mini"  v-if="item.type==\'date\'" v-model="data[item.key]" type="daterange" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :clearable="item.clearable?item.clearable:true"></el-date-picker>\
				<el-date-picker size="mini" v-if="item.type==\'datetime\'" v-model="data[item.key]" type="datetimerange" value-format="yyyy-MM-dd hh:mm:ss" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :clearable="item.clearable?item.clearable:true"></el-date-picker>\
				<el-date-picker size="mini" v-if="item.type==\'date-year\'" v-model="data[item.key]" type="year" value-format="yyyy-MM-dd hh:mm:ss" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :clearable="item.clearable?item.clearable:true"></el-date-picker>\
				<el-autocomplete size="mini" @focus="setAutoData(item.children,item.handler)" v-if="item.type==\'autocomplete\'" :fetch-suggestions="querySearch" @select="handlerSelect" v-model="data[item.key]" placeholder="请输入内容" :clearable="item.clearable?item.clearable:true"></el-autocomplete>\
			</el-form-item>\
			<el-form-item v-if="buttons&&buttons.length>0" class="qfbtn">\
				<div v-for="item in buttons" style="display: inline-block;">\
					<el-button style="float:left;margin-left:10px;" size="mini" v-if="!item.type&&item.value==\'查询\'" type="primary" @click="item.handler.call($root)" >{{item.value}}</el-button>\
					<el-button style="float:left;margin-left:10px;" size="mini" v-if="!item.type&&item.value==\'重置\'" plain @click="item.handler.call($root)">重置</el-button>\
				</div>\
			</el-form-item>\
		</el-form>\
		<div v-if="buttons&&buttons.length>0" class="btnrow">\
			<span v-for="item in buttons">\
				<el-button size="mini" v-if="!item.type&&item.value!=\'查询\'&&item.value!=\'重置\'" :type="item.style?item.style:\'primary\'" @click="item.handler.call($root)" >{{item.value}}</el-button>\
				<fu v-if="item.type==\'fu\'" :value="item.value" :completeupload="item.handler" :tip="item.tip" :fileType="item.fileType" :action="item.action"></fu>\
			</span>\
		</div>\
	<div>',
    methods: {
        getData: function () {
            return this.data;
        },
        setData: function (data1) {
            this.data = data1;
        },
        querySearch: function (queryString, cb) {
            var autoData = this.autoData;
            var results = queryString ? autoData.filter(this.createFilter(queryString)) : autoData;
            cb(results);
        },
        handlerSelect: function (data) {
            if (this.autoHandler) {
                this.autoHandler.call(this.$root, data);
            }
        },
        setAutoData: function (data, handler) {
            this.autoData = data;
            this.autoHandler = handler
        },
        createFilter: function (queryString) {
            return function (autoData) {
                return (autoData.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
        }
    },
    data: {
        autoData: [],
        autoHandler: null
    }
});
Vue.component("detailform", {
    props: {
        items: {
            type: Array,
            required: true
        },
        rules: {
            type: Object,
            default: {}
        },
        buttons: {
            type: Array,
            default: []
        },
        data: {
            type: Object,
            default: {}
        },
        labelwidth: {
            type: String,
            default: "120px"
        },
        childref: {
            type: String,
            default: ""
        },
        disabled: {
            type: Boolean,
            default: false
        },// 是否跳转新页面
        newpage: {
            type: Boolean,
            default: false
        },
        inline: {// 行内表单
            type: Boolean,
            default: true
        },
        url: {
            type: String,
            default: ""
        },
        id: {
            type: String,
            default: ""
        },// 是否有机构级联菜单
        exsistorgitem: {
            type: Boolean,
            default: false
        },// 是否有补贴多选菜单
        exsistcapitalitem: {
            type: Boolean,
            default: false
        }
    },
    methods: {
        getForm: function () {
            return this.$refs.ef;
        },
        validate: function () {
            return this.$refs.ef.validate();
        },
        resetFields: function () {
            return this.$refs.ef.resetFields();
        },
        formatCascader: function (data) {
            var orgListStr = data.org_list;
            orgListArr = orgListStr.split(",");
            for (var j = 0, len = orgListArr.length; j < len; j++) {
                orgListArr[j] = parseInt(orgListArr[j])
            }
            data.org_list = orgListArr;
            return data;
        },
        formatCapitalSelect: function (data) {
            if (data.capitals != null) {
                var str = data.capitals;
                arr = str.split(",");
                for (var j = 0, len = arr.length; j < len; j++) {
                    arr[j] = parseInt(arr[j])
                }
                data.capitals = arr;
                return data;
            } else {
                data.capitals = [];
                return data;
            }
        },
        querySearch: function (queryString, cb) {
            var autoData = this.autoData;
            var results = queryString ? autoData.filter(this.createFilter(queryString)) : autoData;
            cb(results);
        },
        setAutoData: function (data, handler) {
            this.autoData = data;
            this.autoHandler = handler;
        },
        selectHandler: function (data) {
            if (this.autoHandler) {
                this.autoHandler.call(this.$root, data);
            }
        },
        createFilter: function (queryString) {
            return function (autoData) {
                return (autoData.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
        },
        setBank: function (obj) {
            this.data.bank = obj.name;
            this.data.bank_no = obj.key;
            this.data.khyh = obj.value;
        },
        setDeptCode: function (code) {
            this.data.org_code = code;
        },
        setDeptName: function (name) {
            this.data.org_name = name;
        },
        setDeptId: function (id) {
            this.data.depart_id = id;
        },
        getData: function () {
            return this.data;
        },
        clearValidate: function () {
            return this.$refs.ef.clearValidate();
        }

    },
    created: function () {
        var org = this.exsistorgitem;
        var capitals = this.exsistcapitalitem;
        var id = this.id;
        var result;
        var data = {};
        if (this.newpage == "true") {
            if (id != "" && id != null) {
                data.id = id;
            } else {
                return;
            }
            result = getJson(this.url, data);
            if (org == "true") {
                this.data = this.formatCascader(result);
            }
            if (capitals == "true") {
                this.data = this.formatCapitalSelect(result);
            } else {
                this.data = result;
            }
        }
    },
    mounted: function () {
        setRowHeight(this);
    },
    data: {
        autoData: [],
        autoHandler: null
    },
    template: '<el-form ref="ef" class="detailform" :class="{ disabled: disabled }" :model="data" :rules="rules" :label-width="labelwidth" :inline="inline">' +
        '<div v-for="(item,index) in items" v-if="item.visable!==false" :style="item.style" :class="{\'remarks\':item.value==\'备注\',\'col\':true}">' +
        '<el-form-item  :label="item.value" :prop="item.key":style="{ paddingLeft: labelwidth}">' +
        '<el-radio-group size="small" v-model="data[item.key]" v-if="item.type==\'radio\'"><el-radio v-if="item.children.length>0" v-for="item1 in item.children" :disabled="disabled||item1.disabled" :label="item1.key">{{item1.value}}</el-radio></el-radio-group>' +
        '<el-checkbox size="small" v-if="item.children.length>0" v-for="item1 in item.children" v-if="item.type==\'checkbox\'":disabled="disabled||item.disabled" v-model="data[item1.key]">{{item1.value}}</el-checkbox>' +
        '<el-input size="small" v-if="!item.type||item.type==\'input\'" :disabled="disabled||item.disabled" :placeholder="(disabled||item.disabled)?\'\':\'请输入内容\'" v-model="data[item.key]" clearable="item.clear?item.clearable:true"></el-input>' +
        '<el-input size="small" v-if="item.type==\'text\'" :disabled="disabled||item.disabled" :placeholder="item.value" v-model="data[item.key]" clearable="item.clear?item.clearable:true"></el-input>' +
        '<el-input size="small" show-password v-if="item.type==\'password\'" :placeholder="(disabled||item.disabled)?\'\':\'请输入密码\'" type="password" :disabled="disabled||item.disabled" :placeholder="item.value" v-model="data[item.key]" clearable="item.clear?item.clearable:true"></el-input>' +
        '<el-input size="small" v-if="item.type==\'textarea\'" :disabled="disabled||item.disabled" type="textarea"  :rows="item.rows?item.rows:2"  :placeholder="item.placeholder?item.placeholder:item.value"  v-model="data[item.key]" clearable="item.clear?item.clearable:true"></el-input>' +
        '<el-input style="width: 230%" size="small" v-if="item.type==\'textarea2\'" :disabled="disabled||item.disabled" type="textarea"  :rows="item.rows?item.rows:2"  :placeholder="item.placeholder?item.placeholder:item.value"  v-model="data[item.key]" clearable="item.clear?item.clearable:true"></el-input>' +
        '<el-cascader @change="item.change" size="small" v-if="item.type==\'cascader\'" :disabled="disabled||item.disabled" :props="{label:\'value\',value:\'key\',emitPath:false,checkStrictly:true,expandTrigger:\'hover\'}" :show-all-levels="false" :options="item.options||item.children" v-model="data[item.key]" change-on-select ></el-cascader>' +
        '<el-cascader @change="item.change" size="small" v-if="item.type==\'muliselect\'" filterable :disabled="disabled||item.disabled" :props="{label:\'value\',value:\'key\',emitPath:false}" :options="item.options||item.children" v-model="data[item.key]"  ></el-cascader>' +
        '<el-select filterable  @change="item.change" size="small" :multiple="item.multiple" :disabled="disabled||item.disabled" v-if="item.type==\'select\'" v-model="data[item.key]" :placeholder="(disabled||item.disabled)?\'\':\'请选择\'" clearable="item.clear?item.clearable:true"><el-option  v-if="item.children.length>0" v-for="item1 in item.children" :label="item1.value" :key="item1.key" :value="item1.key"></el-option></el-select>' +
        '<el-date-picker size="small" v-if="item.type==\'date\'" :disabled="disabled||item.disabled" :placeholder="(disabled||item.disabled)?\'\':\'选择日期\'" type="date" v-model="data[item.key]" clearable="item.clear?item.clearable:true"></el-date-picker>' +
        '<el-date-picker size="small" v-if="item.type==\'date-year\'" :disabled="disabled||item.disabled" :placeholder="(disabled||item.disabled)?\'\':\'选择年份\'" type="year" v-model="data[item.key]" clearable="item.clear?item.clearable:true"></el-date-picker>' +
        '<el-date-picker size="small" v-if="item.type==\'datetime\'" :disabled="disabled||item.disabled" :placeholder="(disabled||item.disabled)?\'\':\'选择日期时间\'" type="datetime" v-model="data[item.key]" clearable="item.clear?item.clearable:true"></el-date-picker>' +
        '<el-autocomplete size="small" @focus="setAutoData(item.children,item.handler)" :disabled="disabled||item.disabled" v-if="item.type==\'autocomplete\'" :fetch-suggestions="querySearch" @select="selectHandler" v-model="data[item.key]" :placeholder="(disabled||item.disabled)?\'\':\'请输入内容\'" clearable></el-autocomplete>' +
        '<el-button @click="item.handler.call($root)" v-if="item.type==\'button_choose\'">点击选择</el-button>' +
        '<el-button @click="item.handler.call($root)" v-if="item.type==\'button_download\'">点击下载</el-button>' +
        '<el-button @click="item.handler.call($root)" v-if="item.type==\'button_view\'">点击查看</el-button>' +
        '<el-button @click="item.handler.call($root)" v-if="item.type==\'button\'">{{item.button_text?item.button_text:"点击"}}</el-button>' +
        '</el-form-item>' +
        '</div><div class="clear"></div>' +
        '<el-form-item v-if="buttons.length>0" class="formbtn"><span v-for="item in buttons" style="padding: 5px;">' +
        '<el-button type="primary" plain size="small" v-if="item.value==\'返回\'"  @click="item.handler.call($root)" :disabled.sync="item.disabled">{{item.value}}</el-button>' +
        '<el-button type="primary" size="small" v-if="item.value==\'保存\'"  @click="item.handler.call($root)" :disabled.sync="item.disabled">{{item.value}}</el-button>' +
        '<el-button plain size="small" v-if="item.value==\'重置\'"  @click="item.handler.call($root)" :disabled.sync="item.disabled">重置</el-button>' +
        '<el-button size="small" v-if="item.value!=\'返回\'&&item.value!=\'保存\'&&item.value!=\'重置\'" @click="item.handler.call($root)" :disabled.sync="item.disabled">{{item.value}}</el-button>' +
        '</span></el-form-item>' +
        '<div class="clear"></div></el-form>'
});
Vue.component("datatableall", {
    props: {
        url: {
            type: String,
            default: "getData"
        },
        showsummary: {
            type: Boolean,
            default: false
        },
        cols: {
            type: Array,
            required: true
        },
        rows: {
            type: Array,
            default: function () {
                return [];
            }
        },
        autopadding: {
            type: Number,
            default: 0
        },
        maxrow: {
            type: Number,
            default: 500
        },
        remote: {
            type: Boolean,
            default: true
        },
        parameter: {
            type: Object,
            default: function () {
                return {};
            }
        },
        rowclick: {
            type: Function
        },
        tableheight: {
            type: String,
            default: "100%"
        },
        colwidth: {
            type: String,
            default: null
        },
        autoheight: {
            type: Boolean,
            default: false
        },
        rowclassname: {
            type: Function
        },
        selection: {
            type: Boolean,
            default: false
        },
        otherelid: {//非table项占用高度的$el id
            type: Array,
            default: function () {
                return [];
            }
        },
        showrownumber: {
            type: Boolean,
            default: false
        },
        selectable: {
            type: Function
        },
        is_static: {
            type: Boolean,
            default: false
        }
    },
    data: function () {
        return {
            current: null,
            mapperMap: null,
            editIdx: null,
            editRow: null,
            pdfShowFlag: false,
            pdfUrl: "",
            pdfHeight: "460px",
            selectList: [],
            rrows: null,
            currentPage: 1,
            showpagination: false,
            playout: {
                type: String,
                default: "total, prev, pager, next, jumper"
            },
            tableStyle: null
        }
    },
    mounted: function () {
        if (this.autoheight) {
            var _this = this;
            setTableHeight_(_this);
            window.onresize = function () {
                setTableHeight_(_this);
            }
        }
        if (this.rows != null && this.rows.length > 0) {
            this.rrows = [];
            if (this.rows.length > this.maxrow) {
                this.currentPage = 1;
                this.showpagination = true;
                this.rrows = this.rows.slice((this.currentPage - 1) * this.maxrow, this.currentPage * this.maxrow - 1);
            } else {
                this.showpagination = false;
                this.rrows = this.rows;
            }
        }
    },
    watch: {
        rows: function () {
            if (this.rows != null) {
                for (var i = 0; i < this.rows.length; i++) {
                    this.rows[i]["_rindex_"] = i;
                }
                this.rrows = [];
                if (this.rows.length > this.maxrow) {
                    this.currentPage = 1;
                    this.showpagination = true;
                    this.rrows = this.rows.slice((this.currentPage - 1) * this.maxrow, this.currentPage * this.maxrow - 1);
                } else {
                    this.showpagination = false;
                    this.rrows = this.rows;
                }
            }
        },
        currentPage: function (newValue) {
            this.rrows = this.rows.slice((newValue - 1) * this.maxrow, newValue * this.maxrow - 1);
        },
        showpagination: function () {
            var tableHeight = this.$refs.dta.$el.clientHeight;
            if (this.showpagination) {
                this.tableStyle = {
                    height: (tableHeight - 35) + "px"
                }
            } else {
                this.tableStyle = {
                    height: (tableHeight + 35) + "px"
                }
            }
        }
    },
    template: '<div style="height:100%;width:100%;">\
				<el-table fit class="datatable" ref="dta" :show-summary="showsummary" v-bind:style="tableStyle" :height="tableheight" :row-class-name="rowclassname" @row-click="clickHandler" :data="rrows" style="width:100%"  size="mini" stripe border highlight-current-row @current-change="selectRow" @selection-change="handleSelectionChange">\
					<el-table-column type="selection" width="55" v-if="selection" :selectable="selectable">\
					</el-table-column>\
					<el-table-column type="index" v-if="showrownumber"  width="50"> \
				    </el-table-column>\
					<el-table-column :fixed="col1.fixed" v-for="(col1,key,index1) in cols" :width="colwidth?colwidth:col1.width" :prop="col1.key" :label="col1.value" >\
						<templete slot-scope="scope">\
							<el-button v-if="col1.type==\'button\'&&(!bu.filter||bu.filter(scope.row))"  v-for="(bu,key1) in col1.buttons" size="mini" :type="bu.type" @click="bu.handler(scope.$index, scope.row)">{{bu.value}}</el-button>\
							<div v-if="!col1.type||(editIdx!=scope.$index||editRow!=col1.key)" @click="setEdit(scope.$index,col1.key,scope)"  :style="col1.style?col1.style(scope.row[col1.key]):\'\'">{{scope.row[col1.key]===null||scope.row[col1.key]===undefined||scope.row[col1.key]===""?"&nbsp;&nbsp;":(col1.formatter?col1.formatter(scope.row[col1.key]):scope.row[col1.key])}}</div>\
							<el-input size="mini" v-if="col1.type==\'string\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-input>\
							<el-date-picker type="date" placeholder="选择日期" size="mini" v-if="col1.type==\'date\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-date-picker>\
							<el-date-picker type="datetime" placeholder="选择日期时间" size="mini" v-if="col1.type==\'datetime\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-date-picker>\
							<el-input-number controls-position="right" size="mini" v-if="col1.type==\'number\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-input-number>\
						</templete>\
						<el-table-column :fixed="col1.fixed" v-if="col1.children.length>0" v-for="col2 in col1.children" :prop="col2.key" :label="col2.value">\
							<templete slot-scope="scope">\
								<el-button v-if="col1.type==\'button\'&&(!bu.filter||bu.filter(scope.row))"  v-for="(bu,key1) in col1.buttons" size="mini" @click="bu.handler(scope.$index, scope.row)">{{bu.value}}</el-button>\
								<div v-if="!col1.type||(editIdx!=scope.$index||editRow!=col1.key)" @click="setEdit(scope.$index,col1.key,scope)">{{scope.row[col1.key]===null||scope.row[col1.key]===undefined||scope.row[col1.key]===""?"&nbsp;&nbsp;":scope.row[col1.key]}}</div>\
								<el-input size="mini" v-if="col1.type==\'string\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-input>\
								<el-date-picker type="date" placeholder="选择日期" size="mini" v-if="col1.type==\'date\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-date-picker>\
								<el-date-picker type="datetime" placeholder="选择日期时间" size="mini" v-if="col1.type==\'datetime\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-date-picker>\
								<el-input-number controls-position="right" size="mini" v-if="col1.type==\'number\'&&editIdx==scope.$index&&editRow==col1.key" v-model="scope.row[col1.key]"></el-input-number>\
							</templete>\
						</el-table-column>\
					</el-table-column>\
			</el-table>\
			<div v-if="showpagination" style="box-sizing:border-box;bottom:0;width:100%;padding:5px;">\
				<el-pagination\
				  small\
				  layout="prev, pager, next"\
				  :total="rows.length" :current-page.sync="currentPage" :page-size="maxrow">\
				</el-pagination>\
			</div>\
			<el-dialog title="导出PDF预览" :visible.sync="pdfShowFlag" top="15px" width="80%">\
				<iframe :src="pdfUrl" width="100%" :height.sync="pdfHeight"></iframe>\
			</el-dialog>\
		</div>',
    methods: {
        clickHandler: function (a, b, c) {
            this.$emit("row-click", a, b, c);
        },
        handleSelectionChange: function (val) {
            this.selectList = val;
        },
        formatParameter: function (data) {
            var result = {};
            for (var ele in data) {
                var key = ele;
                var value = data[ele];
                if (value) {
                    if (value.constructor == Array) {
                        result[key + "_gteq"] = value[0];
                        result[key + "_lteq"] = value[1];
                    } else {
                        result[key] = value;
                    }
                }
            }
            ;
            return result;
        },
        pdf: function (title, fileName, detailData) {
            var result = getJson("../pdfUtil/getPdfAll", {
                data: this.rows,
                colInfo: this.cols,
                title: title || null,
                detailData: detailData || null,
                fileName: fileName || null
            });
            $("body").append('<form id="tempFile" target="_blank" action="../downloadUtil/downloadFromTemp" style="display:none"><input name="newname" value="' + result.newname + '"><input name="oldname" value="' + result.oldname + '"></form>');
            $("#tempFile").submit();
            $("#tempFile").remove();
        },
        excel: function (title, fileName, detailData) {
            var tempData = JSON.parse(JSON.stringify(this.rows))
            this.cols.forEach(function (col) {
                if (col.formatter) {
                    tempData.forEach(function (data) {
                        data[col.key] = col.formatter(data[col.key])
                    })
                }
            })
            var result = getJson("../excelUtil/getExcelAll", {
                data: tempData,
                colInfo: this.cols,
                title: title || null,
                detailData: detailData || null,
                fileName: fileName || null
            });
            $("body").append('<form id="tempFile" target="_blank" action="../downloadUtil/downloadFromTemp" style="display:none"><input name="newname" value="' + result.newname + '"><input name="oldname" value="' + result.oldname + '"></form>');
            $("#tempFile").submit();
            $("#tempFile").remove();
        },
        previewPDF: function (title, detailData) {
            var result = getJson("../pdfUtil/getPdfAll", {
                data: this.rows,
                colInfo: this.cols,
                title: title || null,
                detailData: detailData || null,
                parameterInfo: $.extend(this.formatParameter(this.parameter), {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                })
            });
            this.pdfShowFlag = true;
            this.pdfHeight = (document.getElementsByTagName("html")[0].offsetHeight - 76) * 0.9 + "px";
            this.pdfUrl = "../pdf/web/viewer.html?file=../../pdfUtil/getPdfData/" + result.newname;
        },
        flushData: function () {
            if (this.remote !== false && this.remote != "false") {
                var result = getJson(this.url, $.extend(this.formatParameter(this.parameter), {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize
                }));
                if (this.mapperMap) {
                    for (var ele in this.cols) {
                        if (this.cols[ele].mapper) {
                            for (var ele1 in result.rows) {
                                result.rows[ele1][this.cols[ele].key] = this.mapperMap[this.cols[ele].mapper][result.rows[ele1][this.cols[ele].oldkey]];
                            }
                        }
                    }
                }
                ;
                this.rows = result;
            }
        },
        selectRow: function (newRow, oldRow) {
            this.current = newRow;
            this.$emit("current-change", newRow, oldRow);
        },
        getCurrent: function () {
            return this.current;
        },
        setCurrent:function(row){
        	this.$refs.dta.setCurrentRow(row);
        	this.current=row;
        },
        setEdit: function (idx, row, data) {
            this.editIdx = idx;
            this.editRow = row;
        },
        setRows: function (data) {
            this.rows = data;
        },
        getData: function () {
            return this.rows;
        },
        deleteRow: function () {
            this.rows.splice(this.editIdx, 1);
        }
    },
    created: function () {
        if (this.is_static) {
            return
        }
        var mapperStr = [];
        for (var i in this.cols) {
            if (this.cols[i].mapper) {
                mapperStr.push(this.cols[i].mapper);
                this.cols[i].oldkey = this.cols[i].key;
                this.cols[i].key = this.cols[i].key + "_name_";
            }
        }
        if (mapperStr.length > 0) {
            this.mapperMap = getJson("../dataUtil/getMapper", mapperStr);
        }
        this.flushData();
    }
});
Vue.component("qfdt", {
    props: {
        data: {
            type: Object,
            required: true
        }
    },
    methods: {
        flushData: function () {
            this.$refs.dt.flushData();
        },
        getCurrent: function () {
            return this.$refs.dt.current;
        },
        getTableData: function () {
            return this.$refs.dt.rows;
        },
        validate: function () {
            return this.$refs.ddf.validate();
        },
        getQfData: function () {
            return this.$refs.qf.getData();
        },
        pdf: function (classInfo, title, fileName, detailData) {
            this.$refs.dt.pdf(classInfo, title, fileName, detailData);
        },
        excel: function (classInfo, title, fileName, detailData) {
            this.$refs.dt.excel(classInfo, title, fileName, detailData);
        },
        previewPDF: function (classInfo, detailData) {
            this.$refs.dt.previewPDF(classInfo, detailData);
        },
        reset: function () {
            this.data.parameter = {};
            var _this = this;
            setTimeout(function () {
                _this.flushData();
            }, 10);

        },
        getSelectList: function () {
            return this.$refs.dt.selectList
        },
        resetFields: function () {
            return this.$refs.ddf.resetFields()
        },
        clearValidate: function () {
            return this.$refs.ddf.clearValidate()
        }
    },
    template: '<div>\
		<queryform ref="qf" :items="data.qf.items" :buttons="data.qf.buttons" :data.sync="data.parameter"></queryform>\
		<datatable ref="dt" :colwidth="data.dt.colwidth" :rowclassname="data.dt.rowclassname" :defaultsort="data.dt.defaultSort" :orderby="data.dt.orderby" :sortby="data.dt.sortby" :cols="data.dt.items" :selection="data.dt.selection" :selectable="data.dt.selectable" :url="data.dt.url" :tableheight="data.tableheight||\'100%\'" :parameter.sync="data.parameter"></datatable>\
		<el-dialog ref="ddf" v-if="data.ddf" :visible.sync="data.ddf.show" :width="data.ddf.width||\'700px\'" :title="data.ddf.title">\
		<div style="margin-bottom: 20px;color:red;border-style: solid;border-width: 1px;border-radius: 5px;border-color: red;padding: 20px" v-if="data.ddf.info">{{data.ddf.info}}</div>\
			<detailform :labelwidth="data.ddf.labelwidth" ref="ddf" :rules="data.ddf.rules" :items="data.ddf.items" :buttons="data.ddf.buttons" :disabled="data.ddf.disabled" :data.sync="data.ddf.data"></detailform>\
		</el-dialog>\
	</div>'
});
Vue.component("rightmenu", {
    props: {
        buttons: {
            type: Array,
            required: true
        }
    },
    data: function () {
        return {
            isShow: false,
            styleObj: {}
        }
    },
    methods: {
        show: function (e) {
            this.styleObj = {
                left: (e.clientX - 2) + "px",
                top: (e.clientY - 2) + "px"
            },
                this.isShow = true;
        }
    },
    template: '<div style="position:relative;overflow:hidden" @click.right.prevent="show" @mouseout="isShow=false">\
		<div class="right-menu" @mouseenter="isShow=true" v-show="isShow" v-bind:style="styleObj">\
        	<a v-for="item in buttons" @mouseenter="isShow=true" @click="item.handler.call($root)">{{item.value}}</a>\
		</div>\
    <slot></slot>\
		</div>'
});

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
function sc(s, mes, e) {
    if (!mes) {
        mes = "此操作将删除数据, 是否继续?";
    }
    new Vue().$confirm(mes, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(s).catch(e);
}

var setTableHeight_ = function (obj) {
    var arr = obj.otherelid,
        html = document.getElementsByTagName('html')[0].offsetHeight,
        tmb = 10,// 10px=>table margin-bottom
        pagenation = 32,// 32px=>pagenation height
        height_ = 0;
    for (var i = 0; i < arr.length; i++) {
        var val = arr[i],
            regPos = /^\d+(\.\d+)?$/, //非负浮点数
            regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if (regPos.test(val) || regNeg.test(val)) {
            height_ += parseInt(val);
        } else {
            height_ += document.getElementById(val).offsetHeight;
        }
    }
    height_ += tmb + pagenation + 20;
    setHeight = html - height_ - (obj.autopadding || 0);
    if (_IEVersion > 5 && _IEVersion < 11) {//IE计算值稍小，出现滚动条，只能减去几个像素
        setHeight -= 5;
    }
    obj.tableheight = setHeight;
    obj.$el.style.height = setHeight + "px";
};
//detailform表单每行添加clear清除浮动
var setRowHeight = function (obj) {
    var col = obj.$el.children,
        rowWidth = obj.$el.offsetWidth,
        addWidth = 0;
    for (i = 0; i < col.length; i++) {
        var _class = col[i].getAttribute("class")
        if (_class && _class.indexOf("col") >= 0) {
            var width = col[i].offsetWidth;
            addWidth += width;
            if (rowWidth - addWidth >= 0 && rowWidth - addWidth <= 30) {
                addWidth = 0;
                var node = document.createElement("DIV");
                node.className = 'clear';
                insertAfter(node, col[i]);
            }
        }
    }
}

//在元素后面添加新的元素
function insertAfter(newElement, targentElement) {
    var parent = targentElement.parentNode;
    if (parent.lastChild == targentElement) {
        parent.appendChild(newElement);
    } else {
        parent.insertBefore(newElement, targentElement.nextSibling)
    }
}

function getSize() {
    var body = document.getElementById("app").parentElement;
    var bodyWidth = body.scrollWidth;
    var bodyHeight = body.scrollHeight;
    return {
        width: bodyWidth,
        height: bodyHeight
    }
}

function formatMoney(number, places, symbol, thousand, decimal) {
    number = number || 0;
    places = !isNaN(places = Math.abs(places)) ? places : 2;
    symbol = symbol !== undefined ? symbol : "$";
    thousand = thousand || ",";
    decimal = decimal || ".";
    var negative = number < 0 ? "-" : "",
        i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
        j = (j = i.length) > 3 ? j % 3 : 0;
    return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
}
Number.prototype.Format = function (places, symbol, thousand, decimal) {
    places = !isNaN(places = Math.abs(places)) ? places : 2;
    symbol = symbol !== undefined ? symbol : "";
    thousand = thousand || ",";
    decimal = decimal || ".";
    var number = this,
        negative = number < 0 ? "-" : "",
        i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
        j = (j = i.length) > 3 ? j % 3 : 0;
    return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
};
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}