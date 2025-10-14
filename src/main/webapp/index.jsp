<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blood Transfusion Center - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
    <div class = "container mt-5">
        <div class = "text-center mb-5">
                <h1 class = "display-4">Blood Transfusion Center</h1>
                <p class ="lead">Blood bank management system</p>
        </div>

        <div class = "row g-4">
            <!-- Donor Card -->
            <div class = "col-md-4">
                 <div class = "card shadow-sm h-100">
                        <div class = "card-body text-center">
                            <div class="mb-3">
                                <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-person-heart text-danger" viewBox="0 0 16 16">
                                     <path d="M9 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0m-9 8c0 1 1 1 1 1h10s1 0 1-1-1-4-6-4-6 3-6 4m13.5-8.09c1.387-1.425 4.855 1.07 0 4.277-4.854-3.207-1.387-5.702 0-4.276Z"/>
                                </svg>
                            </div>
                            <h5 class = "card-title">Donors</h5>
                            <p class = "card-text">Managing blood donors</p>
                            <a href = "${pageContext.request.contextPath}/donors/list" class ="btn btn-primary">See the receivers</a>
                        </div>
                 </div>
            </div>
            <!-- Receiver card -->
            <div class "col-md-4">
                 <div class = "card shadow-sm h-100">
                        <div class = "card-body text-center">
                              <div class="mb-3">
                                  <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-arrow-left-right text-success" viewBox="0 0 16 16">
                                      <path fill-rule="evenodd" d="M1 11.5a.5.5 0 0 0 .5.5h11.793l-3.147 3.146a.5.5 0 0 0 .708.708l4-4a.5.5 0 0 0 0-.708l-4-4a.5.5 0 0 0-.708.708L13.293 11H1.5a.5.5 0 0 0-.5.5m14-7a.5.5 0 0 1-.5.5H2.707l3.147 3.146a.5.5 0 1 1-.708.708l-4-4a.5.5 0 0 1 0-.708l4-4a.5.5 0 1 1 .708.708L2.707 4H14.5a.5.5 0 0 1 .5.5"/>
                                  </svg>
                              </div>
                              <h5 class ="card-title">Associations</h5>
                              <p class = "card-text">Matching donors and recipients</p>
                              <a href = "${pageContext.request.contextPath}/associations/form" class = "btn btn-success">Manage associations</a>
                        </div>
                 </div>
            </div>
        </div>
        <div class = "mt-5 text-center text-meted">
            <p> &copy; 2025 Blood Transfusion Center. All rights reserved. </p>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
