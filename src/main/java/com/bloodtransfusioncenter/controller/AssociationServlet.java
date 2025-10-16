package com.bloodtransfusioncenter.controller;

import com.bloodtransfusioncenter.model.Donor;
import com.bloodtransfusioncenter.model.Recipient;
import com.bloodtransfusioncenter.service.AssociationService;
import com.bloodtransfusioncenter.service.DonorService;
import com.bloodtransfusioncenter.service.RecipientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet controller for Donor-Recipient associations.
 * Handles the matching process between donors and recipients.
 */
public class AssociationServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AssociationServlet.class);

    private AssociationService associationService;
    private DonorService donorService;
    private RecipientService recipientService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.associationService = AssociationService.getInstance();
        this.donorService = DonorService.getInstance();
        this.recipientService = RecipientService.getInstance();
        logger.info("AssociationServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = getAction(request);
        logger.info("AssociationServlet doGet - action: {}", action);

        try {
            switch (action) {
                case "form":
                    showAssociationForm(request, response);
                    break;
                case "remove":
                    removeAssociation(request, response);
                    break;
                case "compatible-donors":
                    showCompatibleDonors(request, response);
                    break;
                case "compatible-recipients":
                    showCompatibleRecipients(request, response);
                    break;
                default:
                    showAssociationForm(request, response);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error in AssociationServlet doGet", e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/common/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = getAction(request);
        logger.info("AssociationServlet doPost - action: {}", action);

        try {
            if (action.equals("associate")) {
                associateDonorToRecipient(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/associations/form");
            }
        } catch (Exception e) {
            logger.error("Error in AssociationServlet doPost", e);
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/associations/form");
        }
    }

    /**
     * Shows the association form with available donors and waiting recipients.
     */
    private void showAssociationForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Donor> availableDonors = donorService.getAvailableDonors();
        List<Recipient> waitingRecipients = recipientService.getWaitingRecipients();

        request.setAttribute("donors", availableDonors);
        request.setAttribute("recipients", waitingRecipients);
        request.getRequestDispatcher("/WEB-INF/views/association/form.jsp").forward(request, response);
    }

    /**
     * Shows compatible donors for a specific recipient.
     */
    private void showCompatibleDonors(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long recipientId = Long.parseLong(request.getParameter("recipientId"));
        List<Donor> compatibleDonors = associationService.getCompatibleAvailableDonors(recipientId);

        Recipient recipient = recipientService.findRecipientById(recipientId)
                .orElseThrow(() -> new Exception("Recipient not found"));

        request.setAttribute("donors", compatibleDonors);
        request.setAttribute("recipient", recipient);
        request.getRequestDispatcher("/WEB-INF/views/association/form.jsp").forward(request, response);
    }

    /**
     * Shows compatible recipients for a specific donor.
     */
    private void showCompatibleRecipients(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long donorId = Long.parseLong(request.getParameter("donorId"));
        List<Recipient> compatibleRecipients = associationService.getCompatibleWaitingRecipients(donorId);

        Donor donor = donorService.findDonorById(donorId)
                .orElseThrow(() -> new Exception("Donor not found"));

        request.setAttribute("recipients", compatibleRecipients);
        request.setAttribute("donor", donor);
        request.getRequestDispatcher("/WEB-INF/views/association/form.jsp").forward(request, response);
    }

    /**
     * Associates a donor with a recipient.
     */
    private void associateDonorToRecipient(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long donorId = Long.parseLong(request.getParameter("donorId"));
        Long recipientId = Long.parseLong(request.getParameter("recipientId"));

        associationService.associateDonorToRecipient(donorId, recipientId);

        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Donor and Recipient associated successfully!");

        response.sendRedirect(request.getContextPath() + "/associations/form");
    }

    /**
     * Removes association between donor and recipient.
     */
    private void removeAssociation(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long donorId = Long.parseLong(request.getParameter("donorId"));
        associationService.removeAssociation(donorId);

        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Association removed successfully!");

        response.sendRedirect(request.getContextPath() + "/donors/list");
    }

    /**
     * Extracts the action from the request path.
     */
    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return "form";
        }
        return pathInfo.substring(1); 
    }
}