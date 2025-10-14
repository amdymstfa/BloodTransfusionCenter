<%@ page contentType = "text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix = "c" url = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" url = "http://java.sun.com/jsp/jstl/fmt" %>

<!-- include header -->
<jsp:include page = "common/header.jsp">
    <jsp:param name = "title" value = "List of donors" />
</jsp:include>

<div class = "d-flex justify-content-between align-items-center mb-4">
    <h2> List of donors</h2>
    <a href = "${pageContext.request.contextPath}/donors/create" class = "btn btn-danger">
        Create a new Donor
    </a>
</div>

<div class = "card shadow">
    <div class = "card-body">
        <c:choose>
            <c:when test = "${empty donors}">
                   <div class = "text-center py-5">
                        <p class = "text-muted">Donors not found !</p>
                        <a href = "${pageContext.request.contextPath}/donors/create" class = "btn btn-danger">Create a donor</a>
                   </div>
            </c:when>
            <c:otherwise>
                <div class = "responsive-table">
                    <table class = "table table-hover">
                        <thead class = "table-danger">
                            <tr>
                            <th>ID</th>
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
                            <c:forEach var  = "donor" items = "${donors}">
                                <tr>
                                    <td>${donor.id}</td>
                                    <td>${donor.fullname}</td>
                                    <td>${donor.cin}</td>
                                    <td>${donor.phone}</td>
                                    <td><span class = "badge bg-danger">${donor.bloodType.label}</span></td>
                                    <td>${donor.age} years</td>
                                    <td></${donor.wight}>
                                    <td>
                                        <c:choose>
                                            <c:when test = "${donor.status == AVAILABLE}">
                                                <span class = "badge bd-success">Available</span>
                                            </c:when>
                                            <c:when test = "${donor.status == NOT_AVAILABLE}">
                                                <span class = "badge bg-warning text-dark">Not available</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class = "badge bg-danger">Not eligible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test = "not empty donor.recipient">
                                                ${donor.recipient.ful;fullname}
                                                <a href = "${pageContext.request.contextPath}/associations/remove?donorId=${donor.id}"
                                                    class = "btn btn-sm btn-outline-danger"
                                                    onclick = "return confirm(Dissociate this donor?)"
                                                >Dissociate</a>
                                            </c:when>
                                            <c:otherwise><span class = "text-muted">-</span></c:otherwise>
                                        </c:choose>
                                    </td>\
                                    <td>
                                        <div class = "btn-group" role = "group">
                                            <a href = "${pageContext.request.contextPath}/donors/edit?id = ${donor.id}"
                                            class = "btn btn-sm btn-outline-primary" title = "edit">
                                            ✏️ </a>
                                        </div>
                                        <div class = "btn-group" role = "group">
                                            <a href = "${pageContext.request.contextPath}/donors/edit?id = ${donor.id}"
                                            class = "btn btn-sm btn-outline-danger" title = "delete"
                                            onclick = "return confirm(delete this donor)">
                                             🗑️ </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class = "mt-3">
                    <p class = "text-muted">Total : <strong>${donors.size()}</strong> donor(s)</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page = "common/footer.jsp"/>