<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="enN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
</head>
<body>
    <div class="content createEdit">
        <h1>Ad</h1>
        <div>
            <form:form id="createEditAdForm" action="/ad/save.do" modelAttribute="createAdForm">
                <div class="form-section">
                    <form:label path="title">Title</form:label>
                    <form:input path="title"/>
                </div>

                <div class="form-section">
                    <form:label path="description">Description</form:label>
                    <form:textarea path="description"/>
                </div>

                <input type="submit" value="Save ad"/>

            </form:form>

        </div>
    </div>




</body>
</html>
