<%@ page contentType = "text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<c:if test = "${not empty sessionScope.messageSuccess}">
    <div class = "container mt-3">
        <div class = "alert alert-success alert-dismissable fade show" role = "alert">
            <strong>Success !</strong> ${sessionScope.messageSuccess}
            <button type = "button" class = "btn-close" data-bs-dismiss = "alert"></button>
        </div>
    </div>
    <c:remove var = "successMessage" scope = "session" />
</c:if>

<c:if test="${not empty sessionScope.errorMessage}">
    <div class="container mt-3">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error !</strong> ${sessionScope.errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </div>
    <c:remove var="errorMessage" scope="session"/>
</c:if>

<c:if test="${not empty error}">
    <div class="container mt-3">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error !</strong> ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </div>
</c:if>