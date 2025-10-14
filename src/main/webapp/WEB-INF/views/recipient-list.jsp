<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Recipients List" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Recipients List</h2>
    <a href="${pageContext.request.contextPath}/recipients/create" class="btn btn-primary">
        New Recipient
    </a>
</div>

<div class="card shadow">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty recipients}">
                <div class="text-center py-5">
                    <p class="text-muted">No recipients registered.</p>
                    <a href="${pageContext.request.contextPath}/recipients/create" class="btn btn-primary">
                        Create the first recipient
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-primary">
                            <tr>
                                <th>ID</th>
                                <th>Full Name</th>
                                <th>ID Card</th>
                                <th>Phone</th>
                                <th>Blood Type</th>
                                <th>Age</th>
                                <th>Urgency</th>
                                <th>Bags Received</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="recipient" items="${recipients}">
                                <tr>
                                    <td>${recipient.id}</td>
                                    <td>${recipient.fullName}</td>
                                    <td>${recipient.cin}</td>
                                    <td>${recipient.phone}</td>
                                    <td>
                                        <span class="badge bg-primary">${recipient.bloodType.label}</span>
                                    </td>
                                    <td>${recipient.age} years</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${recipient.urgencyLevel == 'CRITICAL'}">
                                                <span class="badge bg-danger">🔴 Critical</span>
                                            </c:when>
                                            <c:when test="${recipient.urgencyLevel == 'URGENT'}">
                                                <span class="badge bg-warning text-dark">🟠 Urgent</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-success">🟢 Normal</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <strong>${recipient.associatedDonorsCount}</strong> / ${recipient.requiredBags}
                                        <div class="progress" style="height: 5px;">
                                            <div class="progress-bar bg-success" 
                                                 style="width: ${(recipient.associatedDonorsCount * 100) / recipient.requiredBags}%">
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${recipient.status == 'SATISFIED'}">
                                                <span class="badge bg-success">✅ Satisfied</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark">⏳ Pending</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="${pageContext.request.contextPath}/recipients/edit?id=${recipient.id}" 
                                               class="btn btn-sm btn-outline-primary" title="Edit">
                                                ✏️
                                            </a>
                                            <a href="${pageContext.request.contextPath}/recipients/delete?id=${recipient.id}" 
                                               class="btn btn-sm btn-outline-danger" 
                                               onclick="return confirm('Delete this recipient?')" 
                                               title="Delete">
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
                    <p class="text-muted">Total: <strong>${recipients.size()}</strong> recipient(s)</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="common/footer.jsp" />