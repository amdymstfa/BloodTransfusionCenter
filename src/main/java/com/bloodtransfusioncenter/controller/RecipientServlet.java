package com.bloodtransfusioncenter.controller;

import com.bloodtransfusioncenter.model.Recipient;
import com.bloodtransfusioncenter.service.RecipientService;
import com.bloodtransfusioncenter.enums.BloodType;
import com.bloodtransfusioncenter.enums.Gender;
import com.bloodtransfusioncenter.enums.UrgencyLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet controller for Recipient operations.
 * Handles HTTP requests for recipient management (CRUD operations).
 */
public class RecipientServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(RecipientServlet.class);
    private RecipientService recipientService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.recipientService = RecipientService.getInstance();
        logger.info("RecipientServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = getAction(request);
        logger.info("RecipientServlet doGet - action: {}", action);

        try {
            switch (action) {
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteRecipient(request, response);
                    break;
                default:
                    listRecipients(request, response);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error in RecipientServlet doGet", e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/common/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = getAction(request);
        logger.info("RecipientServlet doPost - action: {}", action);

        try {
            switch (action) {
                case "create":
                    createRecipient(request, response);
                    break;
                case "update":
                    updateRecipient(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/recipients/list");
                    break;
            }
        } catch (Exception e) {
            logger.error("Error in RecipientServlet doPost", e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(request, response);
        }
    }

    /**
     * Displays the list of all recipients ordered by urgency.
     */
    private void listRecipients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Recipient> recipients = recipientService.getAllRecipientsOrderedByUrgency();
        request.setAttribute("recipients", recipients);
        request.getRequestDispatcher("/WEB-INF/views/recipient-list.jsp").forward(request, response);
    }

    /**
     * Shows the recipient creation form.
     */
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("mode", "create");
        request.setAttribute("entityType", "recipient");
        request.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(request, response);
    }

    /**
     * Shows the recipient edit form.
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long id = Long.parseLong(request.getParameter("id"));
        Recipient recipient = recipientService.findRecipientById(id)
                .orElseThrow(() -> new Exception("Recipient not found"));

        request.setAttribute("recipient", recipient);
        request.setAttribute("mode", "edit");
        request.setAttribute("entityType", "recipient");
        request.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(request, response);
    }

    /**
     * Creates a new recipient.
     */
    private void createRecipient(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Recipient recipient = extractRecipientFromRequest(request);
        recipientService.createRecipient(recipient);

        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Recipient created successfully!");

        response.sendRedirect(request.getContextPath() + "/recipients/list");
    }

    /**
     * Updates an existing recipient.
     */
    private void updateRecipient(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long id = Long.parseLong(request.getParameter("id"));
        Recipient recipient = extractRecipientFromRequest(request);
        recipient.setId(id);

        recipientService.updateRecipient(recipient);

        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Recipient updated successfully!");

        response.sendRedirect(request.getContextPath() + "/recipients/list");
    }

    /**
     * Deletes a recipient.
     */
    private void deleteRecipient(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long id = Long.parseLong(request.getParameter("id"));
        recipientService.deleteRecipient(id);

        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Recipient deleted successfully!");

        response.sendRedirect(request.getContextPath() + "/recipients/list");
    }

    /**
     * Extracts recipient data from request parameters.
     */
    private Recipient extractRecipientFromRequest(HttpServletRequest request) {
        Recipient recipient = new Recipient();

        recipient.setFirstName(request.getParameter("firstName"));
        recipient.setLastName(request.getParameter("lastName"));
        recipient.setPhone(request.getParameter("phone"));
        recipient.setCin(request.getParameter("cin"));
        recipient.setBirthDate(LocalDate.parse(request.getParameter("birthDate")));
        recipient.setGender(Gender.valueOf(request.getParameter("gender")));
        recipient.setBloodType(BloodType.valueOf(request.getParameter("bloodType")));
        recipient.setUrgencyLevel(UrgencyLevel.valueOf(request.getParameter("urgencyLevel")));

        return recipient;
    }

    /**
     * Extracts the action from the request path.
     */
    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return "list";
        }
        return pathInfo.substring(1);
    }
}