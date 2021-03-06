package com.example.votingapp.models;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.util.Log;

import com.example.votingapp.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.votingapp.MethodLibrary.API_DATE_FORMAT;
import static com.example.votingapp.MethodLibrary.M_D_FORMAT;
import static com.example.votingapp.MethodLibrary.M_D_Y_FORMAT;
import static com.example.votingapp.MethodLibrary.NUMBER_DATE_FORMAT;
import static com.example.votingapp.MethodLibrary.getDaysBeforeDate;
import static com.example.votingapp.MethodLibrary.getFormattedDate;

@Parcel
public class Election {

    public static final int REGISTER_DAYS_BEFORE = -30;
    private static final int ABSENTEE_DAYS_BEFORE = -7;
    private static final String TAG = "Election";
    private static final int REMINDER_DAYS_BEFORE = -3;
    String name;
    String electionDay;
    int id;
    String division;
    HashMap<String, Action> actions;

    // no-arg, empty constructor required for Parceler
    public Election() {}

    public Election(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        electionDay = jsonObject.getString("electionDay");
        id = jsonObject.getInt("id");
        division = jsonObject.getString("ocdDivisionId");
    }

    public String getName() {
        return name;
    }

    public String getRegisterDeadline() {
        return getDaysBeforeDate(electionDay, REGISTER_DAYS_BEFORE, API_DATE_FORMAT, NUMBER_DATE_FORMAT);
    }

    public String getElectionReminderDate() {
        return getDaysBeforeDate(electionDay, REMINDER_DAYS_BEFORE, API_DATE_FORMAT, API_DATE_FORMAT);
    }

    public String getAbsenteeDeadline() {
        return getDaysBeforeDate(electionDay, ABSENTEE_DAYS_BEFORE, API_DATE_FORMAT, NUMBER_DATE_FORMAT);
    }

    public String getVoteDeadline() {
        return getFormattedDate(electionDay, API_DATE_FORMAT, NUMBER_DATE_FORMAT);
    }

    public String getSimpleElectionDay() {
        return getFormattedDate(electionDay, API_DATE_FORMAT, M_D_Y_FORMAT);
    }

    public String getShortElectionDay() {
        return getFormattedDate(electionDay, API_DATE_FORMAT, M_D_FORMAT);
    }

    public Integer getId() {
        return id;
    }


    public String getDivision() {
        return division;
    }

    public HashMap<String, Action> getActions() {
        return actions;
    }

    public void setActions(HashMap<String, Action> actions) {
        this.actions = actions;
    }

    public int getProgress() {
        int total = 0;
        int done = 0;
        for (Map.Entry entry : actions.entrySet()) {
            String status = ((Action) entry.getValue()).getStatus();
            if (!status.equals("closed")) {
                total++;
                if (status.equals("done")) {
                    done++;
                }
            }
        }
        return (int) (((float) done/total) * 100);
    }
}
