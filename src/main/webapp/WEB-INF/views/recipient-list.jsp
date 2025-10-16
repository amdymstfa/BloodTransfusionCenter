<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Liste des Receveurs" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>📋 Liste des Receveurs</h2>
    <a href="${pageContext.request.contextPath}/recipients/create" class="btn btn-primary">
        ➕ Nouveau receveur
    </a>
</div>

<div class="card shadow">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty recipients}">
                <div class="text-center py-5">
                    <p class="text-muted">Aucun receveur enregistré.</p>
                    <a href="${pageContext.request.contextPath}/recipients/create" class="btn btn-primary">
                        Créer le premier receveur
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-primary">
                            <tr>
                                <th>ID</th>
                                <th>Nom complet</th>
                                <th>CIN</th>
                                <th>Téléphone</th>
                                <th>Groupe sanguin</th>
                                <th>Âge</th>
                                <th>Urgence</th>
                                <th>Poches reçues</th>
                                <th>Statut</th>
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
                                    <td>${recipient.age} ans</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${recipient.urgencyLevel == 'CRITICAL'}">
                                                <span class="badge bg-danger">🔴 Critique</span>
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
                                                <span class="badge bg-success">✅ Satisfait</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark">⏳ En attente</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="${pageContext.request.contextPath}/recipients/edit?id=${recipient.id}" 
                                               class="btn btn-sm btn-outline-primary" title="Modifier">
                                                ✏️
                                            </a>
                                            <a href="${pageContext.request.contextPath}/recipients/delete?id=${recipient.id}" 
                                               class="btn btn-sm btn-outline-danger" 
                                               onclick="return confirm('Supprimer ce receveur ?')" 
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
                    <p class="text-muted">Total: <strong>${recipients.size()}</strong> receveur(s)</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
