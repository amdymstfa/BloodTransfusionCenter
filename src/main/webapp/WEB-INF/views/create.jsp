<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Créer ${entityType == 'donor' ? 'un donneur' : 'un receveur'}" />
</jsp:include>

<div class="row">
    <div class="col-md-8 offset-md-2">
        <div class="card shadow">
            <div class="card-header bg-danger text-white">
                <h4 class="mb-0">
                    <c:choose>
                        <c:when test="${mode == 'edit'}">
                            Modifier ${entityType == 'donor' ? 'le donneur' : 'le receveur'}
                        </c:when>
                        <c:otherwise>
                            Créer ${entityType == 'donor' ? 'un donneur' : 'un receveur'}
                        </c:otherwise>
                    </c:choose>
                </h4>
            </div>
            <div class="card-body">
                <!-- Tab Navigation -->
                <ul class="nav nav-tabs mb-4" id="formTabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link ${entityType == 'donor' ? 'active' : ''}" 
                                id="donor-tab" 
                                data-bs-toggle="tab" 
                                data-bs-target="#donor-form" 
                                type="button">
                            🩸 Donneur
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link ${entityType == 'recipient' ? 'active' : ''}" 
                                id="recipient-tab" 
                                data-bs-toggle="tab" 
                                data-bs-target="#recipient-form" 
                                type="button">
                            🏥 Receveur
                        </button>
                    </li>
                </ul>

                <!-- Tab Content -->
                <div class="tab-content" id="formTabContent">
                    <!-- Donor Form -->
                    <div class="tab-pane fade ${entityType == 'donor' ? 'show active' : ''}" 
                         id="donor-form" 
                         role="tabpanel">
                        <form action="${pageContext.request.contextPath}/donors/${mode == 'edit' ? 'update' : 'create'}" 
                              method="post" 
                              onsubmit="return validateDonorForm()">
                            
                            <c:if test="${mode == 'edit'}">
                                <input type="hidden" name="id" value="${donor.id}">
                            </c:if>

                            <div class="row">
                                <!-- First Name -->
                                <div class="col-md-6 mb-3">
                                    <label for="donorFirstName" class="form-label">Prénom <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="donorFirstName" name="firstName" 
                                           value="${donor.firstName}" required>
                                </div>

                                <!-- Last Name -->
                                <div class="col-md-6 mb-3">
                                    <label for="donorLastName" class="form-label">Nom <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="donorLastName" name="lastName" 
                                           value="${donor.lastName}" required>
                                </div>
                            </div>

                            <div class="row">
                                <!-- Phone -->
                                <div class="col-md-6 mb-3">
                                    <label for="donorPhone" class="form-label">Téléphone <span class="text-danger">*</span></label>
                                    <input type="tel" class="form-control" id="donorPhone" name="phone" 
                                           value="${donor.phone}" 
                                           pattern="^(\\+212|0)[5-7]\\d{8}$" 
                                           placeholder="0612345678" required>
                                    <small class="text-muted">Format: 0612345678 ou +212612345678</small>
                                </div>

                                <!-- CIN -->
                                <div class="col-md-6 mb-3">
                                    <label for="donorCin" class="form-label">CIN <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="donorCin" name="cin" 
                                           value="${donor.cin}" 
                                           pattern="^[A-Z]{1,2}\\d{5,7}$" 
                                           placeholder="AB123456" required>
                                    <small class="text-muted">Format: AB123456</small>
                                </div>
                            </div>

                            <div class="row">
                                <!-- Birth Date -->
                                <div class="col-md-6 mb-3">
                                    <label for="donorBirthDate" class="form-label">Date de naissance <span class="text-danger">*</span></label>
                                    <input type="date" class="form-control" id="donorBirthDate" name="birthDate" 
                                           value="${donor.birthDate}" 
                                           max="<%= java.time.LocalDate.now() %>" required>
                                </div>

                                <!-- Weight -->
                                <div class="col-md-6 mb-3">
                                    <label for="donorWeight" class="form-label">Poids (kg) <span class="text-danger">*</span></label>
                                    <input type="number" class="form-control" id="donorWeight" name="weight" 
                                           value="${donor.weight}" 
                                           min="40" max="200" step="0.1" required>
                                    <small class="text-muted">Minimum 50 kg requis</small>
                                </div>
                            </div>

                            <div class="row">
                                <!-- Gender -->
                                <div class="col-md-6 mb-3">
                                    <label for="donorGender" class="form-label">Sexe <span class="text-danger">*</span></label>
                                    <select class="form-select" id="donorGender" name="gender" required>
                                        <option value="">-- Sélectionner --</option>
                                        <option value="MALE" ${donor.gender == 'MALE' ? 'selected' : ''}>Homme</option>
                                        <option value="FEMALE" ${donor.gender == 'FEMALE' ? 'selected' : ''}>Femme</option>
                                    </select>
                                </div>

                                <!-- Blood Type -->
                                <div class="col-md-6 mb-3">
                                    <label for="donorBloodType" class="form-label">Groupe sanguin <span class="text-danger">*</span></label>
                                    <select class="form-select" id="donorBloodType" name="bloodType" required>
                                        <option value="">-- Sélectionner --</option>
                                        <option value="O_POSITIVE" ${donor.bloodType == 'O_POSITIVE' ? 'selected' : ''}>O+</option>
                                        <option value="O_NEGATIVE" ${donor.bloodType == 'O_NEGATIVE' ? 'selected' : ''}>O-</option>
                                        <option value="A_POSITIVE" ${donor.bloodType == 'A_POSITIVE' ? 'selected' : ''}>A+</option>
                                        <option value="A_NEGATIVE" ${donor.bloodType == 'A_NEGATIVE' ? 'selected' : ''}>A-</option>
                                        <option value="B_POSITIVE" ${donor.bloodType == 'B_POSITIVE' ? 'selected' : ''}>B+</option>
                                        <option value="B_NEGATIVE" ${donor.bloodType == 'B_NEGATIVE' ? 'selected' : ''}>B-</option>
                                        <option value="AB_POSITIVE" ${donor.bloodType == 'AB_POSITIVE' ? 'selected' : ''}>AB+</option>
                                        <option value="AB_NEGATIVE" ${donor.bloodType == 'AB_NEGATIVE' ? 'selected' : ''}>AB-</option>
                                    </select>
                                </div>
                            </div>

                            <!-- Contraindications -->
                            <div class="mb-3">
                                <label for="donorContraindications" class="form-label">Contre-indications médicales</label>
                                <textarea class="form-control" id="donorContraindications" name="contraindications" 
                                          rows="3" placeholder="Ex: hépatite, VIH, diabète, grossesse, allaitement...">${donor.contraindications}</textarea>
                                <small class="text-muted">Si aucune, laisser vide</small>
                            </div>

                            <!-- Buttons -->
                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/donors/list" class="btn btn-secondary">Annuler</a>
                                <button type="submit" class="btn btn-danger">
                                    ${mode == 'edit' ? 'Mettre à jour' : 'Créer'}
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- Recipient Form -->
                    <div class="tab-pane fade ${entityType == 'recipient' ? 'show active' : ''}" 
                         id="recipient-form" 
                         role="tabpanel">
                        <form action="${pageContext.request.contextPath}/recipients/${mode == 'edit' ? 'update' : 'create'}" 
                              method="post" 
                              onsubmit="return validateRecipientForm()">
                            
                            <c:if test="${mode == 'edit'}">
                                <input type="hidden" name="id" value="${recipient.id}">
                            </c:if>

                            <div class="row">
                                <!-- First Name -->
                                <div class="col-md-6 mb-3">
                                    <label for="recipientFirstName" class="form-label">Prénom <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="recipientFirstName" name="firstName" 
                                           value="${recipient.firstName}" required>
                                </div>

                                <!-- Last Name -->
                                <div class="col-md-6 mb-3">
                                    <label for="recipientLastName" class="form-label">Nom <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="recipientLastName" name="lastName" 
                                           value="${recipient.lastName}" required>
                                </div>
                            </div>

                            <div class="row">
                                <!-- Phone -->
                                <div class="col-md-6 mb-3">
                                    <label for="recipientPhone" class="form-label">Téléphone <span class="text-danger">*</span></label>
                                    <input type="tel" class="form-control" id="recipientPhone" name="phone" 
                                           value="${recipient.phone}" 
                                           pattern="^(\\+212|0)[5-7]\\d{8}$" 
                                           placeholder="0612345678" required>
                                </div>

                                <!-- CIN -->
                                <div class="col-md-6 mb-3">
                                    <label for="recipientCin" class="form-label">CIN <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="recipientCin" name="cin" 
                                           value="${recipient.cin}" 
                                           pattern="^[A-Z]{1,2}\\d{5,7}$" 
                                           placeholder="AB123456" required>
                                </div>
                            </div>

                            <div class="row">
                                <!-- Birth Date -->
                                <div class="col-md-6 mb-3">
                                    <label for="recipientBirthDate" class="form-label">Date de naissance <span class="text-danger">*</span></label>
                                    <input type="date" class="form-control" id="recipientBirthDate" name="birthDate" 
                                           value="${recipient.birthDate}" 
                                           max="<%= java.time.LocalDate.now() %>" required>
                                </div>

                                <!-- Gender -->
                                <div class="col-md-6 mb-3">
                                    <label for="recipientGender" class="form-label">Sexe <span class="text-danger">*</span></label>
                                    <select class="form-select" id="recipientGender" name="gender" required>
                                        <option value="">-- Sélectionner --</option>
                                        <option value="MALE" ${recipient.gender == 'MALE' ? 'selected' : ''}>Homme</option>
                                        <option value="FEMALE" ${recipient.gender == 'FEMALE' ? 'selected' : ''}>Femme</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <!-- Blood Type -->
                                <div class="col-md-6 mb-3">
                                    <label for="recipientBloodType" class="form-label">Groupe sanguin <span class="text-danger">*</span></label>
                                    <select class="form-select" id="recipientBloodType" name="bloodType" required>
                                        <option value="">-- Sélectionner --</option>
                                        <option value="O_POSITIVE" ${recipient.bloodType == 'O_POSITIVE' ? 'selected' : ''}>O+</option>
                                        <option value="O_NEGATIVE" ${recipient.bloodType == 'O_NEGATIVE' ? 'selected' : ''}>O-</option>
                                        <option value="A_POSITIVE" ${recipient.bloodType == 'A_POSITIVE' ? 'selected' : ''}>A+</option>
                                        <option value="A_NEGATIVE" ${recipient.bloodType == 'A_NEGATIVE' ? 'selected' : ''}>A-</option>
                                        <option value="B_POSITIVE" ${recipient.bloodType == 'B_POSITIVE' ? 'selected' : ''}>B+</option>
                                        <option value="B_NEGATIVE" ${recipient.bloodType == 'B_NEGATIVE' ? 'selected' : ''}>B-</option>
                                        <option value="AB_POSITIVE" ${recipient.bloodType == 'AB_POSITIVE' ? 'selected' : ''}>AB+</option>
                                        <option value="AB_NEGATIVE" ${recipient.bloodType == 'AB_NEGATIVE' ? 'selected' : ''}>AB-</option>
                                    </select>
                                </div>

                                <!-- Urgency Level -->
                                <div class="col-md-6 mb-3">
                                    <label for="recipientUrgency" class="form-label">Niveau d'urgence <span class="text-danger">*</span></label>
                                    <select class="form-select" id="recipientUrgency" name="urgencyLevel" required>
                                        <option value="">-- Sélectionner --</option>
                                        <option value="CRITICAL" ${recipient.urgencyLevel == 'CRITICAL' ? 'selected' : ''}>🔴 Critique (4 poches)</option>
                                        <option value="URGENT" ${recipient.urgencyLevel == 'URGENT' ? 'selected' : ''}>🟠 Urgent (3 poches)</option>
                                        <option value="NORMAL" ${recipient.urgencyLevel == 'NORMAL' ? 'selected' : ''}>🟢 Normal (1 poche)</option>
                                    </select>
                                </div>
                            </div>

                            <!-- Buttons -->
                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/recipients/list" class="btn btn-secondary">Annuler</a>
                                <button type="submit" class="btn btn-primary">
                                    ${mode == 'edit' ? 'Mettre à jour' : 'Créer'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />