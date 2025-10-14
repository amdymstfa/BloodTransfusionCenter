<%@ page contentType = "text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!-- Include header -->
<jsp:include page = "Common/header.jsp">
    <jsp:param name = "title" value = "${entityType == 'donor' ? 'A donor' : 'A recipient'}" />
<jsp:include>

<div class = "row">
    <div class = "col-md-8 offset-md-2">
        <!-- header of card -->
        <div class = "card shadow">
            <div class = "card-header bg-danger text-white">
                <h4 class = "mb-0">
                    <c:choose>
                        <c:when test = "${mode == 'edit'}">
                            Edit ${entityType == 'donor' ? 'A donor' : 'A recipient'}
                        </c:when>
                        <c:otherwise>
                            Create ${entityType == 'donor' ? 'A donor' : 'A recipient'}
                        </c:otherwise>
                    </c:choose>
                <h4>
            </div>
        </div>
        <!-- body of card -->
        <div class = "card-body">
            <!-- navigation -->
            <ul class = "nav nav-tabs mb-4" id = "formTabs" role = "tablist">
                <li class = "nav-item" role = "presentation">
                    <button class = "nav-link ${entityType = 'donor' ? 'active' : ''}"
                        id = "donor-tab"
                        data-bs-toggle = "tab"
                        data-bs-target="#donor-form"
                        type = "button">
                        Donor
                    </button>
                </li>
                <li class = "nav-item" role = "presentation">
                <button class = "nav-link ${entityType = 'recipient' ? 'active' : ''}"
                    id = "recipient-tab"
                    data-bs-toggle = "tab"
                    data-bs-target="#recipient-form"
                    type = "button">
                    Recipient
                </button>
            </li>
            </ul>
            <!-- Content -->
            <div class = "tabContent" id = "formTabContent">
                <!-- Donor -->
                <div class = "tab-pane fade ${entityType == 'donor' ? 'show active' : ''}"
                id = "donor-form"
                role = "tabpanel">
                <form action = "${contextPage.request.contextPath}/donors/${mode = 'edit'} ? 'update' : create"
                method = "post"
                onsubmit = "return validateDonorForm">

                    <c:if test = "${mode = 'edit'}">
                        <input type = "hidden" name = "id" value = "${donor.id}">
                    </c:if>

                    <div class = "row">
                        <!-- first Name -->
                        <div class = "col-md-6 mb-3">
                            <label for = "donorFirstname" class ="form-label">Firstname <span class="text-danger">*</span></label>
                            <input type = "text" class = "control-form" id = "donorFirstName" name = "fistName" value = "${donor.firstName}" required>
                        </div>
                        <!-- Last Name -->
                        <div class = "col-md-6 mb-3">
                            <label for = "donorLastName" class = "form-label">Lastname <span class = "text-danger"></span>*</label>
                            <input type = "text" class = "control-form" id = "donorLastName" name = "lastname" value = "${donor.lastName}" required>
                        </div>
                    </div>

                    <div class = "row">
                        <!-- Phone -->
                        <div class = "col-md-6 mb-3">
                            <label for = "donorPhone" class = "form-label">Phone <span class = "text-danger">*</span></label>
                            <input type = "tel" class = "control-form" id = "donorPhone" name = "phone" value = "${donor.phone}"
                            pattern = "^(\\+212|0)[5-7]\\d{8}$"
                            placeholder = "06-12-34-56-78">
                            <small class = "text-muted">Format: 0XXXXXXXXX ou +212XXXXXXXXX</small>
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
                            <label for="donorBirthDate" class="form-label">Birth day <span class="text-danger">*</span></label>
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
                            <small class="text-muted">Minimum 50 kg required</small>
                        </div>
                    </div>

                    <div class = "row">
                        <!-- Gender -->
                        <div class = "col-md-6 md-3">
                            <label for = "donorGender" class = "form-label">Gender <span class = "text-danger">*</span></label>
                            <select class"form-select" id = "donorGender" name = "gender" required>
                                <option value = ""> -- Select -- </option>
                                <option value = "MALE" ${donor.gender == 'Male' ? 'selected' : ''}> Male </option>
                                <option value = "FEMALE" ${donor.gender == 'FEMALE' ? 'selected' : ''}> FEMALE </option>
                            </select>
                        </div>

                        <!-- Blood Type -->
                        <div class = "col-md-6 md-3">
                            <label for = "donorBloodType" class = "form-label">Blood Type <span class = "text-danger">*</span></label>
                            <select class = "select-form" id = "donorGenderType" name = "donorBloodType" required>
                                <option value = ""> -- Select --- </option>
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
                        <div class = "col-md-6 md-3">
                            <label for = "recipientUrgencyLevel" class = "form-label">Urgency Level <span>*</span></label>
                            <select class = "select-form" id = "urgencyLevel" name = "urgencyLevel" required>
                                <option value = ""> -- Select --</option>
                                <option value = "CRITICAL" ${recipient.urgencyLevel} == "CRITICAL" ? 'selected' : ''>CRITICAL (4 bags)</option>
                                <option value = "URGENT" ${recipient.urgencyLevel} == "URGENT" ? 'selected' : ''>URGENT (3 bags)</option>
                                <option value = "NORMAL" ${recipient.urgencyLevel} == "NORMAL" ? 'selected' : ''>NORMAL (1 bags)</option>
                            </select>
                        </div>
                    </div>
                    <! -- Button -->
                    <div class = "d-flex justify-content-between">
                        <a href = "${pageContext.request.contextPath}/recipients/list" class = "btn btn-secondary">Cancel</a>
                        <button type = "submit" class = "btn btn-primary">${mode = 'edit' ? 'update': 'create' }</button>
                    </div>
                </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page = "common/footer.jsp" />