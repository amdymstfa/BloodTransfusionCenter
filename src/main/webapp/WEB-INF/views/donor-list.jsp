<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Liste des Donneurs" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>📋 Liste des Donneurs</h2>
    <a href="${pageContext.request.contextPath}/donors/create" class="btn btn-danger">
        ➕ Nouveau donneur
    </a>
</div>

<div class="card shadow">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty donors}">
                <div class="text-center py-5">
                    <p class="text-muted">Aucun donneur enregistré.</p>
                    <a href="${pageContext.request.contextPath}/donors/create" class="btn btn-danger">
                        Créer le premier donneur
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-danger">
                            <tr>
                                <th>ID</th>
                                <th>Nom complet</th>
                                <th>CIN</th>
                                <th>Téléphone</th>
                                <th>Groupe sanguin</th>
                                <th>Âge</th>
                                <th>Poids (kg)</th>
                                <th>Statut</th>
                                <th>Receveur associé</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="donor" items="${donors}">
                                <tr>
                                    <td>${donor.id}</td>
                                    <td>${donor.fullName}</td>
                                    <td>${donor.cin}</td>
                                    <td>${donor.phone}</td>
                                    <td>
                                        <span class="badge bg-danger">${donor.bloodType.label}</span>
                                    </td>
                                    <td>${donor.age} ans</td>
                                    <td>${donor.weight}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${donor.status == 'AVAILABLE'}">
                                                <span class="badge bg-success">Disponible</span>
                                            </c:when>
                                            <c:when test="${donor.status == 'NOT_AVAILABLE'}">
                                                <span class="badge bg-warning text-dark">Non disponible</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">Non éligible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty donor.recipient}">
                                                ${donor.recipient.fullName}
                                                <a href="${pageContext.request.contextPath}/associations/remove?donorId=${donor.id}" 
                                                   class="btn btn-sm btn-outline-danger" 
                                                   onclick="return confirm('Dissocier ce donneur ?')">
                                                    ❌
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
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
                                               onclick="return confirm('Supprimer ce donneur ?')" 
                                               title="Supprimer">
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
                    <p class="text-muted">Total: <strong>${donors.size()}</strong> donneur(s)</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="common/footer.jsp" />