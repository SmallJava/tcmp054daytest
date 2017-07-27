<%--
  Created by IntelliJ IDEA.
  User: TYY
  Date: 2017/6/26
  Time: 9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>班级管理</title>
    <jsp:include page="basic.jsp"></jsp:include>
    <script type="text/javascript">
        $(function () {
           initGradeData();
        });
        function initGradeData(){
            $("#gradeDatagrid").datagrid({
                //一个URL从远程站点请求数据
                url:'${pageContext.request.contextPath}/grade/quryGrade.controller',
                //该方法类型请求远程数据
                method:'POST',
                //是否显示斑马线效果
                striped:true,
                //指明哪一个字段是标识字段
                idField:'id',
                //如果为true，则显示一个行号列
                rownumbers:true,
                //在面板头部显示的标题
                title:'班级信息',
                //在请求远程数据的时候发送额外的参数
                queryParams:{},
                //如果为true，则在Datagrid控件底部显示分页工具栏
                pagination:true,
                //在设置分页属性的时候初始化页面大小
                pageSize:5,
                //在设置分页属性的时候 初始化页面大小选择列表
                pageList:[2,5,10],
                //数据表格的顶部工具栏
                toolbar:[
                    {
                        text:'添加',
                        iconCls:'icon-add',
                        handler:function () {
                            showAddGrade();   //展示添加窗口
                        }
                    },{
                        text: '批量删除',
                        iconCls:'icon-remove',
                        handler:function () {
                            doDeleteGradeByIdList();
                        }
                    }
                ],
                //显示的字段
                columns:[[
                    {field:'ck',checkbox:true},     //复选框
                    {field:'gradeName',title:'班级名称',width:100},
                    {field:'createDate',title:'创建时间',width:200,formatter:dataFormat},       //格式化创建时间
                    {field:'id',title:'操作列',width:200,formatter:operFormat}       //操作列
                ]]
            });
        }

        //操作列
        function operFormat(rowData){
            var queryGradeByIdStr = '<a href="javascript:queryGradeById(\'' + rowData + '\')">详情</a>';
            var editGradeByIdStr = '<a href="javascript:updateGrade(\'' + rowData + '\')">修改</a>';
            var deleteGradeByIdStr = '<a href="javascript:deleteGradeById(\'' + rowData + '\')">删除</a>';
            return queryGradeByIdStr+' '+editGradeByIdStr+' '+deleteGradeByIdStr;
        }

        //格式化创建时间
        function dataFormat(rowData) {
            var date =new Date(rowData);
            var year=date.getFullYear();//年
            var month=date.getMonth()+1;//月
            var day=date.getDate();//日
            var str=year+'-'+month+'-'+day;
            return str;
        }

        //按班级名称查询
        function queryGrade(){
            var gradeName=$("#gradeName").val();
            var queryParams=$("#gradeDatagrid").datagrid('options').queryParams;
            queryParams.gradeName=gradeName;
            $("#gradeDatagrid").datagrid('load');
        }


        //展示查询班级详情的window
        function queryGradeById(gradeId){
            //1. 调用controller查询班级详情
            $.post('${pageContext.request.contextPath}/grade/queryGradeById.controller',{"id":gradeId},
                function (data,status) {
                    if(status=="success"){
                        //2. 将controller返回的数据展示出来
                        var gradeNameStr=data.gradeName;
                        var gradeDetailsStr=data.details;
                        $("#selectGradeName").textbox("setValue",gradeNameStr);
                        $("#selectGradeDtails").textbox("setValue",gradeDetailsStr);
                        //3. 显示出班级详情的window
                        $("#queryGradeByIdWin").window('open');
                    }
                }
            );
        }

        //展示添加班级Window
        function showAddGrade(){
            $("#addGradeWindow").window('open');
        }

        //提交添加班级的Form
        function addGrade(){
            $("#addGradeForm").form('submit',{
                url:'${pageContext.request.contextPath}/grade/addGrade.controller',
                success:function (data) {
                    var dataObj=JSON.parse(data);
                    alert(dataObj.msg);
                    clearAddForm();
                    $("#addGradeWindow").window('close');
                    $("#gradeDatagrid").datagrid('load');
                }
            })
        }

        //清空添加班级的Form
        function clearAddForm(){
            $("#addGradeForm").form('clear');
        }


        //展示修改班级的window
        function updateGrade(rowData){
            $.post("${pageContext.request.contextPath}/grade/queryGradeById.controller",{"id":rowData},function(data,status){
                if(status=="success"){
                    var gradeName=data.gradeName;
                    var details=data.details;
                    var greadId=data.id;
                    $("#id").textbox('setValue',greadId);
                    $("#updateGradeName").textbox("setValue",gradeName);
                    $("#updateDetails").textbox("setValue",details);
                    $("#updateGradeWindow").window('open');
                }
            });
        }

        //提交修改班级的Form
        function editGrade(){
            $("#updateGradeForm").form('submit',{
                url:'${pageContext.request.contextPath}/grade/updateGrade.controller',
                success:function(data){
                    var dataObj = JSON.parse(data);
                    alert(dataObj.msg);
                    $("#gradeDatagrid").datagrid('load');
                    $("#updateGradeWindow").window('close');
                }
            });
        }

        //清空修改班级的Form


        /*删除单个班级*/
        function deleteGradeById(gradeId) {
            if (confirm("确定要删除吗？")) {
                $.post('${pageContext.request.contextPath}/grade/deleteGradeById.controller',
                    {"gradeId":gradeId},
                    function (data, status) {
                        if(status=="success") {
                            alert(data.msg);
                            $("#gradeDatagrid").datagrid('load');
                        }
                    }
                );
            }
        }

        /*删除多个班级*/
        function doDeleteGradeByIdList() {
            //1.获取选中项的班级的id
            var item = $("#gradeDatagrid").datagrid('getSelections');
            if (item.length<1) {
                alert("请选择要删除的班级");
                return;
            }
            var ids = "";
            for(var i=0;i<item.length;i++) {
                ids += item[i].id + ",";
            }
            if (confirm("确定要删除这些班级吗？")) {
                //2.请求controller
                $.post('${pageContext.request.contextPath}/grade/deleteGradeByIdList.controller',
                    {"gradeIdListStr":ids},
                    function (data, status) {
                        if(status=="success") {
                            //3.展示返回数据
                            alert(data.msg);
                            //4.班级列表刷新
                            $("#gradeDatagrid").datagrid('load');
                        }
                    }
                );
            }
        }

    </script>
</head>
<body>
    <!--根据班级名模糊查询-->
    <div>
        <form id="queryGradeForm" method="post">
            <div>
                <label>班级名称：</label>
                <input type="text" name="gradeName" id="gradeName"/>
                <a href="javascript:void(0)" onclick="queryGrade()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
            </div>
        </form>
    </div>

    <%--添加班级的按钮--%>
    <a href="javascript:void(0)" onclick="showAddGrade()" class="easyui-linkbutton" iconCls="icon-add">添加</a>

    <!--数据展示-->
    <div>
        <table id="gradeDatagrid"></table>
    </div>

    <%--班级详情的Window--%>
    <div id="queryGradeByIdWin" class="easyui-window" title="班级详情" closed="true"
         style="left:30%;top:30%;width: 400px;height: 200px;padding:30px 60px 30px 60px " >
        <table cellpadding="5">
            <tr>
                <td>班级名称：</td>
                <td>
                    <input  id="selectGradeName" class="easyui-textbox" readonly/>
                </td>
            </tr>
            <tr>
                <td>班级描述：</td>
                <td>
                    <input id="selectGradeDtails" class="easyui-textbox"
                           data-options="multiline:true" readonly/>
                </td>
            </tr>
        </table>
    </div>

    <%--添加班级window--%>
    <div id="addGradeWindow" class="easyui-window" title="添加班级" closed="true"
         style="left: 30%;top: 30%; width: 400px;height: 200px; padding:30px 60px 30px 60px" >
        <form id="addGradeForm" method="post" >
            <table>
                <tr>
                    <td>班级名称：</td>
                    <td>
                        <input  class="easyui-validatebox" id="addGradeName" name="gradeName"
                            <%--限制输入字符的长度在1到10之间--%>
                            data-options="required:true, validType:'length[1,10]'"/>
                    </td>
                </tr>
                <tr>
                    <td>班级描述：</td>
                    <td>
                        <input class="easyui-textBox" id="addDetails" name="details"
                               <%--多行--%>
                               data-options="multiline:true"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="javascript:void(0)" onclick="addGrade()" class="easyui-linkbutton">提交</a>
                    </td>
                    <td>
                        <a href="javascript:void(0)" onclick="clearAddForm()" class="easyui-linkbutton">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <%--修改班级window--%>
    <div id="updateGradeWindow" class="easyui-window" title="修改班级" closed="true"
         style="top:30%;left: 30%;width: 500px;height: 200px" >
        <div style="padding:10px 65px 10px 65px">
            <form id="updateGradeForm" method="post">
                <table cellpadding="5">
                    <tr>
                        <td>班级名称：</td>
                        <td>
                            <%--只读，不可修改--%>
                            <input class="easyui-textbox" id="updateGradeName" name="gradeName" value="" readonly/>
                        </td>
                    </tr>
                    <tr>
                        <td>班级描述：</td>
                        <td>
                            <input class="easyui-textbox" data-options="multiline:true" id="updateDetails" name="details" value=""/>
                            <input class="easyui-textbox" type="hidden" id="id" name="id" value=""/>
                        </td>
                    </tr>
                </table>
            </form>
            <div>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editGrade()">提交</a>
            </div>
        </div>
    </div>

</body>
</html>
