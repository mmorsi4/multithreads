package Services;


import Entity.*;
public class AuthService{
    private User loggedInUser;
    private CustomerService customerService;
    private AdminService adminService;

    public AuthService(CustomerService customerService, AdminService adminService){
        this.adminService = adminService;
        this.customerService = customerService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public boolean Login(String username, String password){
        if (loggedInUser!=null) {
            return false;
        }

        if (username==null || password==null) {
            return false;
        }

        loggedInUser = customerService.get(username);

        if (loggedInUser !=null) {
            if (loggedInUser.checkPassword(password)) {
                return true;
            }else{
                loggedInUser = null;
                return false;
            }
        }

        loggedInUser = adminService.get(username);
        if (loggedInUser !=null) {
            if (loggedInUser.checkPassword(password)) {
                return true;
            }else{
                loggedInUser = null;
                return false;
            }
        }
        
        return false;
    }

    public void Logout(){
        loggedInUser = null;
    }

    public boolean Signup(String username, String password, java.util.Date dateOfBirth, double balance, String address, Gender gender){
        if (loggedInUser!=null) {
            return false;
        }

        if (customerService.get(username)!=null) {
            return false;
        }

        try {
            customerService.create(username, password, dateOfBirth, balance, address, gender);
            loggedInUser = customerService.get(username);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
