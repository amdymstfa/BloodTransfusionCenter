cat > src/main/webapp/WEB-INF/views/common/404.jsp << 'EOF'
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page not found</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="text-center">
            <h1 class="display-1">404</h1>
            <p class="fs-3"><span class="text-danger">Oops!</span> Page not found.</p>
            <p class="lead">This page does not exist.</p>
            <a href="${pageContext.request.contextPath}/" class="btn btn-danger">Back</a>
        </div>
    </div>
</body>
</html>
EOF