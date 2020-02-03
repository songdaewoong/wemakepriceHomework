<%@ page import="static java.util.Optional.ofNullable" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>Wemakeprice Homework</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script>
        function onlyNumber(event) {
            event = event || window.event;
            var keyID = (event.which) ? event.which : event.keyCode;
            if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39)
                return;
            else
                return false;
        }

        function removeChar(event) {
            event = event || window.event;
            var keyID = (event.which) ? event.which : event.keyCode;
            if (keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39)
                return;
            else
                event.target.value = event.target.value.replace(/[^0-9]/g, "");
        }

        function go() {
            var frm = document.frm;

            if ($("#url").val() == "") {
                alert("url 주소를 입력해주세요.");
                $("#url").focus();
                return;
            }

            if ($("#mok").val() == "") {
                alert("출력묶음단위를 입력해주세요.");
                $("#mok").focus();
                return;
            }

            frm.submit();
        }
    </script>
</head>
<%
    String data = (String) ofNullable(request.getAttribute("data")).orElse("");
//if( )request.getAttribute("data" == null || request.getAttribute("data").equals("")){
//	data = "";
//}else{
//	data = (String)request.getAttribute("data");
//}

    String url = (String) ofNullable(request.getAttribute("url")).orElse("");
//if( request.getAttribute("url") == null || request.getAttribute("url").equals("")){
//	url = "";
//}else{
//	url = (String)request.getAttribute("url");
//}

    String type = (String) ofNullable(request.getAttribute("type")).orElse("");
//if( request.getAttribute("type") == null || request.getAttribute("type").equals("")){
//	type = "";
//}else{
//	type = (String)request.getAttribute("type");
//}

    String mok = (String) ofNullable(request.getAttribute("mok")).orElse("");
//if( request.getAttribute("mok") == null || request.getAttribute("mok").equals("")){
//	mok = "";
//}else{
//	mok = (String)request.getAttribute("mok");
//}

    int remainder = (int) ofNullable(request.getAttribute("remainder")).orElse(0);
//if( request.getAttribute("remainder") == null || request.getAttribute("remainder").equals("")){
//	remainder = 0;
//}else{
//	remainder = (int)request.getAttribute("remainder");
//}


    String msg = (String) ofNullable(request.getAttribute("msg")).orElse("");
//if( request.getAttribute("msg") == null || request.getAttribute("msg").equals("")){
//	msg = "";
//}else{
//	msg = (String)request.getAttribute("msg");
//}
%>

<script>
    if ("<%=msg%>" != "") {
        alert("<%=msg%>");
        location.href = "/index";
    }
</script>
<body>
<form name="frm" method="post" action="/homeWork">
    URL&nbsp;&nbsp;<input type="text" id="url" name="url" value="<%=url %>" style="width:400px;"
                          onkeypress="if(event.keyCode == 13){ go(); return; }"/>
    <br/><br/>
    Type
    <select id="type" name="type">
        <option value="tag" <%=type.equals("tag") ? "selected" : "" %>>HTML 태그제외</option>
        <option value="all"<%=type.equals("all") ? "selected" : "" %>>Text 전체</option>
    </select>
    <br/><br/>
    출력묶음단위(자연수)
    <input type="text" id="mok" name="mok" value="<%=mok %>" onkeydown="return onlyNumber(event)"
           onkeyup="removeChar(event); if(event.keyCode == 13){ go(); return; }" style="width:50px;"/>
    <input type="button" value="출력" onclick="go();"/>
    <br/><br/>
    <textarea style="width:450px;" readonly><%=data %></textarea>
    <br/><br/>
    몫 : <%=mok %>
    <br/>
    나머지 : <%=remainder %>
</form>
</body>
</html>