package com.usjt.beehealthy.Utilities;

import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.RadioButton;

import com.usjt.beehealthy.Model.Consult;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.Model.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static Object gettingLoginAttributes(String type, JSONObject response) throws JSONException {
        if (type.equals("nutritionist")) {
            Nutritionist nutritionist = new Nutritionist();
            nutritionist.setFullname(response.getString("fullname"));
            nutritionist.setIduser(response.getLong("iduser"));

            nutritionist.setEmail(response.getString("email"));
            nutritionist.setPassword(response.getString("password"));
            nutritionist.setBirthday(response.getString("birthday"));
            nutritionist.setType(response.getString("type"));
            nutritionist.setSpecialization(response.getString("specialization"));
            nutritionist.setCrn(response.getString("crn"));

            return nutritionist;
        } else if (type.equals("patient")) {
            Patient patient = new Patient();
            patient.setIduser(response.getLong("iduser"));
            patient.setFullname(response.getString("fullname"));
            patient.setEmail(response.getString("email"));
            patient.setPassword(response.getString("password"));
            patient.setBirthday(response.getString("birthday"));
            patient.setType(response.getString("type"));
            patient.setHeight(response.getDouble("height"));
            patient.setWeight(response.getDouble("weight"));
            return patient;
        }
        return null;
    }

    public static String getType(RadioButton patientRButton, RadioButton nutritionistRButton){
        String type = null;
        if (patientRButton.isChecked()) {
            type = "patient";
        } else if (nutritionistRButton.isChecked()) {
            type = "nutritionist";
        }
        return type;
    }


    public static JSONObject userObj (EditText email, EditText password, String type) throws JSONException{
        JSONObject user = new JSONObject();
        user.put("email", email.getText());
        user.put("password", password.getText());
        user.put("type", type);
        return user;
    }

    public static JSONObject userObj (EditText email, EditText password, String type, EditText fullname) throws JSONException{
        JSONObject user = new JSONObject();
        user.put("email", email.getText());
        user.put("password", password.getText());
        user.put("type", type);
        user.put("fullname", fullname.getText());
        return user;
    }

    public static List<Consult> populateConsultList(JSONArray consultsArray) throws JSONException {
        List<Consult> consults = new ArrayList<>();
        JSONObject consultObject;

        for (int i = 0; i < consultsArray.length(); i++) {
            consultObject = consultsArray.getJSONObject(i);
            JSONObject nutritionistObject = consultObject.getJSONObject("nutritionist");
            JSONObject patientObject = consultObject.getJSONObject("patient");

            Long idconsult = consultObject.getLong("idconsult");
            String place = consultObject.getString("place");
            String date = consultObject.getString("date");
            Long idnutritionist = nutritionistObject.getLong("iduser");
            String nutritionistEmail = nutritionistObject.getString("email");
            String nutritionistFullname = nutritionistObject.getString("fullname");
            String nutritionistBirthday = nutritionistObject.getString("birthday");
            String specialization = nutritionistObject.getString("specialization");
            String crn = nutritionistObject.getString("crn");
            Long idpatient = patientObject.getLong("iduser");
            String patientEmail = patientObject.getString("email");
            String patientFullname = patientObject.getString("fullname");
            String patientBirthday = patientObject.getString("birthday");
            Double weight = patientObject.getDouble("weight");
            Double height = patientObject.getDouble("height");
            String description = patientObject.getString("description");

            Nutritionist nutritionist = new Nutritionist(
                    idnutritionist, nutritionistEmail, nutritionistFullname, nutritionistBirthday, specialization, crn);

            Patient patient = new Patient(
                    idpatient, patientEmail, patientFullname, patientBirthday, weight, height, description);

            Consult consult = new Consult(idconsult, place, date, nutritionist, patient);

            consults.add(consult);
        }
        return consults;
    }


    public static JSONObject nutritionistObj(Nutritionist nutritionist) throws JSONException {
        JSONObject nutritionistObject = new JSONObject();
        nutritionistObject.put("iduser", nutritionist.getIduser());
        nutritionistObject.put("email", nutritionist.getEmail());
        nutritionistObject.put("type", "nutritionist");
        nutritionistObject.put("password", nutritionist.getPassword());
        nutritionistObject.put("birthday", nutritionist.getBirthday());
        nutritionistObject.put("fullname", nutritionist.getFullname());
        nutritionistObject.put("crn", nutritionist.getCrn());
        nutritionistObject.put("specialization", nutritionist.getSpecialization());
        return nutritionistObject;
    }

}
