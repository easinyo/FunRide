package com.dubinostech.rideshareapp.ui.signup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dubinostech.rideshareapp.R;
import com.dubinostech.rideshareapp.data.ErrorCode;
import com.dubinostech.rideshareapp.data.SignupResponse;
import com.dubinostech.rideshareapp.data.Utils;
import com.dubinostech.rideshareapp.model.SignModel.SignUpModel;
import com.dubinostech.rideshareapp.presenter.SignupPresenter;
import com.dubinostech.rideshareapp.ui.MainActivity;
import com.dubinostech.rideshareapp.ui.view.SignUpView;

import org.jetbrains.annotations.Nullable;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SignUpView {
    private Button signup;
    private TextView title;
    private ProgressDialog progressDialog;
    private EditText firstname, lastname,email,phone,password,confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup = findViewById(R.id.signup);
        title = findViewById(R.id.title);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("USER AGREEMENT");
            builder1.setMessage("ACCEPTANCE OF TERMS\n" +
                    "Welcome to FunRide. FunRide provides its service to you, subject to the following Terms of Service(TOS), which may be updated by us from time to time without notice. You can review the most recent version of the TOS at any time at www.funride.com/termsofservice . In addition, when using our services, you may be subject to guidelines, rules, or additional terms that may be posted occasionally that are applicable to such services. FunRide reserves the right at any time to modify or discontinue, temporarily or permanently, the Service (or any part thereof) with or without notice. You agree that FunRide will not be liable to you or to any third party for any modification, suspension or cancellation of the Service.\n" +
                    "\n" +
                    "DESCRIPTION OF SERVICE\n" +
                    "FunRide facilitates the pairing of passengers and drivers wanting to ride-share mostly within, but not limited to, Canada and the United States. The services offered by FunRide are available on the Google Play Store for Android devices.\n" +
                    "\n" +
                    "Drivers are allowed to post rides, that is, to post an itinerary inviting potential passengers to ride-share with you in exchange for a contribution for gas and benefit from FunRide's pairing service. You can post a ride either using our mobile app. Passengers are allowed to reserve and book a seat in the available rides posted by drivers. You can book a seat by calling by using our mobile app.\n" +
                    "\n" +
                    "Pairing passengers and drivers: the information provided in the ride postings are not sufficient for passengers to communicate directly with the drivers. Passengers must first book one or more seats, depending on availability, and are then provided with (i) the details of the meeting place, (ii) the driver's identity, and the (iii) the details of the vehicle ( model, make, color, license plate). Once the seat is booked, the driver will also be able to know the identity of the passenger(s). Names and phone numbers are kept confidential unless explicitly requested by the user concerned.\n" +
                    "\n" +
                    "All potential new features released or updates to the services described above, or any services launched in the future will be also subject to these Terms and Conditions.\n" +
                    "\n" +
                    "GENERAL\n" +
                    "Entire Agreement\n" +
                    "The TOS constitute the entire agreement between you and FunRide and govern your use of the Service, superseding any prior agreements, oral or written, between you and FunRide with respect to the Service. You may also be subject to additional terms and conditions that may apply when you use or purchase certain FunRide services, affiliate services, third-party content or third-party software.\n" +
                    "\n" +
                    "Choice of Law and Forum\n" +
                    "The TOS and the relationship between you and FunRide shall be governed by the laws of the province of Quebec and Canada without regard to its conflict of law provisions. You and FunRide agree to submit to the personal and exclusive jurisdiction of the courts located within the province of Ontario, Canada.\n" +
                    "\n" +
                    "No Right of Survivorship and Non-Transferability\n" +
                    "You agree that your FunRide account is non-transferable and any rights to your FunRide I.D. or Content within your account terminate upon your death. Upon receipt of a copy of a death certificate, your account may be terminated and all Content permanently deleted.\n" +
                    "\n" +
                    "Statute of Limitations\n" +
                    "You agree that regardless of any statute or law to the contrary, any claim or cause of action arising out of or related to use of the Service or related to the TOS must be filed within one (1) year after such claim or cause of action arose or be forever barred.\n" +
                    "\n" +
                    "Further Assurances\n" +
                    "You agree to use reasonable efforts to do, make, execute, deliver, or cause to be done, made, executed, or delivered, all such further acts, documents, and things as FunRide may reasonably require from time to time for the purpose of giving effect to these TOS, including regularly reviewing the TOS and updating your registration information.\n" +
                    "\n" +
                    "The section titles of the above Terms of Service are used only to facilitate reading. They have no legal or contractual value.\n" +
                    "\n" +
                    "REGISTRATION OBLIGATIONS\n" +
                    "In consideration of your use of the Service, you confirm that you are of legal age to form a binding contract and are not a person barred from receiving services under the laws of Canada or other applicable jurisdictions. You also agree to (a) provide true, accurate, current and complete information about yourself as prompted by the Service's registration form (such information being the \"Registration Data\") and (b) maintain and promptly update the Registration Data to keep it true, accurate, current and complete. If you provide any information that is untrue, inaccurate, not current or incomplete, or if FunRide has reasonable grounds to suspect that such information is untrue, inaccurate, not current or incomplete, FunRide has the right to suspend or terminate your account and refuse any and all current or future use of the Service (or any portion thereof).\n" +
                    "\n" +
                    "FunRide is concerned about the safety and privacy of all its users, particularly children. For this reason, parents who wish to allow their children access to the Service should assist them in setting up any relevant accounts and supervise their access to the Service. Please remember that the Service is designed to appeal to a broad audience. Accordingly, as the legal guardian, it is your responsibility to determine whether any of the Services and/or Content are appropriate for your child. FunRide requires an adult to accompany any child under the age of fourteen (14) who travels using the Service.\n" +
                    "\n" +
                    "ACCOUNT, SECURITY, AND TERMINATION\n" +
                    "During the registration for an FunRide I.D., you will select a password and FunRide I.D. Upon successful registration for the Service, you will receive an account designation. You understand and agree that you are solely responsible for maintaining the confidentiality of your account including your password, and are fully responsible for all activities that occur under your account, including your password. You agree to (a) immediately notify FunRide of any unauthorized use of your password or account or any other breach of security, and (b) exit from your account at the end of each session. FunRide will not be liable for any loss or damage arising from your failure to comply.\n" +
                    "\n" +
                    "You agree that FunRide, in its sole discretion, may terminate your account or any part thereof, including your use of the Service, and remove and discard any Content within the Service, for any reason, including, without limitation, (a) lack of use, (b) if FunRide believes that you have violated or acted inconsistently with the letter or spirit of the TOS or other incorporated agreements or guidelines, (c) requests by law enforcement or other government agencies, (d) a request by you (self-initiated account deletions), (e) cancellation or material modification to the Service (or any part thereof), (f) unexpected technical or security issues or problems, (g) in compliance with legal process; (h) if you have or we believe you have engaged in illegal activities, including without limitation, fraud, and/or (i) nonpayment of any fees owed by you in connection with the Services. FunRide may also, in its sole discretion and at any time, discontinue providing the Service, or any part thereof, with or without notice.\n" +
                    "\n" +
                    "You acknowledge and agree that any termination of your access to the Service under any provision of this TOS may be effected without prior notice and that FunRide may immediately deactivate or delete your account and all related Content associated with or stored in your account and/or bar any further access to such Content or the Service. Further, you agree that FunRide is not liable to you or any third-party for termination of your account or termination of your access to the Service.\n" +
                    "\n" +
                    "As FunRide IDs are the property of FunRide, upon the termination of your account the FunRide I.D. previously associated with your account will become available to other FunRide users to select as their FunRide I.D.\n" +
                    "\n" +
                    "USER RESPONSIBILITIES\n" +
                    "General\n" +
                    "You understand that all materials, including without limitation, information, data, text, software, music, sound, photographs, graphics, video, and email messages or other kinds of messages (\"Content\"), whether publicly posted or privately transmitted, are the sole responsibility of the person from which such Content originated. This means that you, and not FunRide, are entirely responsible for all Content that you upload, post, email, transmit or otherwise make available via the Service. FunRide does not control the Content posted via the Service and, as such, does not guarantee the accuracy, integrity or quality of such Content. You understand that by using the Service, you may be exposed to Content that is offensive, indecent or objectionable. Under no circumstances will FunRide be liable in any way for any Content, including, but not limited to, for any errors or omissions in any Content, or for any loss or damage of any kind incurred as a result of the use of or reliance upon any Content posted, emailed, transmitted, or otherwise made available via the Service.\n" +
                    "\n" +
                    "You acknowledge that FunRide does not pre-screen Content, but that FunRide and its designees have the right (but not the obligation) in their sole discretion to refuse or move any Content that is available via the Service. Moreover, FunRide and its designees shall have the right to remove any Content that violates the TOS or is otherwise objectionable. You agree that you must evaluate, and bear all risks associated with, the use of any Content, including any reliance on the accuracy, completeness, or usefulness of such Content. In this regard, you acknowledge that you may not rely on any Content created by FunRide or submitted to FunRide that is available on any part of the Service, including but not limited to information in FunRide forums or blog.\n" +
                    "\n" +
                    "You acknowledge and expressly consent to FunRide accessing, preserving, and disclosing your account information and Content if required to do so by law or in the good faith belief that such access, preservation or disclosure is reasonably necessary to (a) comply with legal process; (b) enforce the TOS; (c) respond to claims that any Content violates the rights of third-parties; (d) respond to your requests for customer service; or (e) protect the rights, property, or personal safety of FunRide, its users and the public.\n" +
                    "\n" +
                    "You understand that the technical processing and transmission of the Service, including your Content, may involve (a) transmissions over various networks; and (b) changes to conform and adapt to technical requirements of connecting networks or devices.\n" +
                    "\n" +
                    "You understand that the Service and software embodied within the Service may include security components that permit digital materials to be protected, and use of these materials is subject to usage rules set by FunRide and/or content providers who provide Content to the Service. You agree that you will not attempt to override or circumvent any of the usage rules embedded into the Service. Any unauthorized reproduction, publication, further distribution or public exhibition of the materials provided on the Service, in whole or in part, is strictly prohibited.\n" +
                    "\n" +
                    "FunRide attaches great importance to the driving history of its driver members, particularly regarding driving under the influence of alcohol. Please note that drivers with a history of driving under the influence of alcohol (DUI) will be denied access to our service as long as they are obliged by law to drive with an alcohol breath tester (breathalyzer) or any alcohol tester of any kind.\n" +
                    "\n" +
                    "If you plan on using our service to, from, or within the province of Ontario, it is your responsibility to read, understand and abide by the Public Vehicle Act of Ontario. The Public Vehicle Act of Ontario clearly defines a \"carpool vehicle\" and the conditions you must meet to be excluded from being considered a public transport vehicle or taxi.\n" +
                    "\n" +
                    "To facilitate border crossing between the U.S. and Canada, we ask all FunRide members to:\n" +
                    "\n" +
                    "Be 18 years of age or older ;\n" +
                    "Be free of a criminal record in Canada or in the U.S. ;\n" +
                    "Have the required personal identification ready for presentation at primary inspection before arriving at the border.\n" +
                    "For more information, please consult the Canada-U.S. Border Crossing Check-List\n" +
                    "\n" +
                    "Good Faith\n" +
                    "All participants in ride-share have the responsibility to ensure that the ride goes as smoothly as possible. A good-faith approach and adherence to the values of FunRide are required from all members, even if another member infringes on the terms of service. These values are a collaboration, care, and commitment to excellence.\n" +
                    "\n" +
                    "Cancellations\n" +
                    "Please note that repeated cancellations greatly reduce the overall reliability of the service for driver as well as passenger members. FunRide reserves the right to suspend your account.\n" +
                    "\n" +
                    "INTERNATIONAL USERS\n" +
                    "Recognizing the global nature of the Internet, you agree to comply with all local rules regarding online conduct and acceptable Content. You also acknowledge that it is your responsibility to have personal, travel or driver insurance for you and, if applicable, for your passengers, and abide by the current Canadian laws and local laws applicable in your province or country of residence and / or destination concerning personal information, internet and data transmission from Canada and the province or state or country where you reside. Any breach of the agreement stated in the previous paragraph could be sanctioned according to Canadian law or other applicable laws and result in you being held accountable for your actions in a court of law.\n" +
                    "\n" +
                    "CONTRIBUTIONS TO FunRide\n" +
                    "By submitting ideas, suggestions, documents, and/or proposals (\"Contributions\") to FunRide through its suggestion or feedback webpages, forums or blog, you acknowledge and agree that: (a) your Contributions do not contain confidential or proprietary information; (b) FunRide is not under any obligation of confidentiality, express or implied, with respect to the Contributions; (c) FunRide shall be entitled to use or disclose (or choose not to use or disclose) such Contributions for any purpose, in any way, in any media worldwide; (d) FunRide may have something similar to the Contributions already under consideration or in development; (e) your Contributions automatically become the property of FunRide without any obligation of FunRide to you; and (f) you are not entitled to any compensation or reimbursement of any kind from FunRide under any circumstances.\n" +
                    "\n" +
                    "INDEMNITY\n" +
                    "You acknowledge and accept that FunRide does not do any background checks on its users, cannot be responsible for the behaviour and personal conduct of its members or any criminal acts committed by them before, during and after using the service, and does not verify or validate the veracity of the information submitted by its members. You are solely responsible for your own protection and safety when engaging and communicating with other community members. FunRide is not responsible for any loss or damage or personal injury, physical, psychological or material, direct or indirect, that you, another member or a third party may incur before, while and after using the service, by using any information on this website, by the misuse of the service by other members or by information FunRide obtained by you or other members of the service.\n" +
                    "\n" +
                    "You agree to indemnify and hold FunRide and its subsidiaries, affiliates, officers, employees, agents, co-branders, partners and licensors harmless from any claim or demand, including reasonable legal fees, made by any third party due to or arising out of Content you submit, post, transmit or make available through the Service, your use of the Service, your connection to the Service, your violation of the TOS, or your violation of any rights of another.\n" +
                    "\n" +
                    "NO RESALE OF SERVICE\n" +
                    "You agree not to reproduce, duplicate, copy, sell, trade, or exploit for any commercial purposes, any portion of the Service (including the FunRide I.D. associated with your account), use of the Service, or access to the Service. You also agree not to be involved, directly or indirectly, with any activity that could compete with FunRide.\n" +
                    "\n" +
                    "MODIFICATIONS TO SERVICE\n" +
                    "FunRide reserves the right at any time to modify or discontinue, temporarily or permanently, the Service (or any part thereof) with or without notice. You agree that FunRide will not be liable to you or to any third party for any modification, suspension or cancellation of the Service.\n" +
                    "\n" +
                    "EXTERNAL LINKS\n" +
                    "The Service may provide, or third parties may provide, links to other World Wide Web sites or resources. Because FunRide has no control over such sites and resources, you acknowledge and agree that FunRide is not responsible for the availability of such external sites or resources, and does not endorse and is not responsible or liable for any Content, advertising, products, or other materials on or available from such sites or resources. You further acknowledge and agree that FunRide is not responsible or liable, directly or indirectly, for any damage or loss caused or alleged to be caused by or in connection with use of or reliance on any such Content, goods or services available on or through any such site or resource.\n" +
                    "\n" +
                    "LIABILITY LIMITATIONS\n" +
                    "You expressly understand and agree that FunRide and its subsidiaries, affiliates, officers, employees, agents, co-branders, partners and licensors shall not be liable for any direct, indirect, incidental, special, consequential, or exemplary damages, including but not limited to, damages for loss of profits, goodwill, use, data, or other intangible losses (even if FunRide has been advised of the possibility of such damages), resulting from: (i) the use or the inability to use the service; (ii) the cost of procurement of substitute goods and services resulting from any goods, data, information, or services purchased or obtained or messages received or transactions entered into through or from the service; (iii) unauthorized access to or alteration of your transmissions or data; (iv) statements or conduct of any third party on the service; or (v) any other matter relating to the service.\n" +
                    "\n" +
                    "DISCLAIMER\n" +
                    "You understand and agree that: A. Your use of the service is at your sole risk. The service is provided on an \"As is\" and \"As available\" basis. FunRide and its subsidiaries, affiliates, officers, employees, agents, co-branders, partners and licensors expressly disclaim all warranties, representations, and conditions of any kind, whether express or implied, including, but not limited to the implied warranties, representations, and conditions of merchantability, fitness for a particular purpose and non-infringement.\n" +
                    "\n" +
                    "B. FunRide and its subsidiaries, affiliates, officers, employees, agents, co-branders, partners and licensors make no warranty that (I) the service will meet your requirements, (ii) the service will be uninterrupted, timely, secure, or error-free, (iii) the results that may be obtained from the use of the service will be accurate or reliable, (iv) the quality of any products, services, information, or other material purchased or obtained by you through the service will meet your expectations, and (v) any errors in the software will be corrected.\n" +
                    "\n" +
                    "C. Any material downloaded or otherwise obtained through the use of the service is done at your own discretion and risk and that you will be solely responsible for any damage to your computer system or loss of data that results from the download of any such material. No advice or information, whether oral or written, obtained by you from FunRide or through or from the service shall create any warranty not expressly stated in the TOS.\n" +
                    "\n" +
                    "D. No advice or information, whether oral or written, obtained by you from FunRide or through or from the service shall create any warranty not expressly stated in the TOS.\n" +
                    "\n" +
                    "VIOLATIONS\n" +
                    "Please report any violations of the TOS to our customer service agents.");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Accept", ((DialogInterface dialog, int which) -> {
                register();
            }));

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    private void register(){
        String firstnameStr = firstname.getText().toString();
        String lastnameStr = lastname.getText().toString();
        String emailStr = email.getText().toString();
        String phoneStr = phone.getText().toString();
        String passwordStr = password.getText().toString();
        String confirmPasswordStr = confirmpassword.getText().toString();
        this.progressDialog = new ProgressDialog(this);

        if(firstnameStr.isEmpty())
        {
            displayToast("First name  field empty");
        }
        else if( lastnameStr.isEmpty()){
            displayToast("Last name field empty");
        }
        else if( emailStr.isEmpty()){
            displayToast("Email field empty");
        }

        else if( phoneStr.isEmpty()){
            displayToast("Phone field empty");
        }
        else if( passwordStr.isEmpty()){
            displayToast("password field empty");
        }
        else  if(confirmPasswordStr.isEmpty()){
            displayToast("Sex field is empty ");
        }
        else if(!confirmPasswordStr.equals(passwordStr)){
            displayToast("passwords don't match ");
        } else if (!Utils.isNetworkAvailable(this)) {
            Utils.displayCommonAlertDialog(this, String.valueOf(R.string.connection_issue_msg));
        }
        else{
            SignupPresenter presenter = new SignupPresenter(this, new SignUpModel());
            presenter.callSignUp(emailStr, passwordStr, confirmPasswordStr);
        }
    }

    public void signupSuccess(@Nullable SignupResponse user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("home_msg", "This is HOME");
        this.startActivity(intent);
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (progressDialog != null)
            progressDialog.setTitle("Sign up");
        progressDialog.setMessage(String.valueOf(R.string.activity_login_loading_msg));
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void setEmailError(ErrorCode code) {
        if (code.getId() == ErrorCode.EMAIL_INVALID.getId()) {
            email.setError(getString(R.string.activity_login_email_invalid));
        }
    }

    @Override
    public void setPasswordError(ErrorCode code) {
        if (code.getId() == ErrorCode.PASSWORD_INVALID.getId()) {
            password.setError(getString(R.string.activity_login_password_err));
        }
    }

    @Override
    public void signUpSuccess(SignupResponse user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("home_msg", "This is HOME");
        startActivity(intent);
        finish();
    }

    @Override
    public void signUpFailure(ErrorCode code) {
        if (code.getId() == 5) {
            Toast.makeText(
                    this,
                    String.valueOf(R.string.activity_signup_email_existing),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void signUpFailure(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show();
    }
}