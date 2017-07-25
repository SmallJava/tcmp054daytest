<%--
  Created by IntelliJ IDEA.
  User: TYY
  Date: 2017/7/3
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hobby</title>
    <jsp:include page="basic.jsp"></jsp:include>
    <%--引入时间格式化--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/timeFormat.js"></script>
    <script type="text/javascript">
        $(function () {
            initData();
        });

        function initData() {
            $("#hobbyDatagrid").datagrid({
                url:'${pageContext.request.contextPath}/hobby/queryHobby.controller',
                method:'POST',
                striped:true,
                idField:'id',
                rownumbers:true,
                title:'爱好列表',
                pagination:true,
                pageSize:5,
                pageList:[2,5,10],
                toolbar:[
                    {
                        text:'添加',
                        iconCls:'icon-add',
                        handler:function () {
                            showAddHobbyWindow();   //展示添加窗口
                        }
                    },{
                        text:'批量删除',
                        iconCls:'icon-remove',
                        handler:function () {
                            doDeleteHobbyByIdList();
                        }
                    }
                ],
                //显示的字段
                columns:[[
                    {field:'ck',checkbox:true},     //复选框
                    {field:'hobbyName', title:'爱好名称', width:150},
                    {field:'createTime', title:'创建时间', width:200, formatter:timeFormat},       //格式化创建时间
                    {field:'id', title:'操作列', width:100, formatter:operFormat}       //操作列
                ]]
            });
        }

        //操作列
        function operFormat(rowData) {
            var editHobbyByIdStr = '<a href="javascript:doUpdateHobby(\'' + rowData + '\')">修改</a>';
            var deleteHobbyByIdStr = '<a href="javascript:doDeleteHobbyById(\'' + rowData + '\')">删除</a>';
            return editHobbyByIdStr+' '+deleteHobbyByIdStr;
        }

        /*
         * 添加爱好
         */
        //展示添加爱好Window
        function showAddHobbyWindow() {
            $("#addHobbyWindow").window('open');
        }


        //提交添加爱好Form
        function addHobby() {
            $("#addHobbyForm").form('submit',{
                url:'${pageContext.request.contextPath}/hobby/addHobby.controller',
                success:function (data) {
                    var dataObj = JSON.parse(data);
                    alert(dataObj.msg);
                    /*清空表单*/
                    clearAddHobby();
                    /*关闭窗口*/
                    $("#addHobbyWindow").window('close');
                    /*重新加载*/
                    $("#hobbyDatagrid").datagrid('load');
                }
            });
        }

        //清空添加爱好Form
        function clearAddHobby() {
            $("#addHobbyForm").form('clear');
        }


        /*
         * 修改爱好
         */
        //展示修改爱好Window
        function doUpdateHobby(rowData) {
            updateStudentFormSetValue(rowData);
            //展示
            $("#updateHobbyWindow").window('open');
        }

        function updateStudentFormSetValue(rowData) {
            $.post("${pageContext.request.contextPath}/hobby/queryHobbyById.controller",
                {"id":rowData},
                function(data,status){
                    if(status=="success"){
                        /*var hobbyId=data.id;
                        var hobbyName=data.hobbyName;
                        var createTime=data.createTime;
                        $("#updateHobbyId").textbox("setValue", hobbyId);
                        $("#updateHobbyName").textbox("setValue", hobbyName);
                        $("#updateCreateTime").textbox("setValue", createTime);*/
                        //加载
                        $("#updateHobbyForm").form('load',data);
                    }
                }
            );
        }

        //提交修改爱好Form
        function updateHobby(){
            $("#updateHobbyForm").form('submit',{
                url:'${pageContext.request.contextPath}/hobby/updateHobby.controller',
                success:function(data){
                    var dataObj = JSON.parse(data);
                    alert(dataObj.msg);
                    /*关闭窗口*/
                    $("#updateHobbyWindow").window('close');
                    /*重新加载*/
                    $("#hobbyDatagrid").datagrid('load');
                }
            });
        }

        //重置修改爱好Form
        function resetUpdateHobby() {
            var hobbyId = $("#updateHobbyId").textbox('getValue');
            updateStudentFormSetValue(hobbyId);
        }


        
        /*
         *删除单个爱好
         */
        function doDeleteHobbyById(id) {
            if(confirm('确定删除吗？')) {
                $.post('${pageContext.request.contextPath}/hobby/deleteHobbyById.controller',
                    {"id": id},
                    function (data, status) {
                        if(status="success") {
                            alert(data.msg);
                            $("#hobbyDatagrid").datagrid('load');
                        }
                    }
                );
            }
        }

        /*
         *删除多个爱好
         */
        function doDeleteHobbyByIdList() {
            //1.获取选中项的爱好的id
            var item = $("#hobbyDataGrid").datagrid('getSelections');
            if (item.length<1) {
                alert("请选择要删除的爱好");
                return;
            }
            var ids = "";
            for(var i=0;i<item.length;i++) {
                ids += item[i].id + ",";
            }
            if (confirm("确定要删除这些爱好吗？")) {
                //2.请求controller
                $.post('${pageContext.request.contextPath}/hobby/deleteHobbyByIdList.controller',
                    {"hobbyIdListStr":ids},
                    function (data, status) {
                        if(status=="success") {
                            //3.展示返回数据
                            alert(data.msg);
                            //4.爱好列表刷新
                            $("#hobbyDataGrid").datagrid('load');
                        }
                    }
                );
            }
        }




    </script>
</head>
<body>
    <table id="hobbyDatagrid"></table>

    <div>
        <%--添加爱好window--%>
        <div id="addHobbyWindow" class="easyui-window" title="添加爱好" closed="true"
            style="top:30%; left:30%; width: 400px; height: 200px; padding: 30px 60px;">
            <form id="addHobbyForm" method="post">
                <table>
                    <tr>
                        <td>爱好：</td>
                        <td>
                            <input class="easyui-validatebox" name="hobbyName" id="addHobbyName"
                                <%--限制输入字符的长度在6到12之间--%>
                                data-options="required:true, validType:'length[6,12]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="addHobby()">提交</a>
                        </td>
                        <td>
                            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="clearAddHobby()">清空</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <%--修改爱好window--%>
        <div id="updateHobbyWindow" class="easyui-window" title="修改爱好" closed="true"
             style="top:30%;left: 30%;width: 400px;height: 200px" >
            <div style="padding:10px 65px 10px 65px">
                <form id="updateHobbyForm" method="post">
                    <table cellpadding="10">
                        <tr>
                            <td>爱好：</td>
                            <td>
                                <%--验证输入字符的长度在2到12之间--%>
                                <input class="easyui-validatebox" name="hobbyName" id="updateHobbyName"
                                    data-options="required:true, validType:'length[2,12]'"/>
                                <input type="hidden" class="easyui-textbox" id="updateHobbyId" name="id"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateHobby()">提交</a>
                            </td>
                            <td>
                                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="resetUpdateHobby()">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
