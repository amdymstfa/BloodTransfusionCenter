<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Donors List" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>📋 List of Donors</h2>
    <a href="${pageContext.request.contextPath}/donors/create" class="btn btn-danger">
        ➕ New donor
    </a>
</div>

<div class="card shadow">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty donors}">
                <div class="text-center py-5">
                    <p class="text-muted">No registered donors.</p>
                    <a href="${pageContext.request.contextPath}/donors/create" class="btn btn-danger">
                        Create the first donor
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-danger">
                            <tr>

                                <th>Full Name</th>
                                <th>CIN</th>
                                <th>Phone Number</th>
                                <th>Blood Type</th>
                                <th>Age</th>
                                <th>Weight (kg)</th>
                                <th>Status</th>
                                <th>Associated Recipient</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="donor" items="${donors}">
                                <tr>

                                    <td>${donor.fullName}</td>
                                    <td>${donor.cin}</td>
                                    <td>${donor.phone}</td>
                                    <td>
                                        <span class="badge bg-danger">${donor.bloodType.label}</span>
                                    </td>
                                    <td>${donor.age} years</td>
                                    <td>${donor.weight}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${donor.status == 'AVAILABLE'}">
                                                <span class="badge bg-success">AVAILABLE</span>
                                            </c:when>
                                            <c:when test="${donor.status == 'NOT_AVAILABLE'}">
                                                <span class="badge bg-warning text-dark">NOT AVAILABLE</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">Not eligible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty donor.recipient}">
                                                ${donor.recipient.fullName}
                                                <a href="${pageContext.request.contextPath}/associations/remove?donorId=${donor.id}" 
                                                   class="btn btn-sm btn-outline-danger" 
                                                   onclick="return confirm('Dissociate this donor?')">
                                                    ⛓️
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">Not Associated</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="${pageContext.request.contextPath}/donors/edit?id=${donor.id}" 
                                               class="btn btn-sm btn-outline-primary" title="Modifier">
                                                ✏️
                                            </a>
                                            <a href="${pageContext.request.contextPath}/donors/delete?id=${donor.id}" 
                                               class="btn btn-sm btn-outline-danger" 
                                               onclick="return confirm('Delete this donor ?')"
                                               title="delete">
                                                🗑️
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="mt-3">
                    <p class="text-muted">Total: <strong>${donors.size()}</strong> donors(s)</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="common/footer.jsp" />