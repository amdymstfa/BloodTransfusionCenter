<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Associate Donor and Recipient" />
</jsp:include>

<h2 class="mb-4">🔗 Associate a Donor with a Recipient</h2>

<div class="row">
    <div class="col-md-6 offset-md-3">
        <div class="card shadow">
            <div class="card-header bg-success text-white">
                <h5 class="mb-0">Association Form</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/associations/associate" method="post">

                    <!-- Select Donor -->
                    <div class="mb-3">
                        <label for="donorId" class="form-label">Donor <span class="text-danger">*</span></label>
                        <select class="form-select" id="donorId" name="donorId" required>
                            <option value="">-- Select a donor --</option>
                            <c:forEach var="donor" items="${donors}">
                                <option value="${donor.id}">
                                    ${donor.fullName} - ${donor.bloodType.label} - ${donor.cin}
                                </option>
                            </c:forEach>
                        </select>
                        <small class="text-muted">Only available donors are displayed</small>
                    </div>

                    <!-- Select Recipient -->
                    <div class="mb-3">
                        <label for="recipientId" class="form-label">Recipient <span class="text-danger">*</span></label>
                        <select class="form-select" id="recipientId" name="recipientId" required>
                            <option value="">-- Select a recipient --</option>
                            <c:forEach var="recipient" items="${recipients}">
                                <option value="${recipient.id}">
                                    ${recipient.fullName} - ${recipient.bloodType.label} -
                                    <c:choose>
                                        <c:when test="${recipient.urgencyLevel == 'CRITICAL'}">🔴 Critical</c:when>
                                        <c:when test="${recipient.urgencyLevel == 'URGENT'}">🟠 Urgent</c:when>
                                        <c:otherwise>🟢 Normal</c:otherwise>
                                    </c:choose>
                                    (${recipient.associatedDonorsCount}/${recipient.requiredBags})
                                </option>
                            </c:forEach>
                        </select>
                        <small class="text-muted">Only pending recipients are displayed</small>
                    </div>

                    <!-- Alert Info -->
                    <div class="alert alert-info">
                        <strong>ℹ️ Information:</strong> The system automatically checks blood compatibility before association.
                    </div>

                    <!-- Buttons -->
                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Cancel</a>
                        <button type="submit" class="btn btn-success">✅ Associate</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
