<%--
  Created by IntelliJ IDEA.
  User: TYY
  Date: 2017/7/4
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生管理</title>
    <jsp:include page="basic.jsp"></jsp:include>

    <script type="text/javascript">
        $(function () {
            initStudent();
        });

        function initStudent() {
            $("#studentDatagrid").datagrid({
                //一个URL从远程站点请求数据
                url:'${pageContext.request.contextPath}/student/queryStudent.controller',
                //该方法类型请求远程数据
                method:'POST',
                //是否显示斑马线效果
                striped:true,
                //指明哪一个字段是标识字段
                idField:'id',
                //如果为true，则显示一个行号列
                rownumbers:true,
                //在面板头部显示的标题
                title:'学生信息',
                //如果为true，则在Datagrid控件底部显示分页工具栏
                pagination:true,
                //在设置分页属性的时候初始化页面大小
                pageSize:5,
                //在设置分页属性的时候 初始化页面大小选择列表
                pageList:[2,5,10],
                //工具栏
                toolbar:[
                    {
                        text:'添加',
                        iconCls:'icon-add',
                        handler:function () {
                            showAddGradeWindow();
                        }
                    },{
                        text:'批量删除',
                        iconCls:'icon-remove',
                        handler:function () {
                            doDeleteStudentByIdList();
                        }
                    }
                ],
                //显示的字段
                columns:[[
                    {field:'ck',checkbox:true},     //复选框
                    {field:'studentName',title:'姓名',width:80},
                    {field:'gender',title:'性别',width:40},
                    {field:'age',title:'年龄',width:40},
                    {field:'studentNum',title:'学号',width:160},
                    {field:'grade',title:'班级名称',width:100,formatter:formGrade},       //班级
                    {field:'id',title:'操作',width:120,formatter:operFormat}      //操作列
                ]]
            });
        }

        //班级
        function formGrade(rowData) {
            return rowData.gradeName;
        }

        //操作列
        function operFormat(rowData) {
            var detailStudentStr = '<a href="javascript:detail(\'' + rowData + '\')">详情</a>';
            var updateStudentStr = '<a href="javascript:showUpdateStudentWindow(\'' + rowData + '\')">修改</a>';
            var deleteStudentStr = '<a href="javascript:doDeleteStudentById(\'' + rowData + '\')">删除</a>';

            return detailStudentStr+' '+updateStudentStr+' '+deleteStudentStr;
        }

        /*
         * 查看详情
         */
        function detail(id) {
            $.post('${pageContext.request.contextPath}/student/queryStudentById.controller',
                {"id":id},
                function (data, status) {
                    if(status=="success") {
                        $("#studentForm").form('load',data);
                        //获得班级的信息放入studentForm中
                        var grade=data.grade;
                        $("#studentForm").form('load',grade);
                        $("#studentWindow").window('open');
                    }
                }
            );
        }

        /*
         * 添加学生
         *
         */
        //展示添加学生的window
        function showAddGradeWindow() {
            //1.性别combobox赋值
            addGender();
            //2.班级combobox赋值
            addAllGrade();
            //3.打开window
            $("#addStudentWindow").window('open');
        }

        //1.性别combobox赋值
        function addGender() {
            $("#addGender").combobox({
                url:'${pageContext.request.contextPath}/static/gender.json',
                valueField:'value',
                textField:'text',
                editable:false
            });
        }
        //2.班级combobox赋值
        function addAllGrade() {
            $.get('${pageContext.request.contextPath}/grade/queryAllGrade.controller',
                function (data,status) {
                    if(status=="success") {
                        $("#addGradeId").combobox({
                            data:data,
                            valueField:'id',
                            textField:'gradeName',
                            editable:false
                        });
                    }
                }
            );
        }

        //提交添加表格
        function addStudent() {
            //添加学号唯一策略
            var gradeStr =$("#addGradeId").combobox('getValue');
            var gradeName=$("#addGradeId").combobox('getText');
            $("#addGradeName").textbox('setValue',gradeName);

            //1.提交表单
            $("#addStudentForm").form('submit',{
                url:'${pageContext.request.contextPath}/student/addStudent.controller',
                success:function (data) {
                    var dataObj = JSON.parse(data);
                    alert(dataObj.msg);
                    //2.清空表单
                    $("#addStudentForm").form('clear');
                    //3.关闭window
                    $("#addStudentWindow").window('close');
                    //4.刷新列表
                    $("#studentDatagrid").datagrid('load');
                }
            });
        }

        //清空添加表格
        function clearAddStudent() {
            $("#addStudentForm").form('clear');
        }

        /*
         * 修改学生
         *
         */
        //展示修改学生window
        function showUpdateStudentWindow(rowData) {
            updateStudentFormSetValue(rowData);
            //4.展示
            $("#updateStudentWindow").window('open');
        }

        function updateStudentFormSetValue(rowData) {
            $.post('${pageContext.request.contextPath}/student/queryStudentById.controller',
                {"id":rowData},
                function (data, status) {
                    if(status=="success") {
                        //1.性别的combobox赋值
                        var gender=data.gender;
                        getGender(gender);
                        //2.班级的combobox赋值
                        var gradeId=data.grade.id;
                        getAllGrade(gradeId);
                        //3.加载
                        $("#updateStudentForm").form('load', data);
                    }
                }
            );
        }

        //1.性别combobox赋值
        function getGender(gender) {
            $("#updateGender").combobox({
                url:'${pageContext.request.contextPath}/static/gender.json',
                valueField:'value',
                textField:'text',
                editable:false,
                value:gender
            });
        }
        //2.班级combobox赋值
        function getAllGrade(gradeId) {
            $.get('${pageContext.request.contextPath}/grade/queryAllGrade.controller',
                function (data,status) {
                    if(status=="success") {
                        $("#updateGradeId").combobox({
                            data:data,
                            valueField:'id',
                            textField:'gradeName',
                            editable:false,
                            value:gradeId
                        });
                    }
                }
            );
        }

        //提交修改表格
        function updateStudent() {
            $("#updateStudentForm").form('submit', {
                url:'${pageContext.request.contextPath}/student/updateStudent.controller',
                success:function (data) {
                    var dataObj = JSON.parse(data);
                    alert(dataObj.msg);
                    $("#updateStudentWindow").window('close');
                    $("#studentDatagrid").datagrid('load');
                }
            });
        }

        //重置修改表格
        function resetUpdateStudent() {
            var studentId = $("#updateStudentId").textbox('getValue');
            updateStudentFormSetValue(studentId);
        }

        /*
         *删除单个学生
         *
         */
        function doDeleteStudentById(id) {
            if(confirm('确定删除吗？')) {
                $.post('${pageContext.request.contextPath}/student/deleteStudentById.controller',
                    {"id": id},
                    function (data, status) {
                        if(status="success") {
                            alert(data.msg);
                            $("#studentDatagrid").datagrid('load');
                        }
                    }
                );
            }
        }

        /*
         *删除多个学生
         */
        function doDeleteStudentByIdList() {
            //1.获取选中项的学生的id
            var item = $("#studentDatagrid").datagrid('getSelections');
            if (item.length<1) {
                alert("请选择要删除的学生");
                return;
            }
            var ids = "";
            for(var i=0;i<item.length;i++) {
                ids += item[i].id + ",";
            }
            if (confirm("确定要删除这些学生吗？")) {
                //2.请求controller
                $.post('${pageContext.request.contextPath}/student/deleteStudentByIdList.controller',
                    {"studentIdListStr":ids},
                    function (data, status) {
                        if(status=="success") {
                            //3.展示返回数据
                            alert(data.msg);
                            //4.学生列表刷新
                            $("#studentDatagrid").datagrid('load');
                        }
                    }
                );
            }
        }


    </script>
</head>
<body>
    <table id="studentDatagrid"></table>

    <%--初始详情的window--%>
    <div id="studentWindow" class="easyui-window" title="学生详情" closed="true"
        style="left:30%;top:30%;width: 400px;height: 300px;padding:30px 60px; " >
        <form id="studentForm" action="" >
            <table>
                <tr>
                    <td>姓名：</td>
                    <td><input class="easyui-textbox" name="studentName"></td>
                </tr>
                <tr>
                    <td>性别：</td>
                    <td><input class="easyui-textbox" name="gender"></td>
                </tr>
                <tr>
                    <td>年龄：</td>
                    <td><input class="easyui-textbox" name="age"></td>
                </tr>
                <tr>
                    <td>学号：</td>
                    <td><input class="easyui-textbox" name="studentNum"></td>
                </tr>
                <tr>
                    <td>班级名称：</td>
                    <td><input class="easyui-textbox" name="gradeName">
                </tr>
                <tr>
                    <td>介绍：</td>
                    <td><input class="easyui-textbox" name="details">
                </tr>
            </table>
        </form>
    </div>

    <%--添加学生的window--%>
    <div id="addStudentWindow" class="easyui-window" title="添加学生" closed="true"
        style="left: 30%;top: 20%;width: 400px;height: 500px;padding: 30px 60px;">
        <form id="addStudentForm" method="post">
            <table>
                <tr>
                    <td>姓名：</td>
                    <td>
                        <input class="easyui-validatebox" name="studentName"
                               data-options="required:true,validType:'length[2,8]'">
                    </td>
                </tr>
                <tr>
                    <td>性别：</td>
                    <td>
                        <input class="easyui-combobox" name="gender" id="addGender"
                               data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>年龄：</td>
                    <td>
                        <input class="easyui-numberbox" name="age"
                               <%--min:最小数, precision:小数点后位数--%>
                               data-options="required:true, min:1, precision:0"/>
                    </td>
                </tr>
                <%--功能被“添加学号唯一策略”取代--%>
                <%--<tr>
                    <td>学号：</td>
                    <td>
                        <input class="easyui-validatebox" name="studentNum"
                               data-options="required:true,validType:'length[2,8]'"/>
                    </td>
                </tr>--%>
                <tr>
                    <td>班级：</td>
                    <td>
                        <input class="easyui-combobox" name="grade.id" id="addGradeId"
                               data-options="required:true, missingMessage:'请选择所属班级'"/>
                        <input type="hidden" class="easyui-textbox" name="grade.gradeName" id="addGradeName"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" onclick="addStudent()">提交</a>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" onclick="clearAddStudent()">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <%--修改学生的window--%>
    <div id="updateStudentWindow" class="easyui-window" title="修改学生" closed="true"
        style="left: 30%; top:20%; width: 400px; height: 300px; padding:30px 60px;">
        <form id="updateStudentForm" method="post">
            <table>
                <tr>
                    <td>姓名：</td>
                    <td>
                        <input class="easyui-validatebox" name="studentName"
                               data-options="required:true, validType:'length[2,6]'" />
                        <input class="easyui-textbox" type="hidden" name="id" id="updateStudentId" />
                    </td>
                </tr>
                <tr>
                    <td>性别：</td>
                    <td>
                        <input class="easyui-combobox" name="gender" id="updateGender"
                               data-options="required:true" />
                    </td>
                </tr>
                <tr>
                    <td>年龄：</td>
                    <td>
                        <input class="easyui-numberbox" name="age"
                               <%--min:最小数, precision:小数点后位数--%>
                               data-options="required:true, min:1, precious:0" />
                    </td>
                </tr>
                <tr>
                    <td>学号：</td>
                    <td>
                        <input class="easyui-validatebox" name="studentNum"
                               <%--只读，不准修改--%>
                               readonly />
                    </td>
                </tr>
                <tr>
                    <td>班级：</td>
                    <td>
                        <input class="easyui-combobox" name="grade.id" id="updateGradeId"
                               data-options="required:true" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" onclick="updateStudent()">提交</a>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" onclick="resetUpdateStudent()">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

</body>
</html>
