<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {

            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });

            $("#addBtn").click(function () {

                $("#create-marketActivityOwner").html("");

                $.ajax({
                    url: "workbench/activity/getUserList.do",
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        /**
                         * data: [{user1},{user2}....]
                         */

                        var createOwner = $("#create-marketActivityOwner");

                        $.each(data, function (i, n) {
                            createOwner.append("<option value='" + n.id + "'>" + n.name + "</option>");
                        })

                        var defaultId = "${user.id}"
                        createOwner.val(defaultId);

                    }
                })
                $("#createActivityModal").modal("show");

            })

            $("#saveBtn").click(function () {
                $.ajax({
                    url: "workbench/activity/saveActivity.do",
                    data: {
                        "owner": $.trim($("#create-marketActivityOwner").val()),
                        "name": $.trim($("#create-marketActivityName").val()),
                        "startDate": $.trim($("#create-startTime").val()),
                        "endDate": $.trim($("#create-endTime").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-describe").val())
                    },
                    dataType: "json",
                    success: function (data) {
                        if (!data.success)
                            alert("???????????????????????????");
                        else {
                            $("#createActivityModal").modal("hide");
                        }
                    },
                    type: "get",
                    async: false
                })
                pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

            })

            $("#deleteBtn").click(function () {

                if (confirm("???????????????????????????")) {

                    if ($("input[name=xz]:checked").length == 0)
                        alert("????????????????????????");
                    else {
                        /**
                         * workbench/activity/delete?id=xxx&id=xxx
                         * */
                        var $xz = $("input[name=xz]:checked");
                        var xzParams = "";
                        $.each($xz, function (i, n) {
                            xzParams += "id=";
                            xzParams += n.value;
                            if (i != $xz.length - 1)
                                xzParams += "&"
                        })
                        $.ajax({
                            url: "workbench/activity/delete.do?" + xzParams,
                            dataType: "json",
                            type: "get",
                            success: function (data) {
                                if (!data.success) {
                                    alert("???????????????");
                                }
                                pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

                            }
                        })
                    }
                }

            })

            $("#editBtn").click(function () {

                var $xz = $("input[name=xz]:checked");
                if ($xz.length != 1) {
                    alert("???????????????????????????????????????");
                } else {
                    //???????????????????????????????????????
                    $.ajax({
                        url: "workbench/activity/getActivityInfo.do",
                        data: {"id": $xz.val()},
                        dataType: "json",
                        success: function (data) {
                            /*
                            * data:{"uList":[{user1},{2},{3}...],"a",{activity}}
                            *   edit-marketActivityOwner
                            *   <option>zhangsan</option>
                            *   <option>lisi</option>
                            *   <option>wangwu</option>
                            * */
                            var html = '';
                            $.each(data.uList, function (i, n) {
                                html += '<option value="' + n.id + '">' + n.name + '</option>'
                            })
                            $("#edit-marketActivityOwner").html(html);

                            $("#hidden-edit-activityId").val(data.a.id);
                            $("#edit-marketActivityName").val(data.a.name);
                            $("#edit-startTime").val(data.a.startDate);
                            $("#edit-endTime").val(data.a.endDate);
                            $("#edit-cost").val(data.a.cost);
                            $("#edit-describe").val(data.a.description);

                        },
                        type: "post"
                    })
                    $("#editActivityModal").modal("show");
                }

            })

            $("#updateBtn").click(function () {
                $.ajax({
                    url: "workbench/activity/updateActivity.do",
                    data: {
                        "id": $.trim($("#hidden-edit-activityId").val()),
                        "owner": $.trim($("#edit-marketActivityOwner").val()),
                        "name": $.trim($("#edit-marketActivityName").val()),
                        "startDate": $.trim($("#edit-startTime").val()),
                        "endDate": $.trim($("#edit-endTime").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-describe").val())
                    },
                    dataType: "json",
                    success: function (data) {
                        if (!data.success)
                            alert("???????????????????????????");
                        else {
                            $("#editActivityModal").modal("hide");
                        }
                    },
                    type: "post",
                    async: false
                })
                pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                    , $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

            })

            //??????????????????
            $("#qx").click(function () {
                $("input[name=xz]").prop("checked", this.checked);
            })

            //????????????????????????
            $("#pageListTb").on("click", $("input[name=xz]"), function () {
                $("#qx").prop("checked", $("input[name=xz]:checked").length == $("input[name=xz]").length);
            })

            /**
             *  ?????????????????????????????????????????????????????????????????????????????????
             *  ????????????pageList???????????????????????????????????????????????????????????????
             * */
            $("#searchBtn").click(function () {
                $("#hidden-name").val($.trim($("#Search-name").val()));
                $("#hidden-owner").val($.trim($("#Search-owner").val()));
                $("#hidden-startDate").val($.trim($("#Search-startTime").val()));
                $("#hidden-endDate").val($.trim($("#Search-endTime").val()));
                pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

            })

            /**
             *  ???????????????????????????ajax???????????????????????????????????????
             */
            pageList(1, 2);

        });

        function pageList(pageNo, pageSize) {

            /**
             * data:{"total":num,"dataList":[{act1},{act2}....]}
             */

            $("#qx").prop("checked", false);

            $("#Search-name").val($.trim($("#hidden-name").val()));
            $("#Search-owner").val($.trim($("#hidden-owner").val()));
            $("#Search-startTime").val($.trim($("#hidden-startDate").val()));
            $("#Search-endTime").val($.trim($("#hidden-endDate").val()));

            $.ajax({
                url: "workbench/activity/pageList.do",
                data: {
                    "name": $.trim($("#Search-name").val()),
                    "ownerName": $.trim($("#Search-owner").val()),
                    "startTime": $.trim($("#Search-startTime").val()),
                    "endTime": $.trim($("#Search-endTime").val()),
                    "pageNo": $.trim(pageNo),
                    "pageSize": $.trim(pageSize)
                },
                dataType: "json",
                type: "get",
                success: function (data) {
                    <%--
                        <tr class="active">
                        <td><input type="checkbox"/></td>
                        <td><a style="text-decoration: none; cursor: pointer;"
                               onclick="window.location.href='workbench/activity/detail.jsp';">?????????</a></td>
                        <td>zhangsan</td>
                        <td>2020-10-10</td>
                        <td>2020-10-20</td>
                        </tr>
                    --%>
                    var html = '';
                    $.each(data.actList, function (i, n) {
                        html += '<tr class="active">'
                        html += '<td><input type="checkbox" name="xz" value=' + n.id + ' /></td>'
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id=' + n.id + '\';">' + n.name + '</a></td>'
                        html += '<td>' + n.owner + '</td>'
                        html += '<td>' + n.startDate + '</td>'
                        html += '<td>' + n.endDate + '</td>'
                        html += '</tr>'
                    })
                    $("#pageListTb").html(html);

                    var totalPages = data.num % pageSize == 0 ? data.num / pageSize : (parseInt(data.num / pageSize) + 1);

                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // ??????
                        rowsPerPage: pageSize, // ???????????????????????????
                        maxRowsPerPage: 20, // ?????????????????????????????????
                        totalPages: totalPages, // ?????????
                        totalRows: data.num, // ???????????????

                        visiblePageLinks: 3, // ??????????????????

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });

                }
            })

        }

    </script>
</head>
<body>

<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>
<input type="hidden" id="hidden-edit-activityId"/>


<!-- ????????????????????????????????? -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">??</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">??????????????????</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form" id="create-form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">?????????<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-marketActivityOwner">

                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">??????<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">????????????</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startTime" readonly>
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label ">????????????</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endTime" readonly>
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">??????</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">??????</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">??????</button>
                <button type="button" class="btn btn-primary" id="saveBtn">??????</button>
            </div>
        </div>
    </div>
</div>

<!-- ????????????????????????????????? -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">??</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">??????????????????</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">?????????<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-marketActivityOwner">

                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">??????<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-marketActivityName"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label time">????????????</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startTime" readonly/>
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label time">????????????</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endTime" readonly/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">??????</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">??????</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">??????</button>
                <button type="button" class="btn btn-primary" id="updateBtn">??????</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>??????????????????</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">??????</div>
                        <input class="form-control" type="text" id="Search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">?????????</div>
                        <input class="form-control" type="text" id="Search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">????????????</div>
                        <input class="form-control time" type="text" id="Search-startTime" readonly/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">????????????</div>
                        <input class="form-control time" type="text" id="Search-endTime" readonly/>
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="searchBtn">??????</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="addBtn">
                    <span class="glyphicon glyphicon-plus"></span> ??????
                </button>
                <button type="button" class="btn btn-default" id="editBtn"><span
                        class="glyphicon glyphicon-pencil"></span> ??????
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span
                        class="glyphicon glyphicon-minus"></span> ??????
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>??????</td>
                    <td>?????????</td>
                    <td>????????????</td>
                    <td>????????????</td>
                </tr>
                </thead>
                <tbody id="pageListTb">

                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage"></div>
        </div>

    </div>

</div>
</body>
</html>