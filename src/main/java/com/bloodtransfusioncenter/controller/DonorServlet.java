package com.bloodtransfusioncenter.controller ;

import com.bloodtransfusioncenter.model.Donor;
import com.bloodtransfusioncenter.service.DonorService;
import com.bloodtransfusioncenter.enums.BloodType;
import com.bloodtransfusioncenter.enums.Gender;
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
 * Servlet controller for Donor operations
 * Handles http requests for donor management
 */

public class DonorServlet extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(DonorServlet.class);
    private DonorService donorService ;

    @Override
    public void init() throws ServletException {
        super.init();
        this.donorService = DonorService.getInstance() ;
        logger.info("DonorServlet Initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String action = getAction(request);
        logger.info("DonorServlet doGet - action {}", action);

        try {
            switch (action) {
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteDonor(request, response);
                    break;
                default:
                    listDonors(request, response);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error in DonorServer doGet" , e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("WEB-INF/views/common/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = getAction(request);
        logger.info("DonorServlet doPost - action: {}", action);

        try {
            switch (action) {
                case "create":
                    createDonor(request, response);
                    break;
                case "update":
                    updateDonor(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/donors/list");
                    break;
            }
        } catch (Exception e) {
            logger.error("Error in DonorServlet doPost", e);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(request, response);
        }
    }

    /**
     * Display the list of donors
     */
//    private void listDonors(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        List<Donor> donors = donorService.getAllDonors();
//        request.setAttribute("donors", donors);
//        List<Donor> donorsWithRecipients = donorService.getAllDonorsWithRecipients();
//        request.setAttribute("donors", donorsWithRecipients);
//        request.getRequestDispatcher("/WEB-INF/views/donor-list.jsp").forward(request, response);
//    }
    private void listDonors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Donor> donorsWithRecipients = donorService.getAllDonorsWithRecipients();
        request.setAttribute("donors", donorsWithRecipients);
        request.getRequestDispatcher("/WEB-INF/views/donor-list.jsp").forward(request, response);
    }


    /**
     * Show the donor creation from
     */
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("mode", "create");
        request.setAttribute("entityType", "donor");
        request.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(request, response);
    }

    /**
     * Show the donor edit form
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long id = Long.parseLong(request.getParameter("id"));
        Donor donor = donorService.findDonorById(id)
                .orElseThrow(() -> new Exception("Donor not found"));

        request.setAttribute("donor", donor);
        request.setAttribute("mode", "edit");
        request.setAttribute("entityType", "donor");
        request.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(request, response);
    }

    /**
     * Create new donor
     */
    private void createDonor(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Donor donor = extractDonorFromRequest(request);
        donorService.createDonor(donor);

        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Donor created successfully!");

        response.sendRedirect(request.getContextPath() + "/donors/list");
    }

    /**
     * update an existing donor
     */
    private void updateDonor(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long id = Long.parseLong(request.getParameter("id"));
        Donor donor = extractDonorFromRequest(request);
        donor.setId(id);

        donorService.updateDonor(donor);

        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Donor updated successfully!");

        response.sendRedirect(request.getContextPath() + "/donors/list");
    }

    /**
     * Delete a donor
     */
    private void deleteDonor(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long id = Long.parseLong(request.getParameter("id"));
        donorService.deleteDonor(id);

        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Donor deleted successfully!");

        response.sendRedirect(request.getContextPath() + "/donors/list");
    }

    /**
     * Extracts donor data from request parameters.
     */
    private Donor extractDonorFromRequest(HttpServletRequest request) {
        Donor donor = new Donor();

        donor.setFirstName(request.getParameter("firstName"));
        donor.setLastName(request.getParameter("lastName"));
        donor.setPhone(request.getParameter("phone"));
        donor.setCin(request.getParameter("cin"));
        donor.setBirthDate(LocalDate.parse(request.getParameter("birthDate")));
        donor.setGender(Gender.valueOf(request.getParameter("gender")));
        donor.setBloodType(BloodType.valueOf(request.getParameter("bloodType")));
        donor.setWeight(Double.parseDouble(request.getParameter("weight")));
        donor.setContraindications(request.getParameter("contraindications"));

        return donor;
    }

    /**
     * Extract the actions from the request
     */
    private String getAction(HttpServletRequest request){
        String pathinfo = request.getPathInfo();
        if (pathinfo == null || pathinfo.equals("/")){
            return "list" ;
        }
        return pathinfo.substring(1);
    }
}
