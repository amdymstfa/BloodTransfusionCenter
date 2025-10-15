<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Error server</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="text-center">
            <h1 class="display-1">500</h1>
            <p class="fs-3"><span class="text-danger">Error!</span> Error server.</p>
            <p class="lead">An error occurred while processing your request.</p>
            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">
                    ${error}
                </div>
            </c:if>
            <a href="${pageContext.request.contextPath}/" class="btn btn-danger">Return to home</a>
        </div>
    </div>
</body>
</html>
