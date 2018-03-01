package com.eecglobal.eec.views;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.eecglobal.eec.R;
import com.eecglobal.eec.activities.Paytm.PayFeesActivity;
import com.eecglobal.eec.activities.centertour.CentersListActivity;
import com.eecglobal.eec.activities.menuscreens.CertificateRequestActivity;
import com.eecglobal.eec.activities.menuscreens.ContactUsActivity;
import com.eecglobal.eec.activities.menuscreens.CourseInformationActivity;
import com.eecglobal.eec.activities.menuscreens.FeedbackActivity;
import com.eecglobal.eec.activities.menuscreens.IdRequestActivity;
import com.eecglobal.eec.activities.menuscreens.MockScoreActivity;
import com.eecglobal.eec.activities.menuscreens.SpeakingScheduleActivity;
import com.eecglobal.eec.activities.menuscreens.collegesearch.CSPreferenceActivity;
import com.eecglobal.eec.activities.menuscreens.examcalenderscreens.ExamDatesCalenderActivity;
import com.eecglobal.eec.activities.menuscreens.profile.ProfileActivity;
import com.eecglobal.eec.activities.menuscreens.studentsuccess.SuccessListActivity;
import com.eecglobal.eec.activities.menuscreens.studyvideoscreens.SVChannelsListActivity;
import com.eecglobal.eec.activities.wizard.eecpreferences.CenterSelectionActivity;
import com.eecglobal.eec.activities.wizard.eecpreferences.CourseSelectionActivity;
import com.eecglobal.eec.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hbb20 on 26/11/16.
 */

public class MenuList {
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.l_menu_list_holder)
    LinearLayout lMenuListHolder;
    @BindView(R.id.l_menu_profile)
    LinearLayout lMenuProfile;
    @BindView(R.id.l_menu_course_info)
    LinearLayout lMenuCourseInfo;
    @BindView(R.id.l_menu_eec_events)
    LinearLayout lMenuEecEvents;
    @BindView(R.id.l_menu_class_timetable)
    LinearLayout lMenuClassTimetable;
    @BindView(R.id.l_menu_syllabus)
    LinearLayout lMenuSyllabus;
    @BindView(R.id.l_menu_mock_test_booking)
    LinearLayout lMenuMockTestBooking;
    @BindView(R.id.l_menu_mock_test_score)
    LinearLayout lMenuMockTestScore;
    @BindView(R.id.l_menu_ielts_speaking_dates)
    LinearLayout lMenuIeltsSpeakingDates;
    @BindView(R.id.l_menu_study_videos)
    LinearLayout lMenuStudyVideos;
    @BindView(R.id.l_menu_college_search)
    LinearLayout lMenuCollegeSearch;
    @BindView(R.id.l_menu_exam_calender)
    LinearLayout lMenuExamCalender;
    @BindView(R.id.l_menu_id_card_request)
    LinearLayout lMenuIdCardRequest;
    @BindView(R.id.l_menu_certificate_request)
    LinearLayout lMenuCertificateRequest;
    @BindView(R.id.l_menu_pay_eec_fees)
    LinearLayout lMenuPayEecFees;
    @BindView(R.id.l_menu_share_success)
    LinearLayout lMenuShareSuccess;
    @BindView(R.id.l_menu_feedback)
    LinearLayout lMenuFeedback;
    @BindView(R.id.l_menu_center_tour)
    LinearLayout lMenuCenterTour;
    @BindView(R.id.l_menu_contact_us)
    LinearLayout lMenuContactUs;

    /**
     * ButterKnife Code
     **/

    Context context;

    public MenuList(Context context, View rootView) {
        this.context = context;
        ButterKnife.bind(this, rootView);
        setupClickListeners();
    }

    private void setupClickListeners() {
        lMenuFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FeedbackActivity.class);
                context.startActivity(intent);
            }
        });

        lMenuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CenterSelectionActivity.launchForPreferenceUpdate(context);
                if (User.isLoggedIn(context)) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    context.startActivity(intent);
                } else {
                    User.promptToLogin(context);
                }

            }
        });

        lMenuCenterTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CentersListActivity.launch(context);
            }
        });
        lMenuStudyVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SVChannelsListActivity.class);
                context.startActivity(intent);
            }
        });

        lMenuIeltsSpeakingDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpeakingScheduleActivity.class);
                context.startActivity(intent);
            }
        });

        lMenuCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CourseInformationActivity.class);
                context.startActivity(intent);
            }
        });

        lMenuSyllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CenterSelectionActivity.launchForStudyPlanner(context);
            }
        });

        lMenuClassTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CenterSelectionActivity.launchForClassTimings(context);
            }
        });

        lMenuEecEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CenterSelectionActivity.launchForEvents(context);
            }
        });

        lMenuMockTestBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CenterSelectionActivity.launchForMockTest(context);
            }
        });

        lMenuCertificateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CertificateRequestActivity.launch(context);
            }
        });
        lMenuIdCardRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdRequestActivity.launch(context);
            }
        });

        lMenuShareSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuccessListActivity.launch(context);
            }

        });

        lMenuMockTestScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.isLoggedIn(context)) {
                    Intent intent = new Intent(context, MockScoreActivity.class);
                    context.startActivity(intent);
                } else {
                    User.promptToLogin(context);
                }
            }
        });

        lMenuExamCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseSelectionActivity.launch(context, ExamDatesCalenderActivity.MODE_EXAM_CALENDER);
            }
        });

        lMenuContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactUsActivity.launch(context);
            }
        });

        lMenuCollegeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CSPreferenceActivity.class);
                context.startActivity(intent);
            }
        });

        lMenuPayEecFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.isLoggedIn(context)) {
                    Intent intent = new Intent(context, PayFeesActivity.class);
                    context.startActivity(intent);
                } else {
                    User.promptToLogin(context);
                }

            }
        });


    }
}
