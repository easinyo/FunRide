/**
 * In the FunRideApp, a User is the object reprenseting the app user who will create an account.
 * It contains: 
 * FirstName
 * FirstName
 * Email
 * Phone
 * Password
 * ConfirmedPassword
 */
package com.dubinostech.rideshareapp.repository.Data;

public class User {

    private String firstName,lastName,email,phone,password, confirmPassword;

    /**
     * Constructor used for initializing a user.
     * 
     * @param firstName
     *            the user's first name
     * @param lastName
     *            the user's last name
     * @param email
     *            the user's email
     * @param phone
     *            the user's last phone
     * @param password
     *            the user's password
     * @param confirmPassword
     *            the user's confirmed password
     */

    public User(String firstName, String lastName, String email, String phone, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public User() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.phone = null;
        this.password = null;
        this.confirmPassword = null;
    }

    /**
     * Changes the user's first name 
     * 
     * @param firstName
     *            the firstname to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Changes the user's last name 
     * 
     * @param firstName
     *            the lastname to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Changes the user's email  
     * 
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Changes the user's phone  
     * 
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Changes the user's password  
     * 
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Changes the user's confirmedPassword  
     * 
     * @param confirmedPassword
     *            the confirmedPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Getter for first name
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for las name
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for phone
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Getter for password 
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for confirmedPassword
     *
     * @return confirmedPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }
}
