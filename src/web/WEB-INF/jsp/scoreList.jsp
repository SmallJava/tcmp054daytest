<%--
  Created by IntelliJ IDEA.
  User: TYY
  Date: 2017/7/16
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>scoreList</title>

        <jsp:include page="basic.jsp"></jsp:include>
        <script type="text/javascript">
            $(function () {
                queryAllGrade();
            });

            function queryAllGrade(){
                $.get('${pageContext.request.contextPath}/grade/queryAllGrade.controller',
                    function(data,stauts){
                        if(stauts=="success"){
                            $("#gradeCombobox").combobox({
                                data:data,
                                valueField:"id",
                                textField:"gradeName",
                                editable:false,
                                onSelect:function (record) {
                                    queryStudentByGradeId(record.id);
                                }
                            });
                        }
                    }
                );
            }

            function queryStudentByGradeId(gradeId){
                $.get('${pageContext.request.contextPath}/student/queryStudentByGradeId.controller',
                    {"gradeId":gradeId},
                    function (data,status) {
                        if(status=='success'){
                            $("#studentCombobox").combobox({
                                data:data,
                                valueField:'id',
                                textField:'studentName',
                                editable:false
                            });
                        }
                    }
                );
            }

            function queryScore(){
                var gradeId=$("#gradeCombobox").combobox("getValue");
                var studentId=$("#studentCombobox").combobox("getValue");
                if(gradeId.length<=0){
                    alert("请选择班级或者班级的学生");
                    return;
                }
                $("#scoreDataGrid").datagrid({
                    url:'${pageContext.request.contextPath}/score/queryScoreByGradeIdOrStudentId.controller',
                    method:'GET',
                    striped:true,
                    rownumbers:true,
                    title:'成绩列表',
                    pagination:true,
                    pageSize:5,
                    pageList:[2,5,10],
                    queryParams:{"gradeId":gradeId,"studentId":studentId},
                    columns:[[
                        {field:'score',title:'成绩',width:80},
                        {field:'course',title:'课程',width:100,formatter:courseFormat},
                        {field:'student',title:'姓名',width:100,formatter :studentFormat},
                        {field:'grade',title:'班级',width:100,formatter :gradeFormat}
                    ]]
                });
            }

            function courseFormat(rowData){
                return rowData.courseName;
            }
            function studentFormat(rowData) {
                return rowData.studentName;
            }
            function gradeFormat(value,row,index) {
                return row.student.grade.gradeName;
            }

        </script>
    </head>
    <body>
        <div>
            <form action="" id="queryScoreForm">
                班级：
                <input class="easyui-combobox" id="gradeCombobox" name="gradeId"/>
                学生：
                <input class="easyui-combobox" id="studentCombobox" name="studentId"/>
                <a href="javascript:void(0);" onclick="queryScore()">查询</a>
            </form>
        </div>
        <div>
            <table id="scoreDataGrid"></table>
        </div>
    </body>
</html>
